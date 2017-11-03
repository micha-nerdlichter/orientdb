package com.orientechnologies.lucene.search;

import java.util.*;

public abstract class OLazyHashMap extends HashMap {
  private boolean initialized = false;
  
  @Override
  public int size() {
    init();
    return super.size();
  }
  
  @Override
  public Object get(Object key) {
    init();
    return super.get(key);
  }
  
  @Override
  public Collection<Map<String, Float>> values() {
    init();
    return super.values();
  }
  
  @Override
  public Set<Object> keySet() {
    init();
    return super.keySet();
  }
  
  private void init() {
    if(!initialized) {
      putAll(load());
      initialized = true;
    }
  }
  
  protected abstract Map load();
}