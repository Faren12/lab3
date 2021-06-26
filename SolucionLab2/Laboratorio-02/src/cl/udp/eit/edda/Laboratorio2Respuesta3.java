package cl.udp.eit.edda;

import cl.udp.eit.edda.archivos.ArchivoTripletaUtil;
import cl.udp.eit.edda.archivos.ArchivosCSVUtil;
import cl.udp.eit.edda.archivos.DataSet;
import cl.udp.eit.edda.estructuras.Cola;
import cl.udp.eit.edda.estructuras.Tripleta;
import cl.udp.eit.edda.ordenamiento.MaxHeap;

import java.nio.file.Path;
import java.util.List;
import java.io.*;

import static cl.udp.eit.edda.Constantes.DATA_SET_PATH;

public class Laboratorio2Respuesta3 {

    public static void main(String args[]) {


        Cola<Tripleta> colaTripletas;
        List<Path> archivosDataSet = DataSet.getPaths(DATA_SET_PATH);

        //Leo archivos del DataSet y los transformo a una cola de tripletas
        for(Path pathArchivo:archivosDataSet){
            colaTripletas = new Cola<>();
            String categoria = pathArchivo.getFileName().toString().replace("mx-", "").replace(".csv", "");
            colaTripletas = ArchivoTripletaUtil.lineasToTripletas(categoria, ArchivosCSVUtil.getValuesCSV(pathArchivo));
            MaxHeap<Tripleta> colaEficiente= new MaxHeap(colaTripletas);
            Cola<Tripleta> maximosYMinimos = new Cola<>();
            agregarMasComprados(3,colaEficiente,maximosYMinimos);
            agregarMenosComprados(3,colaEficiente,maximosYMinimos);
            printMasYMenosComprados(categoria,maximosYMinimos);
        }

    }

    private static void printMasYMenosComprados(String categoria, Cola<Tripleta> maximosYMinimos) {
        try {
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter("MyOutput.txt",false));
            writer.write("=========Máximos y minimos==================\n");
            writer.write("categoria: " + categoria + "\n");
            for (Tripleta tripleta:maximosYMinimos){
                writer.write(tripleta + "\n");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void agregarMenosComprados(int cantidad, MaxHeap<Tripleta> colaEficiente, Cola<Tripleta> maximosYMinimos) {
        int size=colaEficiente.size();
        for(int i=0; i<size;i++){
            if(i>=size-cantidad){
                maximosYMinimos.encolar(colaEficiente.deleteMax());
            }else {
                colaEficiente.deleteMax();
            }
        }
    }

    private static void agregarMasComprados(int cantidad, MaxHeap<Tripleta> colaEficiente, Cola<Tripleta> maximosYMinimos) {
        for(int i=0; i<cantidad;i++){
            maximosYMinimos.encolar(colaEficiente.deleteMax());
        }
    }
}
