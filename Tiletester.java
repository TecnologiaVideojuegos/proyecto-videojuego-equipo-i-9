/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FrancescoAndreace
 */
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.tiled.TiledMap;


public class Tiletester extends BasicGame {
 
    TiledMap map;
    public Tiletester()
    {
        super("Wizard game");
        
    }
 
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new Tiletester());
            app.setDisplayMode(1200, 900, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
 
    @Override
    public void init(GameContainer container) throws SlickException
    {
            map = new TiledMap("/Users/FrancescoAndreace/Documents/GitHub/proyecto-videojuego-equipo-i-9/v.0.1__Map.tmx","/Users/FrancescoAndreace/Documents/GitHub/proyecto-videojuego-equipo-i-9");
      
    }      
 
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        
    }
 
        @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        map.render(100,100,0,0,10,10);
    }
}
