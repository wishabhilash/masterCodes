package test;

import com.ass.client.*;

public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
//		// Generate stubs
		StubGen stubGen = new StubGen();
		stubGen.run();
		
		addStub addS = new addStub();
		addS.add(5, 6);
		
		subStub subS = new subStub();
		subS.sub(4, 5);
	}

}
