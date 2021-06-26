package cl.udp.eit.edda.archivos;


import cl.udp.eit.edda.estructuras.Cola;
import cl.udp.eit.edda.estructuras.Pila;
import cl.udp.eit.edda.estructuras.Tripleta;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ArchivoTripletaUtil {

    public static void escribirDecreciente(String path, Cola<Tripleta> colaTripletaProductoAmazon) throws IOException {

        Path pathFile= pathFile(path, "tripletasAmazon-decreciente.txt");
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter (pathFile, StandardOpenOption.APPEND)){
            bufferedWriter.write("Productos más solicitados de cada categoría:");
            bufferedWriter.newLine();
            colaTripletaProductoAmazon.forEach(tripletaProductoAmazon -> escribirTripleta(bufferedWriter,tripletaProductoAmazon));

        }catch (Exception e){
            System.out.format("Error al escribir archivo de tripletas decreciente. Detalles %s%n",e.getMessage());
        }
    }

    public static void escribirCreciente(String path, Pila<Tripleta> pilaTripletaProductoAmazon) throws IOException {
        Path pathFile= pathFile(path, "tripletasAmazon-creciente.txt");
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter (pathFile, StandardOpenOption.APPEND)){
            bufferedWriter.write("Productos más solicitados en orden creciente:");
            bufferedWriter.newLine();
            pilaTripletaProductoAmazon.forEach(tripletaProductoAmazon -> escribirTripleta(bufferedWriter,tripletaProductoAmazon));
        }catch (Exception e){
            System.out.format("Error al escribir archivo de tripletas creciente. Detalles %s%n",e.getMessage());
        }
    }

    private static void escribirTripleta(BufferedWriter bufferedWriter, Tripleta tripletaProductoAmazon){
        try {
            bufferedWriter.write(String.format("%s - %s, comprado %d veces.",tripletaProductoAmazon.getCategoria(),tripletaProductoAmazon.getNombreProducto(),tripletaProductoAmazon.getNumeroVeces()));
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.format("Error al escribir archivo de tripletas %s. Detalles %s%n",e.getMessage());
        }
    }
    private static Path pathFile(String path, String nombreArchivo) throws IOException {
        Path pathToFile= Paths.get(path);
        Files.createDirectories(pathToFile);
        Path pathFile= pathToFile.resolve(nombreArchivo);
        if(!Files.exists(pathFile)){
            Files.createFile(pathFile);
        }
        return pathFile;
    }

    public static Cola<Tripleta> lineasToTripletas(String categoria,List<List<String>> valoresArchivo){
        Cola<Tripleta> colaTripletas= new Cola<>();
        Cola<String> colaNombresProductos = new Cola<>();
        for(List<String> valoresLinea:valoresArchivo){
            if (valoresLinea == null) continue;
            if (valoresLinea.size() > 3 && !valoresLinea.get(3).isEmpty()) {
                colaNombresProductos.encolar(valoresLinea.get(3));
            }
        }
        for(String nombreProducto:colaNombresProductos){
            if(!existeEnColaTripletaAmazon(categoria,nombreProducto,colaTripletas)){
                Tripleta tripletaProductoAmazon = new Tripleta(categoria,nombreProducto,contarApariciones(nombreProducto, colaNombresProductos));
                colaTripletas.encolar(tripletaProductoAmazon);
            }
        }
        return colaTripletas;
    }

    public static int contarApariciones(String nombreABuscar, Cola<String> colaProductos){

        int contador=0;
        for(String linea:colaProductos){
            if(linea!=null && linea.compareToIgnoreCase(nombreABuscar)==0)
                contador++;
        }
        return contador;
    }

    public static boolean existeEnColaTripletaAmazon(String categoria, String nombreProducto, Cola<Tripleta> colaTripleta){
        for(Tripleta tripletaProductoAmazon:colaTripleta){
            if(tripletaProductoAmazon.getCategoria().compareTo(categoria) ==0 && tripletaProductoAmazon.getNombreProducto().compareToIgnoreCase(nombreProducto)==0){
                return true;
            }
        }
        return false;
    }
}
