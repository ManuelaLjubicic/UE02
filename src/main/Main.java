package main;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Opening;
import filter.*;
import interfaces.IPushPipe;
import pipes.PushPipe;

import java.io.StreamCorruptedException;

/**
 * Created by manue on 09.11.2015.
 */
public class Main {

    public static void main(String[] args) {

        FastBitmap fb = new FastBitmap("res\\loetstellen.jpg");
        int width = fb.getWidth();
        int heigth = fb.getHeight();



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

        pipe1.setSuccessorFilter(filter2);
        pipe2.setSuccessorFilter(filter3);
        pipe3.setSuccessorFilter(filter4);
        pipe4.setSuccessorFilter(filter5);


        try {
            filter1.write("res\\loetstellen.jpg");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            System.out.println("scheiﬂe");
        }
    }


}
