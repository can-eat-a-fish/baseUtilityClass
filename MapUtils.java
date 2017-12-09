package cn.we.base.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by lei on 2016/12/17.
 */

public class MapUtils {
    public static AMapLocationClient mlocationClient;

    public static void getCurrentLocation(Context context, AMapLocationListener aMapLocationListener) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationCacheEnable(true);
        if (aMapLocationListener != null)
            //设置定位回调监听
            mlocationClient.setLocationListener(aMapLocationListener);
        //设置为高精度定位模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();//启动定位

    }

    public static void mapScreenShot(AMap aMap, final FileOutputStream fos) {
        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                                  @Override
                                  public void onMapScreenShot(Bitmap bitmap) {
                                      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                                      if (null == bitmap) {
                                          return;
                                      }
                                      boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                      try {
                                          fos.flush();
                                      } catch (IOException e) {
                                          e.printStackTrace();
                                      }
                                      try {
                                          fos.close();
                                      } catch (IOException e) {
                                          e.printStackTrace();
                                      }
                                      StringBuffer buffer = new StringBuffer();
                                      if (b)
                                          buffer.append("截屏成功 ");
                                      else {
                                          buffer.append("截屏失败 ");
                                      }
                                  }
                              }

        );
    }
}
