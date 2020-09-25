package com.github.yumengliu.logger;

import java.util.logging.Level;

public class SampleApp {
  public static void main( String[] args ) {
    SampleValueClass valueObject = new SampleValueClass("string", 3);
    ValueLogger valueLogger = new ValueLogger(valueObject);
    valueLogger.log();
    valueObject.stringValue = "newString";
    valueLogger.log();
    valueLogger.log(Level.SEVERE);
  }
}
