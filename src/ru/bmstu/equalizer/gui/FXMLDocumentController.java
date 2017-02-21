/*
 * Copyright (C) Natalia Selyuto 2016 
 * Bauman Moscow State Technical University
 * IU3 
 */
package ru.bmstu.equalizer.gui;


import ru.bmstu.equalizer.player.AudioPlayer;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.*;



/**
 *
 * @author Natalia Selyuto
 */
public class FXMLDocumentController implements Initializable {
     
   
    @FXML 
    private Slider Slider0, Slider1, Slider2, Slider3, Slider4, Slider5,
                    soundSlider, distortionSlider;
    @FXML 
    private Label Label0, Label1, Label2, Label3, Label4, Label5;
    @FXML
    private LineChart chart1;
    @FXML
    private LineChart chart2;
    @FXML
    private NumberAxis xAxis1, yAxis1, xAxis2, yAxis2;
    private AudioPlayer audioPlayer;
    private Thread playThread, plotThread;
    @FXML
    CheckBox chorusBox, distBox;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        this.listenSliders();
        this.initialGraph();
        this.checkBoxInnitial();
        this.volumeFromSlider();  
    }
    
    @FXML
    private void open() throws FileNotFoundException, IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        //Выбор файлов формата wav
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Audio Files", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        
        if(selectedFile == null) return;
        
        this.audioPlayer = new AudioPlayer(selectedFile);  
        playThread = new Thread(()->{
        	this.audioPlayer.play();
        });
        playThread.start();
        
        System.out.println("PLAY");  
    }
    
    @FXML
    private void play() {
        if (this.audioPlayer != null)
            this.audioPlayer.setPause(false);
    }
    
    @FXML
    private void pause() {
        if (this.audioPlayer != null)
            this.audioPlayer.setPause(true);
    }
    
    @FXML
    private void stop() {
        if (this.audioPlayer == null) return;
        Slider0.setValue(1.0);
        Slider1.setValue(1.0);
        Slider2.setValue(1.0);
        Slider3.setValue(1.0);
        Slider4.setValue(1.0);
        Slider5.setValue(1.0);
        
        soundSlider.setValue(0.65);
        this.distortionSlider.setValue(1.0);
    }
    
    @FXML
    private void chorusBox() throws IOException, InterruptedException{
        System.out.println("Delay");
    	if(!this.audioPlayer.delayIsActive())
    		this.audioPlayer.setDelay(true);
    	else this.audioPlayer.setDelay(false);
    }
    
    @FXML
    private void distBox(){
        System.out.println("Distortion");
    	if(!this.audioPlayer.distortionIsActive())
    		this.audioPlayer.setDistortion(true);
    	else this.audioPlayer.setDistortion(false);
    }
    
    @FXML
    private void clickClose() {
    	if(this.audioPlayer != null) {
            if(this.playThread != null)
        	this.playThread.interrupt();
            this.audioPlayer.getEqualizer().close();
            this.audioPlayer.close();
    	}
    	
    	System.exit(0);
    }
    
    //Метод, осуществляющий прослушку слайдеров и изменяющий КУ полос эквалайзера
    private void listenSliders(){
        Slider0.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label0.setText(str);
            audioPlayer.getEqualizer().getFilter((short)0).setGain((float)newValue.doubleValue());
        });
        
        Slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label1.setText(str);
            audioPlayer.getEqualizer().getFilter((short)1).setGain((float)newValue.doubleValue());
        });
        
        Slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label2.setText(str);
           audioPlayer.getEqualizer().getFilter((short)2).setGain((float)newValue.doubleValue());
        });
        
        Slider3.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label3.setText(str);
            audioPlayer.getEqualizer().getFilter((short)3).setGain((float)newValue.doubleValue());
        });
        
        Slider4.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label4.setText(str);
            audioPlayer.getEqualizer().getFilter((short)4).setGain((float)newValue.doubleValue());
        });
        
        Slider5.valueProperty().addListener((observable, oldValue, newValue) -> {
            String str = String.format("%.3f",(newValue.doubleValue()));
            Label5.setText(str);
            audioPlayer.getEqualizer().getFilter((short)5).setGain((float)newValue.doubleValue());
        });
        
        distortionSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            String str = String.format("%.1f", (newValue.doubleValue()));
            audioPlayer.setDistortionCoef(newValue.doubleValue());
        });
    }
    
    
    private XYChart.Data[] oldData;
    private XYChart.Data[] newData;
    //Метод, осуществляющий инициализацию графиков
    private void initialGraph(){
        XYChart.Series oldGraph = new XYChart.Series<>();
        XYChart.Series newGraph = new XYChart.Series<>();
        int size = 512;
        oldData = new XYChart.Data[size];
        newData = new XYChart.Data[size];
        for (int i = 0; i < oldData.length; i++){
            oldData[i] = new XYChart.Data<>(i, 0);
            oldGraph.getData().add(oldData[i]);
            
            newData[i] = new XYChart.Data<>(i, 0);
            newGraph.getData().add(newData[i]);
        }
        
        chart1.getData().add(oldGraph);
        chart2.getData().add(newGraph);
        chart1.setCreateSymbols(false);
        chart2.setCreateSymbols(false);
        chart1.setAnimated(false);
        chart2.setAnimated(false);
        
        this.chart1.getYAxis();
        this.yAxis1.setLowerBound(-0.2);
        this.yAxis1.setUpperBound(0.3);
        this.yAxis1.setAnimated(false);
        
        this.chart2.getYAxis();
        this.yAxis2.setLowerBound(-0.2);
        this.yAxis2.setUpperBound(0.3);
        this.yAxis2.setAnimated(false);  
    }
    
    private void checkBoxInnitial() {
    	this.chorusBox = new CheckBox();
    	this.chorusBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            });
        
        this.distBox = new CheckBox();
        this.distBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            });
    }
    
    private void volumeFromSlider() {
        soundSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            audioPlayer.setVolume(newValue.doubleValue());
        });
    }

    private boolean graphFlag = false;
    @FXML
    private void clickPlot(){
        if (this.graphFlag == false){
            this.graphFlag = true;
        }
        else this.graphFlag = false;
        //System.out.println("PLOT");
        this.plotThread = new Thread(()->{
            while(this.graphFlag){
                if(this.graphFlag == false)
                    for(;;){
                        try{
                            if(this.graphFlag == true) break;
                            this.plotThread.sleep(50);
                        }
                        catch(Exception e){
                        }
                    }
                
                if(audioPlayer.isCalculated){
                        for(int j = 0; j < this.audioPlayer.getFourierInput().length; j++){
                        //System.out.println("plotddd");
                        this.oldData[j].setYValue(Math.log10(this.audioPlayer.getFourierInput()[j] * 0.1) / 10);
                        this.newData[j].setYValue(Math.log10(this.audioPlayer.getFourierOutput()[j] * 0.1) / 10);
                        }
                }
                try {
                    this.plotThread.sleep(60);
                } 
                catch (Exception e) {
                }
            }
        });
        plotThread.start();
    }
}
