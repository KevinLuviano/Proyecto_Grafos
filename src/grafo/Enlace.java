// Alexia
package grafo;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
public class Enlace {
    private int x1, y1, x2, y2;
    private String nombre;
    public Enlace(int x1, int y1, int x2, int y2, String nombre) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.nombre = nombre;
    }
    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Esto activa el "Antialiasing" para que la línea no se vea pixeleada
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Estilos.COLOR_ENLACE);
        g2d.drawLine(x1, y1, x2, y2); // Dibuja la línea de punto A a punto B
        g2d.setFont(Estilos.FUENTE_ENLACE);
        // Aquí calculo la mitad de la línea para poner el nombre/peso ahí
        // Básicamente busco el punto medio entre x1 y x2, y entre y1 y y2
        if (x1 > x2 && y1 > y2) {
            g.drawString(nombre, x1 - Math.abs((x1 - x2) / 2), y1 - Math.abs((y1 - y2) / 2));
        }
        if (x1 < x2 && y1 < y2) {
            g.drawString(nombre, x2 - Math.abs((x1 - x2) / 2), y2 - Math.abs((y1 - y2) / 2));
        }
        if (x1 > x2 && y1 < y2) {
            g.drawString(nombre, x1 - Math.abs((x1 - x2) / 2), y2 - Math.abs((y1 - y2) / 2));
        }
        if (x1 < x2 && y1 > y2) {
            g.drawString(nombre, x2 - Math.abs((x1 - x2) / 2), y1 - Math.abs((y1 - y2) / 2));
        }
    }
    // Getters y Setters para poder mover las líneas cuando muevo los nodos
    public int getX1() { 
        return x1; 
    }
    public int getY1() { 
        return y1; 
    }
    public int getX2() { 
        return x2; 
    }
    public int getY2() { 
        return y2; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre;
    }
    public void setX1(int x1) { 
        this.x1 = x1; 
    }
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public void setX2(int x2) { 
        this.x2 = x2; 
    }
    public void setY2(int y2) { 
        this.y2 = y2; 
    }
}
