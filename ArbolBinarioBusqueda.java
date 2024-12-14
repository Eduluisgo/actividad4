class Nodo {
    int valor;
    Nodo izquierdo, derecho;

    public Nodo(int valor) {
        this.valor = valor;
        izquierdo = derecho = null;
    }
}

public class ArbolBinarioBusqueda {
    private Nodo raiz;

    public ArbolBinarioBusqueda() {
        raiz = null;
    }

    // Método para insertar un nuevo valor en el árbol
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        }

        return nodo;
    }

    // Método para buscar un valor en el árbol
    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return false;
        }

        if (valor == nodo.valor) {
            return true;
        }

        if (valor < nodo.valor) {
            return buscarRecursivo(nodo.izquierdo, valor);
        }

        return buscarRecursivo(nodo.derecho, valor);
    }

    // Método para recorrer el árbol en orden
    public void recorrerEnOrden() {
        recorrerEnOrdenRecursivo(raiz);
    }

    private void recorrerEnOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            recorrerEnOrdenRecursivo(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            recorrerEnOrdenRecursivo(nodo.derecho);
        }
    }

    public static void main(String[] args) {
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        // Insertar elementos
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        // Buscar elementos
        System.out.println("Buscar 40: " + arbol.buscar(40)); // true
        System.out.println("Buscar 90: " + arbol.buscar(90)); // false

        // Recorrer el árbol
        System.out.print("Recorrido en orden: ");
        arbol.recorrerEnOrden(); // 20 30 40 50 60 70 80
        System.out.println();
    }
}