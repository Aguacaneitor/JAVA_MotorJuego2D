/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.mapas;

import java.awt.Rectangle;
import principal.sprites.Sprite;

/**
 *
 * @author Daniel
 */
public class Tile {
    //Variables
    private final Sprite sprite;
    private final int ID;
    private boolean solido;
    
    public Tile (final Sprite sprite,final int ID){
        this.sprite = sprite;
        this.ID = ID;
        solido = false;
    }
    public Tile (final Sprite sprite,final int ID, final boolean solido){
        this.sprite = sprite;
        this.ID = ID;
        this.solido = solido;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getID() {
        return ID;
    }

    public boolean isSolido() {
        return solido;
    }

    public void setSolido(boolean solido) {
        this.solido = solido;
    }
    
    public Rectangle obtenerLimites (final int x, final int y){
        return new Rectangle(x,y,sprite.getAncho(),sprite.getAlto());
    }
}
