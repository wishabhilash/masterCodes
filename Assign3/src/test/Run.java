package test;

import com.ass.client.*;

public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		StubGen stubGen = new StubGen();
		stubGen.run();
		
		addStub obj = new addStub();
		obj.add(4,5);
	}

}
