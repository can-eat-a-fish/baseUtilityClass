package cn.we.base.utils;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

import cn.we.base.widget.content.SingleChatAudioListWaveView;

/**
 * 2017年7月19日12:48:23
 */
public class SingleChatMediaManager {
    private final String TAG = SingleChatMediaManager.class.getName();
//    private static SingleChatMediaManager manager1;
//    public static SingleChatMediaManager getInstance() {
//        if (manager1 == null) {
//            synchronized (SingleChatMediaManager.class) {
//                if (manager1 == null) {
//                    manager1 = new SingleChatMediaManager();
//                }
//            }
//        }
//        return manager1;
//    }

    private MediaPlayer mediaPlayer;
    /**
     * 播放动作，异步加载完成-->true
     */
    private boolean mPlay, mPrepared;
    private SingleChatAudioListWaveView bindView;//当前播放页

    public SingleChatMediaManager() {
        setMediaPlayer();
    }

    private void setMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }
        //加载完成
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mPrepared = true;
                if (mPlay) {
                    updateView();
                    mediaPlayer.start();
                }
            }
        });
        //播放结束
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                resetBindView();
            }
        });
    }

    /**
     * 设置播放路径
     * 预加载
     *  @param url
     * @param bindView 播放当前的view
     */
    public void seturl_start_paused(String url, SingleChatAudioListWaveView bindView) {
        if (this.bindView != null && this.bindView == bindView) {
            start_paused();
            return;
        }
        resetBindView();
        try {
            mPrepared = false;
            mPlay = true;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            this.bindView = bindView;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "设置url失败");
        }
    }

    public void reData(String url, SingleChatAudioListWaveView bindView){
        try {
            mPrepared = false;
            mPlay = true;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            this.bindView = bindView;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "设置url失败");
        }
        //resetBindView();
    }

    /**
     * 重置绑定的view
     */
    private void resetBindView() {
        if (this.bindView != null) {
            this.bindView.reSetView();
        }
    }

    private void start_paused() {
        if (!mediaPlayer.isPlaying()) {
            mPlay = true;
            if (mPrepared) {
                updateView();
                mediaPlayer.start();
            } else {//预加载中，可添加加载状态

            }
        } else {
            mediaPlayer.pause();
            if(bindView!=null){
                bindView.paused();
            }
        }
    }

    /*public void paused(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }*/

    /**
     * 仅仅页面销毁回收调用
     */
    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        resetBindView();
        this.bindView = null;//解除绑定
    }

    /**
     * 重置所有状态
     */
    private void reset() {
        mPlay = false;
        mPrepared = false;
    }

    /**
     * 不在使用此类时
     * 释放资源
     */
    public void releaseAll() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
        //manager1 = null;
        this.bindView = null;
    }

    /**
     * 当前播放进度
     *
     * @return
     */
    public int getCurent() {
        int tag = -1;
        if (mediaPlayer != null) {
            tag = mediaPlayer.getCurrentPosition();
        }
        return tag;
    }

    /**
     * 得到总时长
     *
     * @return
     */
    public int getDuration() {
        int tag = -1;
        if (mediaPlayer != null) {
            tag = mediaPlayer.getDuration();
        }
        return tag;
    }

    public void seekTo(int tag) {
        if (mediaPlayer != null)
            mediaPlayer.seekTo(tag);
    }

    /**
     * 静音
     */
    public void setMute() {
        mediaPlayer.setVolume(0, 0);
    }

    /**
     * 恢复音量
     */
    public void reciverMute(float type, float num) {
        mediaPlayer.setVolume(type, num);
    }
   private void updateView(){
       if(bindView!=null){
           bindView.updateView();
       }
   }
}
