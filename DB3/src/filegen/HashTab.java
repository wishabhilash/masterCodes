package filegen;

import java.util.*;

public class HashTab {
	private Integer primeNum = 156779;
	private ArrayList<LinkedList<Integer>> hashMap = new ArrayList<LinkedList<Integer>>();
	
	public HashTab(){
		// TODO Auto-generated constructor stub
		for(int i = 0; i < primeNum; i++) hashMap.add(null);
	}
	
	public boolean put(Integer t){
		boolean contain = contains(t);
		if(contain)	return false;
		Integer index = t % primeNum;
		LinkedList<Integer> temp = hashMap.get(index);
		if(temp == null) temp = new LinkedList<Integer>();
		temp.add(t);
		hashMap.set(index, temp);
		return true;
	}
	
	public boolean contains(Integer n){
		Integer index = n % primeNum;
		LinkedList<Integer> t = hashMap.get(index);
		if(t != null){
			Iterator itr = t.iterator();
			while(itr.hasNext()){
				Integer ind = (int)itr.next();
//				System.out.println(mapData.get(ind) + " " + n);
				if(ind.equals(n)) return true;
			}
		}
		return false;
	}
}
