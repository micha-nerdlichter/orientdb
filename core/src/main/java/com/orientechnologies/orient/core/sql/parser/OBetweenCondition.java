/* Generated By:JJTree: Do not edit this line. OBetweenCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.schema.OType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OBetweenCondition extends OBooleanExpression {

  protected OExpression first;
  protected OExpression second;
  protected OExpression third;

  public OBetweenCondition(int id) {
    super(id);
  }

  public OBetweenCondition(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override
  public boolean evaluate(OIdentifiable currentRecord, OCommandContext ctx) {
    Object firstValue = first.execute(currentRecord, ctx);
    if (firstValue == null) {
      return false;
    }

    Object secondValue = second.execute(currentRecord, ctx);
    if (secondValue == null) {
      return false;
    }

    secondValue = OType.convert(secondValue, firstValue.getClass());

    Object thirdValue = third.execute(currentRecord, ctx);
    if (thirdValue == null) {
      return false;
    }
    thirdValue = OType.convert(thirdValue, firstValue.getClass());

    final int leftResult = ((Comparable<Object>) firstValue).compareTo(secondValue);
    final int rightResult = ((Comparable<Object>) firstValue).compareTo(thirdValue);

    return leftResult >= 0 && rightResult <= 0;
  }

  @Override
  public void replaceParameters(Map<Object, Object> params) {
    first.replaceParameters(params);
    second.replaceParameters(params);
    third.replaceParameters(params);
  }

  public OExpression getFirst() {
    return first;
  }

  public void setFirst(OExpression first) {
    this.first = first;
  }

  public OExpression getSecond() {
    return second;
  }

  public void setSecond(OExpression second) {
    this.second = second;
  }

  public OExpression getThird() {
    return third;
  }

  public void setThird(OExpression third) {
    this.third = third;
  }

  @Override
  public String toString() {
    return first.toString() + " BETWEEN " + second.toString() + " AND " + third.toString();
  }

  @Override public boolean supportsBasicCalculation() {
    return true;
  }

  @Override protected int getNumberOfExternalCalculations() {
    return 0;
  }

  @Override protected List<Object> getExternalCalculationConditions() {
    return Collections.EMPTY_LIST;
  }

}
/* JavaCC - OriginalChecksum=f94f4779c4a6c6d09539446045ceca89 (do not edit this line) */
