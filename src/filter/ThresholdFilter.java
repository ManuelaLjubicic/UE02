package filter;

import Catalano.Core.IntRange;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.ReplaceColor;
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

        ReplaceColor colorFiltering = new ReplaceColor(new IntRange(0, 36),new IntRange(0,36),new IntRange(0,36));
        value.toRGB();
        colorFiltering.ApplyInPlace(value, 255,255,255);

        JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }
}
