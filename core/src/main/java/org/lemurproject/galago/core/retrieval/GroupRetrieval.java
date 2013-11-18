/*
 *  BSD License (http://lemurproject.org/galago-license)
 */
package org.lemurproject.galago.core.retrieval;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.lemurproject.galago.core.index.stats.AggregateStatistics;
import org.lemurproject.galago.core.index.stats.FieldStatistics;
import org.lemurproject.galago.core.index.stats.IndexPartStatistics;
import org.lemurproject.galago.core.index.stats.NodeStatistics;
import org.lemurproject.galago.core.parse.Document;
import org.lemurproject.galago.core.parse.Document.DocumentComponents;
import org.lemurproject.galago.core.retrieval.query.Node;
import org.lemurproject.galago.core.retrieval.query.NodeType;
import org.lemurproject.galago.core.retrieval.query.QueryType;
import org.lemurproject.galago.core.retrieval.traversal.Traversal;
import org.lemurproject.galago.utility.Parameters;

/**
 * Provides a facility to map labels to an abstract retrieval. Therefore, if you
 * want a set of retrievals to be grouped under the "civil war" label, you can
 * tie them together using a MultiRetrieval, and list it here under "civil war".
 *
 * Note that the GroupRetrieval implementation has no facility to explicitly
 * group retrieval objects - that has been left to the MultiRetrieval class.
 *
 * @author sjh
 */
public class GroupRetrieval implements Retrieval {

  protected FeatureFactory features;
  protected Parameters globalParameters;
  protected String defGroup;
  protected Map<String, Retrieval> groups;
  protected List<Traversal> defaultTraversals;

  public GroupRetrieval(Map<String, Retrieval> groups, Parameters parameters,
          String defGroup) throws Exception {
    this.groups = groups;
    this.globalParameters = parameters;
    this.defGroup = defGroup;
    this.features = new FeatureFactory(globalParameters);
    defaultTraversals = features.getTraversals(this);
  }

  // IMPLEMENTED FUNCTIONS - Traversals use group-retrievals to collect aggregate stats
  public Collection<String> getGroups() {
    return groups.keySet();
  }

  @Override
  public Node transformQuery(Node queryTree, Parameters queryParams) throws Exception {
    for (Traversal traversal : defaultTraversals) {
      queryTree = traversal.traverse(queryTree, queryParams);
    }
    return queryTree;
  }

  @Override
  public void close() throws IOException {
    for (Retrieval r : groups.values()) {
      r.close();
    }
  }

  // DEFAULT FORWARDED FUNCTIONS
  @Override
  public Parameters getGlobalParameters() {
    return groups.get(defGroup).getGlobalParameters();
  }

  @Override
  public Parameters getAvailableParts() throws IOException {
    return groups.get(defGroup).getAvailableParts();
  }

  @Override
  public NodeType getNodeType(Node node) throws Exception {
    return groups.get(defGroup).getNodeType(node);
  }

  @Override
  public QueryType getQueryType(Node node) throws Exception {
    return groups.get(defGroup).getQueryType(node);
  }

  @Override
  public Results executeQuery(Node root) throws Exception {
    return groups.get(defGroup).executeQuery(root);
  }

  @Override
  public Results executeQuery(Node root, Parameters parameters) throws Exception {
    if (parameters.isString("group")) {
      return groups.get(parameters.getString("group")).executeQuery(root, parameters);
    }
    return groups.get(defGroup).executeQuery(root, parameters);
  }

  @Override
  public AggregateStatistics getStatisics(Node root, Parameters parameters) throws Exception {
    return groups.get(defGroup).getStatisics(root, parameters);
  }

  @Override
  public Map<Node, AggregateStatistics> getStatisics(Collection<Node> root, Parameters parameters) throws Exception {
    return groups.get(defGroup).getStatisics(root, parameters);
  }

  @Override
  public Document getDocument(String identifier, DocumentComponents p) throws IOException {
    return groups.get(defGroup).getDocument(identifier, p);
  }

  @Override
  public Map<String, Document> getDocuments(List<String> identifier, DocumentComponents p) throws IOException {
    return groups.get(defGroup).getDocuments(identifier, p);
  }

  @Override
  public Integer getDocumentLength(Integer docid) throws IOException {
    return groups.get(defGroup).getDocumentLength(docid);
  }

  @Override
  public Integer getDocumentLength(String docname) throws IOException {
    return groups.get(defGroup).getDocumentLength(docname);
  }

  @Override
  public String getDocumentName(Integer docid) throws IOException {
    return groups.get(defGroup).getDocumentName(docid);
  }

