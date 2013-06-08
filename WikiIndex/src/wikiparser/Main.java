package wikiparser;

import java.io.*;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Main {
	private static String rootPath = "/media/SOUL/wikiFile/";
	
	private static String directoryPath = rootPath + "test2";
	private static String indexDirectoryPath = rootPath + "test";
	private static String frdIndexDirectoryPath = rootPath + "frdIndexSplit";
	private static String outputFileName = rootPath + "indices/main.index";
	private static String denseIndexName = rootPath + "indices/dense.index";
	private static String sparseIndexName = rootPath + "indices/sparse.index";
	private static String frdSparseIndexName = rootPath + "indices/frdsparse.index";
	private static String frdIndexName = rootPath + "indices/frd.index";
	
	private static Integer period = 100;
	private static Integer frdPeriod = 10;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		// TODO Auto-generated method stub
		
		//	Create the directories
		createDirs();
		
		OutputStreamWriter logger = new OutputStreamWriter(new FileOutputStream("/media/SOUL/wikiFile/log"));
		
		File dir = new File(directoryPath);
		String[] fileNames = dir.list();
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLParser handler = new XMLParser();
		long t1=System.currentTimeMillis();
		for(int i = 0; i < fileNames.length; i++){
			if(!fileNames[i].startsWith(".")){
				System.out.println(fileNames[i]);
				//long t1=System.currentTimeMillis();
				parser.parse(directoryPath +"/"+fileNames[i], handler);
				handler.createIndex(indexDirectoryPath + "/" + fileNames[i] + ".index");
				handler.createForwardIndex(frdIndexDirectoryPath + "/frd_" + fileNames[i] + ".index");
				//System.out.println(System.currentTimeMillis() - t1);
				handler.flushData();
			}
		}
//		parser.parse(directoryPath + "/xaaaa", handler);
//		//parser.parse("/home/wish/exm.xml", handler);
//		handler.createIndex(indexDirectoryPath + "/xaaao.index");

		System.out.println("Merging Indexes...");
		MergeFiles merge = new MergeFiles(indexDirectoryPath, outputFileName);

		System.out.println("Creating Forward Index...");
		logger.write("Creating Forward Index...");
		MergeFilesFrd merge2 = new MergeFilesFrd(frdIndexDirectoryPath, frdIndexName);

		System.out.println("Creating Dense Index...");
		logger.write("Creating Dense Index...");
		secIndex("dense",outputFileName, denseIndexName, period);
		
		System.out.println("Creating Sparse Index...");
		logger.write("Creating Sparse Index...");
		secIndex("sparse", denseIndexName, sparseIndexName, period);
		
		System.out.println("Creating Forward Sparse Index...");
		logger.write("Creating Forward Sparse Index...");
		secIndex("sparse", frdIndexName, frdSparseIndexName, frdPeriod);

		System.out.println(System.currentTimeMillis() - t1);
		logger.close();
	}
	
//	public static void secIndex(String indexName, String first, String second) throws FileNotFoundException, IOException{
////		DataInputStream in = new DataInputStream(new FileInputStream(first));
////		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//		RandomAccessFile randAcc = new RandomAccessFile(first, "r");
//		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(second));
//		String line;
////		long byteCount = 0;
//		long pos = randAcc.getFilePointer();
//		Integer lineCount = 0;
//		while((line = randAcc.readLine()) != null){
//			if(lineCount % period == 0){
//				StringTokenizer token = new StringTokenizer(line, "#");
//				if(indexName.compareTo("dense") == 0){
////					out.write(extractTerm(token.nextToken()) + "#" + Long.toHexString(byteCount) + "\n");
//					out.write(extractTerm(token.nextToken()) + "#" + Long.toHexString(pos) + "\n");
//				}
//				else{
////					out.write(token.nextToken() + "#" + Long.toHexString(++byteCount) + "\n");
//					out.write(token.nextToken() + "#" + Long.toHexString(pos) + "\n");
//				}
//			}
////			byteCount += line.length() + 1;
//			pos = randAcc.getFilePointer();
//			if(indexName.compareToIgnoreCase("sparse") == 0) lineCount++;
//		}
////		in.close();
//		randAcc.close();
//		out.close();
//	}
	
	public static void secIndex(String indexName, String first, String second, int interval) throws FileNotFoundException, IOException{
		DataInputStream in = new DataInputStream(new FileInputStream(first));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(second));
		String line;
		long byteCount = 0;
		Integer lineCount = 0;
		while((line = bufferedReader.readLine()) != null){
			if(lineCount % interval == 0){
				StringTokenizer token = new StringTokenizer(line, "#");
				if(indexName.compareTo("dense") == 0){
					out.write(extractTerm(token.nextToken()) + "#" + Long.toHexString(byteCount) + "\n");
				}
				else{
					out.write(token.nextToken() + "#" + Long.toHexString(byteCount) + "\n");
				}
			}
			byteCount += line.getBytes().length + 1;
			if(indexName.compareToIgnoreCase("sparse") == 0) lineCount++;
		}
		in.close();
		out.close();
	}
	
	private static String extractTerm(String term){
		StringBuilder num = new StringBuilder("");
		int len = term.length();
		while(true){
			len--;
			try{
				Integer.parseInt(term.substring(len));
				num.append(term.charAt(len));
			}catch(NumberFormatException e){break;}
		}
		return term.substring(0, len+1);
	}
	
	public static void createDirs(){
		boolean success;
		File file = new File(directoryPath);
		if(!file.exists()){
			success = (new File(directoryPath)).mkdir();
			if(success) System.out.println(directoryPath + " created!");
		}
		
		file = new File(indexDirectoryPath);
		if(!file.exists()){
			success = (new File(indexDirectoryPath)).mkdir();
			if(success) System.out.println(indexDirectoryPath + " created!");
		}
		
		file = new File(frdIndexDirectoryPath);
		if(!file.exists()){
			success = (new File(frdIndexDirectoryPath)).mkdir();
			if(success) System.out.println(frdIndexDirectoryPath + " created!");
		}
	}
}
