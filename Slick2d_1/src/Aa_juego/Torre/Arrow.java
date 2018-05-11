/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego.Torre;

/**
 *
 * @author dante
 */
public class Arrow extends Towers{
    
        public Arrow(double xPos, double yPos) {
        super(xPos, yPos);
        this.buyingCost = newbuyingCost;
        this.refundValue = newRefundValue;
        this.reloadTime = newReloadTime;
        this.power = newPower;
        this.range = newRange;
        this.towerType = type.Bomb;
    }
    //los atributos estan puestos al azar
    private static int newbuyingCost = 100;
    private static int newRefundValue = 80;
    private static double newReloadTime = 0.5;
    private static double newRange = 500;
    private static double newPower = 0.8;

}

