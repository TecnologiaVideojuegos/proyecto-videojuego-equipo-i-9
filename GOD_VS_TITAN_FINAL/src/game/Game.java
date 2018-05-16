package game;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;




public class Game extends StateBasedGame {


        
	static long tickCount = 0;

	Image blackBeetleDead;
	Image SandTile;
	Image GravelTile;
	Image StartGameButtonGraphic;
	Image EditMapButtonGraphic;
        Music mainMusic;


	public static final int menuScreen = 0;
	public static final int playScreen = 1;
	public static final int mapSelectScreen = 3;

	public Game(String title) {
		super(title);
		this.addState(new MenuScreen(menuScreen));
		this.addState(new PlayScreen(playScreen));
		this.addState(new MapSelectScreen(mapSelectScreen));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {

		this.enterState(menuScreen);
                mainMusic = new Music("graficos/regofast.ogg");
                mainMusic.loop();
		
	}

	public static void main(String[] args) throws SlickException {


		
		AppGameContainer app = new AppGameContainer(new Game("God vs Titans"));
		
		app.setDisplayMode(1024, 1024, false); //es para la imajen
		app.setShowFPS(false);
		
		app.setMinimumLogicUpdateInterval(20);
		app.setMaximumLogicUpdateInterval(21);
		app.start();
	}





}
