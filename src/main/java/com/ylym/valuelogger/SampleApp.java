package com.ylym.valuelogger;

public class SampleApp {
  public static void main( String[] args ) {
    SampleValueClass valueObject = new SampleValueClass("string", 3);
    Logger valueLogger = new Logger(valueObject);
    valueLogger.log();
    valueObject.stringValue = "newString";
    valueLogger.log();
  }
}
