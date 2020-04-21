package com.example.app_exoplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_exoplayer.Interface.ClickListener;
import com.example.app_exoplayer.Models.ExoPlayer_Model;
import com.example.app_exoplayer.R;
import com.example.app_exoplayer.VideoPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExoPlayer_Adapter extends RecyclerView.Adapter{
    List<ExoPlayer_Model> exoPlayer_models=new ArrayList<>();
    Context context;
    ClickListener clickListener;

    public ExoPlayer_Adapter(List<ExoPlayer_Model> exoPlayer_models, Context context, ClickListener clickListener) {
        this.exoPlayer_models = exoPlayer_models;
        this.context = context;
        this.clickListener = clickListener;
    }
    public class VideoHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView duration;
        public TextView filePath;
        public ImageView thumb;

        public VideoHolder(View view){
            super(view);
            title=view.findViewById(R.id.exo_title);
            duration=view.findViewById(R.id.exo_duration);
            filePath=view.findViewById(R.id.file_path);
            thumb=view.findViewById(R.id.thumb);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exoplayer,parent,false);
        return new VideoHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoHolder videoHolder= (VideoHolder) holder;
        ExoPlayer_Model exoPlayer_model=exoPlayer_models.get(position);
        videoHolder.title.setText(exoPlayer_model.getFileName());
        videoHolder.filePath.setText(exoPlayer_model.getFilePath());
        MediaPlayer mediaPlayer= MediaPlayer.create(context, Uri.fromFile(new File(exoPlayer_model.getFilePath())));
        double msec=0;
        if(mediaPlayer!=null){
            msec=mediaPlayer.getDuration();
        }
        double minutes=(msec%3600)/60;
        videoHolder.duration.setText(""+String.format("%.2f",minutes));
        Glide.with(context)
                .load(exoPlayer_model.getFilePath())
                .into(videoHolder.thumb);

        double finalMsec = msec;
        videoHolder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayer.class);
                intent.putExtra("title",exoPlayer_models.get(holder.getAdapterPosition()).getFileName());
                if(finalMsec ==0){
                    Toast.makeText(context, "Invalid Video", Toast.LENGTH_SHORT).show();
                }
                else{
                    clickListener.onClickItem(exoPlayer_model.getFilePath());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exoPlayer_models.size();
    }
}
