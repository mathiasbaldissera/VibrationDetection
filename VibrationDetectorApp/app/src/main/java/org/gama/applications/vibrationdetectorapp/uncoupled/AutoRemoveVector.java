package org.gama.applications.vibrationdetectorapp.uncoupled;


import java.util.Vector;

@SuppressWarnings("serial")
public class AutoRemoveVector<E> extends Vector<E>{

    private final int maxSize;
    private int realSize=0;
    public AutoRemoveVector(int maxSize){
        this.maxSize=maxSize;
    }

    @Override
    public synchronized boolean add(E e) {
        if (super.size()>maxSize)
            remove(0);
        realSize = getRealSize() + 1;
        return super.add(e);
    }
    
   

    public int getRealSize() {
        return realSize;
    }
}
