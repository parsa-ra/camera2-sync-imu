package com.example.android.camera2video;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class storageUtil {

    public static final String Folder_Name = "ParaCamera";
    public boolean Folder_Created = false;
    private static final String TAG = "ParaCamera_Storage";
    public String User_path;


    // method for creating program directory
    public void makeDir(){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + Folder_Name) ;
        if(!folder.exists()){
            Log.i(TAG, "Trying to Create: " + folder.getAbsolutePath());
            Folder_Created = folder.mkdir();
            if(Folder_Created){
                Log.i(TAG, "The program folder successfully created") ;
            }else{
                Log.e(TAG, "Can't Create program directory check permissions");
            }
        }else{
            Log.i(TAG, "The Program folder already exists");
        }
    }
}
