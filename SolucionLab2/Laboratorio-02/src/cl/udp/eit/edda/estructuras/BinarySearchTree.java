package cl.udp.eit.edda.estructuras;

import java.security.Key;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
        public Node getLeft(){
            return left;
        }
        public Node getRight(){
            return right;
        }
        public Key getKey(){
            return key;
        }
        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
        public void setSize(int size){
            this.size=size;
        }

    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (null == node) {
            return 0;
        } else {
            return node.getSize();
        }
    }

    public boolean contains(Key key){
        if (key==null) throw  new IllegalArgumentException("argument to contains() is null");
        return get(key)!=null;
    }

    public Value get(Key key){
        return get(root,key);
    }

    private Value get(Node node, Key key){
        if(key==null) throw new IllegalArgumentException("calls get() with a null key");
        if(node == null) return null;

        int compare = key.compareTo(node.getKey());
        if(compare < 0) {
            return get(node.getLeft(), key);
        }else if(compare>0){
            return get(node.getRight(),key);
        }
        return node.value;

    }

    public void put(Key key, Value value){
        if(key==null) throw new IllegalArgumentException("calls put() with a null key");
        if(value == null){
            delete(key);
            return;
        }
        this.root=put(root,key,value);
    }
    private Node put(Node node, Key key, Value value){
        if(node == null) { //cuando el árbol está vacío
            return new Node(key,value,1);
        }
        int compare = key.compareTo(node.getKey());
        if (compare<0){
            node.setLeft(put(node.getLeft(),key,value));
        }else if (compare>0){
            node.setRight(put(node.getRight(),key,value));
        }else {
            node.value=value;
        }
        node.size = 1 +size(node.getLeft())+size(node.getRight());
        return node;
    }

    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        this.root= deleteMin(root);
    }
    private Node deleteMin(Node node){
        if(node.getLeft() ==null){
            return node.getRight();
        }
        node.setLeft(deleteMin(node.getLeft()));
        node.size = size(node.getLeft())+size(node.getRight())+1;
        return node;
    }

    public void deleteMax(){
        if(isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        this.root = deleteMax(root);
    }

    private Node deleteMax(Node node){
        if(node.getRight()==null) return node.getLeft();
        node.setRight(deleteMax(node.getRight()));
        node.setSize(size(node.getLeft())+size(node.getRight())+1);
        return node;
    }

    public void delete(Key key){
        if(key==null) throw new IllegalArgumentException("calls delete() with a null key");
        this.root= delete(root,key);
    }

    private Node delete(Node node, Key key){
        if(node == null) return node;

        int compare = key.compareTo(node.getKey());

        if(compare<0){
            node.setLeft(delete(node.getLeft(),key));
        }else if(compare>0){
            node.setRight(delete(node.getRight(),key));
        }else {
            if(node.getRight()== null){
                return node.getLeft();
            }
            if (node.getLeft()==null){
                return node.getRight();
            }
            Node temporal= node;
            node = min(temporal.getRight());
            node.setRight(deleteMin(temporal.getRight()));
            node.setLeft(node.getLeft());
        }
        node.setSize(size(node.getLeft())+size(node.getRight())+1);
        return node;
    }

    public Key min(){
        if (isEmpty()) throw new NoSuchElementException("call min() with empty symbol table");
        return min(root).getKey();
    }
    public Node min(Node node){
        if (node.getLeft() == null){
            return node;
        }
        return min(node.getLeft());
    }

    public Key max(){
        if(isEmpty()) throw new NoSuchElementException("call max() with empty symbol table");
        return max(root).getKey();
    }

    public Node max(Node node){
        if(node.getRight()==null){
            return node;
        }
        return max(node.getRight());
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     * @param key
     * @return
     */
    public Key floor(Key key){
        if(key==null) throw new IllegalArgumentException("argument to floor() is null");
        if(isEmpty()) throw new NoSuchElementException("calls floor with empty symbol table");

        Node node =floor(root,key);
        if(node == null) throw new NoSuchElementException("argument to floor() is too small");

        return node.getKey();
    }

    private Node floor(Node node, Key key){
        if(node == null){
            return null;
        }
        int compare = key.compareTo(node.getKey());
        if(compare == 0) {
            return node;
        }
        if(compare < 0){
            return floor(node.getLeft(),key);
        }
        Node temporal = floor(node.getRight(),key);
        if(temporal!=null){
            return temporal;
        }

        return node;

    }
    public Key floor2(Key key) {
        Key x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null) return best;
        int cmp = key.compareTo(x.key);
        if      (cmp  < 0) return floor2(x.left, key, best);
        else if (cmp  > 0) return floor2(x.right, key, x.key);
        else               return x.key;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     * @param key
     * @return
     */
    public Key ceiling(Key key){
        if(key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if(isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");

        Node node = ceiling(root, key);
        if(node==null){
            throw new NoSuchElementException("argument to ceiling() is too large");
        }
        return node.getKey();
    }

    private Node ceiling(Node node, Key key){
        if(node == null) return null;
        int compare = key.compareTo(node.getKey());
        if(compare==0){
            return node;
        }
        if(compare<0){
            Node temporal = ceiling(node.getLeft(),key);
            if(temporal!=null){
                return temporal;
            }else {
                return node;
            }
        }
        return ceiling(node.getRight(),key);
    }
    public Key select(int rank){
        if (rank<0 || rank>=size()){
            throw new IllegalArgumentException("argument to select() is invalid: "+rank);
        }
        return select(root,rank);
    }

    private Key select(Node node, int rank){
        if(node==null){
            return null;
        }
        int leftSize= size(node.getLeft());
        if(leftSize>rank){
            return select((node.getLeft()),rank);
        }else if(leftSize<rank){
            return select(node.getRight(),rank-leftSize-1);
        }
        return node.getKey();
    }

    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    //Numero de keys en el subarbol menor que la key
    private int rank(Key key, Node node){
        if(node==null) return 0;
        int compare= key.compareTo(node.getKey());
        if(compare<0){
            return rank(key,node.getLeft());
        }else if(compare>0){
            return 1+size(node.getLeft())* rank(key,node.getRight());
        }else {
            return size(node.getLeft());
        }
    }

    public Iterable<Key> keys(){
        if(isEmpty()){
            return new Cola<>();
        }
        return keys(min(),max());
    }

    public Iterable<Key> keys(Key low, Key hight){
        if(low==null){
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if(hight==null){
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        Cola<Key> cola = new Cola<>();
        keys(root, cola,low,hight);
        return cola;
    }

    private void keys(Node node, Cola<Key> cola, Key low, Key hight){
        if(node==null) return;
        int compareLow= low.compareTo(node.getKey());
        int compareHight= hight.compareTo(node.getKey());

        if(compareLow<0){
            keys(node.getLeft(),cola,low,hight);
        }
        if(compareLow<=0 && compareHight>=0){
            cola.encolar(node.getKey());
        }
        if(compareHight>0){
            keys(node.getRight(),cola,low,hight);
        }
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }
    public int height() {
        return height(root);
    }
    private int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
