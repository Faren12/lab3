package cl.udp.eit.edda.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Cola<T> implements Iterable<T>{
    private Nodo<T> nodoActual;
    private Nodo<T> nodoSiguiente;
    private int tamanio=0;

    public boolean isVacia(){
        return nodoActual ==null;
    }

    public int getTamanio(){
        return tamanio;
    }

    /** Peek
     *  Si la cola no está vacia entonces devuelve el elemento mas antiguo
     * @return devuelve el elemento mas antiguo en la cola
     */
    public T mirar(){
        if(isVacia()){
            new NoSuchElementException("La cola esta vacia");
        }
        return nodoActual.getItem();
    }

    public void encolar(T item){
        Nodo<T> penultimo = nodoSiguiente;
        nodoSiguiente = new Nodo<>(item);
        if(isVacia()){
            nodoActual = nodoSiguiente;
        }else {
            penultimo.setSiguiente(nodoSiguiente);

        }
        aumentarTamanio();
    }

    /**
     * Dequeue
     * @return
     */
    public T sacar(){
        if(isVacia()){
            new NoSuchElementException("La cola esta vacia");
        }
        if(getTamanio()<1){
            System.out.println(isVacia());
        }
        T item = nodoActual.getItem();
        nodoActual = nodoActual.getSiguiente();
        reducirTamanio();
        if(isVacia()){
            nodoSiguiente =null;
        }
        return item;
    }

    public String toString(){
        StringBuilder todosLosItemsString = new StringBuilder();
        for(T item: this){
            todosLosItemsString.append(item).append(",");
        }
        return todosLosItemsString.toString();
    }
    private void aumentarTamanio(){
        tamanio++;
    }
    private void reducirTamanio(){
        tamanio--;
    }



    @Override
    public Iterator<T> iterator() {
        return new ColaIterator(nodoActual);
    }

    private class ColaIterator implements Iterator<T>{

        private Nodo<T> nodoIteracionActual;

        public ColaIterator(Nodo<T> nodoInicial) {
            nodoIteracionActual=nodoInicial;
        }

        @Override
        public boolean hasNext() {
            return nodoIteracionActual!=null;
        }

        @Override
        public T next() {
            if(hasNext()){
                T nodo=this.nodoIteracionActual.getItem();
                nodoIteracionActual=this.nodoIteracionActual.getSiguiente();
                return nodo;
            }
            throw new NoSuchElementException("no quedan elementos en la cola");
        }
    }

    public Cola<T> clone(){
        Cola<T> cola = new Cola<>();
        for(T t:this){
            cola.encolar(t);
        }
        return cola;
    }



}
