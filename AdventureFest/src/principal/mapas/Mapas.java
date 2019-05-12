/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author Daniel
 */
public class Mapas {

    private final int ancho, alto;
    private int capa, longitud;
    private ArrayList<Integer> capa1 = new ArrayList<Integer>();
    private ArrayList<Integer> longitud1 = new ArrayList<Integer>();
    private boolean enSuelo;
    private ArrayList<Boolean> enSuelo1 = new ArrayList<Boolean>();
    private final String[] partes;
    private final Sprite[] paleta;
    private final HojaSprites[] hojasMapa;
    private final boolean[][] colisiones;
    private final Tile[][] tilesMapa;
    private final String[] capas;
    private final String[][] posicionSprites;
    public final int[][] tipoDeAccion;
    public final Point[] posicionAcciones;
    private final boolean[][] acciones;
    private final Point[][] desfaseDeMovimientoAccion;
    //private final Rectangle bordeMapa;
    public ArrayList<Rectangle> areaColisiones = new ArrayList<Rectangle>();
    public ArrayList<Rectangle> areaAcciones = new ArrayList<Rectangle>();
    public ArrayList<Integer> tipoDeAcciones = new ArrayList<Integer>();
    public ArrayList<Point> referenciaCapaPosicion = new ArrayList<Point>();
    private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITES / 2;
    //Crates, trampolines y otras acciones:
    private final BufferedImage crate, trampolin;
    private final HojaSprites imagenAcciones;
    public final String [] posicionJugadorS;
    public final Point posicionJugador;
    

    public Mapas(final String ruta) {

        String contenido = CargadorRecursos.leerArchivoTexto(ruta);

        partes = contenido.split("\\*");
        ancho = Integer.parseInt(partes[0]);
        alto = Integer.parseInt(partes[1]);
        String[] hojasSeparadas = partes[2].split("-");
        hojasMapa = new HojaSprites[hojasSeparadas.length];
        String[] spritesPaleta = partes[3].split("#");
        paleta = new Sprite[spritesPaleta.length];
        capas = partes[5].split("#");
        posicionSprites = new String[capas.length][capas[0].split("-").length];
        llenarMatrixSprites();
        tilesMapa = new Tile[capas.length][posicionSprites[0].length];
        ObtenerHojas(hojasSeparadas);
        String[] capasAccion = partes[4].split("#");
        //POINT de DESFASE
        capa = capasAccion.length;
        longitud = capasAccion[0].split("-").length;
        desfaseDeMovimientoAccion = new Point[capasAccion.length][capasAccion[0].split("-").length];
        //POINT de DESFASE
        tipoDeAccion = new int[capa][longitud];
        acciones = new boolean[capa][longitud];
        posicionAcciones = new Point[longitud];
        llenarPosicionAccion();
        llenarDesfaseVacio();
        colisiones = new boolean[capasAccion.length][capasAccion[0].split("-").length];
        leerPaletaSprites(hojasSeparadas, spritesPaleta);
        llenarColisiones(capasAccion);
        ConvertirTiles(posicionSprites, spritesPaleta);
        enSuelo = true;
        //Imagenes acciones:
        imagenAcciones = new HojaSprites("/imagenes/objetos/Acciones.png", Constantes.LADO_SPRITES, false);
        crate = imagenAcciones.getSprite(0).getImagen();
        trampolin = imagenAcciones.getSprite(1).getImagen();
        posicionJugadorS = partes[6].split("-");
        posicionJugador = new Point (Integer.parseInt(posicionJugadorS[0]), Integer.parseInt(posicionJugadorS[1]));
        //bordeMapa = new Rectangle(0,0, ancho+10, alto+10);
    }

    private void llenarMatrixSprites() {
        for (int c = 0; c < capas.length; c++) {
            posicionSprites[c] = capas[c].split("-");
        }
    }

    public Sprite[] getSpritesMapa() {
        return paleta;
    }

