
public class arbol {
    public Node root;
    public quintupleta value;

    private class Node {
        public float key;
        public quintupleta val;
        public Node left, right;

        public Node(float key, quintupleta val) {
            this.key = key;
            this.val = val;
            left=null;
            right=null;

        }
    }
    arbol(float key,quintupleta root){
        root=new Node(key, root);
    }
    public void add(Node nodo,quintupleta val){
        if(val.get_Precio_max()<=nodo.key){
            if(nodo.left!=null){
                add(nodo.left,val);
            }else{
                nodo.left=new Node(val.get_Precio_max(),val);
            }
        }else{
            if(nodo.right!=null){
                add(nodo, val);
            }else{
                nodo.right=new node(val.get_Precio_max(),val);
            }
        }
    }
    public void print(){
        print(root);
    }
    public void print(Node a){
        if(a.left!=null){
            print(a.left);
        }
        a.val.print();
        if(a.left!=null){
            print(a.left);
        }
    }
}
