package cl.udp.eit.edda.ordenamiento;
import java.util.ArrayList;

import java.util.Comparator;

public class insertSort <T extends Comparable<T>>
{
    public static int numeroComparaciones;

    public insertSort()
    {
        numeroComparaciones = 0;
    }

    ArrayList<T> Isort(ArrayList<T> array) 
    { 
        for (int i = 1; i < array.size(); i++) {
            T key = (T)array.get(i); 
            int j = i - 1;
            numeroComparaciones++;
            while (j >= 0 && array.get(j).compareTo(key) < 0) 
            { 
                array.set(j+1,array.get(j));
                j = j - 1; 
            } 
            array.set(j+1,key);
        }
        return array;
    } 
}