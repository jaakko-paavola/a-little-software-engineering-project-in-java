/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetwars.ui;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import planetwars.database.Database;
import planetwars.database.Player;
import planetwars.database.PlayerDao;
import planetwars.logics.Animation;
import planetwars.logics.graphicobjects.BoundaryRectangle;
import planetwars.logics.Game;
import planetwars.logics.GameArena;
import planetwars.logics.graphicobjects.MapLocator;
import planetwars.logics.graphicobjects.Planet;
import planetwars.logics.graphicobjects.Ship;
import planetwars.logics.graphicobjects.Torpedo;
import planetwars.logics.graphicobjects.Shape;

/**
 *
 * @author jaakkpaa
 */
public class PlanetWarsApplication extends Application{
    
	public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenWidth = (int) resolution.getWidth();
	public static int screenHeight = (int) resolution.getHeight(); 
	public static int mapWidth = 300;
	public static int mapHeight = 300;
	public static Pane gameView;
	public static Pane mapView;
	public static GridPane gridPane;
    public static Map<KeyCode, Boolean> keysPressed = new HashMap<>();  
	public static Text textCoordinates = new Text(10, 20, "Coordinates: 0.0");
	public static Text textSpeed = new Text(10, 20, "Speed: 0.0");
	public static Text textPoints = new Text(10, 20, "Points: 0");

	public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) throws Exception {
		GridPane paneSignIn = new GridPane();
		Text textUsername = new Text("Username");	
		TextField textFieldUsername = new TextField();	
		Text textPassword = new Text("Password");	
		TextField textFieldPassword = new TextField();	
		Button buttonSignIn = new Button("Sign in");
		Button buttonSignUp = new Button("Sign up");
		
		Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:planetwars.db");
		PlayerDao playerDao = new PlayerDao(database);
		
		paneSignIn.add(textUsername, 1, 1);
		paneSignIn.add(textFieldUsername, 2, 1);
		paneSignIn.add(textPassword, 1, 2);
		paneSignIn.add(textFieldPassword, 2, 2);
		paneSignIn.add(buttonSignIn, 1, 3);
		paneSignIn.add(buttonSignUp, 3, 3);
		
		Scene sceneLogIn = new Scene(paneSignIn);
		primaryStage.setScene(sceneLogIn);
		primaryStage.show();

		buttonSignUp.setOnAction((event) -> {
			try {
				playerDao.saveOrUpdate(new Player(textFieldUsername.getText(), textFieldPassword.getText()));
				Player player = playerDao.findOne(textFieldUsername.getText());
				startGame(primaryStage, player);
			} catch (Exception e) {
				primaryStage.setTitle(e.getMessage());
				return;
			}
		});
		buttonSignIn.setOnAction((event) -> {
			try {
				Player player = playerDao.findOne(textFieldUsername.getText());
				if (!player.getPassword().equals(textFieldPassword.getText())) {
					primaryStage.setTitle("Wrong password");
					return;
				}
				startGame(primaryStage, player);
			} catch (Exception e) {
				primaryStage.setTitle(e.getMessage());
				return;
			}
		});	
    }

	private void startGame(Stage primaryStage, Player player) {
		primaryStage.close();
		
		AnchorPane rootPane = new AnchorPane();
		rootPane.setPrefSize(GameArena.spaceWidth, GameArena.spaceHeight);
		
		gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setStyle("-fx-background-color: black; -fx-border-color: red");
		gridPane.setMinWidth(screenWidth);
		gridPane.add(textCoordinates, 2, 1);
		gridPane.add(textSpeed, 3, 1);
		gridPane.add(textPoints, 4, 1);
		gridPane.setAlignment(Pos.CENTER);
		
		textCoordinates.setFill(Color.WHITE);
		textSpeed.setFill(Color.WHITE);
		textPoints.setFill(Color.WHITE);
		
		gameView = new Pane();
		gameView.setStyle("-fx-background-color: black");
		gameView.setPrefSize(screenWidth, screenHeight);
		
		mapView = new Pane();
		mapView.setPrefSize(mapWidth, mapHeight);
		mapView.setStyle("-fx-background-color: black; -fx-border-color: green");
		
		GameArena gameArena = new GameArena();
		Game game = new Game(screenWidth, screenHeight);
		
		gameView.getChildren().add(game.getPlayer1Ship().getShape());
		mapView.getChildren().add(game.getMapLocator().getShape());
		for (Planet planet : gameArena.getPlanets()) {
			gameView.getChildren().add(planet.getShape());
			mapView.getChildren().add(planet.getMapViewPlanet().getShape());
		}
		gameView.getChildren().add(gameArena.getBoundaryRectangle().getShape());
		
		rootPane.getChildren().addAll(gameView, gridPane, mapView);
		
		Scene scene = new Scene(rootPane);
		scene.setOnKeyPressed(event2 -> {
			keysPressed.put(event2.getCode(), Boolean.TRUE);
		});
		
		scene.setOnKeyReleased(event2 -> {
			keysPressed.put(event2.getCode(), Boolean.FALSE);
		});
		
		new Animation(gameArena, game).start();
		
		primaryStage.setTitle("Planet Wars");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
}
