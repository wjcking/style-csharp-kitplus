package com.eastedge.mobilesurvey.imagecache;


import android.content.Context;
import android.util.Log;
import java.security.MessageDigest;

public final class CacheName
{
  private static final String TAG = "CacheName";

  public static String getName(Context context, String url)
  {
    return md5(url);
  }

  public static String getCachePath(Context context, String url)
  {
    return CacheStorage.getCacheDir(context) + getName(context, url);
  }

  public static String getInternalPath(Context context, String url)
  {
    return CacheStorage.getInternalCacheDir(context) + getName(context, url);
  }

  public static String getExternalPath(Context context, String url)
  {
    return CacheStorage.getExternalCacheDir(context) + getName(context, url);
  }

  private static String md5(String source)
  {
    char[] hexDigits = { 
      '0', 
      '1', 
      '2', 
      '3', 
      '4', 
      '5', 
      '6', 
      '7', 
      '8', 
      '9', 
      'A', 
      'B', 
      'C', 
      'D', 
      'E', 
      'F' };
    try
    {
      byte[] btInput = source.getBytes("utf8");
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        str[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      return new String(str).substring(8, 24);
    } catch (Exception e) {
      Log.d("CacheName", e.toString());
    }return null;
  }
}