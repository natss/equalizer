/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.player;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author Natalia Selyuto 
 */
public class OutputAudioStream {
    private final ArrayList<Byte> inputStream;
	private AudioInputStream outputAudioStream;
	
	public OutputAudioStream(ArrayList<Byte> inputStream) {
		this.inputStream = inputStream;
		this.createAudioStream(this.inputStream);
	}
	
	private AudioInputStream createAudioStream(ArrayList<Byte> inputStream) {
		AudioFileFormat aff = new AudioFileFormat();
		AudioFormat format = new AudioFormat((float)aff.getSampleRate(), 
									aff.getBits(), aff.getChannels(), 
									aff.isSigned(), aff.isBigEndian());
		
		final byte[] byteBuffer = new byte[inputStream.size()];
		
		for(int i = 0; i < inputStream.size(); i++) {
			byteBuffer[i] = inputStream.get(i);
		}
		
		ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
		
		this.outputAudioStream = new AudioInputStream(bais, format, byteBuffer.length / 2);
		
		return this.outputAudioStream;
	}
	
	public AudioInputStream getAudioStream() {
		return this.outputAudioStream;
	}
}
