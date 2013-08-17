package com.bignerdranch.android.geoquiz;
public class Log {
  private static final String TAG = "<-- DEBUG -->";

  public static void d(String message) {
    android.util.Log.d(TAG, message);
  }
}
