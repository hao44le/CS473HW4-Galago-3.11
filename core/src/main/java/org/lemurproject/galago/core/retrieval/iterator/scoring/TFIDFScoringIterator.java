/*
 *  BSD License (http://lemurproject.org/galago-license)
 */
package org.lemurproject.galago.core.retrieval.iterator.scoring;

import org.lemurproject.galago.core.retrieval.RequiredParameters;
import org.lemurproject.galago.core.retrieval.RequiredStatistics;
import org.lemurproject.galago.core.retrieval.iterator.CountIterator;
import org.lemurproject.galago.core.retrieval.iterator.LengthsIterator;
import org.lemurproject.galago.core.retrieval.iterator.ScoreIterator;
import org.lemurproject.galago.core.retrieval.iterator.TransformIterator;
import org.lemurproject.galago.core.retrieval.processing.ScoringContext;
import org.lemurproject.galago.core.retrieval.query.AnnotatedNode;
import org.lemurproject.galago.core.retrieval.query.NodeParameters;
import org.lemurproject.galago.tupleflow.Utility;
import org.lemurproject.galago.core.retrieval.iterator.ScoringFunctionIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements BiL2 retrieval model from the DFR framework.
 *
 * @author sjh
 */
@RequiredStatistics(statistics = {"documentCount","nodeDocumentCount"})
@RequiredParameters(parameters = {"c"})
public class TFIDFScoringIterator extends ScoringFunctionIterator {
  private final CountIterator counts;
  private final long documentCount;
  private final long df;

  public TFIDFScoringIterator(NodeParameters np, LengthsIterator lengths, CountIterator counts) throws IOException  {
    super(np,lengths,counts);
    this.counts = counts;
    documentCount = np.getLong("documentCount");
    df = np.getLong("nodeDocumentCount");
  }


  @Override
  public double score(ScoringContext c) {
    double tf = counts.count(c);
    if(df==0)return -1.0;
    double idf = Math.log(documentCount / df);
    return tf*idf;
  }

  @Override
  public double maximumScore() {
    return Double.POSITIVE_INFINITY;
  }

  @Override
  public double minimumScore() {
    return Double.NEGATIVE_INFINITY;
  }
}
