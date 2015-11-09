package pipes;

import filter.AbstractFilter;
import interfaces.*;

import java.io.StreamCorruptedException;

/**
 * Created by manue on 31.10.2015.
 */
public class PullPipe<T> implements IPullPipe<T> {

    AbstractFilter _predecessorFilter;


    @Override
    public T read() throws StreamCorruptedException {

       return  (T)_predecessorFilter.read();
    }

    public AbstractFilter getPredecessorFilter() {
        return _predecessorFilter;
    }

    public void setPredecessorFilter(AbstractFilter _predecessorFilter) {
        this._predecessorFilter = _predecessorFilter;
    }
}
