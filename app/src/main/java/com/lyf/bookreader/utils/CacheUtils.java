package com.lyf.bookreader.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
	private static SharedPreferences mSharedPreferences;

	/**
	 * @param context
	 * @param key 要取的数据的键
	 * @param defValue 缺省值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(key, defValue);
	}
	
	/**
	 * 存储一个boolean类型数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 存储一个String类型的数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取一个String类型的数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getString(key, defValue);
	}

	
	
	/**
	 * 存储一个long类型的数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putLong(Context context, String key, long value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putLong(key, value).commit();
	}
	
	/**
	 * 存储一个int类型的数据
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putInt(Context context, String key, int value) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putInt(key, value).commit();
	}
	
	
	/**
	 * 获取一个long类型的数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(Context context, String key, long defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getLong(key, defValue);
	}
	
	/**
	 * 获取一个int类型的数据
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Context context, String key, int defValue) {
		if(mSharedPreferences == null) {
			mSharedPreferences = context.getSharedPreferences(Constants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getInt(key, defValue);
	}
}
