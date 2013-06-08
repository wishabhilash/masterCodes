package demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class StrippedHashSet<T> extends BaseHashSet<T>
{
    final ReentrantLock[] locks;
    
    public StrippedHashSet(int capacity){
        super(capacity);
        locks = new ReentrantLock[capacity];
        
        for(int i = 0; i < locks.length; i++)
            locks[i] = new ReentrantLock();
    }
    
    public final void acquire(T x){
        locks[x.hashCode() % locks.length].lock();
    }
    
    public final void release(T x){
        locks[x.hashCode() % locks.length].unlock();
    }
    
    public void resize(){
        int oldCapacity = table.length;
        for(Lock lock : locks)
            lock.lock();
        
        try{
            if(oldCapacity != table.length) return;
            
            int newCapacity = 2 * oldCapacity;
            
            List<T>[] oldTable = table;
            
            table = (List<T>[]) new List[newCapacity];
            
            for(int i = 0; i < newCapacity; i++)
                table[i] = new ArrayList<T>();
            
            for(List<T> bucket : oldTable)
                for(T x : bucket)
                    table[x.hashCode() % table.length].add(x);
        }finally{
            for(Lock lock : locks)
                lock.unlock();
        }
    }
    
    public boolean policy(){
        return setSize/table.length > 4;
    }
}