    private void ObtenerHojas(final String[] hojasSeparadas) {

        for (int i = 0; i < hojasSeparadas.length; i++) {
            String ruta = "/imagenes/spritesMapas/" + hojasSeparadas[i];
            if (i < 1) {
                hojasMapa[i] = new HojaSprites(ruta, 32, true);
            } else {
                hojasMapa[i] = new HojaSprites(ruta, 32, false);
            }
        }

    }

    private void leerPaletaSprites(final String[] hojasSeparadas, final String[] spritesPaleta) {
        for (int i = 0; i < paleta.length; i++) {

            int hojaAUtilizar = Integer.parseInt(spritesPaleta[i].substring(0, 2));
            int posicionX = Integer.parseInt(spritesPaleta[i].substring(2, 3));
            int posicionY = Integer.parseInt(spritesPaleta[i].substring(3, 4));
            int posicionHoja = 0;
//            if (hojaAUtilizar == 1) {
//                for (int indice = 0; indice < hojasSeparadas.length; indice++) {
//                    if (hojasMapa[indice].getRuta().equals("/imagenes/spritesMapas/hojaSprite_Campo.png")) {
//                        posicionHoja = indice;
//                        break;
//                    }
//                }
//            }    

            paleta[i] = hojasMapa[hojaAUtilizar - 1].getSprite(posicionX, posicionY);
        }

    }

    private void llenarColisiones(String[] capasAccion) {

        for (int c = 0; c < capasAccion.length; c++) {
            String[] accionPorCapa = capasAccion[c].split("-");
            for (int i = 0; i < accionPorCapa.length; i++) {
                int indice = Integer.parseInt(accionPorCapa[i]);
                tipoDeAccion[c][i] = indice;
                if (indice == 1) {
                    colisiones[c][i] = true;
                    acciones[c][i] = false;
                } else if (indice > 1) {
                    acciones[c][i] = true;
                    colisiones[c][i] = false;
                } else {
                    colisiones[c][i] = false;
                    acciones[c][i] = false;
                }
            }
        }
    }

    private void ConvertirTiles(final String[][] posicionSprites, final String[] spritesPaleta) {
        for (int c = 0; c < capas.length; c++) {
            for (int i = 0; i < posicionSprites[c].length; i++) {
                int valorSpritedePaleta = Integer.parseInt(posicionSprites[c][i].substring(0, 4));
                int posicionSprite = 0;
                for (int indice = 0; indice < spritesPaleta.length; indice++) {
                    int valor = Integer.parseInt(spritesPaleta[indice].substring(0, 4));
                    if (valorSpritedePaleta == valor) {
                        posicionSprite = indice;
                        break;
                    }
                }

                tilesMapa[c][i] = new Tile(paleta[posicionSprite], tipoDeAccion[c][i], colisiones[c][i]);

            }
        }

    }

    public void actualizar(final int capa, final int posicionX, final int posicionY) {
        actualizarAreasColicion(capa, posicionX, posicionY);
        actualizarAreasAcciones(capa, posicionX, posicionY);
        for (int indice = 0; indice < enSuelo1.size(); indice++) {
            if (!enSuelo1.get(indice)) {
                hacerDesfaseDeAccion(capa1.get(indice), longitud1.get(indice), 0, 1);
                if (desfaseDeMovimientoAccion[capa1.get(indice)][longitud1.get(indice)].y > 1000) {
                    tipoDeAccion[capa1.get(indice)][longitud1.get(indice)] = 0;
                    acciones[capa1.get(indice)][longitud1.get(indice)] = false;
                }
            }
        }
    }

