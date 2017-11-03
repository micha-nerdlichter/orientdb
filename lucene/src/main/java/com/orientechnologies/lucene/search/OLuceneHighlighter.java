package com.orientechnologies.lucene.search;

import com.orientechnologies.lucene.query.OLuceneQueryContext;
import com.orientechnologies.orient.core.id.OContextualRecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.highlight.*;

import java.io.IOException;
import java.util.*;

public class OLuceneHighlighter extends OLazyHashMap {
  private OLuceneQueryContext queryContext;
  private OLuceneHits hits;
  private OContextualRecordId recordId;
  private Analyzer analyzer;

  public OLuceneHighlighter(OLuceneQueryContext queryContext, OLuceneHits hits, OContextualRecordId recordId, Analyzer analyzer) {
    this.queryContext = queryContext;
    this.hits = hits;
    this.recordId = recordId;
    this.analyzer = analyzer;
  }

  @Override
  protected Map<String, String> load() {
    Highlighter highlighter = new Highlighter(
      new SimpleHTMLFormatter("<span class=\"hit\">", "</span>"),
      new QueryScorer(queryContext.query)
    );
    highlighter.setTextFragmenter(new NullFragmenter());
    Map<String, String> highlightedTexts = new HashMap<String, String>();
    ODocument rec = new ODocument(recordId);
    for (Object fieldName : hits.keySet()) {
      String text = rec.field(fieldName.toString());
      try {
        TokenStream tokenStream = analyzer.tokenStream("", text);
        highlightedTexts.put(
          fieldName.toString(),
          highlighter.getBestFragment(tokenStream, text)
        );
      } catch (IOException e) {
      } catch (InvalidTokenOffsetsException e) {
      }
    }
    return highlightedTexts;
  }
}