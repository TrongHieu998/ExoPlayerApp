package com.example.app_exoplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_exoplayer.Models.ExoPlayer_Model;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.FileOutputStream;

@RequiresApi(api = Build.VERSION_CODES.M)
public class VideoPlayer extends AppCompatActivity implements View.OnClickListener {
    PlayerView playerView;
    private ImageButton btn_back,btn_brightness,btn_screenshot,btn_rotation,btn_sound
            ,btn_flipHorizontal,btn_flipVertical,btn_color,btn_ratio,btn_repeat_ab,btn_increase
            ,btn_reduction,btn_repeat_a,btn_repeat_b,btn_share;
    private SeekBar seekBar_brightness,seekBar_sound,seekBar_CLbrightness,seekBar_CLcontrast,seekBar_CLRange;
    private TextView txt_title,txt_brightness,txt_sound,txt_speed;
    private AudioManager audioManager;
    private RelativeLayout relativeLayout;
    private TextureView textureView;
    private DefaultTimeBar defaultTimeBar;
    SimpleExoPlayer player;
    private float speed= 1.0f;
    private int rotion=1;
    PlaybackParams playbackParams=new PlaybackParams();
    ExoPlayer_Model exoPlayer_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        playerView= findViewById(R.id.Exo_Player);
        txt_title=findViewById(R.id.txt_title);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            txt_title.setText(bundle.getString("title"));
        }
        textureView = (TextureView) playerView.getVideoSurfaceView();
        setScreenOrientation(getIntent().getStringExtra("path_file"));
        mapping();
    }

    private void init(String filePath){
        player=ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        DataSource.Factory datasource=new DefaultDataSourceFactory(VideoPlayer.this, Util.getUserAgent(VideoPlayer.this,getString(R.string.app_name)));
        MediaSource videoSource=new ExtractorMediaSource.Factory(datasource).createMediaSource(Uri.parse(filePath));
        player.prepare(videoSource);
        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        player.setPlayWhenReady(true);
    }
    private void setScreenOrientation(String filePath){
        MediaPlayer mp=new MediaPlayer();
        try {
            mp.setDataSource(filePath);
            mp.prepare();
            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    init(filePath);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void mapping ()
    {
        relativeLayout=findViewById(R.id.layout_parent);

       //btn_back=findViewById(R.id.btn_back);
        btn_brightness=findViewById(R.id.exo_brightness);
        btn_screenshot=findViewById(R.id.exo_screenshot);
        btn_rotation=findViewById(R.id.exo_rotation);
        btn_sound=findViewById(R.id.exo_sounds);
        btn_flipHorizontal=findViewById(R.id.exo_flipHorizontal);
        btn_flipVertical=findViewById(R.id.exo_flipVertical);
        btn_color=findViewById(R.id.exo_color);
        btn_ratio=findViewById(R.id.exo_ratio);
        btn_repeat_ab=findViewById(R.id.exo_repeat_ab);
        btn_increase=findViewById(R.id.exo_increase);
        btn_reduction=findViewById(R.id.exo_redution);
        btn_repeat_a=findViewById(R.id.exo_repeat_a);
        btn_repeat_b=findViewById(R.id.exo_repeat_b);
        btn_share=findViewById(R.id.exo_test);

        defaultTimeBar=findViewById(R.id.exo_progress);
        txt_speed=findViewById(R.id.txt_exo_speed);


 //       btn_back.setOnClickListener(this);
        btn_brightness.setOnClickListener(this);
        btn_screenshot.setOnClickListener(this);
        btn_rotation.setOnClickListener(this);
        btn_sound.setOnClickListener(this);
        btn_flipHorizontal.setOnClickListener(this);
        btn_flipVertical.setOnClickListener(this);
        btn_color.setOnClickListener(this);
        btn_ratio.setOnClickListener(this);
        btn_repeat_ab.setOnClickListener(this);
        btn_increase.setOnClickListener(this);
        btn_reduction.setOnClickListener(this);
        btn_repeat_a.setOnClickListener(this);
        btn_repeat_b.setOnClickListener(this);
        btn_share.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.exo_brightness:
                Dialog dialog_brightness=new Dialog(this);
                dialog_brightness.setContentView(R.layout.brightness_custom);
                int brightness= Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,0);
                seekBar_brightness=dialog_brightness.findViewById(R.id.seekBar_brightness);;
                txt_brightness=dialog_brightness.findViewById(R.id.txt_brightness);
                txt_brightness.setText(brightness+"/100");
                seekBar_brightness.setProgress(brightness);
                seekBar_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Context context=getApplicationContext();
                        boolean canWrite= Settings.System.canWrite(context);
                        if(canWrite)
                        {
                            int n=progress*100/100;
                            txt_brightness.setText(n+"/100");
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,n);
                        }
                        else {
                            Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            context.startActivity(intent);
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                dialog_brightness.show();
                break;
            case R.id.exo_screenshot:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
                } else {
                }
                Bitmap bitmap=textureView.getBitmap();
                store(bitmap,"ScreenShot.png");

                break;
            case R.id.exo_rotation:
                Display display1= getWindowManager().getDefaultDisplay();
                int width1 = display1.getWidth();
                int height1 = display1.getHeight();
                if (width1 > height1) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
            case R.id.exo_sounds:
                Dialog dialog_sounds=new Dialog(this);
                dialog_sounds.setContentView(R.layout.sounds_custom);
                seekBar_sound=dialog_sounds.findViewById(R.id.seekBar_sounds);
                txt_sound=dialog_sounds.findViewById(R.id.txt_sound);
                audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                seekBar_sound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                seekBar_sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                        txt_sound.setText(""+progress+"%");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                dialog_sounds.show();
                break;
            case R.id.exo_flipHorizontal:
                if(textureView.getRotationY()==0 ){
                    textureView.setRotationY(180);

                }
                else {
                    textureView.setRotationY(0);
                }
                break;
            case R.id.exo_flipVertical:
                textureView = (TextureView) playerView.getVideoSurfaceView();
                if(textureView.getRotationX()==0){
                    textureView.setRotationX(180);
                }
                else {
                    textureView.setRotationX(0);
                }
                break;
            case R.id.exo_color:
                Dialog dialog_color=new Dialog(this);
                dialog_color.setContentView(R.layout.color_custom);
                seekBar_CLbrightness=dialog_color.findViewById(R.id.seekBar_CLbrightness);
                seekBar_CLbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Context context=getApplicationContext();
                        boolean canWrite= Settings.System.canWrite(context);
                        if(canWrite)
                        {
                            int n=progress*100/100;
//                            txt_brightness.setText(n+"/100");
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,n);
                        }
                        else {
                            Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            context.startActivity(intent);
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                seekBar_CLcontrast=dialog_color.findViewById(R.id.seekBar_CLcontrast);
                seekBar_CLcontrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textureView.setOpaque(false);
                       relativeLayout.setBackgroundColor(Color.blue(Color.YELLOW));
                        playerView.setAlpha(0.30f);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                seekBar_CLRange=dialog_color.findViewById(R.id.seekBar_CLRange);
                seekBar_CLRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @SuppressLint("Range")
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            textureView.setOpaque(false);
                        }
                        float[] hsvColor = {0, 1, 1};
                        hsvColor[0] = 360f * progress / 255;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            relativeLayout.setBackgroundColor(Color.HSVToColor(hsvColor));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            playerView.setAlpha(.8f);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                dialog_color.show();
                break;
            case R.id.exo_ratio:
                adjustAspectRatio(1,1 );
                Toast.makeText(this, "Vừa chiều dọc", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exo_redution:
                if(speed>0.2){
                    playbackParams.setSpeed(speed);
                    Double g=Double.parseDouble(String.valueOf(speed));
                    g=(double)Math.round(g*10)/10;
                    String s=Double.toString(g);
                    txt_speed.setText(s+"0x");
                    playbackParams.setPitch(1);
                    playbackParams.setAudioFallbackMode(
                            PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT);
                    player.setPlaybackParams(playbackParams);
                }
                speed= (float) (speed-0.1);
                break;
            case R.id.exo_increase:
                if(speed<2.1){
                    playbackParams.setSpeed(speed);
                    Double t=Double.parseDouble(String.valueOf(speed));
                    t=(double)Math.round(t*10)/10;
                    String k=Double.toString(t);
                    txt_speed.setText(k+"0x");
                    playbackParams.setPitch(1);
                    playbackParams.setAudioFallbackMode(
                            PlaybackParams.AUDIO_FALLBACK_MODE_DEFAULT);
                    player.setPlaybackParams(playbackParams);
                }
                speed= (float) (speed+0.1);
                break;
            case R.id.exo_repeat_ab:
                break;
            case R.id.exo_repeat_a:
                updateProgress();
                break;
            case R.id.exo_test:
                if(rotion==1){
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    Toast.makeText(this, "Vừa chiều dọc", Toast.LENGTH_SHORT).show();
                    rotion=2;
                }
                else if(rotion==2){
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    Toast.makeText(this, "Đầy màn hình", Toast.LENGTH_SHORT).show();
                    rotion=3;
                }
                else if(rotion==3){
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    Toast.makeText(this, "Vừa chiều ngang", Toast.LENGTH_SHORT).show();
                    rotion=4;
                }
                else if(rotion==4){
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    Toast.makeText(this, "16:9", Toast.LENGTH_SHORT).show();
                    rotion=1;
                }
//                else if(rotion==5){
//                    adjustAspectRatio(1,1);
//                    Toast.makeText(this, "3:4", Toast.LENGTH_SHORT).show();
//                    rotion=6;
//                }
                break;
        }
    }
    private void updateProgress(){
        Handler handler= new Handler();
        //get current progress
        long position = player == null ? 0 : player.getCurrentPosition();
        //updateProgress() will be called repeatedly, you can check
        //player state to end it
        handler.postDelayed(updateProgressAction,10000);

    }
    private final Runnable updateProgressAction = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        if(player!=null){
            player.release();
        }
        if(player!=null){
            if(player.isLoading()) {
                player.release();
            }
        }
        if(player!=null){
            if(player.isPlayingAd()) {
                player.release();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.release();
        }

        if(player!=null){
            if(player.isLoading()) {
                player.release();
            }
        }
        if(player!=null){
            if(player.isPlayingAd()) {
                player.release();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(player!=null){
            player.release();
        }

        if(player!=null){
            if(player.isLoading()) {
                player.release();
            }
        }
        if(player!=null){
            if(player.isPlayingAd()) {
                player.release();
            }
        }
    }
    public void store(Bitmap bm,String fileName){
        String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ExoPlayer";
        File file=new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        File f=new File(filePath,fileName);
        try {
            FileOutputStream fos= new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }
    private void adjustAspectRatio(int videoWidth, int videoHeight) {
        int viewWidth = textureView.getWidth();
        int viewHeight = textureView.getHeight();
        double aspectRatio = (double) videoHeight / videoWidth;
        int newWidth, newHeight;
        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }

        int xoff = (viewWidth - newWidth) / 2;
        int yoff = (viewHeight - newHeight) / 2;

        Matrix txform = new Matrix();
        textureView.getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        textureView.setTransform(txform);
    }
}
