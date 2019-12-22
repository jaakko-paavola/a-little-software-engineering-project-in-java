/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Ship;
import planetwars.logic.graphicobjects.Torpedo;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 * The class handles the current game arena with its planets and boundaries and
 * creates a random game arena with the number of planets equal to the level
 * the player is on.
 * @author jaakkpaa
 */
public class GameArena implements GameArenaInterface {
	private int spaceWidth = 10000;
	private int spaceHeight = 10000;	
	private ArrayList<Planet> planets;
	private BoundaryRectangle boundaryRectangle;
	private Ship player1Ship;
	private List<Torpedo> torpedos;	
	private final static int player1StartingXCoord = screenWidth / 2;
	private final static int player1StartingYCoord = screenHeight / 2;
	
	/**
	 * Creates a new game arena with the number of planets decided by the
	 * player's level. The planets are placed in random locations in the game
	 * arena having random sizes and colors.
	 *
	 * @param level The player's level.
	 */	
	public GameArena(int level) {
		planets = new ArrayList<>();
		this.player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		this.torpedos = new ArrayList<>();

		for (int i = 0; i < level; i++) {
			planets.add(new Planet(new Random().nextInt(spaceWidth / 2 - 100) + 50,
					new Random().nextInt(spaceHeight / 2 - 100) + 50,
					new Random().nextInt(80) + 20,
					Color.rgb(new Random().nextInt(200) + 56,
							new Random().nextInt(200) + 56,
							new Random().nextInt(200) + 56), spaceWidth, spaceHeight));
		}

		boundaryRectangle = new BoundaryRectangle(Color.RED, spaceWidth, spaceHeight);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
	}
	
	public int getSpaceWidth() {
		return spaceWidth;
	}

	public int getSpaceHeight() {
		return spaceHeight;
	}

	public BoundaryRectangle getBoundaryRectangle() {
		return boundaryRectangle;
	}
	
	@Override
	public ArrayList<Planet> getPlanets() {
		return planets;
	}
	
	public static int getPlayer1StartingXCoord() {
		return player1StartingXCoord;
	}

	public static int getPlayer1StartingYCoord() {
		return player1StartingYCoord;
	}
	
	public Ship getPlayer1Ship() {
		return player1Ship;
	}	
	
	public List<Torpedo> getTorpedos() {
		return torpedos;
	}
}