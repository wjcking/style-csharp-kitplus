package com.eastedge.mobilesurvey.imagecache;


import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

public class ImageExpire extends Thread
{
  private static final String TAG = "ImageExpire";
  private static final int IN_CACHE_SIZE = 10;
  private static final int EX_CACHE_SIZE = 100;
  private Context context;

  public ImageExpire(Context context)
  {
    this.context = context;
  }

  public void run()
  {
    cleanIntertal();
    cleanExtertal();
  }

  private boolean cleanIntertal()
  {
    return clean(CacheStorage.getInternalCacheDir(this.context), 10);
  }

  private boolean cleanExtertal()
  {
    return clean(CacheStorage.getExternalCacheDir(this.context), 100);
  }

  private boolean clean(String dirPath, int capacity) {
    boolean flag = false;
    try {
      File dir = new File(dirPath);
      File[] files = dir.listFiles(new ImageFilter(null));
      int size = files.length - capacity;
      if (size > 0) {
        Arrays.sort(files, new FileComparator(null));
        for (int i = 0; i < size; i++) {
          if (files[i].exists()) {
            files[i].delete();
          }
        }
      }
      flag = true;
    } catch (Exception e) {
      Log.e("ImageExpire", e.toString());
    }
    return flag;
  }
  private class FileComparator implements Comparator<File> {
    private FileComparator(Object object) {
    }

    public int compare(File file1, File file2) {
      if (file1.lastModified() - file2.lastModified() < 0L)
        return -1;
      if (file1.lastModified() - file2.lastModified() > 0L) {
        return 1;
      }
      return 0;
    }
  }

  private class ImageFilter implements FileFilter {
    private ImageFilter(Object object) {
    }

    public boolean accept(File file) {
      return ImageCheck.isAvailable(file.getAbsolutePath());
    }
  }
}