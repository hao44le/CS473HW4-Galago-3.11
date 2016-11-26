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
@RequiredStatistics(statistics = {"collectionLength", "documentCount"})
@RequiredParameters(parameters = {"c"})
public class CosineScoringIterator extends ScoringFunctionIterator {

  private final LengthsIterator lengths;
  private final CountIterator counts;
  private final NodeParameters np;
  // parameter :
  private final double c;
  // collectionStats and constants
  private final double averageDocumentLength;

  public CosineScoringIterator(NodeParameters np, LengthsIterator lengths, CountIterator counts) throws IOException  {
    super(np,lengths,counts);
    this.np = np;
    this.counts = counts;
    this.lengths = lengths;
    c = np.get("c", 1.0);
    averageDocumentLength = (double) np.getLong("collectionLength") / (double) np.getLong("documentCount");
  }


  @Override
  public double score(ScoringContext c) {
    double tf = counts.count(c);
    return tf;
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
