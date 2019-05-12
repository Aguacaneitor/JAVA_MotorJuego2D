/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.entes.trampas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import principal.Constantes;
import principal.entes.Jugador;
import principal.sprites.HojaSprites;

/**
 *
 * @author Daniel
 */
public class Trampas {

    private Point posicion;
    private final int direccion;
    public Rectangle hitBox;
    private ArrayList<Proyectil> proyectiles = new ArrayList<Proyectil>();
    private Jugador jugador;
    private final int tipo = 0;
    private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private int contador;
    //Sprites:
    private final BufferedImage imagen;
    private final HojaSprites hojaImagenTrampas;

    public Trampas(int posicionX, int posicionY, final int direccion, Jugador jugador) {
        posicion = new Point(posicionX * 32 - 10, posicionY * 32 + 28);
        this.direccion = direccion;
        this.jugador = jugador;
        hitBox = new Rectangle(posicion.x, posicion.y, Constantes.LADO_SPRITES, Constantes.LADO_SPRITES);
        //sprites:
        hojaImagenTrampas = new HojaSprites("/imagenes/objetos/HojaTrampas.png", Constantes.LADO_SPRITES, false);
        imagen = hojaImagenTrampas.getSprite(direccion - 1).getImagen();
    }

    public void actualizar() {
        actualizarPosicion((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
        if (contador == 0) {
            if (direccion == 2 && jugador.getPosicionX() + 200 >= posicion.x && !(jugador.getPosicionX() > posicion.x)){
            disparar();
        }
            if (direccion == 1&& jugador.getPosicionX() - 200 <= posicion.x && !(jugador.getPosicionX() < posicion.x)){
            disparar();
        }
        }
        if (contador != 0) {
            contador++;
            if (contador >= 100) {
                contador = 0;
            }
        }
        for (int indice = 0; indice < proyectiles.size(); indice++) {
            proyectiles.get(indice).actualizar((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
            hitJugador(indice, proyectiles.get(indice).hitBox);
//            jugador.colisionExterna(proyectiles.get(indice).hitBox);
            validarDistanciaDisparo(indice);
        }
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, hitBox.x, hitBox.y, null);
        for (int indice = 0; indice < proyectiles.size(); indice++) {
            proyectiles.get(indice).dibujar(g);
        }
    }

    public void disparar() {
        if (jugador.getPosicionY() >= posicion.y - 35 && jugador.getPosicionY() <= posicion.y + 25) {
            Proyectil disparo = new Proyectil(posicion.x, posicion.y, direccion, 1);
            proyectiles.add(disparo);
            contador++;
        }
    }

    private void validarDistanciaDisparo(final int indice) {
        final Proyectil bala = proyectiles.get(indice);
        if (bala.getDistanciaRecorrida() >= 100) {
            proyectiles.remove(indice);
        } else {
            if (bala.isGolpe()) {
                proyectiles.remove(indice);
            }
        }
    }

    private void hitJugador(final int indice, final Rectangle bala) {
        if (bala.intersects(jugador.getLIMITE_IZQUIERDA()) || bala.intersects(jugador.getLIMITE_DERECHA())) {
            jugador.aumentarVidaActual(-10);
            jugador.verificarEmpuje(direccion);
            proyectiles.get(indice).setGolpe(true);            
        }
//        if (bala.intersects(jugador.getLIMITE_DERECHA())) {
//            jugador.aumentarVidaActual(-10);
//            proyectiles.get(indice).setGolpe(true);
//        }
    }

    private void actualizarPosicion(final int posicionX, final int posicionY) {
        hitBox.x = posicion.x - posicionX + MARGEN_X;
        hitBox.y = posicion.y - posicionY + MARGEN_Y;

    }

}
