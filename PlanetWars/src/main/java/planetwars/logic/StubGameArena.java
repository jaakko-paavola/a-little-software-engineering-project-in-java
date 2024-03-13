/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.logic;

import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import planetwars.logic.graphicobjects.BoundaryRectangle;
import planetwars.logic.graphicobjects.MapLocator;
import planetwars.logic.graphicobjects.Planet;
import planetwars.logic.graphicobjects.Shape;
import planetwars.logic.graphicobjects.Ship;
import planetwars.logic.graphicobjects.Torpedo;

/**
 *
 * @author jaakkpaa
 */
public class StubGameArena implements GameArenaInterface {
	private ArrayList<Planet> planets;
	private BoundaryRectangle boundaryRectangle;
	private List<Torpedo> torpedos;	
	private Ship player1Ship;
	private MapLocator mapLocator;	

	private int spaceWidth = 10000;
	private int spaceHeight = 10000;
	private final int player1StartingXCoord = screenWidth / 2;
	private final int player1StartingYCoord = screenHeight / 2;
	
	public StubGameArena(int level) {
		player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		boundaryRectangle = new BoundaryRectangle(Color.RED, spaceWidth, spaceHeight);
		boundaryRectangle.getShape().setFill(Color.TRANSPARENT);
		planets = new ArrayList<Planet>();	
		planets.add(new Planet(player1StartingXCoord, player1StartingYCoord, 1000, Color.CORAL, 10000, 10000));
		planets.get(0).setAlive(false);
		planets.get(0).setConquered(true);
		torpedos = new ArrayList<Torpedo>();
		mapLocator = new MapLocator(player1Ship, this);
	}

	@Override
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public int getSpaceWidth() {
		return spaceWidth;
	}

	public int getSpaceHeight() {
		return spaceHeight;
	}

	@Override
	public Shape getBoundaryRectangle() {
		return boundaryRectangle;
	}

	public Ship getPlayerShip() {
		return this.player1Ship;
	}

	@Override
	public List<Torpedo> getTorpedos() {
		return torpedos;
	}

	@Override
	public MapLocator getMapLocator() {
		return mapLocator;
	}

}
