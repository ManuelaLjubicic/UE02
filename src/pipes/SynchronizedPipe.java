package pipes;

import interfaces.*;

import java.io.StreamCorruptedException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by manue on 17.11.2015.
 */
public class SynchronizedPipe<T> implements IPullPipe<T>, IPushPipe<T> {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock lockQueue = lock.writeLock();
    private final LinkedList<T> _queue = new LinkedList<>();

    @Override
    public T read() throws StreamCorruptedException {
        lockQueue.lock();
        T temp = null;
        if(!_queue.isEmpty()){
            temp = _queue.remove(0);
        }
        lockQueue.unlock();
        return temp;
    }

    @Override
    public void write(T value) throws StreamCorruptedException {
        lockQueue.lock();
        _queue.add(value);
        lockQueue.unlock();
    }
}
