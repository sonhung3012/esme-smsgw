package com.fis.framework.util;

import java.util.Properties;

public class Config
{
  private static Properties props = null;
  private static String filename = "config.properties";

  public static String getString(String name) {
    return getProps().getProperty(name);
  }

  private static Properties getProps() {
    if (props == null) {
      props = PropertyLoader.loadProperties(filename);
    }
    return props;
  }
}