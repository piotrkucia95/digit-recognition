package Controller;

import java.io.File;

public class Mnist {

    public static void main(String[] args) throws Exception {
        
    	//network was trained and the final weights and biases were saved in test.txt file
    	
    	Network network = new Network(784, 256, 100, 10);
        NetworkTrainSet set = createTrainSet(0, 30000);
        trainData(network, set, 100, 50, 100, "res/test.txt");
    	
    	//network load values from file text.txt
    	//Network network = Network.loadNetwork("res/test.txt");
    	
    	//network testing
    	NetworkTrainSet testSet = createTrainSet(5000,9999);
        testTrainSet(network, testSet, 10);
    }

    //this method loads 'inputs' and 'outputs' from mnist data base to train the network
    public static NetworkTrainSet createTrainSet(int start, int end) {

    	NetworkTrainSet set = new NetworkTrainSet(28 * 28, 10);

        try {
            String path = new File("").getAbsolutePath();

            MnistImageFile m = new MnistImageFile(path + "/res/trainImage.idx3-ubyte", "rw");
            MnistLabelFile l = new MnistLabelFile(path + "/res/trainLabel.idx1-ubyte", "rw");

            for(int i = start; i <= end; i++) {
                if(i % 100 ==  0){
                    System.out.println("prepared: " + i);
                }

                double[] input = new double[28 * 28];
                double[] output = new double[10];

                output[l.readLabel()] = 1d;
                
                
                for(int j = 0; j < 28*28; j++){
                    input[j] = (double)m.read() / (double)256;
                }

                
                set.addData(input, output);
                m.next();
                l.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    
    //this method trains network with the values of inputs and outputs loaded from mnist database
    public static void trainData(Network net, NetworkTrainSet set, int epochs, int loops, int batch_size, String output_file) throws Exception  {
    	for(int e = 0; e < epochs;e++) {
          
        	net.train(set, loops, batch_size);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>   "+ e+ "   <<<<<<<<<<<<<<<<<<<<<<<<<<");
			net.saveNetwork(output_file);
			
        }
    }
    
    //this method counts the proportion of trainsets learned correctly to overall amount of trainsets
    public static void testTrainSet(Network net, NetworkTrainSet set, int printSteps) {
        int correct = 0;
        for(int i = 0; i < set.size(); i++) {

            double highest = NetworkTools.indexOfHighestValue(net.calculate(set.getInput(i)));
            double actualHighest = NetworkTools.indexOfHighestValue(set.getOutput(i));
            if(highest == actualHighest) {

                correct ++ ;
            }
            if(i % printSteps == 0) {
                System.out.println(i + ": " + (double)correct / (double) (i + 1));
            }
        }
        System.out.println("Testing finished, RESULT: " + correct + " / " + set.size()+ "  -> " + (double)correct / (double)set.size() +" %");
    }
}