  @Override
  public void addNodeToCache(Node node) throws Exception {
    groups.get(defGroup).addNodeToCache(node);
  }

  @Override
  public void addAllNodesToCache(Node node) throws Exception {
    groups.get(defGroup).addAllNodesToCache(node);
  }

  // DEPRECATED //
  @Deprecated
  @Override
  public IndexPartStatistics getIndexPartStatistics(String partName) throws IOException {
    return groups.get(defGroup).getIndexPartStatistics(partName);
  }

  @Deprecated
  @Override
  public FieldStatistics getCollectionStatistics(String nodeString) throws Exception {
    return groups.get(defGroup).getCollectionStatistics(nodeString);
  }

  @Deprecated
  @Override
  public FieldStatistics getCollectionStatistics(Node node) throws Exception {
    return groups.get(defGroup).getCollectionStatistics(node);
  }

  @Deprecated
  @Override
  public NodeStatistics getNodeStatistics(String nodeString) throws Exception {
    return groups.get(defGroup).getNodeStatistics(nodeString);
  }

  @Deprecated
  @Override
  public NodeStatistics getNodeStatistics(Node node) throws Exception {
    return groups.get(defGroup).getNodeStatistics(node);
  }

  // IDENTICAL FUNCTIONS THAT USE PARTICULAR GROUPS //
  public Parameters getGlobalParameters(String group) {
    return groups.get(group).getGlobalParameters();
  }

  public Parameters getAvailableParts(String group) throws IOException {
    return groups.get(group).getAvailableParts();
  }

  public NodeType getNodeType(Node node, String group) throws Exception {
    return groups.get(group).getNodeType(node);
  }

  public QueryType getQueryType(Node node, String group) throws Exception {
    return groups.get(group).getQueryType(node);
  }

  public Node transformQuery(Node queryTree, Parameters qp, String group) throws Exception {
    return groups.get(group).transformQuery(queryTree, qp);
  }

  public Results executeQuery(Node root, String group) throws Exception {
    return groups.get(group).executeQuery(root);
  }

  public Results executeQuery(Node root, Parameters parameters, String group) throws Exception {
    return groups.get(group).executeQuery(root, parameters);
  }

  public AggregateStatistics getStatisics(Node root, Parameters parameters, String group) throws Exception {
    return groups.get(group).getStatisics(root, parameters);
  }

  public Map<Node, AggregateStatistics> getStatisics(Collection<Node> root, Parameters parameters, String group) throws Exception {
    return groups.get(group).getStatisics(root, parameters);
  }

  public Document getDocument(String identifier, DocumentComponents p, String group) throws IOException {
    return groups.get(group).getDocument(identifier, p);
  }

  public Map<String, Document> getDocuments(List<String> identifier, DocumentComponents p, String group) throws IOException {
    return groups.get(group).getDocuments(identifier, p);
  }

  public Integer getDocumentLength(Integer docid, String group) throws IOException {
    return groups.get(group).getDocumentLength(docid);
  }

  public Integer getDocumentLength(String docname, String group) throws IOException {
    return groups.get(group).getDocumentLength(docname);
  }

  public String getDocumentName(Integer docid, String group) throws IOException {
    return groups.get(group).getDocumentName(docid);
  }

  public void addNodeToCache(Node node, String group) throws Exception {
    groups.get(group).addNodeToCache(node);
  }

  public void addAllNodesToCache(Node node, String group) throws Exception {
    groups.get(group).addAllNodesToCache(node);
  }

  // more deprecated functions //
  @Deprecated
  public IndexPartStatistics getRetrievalStatistics(String partName, String group) throws IOException {
    return groups.get(group).getIndexPartStatistics(partName);
  }

  @Deprecated
  public FieldStatistics getCollectionStatistics(String nodeString, String group) throws Exception {
    return groups.get(group).getCollectionStatistics(nodeString);
  }

  @Deprecated
  public FieldStatistics getCollectionStatistics(Node node, String group) throws Exception {
    return groups.get(group).getCollectionStatistics(node);
  }

  @Deprecated
  public NodeStatistics getNodeStatistics(String nodeString, String group) throws Exception {
    return groups.get(group).getNodeStatistics(nodeString);
  }

  @Deprecated
  public NodeStatistics getNodeStatistics(Node node, String group) throws Exception {
    return groups.get(group).getNodeStatistics(node);
  }

  // META GroupRetrieval functions //
  public boolean containsGroup(String group) {
    return groups.containsKey(group);
  }

  public Retrieval getRetrieval(String group) {
    return groups.get(group);
  }
}
