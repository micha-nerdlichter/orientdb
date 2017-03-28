package com.orientechnologies.orient.test.database.auto;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Test;

public class ODBLoadUnload {

  @Test
  public void testLoadUnload() {
    ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:loadtest");
    db.create();

    OSchema schema = db.getMetadata().getSchema();
    OClass reloadClass = schema.createClass("reloadClass");

    reloadClass.createProperty("val1", OType.INTEGER);
    reloadClass.createProperty("val2", OType.INTEGER);

    reloadClass.createIndex("val1Index", OClass.INDEX_TYPE.NOTUNIQUE,"val1");
    reloadClass.createIndex("val2Index", OClass.INDEX_TYPE.NOTUNIQUE, "val2");

    Orient.instance().shutdown();

    for (int i = 0; i < 1000; i++) {
      System.out.printf("Iteration %d started \n", i);

      Orient.instance().startup();

      db = new ODatabaseDocumentTx("plocal:loadtest");
      db.open("admin", "admin");

      for (int n = 0; n < 100000; n++) {
        ODocument document = new ODocument("reloadClass");

        document.field("val1", i);
        document.field("val2", i * 10);
        document.field("val3", 3 * i);

        document.save();
      }

      Orient.instance().shutdown();
    }
  }
}
