package filter;

import Catalano.Imaging.FastBitmap;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by manue on 09.11.2015.
 */
public class LoadImageSource extends AbstractFilter<String, FastBitmap> {

    private String _filePath;
    private int _maxIterations;

    public LoadImageSource(String filePath) throws InvalidParameterException {
        _filePath = filePath;
    }

    public LoadImageSource(String filePath, int maxIterations) throws InvalidParameterException {
        _filePath = filePath;
        _maxIterations = maxIterations;
    }

    public LoadImageSource(IPushPipe<FastBitmap> output)throws InvalidParameterException{
        super(output);
    }

    public LoadImageSource(IPushPipe<FastBitmap> output, int maxIterations)throws InvalidParameterException{

        super(output);
        _maxIterations= maxIterations;
    }

    @Override
    public FastBitmap read() throws StreamCorruptedException {
         return processFilter(_filePath);
    }

    @Override
    public void run() {

    }

    @Override
    public FastBitmap processFilter(String value) {
        FastBitmap fb = new FastBitmap(value);
        if (_maxIterations <=1) {
            JOptionPane.showMessageDialog(null, fb.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        }
        return fb;
    }

}
