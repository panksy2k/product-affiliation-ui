package com.product.affiliation.data;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonPrimitive;
import com.nimbusds.jose.shaded.gson.JsonSerializationContext;
import com.nimbusds.jose.shaded.gson.JsonSerializer;
import com.product.affiliation.query.EqualsOperator;
import com.product.affiliation.query.GtOperator;
import com.product.affiliation.query.InOperator;
import com.product.affiliation.query.LtOperator;
import com.product.affiliation.query.Operator;
import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ProductFilterSerializer implements JsonSerializer<FilterCriteria> {
  private static final Logger LOG = Logger.getLogger(ProductFilterSerializer.class.getSimpleName());

  @Override
  public JsonElement serialize(FilterCriteria unserializedData, Type type,
                               JsonSerializationContext jsonSerializationContext) {

    LOG.info("Inside Product serializer");

    final JsonObject dataInJsonObject = new JsonObject();

    for(Map.Entry<String, Operator<?>> dataRow : unserializedData.getFiltersCriteria().entrySet()) {
      Operator<?> filterNameValuePair = dataRow.getValue();

      if(filterNameValuePair instanceof EqualsOperator<?>) {
        EqualsOperator<?> temp = (EqualsOperator) filterNameValuePair;

        if(!Objects.isNull(temp.values()) && !temp.values().isEmpty()) {
          dataInJsonObject.addProperty(dataRow.getKey(), temp.values().stream().map(Object::toString).findFirst().get());
        }
      } else if(filterNameValuePair instanceof GtOperator) {
        GtOperator<Double> temp = (GtOperator<Double>) filterNameValuePair;

        if(!Objects.isNull(temp.values()) && !temp.values().isEmpty()) {
          dataInJsonObject.addProperty(dataRow.getKey(), temp.values().stream().findFirst().get());
        }
      } else if(filterNameValuePair instanceof LtOperator) {
        LtOperator<Double> temp = (LtOperator<Double>) filterNameValuePair;

        if(!Objects.isNull(temp.values()) && !temp.values().isEmpty()) {
          dataInJsonObject.addProperty(dataRow.getKey(), temp.values().stream().findFirst().get());
        }
      } else if(filterNameValuePair instanceof InOperator) {
        InOperator<String> temp = (InOperator<String>) filterNameValuePair;

        if(!Objects.isNull(temp.values()) && !temp.values().isEmpty()) {
          dataInJsonObject.add(dataRow.getKey(), jsonSerializationContext.serialize(temp, InOperator.class));

          JsonArray inOperatorArray = new JsonArray(temp.values().size());
          temp.values().stream().forEach(v -> inOperatorArray.add(new JsonPrimitive(v)));

          dataInJsonObject.add(dataRow.getKey(), inOperatorArray);
        }
      }
    }

    return dataInJsonObject;
  }
}
