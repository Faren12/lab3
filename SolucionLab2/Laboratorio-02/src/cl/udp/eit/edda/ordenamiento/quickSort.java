package cl.udp.eit.edda.ordenamiento;
import java.util.ArrayList;

import java.util.Comparator;

public class quickSort <T extends Comparable<T>>
{
    public static int numeroComparaciones; 
    private ArrayList<T> array;

    public quickSort()
    {
        numeroComparaciones = 0;
        array = new ArrayList<T>();
    }

    private int partition(int low, int high) 
    { 
        //int pivot = array.get(high).getDmg();  
        int i = (low-1);
        for (int j=low; j<high; j++) 
        { 
            numeroComparaciones++;
            if (array.get(high).compareTo(array.get(j)) < 0) 
            { 
                i++; 
                T temp = (T)array.get(i); 
                array.set(i,array.get(j));
                array.set(j,temp);
            } 
        } 
        T temp = (T)array.get(i+1); 
        array.set(i+1,array.get(high));
        array.set(high,temp); 
  
        return i+1; 
    } 

    public ArrayList<T> Qsort(ArrayList<T> array)
    {
        this.array = array;
        Qsort1(0, this.array.size()-1);
        return this.array;
    }

    private void Qsort1(int low, int high) 
    { 
        if (low < high) 
        { 
            int pi = partition(low, high); 
            Qsort1(low, pi-1); 
            Qsort1(pi+1, high); 
        } 
    }
}