package filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Grayscale;
import Catalano.Imaging.Filters.HysteresisThreshold;
import Catalano.Imaging.Filters.Threshold;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by manue on 09.11.2015.
 */
public class ThresholdFilter extends AbstractFilter<FastBitmap, FastBitmap> {

    int _thresholdValue;

    public ThresholdFilter(IPullPipe<FastBitmap> input, int thresholdValue) throws InvalidParameterException{
        super(input);
        _thresholdValue = thresholdValue;
    }

    public ThresholdFilter(IPushPipe<FastBitmap> output, int thresholdValue) throws InvalidParameterException{
        super(output);
        _thresholdValue = thresholdValue;
    }


    @Override
    public FastBitmap read() throws StreamCorruptedException {
        return null;
    }

    @Override
    public void run() {

    }

    @Override
    public void write(FastBitmap value) throws StreamCorruptedException {
        writeOutput(createThreshold(value));
    }

    private FastBitmap createThreshold(FastBitmap value){
        Grayscale g = new Grayscale();
        g.applyInPlace(value);
//        Threshold t = new Threshold(_thresholdValue, true);
//        t.applyInPlace(value);

        HysteresisThreshold th = new HysteresisThreshold(0,30);
        th.applyInPlace(value);

        //JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }
}
