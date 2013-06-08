package demo;

import java.util.*;

public abstract class BaseHashSet<T>
{
    protected List<T> [] table;
    protected int setSize;
    
    public BaseHashSet(int capacity){
        setSize = 0;
        table = (List<T>[]) new List[capacity];
        for(int i = 0; i < capacity; i++)
            table[i] = new ArrayList<T>();
        
        
    }
    
    public boolean contains(T x){
        acquire(x);
        try{
            int myBucket = x.hashCode() % table.length;
            return table[myBucket].contains(x);
        }finally{
            release(x);
        }
    }
    
    public boolean add(T x){
        boolean result = false;
        acquire(x);
        try{
            int myBucket = Math.abs(x.hashCode() % table.length);
            if(!table[myBucket].contains(x)){
                table[myBucket].add(x);
                result = true;
                setSize++;
            }
        }finally{
            release(x);
        }
        
        if(policy())
            resize();
        return result;
    }
    
    public abstract boolean policy();
    
    public void acquire(T x){
        
    }

    public void release(T x){
        
    }
    
    public abstract void resize();
}
