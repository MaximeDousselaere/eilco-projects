package spaceinvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author DOUSSELAERE & EL RHOUFI
 */
public class Tir {
    
    private int posX;
    private int posY;
    private int speed = 10;
    private int speedAlien = 10;
    private int size = 9;
    
    public Tir(int posX, int posY){
        this.posX=posX;
        this.posY=posY;
    }
    
    public void update(){
        this.posY-=speed;
    }
    
    public void updateAlien(){
        this.posY+=speedAlien;
    }
        
    public void affiche(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillOval(posX, posY, size, size);
    }
    
    public boolean collision(Alien alien){
        int dist = this.distance(this.posX, this.posY, alien.getPosX(), alien.getPosY());
        if((dist>(-1)*(5+(alien.getSize()))) && (dist<5+(alien.getSize()))){
            return true;
        }
        return false;
    }
    
    public static int distance(int x1, int y1, int x2, int y2){
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    public void setSize(int s){
        this.size=s;
    }
    
    public int getPosY(){
        return this.posY;
    }
    
    public int getPosX(){
        return this.posX;
    }
    
    public int getSize(){
        return this.size;
    }
    
    public void setSpeedAlien(int s){
        this.speedAlien=s;
    }
    
    
}
