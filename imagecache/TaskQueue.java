package com.eastedge.mobilesurvey.imagecache;


import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

public final class TaskQueue
{
  private static final String TAG = "TaskQueue";
  private static TaskQueue taskQueue = null;
  private static ThreadPoolExecutor threadPool = null;
  private static final int CORE_SIZE = 2;
  private static final int MAX_SIZE = 3;
  private static final int QUEUE_SIZE = 20;
  private static final long ALIVE_TIME = 10L;
  private static final TimeUnit T_Unit = TimeUnit.SECONDS;
  private static BlockingQueue<Runnable> queue = null;
  private static RejectedExecutionHandler rejectedHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

  private TaskQueue() {
    queue = new LinkedBlockingQueue(20);
    threadPool = new ThreadPoolExecutor(2, 3, 10L, T_Unit, queue, rejectedHandler);
  }

  public static TaskQueue getInstance()
  {
    if (taskQueue == null) {
      taskQueue = new TaskQueue();
    }
    return taskQueue;
  }

  public void addTask(AsyncImageTask task)
  {
    if (!hadLocal(task)) {
      boolean had = false;
      for (int i = 0; i < queue.size(); i++) {
        AsyncImageTask t = (AsyncImageTask)queue.element();
        if (task.getTaskId().equals(t.getTaskId())) {
          had = true;
          Log.d("TaskQueue", "the task id is:" + t.getTaskId());
          break;
        }
      }
      if (!had) {
        if (task.getDefaultImage() != -1) {
          if (!task.isForBackground())
            task.getImageView().setImageResource(task.getDefaultImage());
          else {
            task.getImageView().setBackgroundResource(task.getDefaultImage());
          }
        }
        threadPool.execute(task);
      }
      else if (task.getDefaultImage() != -1) {
        if (!task.isForBackground())
          task.getImageView().setImageResource(task.getDefaultImage());
        else
          task.getImageView().setBackgroundResource(task.getDefaultImage());
      }
    }
    else
    {
      String destPath = CacheName.getCachePath(task.getImageView().getContext(), task.getDestUrl());
      Message msg = new Message();
      msg.what = 3;
      msg.obj = destPath;
      task.getHandler().sendMessage(msg);
    }
  }

  private boolean hadLocal(AsyncImageTask task)
  {
    String destPath = CacheName.getCachePath(task.getImageView().getContext(), task.getDestUrl());
    File file = new File(destPath);
    if (file.exists()) {
      if (ImageCheck.isAvailable(destPath)) {
        file.setLastModified(System.currentTimeMillis());
        return true;
      }
      file.delete();
    }

    return false;
  }

  public void shutDown()
  {
    threadPool.shutdown();
    taskQueue = null;
    queue = null;
  }
}
