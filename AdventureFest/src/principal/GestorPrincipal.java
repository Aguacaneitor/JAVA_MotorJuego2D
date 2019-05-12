/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal;

import principal.control.GestorControles;
import principal.control.Raton;
import principal.graficos.SuperficieDeDibujo;
import principal.graficos.Ventana;
import principal.maquinaEstado.GestorDeEstados;

/**
 *
 * @author Daniel
 */
public class GestorPrincipal {

    //Variables
    private boolean enfuncionamiento = false;
    private String titulo;
    private static int ancho, alto;
    private int APS;
    private int FPS;
    //graficos
    private SuperficieDeDibujo sd;
    private Ventana ventana;
    private GestorDeEstados ge;

    private GestorPrincipal(final String titulo, final int ancho, final int alto) {
        this.alto = alto;
        this.ancho = ancho;
        this.titulo = titulo;
        ge = new GestorDeEstados();

    }

    public static void main(String[] args) {        
        GestorPrincipal gp = new GestorPrincipal("AdventureFest", Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);     
        gp.iniciarJuego();
        gp.iniciarBuclePrincipal();

    }

    private void iniciarJuego() {
        enfuncionamiento = true;
        sd = new SuperficieDeDibujo(ancho, alto);
        ventana = new Ventana (titulo, sd);
        inicializar();
    }

    private void inicializar() {

    }

    private void iniciarBuclePrincipal() {
        APS = 0;
        FPS = 0;

        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO = 60;
        final int NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

        long tiempodeReferenciadeActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();
        double delta = 0;
        double tiempoSinProcesar;

        while (true) {
            long tiempoInicial = System.nanoTime();
            tiempoSinProcesar = tiempoInicial - tiempodeReferenciadeActualizacion;
            tiempodeReferenciadeActualizacion = tiempoInicial;
            delta += tiempoSinProcesar / NS_POR_ACTUALIZACION;
            while (delta >= 1) {
                delta--;
                actualizar();

            }
            dibujar();

            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                System.out.println("APS: "+Constantes.APS+"|| FPS: "+FPS);
                referenciaContador = System.nanoTime();
                Constantes.APS = 0;
                APS = 0;
                FPS = 0;
            }
        }
    }

    private void actualizar() {
        if (!GestorControles.teclado.pause.getPulsada()){
        APS++;
        Constantes.APS++;
        if (GestorControles.teclado.salir.getPulsada()){
            System.exit(0);
        }
        ge.actualizar();
        }
        sd.actualizar();
    }

    private void dibujar() {
        FPS++;
        sd.dibujar(ge);
    }

}
