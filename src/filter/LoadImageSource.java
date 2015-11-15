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

    public LoadImageSource(String filePath) throws InvalidParameterException {
        _filePath = filePath;
    }

    public LoadImageSource(IPushPipe<FastBitmap> output)throws InvalidParameterException{
        super(output);
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
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
        return fb;
    }

}
