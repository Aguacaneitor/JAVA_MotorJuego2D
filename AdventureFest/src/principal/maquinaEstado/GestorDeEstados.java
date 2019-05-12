/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.maquinaEstado;

import java.awt.Graphics;
import principal.maquinaEstado.estados.juego.GestorDeJuego;

/**
 *
 * @author Daniel
 */
public class GestorDeEstados {
    
    private EstadoJuego[] estados;
    private EstadoJuego estadoActual;

    public GestorDeEstados() {
        iniciarEstados();
        iniciarEstadoActual();
    }

    private void iniciarEstados() {
    
        estados = new EstadoJuego [1];
        estados[0] = new GestorDeJuego();
        //Añadir e iniciar los demas estadoss a medida los creemos
    }

    private void iniciarEstadoActual() {
        estadoActual = estados[0];
    }
    
    public void actualizar(){
        estadoActual.actualziar();
    }
    public void dibujar (Graphics g){
        estadoActual.dibujar(g);
    }
    public void cambiarEstadoActual (final int nuevoEstado){
        estadoActual = estados [nuevoEstado];
    }
   
   
}
