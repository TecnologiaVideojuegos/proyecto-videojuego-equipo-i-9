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
public class artemisa extends capitulo{
    
    public artemisa (){
        super( lives, coins, stream, mapa);
    }
    
    private static int lives = 15;
    private static int coins = 150;
    private static String mapa = "mapas/mapaArtemisa.png";
    private static int[][] stream = { 
        {0,2,0},
        {1,2,0},
        {4,0,0},
        {6,0,0},
        {4,3,0},
        {6,4,0},
        {7,5,0},
        {10,0,0},
        {10,2,0},
        {8,0,2},
    };
}
