package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author DOUSSELAERE & EL RHOUFI
 */
public class Fusee {
    
    protected int posX;
    protected int posY;
    private int size;
    protected boolean explosion;
    protected Image img;
    
    public Fusee(int posX, int posY, int size, Image img){
        this.posX=posX;
        this.posY=posY;
        this.size=size;
        this.img=img;
    }
    
    public void update(){
        
    }
    
    //public Tir tir(){
    //    
    //}
    
    public void affiche(GraphicsContext gc){
        gc.drawImage(img, posX, posY, size, size);
    }
                
    public void moveRight(){
        this.posX= this.posX+5;
        if(this.posX>500-this.size){ //gestion bordures
            posX=500-this.size;
        }
    }
    
    public void moveLeft(){
        this.posX= this.posX-5;
        if(this.posX<0){ //gestion bordures
            posX=0;
        }
    }
    
    public void moveRightFusee(){
        this.posX= this.posX+10;
        if(this.posX>500-this.size){ //gestion bordures
            posX=500-this.size;
        }
    }
    
    public void moveLeftFusee(){
        this.posX= this.posX-10;
        if(this.posX<0){ //gestion bordures
            posX=0;
        }
    }
    
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getSize(){
        return size;
    }
    
    public Tir tirer(){
        return new Tir(posX+(size/2)-3, posY);
    }
    
    public boolean collision(Tir tir){
        int dist = this.distance(this.posX, this.posY, tir.getPosX(), tir.getPosY());
        if((dist>(-1)*(5+(tir.getSize()))) && (dist<5+(tir.getSize()))){
            return true;
        }
        return false;
    }
    
    public static int distance(int x1, int y1, int x2, int y2){
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    
}
