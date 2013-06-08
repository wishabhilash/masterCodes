package quorum;

import java.util.LinkedList;
import java.util.Queue;

public class TrainQuorum
{
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        
    }
    
    
    
}


class Train{
    int name;
    boolean permission;
    Train left = null, right = null;
    
    public Train(int name, boolean permission){
        this.name = name;
        this.permission = permission;
    }
}


class BinaryTree{
    Train root = null;
    Queue<Train> myQueue = new LinkedList<Train>();
    
    public void insert(int name, boolean permission){
        if(root == null){
            root = new Train(name, permission);
            myQueue.add(root);
        }else{
            Train node = new Train(name, permission);
            Train temp = myQueue.peek();
            if(temp.left != null){
                
            }else if(temp.right != null){
                
            }else{
                
            }
        }
    }
    
}