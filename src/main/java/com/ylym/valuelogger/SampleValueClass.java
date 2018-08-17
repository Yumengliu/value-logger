package com.ylym.valuelogger;

import com.ylym.valuelogger.annotation.ValueToLog;

public class SampleValueClass {

  @ValueToLog(name = "stringValue")
  public String stringValue;

  @ValueToLog(name = "differentIntValue")
  private int intValue;

  public SampleValueClass(String stringValue, int intValue) {
    this.stringValue = stringValue;
    this.intValue = intValue;
  }
}
