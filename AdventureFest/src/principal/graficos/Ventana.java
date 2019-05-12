/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.graficos;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Daniel
 */
public class Ventana extends JFrame {
    
    private String titulo;
    private SuperficieDeDibujo sd;
    
    public Ventana (final String titulo, final SuperficieDeDibujo sd){
        this.titulo = titulo;
        configurarVentana(sd);
        
        
    }

    private void configurarVentana(final SuperficieDeDibujo sd) {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(titulo);
        //this.setIconImage(image);
        //this.setSize(sd.ancho, sd.alto);
        this.setLayout(new BorderLayout());
        this.add(sd, BorderLayout.CENTER);
        this.setResizable(false);
        this.setUndecorated(true);
        this.pack();        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    
    }
    
}
