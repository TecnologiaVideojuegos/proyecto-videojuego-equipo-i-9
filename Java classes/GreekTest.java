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
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
/**
 * @author panos
 */
public class GreekTest extends BasicGame
{
    /** The game container */
	private GameContainer container;
    /** The background image */
	private Image background;
    /** The image being rendered */
	private Image image;
	/** The areas defined */
	private MouseOverArea[] areas = new MouseOverArea[4];
	/** The mouse image */
        private Image mouse;
    
    public GreekTest()
    {
        super("Wizard game");
        
    }
 
    public static void main(String[] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new GreekTest());
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
            mouse = new Image("/Users/FrancescoAndreace/Downloads/navigation.png");
            container.setMouseCursor(mouse, 0, 0);
            image = new Image("/Users/FrancescoAndreace/Downloads/play.png");
    }
 
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
    }
 
    public void render(GameContainer container, Graphics g) throws SlickException
    {
       Image background = new Image("/Users/FrancescoAndreace/Downloads/25254.jpg");
       background.draw(0, 0, (float) 0.4);
       image.draw(150, 200, (float) 0.1);
    }
}