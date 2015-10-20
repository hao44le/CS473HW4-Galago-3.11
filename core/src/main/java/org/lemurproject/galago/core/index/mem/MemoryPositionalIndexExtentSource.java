// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.index.mem;

import org.lemurproject.galago.core.index.mem.MemoryPositionalIndex.PositionalPostingList;
import org.lemurproject.galago.core.index.source.ExtentSource;
import org.lemurproject.galago.core.index.source.MemValueSource;
import org.lemurproject.galago.core.index.stats.NodeStatistics;
import org.lemurproject.galago.core.util.ExtentArray;
import org.lemurproject.galago.utility.buffer.VByteInput;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author sjh
 */
public class MemoryPositionalIndexExtentSource extends MemValueSource implements ExtentSource {

  PositionalPostingList postings;
  VByteInput documents_reader;
  VByteInput counts_reader;
  VByteInput positions_reader;
  long iteratedDocs;
  long currDocument;
  int currCount;
  ExtentArray extents;
  ExtentArray emptyExtents;
  boolean done;
  // stats
  private NodeStatistics stats;
  private long finalDocument;
  private int finalCount;

  public MemoryPositionalIndexExtentSource(PositionalPostingList data) throws IOException {
    super(data.key());
    this.postings = data;
    reset();
  }

  @Override
  public void reset() throws IOException {

    // stats extracted from postings
    finalDocument = postings.lastDocument();
    finalCount = postings.lastCount();
    stats = postings.stats();

    documents_reader = new VByteInput(
            new DataInputStream(
            new ByteArrayInputStream(postings.getDocumentDataBytes())));
    counts_reader = new VByteInput(
            new DataInputStream(
            new ByteArrayInputStream(postings.getCountDataBytes())));
    positions_reader = new VByteInput(
            new DataInputStream(
            new ByteArrayInputStream(postings.getPositionDataBytes())));

    iteratedDocs = 0;
    currDocument = 0;
    currCount = 0;
    done = false;
    extents = new ExtentArray();
    emptyExtents = new ExtentArray();

    read();
  }

  @Override
  public int count(long id) {
    if (!done && this.currDocument == id) {
      return currCount;
    }
    return 0;
  }

  @Override
  public ExtentArray extents(long id) {
    if (!done && this.currDocument == id) {
      return extents;
    }
    return this.emptyExtents;
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public long currentCandidate() {
    return currDocument;
  }

  @Override
  public boolean hasMatch(long identifier) {
    return (!isDone() && identifier == currDocument);
  }

  @Override
  public boolean hasAllCandidates() {
    return false;
  }

  public void loadExtents() throws IOException {
    extents.reset();
    extents.setDocument(currDocument);
    int position = 0;
    for (int i = 0; i < currCount; i++) {
      position += positions_reader.readInt();
      extents.add(position);
    }
  }

  public void read() throws IOException {
    if (iteratedDocs >= stats.nodeDocumentCount) {
      done = true;
      return;
    } else if (iteratedDocs == stats.nodeDocumentCount - 1) {
      currDocument = finalDocument;
      currCount = finalCount;
    } else {
      currDocument += documents_reader.readLong();
      currCount = counts_reader.readInt();
    }
    loadExtents();

    iteratedDocs++;
  }

  @Override
  public void syncTo(long identifier) throws IOException {
    // TODO: need to implement skip lists

    while (!isDone() && (currDocument < identifier)) {
      read();
    }
  }

  @Override
  public void movePast(long identifier) throws IOException {
    syncTo(identifier+1);
  }

  @Override
  public long totalEntries() {
    return stats.nodeDocumentCount;
  }

  @Override
  public NodeStatistics getStatistics() {
    return stats.clone();
  }
}
