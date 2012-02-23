// BSD License (http://lemurproject.org/galago-license)
package org.lemurproject.galago.core.retrieval;

import org.lemurproject.galago.core.retrieval.processing.RankedDocumentModel;
import org.lemurproject.galago.core.retrieval.processing.ProcessingModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import org.lemurproject.galago.core.index.AggregateReader.AggregateIterator;
import org.lemurproject.galago.core.index.AggregateReader.CollectionStatistics;
import org.lemurproject.galago.core.index.AggregateReader.NodeStatistics;
import org.lemurproject.galago.core.index.Index;
import org.lemurproject.galago.core.index.NamesReader.Iterator;
import org.lemurproject.galago.core.index.disk.CachedDiskIndex;
import org.lemurproject.galago.core.index.disk.DiskIndex;
import org.lemurproject.galago.core.parse.Document;
import org.lemurproject.galago.core.retrieval.structured.FeatureFactory;
import org.lemurproject.galago.core.retrieval.query.NodeType;
import org.lemurproject.galago.core.retrieval.traversal.Traversal;
import org.lemurproject.galago.core.retrieval.query.Node;
import org.lemurproject.galago.core.retrieval.query.QueryType;
import org.lemurproject.galago.core.retrieval.query.StructuredQuery;
import org.lemurproject.galago.core.retrieval.iterator.AbstractIndicator;
import org.lemurproject.galago.core.retrieval.iterator.ContextualIterator;
import org.lemurproject.galago.core.retrieval.iterator.CountIterator;
import org.lemurproject.galago.core.retrieval.iterator.CountValueIterator;
import org.lemurproject.galago.core.retrieval.processing.ScoringContext;
import org.lemurproject.galago.core.retrieval.iterator.ScoreIterator;
import org.lemurproject.galago.core.retrieval.iterator.ScoringFunctionIterator;
import org.lemurproject.galago.core.retrieval.iterator.StructuredIterator;
import org.lemurproject.galago.core.retrieval.processing.RankedPassageModel;
import org.lemurproject.galago.tupleflow.Parameters;
import org.lemurproject.galago.tupleflow.Parameters.Type;
import org.lemurproject.galago.tupleflow.Utility;

/**
 * The responsibility of the LocalRetrieval object is to
 * provide a simpler interface on top of the DiskIndex.
 * Therefore, given a query or text string representing a query,
 * this object will perform the necessary transformations to make
 * it an executable object.
 *
 * 10/7/2010 - Modified for asynchronous execution
 *
 * @author trevor
 * @author irmarc
 */
public class LocalRetrieval implements Retrieval {

  protected Index index;
  protected FeatureFactory features;
  protected Parameters globalParameters;

  /**
   * One retrieval interacts with one index. Parameters dictate the behavior
   * during retrieval time, and selection of the appropriate feature factory.
   * Additionally, the supplied parameters will be passed forward to the chosen
   * feature factory.
   */
  public LocalRetrieval(Index index) throws IOException {
    this(index, new Parameters());
  }

  public LocalRetrieval(Index index, Parameters parameters) throws IOException {
    this.globalParameters = parameters;
    setIndex(index);
  }

  /** 
   * For this constructor, being sent a filename path to the indicies, 
   * we first list out all the directories in the path. If there are none, then 
   * we can safely assume that the filename specifies a single index (the files 
   * listed are all parts), otherwise we will treat each subdirectory as a 
   * separate logical index.
   */
  public LocalRetrieval(String filename, Parameters parameters)
          throws FileNotFoundException, IOException, Exception {
    this.globalParameters = parameters;
    if (globalParameters.containsKey("cacheQueries")) {
      CachedDiskIndex cachedIndex = new CachedDiskIndex(filename);
      setIndex(cachedIndex);

      if (globalParameters.isList("cacheQueries", Type.STRING)) {
        List<String> queries = globalParameters.getAsList("cacheQueries");
        for (String q : queries) {
          Node queryTree = StructuredQuery.parse(q);
          queryTree = transformQuery(queryTree);
          cachedIndex.cacheQueryData(queryTree);
        }
      } else if (globalParameters.isList("cacheQueries", Type.MAP)) {
        List<Parameters> queries = globalParameters.getAsList("cacheQueries");
        for (Parameters q : queries) {
          Node queryTree = StructuredQuery.parse(q.getString("text"));
          queryTree = transformQuery(queryTree);
          cachedIndex.cacheQueryData(queryTree);
        }
      } else {
        Logger.getLogger(this.getClass().getName()).info("Could not process cachedQueries list. No posting list data cached.");
      }
    } else {
      setIndex(new DiskIndex(filename));
    }
  }

