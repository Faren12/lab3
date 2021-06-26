package cl.udp.eit.edda.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ColaIterator<T> implements Iterator<T> {

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
            nodoIteracionActual=nodoIteracionActual.getSiguiente();
            return nodo;
        }
        throw new NoSuchElementException("no quedan elementos en la cola");
    }
}