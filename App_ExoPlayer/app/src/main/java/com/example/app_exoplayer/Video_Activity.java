//package com.example.app_exoplayer;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.SurfaceTexture;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.provider.Settings;
//import android.view.Display;
//import android.view.Surface;
//import android.view.TextureView;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class Video_Activity extends AppCompatActivity implements TextureView.SurfaceTextureListener,MediaPlayer.OnPreparedListener, View.OnClickListener,
//SeekBar.OnSeekBarChangeListener{
//    //private SurfaceView surfaceView;
//    private TextureView textureView;
//    private ImageButton btn_playVideo,btn_back,btn_rev,btn_fwd,btn_brightness,btn_screenshot,btn_rotation,btn_sound
//            ,btn_flipHorizontal,btn_flipVertical,btn_color,btn_ratio,btn_repeat,getBtn_repeat_ab;
//    private TextView videoName,txt_endTime,txt_startTime;
//    private SeekBar seekBar,seekBar_brightness,seekBar_sound,seekBar_CLbrightness,seekBar_CLcontrast,seekBar_CLRange;
//   // private ColorSeekBar seekBar_CLRange;
//    private MediaPlayer mediaPlayer;
//    private TextView txt_brightness,txt_sound;
//    private Context context;
//    private ImageView testColor;
//    private FrameLayout parent;
//    private AspectRatioFrameLayout aspectRatioFrameLayout;
//    private AudioManager audioManager;
//    Handler videoHandler;
//    int numberOfReplay = 10;
//    int currentReplay = 0;
//    Runnable videoRunnable;
//    //SurfaceHolder surfaceHolder;
//    private String videoPath= Environment.getExternalStorageDirectory().getPath()+"/Download/PhoKhongEm.mp4";
//    //private String videoPath= "android.resource://com.example.app_exoplayer/"+R.raw.phokhongem;
//    String videoTitle="PhoKhongEm.mp4";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_video_);
//        initialize();
//        setHandler();
//        initializeSeekBar();
//    }
//
//    private void initializeSeekBar() {
//        seekBar.setProgress(0);
//        seekBar.setOnSeekBarChangeListener(this);
//    }
//
//    private void initialize() {
//       // surfaceView=findViewById(R.id.surface_view);
//        textureView=findViewById(R.id.texture_view);
//        btn_playVideo=findViewById(R.id.btn_play);
//        btn_back=findViewById(R.id.btn_back);
//        btn_rev=findViewById(R.id.btn_rev);
//        btn_fwd=findViewById(R.id.btn_fwd);
//        btn_brightness=findViewById(R.id.btn_brightness);
//        btn_screenshot=findViewById(R.id.btn_screenshot);
//        btn_rotation=findViewById(R.id.btn_rotation);
//        btn_sound=findViewById(R.id.btn_sounds);
//        btn_flipHorizontal=findViewById(R.id.btn_flipHorizontal);
//        btn_flipVertical=findViewById(R.id.btn_flipVertical);
//        btn_color=findViewById(R.id.btn_color);
//        btn_ratio=findViewById(R.id.btn_ratio);
//        btn_repeat=findViewById(R.id.btn_repeat);
//        getBtn_repeat_ab=findViewById(R.id.btn_repeat_ab);
//
//        parent=findViewById(R.id.parent);
//        videoName=findViewById(R.id.txt_title);
//        txt_startTime=findViewById(R.id.txt_startTime);
//        txt_endTime=findViewById(R.id.txt_endTime);
//        seekBar=findViewById(R.id.seekbar);
//        aspectRatioFrameLayout=findViewById(R.id.james);
//        videoName.setText(videoTitle);
//        testColor=findViewById(R.id.testColor);
//
//        textureView.setSurfaceTextureListener(this);
//        textureView.setKeepScreenOn(true);
//        mediaPlayer= new MediaPlayer();
//
//
////
////        surfaceHolder=surfaceView.getHolder();
////        surfaceHolder.addCallback(this);
////        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
////        surfaceView.setKeepScreenOn(true);
//
//
//
//        btn_playVideo.setOnClickListener(this);
//        btn_back.setOnClickListener(this);
//        btn_rev.setOnClickListener(this);
//        btn_fwd.setOnClickListener(this);
//        btn_brightness.setOnClickListener(this);
//        btn_screenshot.setOnClickListener(this);
//        btn_rotation.setOnClickListener(this);
//        btn_sound.setOnClickListener(this);
//        btn_flipHorizontal.setOnClickListener(this);
//        btn_flipVertical.setOnClickListener(this);
//        btn_color.setOnClickListener(this);
//        btn_ratio.setOnClickListener(this);
//        btn_repeat.setOnClickListener(this);
//        getBtn_repeat_ab.setOnClickListener(this);
//    }
//    private void setHandler(){
//        videoHandler= new Handler();
//        videoRunnable= new Runnable() {
//            @Override
//            public void run() {
//                if(mediaPlayer.getDuration()>0)
//                {
//                    int currentVideoDuration=mediaPlayer.getCurrentPosition();
//                    seekBar.setProgress(currentVideoDuration);
//                    txt_startTime.setText(""+convertIntoTime(currentVideoDuration));
//                    txt_endTime.setText(""+convertIntoTime(mediaPlayer.getDuration()-currentVideoDuration));
//                }
//                videoHandler.postDelayed(this,0);
//            }
//        };
//        videoHandler.postDelayed(videoRunnable,0);
//    }
//
//    private String convertIntoTime(int ms) {
//        String time;
//        int x, seconds, minutes, hours;
//        x = (int) (ms / 1000);
//        seconds = x % 60;
//        x /= 60;
//        minutes = x % 60;
//        x /= 60;
//        hours = x % 24;
//        if (hours != 0)
//            time = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
//        else time = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
//        return time;
//    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        Surface sf=new Surface(surface);
//        try {
//            mediaPlayer.setDataSource(videoPath);
//            mediaPlayer.setSurface(sf);
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(this);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//    }
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        return false;
//    }
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//    }
////    @Override
////    public void surfaceCreated(SurfaceHolder holder) {
////        mediaPlayer.setDisplay(holder);
////        try {
////            mediaPlayer.setDataSource(videoPath);
////            mediaPlayer.prepare();
////            mediaPlayer.setOnPreparedListener(this);
////            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////    @Override
////    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
////
////    }
////    @Override
////    public void surfaceDestroyed(SurfaceHolder holder) {
////    }
//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        mediaPlayer.start();
//        seekBar.setMax(mediaPlayer.getDuration());
//    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_back:
//                onBackPressed();
//                break;
//            case R.id.btn_play:
//                if(mediaPlayer.isPlaying()){
//                    mediaPlayer.pause();
//                    btn_playVideo.setImageResource(R.drawable.main_ic_play);
//                }
//                else {
//                    mediaPlayer.start();
//                    btn_playVideo.setImageResource(R.drawable.main_ic_pause);
//                }
//                break;
//            case R.id.btn_rev:
//                if(mediaPlayer==null){
//                    return;
//                }
//                int posRev=mediaPlayer.getCurrentPosition();
//                posRev -=10000;
//                mediaPlayer.seekTo(posRev);
//                break;
//            case R.id.btn_fwd:
//                if (mediaPlayer == null) {
//                    return;
//                }
//                int posFwd = mediaPlayer.getCurrentPosition();
//                posFwd += 10000;
//                mediaPlayer.seekTo(posFwd);
//                break;
//            case R.id.btn_brightness:
//                Dialog dialog_brightness=new Dialog(this);
//                dialog_brightness.setContentView(R.layout.brightness_custom);
//                int brightness= Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,0);
//                seekBar_brightness=dialog_brightness.findViewById(R.id.seekBar_brightness);;
//                txt_brightness=dialog_brightness.findViewById(R.id.txt_brightness);
//                txt_brightness.setText(brightness+"/100");
//                seekBar_brightness.setProgress(brightness);
//                seekBar_brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        Context context=getApplicationContext();
//                        boolean canWrite= Settings.System.canWrite(context);
//                        if(canWrite)
//                        {
//                            int n=progress*100/100;
//                            txt_brightness.setText(n+"/100");
//                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
//                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,n);
//                        }
//                        else {
//                            Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                            context.startActivity(intent);
//                        }
//                    }
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                    }
//                });
//                dialog_brightness.show();
//                break;
//            case R.id.btn_screenshot:
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
//                } else {
//                    ///
//                }
//                View rootView=getWindow().getDecorView().findViewById(android.R.id.content);
//               // View rootView=getWindow().getDecorView().getRootView();
//                Bitmap bitmap=getScreenShot(rootView);
//
//                //Bitmap bitmap=textureView.getBitmap();
//               // bitmap = Bitmap.createBitmap( bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), textureView.getTransform( null ), true );
//                store(bitmap,"ScreenShot.png");
//
//                break;
//            case R.id.btn_sounds:
//                Dialog dialog_sounds=new Dialog(this);
//                dialog_sounds.setContentView(R.layout.sounds_custom);
//                seekBar_sound=dialog_sounds.findViewById(R.id.seekBar_sounds);
//                txt_sound=dialog_sounds.findViewById(R.id.txt_sound);
//                audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                seekBar_sound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//                seekBar_sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
//                        txt_sound.setText(""+progress+"%");
//                    }
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                    }
//                });
//                dialog_sounds.show();
//                break;
//            case R.id.btn_rotation:
//                Display display1= getWindowManager().getDefaultDisplay();
//                int width1 = display1.getWidth();
//                int height1 = display1.getHeight();
//                if (width1 > height1) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                }
//                else {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                }
//                break;
//            case R.id.btn_flipHorizontal:
//                if(textureView.getRotationY()==0 ){
//                    textureView.setRotationY(180);
//                }
//                else {
//                    textureView.setRotationY(0);
//                }
//                break;
//            case R.id.btn_flipVertical:
//                if(textureView.getRotationX()==0){
//                    textureView.setRotationX(180);
//                }
//                else {
//                    textureView.setRotationX(0);
//                }
//                break;
//            case R.id.btn_color:
//                Dialog dialog_color=new Dialog(this);
//                dialog_color.setContentView(R.layout.color_custom);
//                seekBar_CLbrightness=dialog_color.findViewById(R.id.seekBar_CLbrightness);
//                seekBar_CLbrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        Context context=getApplicationContext();
//                        boolean canWrite= Settings.System.canWrite(context);
//                        if(canWrite)
//                        {
//                            int n=progress*100/100;
////                            txt_brightness.setText(n+"/100");
//                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
//                                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
//                            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,n);
//                        }
//                        else {
//                            Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                            context.startActivity(intent);
//                        }
//                    }
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//                seekBar_CLcontrast=dialog_color.findViewById(R.id.seekBar_CLcontrast);
//                seekBar_CLcontrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @SuppressLint("Range")
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        textureView.setOpaque(false);
//                        aspectRatioFrameLayout.setBackgroundColor(Color.blue(Color.YELLOW));
//                        textureView.setAlpha(0.30f);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//                seekBar_CLRange=dialog_color.findViewById(R.id.seekBar_CLRange);
//                seekBar_CLRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @SuppressLint("Range")
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        textureView.setOpaque(false);
//                        float[] hsvColor = {0, 1, 1};
//                        hsvColor[0] = 360f * progress / 255;
//                        aspectRatioFrameLayout.setBackgroundColor(Color.HSVToColor(hsvColor));
//                        textureView.setAlpha(.8f);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//                dialog_color.show();
//                break;
//            case R.id.btn_ratio:
//                adjustAspectRatio(1,1 );
//                Toast.makeText(this, "Vừa chiều dọc", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_repeat:
//                Toast.makeText(this, "Lặp lại video hiện tại", Toast.LENGTH_SHORT).show();
//                btn_repeat.setImageResource(R.drawable.ic_repeat_one);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mediaPlayer.start();
//                    }
//                });
//                break;
//            case R.id.btn_repeat_ab:
//                Toast.makeText(this, "lặp đoạn a-b", Toast.LENGTH_SHORT).show();
////                mediaPlayer.start();
////                currentReplay++;
////                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
////                    @Override
////                    public void onCompletion(MediaPlayer mp) {
////                        if( currentReplay < numberOfReplay){
////                            mp.start();
////                            currentReplay ++;
////                        }
////                    }
////                });
//                float speed=1.00f;
//                for(int i=5;i<=95;i++){
//                    speed+=i;
//                }
//                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
//                break;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        releaseMediaPlayer();
//
//    }
//
//    private void releaseMediaPlayer() {
//        if(mediaPlayer !=null){
//            videoHandler.removeCallbacks(videoRunnable);
//            mediaPlayer.release();
//            mediaPlayer=null;
//        }
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        releaseMediaPlayer();
//    }
//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        switch (seekBar.getId()){
//            case R.id.seekbar:
//                if(fromUser){
//                    mediaPlayer.seekTo(progress);
//                    int currentVideoDuration=mediaPlayer.getCurrentPosition();
//                    txt_startTime.setText(""+convertIntoTime(currentVideoDuration));
//                    txt_endTime.setText(""+convertIntoTime(mediaPlayer.getDuration()-currentVideoDuration));
//                }
//                break;
//        }
//    }
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//
//    }
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//
//    }
//
//    /// test
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode==1000){
//            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Permisson granted", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(this, "Permisson not granted", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//    public static Bitmap getScreenShot(View view){
//
//        View v=view.getRootView();
//        v.setDrawingCacheEnabled(true);
//        Bitmap bitmap=Bitmap.createBitmap(v.getDrawingCache());
//        bitmap.setHasAlpha(true);
//        v.setDrawingCacheEnabled(false);
//        return bitmap;
//    }
//    public void store(Bitmap bm,String fileName){
//        String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/ExoPlayer";
//        File file=new File(filePath);
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        File f=new File(filePath,fileName);
//        try {
//            FileOutputStream fos= new FileOutputStream(f);
//            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
//            fos.flush();
//            fos.close();
//            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void adjustAspectRatio(int videoWidth, int videoHeight) {
//        int viewWidth = textureView.getWidth();
//        int viewHeight = textureView.getHeight();
//        double aspectRatio = (double) videoHeight / videoWidth;
//
//        int newWidth, newHeight;
//        if (viewHeight > (int) (viewWidth * aspectRatio)) {
//            // limited by narrow width; restrict height
//            newWidth = viewWidth;
//            newHeight = (int) (viewWidth * aspectRatio);
//        } else {
//            // limited by short height; restrict width
//            newWidth = (int) (viewHeight / aspectRatio);
//            newHeight = viewHeight;
//        }
//        int xoff = (viewWidth - newWidth) / 2;
//        int yoff = (viewHeight - newHeight) / 2;
//
//
//        Matrix txform = new Matrix();
//        textureView.getTransform(txform);
//        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
//        //txform.postRotate(10);          // just for fun
//        txform.postTranslate(xoff, yoff);
//        textureView.setTransform(txform);
//    }
//
//}
