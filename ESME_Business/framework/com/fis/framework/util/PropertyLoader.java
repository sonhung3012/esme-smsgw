package com.fis.framework.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader
{
  private static final boolean THROW_ON_LOAD_FAILURE = true;
  private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
  private static final String SUFFIX = ".properties";

  public static Properties loadProperties(String name, ClassLoader loader)
  {
    if (name == null) {
      throw new IllegalArgumentException("null input: name");
    }
    if (name.startsWith("/")) {
      name = name.substring(1);
    }
    if (name.endsWith(".properties")) {
      name = name.substring(0, name.length() - ".properties".length());
    }
    Properties result = null;

    InputStream in = null;
    try {
      if (loader == null) {
        loader = ClassLoader.getSystemClassLoader();
      }

      name = name.replace('.', '/');

      if (!name.endsWith(".properties")) {
        name = name.concat(".properties");
      }

      in = loader.getResourceAsStream(name);
      if (in != null) {
        result = new Properties();
        result.load(in);
      }
    }
    catch (Exception e) {
      result = null;

      if (in != null)
        try {
          in.close();
        }
        catch (Throwable localThrowable)
        {
        }
    }
    finally
    {
      if (in != null)
        try {
          in.close();
        }
        catch (Throwable localThrowable1) {
        }
    }
    if (result == null) {
      throw new IllegalArgumentException(
        "could not load [" + 
        name + 
        "]" + 
        " as " + 
        "a classloader resource");
    }

    return result;
  }

  public static Properties loadProperties(String name)
  {
    return loadProperties(
      name, 
      Thread.currentThread().getContextClassLoader());
  }
}