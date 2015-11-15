package filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Median;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by manue on 12.11.2015.
 */
public class MedianFilter extends AbstractFilter<FastBitmap, FastBitmap> {

    private int _radius;

    public MedianFilter(IPullPipe<FastBitmap> input, int radius) throws InvalidParameterException {
        super(input);
        _radius = radius;

    }

    public MedianFilter(IPushPipe<FastBitmap> output, int radius) throws InvalidParameterException{
        super(output);
        _radius = radius;
    }

    @Override
    public void run() {
    }

    @Override
    public FastBitmap processFilter(FastBitmap value){
        Median median = new Median(_radius);
        median.applyInPlace(value);
//        JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }
}
