/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.herramientas;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 */
public class CargadorRecursos {
    
    public static BufferedImage cargaImagenCompatibleOpaca (final String ruta) {
        Image imagen = null;
        
        try {
            imagen = ImageIO.read(ClassLoader.class.getResource(ruta));
        } catch (IOException ex) {
            Logger.getLogger(CargadorRecursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null),Transparency.OPAQUE);
        
        Graphics g = imagenAcelerada.getGraphics();
        
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        
        return imagenAcelerada;
        
    }
    public static BufferedImage cargaImagenCompatibleTraslucida (final String ruta) {
        Image imagen = null;
        
        try {
            imagen = ImageIO.read(ClassLoader.class.getResource(ruta));
        } catch (IOException ex) {
            Logger.getLogger(CargadorRecursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);
        
        Graphics g = imagenAcelerada.getGraphics();
        
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        
        return imagenAcelerada;
        
    }
    public static String leerArchivoTexto (final String ruta){
        String contenido = "";
        String linea;
        
        InputStream entradaBytes = ClassLoader.class.getResourceAsStream(ruta);
        
        BufferedReader lector = new BufferedReader (new InputStreamReader(entradaBytes));
        
        try {
            while((linea = lector.readLine())!=null){
                contenido += linea;
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (entradaBytes != null){
                    entradaBytes.close();
                }
                if (lector != null){
                    lector.close();
                }
            } catch (IOException t){
                t.printStackTrace();
            }            
        }
        
        
        return contenido;        
    }    
}
