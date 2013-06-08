package com.demo.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import com.demo.locks.Backoff;

public class HeirarchialLock implements Lock
{
    public static HeirarchialLock hboLock = new HeirarchialLock();
    AtomicInteger state;
    private static final int LOCAL_MIN_DELAY = 3;
    private static final int LOCAL_MAX_DELAY = 7;
    private static final int REMOTE_MIN_DELAY = 3;
    private static final int REMOTE_MAX_DELAY = 7;
    private static final int FREE = -1;

    public static void main(String args[]){
        new HeirarchialLock(); 
    }
    
    public HeirarchialLock(){
        // TODO Auto-generated constructor stub
        state = new AtomicInteger(FREE);
        
    }
        
    public void unlock(){
        state.set(FREE);
    }
    
    public void lock(){
        int myCluster = Integer.parseInt(Thread.currentThread().getName());
        Backoff localBackoff = new Backoff(LOCAL_MIN_DELAY, LOCAL_MAX_DELAY);
        Backoff remoteBackoff = new Backoff(REMOTE_MIN_DELAY, REMOTE_MAX_DELAY);
        
        while(true){
            if(state.compareAndSet(FREE, myCluster)) return;
            
            int lockState = state.get();
            try{
                if(lockState == myCluster) localBackoff.backoff();
                else remoteBackoff.backoff();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    
    
    
    public boolean tryLock(long time, TimeUnit unit){
        return true;
    }

    public boolean tryLock(){
        return true;
    }
    
    public Condition newCondition(){
        return null;
    }
    
    public void lockInterruptibly(){
        
    }

}
