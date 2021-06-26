package cl.udp.eit.edda.archivos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataSet {

    public static List<Path> getPaths(String path){
        List<Path> archivos = new ArrayList<>();
        try {

            archivos= Files.list(Paths.get(path)).collect(Collectors.toList());

        }catch (Exception e){
            System.out.println("Ocurrió un problema "+e);
        }
        return archivos;

    }
}
