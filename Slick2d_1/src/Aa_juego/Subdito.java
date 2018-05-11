/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;

/**
 *
 * @author dante
 */
abstract public class Subdito {
    
    public enum type{Tank, Pathfinder, Soldier};
    public enum direccion{LEFT,RIGHT, UP, DOWN};
    private double health;

    private double speed; 
    private double armor;
    private double XLoc;
    private double YLoc;
    private String name;
    private boolean alive;
    /*private final double freezeMultiplier = 0.3;	//how much speed is reduced by when frozen
    private boolean frozen = false;
    private long freezeStartTime = -10000;	//when the critter was frozen
    private long freezeDuration = 4000;	*/	//How long freeze lasts in ms
    public boolean canMove = true;
    protected type tipoSubdito;
    private double distanciaRecorrida;
    private boolean arrive = false;

    private boolean visible = true;
    
    
    public Subdito (double xPos, double yPos, double pHealth, double pSpeed, double pArmor, String pName, type pTipoSubdito){
        this.health = pHealth;
        this.armor = pArmor;
        this.speed = pSpeed;
        this.name = pName;
        this.XLoc = xPos;
        this.YLoc = yPos;
        alive = true;
        this.tipoSubdito = pTipoSubdito;
        distanciaRecorrida = 0;
        
    }
    
    public void takeDamage (double damage){
        health = health - (damage - armor);
        if (health <= 0){
            alive = false;
            visible = false;
        }
    }
    
    public void move(){
        
    }
    
    //Getters
    
    
    public double getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }

    public double getArmor() {
        return armor;
    }

    public double getXLoc() {
        return XLoc;
    }

    public double getYLoc() {
        return YLoc;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public type getTipoSubdito() {
        return tipoSubdito;
    }

    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public boolean isArrive() {
        return arrive;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }
    
    
}
