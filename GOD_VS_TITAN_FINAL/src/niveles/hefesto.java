/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niveles;

/**
 *
 * @author antonio
 */
public class hefesto extends capitulo{
    
    public hefesto (){
        super( lives, coins, stream, mapa);
    }
    
    private static int lives = 10;
    private static int coins = 200;
    private static String mapa = "mapas/mapaHefesto.png";
    private static int[][] stream = { 
        {2,2,0},
        {3,2,0},
        {3,4,0},
        {6,0,0},
        {5,3,0},
        {8,4,0},
        {10,4,0},
        {8,0,1},
        {10,2,2},
        {10,4,2},
    };
}
