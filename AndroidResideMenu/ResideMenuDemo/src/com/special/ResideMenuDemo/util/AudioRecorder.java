package com.special.ResideMenuDemo.util;

import java.io.File;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

public class AudioRecorder
{
	private static int SAMPLE_RATE_IN_HZ = 8000; 

	final MediaRecorder recorder = new MediaRecorder();
	final String path;

	public String getFileName()
	{
		return path;
	}
	public AudioRecorder(String path)
	{
		this.path = sanitizeVoicePath(path);
	}

	private String sanitizeVoicePath(String path)
	{
		if (!path.startsWith("/"))
		{
			path = "/" + path;
		}
		if (!path.contains("."))
		{
			path += ".amr";
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/cfrt/patrol/voice" + path;
	}

	public void start() throws IOException
	{
		
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) { throw new IOException(
				"SD Card is not mounted,It is  " + state + "."); }
		File directory = new File(path).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) { 
			throw new IOException("Path to file could not be created"); 
		}
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);
		recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
		recorder.setOutputFile(path);
		recorder.prepare();
		recorder.start();
	}

	public void stop() throws IOException
	{
		recorder.stop();
		recorder.release();
	}
	
	public double getAmplitude() {		
		if (recorder != null){			
			return  (recorder.getMaxAmplitude());		
			}		
		else			
			return 0;	
		}
}