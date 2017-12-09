package cn.we.base.utils;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * amr音频处理
 *
 * @author hongfa.yy
 * @version 创建时间2012-11-21 下午4:33:28
 */
public class RecordManager {

    public static final int MAX_LENGTH = 1000 * 61;// 最大录音时长1000*60;
    private static final int TIME = 0X11;
    private final String TAG = "RecordManager";
    private Handler mHandler = new Handler();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIME) {

                if (PROGRESS > MAX_LENGTH) {

                    if (onTimePassListener != null) {
                        PROGRESS = 0;
                        onTimePassListener.recordComplete();
                    }
                    stopRecord();
                } else {
                    PROGRESS += 1000;
                    if (onTimePassListener != null && PROGRESS < MAX_LENGTH) {
                        onTimePassListener.getCurrentTime(PROGRESS / 1000);
                    }
                    handler.sendEmptyMessageDelayed(TIME, 1000);
                }
            }
        }
    };

    private OnVolumnChangedListener onVolumnChangedListener;
    private MediaRecorder mMediaRecorder;
    private File file;
    private long startTime;
    private long endTime;
    private long progress;
    private OnTimePassListener onTimePassListener;
    /**
     * 更新话筒状态 分贝是也就是相对响度 分贝的计算公式K=20lg(Vo/Vi) Vo当前振幅值 Vi基准值为600：我是怎么制定基准值的呢？ 当20
     * * Math.log10(mMediaRecorder.getMaxAmplitude() / Vi)==0的时候vi就是我所需要的基准值
     * 当我不对着麦克风说任何话的时候，测试获得的mMediaRecorder.getMaxAmplitude()值即为基准值。
     * Log.i("mic_", "麦克风的基准值：" + mMediaRecorder.getMaxAmplitude());前提时不对麦克风说任何话
     */
    private int BASE = 20;
    private int SPACE = 80;// 间隔取样时间
    private int PROGRESS;
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    public RecordManager(File file) {
        this.file = file;
    }

    /**
     * 开始录音 使用amr格式
     *
     * @param
     * @return
     */
    public void startRecord() {
        PROGRESS = 0;

        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mMediaRecorder.setAudioSamplingRate(16000);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            mMediaRecorder.setAudioChannels(2);
            /* ③准备 */
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            handler.sendEmptyMessageDelayed(TIME, 1000);
            // pre=mMediaRecorder.getMaxAmplitude();
            updateMicStatus();
        } catch (IllegalStateException e) {
            Log.i(TAG,
                    "call startAmr(File mRecAudioFile) failed!"
                            + e.getMessage());
        } catch (IOException e) {
            Log.i(TAG,
                    "call startAmr(File mRecAudioFile) failed!"
                            + e.getMessage());
        }

    }

    /**
     * 停止录音
     *
     * @param
     */
    public long stopRecord() {
        if (mMediaRecorder == null)
            return 0L;

        endTime = System.currentTimeMillis();
        Log.i("ACTION_END", "endTime" + endTime);
        /* zk 录音时间过短闪退 至少录制1秒*/
        if ((endTime - startTime) > 1000) {
            mMediaRecorder.stop();
        }
        if (onTimePassListener != null) {
            onTimePassListener.recordComplete();
        }

        mMediaRecorder.reset();
        mMediaRecorder.release();

        mHandler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        PROGRESS = 0;
        mMediaRecorder = null;
        Log.i("ACTION_LENGTH", "Time" + (endTime - startTime));
        return endTime - startTime;
    }

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            int ratio = mMediaRecorder.getMaxAmplitude() / BASE;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (30 * Math.log10(ratio));
            else {
                db = 2;
            }

            if (onVolumnChangedListener != null) {
                onVolumnChangedListener.onVolumnChanged(MAX_LENGTH, PROGRESS, db);
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
            /*
             * if (db > 1) { vuSize = (int) (20 * Math.log10(db)); Log.i("mic_",
             * "麦克风的音量的大小：" + vuSize); } else Log.i("mic_", "麦克风的音量的大小：" + 0);
             */
        }
    }

    public OnVolumnChangedListener getOnVolumnChangedListener() {
        return onVolumnChangedListener;
    }

    public void setOnVolumnChangedListener(OnVolumnChangedListener onVolumnChangedListener) {
        this.onVolumnChangedListener = onVolumnChangedListener;
    }

    public boolean isListening() {
        return false;
    }

    public long getDuration() {
        return PROGRESS / 1000;
    }

    public void release() {
        if (mMediaRecorder != null)
            mMediaRecorder.reset();
        if (mMediaRecorder != null)
            mMediaRecorder.release();
        mHandler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        PROGRESS = 0;
        mMediaRecorder = null;
    }

    public interface OnVolumnChangedListener {
        void onVolumnChanged(long max, long progress, int db);
    }

    public interface OnTimePassListener {
        void getCurrentTime(long time);

        void recordComplete();

    }

    public OnTimePassListener getOnTimePassListener() {
        return onTimePassListener;
    }

    public void setOnTimePassListener(OnTimePassListener onTimePassListener) {
        this.onTimePassListener = onTimePassListener;
    }
}
