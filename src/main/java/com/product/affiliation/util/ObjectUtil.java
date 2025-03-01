package com.product.affiliation.util;

import kong.unirest.core.json.JSONObject;

public final class ObjectUtil {

  public static <T> Object parseValue(JSONObject source, String key, Class<T> valueType) {
    if(!source.has(key)) {
      return null;
    }

    if(Double.class.isAssignableFrom(valueType)) {
      return (Double) source.getDouble(key);
    } else if(Float.class.isAssignableFrom(valueType)) {
      return (Float) source.getFloat(key);
    } else if(valueType.isAssignableFrom(Integer.class)) {
      return (Integer) source.getInt(key);
    } else if(valueType.isAssignableFrom(Boolean.class)) {
      return (Boolean) source.getBoolean(key);
    }

    return (String) source.getString(key);
  }
}
