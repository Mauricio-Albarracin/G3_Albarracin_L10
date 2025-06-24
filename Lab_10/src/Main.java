import btree.*;

public class Main {
    public static void main(String[] args) {
        // Crear árbol B de orden 4 (máximo 3 claves por nodo)
        BTree<Integer> arbol = new BTree<>(4);

        // Insertar claves como en tu ejemplo (ajustado para que formen niveles)
        int[] claves = {31, 12, 19, 3, 10, 13, 16, 22, 25, 28, 41, 57, 63, 33, 35, 40, 49, 52, 55, 60, 62, 67, 70, 72};

        for (int clave : claves) {
            arbol.insert(clave);
        }

        // Mostrar estructura del árbol B
        System.out.println(arbol.toString());
    }
}
