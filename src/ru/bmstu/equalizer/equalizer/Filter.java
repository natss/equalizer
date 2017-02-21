/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.equalizer;

/**
 *
 * @author Natalia Selyuto 
 */

import java.util.concurrent.Callable;

public class Filter implements Callable<short[]>  {
	
	protected short countOfCoefs;
	protected double[] coefsFilter;
	protected short[] inputSignal;
	protected short[] outputSignal;
	protected double gain;
	
	public Filter(final int lenghtOfInputSignal){
		gain = 1;
		this.outputSignal = new short[lenghtOfInputSignal];
	}
	

	public void settings(final double[] coefsFilter, final short countOfCoefs, final short[] inputSignal) {
		this.inputSignal = inputSignal;
		this.coefsFilter =  coefsFilter;
		this.countOfCoefs = countOfCoefs;
		this.outputSignal = new short[inputSignal.length];
	}
	
	private short[] svertka() {
		int multiplication;
		for(int i = 0; i <  inputSignal.length - FilterInfo.COUNT_OF_COEFS; i++) {
			for(int j = 0; j < this.countOfCoefs; j++) {
				multiplication =  (int) (this.inputSignal[i] * this.coefsFilter[j]);
                                this.outputSignal[i+j] += gain * (short)(multiplication);
			}	
		}
		return this.outputSignal;
	}

	public void setGain(float d) {
		this.gain = d;
	}
	
	public double getGain() {
		return this.gain;
	}
	
	public short[] getOutputSignal() {
		return this.outputSignal;
	}
	
	public long getCountOfSamples() {
		return this.inputSignal.length;
	}

	@Override
	public short[] call() throws Exception {
		this.svertka();
		return this.outputSignal;
	}

}


