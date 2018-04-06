/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FrancescoAndreace
 */
public class TowerOne {
 // private int life = 300;
    private int attack;
    private double attackSpeed;
    private int attackType; // 0 = area , 1 = target;
    public TowerOne()
    {
        attack = 100;
        attackSpeed = 1;
        attackType = 0;
    }
    
    public void SpeedUp(int i)
    {
        attackSpeed = attackSpeed*i;
    }
    
    public void SpeedDown(int i)
    {
        attackSpeed = attackSpeed/i;
    }
    
    public void Stop()
    {
        attackSpeed = 0;
    }
    
    public void attack()
    {
        
    }
}

