package cl.udp.eit.edda.ordenamiento;
import java.util.ArrayList;

import java.util.Comparator;

public class bubbleSort <T extends Comparable<T>>
{
    public static int numeroComparaciones; 

    public bubbleSort()
    {
        numeroComparaciones = 0;
    }

    public ArrayList<T> Bsort(ArrayList<T> array) 
    { 
        int i, j; 
        T temp;
        boolean swapped; 
        for (i = 0; i < array.size() - 1; i++)  
        { 
            swapped = false; 
            for (j = 0; j < array.size() - i - 1; j++)  
            { 
                //if (array.get(j).getDmg() < array.get(j+1).getDmg())
                numeroComparaciones++;
                if (array.get(j).compareTo(array.get(j+1)) < 0)  
                { 
                    temp = (T)array.get(j);
                    array.set(j,array.get(j+1));
                    array.set(j+1,temp);
                    swapped = true; 
                } 
            } 
            if (swapped == false)
            {
                break;
            }   
        } 
        return array;
    }
}