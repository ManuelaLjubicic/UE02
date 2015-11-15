package filter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Opening;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;
import java.security.InvalidParameterException;

/**
 * Created by manue on 12.11.2015.
 */
public class OpeningFilter extends AbstractFilter<FastBitmap, FastBitmap> {

    private int _maskSize;
    private int _iterations;

    //TODO ask Karin???
//    private int _circleMask[][] = {
//            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
//            { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
//            { 0, 0, 0, 0 ,1, 1, 1, 1, 1, 0, 0, 0, 0},
//            { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
//            { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
//            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//            { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
//            { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
//            { 0, 0, 0, 0 ,1, 1, 1, 1, 1, 0, 0, 0, 0},
//            { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
//            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
//    };

    public OpeningFilter(IPullPipe<FastBitmap> input, int maskSize, int iterations) throws InvalidParameterException {
        super(input);
        init(maskSize, iterations);
    }

    public OpeningFilter(IPushPipe<FastBitmap> output, int maskSize, int iterations) throws InvalidParameterException{
        super(output);
        init(maskSize, iterations);
    }

    private void init(int maskSize, int iterations){
        _maskSize = maskSize;
        _iterations = iterations;
    }

    @Override
    public void run() {

    }

    @Override
    public FastBitmap processFilter(FastBitmap value) {
        Opening o = new Opening(_maskSize);
      //  o = new Opening(_circleMask);
        for(int i = 0; i < _iterations; i++){
            o.applyInPlace(value);
        }
       // JOptionPane.showMessageDialog(null, value.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return value;
    }
}
