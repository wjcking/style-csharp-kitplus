package com.eastedge.mobilesurvey.imagecache;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;

public final class BitmapManager
{
  private static BitmapManager instance = new BitmapManager();
  private static WeakHashMap<String, Bitmap> imageMap = null;
  private static LinkedList<String> cacheList = null;
  private static Bitmap bitmap = null;
  private int cacheSize = 20;

  static
  {
    imageMap = new WeakHashMap();
    cacheList = new LinkedList();
  }

  public static synchronized BitmapManager getInstance()
  {
    if (instance == null) {
      instance = new BitmapManager();
    }
    return instance;
  }

  public void putBitmap(String key, Bitmap bitmap)
  {
    if (imageMap.size() >= this.cacheSize) {
      destroyLast();
    }
    if ((key == null) || (bitmap == null)) {
      return;
    }
    if (!imageMap.containsKey(key)) {
      imageMap.put(key, bitmap);
      cacheList.addFirst(key);
    }
  }

  public void putBitmap(String path, int inSampleSize)
  {
    if (imageMap.containsKey(path)) {
      return;
    }
    if (imageMap.size() >= this.cacheSize) {
      destroyLast();
    }
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inSampleSize = inSampleSize;
    try {
      bitmap = BitmapFactory.decodeFile(path, options);
      if (bitmap != null) {
        imageMap.put(path, bitmap);
        cacheList.addFirst(path);
      }
    } catch (OutOfMemoryError e) {
      Log.e(getClass().getSimpleName(), e.toString());
      Log.e(getClass().getSimpleName(), "the path is--->" + path);

      destroyLast();
      putBitmap(path, inSampleSize <= 0 ? 1 : inSampleSize * 2);
    }
  }

  public void putBitmap(String path, int width, int height)
  {
    if (imageMap.containsKey(path)) {
      return;
    }
    if (imageMap.size() >= this.cacheSize) {
      destroyLast();
    }
    BitmapFactory.Options options = new BitmapFactory.Options();
    try {
      bitmap = BitmapFactory.decodeFile(path, options);
      if (bitmap != null) {
        bitmap = zoomImage(bitmap, width, height);
        if (bitmap != null) {
          imageMap.put(path, bitmap);
          cacheList.addFirst(path);
        }
      }
    } catch (OutOfMemoryError e) {
      Log.e(getClass().getSimpleName(), e.toString());
      Log.e(getClass().getSimpleName(), "the path is--->" + path);

      destroyLast();
      putBitmap(path, width * 4 / 5, height * 4 / 5);
    }
  }

  public Bitmap getBitmap(String key)
  {
    if (imageMap.containsKey(key)) {
      bitmap = (Bitmap)imageMap.get(key);
    }
    return (bitmap != null) && (!bitmap.isRecycled()) ? bitmap : null;
  }

  public boolean isAvailable(String key)
  {
    if (imageMap.containsKey(key)) {
      return (imageMap.get(key) != null) && (!((Bitmap)imageMap.get(key)).isRecycled());
    }
    return false;
  }

  public void remove(String key)
  {
    if (imageMap.containsKey(key)) {
      Bitmap bitMap = (Bitmap)imageMap.remove(key);
      if ((bitMap != null) && (!bitMap.isRecycled())) {
        bitMap = null;
      }
      cacheList.remove(key);
    }
  }

  public void removeAll()
  {
    Set set = imageMap.keySet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()) {
      String key = (String)iterator.next();
      remove(key);
    }
    imageMap.clear();
  }

  public int getCacheSize()
  {
    return this.cacheSize;
  }

  public void setCacheSize(int cacheSize)
  {
    if (cacheSize < 0) {
      throw new RuntimeException("the cache size is--->" + cacheSize);
    }
    do
      destroyLast();
    while (cacheSize < cacheList.size());

    this.cacheSize = cacheSize;
  }

  private void destroyLast() {
    synchronized (this) {
      new Thread() {
        public void run() {
          try {
            String key = (String)BitmapManager.cacheList.removeLast();
            if (key.length() > 0) {
              Bitmap bitMap = (Bitmap)BitmapManager.imageMap.remove(key);
              if ((bitMap != null) && (!bitMap.isRecycled())) {
                bitMap = null;
              }
            }
            System.gc();
          } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "destroyLast()--->" + e.toString());
          }
        }
      }
      .start();
    }
  }

  private Bitmap zoomImage(Bitmap bm, float mWidth, float mHeight) {
    if (bm != null) {
      int width = bm.getWidth();
      int height = bm.getHeight();
      float scaleWidth = mWidth / width;
      float scaleHeight = mHeight / height;
      Matrix matrix = new Matrix();
      matrix.postScale(scaleWidth, scaleHeight);
      try {
        bm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
      } catch (OutOfMemoryError e) {
        Log.e(getClass().getSimpleName(), "zoomImage()--->" + e.toString());
      }
    }

    return bm;
  }

  public int size()
  {
    return imageMap.size();
  }
}
