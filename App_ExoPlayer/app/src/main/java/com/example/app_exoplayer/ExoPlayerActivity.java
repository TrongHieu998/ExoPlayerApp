package com.example.app_exoplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_exoplayer.Adapter.ExoPlayer_Adapter;
import com.example.app_exoplayer.Interface.ClickListener;
import com.example.app_exoplayer.Models.ExoPlayer_Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExoPlayerActivity extends AppCompatActivity implements ClickListener {
    private static final int PERMISSION_CODE = 101;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        //getSupportActionBar().hide();
        recyclerView=findViewById(R.id.exo_recyclerView);
        if(Build.VERSION.SDK_INT>=23){
            if(checkPermission()){
                readAllFIles();
            }
            else{
                requestPermission();
            }
        }
        else{
            readAllFIles();
        }
    }
    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(ExoPlayerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(ExoPlayerActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(this, "Allow Permission", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(ExoPlayerActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readAllFIles();
                }
        }
    }
    private void readAllFIles() {
        HashSet<String> hashSet=new HashSet<>();
        String[] projection={MediaStore.Video.VideoColumns.DATA,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor=getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
        try{
            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    hashSet.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        List<String> file_path=new ArrayList<>(hashSet);
        List<ExoPlayer_Model> videoModelList=new ArrayList<>();
        for (String data:file_path){
            File file=new File(data);
            videoModelList.add(new ExoPlayer_Model(file.getName(),file.getAbsolutePath()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(ExoPlayerActivity.this));
        ExoPlayer_Adapter videoItemAdapter=new ExoPlayer_Adapter(videoModelList,ExoPlayerActivity.this,ExoPlayerActivity.this);
        recyclerView.setAdapter(videoItemAdapter);

        //lets create video player
    }

    @Override
    public void onClickItem(String filePath) {
        startActivity(new Intent(ExoPlayerActivity.this, VideoPlayer.class).putExtra("path_file",filePath));
    }
}
