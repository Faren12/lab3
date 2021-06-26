public class quintupleta implements Comparable {

    private String categoria;
    private String nombre_producto;
    private int conteo;
    private String estrellas;
    private String precio_max;
    private int mOrden;


    public quintupleta() {
        categoria = new String();
        nombre_producto = new String();
        estrellas = new String();
        precio_max = new String();
        conteo = 0;
    }
    public quintupleta(String c, String n, String e, String p) {
        categoria = c;
        nombre_producto = n;
        estrellas = e;
        precio_max = p;
        conteo = 1;
    }

    public void set_categoria(String c) {
        categoria = c;
    }
    public void set_producto(String p) {
        nombre_producto = p;
    }
    public void incConteo() {
        conteo++;
    }
    public String get_categoria() {
        return categoria;
    }
    public String get_producto() {
        return nombre_producto;
    }
    public int get_conteo() {
        return conteo;
    }
    public String get_Estrellas(){
        return estrellas;
    }
    public String get_Precio_max(){
        return precio_max;
    }
    public int getmOrden(){
        return mOrden;
    }
    public void setmOrden(int m){
        this.mOrden = mOrden;
    }
    public void print(){
        System.out.println(categoria+"|"+nombre_producto+"|"+conteo+"|"+estrellas+"|"+precio_max);
    }
    @Override
    public int compareTo(Object t2) {
        int t2_count = ((quintupleta)t2).get_conteo();
        return this.get_conteo() - t2_count;
    }
}
