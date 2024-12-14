import java.util.*;

class Nodo {
    int id;
    int distancia;
    Nodo anterior;

    public Nodo(int id) {
        this.id = id;
        this.distancia = Integer.MAX_VALUE; // Distancia inicial infinita
        this.anterior = null;
    }
}

class Arista {
    int idOrigen;
    int idDestino;
    int peso;

    public Arista(int idOrigen, int idDestino, int peso) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.peso = peso;
    }
}

public class Grafo {
    private Map<Integer, Nodo> nodos;
    private Map<Integer, List<Arista>> aristas;

    public Grafo() {
        this.nodos = new HashMap<>();
        this.aristas = new HashMap<>();
    }

    public void agregarNodo(int id) {
        if (!nodos.containsKey(id)) {
            nodos.put(id, new Nodo(id));
        }
    }

    public void agregarArista(int idOrigen, int idDestino, int peso) {
        if (!nodos.containsKey(idOrigen) || !nodos.containsKey(idDestino)) {
            throw new IllegalArgumentException("Ambos nodos deben existir antes de agregar una arista.");
        }
        Arista arista = new Arista(idOrigen, idDestino, peso);
        aristas.computeIfAbsent(idOrigen, k -> new ArrayList<>()).add(arista);
    }

    public List<Integer> encontrarCaminoMasCorto(int idOrigen, int idDestino) {
        if (!nodos.containsKey(idOrigen) || !nodos.containsKey(idDestino)) {
            throw new IllegalArgumentException("Los nodos origen y destino deben existir.");
        }

        // Restablecer el estado de los nodos
        for (Nodo nodo : nodos.values()) {
            nodo.distancia = Integer.MAX_VALUE;
            nodo.anterior = null;
        }

        Nodo origen = nodos.get(idOrigen);
        origen.distancia = 0;

        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.distancia));
        cola.add(origen);

        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();

            if (nodoActual.id == idDestino) {
                List<Integer> camino = new ArrayList<>();
                while (nodoActual != null) {
                    camino.add(0, nodoActual.id); // Insertar al inicio
                    nodoActual = nodoActual.anterior;
                }
                return camino;
            }

            if (aristas.containsKey(nodoActual.id)) {
                for (Arista arista : aristas.get(nodoActual.id)) {
                    Nodo vecino = nodos.get(arista.idDestino);
                    int nuevaDistancia = nodoActual.distancia + arista.peso;

                    if (nuevaDistancia < vecino.distancia) {
                        vecino.distancia = nuevaDistancia;
                        vecino.anterior = nodoActual;

                        // Actualizar la cola de prioridad
                        cola.remove(vecino);
                        cola.add(vecino);
                    }
                }
            }
        }

        return null; // No se encontró un camino al destino
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // Agregar nodos
        grafo.agregarNodo(1);
        grafo.agregarNodo(2);
        grafo.agregarNodo(3);
        grafo.agregarNodo(4);
        grafo.agregarNodo(5);

        // Agregar aristas con pesos
        grafo.agregarArista(1, 2, 5);
        grafo.agregarArista(1, 3, 3);
        grafo.agregarArista(2, 4, 2);
        grafo.agregarArista(3, 4, 1);
        grafo.agregarArista(4, 5, 4);

        // Buscar el camino más corto
        try {
            List<Integer> camino = grafo.encontrarCaminoMasCorto(1, 5);

            if (camino != null) {
                System.out.println("Camino más corto: " + camino);
            } else {
                System.out.println("No se encontró un camino al destino.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
