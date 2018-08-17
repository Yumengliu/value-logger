package com.ylym.valuelogger;

import com.ylym.valuelogger.annotation.ValueToLog;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;

public class Logger {

  private Map<String, Object> stringToObjectMap;
  private Map<String, Field> stringToFieldMap;
  private Object agentObject;

  public Logger(Object agentObject) {
    checkNotNull(agentObject);
    this.agentObject = agentObject;
    this.stringToObjectMap = new HashMap<>();
    this.stringToFieldMap = new HashMap<>();
    setUpBookKeepingMaps();
  }

  public void log() {
    updateObjectMap();
    for (Map.Entry<String, Object> entry : stringToObjectMap.entrySet()) {
      System.out.println(String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
    }
  }

  private void updateObjectMap() {
    for (Map.Entry<String, Field> entry : this.stringToFieldMap.entrySet()) {
      String fieldAnnotatedName = entry.getKey();
      Field fieldObject = entry.getValue();
      try {
        Object newObject = fieldObject.get(this.agentObject);
        this.stringToObjectMap.put(fieldAnnotatedName, newObject);
      } catch (IllegalAccessException iae) {
        // Do nothing for now
      }
    }
  }

  private void setUpBookKeepingMaps() {
    Class<?> objectClass = this.agentObject.getClass();
    for (Field field : objectClass.getDeclaredFields()) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(ValueToLog.class)) {
        try {
          Object fieldObject = field.get(this.agentObject);
          String fieldAnnotatedName = field.getAnnotation(ValueToLog.class).name();
          this.stringToObjectMap.put(fieldAnnotatedName, fieldObject);
          this.stringToFieldMap.put(fieldAnnotatedName, field);
        } catch (IllegalAccessException iae) {
          // Do nothing for now.
        }
      }
    }
  }
}
