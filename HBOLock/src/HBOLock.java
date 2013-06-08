import java.lang.management.ThreadInfo;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.atomic.*;

public class HBOLock implements Lock
{
	private static final int LOCAL_MIN_DELAY = 10;
	private static final int LOCAL_MAX_DELAY = 100;
	private static final int REMOTE_MIN_DELAY = 10;
	private static final int REMOTE_MAX_DELAY = 1000;
	private static final int FREE = -1;
	AtomicInteger state;
	public HBOLock()
	{
		state = new AtomicInteger(FREE);
	}
	
	public void lock()
	{
		int myCluster = Integer.parseInt(Thread.currentThread().getName());
		
		
		Backoff localBackoff = new Backoff(LOCAL_MIN_DELAY, LOCAL_MAX_DELAY);
		Backoff remoteBackoff = new Backoff(REMOTE_MIN_DELAY, REMOTE_MAX_DELAY);
		while (true)
		{
			if (state.compareAndSet(FREE, myCluster))
			return;
			
			int lockState = state.get();
			if (lockState == myCluster)
				try {
					localBackoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					remoteBackoff.backoff();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void unlock()
	{
		state.set(FREE);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
}
