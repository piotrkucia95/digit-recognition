package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Network implements Serializable{

	public final int[] NETWORK_LAYER_SIZES;
	public final int INPUT_SIZE;
	public final int NETWORK_SIZE;
	public final int OUTPUT_SIZE;
	
	//output of each neuron
	public double[][] output;
	//weight of each connection[layer][neuron][prevNeuron]
	public double[][][] weight;
	//bias of each neuron
	public double[][] bias;
	
	public double[][] error_signal;
	public double[][] output_derivative;
	
	//neural network constructor
	public Network(int... NETWORK_LAYER_SIZES) {
		
		this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
		this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
		this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
		this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE-1];
		
		this.output		 		= new double[NETWORK_SIZE][];
		this.weight  			= new double[NETWORK_SIZE][][];
		this.bias	 			= new double[NETWORK_SIZE][];
		this.error_signal		= new double [NETWORK_SIZE][];
		this.output_derivative	= new double[NETWORK_SIZE][];
		
		for(int i=0; i<NETWORK_SIZE; i++) {
			
			//weight and bias arrays are filled up with random values
			this.output[i] 				= new double[NETWORK_LAYER_SIZES[i]];
			this.bias[i] 				= NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], -0.5, 0.7);
			this.error_signal[i] 		= new double[NETWORK_LAYER_SIZES[i]];
			this.output_derivative[i] 	= new double[NETWORK_LAYER_SIZES[i]];
			
			if(i>0) {
				weight[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], NETWORK_LAYER_SIZES[i-1], -1, 1);
			}
			
		}
	}
	
	//this method counts a network output after giving an input array
	public double[] calculate(double... input) {
		
		//input length must be equal to size of 1st layer
		if(input.length != NETWORK_LAYER_SIZES[0]) return null;
		
		this.output[0] = input;
		
		for(int layer=1; layer<NETWORK_SIZE; layer++) {
			for(int neuron=0; neuron<NETWORK_LAYER_SIZES[layer]; neuron++) {
				
				//for the beginning sum is equal to the bias of a neuron
				double sum = bias[layer][neuron];
				
				//sum is counted from basic neuron network equation
				for(int prevNeuron=0; prevNeuron<NETWORK_LAYER_SIZES[layer-1]; prevNeuron++) {
					sum += (weight[layer][neuron][prevNeuron] * output[layer-1][prevNeuron]);
				}
				
				//output of each neuron is sigmoid function of sum value 
				output[layer][neuron] = sigmoid(sum);
				output_derivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
			}
		}
		
		//function returns the output of last layer's neurons
		return output[NETWORK_SIZE-1];
	}
	
	//this method trains a neural network with TrainSet
	public void train(NetworkTrainSet set, int loops, int batch_size) {
		if((set.INPUT_SIZE != INPUT_SIZE) || (set.OUTPUT_SIZE != OUTPUT_SIZE)) return;
		for(int i=0; i<loops; i++) {
			NetworkTrainSet batch = set.extractBatch(batch_size);
			for(int j=0; j<batch_size; j++) {
				this.train(batch.getInput(j), batch.getOutput(j), 0.3);
			}
			System.out.println(MSE(batch));
		}
	}

	
	//"min squared error" - how far the output of network is from the target
    public double MSE(double[] input, double[] target) {
        if(input.length != INPUT_SIZE || target.length != OUTPUT_SIZE) return 0;
        calculate(input);
        double v = 0;
        for(int i = 0; i < target.length; i++) {
            v += (target[i] - output[NETWORK_SIZE-1][i]) * (target[i] - output[NETWORK_SIZE-1][i]);
        }
        return v / (2d * target.length);
    }

	public double MSE(NetworkTrainSet set) {
        double v = 0;
        for(int i = 0; i< set.size(); i++) {
            v += MSE(set.getInput(i), set.getOutput(i));
        }
        return v / set.size();
    }
	
	//train method is consisted out of 3 methods
	public void train(double[] input, double[] target, double eta) {
		if((input.length != INPUT_SIZE) || (target.length != OUTPUT_SIZE)) return;
		
		calculate(input);
		backpropError(target);
		updateWeights(eta);
	}
		
	//this method counts backpropagation error which is necessary to manipulate connections' weights
	public void backpropError(double[] target) {
		//error counted for last layer
		for(int neuron=0; neuron<NETWORK_LAYER_SIZES[NETWORK_SIZE-1]; neuron++) {
			error_signal[NETWORK_SIZE-1][neuron] = (output[NETWORK_SIZE-1][neuron] - target[neuron]) * 
					output_derivative[NETWORK_SIZE-1][neuron];
		}
		
		//errors counted for the hidden layers, starting from last
		for(int layer=NETWORK_SIZE-2; layer>0; layer--) {
			for(int neuron=0; neuron<NETWORK_LAYER_SIZES[layer]; neuron++) {
				double sum = 0;
				
				//sum is counted from backpropagation error formula
				for(int nextNeuron=0; nextNeuron<NETWORK_LAYER_SIZES[layer+1]; nextNeuron++) {
					sum += weight[layer+1][nextNeuron][neuron] * error_signal[layer+1][nextNeuron]; 
				}
				this.error_signal[layer][neuron] = sum * output_derivative[layer][neuron];
			}
		}
	}
	
	//eta is learning rate of the the network
	public void updateWeights(double eta) {
		
		//weights are counted from weight values formula
		for(int layer=1; layer<NETWORK_SIZE; layer++) {
			for(int neuron=0; neuron<NETWORK_LAYER_SIZES[layer]; neuron++) {
				
				double delta = (-eta) * error_signal[layer][neuron];
               	bias[layer][neuron] += delta;
               			
				for(int prevNeuron=0; prevNeuron<NETWORK_LAYER_SIZES[layer-1]; prevNeuron++) {
					weight[layer][neuron][prevNeuron] += (delta * output[layer-1][prevNeuron]);
				}
							
			}
		}
		
	}
	
	//sigmoid function
	public double sigmoid(double x) {
		return 1d/(1 + Math.exp(-x));
	}
	
	public static void main(String[] args){
        Network net = new Network(4,3,3,2);

        NetworkTrainSet set = new NetworkTrainSet(4, 2);
        set.addData(new double[]{0.1,0.2,0.3,0.4}, new double[]{0.9,0.1});
        set.addData(new double[]{0.9,0.8,0.7,0.6}, new double[]{0.1,0.9});
        set.addData(new double[]{0.3,0.8,0.1,0.4}, new double[]{0.3,0.7});
        set.addData(new double[]{0.9,0.8,0.1,0.2}, new double[]{0.7,0.3});

        net.train(set, 100000, 4);

        for(int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(net.calculate(set.getInput(i))) + net.MSE(set.getInput(i), set.getOutput(i)));
        }
        System.out.println(net.MSE(set));

    }
	
	//this method saves weights and biases of a network to file
	public void saveNetwork(String fileName) throws Exception {
        
		Parser p = new Parser();
        p.create(fileName);
        ParserNode root = p.getContent();
        ParserNode netw = new ParserNode("Network");
        ParserNode ly = new ParserNode("Layers");
        netw.addAttribute(new ParserAttribute("sizes", Arrays.toString(this.NETWORK_LAYER_SIZES)));
        netw.addChild(ly);
        root.addChild(netw);
        
        for (int layer = 1; layer < this.NETWORK_SIZE; layer++) {

        	ParserNode c = new ParserNode("" + layer);
            ly.addChild(c);
            ParserNode w = new ParserNode("weights");
            ParserNode b = new ParserNode("biases");
            c.addChild(w);
            c.addChild(b);
            b.addAttribute("values", Arrays.toString(this.bias[layer]));

            for (int we = 0; we < this.weight[layer].length; we++) {
                w.addAttribute("" + we, Arrays.toString(weight[layer][we]));
            }
        }
        p.close();
    }
	
	//this method is created to create network with weights and biases loaded from file
    public static Network loadNetwork(String fileName) throws Exception {

        Parser p = new Parser();

            p.load(fileName);
            String sizes = p.getValue(new String[] { "Network" }, "sizes");
            int[] si = ParserTools.parseIntArray(sizes);
            Network net = new Network(si);

            for (int i = 1; i < net.NETWORK_SIZE; i++) {
                String biases = p.getValue(new String[] { "Network", "Layers", new String(i + ""), "biases" }, "values");
                double[] bias = ParserTools.parseDoubleArray(biases);
                net.bias[i] = bias;

                for(int n = 0; n < net.NETWORK_LAYER_SIZES[i]; n++){

                    String current = p.getValue(new String[] { "Network", "Layers", new String(i + ""), "weights" }, ""+n);
                    double[] val = ParserTools.parseDoubleArray(current);

                    net.weight[i][n] = val;
                }
            }
            p.close();
            return net;
    }
	
}