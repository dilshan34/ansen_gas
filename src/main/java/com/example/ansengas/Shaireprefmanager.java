package com.example.ansengas;

import android.content.Context;
import android.content.SharedPreferences;

public class Shaireprefmanager {
    private static Shaireprefmanager mInstance;
    private static Context mctx;

    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USER_NAME="username";
    private static final String KEY_USER_TYPE = "type";
    private static final String KEY_USER_FULLNAME = "name";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_MOBILE = "mobile";
    private static final String KEY_USER_ROOT = "rootId";

    private Shaireprefmanager(Context context)
    {
        mctx=context;
    }


    public static synchronized Shaireprefmanager getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new Shaireprefmanager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String username,String userid,String name,String mobile,String rootId)
    {
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_ID,userid);
        editor.putString(KEY_USER_FULLNAME,name);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_ROOT,rootId);

        editor.apply();return true;
    }


    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_NAME, null) !=null)
        {
            return true;
        }
        return false;
    }

    public boolean Loogout()
    {
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyUsername()
    {
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,null);
    }

    public String getKeyusertid()
    {
        SharedPreferences shaireprefmanager=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return shaireprefmanager.getString(KEY_ID,null);
    }

    public String getKeyName()
    {
        SharedPreferences shaireprefmanager=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return shaireprefmanager.getString(KEY_USER_FULLNAME,null);
    }

    public String getKeyRoot()
    {
        SharedPreferences shaireprefmanager=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return shaireprefmanager.getString(KEY_USER_ROOT,null);
    }

}
