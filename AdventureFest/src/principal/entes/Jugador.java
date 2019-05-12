/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.entes;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.control.GestorControles;
import principal.mapas.Mapas;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author Daniel
 */
public class Jugador {

    private double posicionX;
    private double posicionY;
    private double velocidad = 1;
    private final double velocidadInicial = 1;
    private double velocidadSalto = 1.5;
    private double referenciaGravedad;
    private HojaSprites sprite;
    private static int direccion, estadoAnimacion, estadoAnimacionAtaque, contador, direccionEmpuje, contadorEmpuje;
    private static double fuerzaCaida;
    private final int frecuenciaAnimación;
    BufferedImage imagenActual;
    private static boolean estado, enMovimiento, gravedad, empujado;
    private Mapas mapa;
    private boolean colisionDerecha, colisionIzquierda, colisionArriba, colisionAbajo, enElAire, colisionAbajoConAccion, colisionIzquierdaAccion, colisionDerechaAccion;
    private boolean colisionEXDerecha, colisionEXIzquierda, colisionEXArriba, colisionEXAbajo;
    //Desfase de Accion:
    private Point[][] desfaseDeMovimientoAccion;
    //FIN
    //bordes SLIME
    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_ANCHO_PANTALLA - 8, Constantes.CENTRO_ALTO_PANTALLA - 10, 15, 1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_ANCHO_PANTALLA - 8, Constantes.CENTRO_ALTO_PANTALLA + 15, 16, 1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_ANCHO_PANTALLA - 12, Constantes.CENTRO_ALTO_PANTALLA - 9, 1, 20);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_ANCHO_PANTALLA + 11, Constantes.CENTRO_ALTO_PANTALLA - 9, 1, 20);
    //FIN
    //estado personaje
    private static int vidaActual, vidaTotal, manaActual, manaTotal, capaDedibujo;
    //FIN

    public Jugador(double posicionX, double posicionY, Mapas mapa) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.mapa = mapa;
        contador = 0;
        sprite = new HojaSprites("/imagenes/personajes/Sprite_SLIME2.png", Constantes.LADO_SPRITES, false);
        direccion = 0;
        estadoAnimacion = 0;
        estadoAnimacionAtaque = 4;
        estado = false;
        frecuenciaAnimación = 10;
        contadorEmpuje = 0;
        fuerzaCaida = Constantes.GRAVEDAD;
        referenciaGravedad = getFuerzaVertical();
        imagenActual = sprite.getSprite(0, 0).getImagen();
        setVidaTotal(100);
        aumentarVidaActual(100);
        setManaTotal(100);
        aumentarManaActual(0);
        setCapaDedibujo(2);
        desfaseDeMovimientoAccion = new Point[mapa.getCapa()][mapa.areaAcciones.size()];
    }

    public void dibujar(Graphics g) {
        final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
        final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
        g.setColor(Color.green);
        g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height);
        g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height);
        g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
        g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height);
        g.drawImage(imagenActual, centroX, centroY, null);

    }

    public void actualizar() {
        if (enElAire) {
            setVelocidad(velocidadSalto);
        } else {
            resetVelocidad();
        }
        if (empujado) {
            contadorEmpuje++;
            empujado();
            if (contadorEmpuje >= 30) {
                contadorEmpuje = 0;
                empujado = false;
            }
        }
        if (!GestorControles.teclado.atacar) {
            determinarDireccion();
            animar();

        } else if (GestorControles.teclado.atacar) {
            animarAtaque();
        }

//        if (Constantes.APS % 30 == 0) {
//            aumentarManaActual(1);
//            aumentarVidaActual(-1);
//        }
        EnCaida((int) fuerzaCaida);
        actualizarAreaAccion();
        if (gravedad) {
            contador++;
            if (contador >= 5) {
                contador = 0;
            }
            if (enElAire) {
                setFuerzaCaidaDEF(referenciaGravedad);
                if (getFuerzaVertical() <= 3) {
                    setFuerzaVertical(-60.0 / 600);
                }
                referenciaGravedad = getFuerzaVertical();
            }

            posicionY += fuerzaCaida;
        }
        setFuerzaCaidaDEF(Constantes.GRAVEDAD);
        if (gravedad) {
            enElAire = true;
        }
        gravedad = true;

    }

    private void animar() {
        if (estado) {
            if (Constantes.APS % frecuenciaAnimación == 0) {
                estadoAnimacion++;

                if (estadoAnimacion >= 4) {
                    estadoAnimacion = 0;
                }
                if (direccion == 0) {
                    estadoAnimacion = 0;
                }
            }
            imagenActual = sprite.getSprite(direccion, estadoAnimacion).getImagen();
        } else {
            imagenActual = sprite.getSprite(direccion, 0).getImagen();
        }

    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    public void aumentarPosicionX(double posicionX) {
        this.posicionX += posicionX;
    }

    public void aumentarPosicionY(double posicionY) {
        this.posicionY += posicionY;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    private void determinarDireccion() {
        final int velocidadX = evaluarVelocidadX();
        final int velocidadY = evaluarVelocidadY();
        if (velocidadX == 0 && velocidadY == 0) {
            estado = false;
        }
        if ((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
            mover(velocidadX, velocidadY);
            estado = true;
        } else {
            //izquierda y arriba
            if (velocidadX == -1 && velocidadY == -1) {
//                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
//                    mover(velocidadX, 0);
//                    estado = true;
//                } else {
//                    mover(0, velocidadY);
//                    estado = true;
//                }
                mover(velocidadX, velocidadY);
                estado = true;
            }
            //izquierda y abajo
            if (velocidadX == -1 && velocidadY == 1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                    estado = true;
                } else {
                    mover(0, velocidadY);
                    estado = true;
                }
            }
            //derecha y arriba
            if (velocidadX == 1 && velocidadY == -1) {
//                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
//                    mover(velocidadX, 0);
//                    estado = true;
//                } else {
//                    mover(0, velocidadY);
//                    estado = true;
//                }
                mover(velocidadX, velocidadY);
                estado = true;
            }
            //izquierda y abajo
            if (velocidadX == 1 && velocidadY == 1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                    estado = true;
                } else {
                    mover(0, velocidadY);
                    estado = true;
                }
            }

        }

    }

    private int evaluarVelocidadX() {
        int velocidadX = 0;
        if (GestorControles.teclado.izquierda.getPulsada() && !GestorControles.teclado.derecha.getPulsada()) {
            velocidadX = -1;
        } else if (!GestorControles.teclado.izquierda.getPulsada() && GestorControles.teclado.derecha.getPulsada()) {
            velocidadX = 1;
        }
        return velocidadX;
    }

    private int evaluarVelocidadY() {
        int velocidadY = 0;
        if (GestorControles.teclado.arriba.getPulsada() && !GestorControles.teclado.abajo.getPulsada()) {
            velocidadY = -1;
        } else if (!GestorControles.teclado.arriba.getPulsada() && GestorControles.teclado.abajo.getPulsada()) {
            velocidadY = 1;
        }
        return velocidadY;
    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY) {
        int posicionFuturaX = (int) posicionX + velocidadX * (int) velocidad;
        int posicionFuturaY = (int) posicionY + velocidadY * (int) velocidad;
        final Rectangle bordesMapa = mapa.getBordes(posicionFuturaX, posicionFuturaY, 28, 18);
//        LIMITE_ABAJO.width;
//        LIMITE_DERECHA.height;
        final boolean fuera;
        if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
            fuera = false;
        } else {
            fuera = true;
        }
        return fuera;
    }

    private void mover(final int velocidadX, final int velocidadY) {
        enMovimiento = true;
        cambiarDireccion(velocidadX, velocidadY);
        if (!fueraMapa(velocidadX, velocidadY)) {
            enColision(velocidadX, velocidadY);
            if (velocidadX == -1 && !colisionIzquierda && !colisionIzquierdaAccion && !empujado) {
                posicionX += velocidadX * velocidad;
            }
            if (velocidadX == 1 && !colisionDerecha && !colisionDerechaAccion && !empujado) {
                posicionX += velocidadX * velocidad;
            }
            if (velocidadY == -1 && !colisionArriba && !colisionEXArriba) {
                //posicionY += velocidadY * fuerzaCaida;
                if (!enElAire) {
                    setFuerzaVertical(4);
                    enElAire = true;
                    referenciaGravedad = getFuerzaVertical();
                }
            }
            if (velocidadY == 1 && !colisionAbajo && !colisionEXAbajo) {
                //posicionY += velocidadY * fuerzaCaida;
            }

        }
        colisionDerechaAccion = false;
        colisionIzquierdaAccion = false;
        colisionIzquierda = false;
        colisionDerecha = false;
        colisionArriba = false;
        colisionAbajo = false;

    }

    private void cambiarDireccion(final int velocidadX, final int velocidadY) {
        if (velocidadX == 1) {
            direccion = 2;
        } else if (velocidadX == -1) {
            direccion = 3;
        }

        if (velocidadY == 1) {
            direccion = 0;
        }

    }

    private void enColision(final int velocidadX, final int velocidadY) {
        for (int r = 0; r < mapa.areaColisiones.size(); r++) {
            final Rectangle area = mapa.areaColisiones.get(r);

            final int origenX = area.x + velocidadX * (int) velocidad + 3 * (-velocidadX * (int) velocidad);
            final int origenY = area.y + velocidadY * (int) velocidad + 3 * (-velocidadY * (int) velocidad);
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
            if (LIMITE_ARRIBA.intersects(areaFutura)) {
                colisionArriba = true;
                setFuerzaCaidaDEF(0);
                referenciaGravedad = getFuerzaVertical();
            }
            if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
                colisionIzquierda = true;
                empujado = false;
                contadorEmpuje = 0;
            }
            if (LIMITE_DERECHA.intersects(areaFutura)) {
                colisionDerecha = true;
                empujado = false;
                contadorEmpuje = 0;
            }
        }
    }

    private void EnCaida(final int fuerzaCaida) {
        for (int r = 0; r < mapa.areaColisiones.size(); r++) {
            final Rectangle area = mapa.areaColisiones.get(r);
            final int origenX;
            final int origenY;
            if (gravedad) {
                origenX = area.x;
                origenY = area.y + 1 * (int) fuerzaCaida + 3 * (-1 * fuerzaCaida);
            } else {
                origenX = area.x;
                origenY = area.y;
            }
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
            if (LIMITE_ABAJO.intersects(areaFutura) && !colisionAbajoConAccion) {
                colisionAbajoConAccion = true;
                gravedad = false;
//                contador++;
                enElAire = false;
                setFuerzaCaidaDEF(Constantes.GRAVEDAD);
//                if (contador >= 20) {
//                    enElAire = false;
//                    contador = 0;
//                }
            }
        }
    }

    public void actualizarAreaAccion() {
        for (int r = 0; r < mapa.areaAcciones.size(); r++) {

            final Rectangle areaAccion = mapa.areaAcciones.get(r);
            final int origenXIz = areaAccion.x - 1 * (int) velocidad + 3 * (1 * (int) velocidad);
            final int origenXDe = areaAccion.x + 1 * (int) velocidad + 3 * (-1 * (int) velocidad);
            final int origenX = areaAccion.x + 1 * (int) velocidad + 3 * (-1 * (int) velocidad);
            final int origenY = areaAccion.y + 1 * (int) fuerzaCaida + 3 * (-1 * (int) fuerzaCaida);
            final Rectangle areaFuturaAccion = new Rectangle(origenX, origenY, areaAccion.width, areaAccion.height);
            final Rectangle areaFuturaAccionIz = new Rectangle(origenXIz, origenY, areaAccion.width, areaAccion.height);
            final Rectangle areaFuturaAccionDe = new Rectangle(origenXDe, origenY, areaAccion.width, areaAccion.height);
//            colisionDerechaAccion = false;
//            colisionIzquierdaAccion = false;

            if (LIMITE_ABAJO.intersects(areaFuturaAccion) && mapa.tipoDeAcciones.get(r) == 3) {
                setCapaDedibujo(2);
            } else if (LIMITE_ABAJO.intersects(areaFuturaAccion) && mapa.tipoDeAcciones.get(r) == 2) {
                setCapaDedibujo(1);
            } else if (LIMITE_ABAJO.intersects(areaFuturaAccion) && mapa.tipoDeAcciones.get(r) == 4) {
//                if (Constantes.APS % 15 == 0) {
//                    aumentarVidaActual(-1);
//                }
                setFuerzaVertical(5);
                referenciaGravedad = getFuerzaVertical();

            } else if (LIMITE_DERECHA.intersects(areaFuturaAccionDe) && mapa.tipoDeAcciones.get(r) == 6 && !enElAire && direccion == 2) {
                mapa.hacerDesfaseDeAccion(mapa.referenciaCapaPosicion.get(r).x, mapa.referenciaCapaPosicion.get(r).y, 1, 0);
                // colisionDerechaAccion = true;
                
            } else if (LIMITE_IZQUIERDA.intersects(areaFuturaAccionIz) && mapa.tipoDeAcciones.get(r) == 6 && !enElAire && direccion == 3) {
                mapa.hacerDesfaseDeAccion(mapa.referenciaCapaPosicion.get(r).x, mapa.referenciaCapaPosicion.get(r).y, -1, 0);
                //colisionIzquierdaAccion = true;

            } else if (LIMITE_ABAJO.intersects(areaFuturaAccion) && mapa.tipoDeAcciones.get(r) == 6 && areaAccion.y + 5 > LIMITE_ABAJO.y) {
                colisionAbajo = true;
                gravedad = false;
                enElAire = false;
                colisionAbajoConAccion = true;
                setFuerzaCaidaDEF(Constantes.GRAVEDAD);
                referenciaGravedad = getFuerzaVertical();
            }
            if (LIMITE_DERECHA.intersects(areaFuturaAccionDe) && mapa.tipoDeAcciones.get(r) == 6 && enElAire && direccion == 2) {
                colisionDerechaAccion = true;
                empujado = false;
                contadorEmpuje = 0;
            } else if (LIMITE_IZQUIERDA.intersects(areaFuturaAccionIz) && mapa.tipoDeAcciones.get(r) == 6 && enElAire && direccion == 3) {
                colisionIzquierdaAccion = true;
                // hacer que al chocar deje de empujar

            }
            if ((LIMITE_DERECHA.intersects(areaFuturaAccionDe) || LIMITE_IZQUIERDA.intersects(areaFuturaAccionIz)) && mapa.tipoDeAcciones.get(r) == 6 ){
                empujado = false;
                contadorEmpuje = 0;
            }
            if (!LIMITE_ABAJO.intersects(areaFuturaAccion)) {
                colisionAbajoConAccion = false;
            }
            if (LIMITE_ARRIBA.intersects(areaFuturaAccion) && mapa.tipoDeAcciones.get(r) == 6) {
                colisionArriba = true;
                setFuerzaCaidaDEF(0);
                referenciaGravedad = getFuerzaVertical();
            }

        }
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void aumentarVidaActual(int valor) {
        Jugador.vidaActual += valor;
        if (vidaActual > vidaTotal) {
            vidaActual = vidaTotal;
        } else if (vidaActual < 0) {
            vidaActual = 0;
        }
    }

    public int getVidaTotal() {
        return vidaTotal;
    }

    public static void setVidaTotal(int vidaTotal) {
        Jugador.vidaTotal = vidaTotal;
    }

    public int getManaActual() {
        return manaActual;
    }

    public static void aumentarManaActual(int valor) {
        Jugador.manaActual += valor;
        if (manaActual > manaTotal) {
            manaActual = manaTotal;
        } else if (manaActual < 0) {
            manaActual = 0;
        }
    }

    public int getManaTotal() {
        return manaTotal;
    }

    public static void setManaTotal(int manaTotal) {
        Jugador.manaTotal = manaTotal;
    }

    private void animarAtaque() {
        if (Constantes.APS % frecuenciaAnimación == 0) {
            estadoAnimacionAtaque++;

            if (estadoAnimacionAtaque >= 10) {
                estadoAnimacionAtaque = 4;
                GestorControles.teclado.atacar = false;
            }
        }
        imagenActual = sprite.getSprite(direccion, estadoAnimacionAtaque).getImagen();
    }

    public int getCapaDedibujo() {
        return capaDedibujo;
    }

    public void setCapaDedibujo(int capaDedibujo) {
        Jugador.capaDedibujo = capaDedibujo;
    }

    public void aumentarCapaDedibujo(int capaDedibujo) {
        Jugador.capaDedibujo += capaDedibujo;
    }

    public void disminuirCapaDedibujo(int capaDedibujo) {
        Jugador.capaDedibujo -= capaDedibujo;
    }

    public Rectangle getLIMITE_ARRIBA() {
        return LIMITE_ARRIBA;
    }

    public Rectangle getLIMITE_ABAJO() {
        return LIMITE_ABAJO;
    }

    public Rectangle getLIMITE_IZQUIERDA() {
        return LIMITE_IZQUIERDA;
    }

    public Rectangle getLIMITE_DERECHA() {
        return LIMITE_DERECHA;
    }

    public void setVelocidad(final double escala) {
        this.velocidad = escala;
    }

    public void resetVelocidad() {
        this.velocidad = velocidadInicial;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setColisionEXDerecha(final boolean colisionEXDerecha) {
        this.colisionEXDerecha = colisionEXDerecha;
    }

    public void setColisionEXIzquierda(final boolean colisionEXIzquierda) {
        this.colisionEXIzquierda = colisionEXIzquierda;
    }

    public void setColisionEXAbajo(final boolean colisionEXAbajo) {
        this.colisionEXAbajo = colisionEXAbajo;
    }

    public void setColisionEXArriba(final boolean colisionEXArriba) {
        this.colisionEXArriba = colisionEXArriba;
    }

    public Point getPoint() {
        final Point point = new Point(0, 0);
        if (direccion == 1) {
            point.y = -1;
        }
        if (direccion == 2) {
            point.x = 1;
        }
        if (direccion == 3) {
            point.x = -1;
        }
        if (direccion == 0) {
            point.y = 1;
        }
        return point;
    }

    private void setFuerzaVertical(final double FuerzaIngresada) {
        fuerzaCaida = fuerzaCaida - FuerzaIngresada;
    }

    private double getFuerzaVertical() {
        return fuerzaCaida;
    }

    private void setFuerzaCaidaDEF(final double valor) {
        fuerzaCaida = valor;
    }

    public void colisionExterna(final Rectangle hit) {
        if (LIMITE_DERECHA.intersects(hit)) {
            aumentarVidaActual(-5);
        }
    }

    private void empujado() {
        switch (direccionEmpuje) {
            case 1:
                aumentarPosicionX(2);
                break;
            case 2:
                aumentarPosicionX(-2);
                break;
        }
    }

    public void verificarEmpuje(final int direccion) {
        empujado = true;
        direccionEmpuje = direccion;
    }
}
