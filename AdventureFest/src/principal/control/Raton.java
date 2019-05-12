/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import principal.Constantes;
import principal.graficos.SuperficieDeDibujo;
import principal.herramientas.CargadorRecursos;

/**
 *
 * @author Daniel
 */
public class Raton extends MouseAdapter {
    
    private Point posicion;
    private SuperficieDeDibujo sd;
    
    private final Cursor cursor;
    
    public Raton(SuperficieDeDibujo sd){
        Toolkit configuracion = Toolkit.getDefaultToolkit();
        
        BufferedImage icono = CargadorRecursos.cargaImagenCompatibleTraslucida("/imagenes/iconos/Cursor2.png");
        
        Point punta = new Point(0,0);
        
        this.cursor = configuracion.createCustomCursor(icono, punta, "Cursos por defecto");
        this.sd = sd;
        posicion = new Point();
        actualizarPosicion(sd);
        
    }
    public void actualizar(final SuperficieDeDibujo sd){
        actualizarPosicion(sd);
    }
    public void dibujar(Graphics g){
        g.setColor(Color.blue);
//        g.drawString("X: "+posicion.getX()+"|Y: "+posicion.getY(), (int)Math.round(posicion.getX()),(int)Math.round(posicion.getY()));
        g.drawString("X: "+posicion.getX()+"|Y: "+posicion.getY(), 20,200);
    }
    
    public Cursor getCursor(){
        return cursor;
    }
    
    private void actualizarPosicion(final SuperficieDeDibujo sd){
        final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(posicionInicial, sd);
        
        posicion.setLocation(posicionInicial.getX()/Constantes.FACTOR_ESCALADO_X,posicionInicial.getY()/Constantes.FACTOR_ESCALADO_Y);
        
    }
    
}
