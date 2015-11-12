package filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Crop;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by manue on 09.11.2015.
 */
public class ROIFilter extends AbstractFilter<FastBitmap, FastBitmap>{

    private int _x;
    private int _y;
    private int _height;
    private int _width;


    public ROIFilter(IPullPipe<FastBitmap> input, int x, int y, int width, int height) throws InvalidParameterException {
        super(input);
        init(x, y, width, height);
    }

    public ROIFilter(IPushPipe<FastBitmap> output, int x, int y, int width, int height)throws InvalidParameterException{
        super(output);
        init(x, y, width, height);
    }


    private void init(int x, int y, int width, int height){
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }

    @Override
    public void run() {
    }

    @Override
    FastBitmap processFilter(FastBitmap value) {
        Crop crop = new Crop(_y, _x, _width, _height);
        crop.ApplyInPlace(value);
        //     JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }
}
