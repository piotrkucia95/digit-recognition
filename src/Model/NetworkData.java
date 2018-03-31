package Model;

import Controller.Network;

//import Controller.NeuralNetwork;

public class NetworkData {
	
	private Network net;
	private double[] input;
	private double[][] target;
	
	public NetworkData() throws Exception {
		
		net = Network.loadNetwork("res/test.txt");	 
		input = new double[784];
		target = new double[][]{
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
		};
	}
	
	public Network getNetwork() {
		return net;	
	}
	
	public double[] getInput() {
		return input;	
	}
	
	public void setInput(double[] array) {
		input = array;
	}
	
	public double[][] getTarget() {
		return target;	
	}
	
	public void setTarget(double[][] array) {
		target = array;	
	}
}
