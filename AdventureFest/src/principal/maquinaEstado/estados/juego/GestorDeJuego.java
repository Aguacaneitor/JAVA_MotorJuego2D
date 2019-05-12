/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.maquinaEstado.estados.juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.control.GestorControles;
import principal.entes.Crates;
import principal.entes.Jugador;
import principal.entes.trampas.GestorTrampas;
import principal.herramientas.CargadorRecursos;
import principal.interfaz_Usuario.HUD;
import principal.mapas.Mapas;
import principal.maquinaEstado.EstadoJuego;

/**
 *
 * @author Daniel
 */
public class GestorDeJuego implements EstadoJuego {

    private GestorMapa gestormapa;
    Mapas mapa = new Mapas("/mapas/Prueba.af");
    Jugador jugador = new Jugador(mapa.posicionJugador.x*32, mapa.posicionJugador.y*32, mapa);
    HUD interfaz = new HUD();
    private GestorTrampas trampas = new GestorTrampas (mapa.tipoDeAccion, jugador, mapa.posicionAcciones ,mapa.getAncho());
    //Crates crate = new Crates (jugador.getPosicionX() + 4*32,jugador.getPosicionY() + 6*32,this.mapa,this.jugador);

    @Override
    public void actualziar() {
        jugador.actualizar();
        mapa.actualizar(jugador.getCapaDedibujo()-1,(int) jugador.getPosicionX(), (int) jugador.getPosicionY());
        trampas.actualizar();
        // crate.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        for (int capa = 0; capa < mapa.cantidadDeCapas(); capa++) {
            mapa.dibujar(g, capa, (int) jugador.getPosicionX(), (int) jugador.getPosicionY());
            if (jugador.getCapaDedibujo() == capa) {
                jugador.dibujar(g);
                trampas.dibujar(g);
            }
//            if (crate.getCapaDeDibujo() == capa) {
//                crate.dibujar(g, (int)jugador.getPosicionX(), (int)jugador.getPosicionY());
//                
//            }
            g.setColor(Color.red);
            g.drawString("PosicionY: " + jugador.getPosicionY(), 20, 80);
            g.drawString("PosicionX: " + jugador.getPosicionX(), 20, 100);
            interfaz.dibujarBarrasEstado(g, jugador.getVidaActual(), jugador.getVidaTotal(), jugador.getManaActual(), jugador.getManaTotal());

        }
    }

   

}
