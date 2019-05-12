/*
*Juego realizado como proyecto personal por Daniel Alberto Díaz
 */
package principal.interfaz_Usuario;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Daniel
 */
public class HUD {
    
   
    public void dibujarBarrasEstado (Graphics g,final int vidaActual,final int vidaTotal,final int manaActual, final int manaTotal){
        int anchoVida = 100 * vidaActual/vidaTotal;
        int anchoMana = 100 * manaActual/manaTotal;
        Color rojoOscuro = new Color(0xffcc0000);
        Color azulOscuro = new Color(0xff000099);
        Color grisFondo = new Color(0xffb8b894);
        //VIDA
        g.setColor(Color.WHITE);
        if (anchoVida == 0){
            g.setColor(Color.GRAY);
        }
        g.drawString("VIDA", 20, 32);
        g.drawRect(60, 20, 101, 12);
        g.setColor(grisFondo);
        g.fillRect(61,21,100,10);
        g.setColor(Color.RED);
        g.fillRect(61, 21, anchoVida, 4);
        g.setColor(rojoOscuro);
        g.fillRect(61, 25, anchoVida, 6);
        //Mana
        g.setColor(Color.WHITE);
        if (anchoMana == 0){
            g.setColor(Color.GRAY);
        }
        g.drawString("MANA", 20, 52);
        g.drawRect(60, 40, 101, 12);
        g.setColor(grisFondo);
        g.fillRect(61,41,100,10);
        g.setColor(Color.BLUE);
        g.fillRect(61, 41, anchoMana, 4);
        g.setColor(azulOscuro);
        g.fillRect(61, 45, anchoMana, 6);
    }
    
}
