package com.demo.test;

import java.lang.Runnable;

public class CommonRunnable implements Runnable
{
    Thread t;
    public CommonRunnable(String s){
        t = new Thread(this, "Demo thread");
    }
    
    public void run(){
        
    }
}
