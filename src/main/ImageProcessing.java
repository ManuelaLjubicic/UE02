package main;

import Catalano.Imaging.FastBitmap;
import filter.*;
import pipes.PullPipe;
import pipes.PushPipe;
import pipes.SynchronizedPipe;

import java.awt.*;
import java.io.File;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

/**
 * Created by Karin on 19.11.2015.
 */
public class ImageProcessing {

    protected void exerciseA(int width, String filePathSource, String processedImage,
                                   String printFileName, int startValue, int setValueY, int incrementX,
                                   int toleranceX, int toleranceY, int maxIterations) {

        PushPipe<FastBitmap> pipe1 = new PushPipe<>();
        LoadImageSource filter1 = new LoadImageSource(pipe1);

        PushPipe<FastBitmap> pipe2 = new PushPipe<>();
        ROIFilter filter2 = new ROIFilter(pipe2, 0, 55, width, 80);

        PushPipe<FastBitmap> pipe3 = new PushPipe<>();
        ThresholdFilter filter3 = new ThresholdFilter(pipe3);

        PushPipe<FastBitmap> pipe4 = new PushPipe<>();
        MedianFilter filter4 = new MedianFilter(pipe4, 3);

        PushPipe<FastBitmap> pipe5 = new PushPipe<>();
        OpeningFilter filter5 = new OpeningFilter(pipe5, 5, 1);

        PushPipe<FastBitmap> pipe6 = new PushPipe<>();
        SaveFilter filter6 = new SaveFilter(pipe6, processedImage);

        PushPipe<Point[]> pipe7 = new PushPipe<>();
        CalcCentroidsFilter filter7 = new CalcCentroidsFilter(pipe7);

        PrintSink filter8 = new PrintSink(printFileName, startValue, setValueY, incrementX, toleranceX, toleranceY);

        pipe1.setSuccessorFilter(filter2);
        pipe2.setSuccessorFilter(filter3);
        pipe3.setSuccessorFilter(filter4);
        pipe4.setSuccessorFilter(filter5);
        pipe5.setSuccessorFilter(filter6);
        pipe6.setSuccessorFilter(filter7);
        pipe7.setSuccessorFilter(filter8);

        try {
            for(int i = 0; i < maxIterations; i++){
                filter1.write(filePathSource);
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

        protected void exerciseB(int width, String filePathSource, String processedImage,
                                  String printFileName, int startValue, int setValueY, int incrementX,
                                  int toleranceX, int toleranceY, int maxIterations) {

        PullPipe<FastBitmap> pipe1 = new PullPipe<>();
        LoadImageSource filter1 = new LoadImageSource(filePathSource);

        PullPipe<FastBitmap> pipe2 = new PullPipe<>();
        ROIFilter filter2 = new ROIFilter(pipe1, 0, 55, width, 80);

        PullPipe<FastBitmap> pipe3 = new PullPipe<>();
        ThresholdFilter filter3 = new ThresholdFilter(pipe2);


        SynchronizedPipe<FastBitmap> synchronizedPipe1 = new SynchronizedPipe<>();
        MedianFilter filter4 = new MedianFilter(pipe3, synchronizedPipe1, 3, maxIterations);

        PushPipe<FastBitmap> pipe4 = new PushPipe<>();
        OpeningFilter filter5 = new OpeningFilter(synchronizedPipe1, pipe4, 5, 1, maxIterations);

        PushPipe<FastBitmap> pipe5 = new PushPipe<>();
        SaveFilter filter6 = new SaveFilter(pipe5, processedImage);

        PushPipe<Point[]> pipe6 = new PushPipe<>();
        CalcCentroidsFilter filter7 = new CalcCentroidsFilter(pipe6);

        PrintSink filter8 = new PrintSink(printFileName, startValue, setValueY, incrementX, toleranceX, toleranceY);

        pipe1.setPredecessorFilter(filter1);
        pipe2.setPredecessorFilter(filter2);
        pipe3.setPredecessorFilter(filter3);

        pipe4.setSuccessorFilter(filter6);
        pipe5.setSuccessorFilter(filter7);
        pipe6.setSuccessorFilter(filter8);


        Thread pullThread = new Thread(filter4);
        pullThread.start();

        Thread pushThread = new Thread(filter5);
        pushThread.start();

        while(pullThread.isAlive() || pushThread.isAlive()){

        }
    }

    protected void exerciseAPull(int width, String filePathSource, String processedImage,
                             String printFileName, int startValue, int setValueY, int incrementX,
                             int toleranceX, int toleranceY, int maxIterations) {

//        PullPipe<FastBitmap> pipe1 = new PullPipe<>();
//        PrintSink filter1 = new PrintSink(pipe1, printFileName, startValue, setValueY, incrementX, toleranceX, toleranceY);

        PullPipe<FastBitmap> pipe2 = new PullPipe<>();
        CalcCentroidsFilter filter2 = new CalcCentroidsFilter(pipe2);

        PullPipe<FastBitmap> pipe3 = new PullPipe<>();
        SaveFilter filter3 = new SaveFilter(pipe3, processedImage);

        PullPipe<FastBitmap> pipe4 = new PullPipe<>();
        OpeningFilter filter4 = new OpeningFilter(pipe4,5,1);

        PullPipe<FastBitmap> pipe5 = new PullPipe<>();
        MedianFilter filter5 = new MedianFilter(pipe5, 3);

        PullPipe<FastBitmap> pipe6 = new PullPipe<>();
        ThresholdFilter filter6 = new ThresholdFilter(pipe6);

        PullPipe<FastBitmap> pipe7 = new PullPipe<>();
        ROIFilter filter7 = new ROIFilter(pipe7,0, 55, width, 80);

        PullPipe<FastBitmap> pipe8 = new PullPipe<>();
        LoadImageSource filter8 = new LoadImageSource(filePathSource);

//        pipe1.setPredecessorFilter(filter2);
        pipe2.setPredecessorFilter(filter3);
        pipe3.setPredecessorFilter(filter4);
        pipe4.setPredecessorFilter(filter5);
        pipe5.setPredecessorFilter(filter6);
        pipe6.setPredecessorFilter(filter7);
        pipe7.setPredecessorFilter(filter8);

//        try {
//            for(int i = 0; i < maxIterations; i++){
//                filter1.read();
//            }
//        } catch (StreamCorruptedException e) {
//            e.printStackTrace();
//        }
    }
}
