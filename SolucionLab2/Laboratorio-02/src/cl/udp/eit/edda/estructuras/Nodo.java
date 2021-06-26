package cl.udp.eit.edda.estructuras;

public class Nodo<T> {
    private T item;
    private Nodo<T> siguiente;

    public  Nodo (T item){
        this.item =item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
