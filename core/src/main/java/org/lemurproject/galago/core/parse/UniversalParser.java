// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.parse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.lemurproject.galago.tupleflow.Counter;
import org.lemurproject.galago.tupleflow.InputClass;
import org.lemurproject.galago.tupleflow.OutputClass;
import org.lemurproject.galago.tupleflow.StandardStep;
import org.lemurproject.galago.tupleflow.execution.Verified;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import org.lemurproject.galago.tupleflow.StreamCreator;
import org.lemurproject.galago.tupleflow.TupleFlowParameters;
import org.lemurproject.galago.core.types.DocumentSplit;
import org.lemurproject.galago.tupleflow.Parameters;
import org.lemurproject.galago.tupleflow.Utility;

/**
 *
 * @author trevor, sjh
 */
@Verified
@InputClass(className = "org.lemurproject.galago.core.types.DocumentSplit")
@OutputClass(className = "org.lemurproject.galago.core.parse.Document")
public class UniversalParser extends StandardStep<DocumentSplit, Document> {

  private Counter documentCounter;
  private Parameters parameters;
  private Logger logger = Logger.getLogger(getClass().toString());
  private byte[] subCollCheck = "subcoll".getBytes();
  private HashMap<String, Class> documentStreamParsers;

  public UniversalParser(TupleFlowParameters parameters) {
    this.documentCounter = parameters.getCounter("Documents Parsed");
    this.parameters = parameters.getJSON();

    initParsers();
  }

  private void initParsers() {
    documentStreamParsers = new HashMap();

    documentStreamParsers.put("html", FileParser.class);
    documentStreamParsers.put("xml", FileParser.class);
    documentStreamParsers.put("txt", FileParser.class);
    documentStreamParsers.put("arc", ArcParser.class);
    documentStreamParsers.put("warc", WARCParser.class);
    documentStreamParsers.put("trectext", TrecTextParser.class);
    documentStreamParsers.put("trecweb", TrecWebParser.class);
    documentStreamParsers.put("twitter", TwitterParser.class);
    documentStreamParsers.put("corpus", CorpusSplitParser.class);
    documentStreamParsers.put("wiki", WikiParser.class);
    documentStreamParsers.put("mbtei.book", MBTEIBookParser.class);
    documentStreamParsers.put("mbtei.page", MBTEIPageParser.class);
    documentStreamParsers.put("mbtei", MBTEIPageParser.class);

    // now add user defined/overriding document parsers:
    if (parameters.containsKey("parsers")) {
      Parameters parsers = parameters.getMap("parsers");
      for (String ext : parsers.getKeys()) {
        try {
          documentStreamParsers.put(ext, Class.forName(parsers.getString(ext)));
        } catch (ClassNotFoundException ex) {
          System.err.println("Document Parser for " + ext + " : " + parsers.getString(ext) + " could not be found.");
        }
      }
    }
  }

  public boolean isParsable(String extension) {
    return parameters.isString("filetype") || this.documentStreamParsers.containsKey(extension);
  }

  @Override
  public void process(DocumentSplit split) throws IOException {
    long count = 0;
    long limit = Long.MAX_VALUE;
    if (split.startKey.length > 0) {
      if (Utility.compare(subCollCheck, split.startKey) == 0) {
        limit = Utility.uncompressLong(split.endKey, 0);
      }
    }

    if (this.documentStreamParsers.containsKey(split.fileType)) {
      try {
        Class c = documentStreamParsers.get(split.fileType);
        Constructor cstr = c.getConstructor(DocumentSplit.class, Parameters.class);
        DocumentStreamParser parser = (DocumentStreamParser) cstr.newInstance(split, parameters);

        Document document;
        while ((document = parser.nextDocument()) != null) {
          document.fileId = split.fileId;
          document.totalFileCount = split.totalFileCount;

          processor.process(document);
          if (documentCounter != null) {
            documentCounter.increment();
          }
          count++;

          // Enforces limitations imposed by the endKey subcollection specifier.
          // See DocumentSource for details.
          if (count >= limit) {
            break;
          }
        }

        if (parser != null) {
          parser.close();
        }

      } catch (Exception ex) {
        logger.log(Level.INFO, "Failed to parse document split - {0} as {1}\n", new Object[]{split.toString(), split.fileType});
        logger.log(Level.SEVERE, ex.toString());
      }
    } else {
      logger.log(Level.INFO, "Ignoring {0} - could not find a parser for file-type:{1}\n", new Object[]{split.toString(), split.fileType});
    }
  }
}
