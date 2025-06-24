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

    

}
