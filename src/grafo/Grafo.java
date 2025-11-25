package grafo;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase principal donde arranca todo (Main)
public class Grafo {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Proyecto de Grafos");
        Lienzo lienzo = new Lienzo(); // Aquí creo el panel donde voy a dibujar
        
        // --- AQUÍ ARMO EL MENÚ DE ARRIBA ---
        JMenuBar barra = new JMenuBar();
        JMenu menuEdicion = new JMenu("Edición");
        JMenu menuAlgoritmos = new JMenu("Algoritmos");
        
        // Botón para borrar Nodo
        JMenuItem itemDelNodo = new JMenuItem("Eliminar Nodo (por Nombre)");
        itemDelNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Escribe el nombre del Nodo a borrar:");
                if(nombre != null && !nombre.trim().isEmpty()) lienzo.eliminarNodoPorNombre(nombre);
            }
        });
        
        // Botón para borrar Arista
        JMenuItem itemDelArista = new JMenuItem("Eliminar Arista (por Nombre)");
        itemDelArista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Escribe el nombre de la Arista a borrar:");
                if(nombre != null && !nombre.trim().isEmpty()) lienzo.eliminarEnlacePorNombre(nombre);
            }
        });
        
        // Botón para limpiar todo
        JMenuItem itemLimpiar = new JMenuItem("Reiniciar Todo");
        itemLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lienzo.reiniciarLienzo();
            }
        });
        
        // Agrego los botones al menú Edición
        menuEdicion.add(itemDelNodo);
        menuEdicion.add(itemDelArista);
        menuEdicion.addSeparator(); // Una rayita para separar
        menuEdicion.add(itemLimpiar);
        
        // Botones de algoritmos (Matriz y Recorridos)
        JMenuItem itemMatriz = new JMenuItem("Ver Matriz de Adyacencia");
        itemMatriz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lienzo.invocarMatriz();
            }
        });

        JMenuItem itemRecorridos = new JMenuItem("Ver Recorridos BFS/DFS");
        itemRecorridos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lienzo.invocarRecorridos();
            }
        });

        menuAlgoritmos.add(itemMatriz);
        menuAlgoritmos.add(itemRecorridos);
        
        // Pongo los menús en la barra
        barra.add(menuEdicion);
        barra.add(menuAlgoritmos);
        ventana.setJMenuBar(barra);
        
        // Configuración de la ventana
        ventana.add(lienzo);
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null); // Esto centra la ventana en la pantalla
        ventana.setVisible(true);
    }
}
