/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal;

/**
 *
 * @author Daniel
 */
public class Constantes {
    
    public static final int LADO_SPRITES = 32;
    
    public final static int ANCHO_JUEGO = 640;//640
    public final static int ALTO_JUEGO = 360;//360
    public final static double GRAVEDAD = 1;
    
    public static int ANCHO_PANTALLA_COMPLETA = 1366;//1366
    public static int ALTO_PANTALLA_COMPLETA = 768; //768
    
    public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA/(double)ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA/(double)ALTO_JUEGO;
    
    
    public static int CENTRO_ANCHO_PANTALLA = ANCHO_JUEGO/2;
    public static int CENTRO_ALTO_PANTALLA = ALTO_JUEGO/2;
    
    
    
    public static int APS;
    
    
}
