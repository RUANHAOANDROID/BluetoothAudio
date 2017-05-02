package com.bluetooth;

import java.io.File;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

/**
 * ��Ƶ¼��֧������
 * 
 * @author hao.ruan
 *
 */
@SuppressLint("SimpleDateFormat")
public class AudioRecoder {
	private AudioManager mAudioManager;
	private long startTime;
	private long mDuration;
	private String mRecordFile;
	private boolean bRun;

	private MediaRecorder mRecorder;

	public AudioRecoder(Context context) {
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

	}

	public void startRecord(Context ctx) {
		if (mAudioManager.isBluetoothScoAvailableOffCall()) {// ��������
			Log.e("AudioRecoder", "��������");
			// mAudioManager.setMode(AudioManager.Modein);
			mAudioManager.setBluetoothScoOn(true);
			mAudioManager.startBluetoothSco();
			Log.e("AudioRecoder", "��������Sco");
		}
		bRun = true;

		/* ��������ļ���·�� */
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sDateFormat.format(new java.util.Date());
		File dir = Environment.getExternalStorageDirectory();
		File file = new File(dir, "RecordFile_" + "date" + ".amr");
		mRecordFile = file.getAbsolutePath();
		if (file.exists()) {
			file.delete();
		}

		release();

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// ������˷�
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setMaxDuration(60 * 1000);
		mRecorder.setOutputFile(mRecordFile);

		try {
			mRecorder.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mRecorder.start();
		mDuration = 0;
		startTime = System.currentTimeMillis();

	}

	public void stopRecord() {
		if (mRecorder == null || !bRun)
			return;

		mDuration = (long) (Math.ceil(System.currentTimeMillis() - startTime) / 1000.0);

		try {
			mRecorder.stop();
			release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bRun = false;
		destroy();
	}

	public String getRecordPath() {
		return mRecordFile;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getDuration() {
		return mDuration;
	}

	public boolean isRun() {
		return bRun;
	}

	public int getAmplitude() {
		if (mRecorder != null) {
			return mRecorder.getMaxAmplitude();
		}
		return 0;
	}

	private void release() {
		if (mRecorder != null) {
			try {
				mRecorder.release(); // �ͷ�¼����Դ
				mRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void destroy() {
		if (mAudioManager.isBluetoothScoAvailableOffCall()) {// ��������
			mAudioManager.setMode(AudioManager.MODE_NORMAL);
			mAudioManager.setBluetoothScoOn(false);
			mAudioManager.stopBluetoothSco();
		}
	}
}
