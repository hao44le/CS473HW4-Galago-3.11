// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.contrib.retrieval.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lemurproject.galago.core.retrieval.LocalRetrieval;
import org.lemurproject.galago.core.retrieval.ScoredDocument;
import org.lemurproject.galago.core.retrieval.iterator.BaseIterator;
import org.lemurproject.galago.core.retrieval.iterator.DeltaScoringIterator;
import org.lemurproject.galago.core.retrieval.iterator.DisjunctionIterator;
import org.lemurproject.galago.core.retrieval.iterator.ScoreIterator;
import org.lemurproject.galago.core.retrieval.processing.DeltaScoringIteratorMaxDiffComparator;
import org.lemurproject.galago.core.retrieval.processing.ProcessingModel;
import static org.lemurproject.galago.core.retrieval.processing.ProcessingModel.toReversedArray;
import org.lemurproject.galago.core.retrieval.processing.ScoringContext;
import org.lemurproject.galago.core.retrieval.query.Node;
import org.lemurproject.galago.core.retrieval.query.NodeType;
import org.lemurproject.galago.core.util.FixedSizeMinHeap;
import org.lemurproject.galago.utility.Parameters;

/**
 * Assumes the use of delta functions for scoring, then prunes using Maxscore.
 * Generally this causes a substantial speedup in processing time.
 *
 * @author sjh
 */
public class NaiveAggressiveModel extends ProcessingModel {

  LocalRetrieval retrieval;

  public NaiveAggressiveModel(LocalRetrieval lr) {
    this.retrieval = lr;
  }

  @Override
  public ScoredDocument[] execute(Node queryTree, Parameters queryParams) throws Exception {
    ScoringContext context = new ScoringContext();
    int requested = (int) queryParams.get("requested", 1000);

    // step one: find the set of deltaScoringNodes in the tree
    List<Node> scoringNodes = new ArrayList();
    boolean canScore = findDeltaNodes(queryTree, scoringNodes, retrieval);
    if (!canScore) {
      throw new IllegalArgumentException("Query tree does not support delta scoring interface.\n" + queryTree.toPrettyString());
    }

    // step two: create an iterator for each node
    boolean shareNodes = false;
    List<DeltaScoringIterator> scoringIterators = createScoringIterators(scoringNodes, retrieval, shareNodes);

    FixedSizeMinHeap<ScoredDocument> queue = new FixedSizeMinHeap(ScoredDocument.class, requested, new ScoredDocument.ScoredDocumentComparator());

    // Main loop : 
    context.document = -1;

    while (true) {
      long candidate = Long.MAX_VALUE;
      for (int i = 0; i < scoringIterators.size(); i++) {
        // find a real candidate //
        // TODO: add a function that does this more efficiently //
        DeltaScoringIterator dsi = scoringIterators.get(i);
        scoringIterators.get(i).movePast(context.document);
        long c = scoringIterators.get(i).currentCandidate();
        while (!dsi.isDone() && !dsi.hasMatch(c)) {
          scoringIterators.get(i).movePast(c);
          c = scoringIterators.get(i).currentCandidate();
        }
        if (!dsi.isDone()) {
          candidate = (candidate < c) ? candidate : c;
        }
      }

      // Means sentinels are done, we can quit
      if (candidate == Long.MAX_VALUE) {
        break;
      }

      context.document = candidate;

      // Setup to score
      double runningScore = 0.0;

      // score all iterators
      int i = 0;
      while (i < scoringIterators.size()) {
        DeltaScoringIterator dsi = scoringIterators.get(i);
        dsi.syncTo(candidate);
        runningScore += dsi.score(context);
        ++i;
      }

      if (runningScore > queue.peek().score || queue.size() < requested) {
        ScoredDocument scoredDocument = new ScoredDocument(candidate, runningScore);
        queue.offer(scoredDocument);
      }
    }

    return toReversedArray(queue);
  }

  private boolean findDeltaNodes(Node n, List<Node> scorers, LocalRetrieval ret) throws Exception {
    // throw exception if we can't determine the class of each node.
    NodeType nt = ret.getNodeType(n);
    Class<? extends BaseIterator> iteratorClass = nt.getIteratorClass();

    if (DeltaScoringIterator.class.isAssignableFrom(iteratorClass)) {
      // we have a delta scoring class
      scorers.add(n);
      return true;

    } else if (DisjunctionIterator.class.isAssignableFrom(iteratorClass) && ScoreIterator.class.isAssignableFrom(iteratorClass)) {
      // we have a disjoint score combination node (e.g. #combine)
      boolean r = true;
      for (Node c : n.getInternalNodes()) {
        r &= findDeltaNodes(c, scorers, ret);
      }
      return r;

    } else {
      return false;
    }
  }

  private List<DeltaScoringIterator> createScoringIterators(List<Node> scoringNodes, LocalRetrieval ret, boolean shareNodes) throws Exception {
    List<DeltaScoringIterator> scoringIterators = new ArrayList();

    // the cache allows low level iterators to be shared
    Map<String, BaseIterator> queryIteratorCache;
    if (shareNodes) {
      queryIteratorCache = new HashMap();
    } else {
      queryIteratorCache = null;
    }
    for (int i = 0; i < scoringNodes.size(); i++) {
      DeltaScoringIterator scorer = (DeltaScoringIterator) ret.createNodeMergedIterator(scoringNodes.get(i), queryIteratorCache);
      scoringIterators.add(scorer);
    }
    return scoringIterators;
  }
}
