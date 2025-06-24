package btree;

public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    // Constructor de árbol B con orden especificado
    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    // Verifica si el árbol está vacío
    public boolean isEmpty() {
        return this.root == null;
    }

    // Inserta una nueva clave al árbol
    public void insert(E cl) {
        up = false;
        E mediana;
        BNode<E> pnew;

        mediana = push(this.root, cl);

        // Si se necesita subir la mediana al nivel superior (raíz dividida)
        if (up) {
            pnew = new BNode<E>(this.orden);
            pnew.count = 1;

            // Asegura que la clave y los hijos estén preparados
            for (int i = 0; i < orden - 1; i++) {
                pnew.keys.add(null);
                pnew.childs.add(null);
            }
            pnew.childs.add(null);

            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

    // Método recursivo para empujar la clave hacia su lugar
    private E push(BNode<E> current, E cl) {
        int[] pos = new int[1];
        E mediana;

        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        } else {
            Object[] result = current.searchNode(cl);
            boolean encontrado = (boolean) result[0];
            pos[0] = (int) result[1];

            if (encontrado) {
                System.out.println("Item duplicado\n");
                up = false;
                return null;
            }

            mediana = push(current.childs.get(pos[0]), cl);

            if (up) {
                if (current.nodeFull()) {
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }

            return mediana;
        }
    }

    // Inserta la clave en la posición k
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        int i;

        // Desplaza claves e hijos a la derecha
        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }

        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    // Divide un nodo y devuelve la mediana que debe subirse
    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int i, posMdna;

        posMdna = (k <= orden / 2) ? orden / 2 : orden / 2 + 1;

        nDes = new BNode<E>(orden);

        // Asegura espacio en listas del nuevo nodo
        for (i = 0; i < orden - 1; i++) {
            nDes.keys.add(null);
            nDes.childs.add(null);
        }
        nDes.childs.add(null);

        // Mueve claves e hijos al nuevo nodo desde posMdna
        for (i = posMdna; i < orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        nDes.count = (orden - 1) - posMdna;
        current.count = posMdna;

        if (k <= orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }

        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;

        return median;
    }

    // Devuelve una representación en texto del árbol B
    @Override
    public String toString() {
        String s = "";
        if (isEmpty()) {
            s += "BTree is empty...";
        } else {
            s = writeTree(this.root, 0);
        }
        return s;
    }

    // Recorre el árbol en preorden e imprime los nodos con su profundidad
    private String writeTree(BNode<E> current, int nivel) {
        String s = "";

        // Sangría según el nivel del nodo
        for (int i = 0; i < nivel; i++) {
            s += "  ";
        }

        // Agrega el ID del nodo y sus claves
        s += "Nodo ID " + current.idNode + ": ";
        for (int i = 0; i < current.count; i++) {
            s += current.keys.get(i) + " ";
        }
        s += "\n";

        // Recorre recursivamente los hijos no nulos
        for (int i = 0; i <= current.count; i++) {
            if (current.childs.get(i) != null) {
                s += writeTree(current.childs.get(i), nivel + 1);
            }
        }

        return s;
    }

    // Método público de búsqueda
    public boolean search(E cl) {
        return searchRecursive(this.root, cl);
    }

    // Método recursivo de soporte para buscar una clave en el árbol
    private boolean searchRecursive(BNode<E> current, E cl) {
        if (current == null) return false;

        Object[] result = current.searchNode(cl);
        boolean found = (boolean) result[0];
        int pos = (int) result[1];

        if (found) {
            System.out.println(cl + " se encuentra en el nodo " + current.idNode + " en la posición " + pos);
            return true;
        } else {
            // Continuar búsqueda en el hijo correspondiente
            return searchRecursive(current.childs.get(pos), cl);
        }
    }

    public void setRoot(BNode<E> root) {
        this.root = root;
    }

    // Calcula el número mínimo de claves que debe tener un nodo no raíz
    private int minKeys() {
        return (orden / 2) - 1;
    }

    // Método público para eliminar una clave del árbol
    public void remove(E cl) {
        if (isEmpty()) {
            System.out.println("Árbol vacío, no se puede eliminar " + cl);
            return;
        }

        // Llama al método recursivo para eliminar la clave
        removeRecursive(root, cl, null, -1);

        // Si la raíz queda vacía y tiene un hijo, actualizar la raíz
        if (root != null && root.count == 0 && root.childs.get(0) != null) {
            root = root.childs.get(0);
        }
    }

    // Método recursivo para eliminar una clave del árbol
    private void removeRecursive(BNode<E> current, E cl, BNode<E> parent, int parentIdx) {
        if (current == null) {
            System.out.println("Clave " + cl + " no encontrada");
            return;
        }

        Object[] result = current.searchNode(cl);
        boolean found = (boolean) result[0];
        int pos = (int) result[1];

        if (found) {
            // Caso 1: Clave encontrada en un nodo hoja
            if (current.childs.get(0) == null) {
                // Eliminar la clave desplazando las claves a la izquierda
                for (int i = pos; i < current.count - 1; i++) {
                    current.keys.set(i, current.keys.get(i + 1));
                }
                current.count--;
                current.keys.set(current.count, null);
            } else {
                // Caso 2: Clave en un nodo interno, reemplazar con predecesor
                E predecessor = getPredecessor(current, pos);
                current.keys.set(pos, predecessor);
                // Eliminar el predecesor recursivamente
                removeRecursive(current.childs.get(pos), predecessor, current, pos);
            }
        } else {
            // Caso 3: Buscar en el subárbol correspondiente
            removeRecursive(current.childs.get(pos), cl, current, pos);
        }

        // Verificar subdesbordamiento después de la eliminación
        if (current != root && current.count < minKeys()) {
            handleUnderflow(current, parent, parentIdx);
        }
    }

    // Encuentra la clave predecesora (mayor clave en el subárbol izquierdo)
    private E getPredecessor(BNode<E> current, int pos) {
        BNode<E> node = current.childs.get(pos);
        while (node.childs.get(node.count) != null) {
            node = node.childs.get(node.count);
        }
        return node.keys.get(node.count - 1);
    }

    // Maneja el subdesbordamiento de un nodo
    private void handleUnderflow(BNode<E> current, BNode<E> parent, int parentIdx) {
        // Intentar redistribuir con el hermano izquierdo
        if (parentIdx > 0 && parent.childs.get(parentIdx - 1).count > minKeys()) {
            redistribute(current, parent, parentIdx, true); // Hermano izquierdo
            return;
        }
        // Intentar redistribuir con el hermano derecho
        if (parentIdx < parent.count && parent.childs.get(parentIdx + 1).count > minKeys()) {
            redistribute(current, parent, parentIdx, false); // Hermano derecho
            return;
        }
        // Si no es posible redistribuir, fusionar con un hermano
        if (parentIdx > 0) {
            merge(parent, parentIdx - 1); // Fusionar con hermano izquierdo
        } else {
            merge(parent, parentIdx); // Fusionar con hermano derecho
        }
    }

    // Realiza redistribución de claves entre un nodo, su hermano y el padre
    private void redistribute(BNode<E> current, BNode<E> parent, int parentIdx, boolean fromLeft) {
        BNode<E> sibling = fromLeft ? parent.childs.get(parentIdx - 1) : parent.childs.get(parentIdx + 1);
        int parentKeyIdx = fromLeft ? parentIdx - 1 : parentIdx;

        if (fromLeft) {
            // Mover una clave del hermano izquierdo al nodo actual
            current.keys.set(current.count, parent.keys.get(parentKeyIdx));
            current.childs.set(current.count + 1, current.childs.get(current.count));
            for (int i = current.count - 1; i >= 0; i--) {
                current.keys.set(i + 1, current.keys.get(i));
                current.childs.set(i + 2, current.childs.get(i + 1));
            }
            current.childs.set(1, current.childs.get(0));
            current.childs.set(0, sibling.childs.get(sibling.count));
            current.count++;

            // Mover la clave del padre al nodo actual
            parent.keys.set(parentKeyIdx, sibling.keys.get(sibling.count - 1));
            sibling.keys.set(sibling.count - 1, null);
            sibling.childs.set(sibling.count, null);
            sibling.count--;
        } else {
            // Mover una clave del hermano derecho al nodo actual
            current.keys.set(current.count, parent.keys.get(parentKeyIdx));
            current.childs.set(current.count + 1, sibling.childs.get(0));
            current.count++;

            // Mover la clave del padre al nodo actual
            parent.keys.set(parentKeyIdx, sibling.keys.get(0));
            for (int i = 0; i < sibling.count - 1; i++) {
                sibling.keys.set(i, sibling.keys.get(i + 1));
                sibling.childs.set(i, sibling.childs.get(i + 1));
            }
            sibling.childs.set(sibling.count - 1, sibling.childs.get(sibling.count));
            sibling.keys.set(sibling.count - 1, null);
            sibling.childs.set(sibling.count, null);
            sibling.count--;
        }
    }

    // Fusiona un nodo con su hermano
    private void merge(BNode<E> parent, int parentIdx) {
        BNode<E> left = parent.childs.get(parentIdx);
        BNode<E> right = parent.childs.get(parentIdx + 1);

        // Mover la clave del padre al nodo izquierdo
        left.keys.set(left.count, parent.keys.get(parentIdx));
        left.count++;

        // Mover todas las claves y hijos del nodo derecho al nodo izquierdo
        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + i, right.keys.get(i));
            left.childs.set(left.count + i, right.childs.get(i));
        }
        left.childs.set(left.count + right.count, right.childs.get(right.count));
        left.count += right.count;

        // Eliminar la clave y el hijo derecho del padre
        for (int i = parentIdx; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
    }

    public static BTree<Integer> building_Btree() {
        int orden = 4;
        BTree<Integer> bTree = new BTree<>(orden);

        // Crear nodos hoja (nivel 2)
        BNode<Integer> n0 = new BNode<>(orden, 0);
        n0.keys.set(0, 1);
        n0.keys.set(1, 3);
        n0.keys.set(2, 8);
        n0.count = 3;

        BNode<Integer> n1 = new BNode<>(orden, 1);
        n1.keys.set(0, 12);
        n1.keys.set(1, 15);
        n1.count = 2;

        BNode<Integer> n3 = new BNode<>(orden, 3);
        n3.keys.set(0, 18);
        n3.keys.set(1, 19);
        n3.keys.set(2, 21);
        n3.count = 3;

        BNode<Integer> n4 = new BNode<>(orden, 4);
        n4.keys.set(0, 27);
        n4.keys.set(1, 28);
        n4.count = 2;

        BNode<Integer> n8 = new BNode<>(orden, 8);
        n8.keys.set(0, 33);
        n8.keys.set(1, 36);
        n8.keys.set(2, 39);
        n8.count = 3;

        BNode<Integer> n7 = new BNode<>(orden, 7);
        n7.keys.set(0, 42);
        n7.keys.set(1, 45);
        n7.count = 2;

        // Crear nodos intermedios (nivel 1)
        BNode<Integer> n2 = new BNode<>(orden, 2);
        n2.keys.set(0, 10);
        n2.keys.set(1, 16);
        n2.count = 2;
        n2.childs.set(0, n0);
        n2.childs.set(1, n1);
        n2.childs.set(2, n3);

        BNode<Integer> n5 = new BNode<>(orden, 5);
        n5.keys.set(0, 30);
        n5.keys.set(1, 40);
        n5.count = 2;
        n5.childs.set(0, n4);
        n5.childs.set(1, n8);
        n5.childs.set(2, n7);

        // Crear nodo raíz (nivel 0)
        BNode<Integer> n6 = new BNode<>(orden, 6);
        n6.keys.set(0, 25);
        n6.count = 1;
        n6.childs.set(0, n2);
        n6.childs.set(1, n5);

        bTree.setRoot(n6);

        return bTree;
    }

}