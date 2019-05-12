/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.entes.trampas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.sprites.HojaSprites;

/**
 *
 * @author Daniel
 */
public class Proyectil {

    private Point posicion;
    private final Point posicionInicial;
    private final int direccion;
    private final int tipo;
    private int distanciaRecorrida, estadoAnimacion;
    public Rectangle hitBox;
    private boolean golpe;
    private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    //sprites:
    private BufferedImage imagenActual;
    private final HojaSprites hojaProyectiles;

    public Proyectil(final int posicionX, final int  posicionY, final int direccion, final int tipo) {

        posicion = new Point(0, 0);
        posicionInicial = new Point(posicionX + 3, posicionY +3);
        this.direccion = direccion;
        this.tipo = tipo;
        this.hitBox = new Rectangle(posicionInicial.x, posicionInicial.y, 14, 14);
        distanciaRecorrida = 0;
        golpe = false;
        hojaProyectiles = new HojaSprites("/imagenes/objetos/ProyectilesFuego.png", 16, false);
        estadoAnimacion = 0;
    }

    public void actualizar(final int posicionX, final int posicionY) {
//        if (Constantes.APS % 10  == 0){
        disparo();
        actualizarPosicion(posicionX, posicionY);
//        }
    }

    public void dibujar(Graphics g) {
        if (distanciaRecorrida % 15 == 0){
            estadoAnimacion++;
            if (estadoAnimacion >= 4){
                estadoAnimacion = 0;
            }
        }
        imagenActual = hojaProyectiles.getSprite(direccion - 1, estadoAnimacion).getImagen();
        g.drawImage(imagenActual, hitBox.x, hitBox.y, null);
//        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    private void disparo() {
        switch (direccion) {
            case 1:
                posicion.x+= 2;
                break;
            case 2:
                posicion.x-= 2;
                break;
        }
        distanciaRecorrida++;
    }
     public int getDistanciaRecorrida (){
         return distanciaRecorrida;
     }

    public boolean isGolpe() {
        return golpe;
    }

    public void setGolpe(boolean golpe) {
        this.golpe = golpe;
    }
    private void actualizarPosicion (final int posicionX, final int posicionY){
        hitBox.x = posicionInicial.x - posicionX + MARGEN_X + posicion.x;
        hitBox.y = posicionInicial.y - posicionY + MARGEN_Y + posicion.y;
        
    }
     
}