  private void setIndex(Index indx) throws IOException {
    this.index = indx;

    // Handle parameters for this index (since some of these can be different)
    features = new FeatureFactory(globalParameters);
  }

  /**
   * Closes the underlying index
   */
  @Override
  public void close() throws IOException {
    index.close();
  }

  /**
   * Returns the collectionLength and documentCount of a given index, contained
   * in a Parameters object. Additional statistics may be provided, but are not
   * expected.
   */
  @Override
  public CollectionStatistics getRetrievalStatistics(String partName) throws IOException {
    return index.getCollectionStatistics(partName);
  }

  @Override
  public CollectionStatistics getRetrievalStatistics() throws IOException {
    return index.getCollectionStatistics();
  }

  @Override
  public Parameters getGlobalParameters() {
    return this.globalParameters;
  }

  /*
   * {
   *  <partName> : { <nodeName> : <iteratorClass>, stemming : false, ... },
   *  <partName> : { <nodeName> : <iteratorClass>, ... },
   *  ...
   * }
   */
  @Override
  public Parameters getAvailableParts() throws IOException {
    Parameters p = new Parameters();
    ArrayList<String> parts = new ArrayList<String>();
    for (String partName : index.getPartNames()) {
      Parameters inner = new Parameters();
      Map<String, NodeType> nodeTypes = index.getPartNodeTypes(partName);
      for (String nodeName : nodeTypes.keySet()) {
        inner.set(nodeName, nodeTypes.get(nodeName).getIteratorClass().getName());
      }
      p.set(partName, inner);
    }
    return p;
  }

  public Index getIndex() {
    return index;
  }

  @Override
  public Document getDocument(String identifier) throws IOException {
    return this.index.getDocument(identifier);
  }

  @Override
  public Map<String, Document> getDocuments(List<String> identifier) throws IOException {
    return this.index.getDocuments(identifier);
  }

  private ProcessingModel determineProcessingModel(Node queryTree, Parameters p) throws Exception {
    QueryType qt = this.getQueryType(queryTree);
    if (qt == QueryType.BOOLEAN) {
    } else if (qt == QueryType.RANKED) {
      if (p.containsKey("passageSize") || p.containsKey("passageShift")) {
        return new RankedPassageModel(this);
      } else {
        return new RankedDocumentModel(this);
      }
    }
    throw new RuntimeException(String.format("Unable to determine processing model for %s",
            queryTree.toString()));
  }

  /**
   * Accepts a transformed query, constructs the iterator tree from the node tree,
   * then iterates over the iterator tree, and returns the results.
   */
  public ScoredDocument[] runQuery(String query, Parameters p) throws Exception {
    Node root = StructuredQuery.parse(query);
    root = transformQuery(root);
    return runQuery(root, p);
  }

  @Override
  public ScoredDocument[] runQuery(Node queryTree) throws Exception {
    return runQuery(queryTree, new Parameters());
  }

  // Based on the root of the tree, that dictates how we execute.
  @Override
  public ScoredDocument[] runQuery(Node queryTree, Parameters queryParams) throws Exception {
    ScoredDocument[] results = null;
    ProcessingModel pm = determineProcessingModel(queryTree, queryParams);

    // Figure out if there's a working set to deal with
    int[] workingSet = null;

    if (queryParams.containsKey("working")) {
      workingSet = this.getDocumentIds(queryParams.getList("working"));
    }

    if (workingSet != null) {
      pm.defineWorkingSet(workingSet);
    }

    // get some results
    results = pm.execute(queryTree, queryParams);
    if (results == null) {
      results = new ScoredDocument[0];
    }

    // Format and get names
    String indexId = this.globalParameters.get("indexId", "0");
    return getArrayResults(results, indexId);
  }

  /*
   * getArrayResults annotates a queue of scored documents
   * returns an array
   *
   */
  protected <T extends ScoredDocument> T[] getArrayResults(T[] results, String indexId) throws IOException {
    if (results == null || results.length == 0) {
      return null;
    }

    for (int i = 0; i < results.length; i++) {
      results[i].source = indexId;
      results[i].rank = i + 1;
    }

    // this is to assign proper document names
    T[] byID = Arrays.copyOf(results, results.length);

    Arrays.sort(byID, new Comparator<T>() {

      @Override
      public int compare(T o1, T o2) {
        return Utility.compare(o1.document, o2.document);
      }
    });

    // TODO: fix this to use an iterator.
    Iterator namesIterator = index.getNamesIterator();

    for (T doc : byID) {
      namesIterator.skipToKey(doc.document);
      if (doc.document == namesIterator.getCurrentIdentifier()) {
        doc.documentName = namesIterator.getCurrentName();
      } else {
        System.err.println("NAMES ITERATOR FAILED TO FIND DOCUMENT " + doc.document);
        doc.documentName = index.getName(doc.document);
      }
    }

    return results;
  }

