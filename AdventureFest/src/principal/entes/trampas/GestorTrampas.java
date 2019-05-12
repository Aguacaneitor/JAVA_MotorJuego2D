/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.entes.trampas;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import principal.Constantes;
import principal.entes.Jugador;

/**
 *
 * @author Daniel
 */
public class GestorTrampas {
    
    public ArrayList<Trampas> trampas = new ArrayList<Trampas>();
    private Jugador jugador;
    private int ancho;
    
    public GestorTrampas (final int [][] listadoAcciones, Jugador jugador, final Point [] posicionTrampas , final int ancho){
        this.jugador = jugador;
        this.ancho = ancho;
        llenarArrayTrampas (listadoAcciones, posicionTrampas);
    }
    
    public void actualizar (){
        for (int indice = 0; indice < trampas.size(); indice++){
            trampas.get(indice).actualizar();
        }
    }
    public void dibujar(Graphics g){
        for (int indice = 0; indice < trampas.size(); indice++){
            trampas.get(indice).dibujar(g);
        }        
    }

    private void llenarArrayTrampas(final int [][] listadoAcciones, final Point [] posicionTrampas) {
            for(int longitud = 0; longitud < listadoAcciones[2].length; longitud++){
                if (listadoAcciones[2][longitud] == 7 ){
                    final Trampas trampa = new Trampas (posicionTrampas[longitud].x , posicionTrampas[longitud].y, 2, jugador);
                    trampas.add(trampa);
                }
                if (listadoAcciones[2][longitud] == 8){
                    final Trampas trampa = new Trampas (posicionTrampas[longitud].x , posicionTrampas[longitud].y, 1, jugador);
                    trampas.add(trampa);
                }
            }
        }
}
    

