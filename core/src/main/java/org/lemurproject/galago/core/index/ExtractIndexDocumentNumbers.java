// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.index;

import org.lemurproject.galago.core.index.disk.DiskNameReader;
import org.lemurproject.galago.core.index.disk.DiskIndex;
import java.io.File;
import java.io.IOException;

import org.lemurproject.galago.core.parse.Document;
import org.lemurproject.galago.tupleflow.InputClass;
import org.lemurproject.galago.tupleflow.OutputClass;
import org.lemurproject.galago.tupleflow.StandardStep;
import org.lemurproject.galago.tupleflow.TupleFlowParameters;
import org.lemurproject.galago.tupleflow.execution.Verified;

/**
 * <p> Numbers documents using an existing index.</p>
 *
 * <p>The point of this class is to find the small identifier 
 * that is already associated with each document.  
 * NumberedDocuments are generated.
 * </p>
 * 
 * @author sjh
 */
@Verified
@InputClass(className = "org.lemurproject.galago.core.parse.Document")
@OutputClass(className = "org.lemurproject.galago.core.parse.Document")
public class ExtractIndexDocumentNumbers extends StandardStep<Document, Document> {

  private final DiskNameReader.KeyIterator namesIterator;

  public ExtractIndexDocumentNumbers(TupleFlowParameters parameters) throws IOException {
    String namesPath = parameters.getJSON().getString("indexPath") + File.separator + "names.reverse";
    namesIterator = ((DiskNameReader) DiskIndex.openIndexPart(namesPath)).getIterator();
  }

  public void process(Document doc) throws IOException {
    // it's possible that documents already have numbers
    if (doc.identifier >= 0) {
      try {
        namesIterator.findKey(doc.name);
        doc.identifier = namesIterator.getCurrentIdentifier();
      } catch (Exception e) {
        throw new IOException("Can not find document number for document: " + doc.name);
      }
    }
    processor.process(doc);
  }
}
