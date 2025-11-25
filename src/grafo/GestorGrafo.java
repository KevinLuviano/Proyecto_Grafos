package grafo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

// En esta clase puse toda la lógica matemática (Matrices y Recorridos)
// para no llenar de código la parte gráfica
public class GestorGrafo {

    // Función pequeña para saber si dos nodos están unidos por una línea
    private static boolean sonAdyacentes(Nodo n1, Nodo n2, Vector<Enlace> enlaces) {
        for (Enlace e : enlaces) {
            // Reviso si hay una linea que vaya de n1 a n2 O de n2 a n1
            if ((e.getX1() == n1.getX() && e.getY1() == n1.getY() && e.getX2() == n2.getX() && e.getY2() == n2.getY()) ||
                (e.getX1() == n2.getX() && e.getY1() == n2.getY() && e.getX2() == n1.getX() && e.getY2() == n1.getY())) {
                return true;
            }
        }
        return false;
    }

    // Método para crear la Matriz de Adyacencia
    public static String obtenerMatrizAdyacencia(Vector<Nodo> nodos, Vector<Enlace> enlaces) {
        int n = nodos.size();
        int[][] matriz = new int[n][n];
        StringBuilder sb = new StringBuilder("=== Matriz de Adyacencia ===\n\n   ");
        
        // Pongo los nombres arriba
        for (Nodo nodo : nodos) sb.append(nodo.getNombre()).append(" ");
        sb.append("\n");

        // Uso dos ciclos FOR para comparar todos los nodos contra todos
        for (int i = 0; i < n; i++) {
            sb.append(nodos.get(i).getNombre()).append("| ");
            for (int j = 0; j < n; j++) {
                // Si son adyacentes pongo un 1, si no un 0
                matriz[i][j] = sonAdyacentes(nodos.get(i), nodos.get(j), enlaces) ? 1 : 0;
                sb.append(matriz[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Algoritmo DFS (Profundidad)
    // Este algoritmo se va hasta el fondo de una rama antes de volver
    public static String recorridoDFS(Vector<Nodo> nodos, Vector<Enlace> enlaces) {
        if (nodos.isEmpty()) return "Grafo vacío";
        boolean[] visitados = new boolean[nodos.size()]; // Lista para no repetir nodos
        StringBuilder sb = new StringBuilder("Recorrido DFS (Profundidad): ");
        
        // Llamo a la función recursiva empezando en el nodo 0
        dfsRecursivo(0, nodos, enlaces, visitados, sb);
        return sb.toString();
    }

    // Método recursivo para DFS
    private static void dfsRecursivo(int indice, Vector<Nodo> nodos, Vector<Enlace> enlaces, boolean[] visitados, StringBuilder sb) {
        visitados[indice] = true; // Lo marco como visitado
        sb.append(nodos.get(indice).getNombre()).append(" -> ");
        
        // Busco vecinos que no haya visitado aún
        for (int i = 0; i < nodos.size(); i++) {
            if (!visitados[i] && sonAdyacentes(nodos.get(indice), nodos.get(i), enlaces)) {
                dfsRecursivo(i, nodos, enlaces, visitados, sb); // Se llama a sí misma (Recursividad)
            }
        }
    }
    
    // Algoritmo BFS (Anchura)
    // Este va visitando por niveles (vecinos, luego vecinos de vecinos)
    public static String recorridoBFS(Vector<Nodo> nodos, Vector<Enlace> enlaces) {
        if (nodos.isEmpty()) return "Grafo vacío";
        StringBuilder sb = new StringBuilder("Recorrido BFS (Anchura): ");
        boolean[] visitados = new boolean[nodos.size()];
        
        // Uso una COLA (Queue) porque necesito que el primero que entra sea el primero que sale
        Queue<Integer> cola = new LinkedList<>();

        visitados[0] = true;
        cola.add(0); // Empiezo con el primer nodo
        
        while (!cola.isEmpty()) {
            int indiceActual = cola.poll(); // Saco el nodo de la cola
            Nodo nActual = nodos.get(indiceActual);
            sb.append("[").append(nActual.getNombre()).append("] ");
            
            // Agrego a la cola a todos los vecinos que no he visitado
            for (int i = 0; i < nodos.size(); i++) {
                if (!visitados[i] && sonAdyacentes(nActual, nodos.get(i), enlaces)) {
                    visitados[i] = true;
                    cola.add(i);
                }
            }
        }
        return sb.toString();
    }
}
