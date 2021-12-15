package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author DOUSSELAERE & EL RHOUFI
 */
public class Alien extends Fusee{
    
    private static int vitesse;
    private static int direction;
    private boolean isDead;
    
    public Alien(int posX, int posY, int size, Image img){
        super(posX, posY, size, img);
    }
    
    public void descendre(int posY){
        this.posY = posY;
    }
    
    public void setPosX(int x){
        this.posX = x;
    }
    
    public void setImage(Image img){
        this.img = img;
    }
    
    public void setIdDead(boolean a){
        this.isDead=a;
    }
    
    public boolean getIsDead(){
        return this.isDead;
    }
    
    public Tir tirer(){
        return new Tir(posX+(35/2)-3, posY);
    }
    
}
