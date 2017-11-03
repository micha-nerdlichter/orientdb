package com.orientechnologies.lucene.search;

import com.orientechnologies.lucene.query.OLuceneQueryContext;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Explanation;

import java.io.IOException;
import java.util.*;

public class OLuceneExplanation {
  private OLuceneQueryContext queryContext;
  private ScoreDoc score;
  
  public OLuceneExplanation(OLuceneQueryContext queryContext, ScoreDoc score) {
    this.queryContext = queryContext;
    this.score = score;
  }
  
  public String toString() {
    Explanation expl = null;
    try {
       expl = queryContext.getSearcher().explain(queryContext.query, score.doc);
    } catch (IOException ioe) {
    }
    return expl.toString();
  }
}
