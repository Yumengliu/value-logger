package com.ylym.valuelogger;

import com.ylym.valuelogger.annotation.ValueToLog;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;

public class Logger {

  private Map<String, Object> stringToObjectMap;

  public Logger(Object agentObject) {
    checkNotNull(agentObject);
    stringToObjectMap = new HashMap<>();
    Class<?> objectClass = agentObject.getClass();
    for (Field field : objectClass.getDeclaredFields()) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(ValueToLog.class)) {
        try {
          Object fieldObject = field.get(agentObject);
          String fieldAnnotatedName = field.getAnnotation(ValueToLog.class).name();
          stringToObjectMap.put(fieldAnnotatedName, fieldObject);
        } catch (IllegalAccessException iae) {
          // Do nothing for now.
        }
      }
    }
  }

  public void log() {
    for (Map.Entry<String, Object> entry : stringToObjectMap.entrySet()) {
      System.out.println(entry.getValue().toString());
    }
  }
}
