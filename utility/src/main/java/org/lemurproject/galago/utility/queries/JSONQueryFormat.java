package org.lemurproject.galago.utility.queries;

import org.lemurproject.galago.utility.Parameters;
import org.lemurproject.galago.utility.StreamCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This method handles the parsing of a Galago "query file" in many places.
 * @author jfoley.
 */
public class JSONQueryFormat {
  /**
   * this function extracts a list of queries from a parameter object. - there
   * are several methods of inputting queries: (query/queries) ->
   * String/List(String)/List(Map)
   *
   * if List(Map): [{"number":"id", "text":"query text"}, ...]
   */
  public static List<Parameters> collectQueries(Parameters parameters) throws IOException {

    List<Parameters> queries = new ArrayList<>();
    int unnumbered = 0;
    if (parameters.isString("query") || parameters.isList("query", String.class)) {

      String id;
      for (String q : parameters.getAsList("query", String.class)) {
        id = "unk-" + unnumbered;
        unnumbered++;
        queries.add(Parameters.parseArray("number", id, "text", q));
      }
    }
    if (parameters.isString("queries") || parameters.isList("queries", String.class)) {
      String id;
      for (String q : parameters.getAsList("query", String.class)) {
        id = "unk-" + unnumbered;
        unnumbered++;
        queries.add(Parameters.parseArray("number", id, "text", q));
      }
    }
    if (parameters.isList("query", Parameters.class)) {
      queries.addAll(parameters.getList("query", Parameters.class));
    }
    if (parameters.isList("queries", Parameters.class)) {
      List<Parameters> querList = parameters.getList("queries", Parameters.class);
      String scorer = parameters.getString("scorer");
      // System.out.println("scoreer: "+ scorer);
      // System.out.println(parameters);
      for(Parameters p : querList){
          String query = p.getString("text");
          switch (scorer){
            case "tf":
              p.set("text","#ctf("+query+")");
              break;
            case "tfidf":
              p.set("text","#ctf("+query+")");
              break;
            case "logtfidf":
              p.set("text","#ctf("+query+")");
              break;
            case "cosine":
              p.set("text","#ccosine("+query+")");
              break;
            default:
              break;
          }
          // System.out.println(p.getString("text"));

      }
      queries.addAll(querList);
      // System.out.println("after if " +queries);
    }
    // System.out.println("\n---------end---------\n"+queries);
    return queries;
  }

  public static List<Parameters> loadTSV(File input) throws IOException {
    ArrayList<Parameters> queries = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(StreamCreator.openInputStream(input), "UTF-8"))) {
      reader.lines().forEach((line) -> {
        if(line.trim().isEmpty()) return;
        String[] col = line.split("\t");
        assert(col.length == 2) : "Bad line: "+line;
        queries.add(Parameters.parseArray("number", col[0], "text", col[1]));
      });
    }
    return queries;
  }

  public static List<Parameters> collectTSVQueries(Parameters parameters) throws IOException {
    if(parameters.isString("query")) {
      return loadTSV(new File(parameters.getString("query")));
    } else {
      return loadTSV(new File(parameters.getString("queries")));
    }
  }
}
