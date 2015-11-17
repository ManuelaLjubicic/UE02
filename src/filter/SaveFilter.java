package filter;

import Catalano.Imaging.FastBitmap;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import java.security.InvalidParameterException;

/**
 * Created by manue on 15.11.2015.
 */
public class SaveFilter extends AbstractFilter<FastBitmap, FastBitmap> {

    private String _path;

    public SaveFilter(IPullPipe<FastBitmap> input, String path) throws InvalidParameterException {
        super(input);
        _path = path;
    }

    public SaveFilter(IPushPipe<FastBitmap> output, String path) throws InvalidParameterException{
        super(output);
        _path = path;
    }

    @Override
    public void run() {

    }

    @Override
    public FastBitmap processFilter(FastBitmap value) {
        value.saveAsPNG(_path);
      //  System.out.println("Picture was saved to: " + _path);
        return value;
    }
}
