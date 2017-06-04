package org.nupter.contract;

import android.app.Application;

public class GlobalData extends Application {
	private String info;
	public String getUserInfo(){
		return info;
	}
	public void setUserInfo(String s){
		info=s;
	}
	public void onCreate() {  
		info = "null";  
        super.onCreate();  
    } 

}
