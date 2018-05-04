/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;
import Aa_juego.Torre.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author dante
 */
public class PruebaMapa extends BasicGameState{

    private Towers tw;
    private TiledMap map;
    private String mapName;
    private int updateCounter = 0;
    private static int UPDATE_TIME = 1000;
    private int originalTileID = 0;
    private String message = "------";
    private boolean click = false;
    private boolean rClick;
    private Subdito sb;
    @Override
    public int getID() {
        return originalTileID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new TiledMap("testdata/testmap.tmx","testdata");
	// read some properties from map and layer
	mapName = map.getMapProperty("name", "Unknown map name");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        map.render(10, 10, 4,4,15,15);
	if(click){
            Image img = new Image("Graficos/torre_3.png");
            img.setRotation( (float) tw.getAngleOfRotation());
            img.drawCentered( (float) tw.getxPos(), (float) tw.getyPos());}
        if(rClick){
            Image img = new Image("Graficos/Megaman_sprite1.png");
            img.setRotation( (float) tw.getAngleOfRotation());
            img.drawCentered( (float) tw.getxPos(), (float) tw.getyPos());}
	g.scale(0.35f,0.35f);
        map.render(1400, 0);
	g.resetTransform();
        g.drawString(message, 100, 100);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
    }
    public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {			
                    message = "Single Click: "+button+" "+x+","+y;
                    tw = new Arrow(x,y);
                    click = true;
                    /*try {
                        Image img = new Image("Graficos/torre.png");
                        img.setRotation( (float) tw.getAngleOfRotation());
                        img.drawCentered( (float) tw.getxPos(), (float) tw.getyPos());
                        
                    } catch (SlickException ex) {
                        Logger.getLogger(PruebaMapa.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
		}
		if (button == 1) {
                    message = "Double Click: "+button+" "+x+","+y;
                    rClick = true;
                    sb = new SubditoSoldado(x,y);
		}
	}
}
