import java.util.*;
import java.io.*;
public class volcado3 {

    private static void crearMatrizAdyacencia(ArrayList<quintupleta> sortedQuintuplas, Float[][] matrizAdyacencia) {
        int tamanoMatriz = sortedQuintuplas.size();

        for (int i = 0; i < tamanoMatriz; i++) {
            Float elementoActual = Float.parseFloat(sortedQuintuplas.get(i).get_Precio_max());
            for (int j = i; j < tamanoMatriz; j++) {
                Float aux = Float.parseFloat(sortedQuintuplas.get(j).get_Precio_max());
                matrizAdyacencia[i][j] = elementoActual - aux;
                matrizAdyacencia[j][i] = matrizAdyacencia[i][j];
            }
        }
    }

    private static void escribirMatrizAdyacencia(Float[][] matrizAdyacencia, String categoria) {
        try {
            int tamanoMatriz = matrizAdyacencia.length;
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter("MatrizAdyacencia.txt",false));

            for (int i = 0; i < tamanoMatriz; i++) {
                for (int j = 0; j < tamanoMatriz; j++) {
                    writer.write(matrizAdyacencia[i][j] + ",");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Matriz guardada en archivo: ");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BST<String, quintupleta> Stars = new BST<>();
        BST_Pmax<String, quintupleta> maxP = new BST_Pmax<>();
        HashMap<String, ArrayList<BST<String, quintupleta>>> mapS = new HashMap<>();
        HashMap<String, ArrayList<BST_Pmax<String, quintupleta>>> mapP = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        BufferedReader reader;
        BufferedWriter writer;
        String[] filenames = {"mx-amazon-devices.csv", "mx-automotive.csv", "mx-baby.csv", "mx-books.csv", "mx-digital-text.csv", "mx-dvd.csv", "mx-electronics.csv", "mx-grocery.csv", "mx-handmade.csv", "mx-hpc.csv", "mx-kitchen.csv", "mx-music.csv", "mx-musical-instruments.csv", "mx-officeproduct.csv", "mx-pet-supplies.csv", "mx-shoes.csv", "mx-software.csv", "mx-sports.csv", "mx-tools.csv", "mx-videogames.csv"};
        //Para Windows, este arreglo es para meter todos los archivos a un arreglo filelist
        String[] categories = {"Devices", "Automative", "Baby", "Books", "Digital-text", "DvD", "Electronics", "Grocery", "Handmade", "Hpc", "Kitchen", "Music", "Musical-instrument", "Office-product", "Pet-supplies", "Shoes", "Software", "Sports", "Tools", "Videogames"};

        //ArrayList<String> filenames = new ArrayList<>();
        //for(String filelists : filelist){
        //    filenames.add(".\\mexico\\csv\\" + filelists);
        //    File file = new File(filelists);
        //}
        //Para Windows, se crea un arreglo y se hace un for para acceder a los archivos y asi ingresar los datos al arreglo.
        //String[] filenames = {"./mx-amazon-devices.csv", "./mx-automotive.csv", "./mx-baby.csv", "./mx-books.csv", "./mx-digital-text.csv", "./mx-dvd.csv", "./mx-electronics.csv", "./mx-grocery.csv", "./mx-handmade.csv", "./mx-hpc.csv", "./mx-kitchen.csv", "./mx-music.csv", "./mx-musical-instruments.csv", "./mx-officeproduct.csv", "./mx-pet-supplies.csv", "./mx-shoes.csv", "./mx-software.csv", "./mx-sports.csv", "./mx-tools.csv", "./mx-toys.csv", "./mx-videogames.csv"};
        //Arreglo para acceder e ingresar los archivos, para MAC o Linux
        try {
            int index_categories = 0;


            for (String fn : filenames) {
                ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
                //String outfilename = fn.substring(0, fn.length() - 4) + new String("_out") + fn.substring(fn.length() - 4);
                reader = new BufferedReader(new FileReader("./csv/"+fn));
                //writer = new BufferedWriter(new FileWriter(outfilename, false));
                writer = new BufferedWriter(new FileWriter("count.csv", true));
                String line = reader.readLine();
                while (line != null) {
                    ArrayList<String> parsing1 = new ArrayList<String>();
                    String[] row1;
                    row1 = line.split("\\|", -1);
                    for (String x : row1) {
                        parsing1.add(x);
                    }
                    dataset.add(parsing1);
                    line = reader.readLine();
                }

                ArrayList<quintupleta> t_count = new ArrayList<quintupleta>();
                int progress_index = 0;

                for (progress_index = 1; progress_index < dataset.size(); progress_index++) {
                    if (dataset.get(progress_index).size()>8) {
                        

                        String prod_name = dataset.get(progress_index).get(3);
                        String stars = dataset.get(progress_index).get(4);
                        String max_prices = dataset.get(progress_index).get(8);
                        boolean found = false;
                        for (quintupleta search : t_count) {
                            if (search.get_producto().equals(prod_name)) {
                                found = true;
                                search.incConteo();
                            }
                        }
                        if (!found) {
                            quintupleta new_tripleta = new quintupleta(new String(categories[index_categories]), prod_name, stars, max_prices);
                            t_count.add(new_tripleta);
                        }
                    }
                }


                for(quintupleta t_d : t_count)
                {
                    writer.write(t_d.get_categoria() + ", " + t_d.get_producto() + ", " + t_d.get_conteo() + ", " + t_d.get_Estrellas() + ", " + t_d.get_Precio_max() + "\n");
                }

                mapS.put(categories[index_categories], new ArrayList<>());
                mapS.get(categories[index_categories]).add(Stars);
                mapP.put(categories[index_categories], new ArrayList<>());
                mapP.get(categories[index_categories]).add(maxP);

                for (quintupleta t_d : t_count) {
                    Stars.put(t_d.get_Estrellas(), t_d);
                    maxP.put(t_d.get_Precio_max(), t_d);
                }
                ArrayList<quintupleta> sortedQuintupleta = new ArrayList<quintupleta>();
                for (int i = 0; i<t_count.size(); i++) {
                    float pmax;
                    try{
                        pmax = Float.parseFloat(t_count.get(i).get_Precio_max());//Intenta convertir el numero, si no puede se salta la entrada
                    }catch(NumberFormatException e){
                        continue;
                    }
                    if(pmax != 0){
                        // Ordenar por PrecioMaximo
                        t_count.get(i).setmOrden(2);
                        sortedQuintupleta.add(t_count.get(i));
                    }
                }
                Collections.sort(sortedQuintupleta, Collections.reverseOrder());

                int tamanoMatriz = sortedQuintupleta.size();
                Float[][] matrizAdyacencia = new Float[tamanoMatriz][tamanoMatriz];
                crearMatrizAdyacencia(sortedQuintupleta, matrizAdyacencia);
                escribirMatrizAdyacencia(matrizAdyacencia, categories[index_categories]);

                index_categories++;
            }
            boolean Break = false;
            System.out.println("Ingrese 1 para ver los productos mayores a la cantidad de estrellas, 2 para los menores, 3 para la cantidad exacta y 4 para salir");
            while (!Break){
                int aux = scan.nextInt();
                if(aux == 4){
                    Break = true;
                }
                if(aux == 1){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos con mayor cantidad de estrellas a "+ cant);
                    System.out.println("**Cantidad  |   Producto   |   Categoria**");
                    if(Stars.inOrden(Stars.root, 1, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }
                }
                else if(aux == 2){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos con menor cantidad a "+ cant);
                    System.out.println("**Cantidad  |   Producto   |   Categoria**");
                    if(Stars.inOrden(Stars.root, 2, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }
                }
                else if(aux == 3){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos exactamente a "+ cant);
                    System.out.println("**Cantidad |   Producto   |   Categoria**");
                    if(Stars.inOrden(Stars.root, 3, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }

                }
            }
            boolean _break = false;
            System.out.println("Ingrese 1 para ver los productos mayores al precio maximo promedio, 2 para los menores, 3 para la cantidad exacta y 4 para salir");
            while (!_break){
                int aux = scan.nextInt();
                if(aux == 4){
                    _break = true;
                }
                if(aux == 1){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos mayor al promedio "+ cant);
                    System.out.println("**Precio promedio  |   Producto   |   Categoria**");
                    if(Stars.inOrden(maxP.root, 1, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }
                }
                else if(aux == 2){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos menor al promedio "+ cant);
                    System.out.println("**Precio promedio  |   Producto   |   Categoria**");
                    if(Stars.inOrden(maxP.root, 2, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }
                }
                else if(aux == 3){
                    System.out.println("Ingrese la cantidad a evaluar");
                    int cant = scan.nextInt();
                    System.out.println("Ha elegido todos los productos exactamente al promedio "+ cant);
                    System.out.println("**Precio promedio |   Producto   |   Categoria**");
                    if(Stars.inOrden(maxP.root, 3, cant) == 0) {
                        System.out.println("No existe ningun producto");
                    }

                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}