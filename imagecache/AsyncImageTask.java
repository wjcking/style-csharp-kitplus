package com.eastedge.mobilesurvey.imagecache;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncImageTask implements Runnable {
	private final String TAG = "AsyncImageTask";
	public static final String LOAD_IMAGE_MODE = "load_image_mode";
	private String taskId;
	private ImageView imageView;
	private String destUrl;
	private int failImage = -1;
	private int defaultImage = -1;
	private int sampleSize = 1;
	private boolean forBackground = false;
	private boolean canceled = false;
	private AsyncHandler handler = null;
	private Animation animation = null;
	public static final int OK = 1;
	public static final int FAIL = 2;
	public static final int EXSIT = 3;
	private static final int PROGRESS = 4;
	private boolean forHome = false;

	private boolean showProgress = false;
	private int[] progressRes;

	public AsyncImageTask(ImageView imageView, String url) {
		this(null, imageView, url);
	}

	public AsyncImageTask(String taskId, ImageView imageView, String url) {
		if ((taskId == null) || (taskId.trim().length() == 0)) {
			String tid = CacheName.getCachePath(imageView.getContext(), url);
			this.taskId = tid;
		} else {
			this.taskId = taskId;
		}
		if (imageView != null) {
			this.imageView = imageView;
			this.imageView.setTag(this.taskId);
		} else {
			throw new RuntimeException("the imageview is null");
		}
		this.destUrl = url;
		this.handler = new AsyncHandler();
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isForHome() {
		return this.forHome;
	}

	public void setForHome(boolean forHome) {
		this.forHome = forHome;
	}

	public int getDefaultImage() {
		return this.defaultImage;
	}

	public void setDefaultImage(int defaultImage) {
		this.defaultImage = defaultImage;
	}

	public int getFailImage() {
		return this.failImage;
	}

	public void setFailImage(int failImage) {
		this.failImage = failImage;
	}

	public int getSampleSize() {
		return this.sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		if (sampleSize > 0)
			this.sampleSize = sampleSize;
	}

	public boolean isForBackground() {
		return this.forBackground;
	}

	public void setForBackground(boolean forBackground) {
		this.forBackground = forBackground;
	}

	public boolean isCanceled() {
		return this.canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public ImageView getImageView() {
		return this.imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getDestUrl() {
		return this.destUrl;
	}

	public void setDestUrl(String destUrl) {
		this.destUrl = destUrl;
	}

	public AsyncHandler getHandler() {
		return this.handler;
	}

	public void setHandler(AsyncHandler handler) {
		this.handler = handler;
	}

	public Animation getAnimation() {
		return this.animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public boolean isShowProgress() {
		return this.showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	public int[] getProgressRes() {
		return this.progressRes;
	}

	public void setProgressRes(int[] progressRes) {
		this.progressRes = progressRes;
	}

	public void run() {
		String destPath = CacheName.getCachePath(this.imageView.getContext(),
				this.destUrl);
		File file = new File(destPath);
		Message msg = new Message();
		if (!file.exists()) {
			boolean loadImage = Boolean.parseBoolean(System.getProperty(
					"load_image_mode", "true"));
			if (!loadImage) {
				return;
			}

			if (download(this.destUrl, destPath + ".tmp")) {
				File temp = new File(destPath + ".tmp");
				temp.renameTo(file);
				msg.what = 1;
				msg.obj = destPath;
			} else {
				msg.what = 2;
				deleteTemp(destPath + ".tmp");
			}
		} else {
			msg.what = 3;
			msg.obj = destPath;
		}
		if (!this.canceled)
			this.handler.sendMessage(msg);
		else
			deleteTemp(destPath + ".tmp");
	}

	private void deleteTemp(String path) {
		File file = new File(path);
		if (file.exists())
			file.delete();
	}

	private boolean download(String imageUrl, String destPath) {
		deleteTemp(destPath);
		boolean success = false;
		URL url = null;
		InputStream is = null;
		OutputStream out = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(imageUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(300000);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept-Language", "zh-cn");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			if (conn.getResponseCode() == 200) {
				long total = conn.getContentLength();
				long progress = 0L;
				is = conn.getInputStream();
				int read = 0;
				byte[] buffer = new byte[1024];
				out = new FileOutputStream(destPath);
				while ((read = is.read(buffer)) != -1) {
					out.write(buffer, 0, read);
					progress += read;

					Message msg = new Message();
					msg.what = 4;
					msg.obj = (total + "," + progress);
					this.handler.sendMessage(msg);
				}
				success = true;
			} else {
				Log.d("AsyncImageTask",
						"the respond code is ---> " + conn.getResponseCode());
				Log.d("AsyncImageTask", "the url is:" + imageUrl);
			}
		} catch (MalformedURLException e) {
			Log.d("AsyncImageTask",
					"MalformedURLException ---> " + e.toString());
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (conn != null)
					conn.disconnect();
			} catch (IOException e1) {
				Log.d("AsyncImageTask", e.toString());
			}
		} catch (IOException e) {
			Log.d("AsyncImageTask", "IOException ---> " + e.toString());
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (conn != null)
					conn.disconnect();
			} catch (IOException e2) {
				Log.d("AsyncImageTask", e.toString());
			}
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (conn != null)
					conn.disconnect();
			} catch (IOException e) {
				Log.d("AsyncImageTask", e.toString());
			}
		}
		return success;
	}

	final class AsyncHandler extends Handler {
		AsyncHandler() {
		}

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				doFail(msg);
				break;
			case 1:
			case 3:
				if (AsyncImageTask.this.forHome) {
					AsyncImageTask.this.imageView
							.setScaleType(ImageView.ScaleType.FIT_XY);
				}
				doSuccess(msg);
			case 4:
				doProgress(msg);
				break;
			}
		}

		private void doFail(Message msg) {
			if ((AsyncImageTask.this.forBackground)
					&& (AsyncImageTask.this.failImage != -1))
				AsyncImageTask.this.imageView
						.setBackgroundResource(AsyncImageTask.this.failImage);
			else if ((!AsyncImageTask.this.forBackground)
					&& (AsyncImageTask.this.failImage != -1))
				AsyncImageTask.this.imageView
						.setImageResource(AsyncImageTask.this.failImage);
		}

		private void doSuccess(Message msg) {
			String path = (String) msg.obj;
			BitmapManager.getInstance().putBitmap(path,
					AsyncImageTask.this.sampleSize);
			Bitmap bitmap = BitmapManager.getInstance().getBitmap(path);
			String tag = (String) AsyncImageTask.this.imageView.getTag();
			if (((bitmap != null) && (tag == null))
					|| (tag.equals(AsyncImageTask.this.taskId))) {
				if (AsyncImageTask.this.forBackground)
					AsyncImageTask.this.imageView
							.setBackgroundDrawable(new BitmapDrawable(bitmap));
				else if (!AsyncImageTask.this.forBackground) {
					AsyncImageTask.this.imageView.setImageBitmap(bitmap);
				}
				if ((msg.what == 1) && (AsyncImageTask.this.animation != null)) {
					AsyncImageTask.this.imageView
							.setAnimation(AsyncImageTask.this.animation);
					AsyncImageTask.this.animation.start();
				}
			}
		}

		private void doProgress(Message msg) {
			if ((AsyncImageTask.this.progressRes == null)
					|| (AsyncImageTask.this.progressRes.length < 1)
					|| (!AsyncImageTask.this.showProgress)) {
				return;
			}
			String data = (String) msg.obj;
			if ((data.split(",") == null) || (data.split(",").length < 2)) {
				return;
			}
			long total = Long.parseLong(data.split(",")[0]);
			long progress = Long.parseLong(data.split(",")[1]);
			int max = AsyncImageTask.this.progressRes.length;
			String tag = (String) AsyncImageTask.this.imageView.getTag();
			if ((tag == null) || (tag.equals(AsyncImageTask.this.taskId))) {
				long value = total / max;
				int position = (int) (progress / value);
				position = position >= max ? max - 1 : position;
				AsyncImageTask.this.imageView
						.setImageResource(AsyncImageTask.this.progressRes[position]);
			}
		}
	}
}
