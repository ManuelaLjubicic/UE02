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

    public ThresholdFilter(IPullPipe<FastBitmap> input) throws InvalidParameterException{
        super(input);

    }

    public ThresholdFilter(IPushPipe<FastBitmap> output) throws InvalidParameterException{
        super(output);
    }

    @Override
    public void run() {
    }

    @Override
    FastBitmap processFilter(FastBitmap value) {
        ReplaceColor rc = new ReplaceColor(new IntRange(0, 40),new IntRange(0,40),new IntRange(0,40));
        value.toRGB();
        rc.ApplyInPlace(value, 255, 255, 255);

        //     JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }

}
