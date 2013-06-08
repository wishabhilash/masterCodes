package com.demo.test;


import java.lang.Runnable;

import com.demo.locks.HeirarchialLock;

public class HBOLock implements Runnable
{
    
    /**
     * @param args
     */
    
    private static int sharedVariable;
    public static String threadName;
    public static int threadCount = 0;
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
        new Thread(new HBOLock()).start();
    }
    
    public void run(){
        if(++threadCount<=4)
            Thread.currentThread().setName("1");
        else 
            Thread.currentThread().setName("2");
        
        System.out.println(Thread.currentThread().getName());
        
        
        HeirarchialLock.hboLock.lock();
        
        sharedVariable++;
        sharedVariable++;
        sharedVariable++;
        sharedVariable++;

        sharedVariable--;
        sharedVariable--;
        sharedVariable--;
        sharedVariable--;

        System.out.println("Shared Variable = " + sharedVariable);
        
        HeirarchialLock.hboLock.unlock();
    }
    
}
