package com.product.affiliation.views.productbuyaffiliation;

import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Comparator;

public class ProductQuery {
  public static final Comparator<ProductQuery> byKey = Comparator.comparing(q -> q.getKey());

  private String key;

  private Object value;

  private Operator operation;

  public ProductQuery() {
    super();
  }

  public ProductQuery(Operator operation, String key, Object value) {
    this.operation = operation;
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }

  public Operator getOperation() {
    return operation;
  }

  public enum Operator {
    GT("$gt"), LT("$lt"), IS(""), IN("$in");
    final String value;

    Operator(String mongoOperator) {
      value = mongoOperator;
    }

    public String getValue() {
      return value;
    }
  }

  public static ProductQuery of(Operator op, String key, Object value) {
    return new ProductQuery(op, key, value);
  }

  @Override
  public String toString() {
    return "ProductQuery{" +
            "key='" + key + '\'' +
            ", value=" + value +
            ", operation=" + operation +
            '}';
  }
}
