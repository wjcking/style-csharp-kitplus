package com.eastedge.mobilesurvey.imagecache;


import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class ImageCheck
{
  private static final String TAG = "ImageCheck";

  public static boolean isAvailable(String filePath)
  {
    return isImage(filePath);
  }

  private static InputStream getInputStream(String path) {
    InputStream in = null;
    try {
      in = new FileInputStream(path);
    } catch (FileNotFoundException e) {
      Log.d("ImageCheck", e.toString());
    }
    return in;
  }

  private static boolean isImage(String path) {
    boolean flag = false;
    InputStream in = getInputStream(path);
    if (in != null) {
      byte[] bytes = new byte[8];
      try {
        in.read(bytes);
        if ((isJPEG(bytes)) || (isGIF(bytes)) || (isPNG(bytes)))
          flag = true;
      }
      catch (IOException e) {
        Log.d("ImageCheck", "the path is ---> " + path);
        Log.d("ImageCheck", e.toString());
        try
        {
          if (in != null)
            in.close();
        }
        catch (IOException localIOException1)
        {
        }
      }
      finally
      {
        try
        {
          if (in != null)
            in.close();
        }
        catch (IOException localIOException2) {
        }
      }
    }
    return flag;
  }

  private static boolean isJPEG(byte[] b) {
    if (b.length < 2) {
      return false;
    }
    return (b[0] == -1) && (b[1] == -40);
  }

  private static boolean isGIF(byte[] b) {
    if (b.length < 6) {
      return false;
    }
    return (b[0] == 71) && (b[1] == 73) && (b[2] == 70) && (b[3] == 56) && ((b[4] == 55) || (b[4] == 57)) && (b[5] == 97);
  }

  private static boolean isPNG(byte[] b) {
    if (b.length < 8) {
      return false;
    }
    return (b[0] == -119) && (b[1] == 80) && (b[2] == 78) && (b[3] == 71) && (b[4] == 13) && (b[5] == 10) && (b[6] == 26) && (b[7] == 10);
  }
}
