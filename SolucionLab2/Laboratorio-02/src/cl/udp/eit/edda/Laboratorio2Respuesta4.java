package cl.udp.eit.edda;

import cl.udp.eit.edda.archivos.ArchivoTripletaUtil;
import cl.udp.eit.edda.archivos.ArchivosCSVUtil;
import cl.udp.eit.edda.archivos.DataSet;
import cl.udp.eit.edda.estructuras.BinarySearchTree;
import cl.udp.eit.edda.estructuras.Cola;
import cl.udp.eit.edda.estructuras.Tripleta;


import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static cl.udp.eit.edda.Constantes.DATA_SET_PATH;

public class Laboratorio2Respuesta4 {

    public static void main(String args[]) {


        BinarySearchTree<Integer, Tripleta> binarySearchTree = new BinarySearchTree<>();
        Cola<Tripleta> colaTripletas;
        List<Path> archivosDataSet = DataSet.getPaths(DATA_SET_PATH);

        //Leo archivos del DataSet y los transformo a una cola de tripletas
        System.out.println("Cargando información en memoria...");
        int cantidadArchivos = archivosDataSet.size();
        int i = 1;
        for (Path pathArchivo : archivosDataSet) {
            colaTripletas = new Cola<>();
            String categoria = pathArchivo.getFileName().toString().replace("mx-", "").replace(".csv", "");
            colaTripletas = ArchivoTripletaUtil.lineasToTripletas(categoria, ArchivosCSVUtil.getValuesCSV(pathArchivo));
            agregarTripletasABinaryTree(colaTripletas, binarySearchTree);
            System.out.print((i * 100) / cantidadArchivos + "% ");
            i++;
        }
        System.out.println("\n"+binarySearchTree.size()+" elementos cargados, el programa está listo para recibir consultas\n");

        Scanner scanner = new Scanner(System.in);
        menuInteractivoUsuario(scanner, binarySearchTree);


    }

    private static void menuInteractivoUsuario(Scanner scanner, BinarySearchTree<Integer, Tripleta> binarySearchTree) {

        int respuesta, cantidad;
        while (true) {
            System.out.println("Ingrese un número de compras a consultar: ");
            cantidad = scanner.nextInt();
            System.out.println("Qué tipo de productos desea visualizar: ");
            System.out.println("1. Productos con ventas mayores a " + cantidad);
            System.out.println("2. Productos con ventas iguales a " + cantidad);
            System.out.println("3. Productos con ventas menores a " + cantidad);
            System.out.println("4. Salir");
            respuesta = scanner.nextInt();
            if (respuesta == 4) {
                System.out.println("Adiós.");
                break;
            } else {
                switch (respuesta) {
                    case 1:
                        printMayores(cantidad, binarySearchTree);
                        break;
                    case 2:
                        printIguales(cantidad, binarySearchTree);
                        break;
                    case 3:
                        printMenores(cantidad, binarySearchTree);
                        break;
                    default:
                        System.out.println("La opción ingresada no es válida, por favor intente nuevamente.");
                }
            }
        }

    }

    private static void printMenores(int cantidad, BinarySearchTree<Integer, Tripleta> binarySearchTree) {
        try {
            Iterable<Integer> keys = binarySearchTree.keys(binarySearchTree.min(), binarySearchTree.floor(cantidad));
            for (int i : keys) {
                if (i != cantidad)
                    System.out.println(binarySearchTree.get(i));
            }
        }
        catch (NoSuchElementException e){
            System.out.println("No se encontraron elementos con comprados menos de "+cantidad+" veces.");
        }
    }

    private static void printIguales(int cantidad, BinarySearchTree<Integer, Tripleta> binarySearchTree) {
        Tripleta tripleta = binarySearchTree.get(cantidad);
        if (null != tripleta) {
            System.out.println(tripleta);
        } else {
            System.out.println("No existen productos que se hayan comprado exactamente " + cantidad + " veces.");
        }
    }

    private static void printMayores(int cantidad, BinarySearchTree<Integer, Tripleta> binarySearchTree) {
        try {
            Iterable<Integer> keys = binarySearchTree.keys(binarySearchTree.ceiling(cantidad), binarySearchTree.max());
            for (int i : keys) {
                if(i!=cantidad)
                    System.out.println(binarySearchTree.get(i));
            }
        }catch (NoSuchElementException e){
            System.out.println("No se encontraron elementos con comprados más de "+cantidad+" veces.");
        }

    }

    private static void agregarTripletasABinaryTree(Cola<Tripleta> colaTripletas, BinarySearchTree<Integer, Tripleta> binarySearchTree) {
        for (Tripleta tripleta : colaTripletas) {
            binarySearchTree.put(tripleta.getNumeroVeces(), tripleta);
        }
    }
}
