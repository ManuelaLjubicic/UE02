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
    static int height = fb.getHeight();



    public static void main(String[] args) {
        if ((args.length < 4)||((args.length >4) && (args.length<9))) {
            System.out.println("Parameter fehlt");
        } else {
            String filePathSource = "res\\" + args[1] + ".jpg";
            String processedImAGE = "res\\" + args[2] + ".jpg";
            String printFileName = "res\\" + args[3] + ".txt";
            int maxIterations = 1;
            int startValue = 6;
            int incrementX = 65;
            int setValueY = 25;
            int toleranceX = 5;
            int toleranceY = 5;
            if (args.length > 4){
                startValue = Integer.parseInt(args[4]);
                incrementX = Integer.parseInt(args[5]);
                setValueY = Integer.parseInt(args[6]);
                toleranceX = Integer.parseInt(args[7]);
                toleranceY = Integer.parseInt(args[8]);
            }
            ImageProcessing ip = new ImageProcessing();


            switch (args[0]) {

                case "push":
                    ip.exerciseA(width, filePathSource, processedImAGE, printFileName, startValue, setValueY,
                            incrementX, toleranceX, toleranceY, maxIterations);
                    break;

                case "pull":
                    ip.exerciseAPull(width, filePathSource, processedImAGE, printFileName, startValue, setValueY,
                            incrementX, toleranceX, toleranceY, maxIterations);
                    break;

                case "threads":
                    maxIterations = 50;
                    long startTime = new Date().getTime();
                        ip.exerciseA(width, filePathSource, processedImAGE, printFileName, startValue, setValueY,
                                incrementX, toleranceX, toleranceY, maxIterations);
                    long endTime = new Date().getTime();
                    System.out.println("Laufzeit ohne Threads: " + (endTime - startTime) + "ms");
                    startTime = new Date().getTime();
                        ip.exerciseB(width, filePathSource, processedImAGE, printFileName, startValue, setValueY,
                                incrementX, toleranceX, toleranceY, maxIterations);
                    endTime = new Date().getTime();
                    System.out.println("Laufzeit mit Threads: " + (endTime-startTime) + "ms");
                    break;

                default:
                    System.out.println("falscher Parameter");
            }
        }
    }
}
