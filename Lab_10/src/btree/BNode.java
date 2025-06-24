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
        // Inicializa la lista de claves con capacidad n
        this.keys = new ArrayList<E>(n);
        
        // Inicializa la lista de hijos con capacidad n + 1
        this.childs = new ArrayList<BNode<E>>(n + 1);

        // Inicializa el contador de claves en 0
        this.count = 0;

        // Asigna un ID único al nodo y lo incrementa globalmente
        this.idNode = ++idCounter;

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

    // Método para buscar una clave dentro del nodo
    // Retorna: [true, posición] si se encuentra
    //          [false, posición] si no se encuentra pero da la posición donde debería ir
    public Object[] searchNode(E claveBuscada) {
        int posicion = 0;

        // Recorre las claves hasta que encuentra una mayor o igual
        while (posicion < count && claveBuscada.compareTo(keys.get(posicion)) > 0) {
            posicion++;
        }

        // Si la clave fue encontrada en esa posición
        if (posicion < count && claveBuscada.compareTo(keys.get(posicion)) == 0) {
            return new Object[]{true, posicion};
        }

        // Si no fue encontrada, retorna la posición donde se debería insertar o seguir buscando
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
