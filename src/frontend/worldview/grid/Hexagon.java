/**
 * 
 */
package frontend.worldview.grid;

import javafx.scene.shape.Polygon;

/**
 * @author Stone Mathers
 * Created 4/11/2017
 */
public class Hexagon extends Polygon {

	public static final double FULL_CIRCLE = Math.PI * 2;
	
	private double myMinWidth, myMaxWidth;

	public Hexagon(double minWidth, double maxWidth, double scale) {
		setPoints(minWidth, maxWidth, scale);
	}

	/**
	 * @param points
	 */
	public Hexagon(double... points) {
		super(points);
	}
	
	public void setPoints(double scale, double minWidth, double maxWidth){
		myMinWidth = minWidth;
		myMaxWidth = maxWidth;
		setPoints(scale);
	}
	
	private void setPoints(double scale){
		getPoints().clear();
		double width = myMinWidth + ((myMaxWidth - myMinWidth) * scale);
		double radius = width/(Math.cos(FULL_CIRCLE/12) - Math.cos((FULL_CIRCLE/12) * 5));
		
		for(int i = 1; i < 12; i += 2){
			getPoints().add(radius * Math.cos((FULL_CIRCLE/12) * i));
			getPoints().add(radius * Math.sin((FULL_CIRCLE/12) * i));
		}
	}

}
