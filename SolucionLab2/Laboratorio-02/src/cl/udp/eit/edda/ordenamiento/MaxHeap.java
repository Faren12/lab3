package cl.udp.eit.edda.ordenamiento;

import cl.udp.eit.edda.estructuras.Cola;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable> implements Iterable<T> {
    private List<T> nodes;
    public static int numeroComparaciones;

    public MaxHeap(){
        nodes= new ArrayList<>();
        nodes.add(null);
    }

    public MaxHeap(Cola<T> values){
        this();
        for(T t:values){
            this.insert(t);
        }
    }
    public T max(){
        if(isEmpty()) throw new NoSuchElementException("Heap underflow");

        return nodes.get(1);
    }

    public T deleteMax(){
        T max= max();
        exchange(1, size());
        nodes.remove(size());
        sink(1);
        return max;
    }

    public void insert(T node){
        nodes.add(node);
        swim(size());
    }

    public int size(){
        return nodes.size()-1;
    }
    public boolean isEmpty(){
        return size()==0;
    }

    private void exchange(int i, int j){
        T node= nodes.get(i);
        nodes.set(i,nodes.get(j));
        nodes.set(j,node);

    }

    private void swim(int position){
        while (position>1 && less(parent(position),position)){
            exchange(parent(position), position);
            position = parent(position);
        }
    }

    private void sink(int position){
        int child;

        while ( (child= leftChild(position)) <=size()){
            int rightChild=rightChild(position);
            if( child<size() && less(child,rightChild)){
                child = rightChild;
            }
            if(!less(position,child)){
                break;
            }
            exchange(position,child);
            position=child;
        }
    }

    private boolean less(int i, int j){
        numeroComparaciones++;
        return nodes.get(i).compareTo(nodes.get(j)) < 0;
    }

    private int parent(int position){
        return position/2;
    }
    private int leftChild(int position){
        return position*2;
    }
    private int rightChild(int position){
        return position*2 +1;
    }

    @Override
    public Iterator<T> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<T> {

        private MaxHeap<T> copy;

        public HeapIterator(){
            copy = new MaxHeap<>();
            for(int i=1; i<nodes.size();i++){
                copy.insert(nodes.get(i));
            }

        }
        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return copy.deleteMax();
        }
    }

    public Cola<T> getSorted(){
        Cola<T> cola = new Cola<>();
        for(T t:this){
            cola.encolar(t);
        }
        return cola;
    }

    public static <T extends Comparable> Cola<T> sort(Cola<T> values){
        return new MaxHeap<T>(values).getSorted();
    }



    /**
     * Removes and returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public T delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        T min = nodes.get(1);
        exchange(1, size()-1);
        sink(1);
        nodes.remove(nodes.size()-1);


        return min;
    }
}
