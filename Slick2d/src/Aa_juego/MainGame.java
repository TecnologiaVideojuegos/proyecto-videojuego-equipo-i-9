/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;

/**
 *
 * @author dante
 */
public class MainGame extends StateBasedGame{

    public static final int menuScreen = 0;
    public static final int playScreen = 1;
    public static final int editMapScreen = 2;
    public static final int mapSelectScreen =3;

    public MainGame(String title) {
        super(title);
        this.addState(new PruebaMapa());
	//this.addState(new MenuScreen(menuScreen));
	//this.addState(new PlayScreen(playScreen));
	//this.addState(new EditMapScreen(editMapScreen));
	//this.addState(new MapSelectScreen(mapSelectScreen));
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.enterState(menuScreen);
    }
    
    public static void main (String[] args) throws SlickException{
        AppGameContainer app = new AppGameContainer(new MainGame("Gods vs Titans TD"));
        //Mira el tamaño de la pantalla
        app.setDisplayMode(1400, 900, false);
        app.setShowFPS(true);
        
        app.start();
        
    }
}
