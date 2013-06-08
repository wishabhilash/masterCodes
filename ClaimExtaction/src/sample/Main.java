package sample;

import java.io.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("/media/FUN/data_FILES/formal_topics");
		String[] fileList = file.list();
		
		FilterClaim m = new FilterClaim();
		for(int i = 0; i < fileList.length; i++){
			m.getClaimWords("/media/FUN/data_FILES/formal_topics/" + fileList[i]);
		}
		m.destructor();

	}

}