  public StructuredIterator createIterator(Node node, ScoringContext context) throws Exception {
    HashMap<String, StructuredIterator> iteratorCache = new HashMap();
    return createNodeMergedIterator(node, context, iteratorCache);
  }

  protected StructuredIterator createNodeMergedIterator(Node node, ScoringContext context,
          HashMap<String, StructuredIterator> iteratorCache)
          throws Exception {
    ArrayList<StructuredIterator> internalIterators = new ArrayList<StructuredIterator>();
    StructuredIterator iterator;

    // first check if the cache contains this node
    if (globalParameters.get("shareNodes", false)
            && iteratorCache.containsKey(node.toString())) {
      return iteratorCache.get(node.toString());
    }

    for (Node internalNode : node.getInternalNodes()) {
      StructuredIterator internalIterator = createNodeMergedIterator(internalNode, context, iteratorCache);
      internalIterators.add(internalIterator);
    }

    iterator = index.getIterator(node);
    if (iterator == null) {
      iterator = features.getIterator(node, internalIterators);
    }

    if (ContextualIterator.class.isInstance(iterator) && (context != null)) {
      ((ContextualIterator) iterator).setContext(context);
    }

    // we've created a new iterator - add to the cache for future nodes
    if (globalParameters.get("shareNodes", false)) {
      iteratorCache.put(node.toString(), iterator);
    }
    return iterator;
  }

  @Override
  public Node transformQuery(Node queryTree) throws Exception {
    return transformQuery(features.getTraversals(this), queryTree);
  }

  private Node transformQuery(List<Traversal> traversals, Node queryTree) throws Exception {
    for (Traversal traversal : traversals) {
      queryTree = StructuredQuery.walk(traversal, queryTree);
    }
    return queryTree;
  }

  @Override
  public NodeStatistics nodeStatistics(String nodeString) throws Exception {
    // first parse the node
    Node root = StructuredQuery.parse(nodeString);
    root.getNodeParameters().set("queryType", "count");
    root = transformQuery(root);
    return nodeStatistics(root);
  }

  @Override
  public NodeStatistics nodeStatistics(Node root) throws Exception {
    NodeStatistics stats = new NodeStatistics();
    // set up initial values
    stats.node = root.toString();
    stats.nodeDocumentCount = 0;
    stats.nodeFrequency = 0;
    stats.collectionLength = getRetrievalStatistics().collectionLength;
    stats.documentCount = getRetrievalStatistics().documentCount;

    StructuredIterator structIterator = createIterator(root, null);
    if (AggregateIterator.class.isInstance(structIterator)) {
      stats = ((AggregateIterator) structIterator).getStatistics();
    } else if (structIterator instanceof CountIterator) {
      CountValueIterator iterator = (CountValueIterator) structIterator;
      while (!iterator.isDone()) {
        if (iterator.hasMatch(iterator.currentCandidate())) {
          stats.nodeFrequency += iterator.count();
          stats.nodeDocumentCount++;
        }
        iterator.next();
      }
    } else {
      throw new IllegalArgumentException("Node " + root.toString() + " did not return a counting iterator.");
    }
    return stats;
  }

  @Override
  public NodeType getNodeType(Node node) throws Exception {
    NodeType nodeType = index.getNodeType(node);
    if (nodeType == null) {
      nodeType = features.getNodeType(node);
    }
    return nodeType;
  }

  @Override
  public QueryType getQueryType(Node node) throws Exception {
    if (node.getOperator().equals("text")) {
      return QueryType.UNKNOWN;
    }
    NodeType nodeType = getNodeType(node);
    Class outputClass = nodeType.getIteratorClass();
    if (ScoreIterator.class.isAssignableFrom(outputClass)
            || ScoringFunctionIterator.class.isAssignableFrom(outputClass)) {
      return QueryType.RANKED;
    } else if (AbstractIndicator.class.isAssignableFrom(outputClass)) {
      return QueryType.BOOLEAN;
    } else if (CountIterator.class.isAssignableFrom(outputClass)) {
      return QueryType.COUNT;
    } else {
      return QueryType.RANKED;
    }
  }

  @Override
  public int getDocumentLength(int docid) throws IOException {
    return index.getLength(docid);
  }

  @Override
  public int getDocumentLength(String docname) throws IOException {
    return index.getLength(index.getIdentifier(docname));
  }

  @Override
  public String getDocumentName(int docid) throws IOException {
    return index.getName(docid);
  }

  public int[] getDocumentIds(List<String> docnames) throws IOException {
    int[] ids = new int[docnames.size()];
    int i = 0;
    for (String name : docnames) {
      ids[i] = index.getIdentifier(name);
      i++;
    }
    return ids;
  }
}
