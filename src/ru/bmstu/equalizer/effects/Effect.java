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
public abstract class Effect {
	protected short[] inputAudioStream;
	public abstract short[] createEffect();
	public abstract short[] getOutputAudioStream();
}
