package com.xgr.wonderful.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class BitmapUtil {
	private static Context mContext;

	public static final int UNCONSTRAINED = -1;
	
	private static Bitmap b;
	

	/*
	 * 获得设置信息
	 */
	public static BitmapFactory.Options getOptions(String path) {
		BitmapFactory.Options options = new Options();
		options.inJustDecodeBounds = true;// 只描边，不读取数据
		BitmapFactory.decodeFile(path, options);
		return options;
	}

	/**
	 * 获得图像
	 * 
	 * @param path
	 * @param options
	 * @return
	 * @throws FileNotFoundException
	 * 
	 * WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		int windowWidth = wm.getDefaultDisplay().getHeight();
		int windowHeight = wm.getDefaultDisplay().getHeight();
	 * 
	 */
	public static Bitmap getBitmapByPath(Context context, String path, Options options) throws FileNotFoundException {
		
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getHeight();
		int screenHeight = wm.getDefaultDisplay().getHeight();
		
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream in = null;
		in = new FileInputStream(file);
		if (options != null) {
			Rect r = getScreenRegion(screenWidth, screenHeight);
			int w = r.width();
			int h = r.height();
			int maxSize = w > h ? w : h;
			int inSimpleSize = computeSampleSize(options, maxSize, w * h);
			options.inSampleSize = inSimpleSize; // 设置缩放比例
			options.inJustDecodeBounds = false;
		}
		
		int degree = getPicDegree(path);
		  if(degree == 90 || degree == 180 || degree == 270){
              //Roate preview icon according to exif orientation
              Matrix matrix = new Matrix();
              matrix.postRotate(degree);
              Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
              b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            
		  }else{
			  b = BitmapFactory.decodeStream(in, null, options);
		  }
		try {
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	

	private static Rect getScreenRegion(int width, int height) {
		return new Rect(0, 0, width, height);
	}

	/**
	 * 获取需要进行缩放的比例，即options.inSampleSize
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
				.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
				.min(Math.floor(w / minSideLength),
						Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED)
				&& (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
	
	public static String saveToSdCard(Context mContext, Bitmap bitmap){
		Date date1 = new Date(System.currentTimeMillis());
		String dateTime = date1.getTime() + "";
		String files =CacheUtils.getCacheDirectory(mContext, true, "lost") + dateTime+"_14";
		File file = new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);     
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 55, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(!bitmap.isRecycled()){
        	bitmap.recycle();
        }
  
        return file.getAbsolutePath();
	}
	
	public static Bitmap compressImageFromFile(Context context, String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;//只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
//		WindowManager windowManager = ((Activity) context).getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
//		int hh = display.getHeight();
//		int ww = display.getWidth();
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置采样率
		
		newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
		
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//		return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
									//其实是无效的,大家尽管尝试
		int degree = getPicDegree(srcPath) ;       
		if (degree != 0) {   
             Matrix m = new Matrix();  
             m.postRotate(degree);  
             bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),  
            		 bitmap.getHeight(), m, true);  
         }  
          
		return bitmap;
	}
	
	public static Bitmap compressImageByQuality(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while(baos.toByteArray().length/1024>50 && options >=10){
			Log.w("the size is...", ""+baos.toByteArray().length/1024);
			baos.reset();
			options -= 10;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Bitmap b = BitmapFactory.decodeStream(bais);
		return b;
	}
	
	
	
	public static int getPicDegree(String path){
		int degree = 0;
		ExifInterface exif =null;
		try {
			exif = new ExifInterface(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(exif != null){
			//读取图片中相机方向
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;

			default:
				degree = 0;
				break;
			}	
		}
		return degree;
	}

}


