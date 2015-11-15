package filter;

import Catalano.Imaging.FastBitmap;
import interfaces.*;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

public abstract class AbstractFilter<in, out> implements IOable<in, out>, Runnable {
    private IPullPipe<in> m_Input = null;
    private IPushPipe<out> m_Output = null;
    
    public static Object ENDING_SIGNAL = null;
    
    public AbstractFilter(){}

    public AbstractFilter(IPullPipe<in> input) throws InvalidParameterException{
        if (input == null){
            throw new InvalidParameterException("input can't be null!");
        }
        m_Input = input;
    }
    
    public AbstractFilter(IPushPipe<out> output)throws InvalidParameterException{
        if (output == null){
            throw new InvalidParameterException("output can't be null!");
        }
        m_Output = output;
    }
    
    public AbstractFilter(IPullPipe<in> input, IPushPipe<out> output)throws InvalidParameterException{
        if (input == null){
            throw new InvalidParameterException("input can't be null!");
        }else if (output == null){
            throw new InvalidParameterException("output can't be null!");
        }
        m_Input = input;
        m_Output = output;
    }

    public out read() throws StreamCorruptedException {
        return processFilter(readInput());
    }

    public void write(in value) throws StreamCorruptedException {
        writeOutput(processFilter(value));
    }

    public abstract out processFilter(in value);
    
    protected void writeOutput(out value) throws StreamCorruptedException{

            if (value == ENDING_SIGNAL) beforeSendingEndingSignal();
            m_Output.write(value);
    }
    
    protected in readInput() throws StreamCorruptedException{
        if (m_Input != null){
            return m_Input.read();
        }else{
            throw new StreamCorruptedException("input is null");
        }
    }
    
    /**
     * send a signal, that the end of the stream has been reached
     */
    protected void sendEndSignal() throws StreamCorruptedException{
        writeOutput(null);
    }
    
    /**
     * derived class may override this, if they are interested in 
     * getting informed before the ending-signal is sended
     */
    protected void beforeSendingEndingSignal() throws StreamCorruptedException {}
    
    

}
