package pipes;

import filter.AbstractFilter;
import interfaces.IPushPipe;

import java.io.StreamCorruptedException;

/**
 * Created by manue on 31.10.2015.
 */
public class PushPipe<T> implements IPushPipe<T>{

    AbstractFilter _successorFilter;

    //gibt den Wert an den nächsten Filter weiter
    @Override
    public void write(T value) throws StreamCorruptedException {

        if(_successorFilter != null){
            _successorFilter.write(value);
        }
    }

    public void setSuccessorFilter(AbstractFilter filter){
        _successorFilter = filter;
    }
}
