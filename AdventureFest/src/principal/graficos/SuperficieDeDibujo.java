/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import principal.Constantes;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.maquinaEstado.GestorDeEstados;

/**
 *
 * @author Daniel
 */
public class SuperficieDeDibujo extends Canvas {

    private final int ancho, alto;
    //private Teclado teclado;
    private Raton raton;

    public SuperficieDeDibujo(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;

        raton = new Raton(this);

        setCursor(raton.getCursor());
        setIgnoreRepaint(true);
        setPreferredSize(new Dimension(ancho, alto));
        addKeyListener(GestorControles.teclado);
        setFocusable(true);
        requestFocus();
    }
    public void actualizar(){
        raton.actualizar(this);
    }

    public void dibujar(final GestorDeEstados ge) {
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
        if (Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
             g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
        }
        ge.dibujar(g);
        raton.dibujar(g);
//        g.drawString("EscalacoX: "+ Constantes.FACTOR_ESCALADO_X, 20, 120);
//        g.drawString("EscalacoY: "+ Constantes.FACTOR_ESCALADO_Y, 20, 140);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        buffer.show();
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

}
