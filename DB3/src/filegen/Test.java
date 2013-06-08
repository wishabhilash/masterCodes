package filegen;

import java.io.*;
import java.util.*;

public class Test {

	/**
	 * @param args
	 */
	private static ArrayList<Integer> keys = new ArrayList<Integer>();
	private static Stack<NodeBox> nodeStack = new Stack();
	private static NodeBox node, splitNode = null;
	private static int nodeSize = 3;
	private static NodeBox root = new NodeBox();
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		// TODO Auto-generated method stub
		
		
		
	}
	
	public static boolean put(Integer carryNum){
		if(contains(carryNum, root, false)) return false;
		Integer index;
		NodeBox node = new NodeBox();
		while(nodeStack.size() > 0){
			node = nodeStack.pop();
			if(node.isLeaf()){
//				System.out.println("Leaf");
				node.add(carryNum);
			}
			else{
//				System.out.println("not Leaf");
				if(splitNode != null){
					index = node.add(carryNum);
					if(index < (node.size()-1))	node.addPointer(index+1, splitNode);
					else node.addPointer(splitNode);
				}
				else break;
			}

			if(node.size() > nodeSize){
				splitNode = node.splitNode();
				if(node.isLeaf()){
					carryNum = splitNode.getFirst();
				}
				else carryNum = splitNode.removeFirst();
			}else splitNode = null;
		}
		if(splitNode != null){
			root = new NodeBox();
			root.add(carryNum);
			root.addPointer(node);
			root.addPointer(splitNode);
		}
		nodeStack.clear();
		return true;
	}
	
	public static void readTree(NodeBox root){
		ArrayList<Integer> keys = root.getKeyArray();
		Iterator<Integer> itre = keys.iterator();
		while(itre.hasNext()) System.out.println(itre.next());
		System.out.println("\n");
		ArrayList<NodeBox> pointers = root.getPointerArray();
		Iterator<NodeBox> itr = pointers.iterator();
		while(itr.hasNext()){
			readTree(itr.next());
		}
		return;
	}

	public static boolean contains(Integer n, NodeBox root, boolean disp){
		nodeStack.push(root);
//		if(disp) root.display();
		NodeBox nextNode;
		boolean retBool = false;
		if(!root.isLeaf()){
			Integer m = root.search(n);
//			if(disp) System.out.println("m: " + m);
			if(m == null){
				m = root.size()-1;
				nextNode = root.getGreaterPointer(m); 
			}else nextNode = root.getSmallerPointer(m);

			if(nextNode != null){
				if(disp) retBool = contains(n, nextNode, true);
				else retBool = contains(n, nextNode, false);
			}

		}else retBool = root.containsKey(n);
		return retBool;
	}
}

class NodeBox{
	private int nodeSize = 3;
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	private ArrayList<NodeBox> pointers = new ArrayList<NodeBox>();
	
	public NodeBox(){
		
	}
	
	public NodeBox(ArrayList<Integer> keys, ArrayList<NodeBox> pointers){
		this.keys = keys;
		this.pointers = pointers;
	}
	
	public ArrayList<Integer> getKeyArray(){
		return this.keys;
	}

	public ArrayList<NodeBox> getPointerArray(){
		return this.pointers;
	}
	
	public NodeBox getGreaterPointer(Integer n){
		return this.pointers.get(n+1);
	}
	
	public NodeBox getSmallerPointer(Integer n){
		return this.pointers.get(n);
	}
	
	public Integer getKey(Integer index){
		return this.keys.get(index);
	}
	
	//	Returns the first element in the list
	public Integer getFirst(){
		return this.keys.get(0);
	}
	
	//	Removes and returns the first element in the list
	public Integer removeFirst(){
		return this.keys.remove(0);
	}
	
	public boolean containsKey(Integer n){
		return this.keys.contains(n);
	}
	
	public Integer add(Integer n){
		int i = 0;
		Iterator<Integer> itr = this.keys.iterator();
		while(itr.hasNext()){
			int m = itr.next();
			if(m > n) break;
			i++;
		}
		if(i == this.keys.size()) this.keys.add(n);
		else this.keys.add(i, n);
		return i;
	}
	
	public void display(){
		Iterator<Integer> itr = this.keys.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
	}
	
	public void add(Integer index, Integer n){
		this.keys.add(index,n);
	}
	
	public void addPointer(NodeBox N){
		this.pointers.add(N);
	}
	
	public void addPointer(Integer index, NodeBox N){
		this.pointers.add(index, N);
	}
	//	Return the size of node
	public Integer size(){
		return keys.size();
	}
	
	//	Return the newly created Node after splitting;
	public NodeBox splitNode(){
		ArrayList<Integer> key = new ArrayList<Integer>(),
				intrKey = new ArrayList<Integer>();
		ArrayList<NodeBox> pointer = new ArrayList<NodeBox>(),
				intrPointer = new ArrayList<NodeBox>();
		int mid = this.size()/2;
		for(int i = 0; i < mid; i++){
			intrKey.add(this.keys.get(i));
			if(!this.isLeaf()) intrPointer.add(this.pointers.get(i));
		}
		if(!this.isLeaf()) intrPointer.add(this.pointers.get(mid));

		for(int i = mid; i < this.size(); i++){
			key.add(this.keys.get(i));
			if(!this.isLeaf()) pointer.add(this.pointers.get(i+1));
		}
		this.keys = intrKey;
		this.pointers = intrPointer;

		return new NodeBox(key, pointer);
	}

	
	
	//	Check if leaf of not
	public boolean isLeaf(){
		if(pointers.size() == 0) return true;
		return false;
	}

	
	
	
	//	Search the node for element	
	public Integer search(Integer n){
//		System.out.println("One");
		if(keys.size() == 0 || n >= keys.get(keys.size()-1)) return null;
		
//		System.out.println("two");
		if(n < keys.get(0)) return 0;
		
		if(keys.size() < 2){
			if(n > keys.get(0)) return null;
			else return 0;
		}
//		System.out.println("binary");
		int beg = 0, end = keys.size()-1, mid = (beg+end)/2, prevMid = 0;
		while(end > beg){
			if(mid == prevMid) return end;
			if(n > keys.get(mid)) beg = mid;
			else if(n < keys.get(mid))	end = mid;
			else return mid;
			prevMid = mid;
			mid = (beg+end)/2;
//			System.out.println(prevMid);
		}
		return null;
	}
}
