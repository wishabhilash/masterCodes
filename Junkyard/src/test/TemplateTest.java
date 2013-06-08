package test;

public class TemplateTest<T> {
    
    /**
     * @param args
     */
    T[] items;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new TemplateTest(10);
    }
    
    public TemplateTest(int capacity){
        items = (T[]) new Object[capacity];
        System.out.println(items.length);
    }
    
}
