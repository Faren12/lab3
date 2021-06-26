package cl.udp.eit.edda.archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ArchivosCSVUtil {

    public static final String SEPARADOR_COLUMNAS= "\\|";

    public static Optional<List<String>> getNombresColumnas(Path pathArchivo){
        String[] valores = null;
       try( BufferedReader bufferedReader =new BufferedReader(new FileReader(pathArchivo.toFile()))){
           String primeraLinea=bufferedReader.readLine();
           valores=primeraLinea.split(SEPARADOR_COLUMNAS);
           if(valores.length>0){
               return Optional.of(Arrays.asList(valores));
           }

       }catch (Exception e){
           System.out.format("Error al leer nombres de columnas del csv %s. Detalles %s",pathArchivo.toString(), e.getMessage());
       }
        return Optional.empty();
    }

    public static List<List<String>> getValuesCSV(Path pathArchivo){

        List<List<String>> valoresTodasLasLineas= new ArrayList<>();

        try(BufferedReader bufferedReader =Files.newBufferedReader(pathArchivo)){
            bufferedReader.readLine();
            String linea;
            while ( (linea=bufferedReader.readLine())!=null){
                String[] valoresLinea=linea.split(SEPARADOR_COLUMNAS);
                if(valoresLinea.length>0){
                    valoresTodasLasLineas.add(Arrays.asList(valoresLinea));
                }
            }

        }catch (Exception e){
            System.out.format("Error al leer valores del csv %s. Detalles %s",pathArchivo.toString(), e.getMessage());
        }
        return valoresTodasLasLineas;
    }

    public static void writeToCSVFile(Path toFile,List<String> nombresColumnas, String separador, List<List<String>> valoresLineas){


        try(BufferedWriter bufferedWriter = Files.newBufferedWriter (toFile)){

            bufferedWriter.write(formatearValoresALinea(separador,nombresColumnas));
            bufferedWriter.newLine();
            valoresLineas.forEach(valoresLinea -> {
                try {
                   bufferedWriter.write(formatearValoresALinea(separador,valoresLinea));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    System.out.format("Error al escribir archivo csv %s. Detalles %s%n",toFile.toString(),e.getMessage());
                }
            } );


        }catch (Exception e){
            System.out.format("Error al escribir archivo csv %s. Detalles %s%n",toFile.toString(),e.getMessage());
        }
    }

    public static String formatearValoresALinea(String separador, List<String> valoresLinea){
        StringJoiner lineaFormateada = new StringJoiner(separador);
        valoresLinea.forEach(lineaFormateada::add);
        return lineaFormateada.toString();
    }
}
