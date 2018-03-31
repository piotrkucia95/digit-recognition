package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PaintPanel extends Pane{
	
	//actual space you can paint on
	private Canvas canvas;
	
	//paintPanel constructor
	public PaintPanel(int width, int heigth) {
		
		super();
		canvas = new Canvas(width, heigth);
		setPrefSize(width, heigth);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//lambda expression allowing to paint on canvas
		canvas.setOnMouseDragged(e -> {			
			double size = 15;
			double x = e.getX() - size/2;
			double y = e.getY() - size/2;
			gc.setFill(Color.BLACK);
			gc.fillRect(x, y, size, size);
		
		});
		getChildren().add(canvas);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
