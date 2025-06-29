package btree;

import java.util.ArrayList;

// Clase genérica BNode para representar un nodo de un árbol B
public class BNode<E extends Comparable<E>> {
    // Lista de claves almacenadas en el nodo
    protected ArrayList<E> keys;

    // Lista de hijos del nodo (puede contener n + 1 hijos si hay n claves)
    protected ArrayList<BNode<E>> childs;

    // Número de claves actualmente almacenadas en el nodo
    protected int count;

    // Identificador único del nodo
    protected int idNode;

    // Contador estático que incrementa con cada nuevo nodo creado
    private static int idCounter = 0;

    // Constructor que inicializa un nodo con capacidad para n claves
    public BNode(int n) {
        this(n, ++idCounter); // Usa el contador estático por defecto
    }

    // Constructor que permite especificar un ID personalizado
    public BNode(int n, int idNode) {
        // Inicializa la lista de claves con capacidad n
        this.keys = new ArrayList<E>(n);
        
        // Inicializa la lista de hijos con capacidad n + 1
        this.childs = new ArrayList<BNode<E>>(n + 1);

        // Inicializa el contador de claves en 0
        this.count = 0;

        // Asigna el ID especificado
        this.idNode = idNode;

        // Llena las listas con valores null como posición inicial
        for (int i = 0; i < n; i++) {
            this.keys.add(null);    // Espacio reservado para las claves
            this.childs.add(null);  // Espacio reservado para los hijos
        }

        // Agrega el hijo extra (n + 1 en total)
        this.childs.add(null);
    }

    // Método que verifica si el nodo está lleno (ya no se pueden insertar más claves)
    public boolean nodeFull() {
        return count == keys.size();
    }

    // Método que verifica si el nodo está vacío (no tiene claves)
    public boolean nodeEmpty() {
        return count == 0;
    }

    public Object[] searchNode(E claveBuscada) {
    int posicion = 0;

    while (posicion < count) {
        E actual = keys.get(posicion);
        if (actual == null) break;

        int cmp = claveBuscada.compareTo(actual);

        if (cmp == 0) {
            return new Object[]{true, posicion};
        } else if (cmp < 0) {
            break;
        }

        posicion++;
    }

    return new Object[]{false, posicion};
    }


    // Método toString que devuelve una cadena con el ID del nodo y sus claves actuales
    @Override
    public String toString() {
        // StringBuilder permite construir texto de forma eficiente
        StringBuilder constructorTexto = new StringBuilder();

        // Agrega el ID del nodo
        constructorTexto.append("Nodo ID: ").append(idNode).append(" | Claves: ");

        // Agrega todas las claves actuales del nodo
        for (int i = 0; i < count; i++) {
            constructorTexto.append(keys.get(i)).append(" ");
        }

        // Elimina espacios innecesarios al final y retorna la cadena
        String texto = constructorTexto.toString().trim();
        return texto;
    }
}