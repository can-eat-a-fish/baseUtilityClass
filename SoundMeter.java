package com.dongkesoft.iboss.utils;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： SoundMeter
 *		2.功能描述：语音波形组件.
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class SoundMeter {

	/** The Constant EMA_FILTER. */
	static final private double EMA_FILTER = 0.6;

	/** The m recorder. */
	private MediaRecorder mRecorder = null;

	/** The m ema. */
	private double mEMA = 0.0;

	/**
	 * Start.
	 *
	 * @param name
	 *            the name
	 */
	public void start(String name) {
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return;
		}
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

				@Override
				public void onError(MediaRecorder mr, int what, int extra) {
					if (mRecorder != null) {
						mRecorder.reset();
					}

				}
			});
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			// mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			File file = new File(FileUtil.getRecordFilePath(name));
			file.getParentFile().mkdirs();

			mRecorder.setOutputFile(file.getAbsolutePath());
			try {
				mRecorder.prepare();
				mRecorder.start();

				mEMA = 0.0;
			} catch (IllegalStateException e) {

			} catch (IOException e) {

			}

		} else {
			mRecorder.reset();
		}
	}

	/**
	 * Stop.
	 */
	public void stop() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
	}

	/**
	 * Pause.
	 */
	public void pause() {
		if (mRecorder != null) {
			mRecorder.stop();
		}
	}

	public void Reset() {
		mRecorder.reset();
		mRecorder = null;
	}

	/**
	 * Start.
	 */
	public void start() {
		if (mRecorder != null) {
			mRecorder.start();
		}
	}

	/**
	 * Gets the amplitude.
	 *
	 * @return the amplitude
	 */
	public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;

	}

	/**
	 * Gets the amplitude ema.
	 *
	 * @return the amplitude ema
	 */
	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
