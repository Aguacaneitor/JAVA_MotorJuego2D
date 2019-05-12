/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.sprites;

import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
import principal.herramientas.CargadorRecursos;
public class HojaSprites {
    
    private final String ruta;
    //variables del tamaño de hoja en pixeles
    final private int anchoHoja;
    final private int altoHoja;
    //variables del tamaño de hoja en cantidad sprites
    final private int anchoHojaenSprites;
    final private int altoHojaenSprites;
    //tamaño de los sprites
    final private int anchoSprites;
    final private int altoSprites;    
    
    final private Sprite[] sprites;
    
    public HojaSprites (final String ruta, final int tamañoSprites, final boolean hojaOpaca){
        final BufferedImage imagen;
        this.ruta = ruta;
        
        if (hojaOpaca){
            imagen = CargadorRecursos.cargaImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargaImagenCompatibleTraslucida(ruta);  
        }
        anchoHoja = imagen.getWidth();
        altoHoja = imagen.getHeight();
        
        anchoHojaenSprites = anchoHoja/tamañoSprites;
        altoHojaenSprites = altoHoja/tamañoSprites;
        
        anchoSprites = tamañoSprites;
        altoSprites = tamañoSprites;
        
        sprites = new Sprite[anchoHojaenSprites*altoHojaenSprites];
        RellenarSpritesdeImagen(imagen);
    }
    
    public HojaSprites (final String ruta, final int anchoSprites, final int altoSprites, final boolean hojaOpaca){
        final BufferedImage imagen;
        this.ruta = ruta;
        
        if (hojaOpaca){
            imagen = CargadorRecursos.cargaImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargaImagenCompatibleTraslucida(ruta);  
        }
        anchoHoja = imagen.getWidth();
        altoHoja = imagen.getHeight();
        
        anchoHojaenSprites = anchoHoja/anchoSprites;
        altoHojaenSprites = altoHoja/altoSprites;
        
        this.anchoSprites = anchoSprites;
        this.altoSprites = altoSprites;
        
        sprites = new Sprite[anchoHojaenSprites*altoHojaenSprites];
        RellenarSpritesdeImagen(imagen);
    }
    
    private void RellenarSpritesdeImagen (final BufferedImage imagen){
        for (int y = 0; y<altoHojaenSprites;y++){
            for (int x = 0; x<anchoHojaenSprites ; x++){
                final int posicionX = x*anchoSprites;
                final int posicionY = y*altoSprites;
                sprites[x+y*anchoHojaenSprites] = new Sprite(imagen.getSubimage(posicionX , posicionY, anchoSprites, altoSprites));
            }
        }
    }
    
    public Sprite getSprite(final int indice){
        return sprites [indice];        
    }
    public Sprite getSprite(final int x, final int y){
        return sprites [x + y * anchoHojaenSprites];
    }
    public String getRuta(){
        return ruta;
    }
}
