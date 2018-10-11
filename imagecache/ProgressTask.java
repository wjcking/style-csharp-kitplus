package com.eastedge.mobilesurvey.imagecache;


import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

class ProgressTask extends Timer
{
  static final int ALL_DEGREE = 360;
  static final int LARGE_DURATION = 500;
  static final int SMALL_DURATION = 100;
  private ImageView view;
  private boolean finish;
  private LeTimerTask task;
  private UpdateHandler handler;

  public ProgressTask(ImageView view, int animRes)
  {
    this.view = view;
    this.finish = false;
    this.handler = new UpdateHandler(null);
    this.task = new LeTimerTask(null);
  }

  public void start() {
    schedule(this.task, 0L, 100L);
  }

  public void end() {
    this.finish = true;
  }
  private class LeTimerTask extends TimerTask {
    private LeTimerTask(Object object) {
    }
    public void run() {
      if (!ProgressTask.this.finish)
        ProgressTask.this.handler.sendEmptyMessage(0); 
    }
  }

  private class UpdateHandler extends Handler {
    private UpdateHandler(Object object) {
    }

    public void handleMessage(Message msg) {
      if (!ProgressTask.this.finish) {
        Matrix matrix = ProgressTask.this.view.getImageMatrix();
        int degrees = 72;
        matrix.postRotate(degrees, 30.0F, 30.0F);
        ProgressTask.this.view.setImageMatrix(matrix);
      }
    }
  }
}
