package cl.udp.eit.edda.ordenamiento;
import java.util.ArrayList;

import java.util.Comparator;

class selectSort <T extends Comparable<T>>
{ 
    public static int numeroComparaciones; 

    public selectSort()
    {
        numeroComparaciones = 0;
    }

    private void Ssort(ArrayList<T> array) 
    {
        for (int i = 0; i < array.size()-1; i++) 
        { 
            int max_index = i; 
            for (int j = i+1; j < array.size(); j++)
            {
                if (array.get(max_index).compareTo(array.get(j)) < 0)
                { 
                    max_index = j; 
                }
            }
            T temp = (T)array.get(max_index); 
            array.set(max_index, array.get(i));
            array.set(i, temp);
        } 
    } 
}