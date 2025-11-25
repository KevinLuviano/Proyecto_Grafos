package grafo;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseListener, MouseMotionListener {
    // Aquí guardo todos los nodos y enlaces que voy creando
    private Vector<Nodo> vectorNodos;
    private Vector<Enlace> vectorEnlaces;
    private Point p1, p2; // Puntos para cuando voy a crear una línea
    private Nodo nodoMover; // Nodo que estoy arrastrando
    private int iNodo;

    public Lienzo() {
        this.vectorNodos = new Vector<>();
        this.vectorEnlaces = new Vector<>();
        // Activo los "Listeners" para que funcione el mouse
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Estilos.COLOR_FONDO);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // IMPORTANTE: Primero pinto los enlaces y luego los nodos.
        // Si lo hago al revés, la línea queda encima del círculo y se ve feo.
        for (Enlace enlace : vectorEnlaces) {
            enlace.pintar(g);
        }
        for (Nodo nodos : vectorNodos) {
            nodos.pintar(g);
        }
    }

    // --- MÉTODOS PARA BORRAR ---

    public void eliminarNodoPorNombre(String nombreInput) {
        Nodo nodoAEliminar = null;
        // Recorro la lista buscando el nodo que tenga ese nombre
        for (Nodo n : vectorNodos) {
            if (n.getNombre().equalsIgnoreCase(nombreInput)) {
                nodoAEliminar = n;
                break; 
            }
        }
        if (nodoAEliminar != null) {
            vectorNodos.remove(nodoAEliminar); // Lo borro
            eliminarEnlacesHuérfanos(nodoAEliminar); // Borro también sus líneas
            repaint(); // Actualizo el dibujo
            JOptionPane.showMessageDialog(this, "Listo, nodo borrado.");
        } else {
            JOptionPane.showMessageDialog(this, "No encontré ese nodo.");
        }
    }

    public void eliminarEnlacePorNombre(String nombreInput) {
        Enlace enlaceAEliminar = null;
        for (Enlace e : vectorEnlaces) {
            if (e.getNombre().equalsIgnoreCase(nombreInput)) {
                enlaceAEliminar = e;
                break;
            }
        }
        if (enlaceAEliminar != null) {
            vectorEnlaces.remove(enlaceAEliminar);
            repaint();
            JOptionPane.showMessageDialog(this, "Arista borrada.");
        } else {
            JOptionPane.showMessageDialog(this, "No encontré esa arista.");
        }
    }

    // Este método es para que no queden líneas flotando si borro un nodo
    private void eliminarEnlacesHuérfanos(Nodo n) {
        Vector<Enlace> borrar = new Vector<>();
        for (Enlace e : vectorEnlaces) {
            // Si la línea empieza o termina en el nodo que borré, la marco para borrar
            if ((e.getX1() == n.getX() && e.getY1() == n.getY()) ||
                (e.getX2() == n.getX() && e.getY2() == n.getY())) {
                borrar.add(e);
            }
        }
        vectorEnlaces.removeAll(borrar);
    }
    
    public void reiniciarLienzo() {
        vectorNodos.clear(); // Borro todo
        vectorEnlaces.clear();
        p1 = null; p2 = null;
        repaint();
    }
    
    // Llamo a la clase GestorGrafo para hacer los cálculos
    public void invocarMatriz() {
        String res = GestorGrafo.obtenerMatrizAdyacencia(vectorNodos, vectorEnlaces);
        JOptionPane.showMessageDialog(null, res, "Matriz", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void invocarRecorridos() {
        if(vectorNodos.isEmpty()) return;
        String dfs = GestorGrafo.recorridoDFS(vectorNodos, vectorEnlaces);
        String bfs = GestorGrafo.recorridoBFS(vectorNodos, vectorEnlaces);
        JOptionPane.showMessageDialog(null, dfs + "\n\n" + bfs, "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- EVENTOS DEL MOUSE ---

    @Override
    public void mouseClicked(MouseEvent e) {
        // Clic Izquierdo: Crea un Nodo nuevo
        if (e.getButton() == MouseEvent.BUTTON1) { 
            String nombre = JOptionPane.showInputDialog("Nombre del nodo:");
            if(nombre != null && !nombre.trim().isEmpty()){
                this.vectorNodos.add(new Nodo(e.getX(), e.getY(), nombre));
                repaint();
            }
        }
        // Clic Derecho: Crea enlace. Necesito dos clics (origen y destino)
        if (e.getButton() == MouseEvent.BUTTON3) { 
            for (Nodo nodo : vectorNodos) {
                // Checo si hice clic dentro del área de un nodo
                if (new Rectangle(nodo.getX() - Nodo.d / 2, nodo.getY() - Nodo.d / 2, Nodo.d, Nodo.d).contains(e.getPoint())) {
                    if (p1 == null)
                        p1 = new Point(nodo.getX(), nodo.getY()); // Guardo el primer punto
                    else {
                        p2 = new Point(nodo.getX(), nodo.getY()); // Guardo el segundo
                        String nombre = JOptionPane.showInputDialog("Nombre del enlace:");
                        if(nombre != null) {
                             this.vectorEnlaces.add(new Enlace(p1.x, p1.y, p2.x, p2.y, nombre));
                             repaint();
                        }
                        p1 = null; // Reinicio para la próxima
                        p2 = null;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Cuando presiono el mouse, checo si atrapé algún nodo para moverlo
        int iN = 0;
        for (Nodo nodo : vectorNodos) {
            if (new Rectangle(nodo.getX() - Nodo.d / 2, nodo.getY() - Nodo.d / 2, Nodo.d, Nodo.d).contains(e.getPoint())) {
                nodoMover = nodo;
                iNodo = iN;
                break;
            }
            iN++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        nodoMover = null; // Suelto el nodo
        iNodo = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Si tengo un nodo atrapado, actualizo su posición mientras muevo el mouse
        if (nodoMover != null) {
            this.vectorNodos.set(iNodo, new Nodo(e.getX(), e.getY(), nodoMover.getNombre()));
            int iE = 0;
            // Tengo que actualizar también las líneas que están pegadas a ese nodo
            for (Enlace enlace : vectorEnlaces) {
                if (new Rectangle(enlace.getX1() - Nodo.d / 2, enlace.getY1() - Nodo.d / 2, Nodo.d, Nodo.d).contains(e.getPoint())) {
                    this.vectorEnlaces.set(iE, new Enlace(e.getX(), e.getY(), enlace.getX2(), enlace.getY2(), enlace.getNombre()));
                } else if (new Rectangle(enlace.getX2() - Nodo.d / 2, enlace.getY2() - Nodo.d / 2, Nodo.d, Nodo.d).contains(e.getPoint())) {
                    this.vectorEnlaces.set(iE, new Enlace(enlace.getX1(), enlace.getY1(), e.getX(), e.getY(), enlace.getNombre()));
                }
                iE++;
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
