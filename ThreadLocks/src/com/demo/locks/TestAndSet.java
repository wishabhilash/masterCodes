package com.demo.locks;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;

public class TestAndSet implements Lock
{
    AtomicBoolean state = new AtomicBoolean(false);
    public void lock(){
        while(state.getAndSet(true));
    }
    
    public void unlock(){
        state.set(false);
    }
    
    public void lockInterruptibly(){
        
    }
    
    public Condition newCondition(){
        return new Condition()
        {
            
            @Override
            public void signalAll()
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void signal()
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public boolean awaitUntil(Date deadline) throws InterruptedException
            {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public void awaitUninterruptibly()
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public long awaitNanos(long nanosTimeout) throws InterruptedException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public boolean await(long time, TimeUnit unit) throws InterruptedException
            {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public void await() throws InterruptedException
            {
                // TODO Auto-generated method stub
                
            }
        };
    }
    
    public boolean tryLock(){
        return true;
    }
    
    public boolean tryLock(long m, TimeUnit t){
        return true;
    }
}
