package main;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Opening;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import filter.*;
import pipes.PullPipe;
import pipes.PushPipe;
import pipes.SynchronizedPipe;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.Date;

/**
 * Created by manue on 09.11.2015.
 */
public class Main {

    static FastBitmap fb = new FastBitmap("res\\loetstellen.jpg");
    static int width = fb.getWidth();
    static int heigth = fb.getHeight();
    static String filePathSource = "res\\loetstellen.jpg";
    static String saveProcessedName = "res\\processedFoto.png";
    static String printFileName = "res\\printProcessedFoto.txt";
    static int startValue = 6;
    static int incrementX = 65;
    static int setValueY = 25;
    static int toleranceX = 5;
    static int toleranceY = 5;
    static int maxIterations = 50;


    public static void main(String[] args) {

        System.out.println("running without threads...");
        long start = new Date().getTime();
        excerciseA();
        long runningTime = new Date().getTime() - start;
        System.out.println("Running time without threads: " + runningTime + " ms");

        System.out.println("running with threads...");
        long startWithThreads = System.currentTimeMillis();
        excerciseB();
        long runningTimeThreads = System.currentTimeMillis() - startWithThreads;
        System.out.println("Running time with threads: " + runningTimeThreads + " ms");



    }

    private static void excerciseA() {

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
        SaveFilter filter6 = new SaveFilter(pipe6, saveProcessedName);

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
                filter1.write("res\\loetstellen.jpg");
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    private static void excerciseB() {

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
        SaveFilter filter6 = new SaveFilter(pipe5, saveProcessedName);

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

//TODO
    //schwarze mittelpunkte entfernen
    //souts entfernen

}
