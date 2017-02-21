/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.effects;

/**
 *
 * @author Natalia Selyuto
 */
public class Delay extends Effect{
	
	public Delay() {
		super(); 
	}
	
	public void setInputSampleStream(short[] inputAudioStream) {
		this.inputAudioStream = inputAudioStream;		
	}

	@Override
	public synchronized short[] createEffect() {
		
		short amplitude;
		short delayAmplitude;	
		int checkFlag;
		int delay = 5000;
		int position = 0;
		
		for(int i = delay ; i < this.inputAudioStream.length; i ++) {
			amplitude = this.inputAudioStream[i];
			delayAmplitude = this.inputAudioStream[position];
			checkFlag = ( (( delayAmplitude) + (int)(0.9 * amplitude)));
			if(checkFlag < Short.MAX_VALUE && checkFlag > Short.MIN_VALUE) { 
				delayAmplitude = (short)checkFlag;
				this.inputAudioStream[position] =  delayAmplitude; 
				position += 1;
			} 
		}
		return this.inputAudioStream;
	}

	@Override
	public synchronized short[] getOutputAudioStream() {
		return this.inputAudioStream;
	}
}
