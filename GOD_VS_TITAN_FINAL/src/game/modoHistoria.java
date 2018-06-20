/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import map.*;
import niveles.*;
/**
 *
 * @author antonio
 */
public class modoHistoria extends BasicGameState{

    
    Image SandTile;
        Image backgroundImage;
	ArrayList<Map> historyList;
	ArrayList<Rectangle> mapButtonList;
	private final int rectangleWidth = 150;
	private final int rectangleHeight = 30;
	private final int sideMenuWidth = 192;
	private final int bottomMenuWidth = 128;
	private final int mapButtonXInitialOffset = 20;
	private final int mapButtonYInitialOffset = 100;
	private final String selectMapString = "Please select a chapter to play";
	Font font ;
	TrueTypeFont ttf;

	LoadFile loading;
    private boolean historia;
    private int lvl = 0;
        
        
        public modoHistoria (int state){

	}
        
    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        font = new Font("Lithos Pro", Font.BOLD, 30);
        ttf = new TrueTypeFont(font, true);
        backgroundImage = new Image("graficos/grece.png");

    }

    @Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		drawMapBackground(container);
		drawMapButtons(g);

		//draw the title string
		ttf.drawString(container.getWidth() / 2 - ttf.getWidth(selectMapString) / 2, 30, selectMapString, Color.black);

	}

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(Mouse.isButtonDown(0))
			mouseClicked(Mouse.getX(), gc.getHeight() - Mouse.getY(), sbg, gc);
    }
    
    public void drawMapBackground(GameContainer container){
                backgroundImage.draw();
	}
    
    public void drawMapButtons(Graphics g){
		for(int count = 0 ; count<mapButtonList.size() ; count++){
			g.setColor(Color.black);
			g.draw(mapButtonList.get(count));
			g.drawString(loading.getListofFiles(1).get(count) , mapButtonList.get(count).getMinX()+10, mapButtonList.get(count).getMinY()+rectangleHeight/4);	
		}
	}
	
	
	public void initializeAndLoadMaps(){
		mapButtonList = new ArrayList<Rectangle>();
		historyList = new ArrayList<Map>();
		loading = new LoadFile();

		historyList.addAll(loading.getAllMap());

	}
	
	//create rectangle button for each map
	public void createRectangleMapButtons(GameContainer container){
		int mapX = mapButtonXInitialOffset;
		int mapY = mapButtonYInitialOffset;
                for(Map s : historyList){
			Rectangle rectangle = new Rectangle(mapX, mapY, rectangleWidth, rectangleHeight);
			mapX +=(rectangleWidth+30) ;
			if(mapX>container.getWidth()-rectangleWidth){
				mapX=mapButtonXInitialOffset;
				mapY+=rectangleHeight+30;
			}
			mapButtonList.add(rectangle);

		}

	}

	public void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException{
		for(int count = 0 ; count < (mapButtonList.size()-lvl) ; count++){
			//compare if the click occurred inside one of the rectangle buttons, 
			//if it did, load that map, change the frame size to match the map size, and switch states to the PlayScreen state
			if(mapButtonList.get(count).contains(x, y)){
				
                                capitulo cap = new Nivel_1_Artemisa();
                                
                                switch(count){
                                    case 0:
                                        cap = new Nivel_1_Artemisa();
                                        break;
                                    case 1:
                                        cap = new Nivel_2_Hefesto();
                                        break;
                                    case 2:
                                        cap = new Nivel_3_Apolo();
                                        break;
                                    case 3:
                                        cap = new Nivel_4_Poseidon();
                                        break;
                                    case 4:
                                        cap = new Nivel_5_Hades();
                                        break; 
                                    case 5:
                                        cap = new Nivel_6_Zeus();
                                        break;
                                }
                                PlayScreen s = (PlayScreen) sbg.getState(Game.playScreen);
				s.setMap(historyList.get(count));
                                s.setHistoria(historia);
                                System.out.println(cap.getMapa());
                                s.setNivel(cap);
				AppGameContainer gameContainer = (AppGameContainer) container;
				gameContainer.setDisplayMode(historyList.get(count).getWidthOfMap()*32 +sideMenuWidth, historyList.get(count).getHeightOfMap()*32 +bottomMenuWidth, false);
				s.createRectangleButtons(gameContainer);
				sbg.enterState(Game.playScreen);
			}
		}
	}

    void setHistoria(boolean b) {
        this.historia=b;
    }

    void setCompleto(int i) {
        this.lvl= 6 - i;
    }


}

