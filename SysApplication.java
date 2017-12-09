package com.example.dongke.july.utils;

import java.util.LinkedList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class SysApplication extends Application {
	private List<Activity> mList = new LinkedList<Activity>();
	private static SysApplication instance;

	public SysApplication() {
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		try{
		SDKInitializer.initialize(getApplicationContext());
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),"地图初始化错误",Toast.LENGTH_SHORT).show();
		}
	}

	public synchronized static SysApplication getInstance() {
		if (null == instance) {
			instance = new SysApplication();
		}
		return instance;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
			//add-by-yws------ 
		//	android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	public void exitTHisotryActivity(){
		for (Activity activity : mList) {
		if(activity.getClass().getName().equals("com.example.dongke.july.dongke.ImageFile")){
			activity.finish();
		}
		if(activity.getClass().getName().equals("com.example.dongke.july.dongke.AlbumActivity")){
			activity.finish();
		}
		
		if(activity.getClass().getName().equals("com.example.dongke.july.dongke.ShowAllPhoto")){
			activity.finish();
		}
		
		}
	}
	
	

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
