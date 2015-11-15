package filter;

import Catalano.Imaging.FastBitmap;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by manue on 15.11.2015.
 */
public class PrintSink extends AbstractFilter<Point[], Object>{

    private String _fileName;

    public PrintSink(IPullPipe<Point[]> input, String fileName) throws InvalidParameterException {
        super(input);
        _fileName = fileName;
    }

    public PrintSink(IPushPipe<Object> output, String fileName) throws InvalidParameterException {
        super(output);
        _fileName = fileName;
    }

    @Override
    public void run() {

    }

    @Override
    public Object processFilter(Point[] value) {
        try {
            FileWriter fw = new FileWriter(_fileName);

            for(int i = 0; i < value.length; i++){
                fw.write( i + 1 + "\tPoint: x: " + value[i].x + " Y: " + value[i].y + "\r\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
