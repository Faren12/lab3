package cl.udp.eit.edda;

import cl.udp.eit.edda.archivos.ArchivoTripletaUtil;
import cl.udp.eit.edda.archivos.ArchivosCSVUtil;
import cl.udp.eit.edda.archivos.DataSet;
import cl.udp.eit.edda.estructuras.Cola;
import cl.udp.eit.edda.estructuras.Tripleta;
import cl.udp.eit.edda.ordenamiento.MaxHeap;
import cl.udp.eit.edda.ordenamiento.MergeSort;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import java.util.function.UnaryOperator;

import static cl.udp.eit.edda.Constantes.DATA_SET_PATH;

public class Laboratorio2Respuesta2 {



    public static void main(String args[]){

        Cola<Tripleta> colaTripletas= new Cola<>();

        List<Path> archivosDataSet = DataSet.getPaths(DATA_SET_PATH);

        //Leo solo el primer archivo del DataSet y lo transformo a una cola de tripletas
        if(!archivosDataSet.isEmpty()){
            String categoria=archivosDataSet.get(0).getFileName().toString().replace("mx-","").replace(".csv","");
             colaTripletas= ArchivoTripletaUtil.lineasToTripletas(categoria,ArchivosCSVUtil.getValuesCSV(archivosDataSet.get(0)));
        }

        //Todos los algoritmos deberian recibir los elementos en la misma estructura


        ejecutarAlgoritmo("heapSort",MaxHeap::sort,colaTripletas.clone());
        System.out.println("operaciones de comparacion: "+MaxHeap.numeroComparaciones);

        ejecutarAlgoritmo("mergeSort",MergeSort::mergeSort,colaTripletas.clone());
        System.out.println("operaciones de comparación: "+MergeSort.numeroComparaciones);
        System.out.println("operaciones de mezcla: "+MergeSort.numeroMezclas);

    }

    private static <T> void ejecutarAlgoritmo(String nombreAlgoritmo, UnaryOperator<Cola<T>> algoritmo, Cola<T> listaTripletas){
        Instant inicio=Instant.now();
        algoritmo.apply(listaTripletas);
        long duration=Duration.between(inicio,Instant.now()).toMillis();
        System.out.printf("Algoritmo %s demoró %d milisegundos\n",nombreAlgoritmo,duration);


    }
}
