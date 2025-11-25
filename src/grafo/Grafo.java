package grafo;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grafo {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Proyecto de Grafos");
        Lienzo lienzo = new Lienzo(); 
        
        JMenuBar barra = new JMenuBar();
        JMenu menuEdicion = new JMenu("Edición");
        JMenu menuAlgoritmos = new JMenu("Algoritmos");
        
        JMenuItem itemDelNodo = new JMenuItem("Eliminar Nodo (por Nombre)");
        itemDelNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Escribe el nombre del Nodo a borrar:");
                if(nombre != null && !nombre.trim().isEmpty()) lienzo.eliminarNodoPorNombre(nombre);
            }
        });
        
        JMenuItem itemDelArista = new JMenuItem("Eliminar Arista (por Nombre)");
        itemDelArista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog("Escribe el nombre de la Arista a borrar:");
                if(nombre != null && !nombre.trim().isEmpty()) lienzo.eliminarEnlacePorNombre(nombre);
            }
        });
        
        JMenuItem itemLimpiar = new JMenuItem("Reiniciar Todo");
        itemLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lienzo.reiniciarLienzo();
            }
        });
        
        menuEdicion.add(itemDelNodo);
        menuEdicion.add(itemDelArista);
        menuEdicion.addSeparator();
        menuEdicion.add(itemLimpiar);
        
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
        
        barra.add(menuEdicion);
        barra.add(menuAlgoritmos);
        ventana.setJMenuBar(barra);
        
        ventana.add(lienzo);
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null); 
        ventana.setVisible(true);
    }
}