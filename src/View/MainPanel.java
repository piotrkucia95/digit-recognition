package View;

import java.util.Arrays;

import Controller.NetworkTools;
import Model.NetworkData;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainPanel {
	
	private Scene mainScene;
	private BorderPane rootNode;
	private Label instruction;
	private FlowPane mainPanel;
	private PaintPanel paintPanel;
	private FlowPane resultPanel;
	private Separator separator;
	private Button colorsButton;
	private Button clearButton;
	private Label number;
	private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private Series<Number, String> series1;
	private BarChart<Number, String> chart;
	private WritableImage snap;
	private NetworkData net;
	private double[][] colorArray;
	private double[] mainArray;
	private String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	//main panel constructor
	public MainPanel(Stage mainStage) throws Exception {
		
		rootNode		= new BorderPane();
		instruction		= new Label("Draw a digit and press 'Check':");
		mainPanel		= new FlowPane();
		mainScene		= new Scene(rootNode, 590, 300);
		paintPanel 		= new PaintPanel(280, 280);
		resultPanel 	= new FlowPane();
		colorsButton 	= new Button("Check");
		clearButton		= new Button("Clear");
		separator 		= new Separator(Orientation.VERTICAL);
		number			= new Label("     Number: ");
		xAxis 			= new CategoryAxis();
        yAxis 			= new NumberAxis();
        series1 		= new XYChart.Series();  
		chart			= new BarChart<Number, String>(yAxis, xAxis);
		net				= new NetworkData();
		colorArray 		= new double[28][28];
		mainArray		= new double[784];
		
		separator.setPrefSize(20, 0);
		resultPanel.setPrefSize(280, 300);
		number.setStyle("-fx-font-size: 20");
		
		colorsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, getColors);
		clearButton.addEventHandler(MouseEvent.MOUSE_PRESSED, clearCanvas);
		
		chart.setPrefSize(260, 260);
		xAxis.setLabel("Number");
		yAxis.setLabel("%");
		xAxis.setTickLabelRotation(90);
		   
		//adding series of data to a chart
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[0]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[1]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[2]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[3]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[4]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[5]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[6]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[7]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[8]));
        series1.getData().add(new XYChart.Data<Number, String>((double) 0, numbers[9]));
        
        chart.setLegendVisible(false);
        chart.getData().add(series1);
        
		resultPanel.getChildren().addAll(colorsButton, clearButton, number, chart);
		mainPanel.setStyle("-fx-border-color: #000000");
		
		mainPanel.getChildren().addAll(paintPanel, separator, resultPanel);
		rootNode.setTop(instruction);
		rootNode.setCenter(mainPanel);
		
		mainStage.setTitle("Numbers' recognition");
		mainStage.setResizable(false);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	//this event handler transforms canvas into a 2D array of int values informing whether some pixel is painted or not
	EventHandler<MouseEvent> getColors = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			
			snap = paintPanel.getCanvas().snapshot(null, null);
			colorArray = new double[28][28];
			
			for(int i=0; i<28; i++) {
				for(int j=0; j<28; j++) {
					if((snap.getPixelReader().getArgb(i*10, j*10)+1) == 0) {
						colorArray[i][j] = 0;
					} else {
						colorArray[i][j] = 1;
					}	
					mainArray[(28*i)+j] = colorArray[j][i];
				}
			}
					
			//chart values changed
			net.setInput(mainArray);
			//numbers' guessing
			number.setText("     Number:  " +NetworkTools.indexOfHighestValue(net.getNetwork().calculate(net.getInput())));
			series1.getData().clear();
			series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[0]*100, numbers[0]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[1]*100, numbers[1]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[2]*100, numbers[2]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[3]*100, numbers[3]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[4]*100, numbers[4]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[5]*100, numbers[5]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[6]*100, numbers[6]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[7]*100, numbers[7]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[8]*100, numbers[8]));
	        series1.getData().add(new XYChart.Data<Number, String>((net.getNetwork().calculate(net.getInput()))[9]*100, numbers[9]));
		
		}	
	};
	
	EventHandler<MouseEvent> clearCanvas = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			
			paintPanel.getCanvas().getGraphicsContext2D().setFill(Color.WHITE);
			paintPanel.getCanvas().getGraphicsContext2D().fillRect(0, 0, 280, 280);
			
		}
		
		
	};
}
