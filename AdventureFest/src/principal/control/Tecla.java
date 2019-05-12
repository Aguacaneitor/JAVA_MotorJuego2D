/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.control;

/**
 *
 * @author Daniel
 */
public class Tecla {
    
    private boolean pulsada = false;
    private long ultimaPulsacion = System.nanoTime();
    
    public void teclaPulsada(){
        pulsada = true;
        ultimaPulsacion = System.nanoTime();
    }
    
    public void teclaLiberada (){
        pulsada = false;
    }
    
    public boolean getPulsada(){
        return pulsada;
    }

    public long getUltimaPulsacion() {
        return ultimaPulsacion;
    }
    
}
