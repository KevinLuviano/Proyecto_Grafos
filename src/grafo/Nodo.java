package grafo;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Nodo {
    private int x, y; // Coordenadas
    private String nombre;
    public static final int d = 60; // Tamaño del círculo (diámetro)

    public Nodo(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // 1. Dibujo el círculo relleno de color
        g2d.setColor(Estilos.COLOR_NODO);
        g2d.fillOval(this.x - d / 2, this.y - d / 2, d, d);

        // 2. Dibujo el borde del círculo
        g2d.setColor(Estilos.COLOR_BORDE);
        g2d.drawOval(this.x - d / 2, this.y - d / 2, d, d);

        // 3. Pongo el nombre del nodo
        g2d.setColor(Estilos.COLOR_TEXTO);
        g2d.setFont(Estilos.FUENTE_NODO);
        
        // Esto es para que el texto quede JUSTO en el centro del círculo
        // Mido qué tan ancho es el texto y le resto la mitad a la posición X
        int anchoTexto = g2d.getFontMetrics().stringWidth(nombre);
        int altoTexto = g2d.getFontMetrics().getAscent();
        
        g2d.drawString(nombre, x - anchoTexto / 2, y + (altoTexto / 4));
    }

    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}