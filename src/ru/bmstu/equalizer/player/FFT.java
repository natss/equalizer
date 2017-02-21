/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.player;

import static java.lang.Math.*;


/**
 *
 * @author Natalia Selyuto 
 */
public class FFT {
    // AVal - массив анализируемых данных, Nvl - длина массива должна быть кратна степени 2.
// FTvl - массив полученных значений, Nft - длина массива должна быть равна Nvl.

    final double TwoPi = 6.283185307179586;
    private short[] AVal;
    private int Nvl;
    private double[] FTvl;
    private int Nft;

    public FFT(){
    
    }

    void FFTAnalysis(final short[] input, int size) {
  
        this.AVal = input;
        this.Nvl = size;
        this.Nft = size;
        this.FTvl = new double[Nft];
        int i, j, n, m, Mmax, Istp;
        double Tmpr, Tmpi, Wtmp, Theta;
        double Wpr, Wpi, Wr, Wi;
        double[] Tmvl;
  
        n = Nvl * 2; Tmvl = new double[n];
        for (i = 0; i < n; i+=2) {
        Tmvl[i] = 0;
        Tmvl[i+1] = AVal[i/2];
   
        }

        i = 1; j = 1;
        while (i < n) {
            if (j > i) {
                Tmpr = Tmvl[i]; Tmvl[i] = Tmvl[j]; 
                Tmvl[j] = Tmpr; Tmpr = Tmvl[i+1]; 
                Tmvl[i+1] = Tmvl[j+1]; Tmvl[j+1] = Tmpr;
            }
            i = i + 2; m = Nvl;
            while ((m >= 2) && (j > m)) {
                j = j - m; m = m >> 1;
            }
            j = j + m;
        }

        Mmax = 2;
        while (n > Mmax) {
            Theta = -TwoPi / Mmax; Wpi = sin(Theta);
            Wtmp = sin(Theta / 2); Wpr = Wtmp * Wtmp * 2;
            Istp = Mmax * 2; Wr = 1; Wi = 0; m = 1;

            while (m < Mmax) {
                i = m; m = m + 2; 
                Tmpr = Wr; Tmpi = Wi;
                Wr = Wr - Tmpr * Wpr - Tmpi * Wpi;
                Wi = Wi + Tmpr * Wpi - Tmpi * Wpr;

                while (i < n) {
                    j = i + Mmax;
                    Tmpr = Wr * Tmvl[j] - Wi * Tmvl[j-1];
                    Tmpi = Wi * Tmvl[j] + Wr * Tmvl[j-1];
                    Tmvl[j] = Tmvl[i] - Tmpr; 
                    Tmvl[j-1] = Tmvl[i-1] - Tmpi;
                    Tmvl[i] = Tmvl[i] + Tmpr; 
                    Tmvl[i-1] = Tmvl[i-1] + Tmpi;
                    i = i + Istp;
                }
            }
            Mmax = Istp;
        }

        for (i = 0; i < Nft; i++) {
            j = i * 2; 
            FTvl[i] = 2 * sqrt(pow(Tmvl[j],2) + pow(Tmvl[j+1],2)) / Nvl;
        }     
    }   

    public double[] getFFTData(){
        return FTvl;
    }   
}
