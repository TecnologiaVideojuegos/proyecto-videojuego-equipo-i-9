/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author dante
 */
public class GeneradorSubditos {

    private int[][] location;
    private int level;
    private Queue<Subdito> colaSubditos = new LinkedList<Subdito>();
    
    public GeneradorSubditos (int[][] pLocation, int pLevel){
        this.location = pLocation;
        this.level = pLevel;
    }
    
    int[][] ListaSubditos = {
        {0,0,6},
        {2,0,10},
        {3,2,12},
        {5,3,15},
        {10,5,10},
        {15,7,18}}; //Lista para un nivel, para las distintas oleadas
        
    public void generadorCola(){
        
    }
}

