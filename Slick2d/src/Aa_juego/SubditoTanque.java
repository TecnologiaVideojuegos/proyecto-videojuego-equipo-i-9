/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;

import Aa_juego.Subdito.type;

/**
 *
 * @author dante
 */
public class SubditoTanque extends Subdito{
    
    
    public SubditoTanque (double xPos, double yPos){
        
        super (xPos, yPos, health, speed, armor, name, type.Tank);
    }
    private static String name = "Tank";
    private static double health = 200;
    private static double speed = 0.5;
    private static double armor = 20;
    
}
