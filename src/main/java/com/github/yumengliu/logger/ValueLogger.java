package com.github.yumengliu.logger;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.yumengliu.logger.annotation.ValueToLog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValueLogger {

  private Map<String, Object> stringToObjectMap;
  private Map<String, Field> stringToFieldMap;
  private Object agentObject;
  private static final Logger LOGGER = Logger.getLogger(ValueLogger.class.getName());

  public ValueLogger(Object agentObject, Level logLevel) {
    checkNotNull(agentObject);
    checkNotNull(logLevel);
    this.agentObject = agentObject;
    this.stringToObjectMap = new HashMap<>();
    this.stringToFieldMap = new HashMap<>();
    setUpBookKeepingMaps();
    LOGGER.setLevel(logLevel);
  }

  public ValueLogger(Object agentObject) {
    this(agentObject, Level.FINEST);
  }

  public void setLevel(Level logLevel) {
    LOGGER.setLevel(logLevel);
  }

  public void log() {
    logImpl(LOGGER.getLevel());
  }

  public void log(Level level) {
    logImpl(level);
  }

  private void logImpl(Level level) {
    updateObjectMap();
    for (Map.Entry<String, Object> entry : stringToObjectMap.entrySet()) {
      LOGGER.log(level, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
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
