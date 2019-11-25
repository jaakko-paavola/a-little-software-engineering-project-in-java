/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package planetwars.logics.graphicobjects;

/**
 *
 * @author jaakkpaa
 */
import planetwars.ui.PlanetWarsApplication;
import java.util.Timer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import static planetwars.ui.PlanetWarsApplication.SPACE_WIDTH;
import static planetwars.ui.PlanetWarsApplication.mapWidth;
import static planetwars.ui.PlanetWarsApplication.screenWidth;

public abstract class Shape {
	
	private javafx.scene.shape.Shape shape;
	private Point2D movement;
	private Point2D previous;
	private int visits = 0;
	
	public Shape(Polygon polygon, int x, int y) {
		this.shape = polygon;
		this.shape.setTranslateX(x);
		this.shape.setTranslateY(y);
		this.shape.setFill(Color.WHITE);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public Shape(Circle circle, Color color) {
		this.shape = circle;
		this.shape.setTranslateX(circle.getCenterX());
		this.shape.setTranslateY(circle.getCenterY());
		this.shape.setFill(color);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public Shape(Rectangle rectangle, Color color) {
		this.shape = rectangle;
		this.shape.setTranslateX(rectangle.getX());
		this.shape.setTranslateY(rectangle.getY());
		this.shape.setStroke(Color.WHITE);
		
		this.movement = new Point2D(0, 0);
		this.previous = new Point2D(0, 0);
	}
	
	public javafx.scene.shape.Shape getShape() {
		return shape;
	}
	
	public void turnLeft() {
		this.shape.setRotate(this.shape.getRotate() - 1);
	}
	
	public void turnRight() {
		this.shape.setRotate(this.shape.getRotate() + 1);
	}
	
	public void accelerateToOppositeDirectionInReferenceTo(Shape reference) {
		double changeX = Math.cos(Math.toRadians(reference.getShape().getRotate()));
		double changeY = Math.sin(Math.toRadians(reference.getShape().getRotate()));
		
		changeX *= -0.005;
		changeY *= -0.005;
		
		this.setMovement(this.getMovement().add(changeX, changeY));
	}
	
	public void brakeToOppositeDirectionInReferenceTo(Shape reference) {
		double changeX = Math.cos(Math.toRadians(reference.getShape().getRotate()));
		double changeY = Math.sin(Math.toRadians(reference.getShape().getRotate()));
		
		changeX *= 0.001;
		changeY *= 0.001;
		
		this.setMovement(this.getMovement().add(changeX, changeY));
	}
	
	public boolean outOfGameArea() {
		if (((this.getXCoord() <= (int) -Math.round(((1.0 * screenWidth / SPACE_WIDTH) * mapWidth) / 2)
							|| this.getYCoord() <= (int) -Math.round(((
							1.0 * screenWidth / PlanetWarsApplication.SPACE_HEIGHT)
							* PlanetWarsApplication.mapHeight) / 2)))
							||
							(this.getXCoord() >= (int) mapWidth - Math.round(((1.0 * screenWidth / SPACE_WIDTH) * mapWidth) / 2)
							|| this.getYCoord() >= (int) PlanetWarsApplication.mapHeight - Math.round(((
							1.0 * screenWidth / PlanetWarsApplication.SPACE_HEIGHT)
							* PlanetWarsApplication.mapHeight) / 2))) {
			return true;
		}
		return false;
	}
	
	public void move() {
		this.shape.setTranslateX(this.shape.getTranslateX() + this.getMovement().getX());
		this.shape.setTranslateY(this.shape.getTranslateY() + this.getMovement().getY());
	}
	
	public int getXCoord() {
		return (int) Math.round(this.shape.getTranslateX());
	}
	
	public int getYCoord() {
		return (int) Math.round(this.shape.getTranslateY());
	}
	
	public int getXSpeed(Shape reference) {
		return (int) Math.round((this.movement.getX() - this.previous.getX()) * 1000);
	}
	
	public int getYSpeed(Shape reference) {
		return (int) Math.round((this.movement.getY() - this.previous.getY()) * 1000);
	}
	
	public Point2D getMovement() {
		return movement;
	}
	
	public void setMovement(Point2D movement) {
		if (visits == 10000) {
			this.previous = this.movement;
			visits = 0;
		}
		this.movement = movement;
		visits++;
	}
}