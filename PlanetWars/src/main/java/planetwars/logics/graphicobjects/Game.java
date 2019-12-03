/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logics.graphicobjects;

import java.util.ArrayList;
import java.util.List;
import static planetwars.ui.PlanetWarsApplication.screenHeight;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class Game {
	private Ship player1Ship;
	private List<Torpedo> torpedos;	
	private MapLocator mapLocator;	
	private int points;

	public static int player1StartingXCoord = Math.round(screenWidth/2);
	public static int player1StartingYCoord = Math.round(screenHeight/2);	

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public Game(int screenWidth, int screenHeight) {
		this.player1Ship = new Ship(player1StartingXCoord, player1StartingYCoord);
		this.mapLocator = new MapLocator(player1Ship);
		this.player1Ship = player1Ship;
        this.torpedos = new ArrayList<>();
		this.points = 0;
	}
	
	public Ship getPlayer1Ship() {
		return player1Ship;
	}	

	public MapLocator getMapLocator() {
		return mapLocator;
	}

	public List<Torpedo> getTorpedos() {
		return torpedos;
	}
	
}

