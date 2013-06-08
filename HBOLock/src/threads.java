
public class threads implements Runnable
{
	public static String threadName;
	public static int threadCount = 0;
	//public static int sharedVariable = 0;
	//HBOLock hboLock = new HBOLock();
	
   public static void main(String args[]) throws Throwable  
   {
      threads obj1 = new threads();
      threads obj2 = new threads();
      threads obj3 = new threads();
      threads obj4 = new threads();
      threads obj5 = new threads();
      threads obj6 = new threads();
      threads obj7 = new threads();
      threads obj8 = new threads();
      
      new Thread(obj1).start();
      new Thread(obj2).start();
      new Thread(obj3).start();
      new Thread(obj4).start();
      new Thread(obj5).start();
      new Thread(obj6).start();
      new Thread(obj7).start();
      new Thread(obj8).start();
      
   }

   public void run()
   {
	   if(++threadCount<=4)
		   Thread.currentThread().setName("1");
	   else 
		   Thread.currentThread().setName("2");
	   
	   System.out.println(Thread.currentThread().getName());
	   
	   
	   
	   //acquire the lock to access the critical region
	   
	   hierarchialLock.hboLock.lock();
	   
	   /* Critical section starts here */
	   
	   criticalSection.sharedVariable++;
	   criticalSection.sharedVariable++;
	   criticalSection.sharedVariable++;
	   criticalSection.sharedVariable++;
	   
	   criticalSection.sharedVariable--;
	   criticalSection.sharedVariable--;
	   criticalSection.sharedVariable--;
	   criticalSection.sharedVariable--;
	   System.out.println("value of Shared Variable = "+criticalSection.sharedVariable);
	   
	   /* Critical section ends here */
	   
	   // release the lock now.
	   hierarchialLock.hboLock.unlock();
	   
	   
	   
   }
}
