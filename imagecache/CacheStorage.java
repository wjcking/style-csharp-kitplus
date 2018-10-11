package com.eastedge.mobilesurvey.imagecache;


import android.content.Context;
import android.os.Environment;
import java.io.File;

final class CacheStorage
{
  public static String getCacheDir(Context context)
  {
    return getExternalCacheDir(context) != null ? getExternalCacheDir(context) : getInternalCacheDir(context);
  }

  public static String getInternalCacheDir(Context context)
  {
    return context.getCacheDir().getAbsolutePath() + "/";
  }

  public static String getExternalCacheDir(Context context)
  {
    if (isExternalStorageAvailable()) {
      String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
//      String path = sdcard + "/Android/data/" + context.getPackageName() + "/cache/";
      String path = sdcard +"/haier/temp/";
      File cache = new File(path);
      cache.mkdirs();
      return path;
    }
    return null;
  }

  public static boolean isExternalStorageAvailable()
  {
    return (isHaveExternalStorage()) && (!isExternalStorageReadOnly());
  }

  private static boolean isHaveExternalStorage()
  {
    String extStorageState = Environment.getExternalStorageState();
    return "mounted".equals(extStorageState);
  }

  private static boolean isExternalStorageReadOnly()
  {
    String extStorageState = Environment.getExternalStorageState();
    return "mounted_ro".equals(extStorageState);
  }
}
