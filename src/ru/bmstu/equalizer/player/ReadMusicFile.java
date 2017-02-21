/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.player;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Natalia Selyuto 
 */
public class ReadMusicFile{
	private AudioInputStream ais;
	private byte[] outputSignal;
	private SourceDataLine sdl;
	public ReadMusicFile(File filePath) throws UnsupportedAudioFileException, IOException, InterruptedException, LineUnavailableException {
			
		if(filePath != null) {
			this.ais = AudioSystem.getAudioInputStream(filePath);
			AudioFormat format = ais.getFormat();
			this.sdl = AudioSystem.getSourceDataLine(format);
			this.sdl.flush();
		}
		
	}
	
	public byte[] getOutputSignal() {
		return this.outputSignal;
	}
	
	public AudioInputStream getAudioInputStream() {
		return this.ais;
	}
	
	public void closeAudioInputStream() {
		try {
			this.ais.close();
		} catch (IOException e) {
		}
	}
	
	public SourceDataLine getSourceDataLine() {
		return this.sdl;
	}
}

