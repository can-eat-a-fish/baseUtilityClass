package cn.we.base.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by cuiruolei on 2017/7/9.
 */
public class MediaManager {
    private MediaPlayer mMediaPlayer;
    private static MediaManager ourInstance = new MediaManager();
    public Uri uri;
    public String path;
    public Context context;

    public static MediaManager getInstance() {
        return ourInstance;
    }

    private MediaManager() {
    }

    public String getPath() {
        return path;
    }

    public void start() {
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public void stop() {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
    }

    public void release() {
        if (mMediaPlayer != null)
            mMediaPlayer.release();
    }

    public void reset() {
        if (mMediaPlayer != null)
            mMediaPlayer.reset();
    }

    public void setDisplay(SurfaceHolder surface) {
        mMediaPlayer.setDisplay(surface);
    }

    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    public void setSurface(Surface surface) {
        mMediaPlayer.setSurface(surface);
    }

    public void prepare() {
        try {
            if (mMediaPlayer != null)
                mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seekTo(long msec, int mode) {
        //if (mMediaPlayer != null)
            //mMediaPlayer.seekTo(msec, mode);
    }

    public void seekTo(int msec) {
        if (mMediaPlayer != null)
            mMediaPlayer.seekTo(msec);
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        if (mMediaPlayer != null)
            mMediaPlayer.setOnPreparedListener(onPreparedListener);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        if (mMediaPlayer != null)
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        if (mMediaPlayer != null)
            mMediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    public void setDataSource(Context context, Uri uri) {
        try {
//            mMediaPlayer = new MediaPlayer();
            this.uri = uri;
            this.context = context;
            mMediaPlayer.setDataSource(context, uri);
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setDataSource(String path) {
//        mMediaPlayer.reset();
        this.path = path;
        mMediaPlayer = MediaPlayer.create(context, Uri.parse(path));
    }

    public void setDataSource(Context context, String path) {
//        mMediaPlayer.reset();
        this.path = path;
        mMediaPlayer = MediaPlayer.create(context, Uri.parse(path));
    }

    public boolean isPlaying() {
//        if (mMediaPlayer == null) {
//            mMediaPlayer = new MediaPlayer();
//        }
        boolean playing = false;
        if (mMediaPlayer != null)
            playing = mMediaPlayer.isPlaying();
        return playing;
    }

    public int getDuration() {
        if (mMediaPlayer != null)
            return mMediaPlayer.getDuration();
        return 0;
    }

    public void setOnInfoListener(MediaPlayer.OnInfoListener onInfoListener) {
        mMediaPlayer.setOnInfoListener(onInfoListener);
    }

    public void setOnTimedTextListener(MediaPlayer.OnTimedTextListener onTimedTextListener) {
        mMediaPlayer.setOnTimedTextListener(onTimedTextListener);
    }

    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        mMediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    public void setOnBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        mMediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
    }

    public int getCurrentPosition() {
        if (mMediaPlayer != null)
            return mMediaPlayer.getCurrentPosition();
        return 0;
    }

    public int getVideoWidth() {
        return mMediaPlayer.getVideoWidth();
    }

    public int getVideoHeight() {
        return mMediaPlayer.getVideoHeight();
    }


}
