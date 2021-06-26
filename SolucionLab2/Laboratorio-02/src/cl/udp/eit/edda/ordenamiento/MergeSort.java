package cl.udp.eit.edda.ordenamiento;
import cl.udp.eit.edda.estructuras.Cola;

import java.util.Comparator;

public class MergeSort<T extends Comparator<T>> {

    public static int numeroComparaciones;
    public static int numeroMezclas;

    public static <T extends Comparable<T> > Cola<T> mergeSort(Cola<T> colaAOrdenar){
        numeroMezclas++;
        if(colaAOrdenar.getTamanio()<1){
            throw new RuntimeException("No se puede ordenar una cola vacia");
        }
        if(colaAOrdenar.getTamanio()==1){
            Cola<T> colaOrdenada= new Cola<>();
            colaOrdenada.encolar(colaAOrdenar.sacar());
            return colaOrdenada;
        }else{
            int mitad = colaAOrdenar.getTamanio()/2;
            Cola<T> colaLadoDerecho = new Cola<>();
            for(int i=0; i<mitad; i++){
                colaLadoDerecho.encolar(colaAOrdenar.sacar());
            }
            Cola<T> colaLadoIzquiero = colaAOrdenar;
            return mezclar(mergeSort(colaLadoDerecho), mergeSort(colaLadoIzquiero));
        }
    }

    private static <T extends Comparable<T> > Cola<T> mezclar(Cola<T > colaLadoDerecho, Cola<T> colaLadoIzquiero) {
        numeroMezclas++;
        Cola<T> colaOrdenada = new Cola<>();

        while (!colaLadoDerecho.isVacia() && !colaLadoIzquiero.isVacia()){
            if(colaLadoDerecho.mirar().compareTo (colaLadoIzquiero.mirar())>0){
                numeroComparaciones++;
                colaOrdenada.encolar(colaLadoIzquiero.sacar());
            }else {
                numeroComparaciones++;
                colaOrdenada.encolar(colaLadoDerecho.sacar());
            }
        }
        while (!colaLadoDerecho.isVacia()){
            colaOrdenada.encolar(colaLadoDerecho.sacar());
        }
        while (!colaLadoIzquiero.isVacia()){
            colaOrdenada.encolar(colaLadoIzquiero.sacar());
        }
        return colaOrdenada;
    }
}