    private void actualizarAreasColicion(final int capa, final int posicionX, final int posicionY) {
        if (!areaColisiones.isEmpty()) {
            areaColisiones.clear();
        }
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if (colisiones[capa][x + y * ancho]) {
                    int puntoX = x * Constantes.LADO_SPRITES - posicionX + MARGEN_X;
                    int puntoY = y * Constantes.LADO_SPRITES - posicionY + MARGEN_Y;
                    int anchoRect;
                    int altoRect;
                    int desfaseX;
                    int desfaseY;
                    int indice = 1;
                    if (posicionSprites[1][x + y * ancho].equals("0000")) {
                        indice = 2;
                    }
                    switch (posicionSprites[indice][x + y * ancho]) {
                        case "0260":
                            desfaseX = 12;
                            desfaseY = 20;
                            anchoRect = 20;
                            altoRect = 12;
                            break;
                        case "0250":
                            desfaseX = 12;
                            desfaseY = 0;
                            anchoRect = 7;
                            altoRect = 32;
                            break;
                        case "0240":
                            desfaseX = 12;
                            desfaseY = 0;
                            anchoRect = 7;
                            altoRect = 28;
                            break;
                        case "0201":
                            desfaseX = 12;
                            desfaseY = 18;
                            anchoRect = 7;
                            altoRect = 14;
                            break;
                        case "0280":
                            desfaseX = 12;
                            desfaseY = 0;
                            anchoRect = 8;
                            altoRect = 32;
                            break;
                        case "0290":
                            desfaseX = 0;
                            desfaseY = 20;
                            anchoRect = 20;
                            altoRect = 12;
                            break;
                        case "0270":
                            desfaseX = 12;
                            desfaseY = 0;
                            anchoRect = 7;
                            altoRect = 32;
                            break;
                        case "0230":
                            desfaseX = 0;
                            desfaseY = 27;
                            anchoRect = 20;
                            altoRect = 8;
                            break;
                        case "0200":
                            desfaseX = 12;
                            desfaseY = 27;
                            anchoRect = 20;
                            altoRect = 8;
                            break;
                        case "0210":
                            desfaseX = 0;
                            desfaseY = 27;
                            anchoRect = 32;
                            altoRect = 8;
                            break;
                        case "0220":
                            desfaseX = 0;
                            desfaseY = 27;
                            anchoRect = 32;
                            altoRect = 8;
                            break;
                        default:
                            desfaseX = 0;
                            desfaseY = 0;
                            anchoRect = Constantes.LADO_SPRITES;
                            altoRect = Constantes.LADO_SPRITES;
                    }
                    Rectangle r = new Rectangle(puntoX + desfaseX, puntoY + desfaseY, anchoRect, altoRect);
                    areaColisiones.add(r);
                }

            }
        }
