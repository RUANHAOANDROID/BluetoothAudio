package com.bluetooth.ui;

import com.bluetooth.AudioPlayer;
import com.bluetooth.AudioPlayer.onPlayListener;
import com.bluetooth.AudioRecoder;
import com.example.bluetoothaudo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements onPlayListener {
	private AudioRecoder recoder;
	private AudioPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		recoder = new AudioRecoder(getApplicationContext());
		player = new AudioPlayer(this);
		setContentView(R.layout.main);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		recoder.destroy();
	}

	/**
	 * 录音
	 * 
	 * @param v
	 */
	public void Button1(View v) {
		recoder.startRecord(getApplicationContext());
		Toast.makeText(getApplicationContext(), "开始录音：" + recoder.getRecordPath(), 1000).show();
	}

	/**
	 * 结束
	 * 
	 * @param v
	 */
	public void Button2(View v) {
		recoder.stopRecord();
		Toast.makeText(getApplicationContext(), "结束路径：" + recoder.getRecordPath(), 1000).show();
	}

	/**
	 * 播放
	 * 
	 * @param v
	 */
	public void Button3(View v) {
		player.startPlay(recoder.getRecordPath());
		Toast.makeText(getApplicationContext(), "路径：" + recoder.getRecordPath(), 1000).show();
	}

	@Override
	public void onStop(String path) {

	}

}
