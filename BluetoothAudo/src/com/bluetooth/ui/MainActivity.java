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
	 * ¼��
	 * 
	 * @param v
	 */
	public void Button1(View v) {
		recoder.startRecord(getApplicationContext());
		Toast.makeText(getApplicationContext(), "��ʼ¼����" + recoder.getRecordPath(), 1000).show();
	}

	/**
	 * ����
	 * 
	 * @param v
	 */
	public void Button2(View v) {
		recoder.stopRecord();
		Toast.makeText(getApplicationContext(), "����·����" + recoder.getRecordPath(), 1000).show();
	}

	/**
	 * ����
	 * 
	 * @param v
	 */
	public void Button3(View v) {
		player.startPlay(recoder.getRecordPath());
		Toast.makeText(getApplicationContext(), "·����" + recoder.getRecordPath(), 1000).show();
	}

	@Override
	public void onStop(String path) {

	}

}
