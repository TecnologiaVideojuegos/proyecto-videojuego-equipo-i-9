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
public class SubditoSoldado extends Subdito{
    
    
    public SubditoSoldado (int[][] location){
        super (location, health, speed, armor, name, type.Soldier);
    }
    private static String name = "Soldier";
    private static double health = 150;
    private static double speed = 0.75;
    private static double armor = 10;
    
}
