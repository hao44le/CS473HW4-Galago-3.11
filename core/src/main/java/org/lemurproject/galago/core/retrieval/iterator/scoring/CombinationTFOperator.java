// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.retrieval.iterator.scoring;
import org.lemurproject.galago.core.retrieval.RequiredParameters;
import org.lemurproject.galago.core.retrieval.processing.ScoringContext;
import org.lemurproject.galago.core.retrieval.query.AnnotatedNode;
import org.lemurproject.galago.core.retrieval.query.NodeParameters;
import org.lemurproject.galago.core.retrieval.iterator.*;
import org.lemurproject.galago.core.retrieval.ann.OperatorDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class CombinationTFOperator extends DisjunctionIterator implements ScoreIterator {

  NodeParameters np;
  protected ScoreIterator[] scoreIterators;

  public CombinationTFOperator(NodeParameters parameters,
          ScoreIterator[] childIterators) {
    super(childIterators);

    assert (childIterators.length > 0) : "#combine nodes must have more than 1 child.";

    this.np = parameters;

    this.scoreIterators = childIterators;
  }

  @Override
  public double score(ScoringContext c) {
    double total = 0;
    for (int i = 0; i < scoreIterators.length; i++) {
      double score = scoreIterators[i].score(c);
      total += score;
    }
    return total;
  }

  @Override
  public double minimumScore() {
    double min = 0;
    for (int i = 0; i < scoreIterators.length; i++) {
      min += scoreIterators[i].minimumScore();
    }
    return min;
  }

  @Override
  public double maximumScore() {
    double max = 0;
    for (int i = 0; i < scoreIterators.length; i++) {
      max += scoreIterators[i].maximumScore();
    }
    return max;
  }

  @Override
  public String getValueString(ScoringContext c) throws IOException {
    return this.currentCandidate() + " " + this.score(c);
  }

  @Override
  public AnnotatedNode getAnnotatedNode(ScoringContext c) throws IOException {
    String type = "score";
    String className = this.getClass().getSimpleName();
    String parameters = np.toString();
    long document = currentCandidate();
    boolean atCandidate = hasMatch(c);
    String returnValue = Double.toString(score(c));
    List<AnnotatedNode> children = new ArrayList<>();
    for (BaseIterator child : this.iterators) {
      children.add(child.getAnnotatedNode(c));
    }

    return new AnnotatedNode(type, className, parameters, document, atCandidate, returnValue, children);
  }
}
