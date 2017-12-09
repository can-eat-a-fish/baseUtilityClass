package cn.we.base.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import cn.we.base.R;

public class MusicPlay {
    Context context = null;
    AudioManager audioManager;
    MediaPlayer playerSound;
    Thread playThread;

    // 播放音频文件，固定使用扬声器播放
    public MusicPlay(Context context) {
        this.context = context;
        audioManager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMicrophoneMute(false);
        audioManager.setSpeakerphoneOn(true);// 使用扬声器播放，即使已经插入耳机
        audioManager.setMode(AudioManager.STREAM_MUSIC);
    }

    public void play() {
        playThread = new Thread(new PlayThread());
        playThread.run();

    }

    class PlayThread implements Runnable {

        @Override
        public void run() {
            //Log.e("zhiyinqing", "执行到了run方法");

            if (audioManager.isSpeakerphoneOn()) {
                // Log.e("liuyu","扬声器打开了");
            } else {
                audioManager.setSpeakerphoneOn(false);
                Log.e("liuyu", "扬声器关闭了");
                if (audioManager.isSpeakerphoneOn()) {
                    // Log.e("liuyu","扬声器打开了");
                } else {
                    // Log.e("liuyu","扬声器还是没打开");
                }
            }
            playerSound = MediaPlayer.create(context, R.raw.beep);
            playerSound.start();

        }

    }

}