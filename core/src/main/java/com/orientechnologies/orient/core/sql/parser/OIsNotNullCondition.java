/* Generated By:JJTree: Do not edit this line. OIsNotNullCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OIsNotNullCondition extends OBooleanExpression {

  protected OExpression expression;

  public OIsNotNullCondition(int id) {
    super(id);
  }

  public OIsNotNullCondition(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public boolean evaluate(OIdentifiable currentRecord, OCommandContext ctx) {
    return false;
  }

  @Override
  public String toString() {
    return expression.toString() + " IS NOT NULL";
  }

  @Override
  public void replaceParameters(Map<Object, Object> params) {
    expression.replaceParameters(params);
  }

  @Override
  public boolean supportsBasicCalculation() {
    return expression.supportsBasicCalculation();
  }

  @Override
  protected int getNumberOfExternalCalculations() {
    if (!expression.supportsBasicCalculation()) {
      return 1;
    }
    return 0;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    if (!expression.supportsBasicCalculation()) {
      return (List) Collections.singletonList(expression);
    }
    return Collections.EMPTY_LIST;
  }

}
/* JavaCC - OriginalChecksum=a292fa8a629abb7f6fe72a627fc91361 (do not edit this line) */
