package btree; // Paquete donde se encuentra la clase

// Clase que representa un registro de estudiante con código y nombre
public class RegistroEstudiante implements Comparable<RegistroEstudiante> {
    // Atributos privados: código y nombre del estudiante
    private int codigo;
    private String nombre;

    // Constructor que inicializa el código y el nombre
    public RegistroEstudiante(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Método getter que devuelve el código del estudiante
    public int getCodigo() { 
        return codigo; 
    }

    // Método getter que devuelve el nombre del estudiante
    public String getNombre() { 
        return nombre; 
    }

    // Método para comparar estudiantes por su código (usado para ordenamiento)
    @Override
    public int compareTo(RegistroEstudiante otro) {
        return Integer.compare(this.codigo, otro.codigo);
    }

    // Representación en texto del objeto (útil para impresión)
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }

    // Método para verificar igualdad entre dos objetos RegistroEstudiante
    @Override
    public boolean equals(Object obj) {
        // Si el objeto no es de tipo RegistroEstudiante, retorna false
        if (!(obj instanceof RegistroEstudiante)) return false;
        RegistroEstudiante otro = (RegistroEstudiante) obj;

        // Dos estudiantes se consideran iguales si tienen el mismo código
        return this.codigo == otro.codigo;
    }

    // Método que devuelve un código hash basado en el código del estudiante
    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }
}
