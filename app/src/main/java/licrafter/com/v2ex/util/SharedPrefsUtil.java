package licrafter.com.v2ex.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lijinxiang on 11/5/15.
 */
public class SharedPrefsUtil {

    public static SharedPrefsUtil mInstance;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;
    private Context mContext;


    public static SharedPrefsUtil getInstance(Context context,String fileName){
        if (mInstance==null)
            mInstance = new SharedPrefsUtil(context,fileName);
        return mInstance;
    }
    /**
     * 构造函数
     *
     * @param context
     * @param fileName
     */
    public SharedPrefsUtil(Context context, String fileName) {
        mContext = context;
        mSharedPreference = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    /**
     * 存储int值
     *
     * @param key
     * @param value
     */
    public void setIntSP(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 取int值
     * @param key
     * @param defaultValue
     * @return
     */
    public int getIntSP(String key, int defaultValue) {
        return mSharedPreference.getInt(key, defaultValue);
    }

    /**
     * 存string值
     *
     * @param key
     * @param value
     */
    public void setStringSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 取string值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getStringSP(String key, String defaultValue) {
        return mSharedPreference.getString(key, defaultValue);
    }

    /**
     * 存boolean值
     *
     * @param key
     * @param value
     */
    public void setBooleanSP(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * 取boolean值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBooleanSP(String key, boolean defaultValue) {
        return mSharedPreference.getBoolean(key, defaultValue);
    }

    /**
     * 存float值
     *
     * @param key
     * @param value
     */
    public void setFloatSP(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 取float值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public float getFloatSP(String key, float defaultValue) {
        return mSharedPreference.getFloat(key, defaultValue);
    }

    /**
     * 清空所有数据
     * @return
     */
    public boolean clearAll() {
        mEditor.clear();
        return mEditor.commit();
    }
}
