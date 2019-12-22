/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.logic.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetwars.logic.Game;
import planetwars.logic.GameArena;
import planetwars.logic.graphicobjects.Ship;
import static planetwars.logic.graphicobjects.test.TorpedoTest.screenHeight;
import static planetwars.logic.graphicobjects.test.TorpedoTest.screenWidth;

/**
 *
 * @author jaakkpaa
 */
public class GameTest {
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 	
	public static GameArena gameArena;
	public static Game game;
	
	public GameTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {

	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		gameArena = new GameArena(1);
		game = new Game(screenWidth, screenHeight, gameArena, 0);
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void creatingNewGamePlacesShipInTheMiddleOfTheScreen() {
		assertEquals(screenWidth/2, gameArena.getPlayer1Ship().getXCoord());
		assertEquals(screenHeight/2, gameArena.getPlayer1Ship().getYCoord());
	}

	@Test
	public void withGameArenaOfLevel1NewGameWith1PlanetLeftIsCreated() {
		assertEquals(1, game.getPlanetsLeft());
	}
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