//        }
    }

    private void actualizarAreasAcciones(final int capa, final int posicionX, final int posicionY) {
        if (!areaAcciones.isEmpty()) {
            areaAcciones.clear();
            tipoDeAcciones.clear();
        }
        if (!capa1.isEmpty()) {
            capa1.clear();
        }
        if (!longitud1.isEmpty()) {
            longitud1.clear();
        }
        if (!enSuelo1.isEmpty()) {
            enSuelo1.clear();
        }
        if (!referenciaCapaPosicion.isEmpty()) {
            referenciaCapaPosicion.clear();
        }
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if (acciones[capa][x + y * ancho]) {
                    int puntoX = x * Constantes.LADO_SPRITES - posicionX + MARGEN_X;
                    int puntoY = y * Constantes.LADO_SPRITES - posicionY + MARGEN_Y;
                    this.capa = capa;
                    this.longitud = x + y * ancho;
                    int anchoRect;
                    int altoRect;
                    int desfaseX;
                    int desfaseY;
//                    int indice = 1;
//                    if (posicionSprites[1][x + y * ancho].equals("0000")) {
//                        indice = 2;
//                    }
//                    switch (posicionSprites[indice][x + y * ancho]) {
//                            default:
                    desfaseX = 0 + desfaseDeMovimientoAccion[capa][longitud].x;
                    desfaseY = 0 + desfaseDeMovimientoAccion[capa][longitud].y;
                    anchoRect = Constantes.LADO_SPRITES;
                    altoRect = Constantes.LADO_SPRITES;
//                    }
                    Rectangle r = new Rectangle(puntoX + desfaseX, puntoY + desfaseY, anchoRect, altoRect);
                    areaAcciones.add(r);
                    final Point referencia = new Point(capa, longitud);
                    referenciaCapaPosicion.add(referencia);
                    tipoDeAcciones.add(tipoDeAccion[capa][longitud]);
                    if (tipoDeAccion[capa][x + y * ancho] == 6) {
                        enSuelo = false;
                        capa1.add(this.capa);
                        longitud1.add(this.longitud);
                        corroborarEnElSueloCrate(r);
                        enSuelo1.add(enSuelo);
                    }

                }
            }

        }
    }

    private void corroborarEnElSueloCrate(Rectangle r) {
        for (int s = 0; s < areaColisiones.size(); s++) {
            final Rectangle area = areaColisiones.get(s);

            final int origenX = area.x;
            final int origenY = area.y - 3;
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
            if (r.intersects(areaFutura)) {
                enSuelo = true;
                break;
            }
        }

    }

    private void llenarDesfaseVacio() {
        for (int capa = 0; capa < this.capa; capa++) {
            for (int posicion = 0; posicion < longitud; posicion++) {
                desfaseDeMovimientoAccion[capa][posicion] = new Point(0, 0);
            }
        }
    }

    public int getLongitud() {
        return longitud;
    }

    public int getCapa() {
        return capa;
    }

    public void hacerDesfaseDeAccion(final int capa, final int posicion, final int desfaseX, final int desfaseY) {
        desfaseDeMovimientoAccion[capa][posicion].x += desfaseX;
        desfaseDeMovimientoAccion[capa][posicion].y += desfaseY;
    }

    public void dibujar(Graphics g, final int capa, int posicionX, int posicionY) {
//        int anchoSprite = tilesMapa[capa][0].getSprite().getAncho();
//        int altoSprite = tilesMapa[capa][0].getSprite().getAlto();
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int puntoX = x * Constantes.LADO_SPRITES - posicionX + MARGEN_X;
                int puntoY = y * Constantes.LADO_SPRITES - posicionY + MARGEN_Y;
                if (!posicionSprites[capa][x + y * ancho].equals("0000")) {
                    BufferedImage imagen = tilesMapa[capa][x + y * ancho].getSprite().getImagen();
                    g.drawImage(imagen, puntoX, puntoY, null);
                }
            }
        }
        g.setColor(Color.red);
        for (int r = 0; r < areaColisiones.size(); r++) {
            Rectangle rect = areaColisiones.get(r);
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }
        for (int r = 0; r < areaAcciones.size(); r++) {
            Rectangle rect = areaAcciones.get(r);
            switch (tipoDeAccion[referenciaCapaPosicion.get(r).x][referenciaCapaPosicion.get(r).y]) {
                case 4:
                    g.drawImage(trampolin, rect.x, rect.y, null);
                    break;
                case 6:
                    g.drawImage(crate, rect.x, rect.y, null);
                    break;
                default:
                    g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }

    }

    public Tile getTileMapa(final int capa, final int indice) {
        return tilesMapa[capa][indice];
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Rectangle getBordes(final int posicionX, final int posicionY, final int anchoJugador, final int altoJugador) {
        int x = MARGEN_X - posicionX + anchoJugador;
        int y = MARGEN_Y - posicionY + altoJugador;
        int ancho = this.ancho * Constantes.LADO_SPRITES - anchoJugador * 2;
        int alto = this.alto * Constantes.LADO_SPRITES - altoJugador * 2;
        return new Rectangle(x, y, ancho, alto);
    }

    public int cantidadDeCapas() {
        return capas.length;
    }

    public int tipoDeAccion(final int capa, final int x, final int y) {
        return tipoDeAccion[capa][x + y * ancho];
    }

    private void llenarPosicionAccion() {
        //Preventivamente solo para posicion de Trampas
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                posicionAcciones[x + y * ancho] = new Point(x, y);
            }
        }
    }
}
