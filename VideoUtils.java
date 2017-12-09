package cn.we.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lei on 2016/12/2.
 */

public class VideoUtils {
    public static void getFirstFrameFromVideo(String path, ImageView imageView) {
        //创建MediaMetadataRetriever对象
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //设置资源位置
        //绑定资源
        mmr.setDataSource(path);
        //获取第一帧图像的bitmap对象
        Bitmap bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        //加载到ImageView控件上
        imageView.setImageBitmap(bitmap);
        mmr.release();
    }

    public static void getFirstFrameFromVideo(Context context, Uri uri, ImageView imageView) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        imageView.setImageBitmap(mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST));
        mmr.release();
    }

    public static Bitmap getFirstFrameFromVideo(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        Bitmap firstFrame = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        mmr.release();
        return firstFrame;
    }

    /**
     * 获取视频第一帧图像,getFrameAt
     * 参数一是微秒数，如果传入毫秒数只能获得第一帧；
     * 参数二MediaMetadataRetriever.OPTION_CLOSEST在给定时间内检索最近的一帧，
     * 不一定是关键帧，不设置此参数会导致图像并不是视频实际的第一帧
     * 这个方法获取视频第一帧效率很低
     *
     * @param context 上下文
     * @param uri     本地视频Uri
     * @return 返回视频第一帧的Bitmap
     */
    public static Bitmap getFirstFrameFromVideo(Context context, Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        Bitmap firstFrame = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        mmr.release();
        return firstFrame;
    }

    public static File getFirstFrameRromVideo(Context context, Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        Bitmap firstFrame = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        FileOutputStream fos = null;
        File file = null;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                    File.separator + System.currentTimeMillis() + ".jpg";
            file = new File(path);
            fos = new FileOutputStream(file);
            fos.write(BitmapUtils.bitmap2Bytes(firstFrame));
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mmr.release();
        return file;
    }

    /**
     * 获取网络视频的时长
     *
     * @param url 视频地址
     * @return 单位是毫秒
     */
    public static int getDuration(String url) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(url);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();
        return Integer.parseInt(duration);
    }

    /**
     * 获取本地视频时长
     *
     * @param context 上下文
     * @param uri     本地视频Uri
     * @return 单位是毫秒
     */
    public static int getDuration(Context context, Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();
        return Integer.parseInt(duration);
    }
}
