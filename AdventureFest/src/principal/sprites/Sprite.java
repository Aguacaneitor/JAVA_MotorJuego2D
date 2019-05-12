/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.sprites;

import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
public class Sprite {
    
    private final BufferedImage imagen;
    private final int ancho, alto;

    public Sprite(BufferedImage imagen) {
        this.imagen = imagen;        
        ancho = imagen.getWidth();
        alto = imagen.getHeight();       
    }
    public BufferedImage getImagen() {
        return imagen;
    }
    public int getAncho() {
        return ancho;
    }
    public int getAlto() {
        return alto;
    }
    
    
    
}
