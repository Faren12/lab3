package cl.udp.eit.edda.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Pila<T> implements Iterable<T> {

    private Nodo<T> actual;
    private int tamanio;

    public boolean isVacia(){
        return actual==null;
    }

    public int getTamnio(){
        return tamanio;
    }

    /**
     * Push
     */
    public void poner(T item){
        Nodo<T> nodoAnterior = actual;
        actual = new Nodo<>(item);
        actual.setSiguiente(nodoAnterior);
        aumentarTamanio();
    }

    /**
     * Pop
     * @return
     */
    public T sacar(){
        if(isVacia()){
            new NoSuchElementException("La cola esta vacia");
        }
        T item = actual.getItem();
        actual = actual.getSiguiente();
        reducirTamanio();
        return item;
    }

    public T mirar(){
        if(isVacia()) {
            new NoSuchElementException("La cola esta vacia");
        }
        return actual.getItem();
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
        return new ColaIterator<>(actual);
    }
}
