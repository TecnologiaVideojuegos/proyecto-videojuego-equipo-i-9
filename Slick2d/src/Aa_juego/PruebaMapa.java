/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aa_juego;
import Aa_juego.Proyectil.TipoProyectil;
import Aa_juego.Torre.*;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author dante
 */
public class PruebaMapa extends BasicGameState{

    private static int selectedTower =-1;
    private long lastClick=(-1*200);
    private Towers tw;
    private TiledMap map;
    private String mapName;
    private int updateCounter = 0;
    private static int UPDATE_TIME = 1000;
    private int originalTileID = 0;
    private String message = "------";
    private boolean click = false;
    private boolean rClick = false;
    private Subdito sb;
    private ArrayList<Towers> towerList = new ArrayList<Towers>();
    private ArrayList<Subdito> subditoList = new ArrayList<Subdito>();
    private ArrayList<Proyectil> projectileList = new ArrayList<Proyectil>();
    private ArrayList<Proyectil> pToRemove;
    private boolean waveInProgress = false;
    @Override
    public int getID() {
        return originalTileID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new TiledMap("testdata/testmap.tmx","testdata");
	// read some properties from map and layer
	mapName = map.getMapProperty("name", "Unknown map name");
        Subdito mg = new SubditoSoldado(300, 300);
        subditoList.add(mg);
        
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        map.render(10, 10, 4,4,15,15);
	/*if(click){
            Image img = new Image("Graficos/torre_3.png");
            img.setRotation( (float) tw.getAngleOfRotation());
            img.drawCentered( (float) tw.getxPos(), (float) tw.getyPos());}
        if(rClick){
            Image img = new Image("Graficos/Megaman_sprite1.png");
            img.setRotation( (float) tw.getAngleOfRotation());
            img.drawCentered( (float) tw.getxPos(), (float) tw.getyPos());}*/
        drawTorre(g);
        drawSubdito(g);
        drawProyectil(g);
	g.scale(0.35f,0.35f);
        map.render(1400, 0);
	g.resetTransform();
        g.drawString(message, 100, 100);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        updateProjectiles();			
	updateSubdito();
	targetSubdito();
	attackSubdito();
        
        if(Mouse.isButtonDown(0)){
            mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), game, container);
	}
        
    }
    public void mouse_Clicked(int x, int y, StateBasedGame sbg, GameContainer container) {
	/*if (button == 0 && key == Input.KEY_SPACE){
            Subdito mg = new SubditoSoldado(x, y);
            subditoList.add(mg); 
        }*/	
        /*else*/ 
        reloadTowers();
        if (Mouse.isButtonDown(0) && getNearestTower(x,y) == null) {	
            Towers newTower = new Arrow(x,y);
            towerList.add(newTower);
            waveInProgress = true;
	}
        else if(Mouse.isButtonDown(1)){
            Towers t = getNearestTower(x,y);
            if(t!=null){			
		towerList.remove(t);
				
	}
            
                /*else if (selectedTower == 0) {
                    message = "Double Click: "+button+" "+x+","+y;
                    rClick = true;
                    sb = new SubditoSoldado(x,y);
		}*/
	}
    }
    public void keyPressed(int key, char c) {
		if (key == Input.KEY_SPACE) {
                    Subdito mg = new SubditoSoldado(300, 300);
                    subditoList.add(mg);
		}
	}
    public Towers getNearestTower(double x, double y){
	double distanceApproximation=100;
	Towers nearestTower=null;
	for(Towers t: towerList){
		if(Math.abs(t.getxPos()-x)+Math.abs(t.getyPos()-y)<distanceApproximation){
			nearestTower = t;
			distanceApproximation =Math.abs(t.getxPos()-x)+Math.abs(t.getyPos()-y);
		}

	}
	return nearestTower;
	}
    private void drawTorre(Graphics g) throws SlickException{
        for(int i = 0; i < towerList.size(); i++){
            Towers t = towerList.get(i);
            Image img = new Image("Graficos/torre.png");
            img.setRotation( (float) t.getAngleOfRotation());
            img.drawCentered((float) t.getxPos(), (float) t.getyPos());
            
        }

    }

    private void drawSubdito(Graphics g) throws SlickException {
        for(int i = 0; i < subditoList.size(); i++){
            Subdito s = subditoList.get(i);
            Image img = new Image("Graficos/Megaman_sprite1.png");
            img.drawCentered((float) s.getXLoc(), (float) s.getYLoc());
            
        }
    }

    private void attackSubdito() {
        for(int i = 0; i < towerList.size(); i++){
            Towers t = towerList.get(i);
           
            if(t.getSubdito()!= null &&t.canAttack()){
		attackCritter(t);
		t.setReloadTime(System.currentTimeMillis());
		}
	}
    }
    
    private void drawProyectil(Graphics g) throws SlickException{
		for(int i = 0; i < projectileList.size(); i++){
                    Proyectil p = projectileList.get(i);
                    Image img = new Image("GRaficos/flecha.png");
			
                    img.setRotation( (float) p.angleOfProjectileInDegrees());
                    img.drawCentered((float)p.getxLocation(),(float)p.getyLocation());
		}
	}
    
    

    private void targetSubdito() {
        for(int i = 0; i < towerList.size(); i++){
            Towers t = towerList.get(i);
            
            t.setSubdito(null);
            for(int e = 0; e < subditoList.size(); e++){
                Subdito c = subditoList.get(e);
                if(c.isAlive()&&c.isVisible()){
                    //calculate distance
                    if(Math.abs(t.getxPos()-c.getXLoc())+Math.abs(t.getyPos()-c.getYLoc())<t.getRange())
			/*nearestTower = t;
			distanceApproximation =Math.abs(t.getxPos()-x)+Math.abs(t.getyPos()-y);
		}
                    double xDist= Math.abs(c.getXLoc() - t.getxPos());
                    double yDist= Math.abs(c.getYLoc() -  t.getyPos());
                    double dist = Math.sqrt((xDist*xDist)+(yDist*yDist));
                    if(dist<t.getRange() && t.getSubditoTravelDistanceMaximun()< c.getDistanciaRecorrida())*/{
			t.setSubdito(c);
			t.setSubditoTravelDistanceMaximun(c.getDistanciaRecorrida());
                        System.out.println("Torre disparando");
			}
		}

            }
            t.setSubditoTravelDistanceMaximun(0);
	}
    }

    
    
    
    private void updateSubdito() {
        boolean sAreStillVisible= false;
	ArrayList<Subdito> listaEliminar = new ArrayList<Subdito>();
	//Si el subdito esta vivo se  realiza el movimiento
	for(int i = 0; i < subditoList.size(); i++){
            Subdito s = subditoList.get(i);
		if(s.isAlive())
			s.move();
		else{
			listaEliminar.add(s);
		}
		if(s.isVisible())
			sAreStillVisible=true;
		if(s.isArrive()){
			listaEliminar.add(s);
		}
	}

	//Elimina todos los subditos que no esten vivos
	for(int i = 0; i < listaEliminar.size(); i++){
		subditoList.remove(listaEliminar.get(i));
	}

    }

    private void attackCritter(Towers t) {
        Proyectil projectile = new Proyectil(t.getxPos(), t.getyPos(), (double)t.getSubdito().getXLoc(), 
				(double)t.getSubdito().getYLoc(), t.getPower(), t.getSubdito(),  TipoProyectil.NORMAL);
        projectileList.add(projectile);
    }

    private void updateProjectiles() {
        pToRemove = new ArrayList<Proyectil>();
		for(int i = 0; i < projectileList.size(); i++){
                    Proyectil p = projectileList.get(i);
			if(!p.isHit())
				p.proyectileMove();
			else
				pToRemove.add(p);
		}

		for(int i = 0; i < pToRemove.size(); i++){
			projectileList.remove(pToRemove.get(i));
		}
    }

    private void reloadTowers() {
        for(Towers t : towerList){
		t.setTimeOfLastAttack(0);
	}
    }
    
    private void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException {

		//protection against multiple click registration
		if(lastClick + 200 > System.currentTimeMillis())
			return;
		lastClick = System.currentTimeMillis();

		/*if(ExitButton.contains(x,y)){
			restartGame();
			AppGameContainer gameContainer = (AppGameContainer) container;
			gameContainer.setDisplayMode(640, 480, false);
			sbg.enterState(Game.menuScreen);
		}*/

		//no towers selected
		if (selectedTower ==-1){

			if(!waveInProgress){
				waveInProgress = true;
				projectileList = new ArrayList<Proyectil>();
				reloadTowers();
			
			}
			

			/*for(int i=0;i<maximumNumberTowers;i++){
				if(TowerGraphicButtonsList.get(i).contains(x,y)){
					selectedTower = i;

				}
			}
			return;*/
		}
		//tower selected
		else if (selectedTower >=0){
			if(mouseOnMap(x,y)&&(getNearestTower(x,y) == null)){
				Towers newTower = new Arrow(x,y);

				towerList.add(newTower);
				//Player.getPlayer().addCredits((-1)*newTower.getBuyingCost());

				/*if (Player.getPlayer().getCredits()<0){
					//deny tower building due to insufficient funds or invalid tile
					Player.getPlayer().addCredits(newTower.getBuyingCost());
					towerList.remove(newTower);
				}*/
			}
		}
		
		else if(selectedTower == -3){
			if(mouseOnMap(x,y)){
				Towers t = getNearestTower(x,y);
				if(t!=null){
					//t.refundTower();			
					towerList.remove(t);
				}
			}

		}
		selectedTower=-1;
	}
    
    public boolean mouseOnMap(int x, int y){
		if((x<1400) && (y< 900)){
			return true;
		}
		else
			return false;
	}




}


