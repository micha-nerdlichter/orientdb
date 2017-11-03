package com.orientechnologies.lucene.search;

import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.lucene.query.OLuceneQueryContext;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.*;

public class OLuceneHits extends OLazyHashMap {
  private OLuceneQueryContext queryContext;
  private ScoreDoc score;
  private Query queryOriginal;
  
  public OLuceneHits(OLuceneQueryContext queryContext, ScoreDoc score, Query queryOriginal) {
    this.queryContext = queryContext;
    this.score = score;
    this.queryOriginal = queryOriginal;
  }
  
  @Override
  protected Map<String, Map<String, Float>> load() {
    Map<String, Map<String, Float>> hits = new HashMap<String, Map<String, Float>>();
    try {
      collectHits(queryContext.getSearcher(), score, queryOriginal, queryContext.query, hits);
    } catch (IOException ioe) {}
    return hits;
  }

  private void collectHits(IndexSearcher searcher, ScoreDoc score, Query queryOriginal, Query query, Map<String, Map<String, Float>> hits) throws IOException {
    if (queryOriginal instanceof PhraseQuery) {
      Explanation expl = searcher.explain(query, score.doc);
      if (expl.isMatch()) {
        Term[] terms = ((PhraseQuery) queryOriginal).getTerms();
        String field = terms[0].field();
        if (!hits.containsKey(field)) {
          hits.put(field, new HashMap<String, Float>());
        }
        StringBuilder phrase = new StringBuilder();
        String delimiter = "";
        for (Term term : terms) {
          phrase.append(delimiter).append(term.text());
          delimiter = " ";
        }
        hits.get(field).put(phrase.toString(), expl.getValue());
      }
    }

    if (queryOriginal instanceof TermQuery) {
      Explanation expl = searcher.explain(query, score.doc);
      if (expl.isMatch()) {
        Term term = ((TermQuery)queryOriginal).getTerm();
        if (!hits.containsKey(term.field())) {
          hits.put(term.field(), new HashMap<String, Float>());
        }
        hits.get(term.field()).put(term.text(), expl.getValue());
      }
    }
    
    if (queryOriginal instanceof BooleanQuery && query instanceof BooleanQuery) {
      Iterator it1 = ((BooleanQuery)queryOriginal).clauses().iterator();
      Iterator it2 = ((BooleanQuery)query).clauses().iterator();
      while(it1.hasNext() && it2.hasNext()) {
        collectHits(
            searcher,
            score,
            ((BooleanClause)it1.next()).getQuery(),
            ((BooleanClause)it2.next()).getQuery(),
             hits
        );
      }
    }
  }
}