// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.retrieval.processing;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.lemurproject.galago.core.index.Index;
import org.lemurproject.galago.core.retrieval.LocalRetrieval;
import org.lemurproject.galago.core.retrieval.ScoredDocument;
import org.lemurproject.galago.core.retrieval.iterator.MovableScoreIterator;
import org.lemurproject.galago.core.retrieval.query.Node;
import org.lemurproject.galago.tupleflow.Parameters;

/**
 * Performs straightforward document-at-a-time (daat) processing of a fully
 * annotated query, processing scores over documents.
 *
 * @author irmarc
 */
public class RankedDocumentModel extends ProcessingModel {

  LocalRetrieval retrieval;
  Index index;
  List<Integer> whitelist;

  public RankedDocumentModel(LocalRetrieval lr) {
    retrieval = lr;
    this.index = retrieval.getIndex();
    whitelist = null;
  }

  @Override
  public void defineWorkingSet(List<Integer> docs) {
    Collections.sort(docs);
    whitelist = docs;
  }

  @Override
  public ScoredDocument[] execute(Node queryTree, Parameters queryParams) throws Exception {
    if (whitelist == null) {
      return executeWholeCollection(queryTree, queryParams);
    } else {
      return executeWorkingSet(queryTree, queryParams);
    }
  }

  private ScoredDocument[] executeWorkingSet(Node queryTree, Parameters queryParams)
          throws Exception {
    // This model uses the simplest ScoringContext
    ScoringContext context = new ScoringContext();

    // construct the query iterators
    MovableScoreIterator iterator =
            (MovableScoreIterator) retrieval.createIterator(queryParams, queryTree, context);
    int requested = (int) queryParams.get("requested", 1000);
    boolean annotate = queryParams.get("annotate", false);

    // now there should be an iterator at the root of this tree
    PriorityQueue<ScoredDocument> queue = new PriorityQueue<ScoredDocument>();

    for (int i = 0; i < whitelist.size(); i++) {
      int document = whitelist.get(i);
      iterator.syncTo(document);
      context.document = document;

      // This context is shared among all scorers
      double score = iterator.score();
      if (requested < 0 || queue.size() <= requested || queue.peek().score < score) {
        ScoredDocument scoredDocument = new ScoredDocument(document, score);
        if (annotate) {
          scoredDocument.annotation = iterator.getAnnotatedNode();
        }
        queue.add(scoredDocument);
        if (requested > 0 && queue.size() > requested) {
          queue.poll();
        }
      }
    }
    return toReversedArray(queue);
  }

  private ScoredDocument[] executeWholeCollection(Node queryTree, Parameters queryParams)
          throws Exception {
    // This model uses the simplest ScoringContext
    ScoringContext context = new ScoringContext();

    // Number of documents requested.
    int requested = (int) queryParams.get("requested", 1000);
    boolean annotate = queryParams.get("annotate", false);

    // Maintain a queue of candidates
    PriorityQueue<ScoredDocument> queue = new PriorityQueue<ScoredDocument>(requested);

    // construct the iterators -- we use tree processing
    MovableScoreIterator iterator = (MovableScoreIterator) retrieval.createIterator(queryParams, queryTree, context);

    // now there should be an iterator at the root of this tree
    while (!iterator.isDone()) {
      int document = iterator.currentCandidate();
      ////CallTable.increment("doc_begin");

      // This context is shared among all scorers
      context.document = document;
      iterator.syncTo(document);
      if (iterator.hasMatch(document)) {
        double score = iterator.score();
        ////CallTable.increment("doc_finish");
        if (requested < 0 || queue.size() <= requested || queue.peek().score < score) {
          ScoredDocument scoredDocument = new ScoredDocument(document, score);
          if (annotate) {
            scoredDocument.annotation = iterator.getAnnotatedNode();
          }
          queue.add(scoredDocument);
          ////CallTable.increment("heap_insert");
          if (requested > 0 && queue.size() > requested) {
            queue.poll();
            ////CallTable.increment("heap_eject");
          }
        } else {
          ////CallTable.increment("heap_miss");
        }
        ////CallTable.max("heap_max_size", queue.size());
      }
      iterator.movePast(document);
    }
    ////CallTable.set("heap_end_size", queue.size());
    return toReversedArray(queue);
  }
}
