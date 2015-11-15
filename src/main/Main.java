package main;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Opening;
import filter.*;
import pipes.PushPipe;

import java.awt.*;
import java.io.File;
import java.io.StreamCorruptedException;

/**
 * Created by manue on 09.11.2015.
 */
public class Main {

    public static void main(String[] args) {

        FastBitmap fb = new FastBitmap("res\\loetstellen.jpg");
        int width = fb.getWidth();
        int heigth = fb.getHeight();
        String saveProcessedName = "res\\processedFoto.png";
        String printFileName = "res\\printProcessedFoto.txt";



        PushPipe<FastBitmap> pipe1 = new PushPipe<>();
        LoadImageSource filter1 = new LoadImageSource(pipe1);

        PushPipe<FastBitmap> pipe2 = new PushPipe<>();
        ROIFilter filter2 = new ROIFilter(pipe2,0, 55, width, 80);

        PushPipe<FastBitmap> pipe3 = new PushPipe<>();
        ThresholdFilter filter3  = new ThresholdFilter(pipe3);

        PushPipe<FastBitmap> pipe4 = new PushPipe<>();
        MedianFilter filter4 = new MedianFilter(pipe4, 3);

        PushPipe<FastBitmap> pipe5 = new PushPipe<>();
        OpeningFilter filter5 = new OpeningFilter(pipe5, 5, 1);

        PushPipe<FastBitmap> pipe6 = new PushPipe<>();
        SaveFilter filter6 = new SaveFilter(pipe6, saveProcessedName);

        PushPipe<Point[]> pipe7 = new PushPipe<>();
        CalcCentroidsFilter filter7 = new CalcCentroidsFilter(pipe7);

        PushPipe<Object> pipe8 = new PushPipe<>();
        PrintSink filter8 = new PrintSink(pipe8, printFileName);

        pipe1.setSuccessorFilter(filter2);
        pipe2.setSuccessorFilter(filter3);
        pipe3.setSuccessorFilter(filter4);
        pipe4.setSuccessorFilter(filter5);
        pipe5.setSuccessorFilter(filter6);
        pipe6.setSuccessorFilter(filter7);
        pipe7.setSuccessorFilter(filter8);

        try {
            filter1.write("res\\loetstellen.jpg");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            System.out.println("scheiﬂe");
        }
    }


}
