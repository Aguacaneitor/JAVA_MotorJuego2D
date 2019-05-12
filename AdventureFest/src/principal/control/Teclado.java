/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Daniel
 */
public class Teclado implements KeyListener {

    private final static int numeroTeclas = 256;
    public Tecla arriba = new Tecla();
    public Tecla izquierda = new Tecla();
    public Tecla abajo = new Tecla();
    public Tecla derecha = new Tecla();
    public Tecla salir = new Tecla();
    public Tecla pause = new Tecla();
    public boolean atacar = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                arriba.teclaPulsada();
                break;                
            case KeyEvent.VK_S:
                abajo.teclaPulsada();
                break;                
            case KeyEvent.VK_D:
                derecha.teclaPulsada();
                break;                
            case KeyEvent.VK_A:
                izquierda.teclaPulsada();
                break;                
            case KeyEvent.VK_SPACE:
                pause.teclaPulsada();
                break;
            case KeyEvent.VK_ESCAPE:
                salir.teclaPulsada();
                break;      
            case KeyEvent.VK_R:
                atacar = true;
         }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                arriba.teclaLiberada();
                break;                
            case KeyEvent.VK_S:
                abajo.teclaLiberada();
                break;                
            case KeyEvent.VK_D:
                derecha.teclaLiberada();
                break;                
            case KeyEvent.VK_A:
                izquierda.teclaLiberada();
                break;                
            case KeyEvent.VK_SPACE:
                pause.teclaLiberada();
                break;
            case KeyEvent.VK_ESCAPE:
                salir.teclaLiberada();
                break;      
         }

     
    }

}