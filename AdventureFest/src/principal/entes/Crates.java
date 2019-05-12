/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.mapas.Mapas;
import principal.sprites.HojaSprites;

/**
 *
 * @author Daniel
 */
public class Crates {

    private double posicionX;
    private double posicionY;
    private long referencia, referencia2;
    private Mapas mapa;
    private HojaSprites sprite;
    private static int capaDeDibujo;
    private Jugador jugador;
    BufferedImage imagenActual;
    private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private Rectangle colicion;
    private boolean empujando, colicionIzquierda, colicionDerecha, colicionAbajo, colicionArriba;

    public Crates(double posicionX, double posicionY, Mapas mapa, Jugador jugador) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.mapa = mapa;
        this.jugador = jugador;
        sprite = new HojaSprites("/imagenes/objetos/Crate.png", 64, false);
        capaDeDibujo = 1;
        imagenActual = sprite.getSprite(0).getImagen();
        empujando = false;
        actualizarColicion((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
    }

    public void dibujar(Graphics g, final int posicionJugadorX, final int posicionJugadorY) {
        int posicionActualX = (int) posicionX - posicionJugadorX + MARGEN_X;
        int posicionActualY = (int) posicionY - posicionJugadorY + MARGEN_Y;
        g.drawImage(imagenActual, posicionActualX, posicionActualY, null);
        g.setColor(Color.BLUE);
        g.drawRect(colicion.x, colicion.y, colicion.width, colicion.height);
        if (empujando) {
            g.drawString("EMPUJANDO", 20, 400);
        }

    }

    public void actualizar() {
        setCapaDeDibujo();
        actualizarColicion((int) jugador.getPosicionX(), (int) jugador.getPosicionY());

    }

    public int getCapaDeDibujo() {
        return capaDeDibujo;
    }

    public void setCapaDeDibujo() {
        if (jugador.getPosicionY() + 10 > posicionY) {
            this.capaDeDibujo = jugador.getCapaDedibujo() - 1;
        }
        if (jugador.getPosicionY() + 10 < posicionY) {
            this.capaDeDibujo = jugador.getCapaDedibujo();
        }
    }

    private void actualizarColicion(final int posicionJugadorX, final int posicionJugadorY) {
        int posicionActualX = (int) posicionX - posicionJugadorX + MARGEN_X;
        int posicionActualY = (int) posicionY - posicionJugadorY + MARGEN_Y;
        final Rectangle area = new Rectangle(posicionActualX + 16, posicionActualY + 16, 32, 32);
        colicion = area;
        if (colicion.intersects(jugador.getLIMITE_DERECHA()) || colicion.intersects(jugador.getLIMITE_ABAJO()) || colicion.intersects(jugador.getLIMITE_ARRIBA()) || colicion.intersects(jugador.getLIMITE_IZQUIERDA())) {
            empujando = true;
            long tiempoInicio = System.nanoTime();
            referencia = tiempoInicio -referencia2;
            referencia2 = tiempoInicio;
        } else {
            if (referencia >=2000000 ){
            empujando = false;
            jugador.resetVelocidad();
            }
        }
        empujando();

        if (!empujando) {
            jugador.setColisionEXArriba(false);
            jugador.setColisionEXAbajo(false);
            jugador.setColisionEXDerecha(false);
            jugador.setColisionEXIzquierda(false);

        }
    }

    public void moverCrateHorizontal(double posicionX) {
        if (posicionX > 0 && !colicionDerecha) {
            this.posicionX += posicionX;
        }
        if (posicionX < 0 && !colicionIzquierda) {
            this.posicionX += posicionX;
        }
    }

    public void moverCrateVertical(double posicionY) {
        if (posicionY > 0 && !colicionArriba) {
            this.posicionY += posicionY;
        }
        if (posicionY < 0 && !colicionAbajo) {
            this.posicionY += posicionY;
        }
    }

    private void empujando() {
        int velocidadX = 0;
        int velocidadY = 0;
        if (empujando) {
            jugador.setVelocidad(0.6);
            switch (jugador.getDireccion()) {
                case 1:
                    moverCrateVertical(-jugador.getVelocidad());
                    velocidadY = -1;
                    break;
                case 2:
                    moverCrateHorizontal(jugador.getVelocidad());
                    velocidadX = 1;
                    break;
                case 3:
                    moverCrateHorizontal(-jugador.getVelocidad());
                    velocidadX = -1;
                    break;
                case 0:
                    moverCrateVertical(jugador.getVelocidad());
                    velocidadY = 1;
                    break;
            }
            enColision(velocidadX, velocidadY);
        }

    }

    private void enColision(final int velocidadX, final int velocidadY) {
        for (int r = 0; r < mapa.areaColisiones.size(); r++) {
            final Rectangle area = mapa.areaColisiones.get(r);

            final int origenX = area.x + velocidadX * (int) jugador.getVelocidad();
            final int origenY = area.y + velocidadY * (int) jugador.getVelocidad();
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
            if (colicion.intersects(areaFutura)) {
                if (colicion.x > areaFutura.x) {
                    jugador.setColisionEXIzquierda(true);
                    colicionIzquierda = true;
                }
                if (colicion.x < areaFutura.x) {
                    jugador.setColisionEXDerecha(true);
                    colicionDerecha = true;
                }
                if (colicion.y > areaFutura.y) {
                    jugador.setColisionEXAbajo(true);
                    colicionAbajo = true;
                }
                if (colicion.y < areaFutura.y) {
                    jugador.setColisionEXArriba(true);
                    colicionArriba = true;
                }
            }

        }
    }

}
