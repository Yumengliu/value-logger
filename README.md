# Value-logger: a Potable Logger for Java Value Class
Version: 0.1

Build Status: [![CircleCI](https://circleci.com/gh/Yumengliu/value-logger/tree/master.svg?style=svg)](https://circleci.com/gh/Yumengliu/value-logger/tree/master)

## Quick Start

It is super easy to integrate value-logger into your program. Firstly annotate the fields in your desired value class:

```java
public class SampleValueClass {

  @ValueToLog(name = "stringValue")
  public String stringValue;

  @ValueToLog(name = "differentIntValue")
  private int intValue;
}
```

Then in your program, initialize the logger then log at your desired point:

```java
public class SampleApp {
  public static void main( String[] args ) {
    SampleValueClass valueObject = new SampleValueClass("string", 3);
    Logger valueLogger = new Logger(valueObject);
    valueLogger.log();
  }
}
``` 