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
public class SubditoExplorador extends Subdito{
    
    
    public SubditoExplorador (int[][] location){
        super (location, health, speed, armor, name, type.Pathfinder);
    }
    private static String name = "Pathfinder";
    private static double health = 100;
    private static double speed = 1.1;
    private static double armor = 0;
    
}
