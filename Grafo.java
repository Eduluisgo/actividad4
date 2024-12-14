import java.util.*;

class Nodo {
    int id;
    int distancia;
    Nodo anterior;

    public Nodo(int id) {
        this.id = id;
        this.distancia = Integer.MAX_VALUE;
        this.anterior = null;
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
        nodos.put(id, new Nodo(id));
    }

    public void agregarArista(int idOrigen, int idDestino, int peso) {
        Arista arista = new Arista(idOrigen, idDestino, peso);
        aristas.computeIfAbsent(idOrigen, k -> new ArrayList<>()).add(arista);
    }

    public List<Integer> encontrarCaminoMasCorto(int idOrigen, int idDestino) {
        Nodo origen = nodos.get(idOrigen);
        Nodo destino = nodos.get(idDestino);

        if (origen == null || destino == null) {
            return null;
        }

        origen.distancia = 0;
        PriorityQueue<Nodo> cola = new PriorityQueue<>((n1, n2) -> n1.distancia - n2.distancia);
        cola.add(origen);

        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();

            if (nodoActual == destino) {
                List<Integer> camino = new ArrayList<>();
                while (nodoActual != null) {
                    camino.add(0, nodoActual.id);
                    nodoActual = nodoActual.anterior;
                }
                return camino;
            }

            for (Arista arista : aristas.get(nodoActual.id)) {
                Nodo vecino = nodos.get(arista.idDestino);
                int distanciaNueva = nodoActual.distancia + arista.peso;
                if (distanciaNueva < vecino.distancia) {
                    vecino.distancia = distanciaNueva;
                    vecino.anterior = nodoActual;
                    cola.add(vecino);
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        grafo.agregarNodo(1);
        grafo.agregarNodo(2);
        grafo.agregarNodo(3);
        grafo.agregarNodo(4);
        grafo.agregarNodo(5);

        grafo.agregarArista(1, 2, 5);
        grafo.agregarArista(1, 3, 3);
        grafo.agregarArista(2, 4, 2);
        grafo.agregarArista(3, 4, 1);
        grafo.agregarArista(4, 5, 4);

        List<Integer> camino = grafo.encontrarCaminoMasCorto(1, 5);

        if (camino != null) {
            System.out.println("Camino más corto: " + camino);
        } else {
            System.out.println("No se ha encontrado el camino más corto");
        }
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