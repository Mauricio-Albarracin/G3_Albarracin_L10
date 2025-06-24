package btree;

public class Main {
    public static void main(String[] args) {
        /*
        // Crear un árbol B de orden 4 (máximo 3 claves por nodo)
        BTree<Integer> bTree = new BTree<>(4);

        // Crear los nodos según la estructura proporcionada con IDs personalizados
        BNode<Integer> node1 = new BNode<>(3, 1);
        node1.count = 2;
        node1.keys.set(0, 3);
        node1.keys.set(1, 10);

        BNode<Integer> node2 = new BNode<>(3, 2);
        node2.count = 2;
        node2.keys.set(0, 13);
        node2.keys.set(1, 16);

        BNode<Integer> node4 = new BNode<>(3, 4);
        node4.count = 3;
        node4.keys.set(0, 22);
        node4.keys.set(1, 25);
        node4.keys.set(2, 28);

        BNode<Integer> node3 = new BNode<>(3, 3);
        node3.count = 2;
        node3.keys.set(0, 12);
        node3.keys.set(1, 19);
        node3.childs.set(0, node1);
        node3.childs.set(1, node2);
        node3.childs.set(2, node4);

        BNode<Integer> node5 = new BNode<>(3, 5);
        node5.count = 3;
        node5.keys.set(0, 33);
        node5.keys.set(1, 38);
        node5.keys.set(2, 40);

        BNode<Integer> node6 = new BNode<>(3, 6);
        node6.count = 3;
        node6.keys.set(0, 49);
        node6.keys.set(1, 52);
        node6.keys.set(2, 55);

        BNode<Integer> node7 = new BNode<>(3, 7);
        node7.count = 2;
        node7.keys.set(0, 60);
        node7.keys.set(1, 62);

        BNode<Integer> node10 = new BNode<>(3, 10);
        node10.count = 3;
        node10.keys.set(0, 67);
        node10.keys.set(1, 70);
        node10.keys.set(2, 72);

        BNode<Integer> node8 = new BNode<>(3, 8);
        node8.count = 3;
        node8.keys.set(0, 41);
        node8.keys.set(1, 57);
        node8.keys.set(2, 63);
        node8.childs.set(0, node5);
        node8.childs.set(1, node6);
        node8.childs.set(2, node7);
        node8.childs.set(3, node10);

        BNode<Integer> node9 = new BNode<>(3, 9);
        node9.count = 1;
        node9.keys.set(0, 31);
        node9.childs.set(0, node3);
        node9.childs.set(1, node8);

        // Asignar la raíz del árbol
        bTree.setRoot(node9);

        // Imprimir el árbol para verificar la estructura
        System.out.println("Estructura del árbol B:");
        System.out.println(bTree.toString());

        // Buscar la clave 52
        System.out.println("\nBuscando la clave 52:");
        boolean found = bTree.search(52);
        System.out.println("Resultado de la búsqueda: " + found);

        // Eliminar la clave 52
        System.out.println("\nEliminando la clave 52:");
        bTree.remove(52);
        System.out.println("Estructura del árbol después de eliminar 52:");
        System.out.println(bTree.toString());
        
        boolean found1 = bTree.search(52);
        System.out.println("Resultado de la búsqueda: " + found1);
        */

        BTree<Integer> arbol = BTree.building_Btree();
        System.out.println("Árbol B generado desde archivo:");
        System.out.println(arbol);
    

    }
}