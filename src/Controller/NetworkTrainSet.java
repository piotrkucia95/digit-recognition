package Controller;

import java.util.ArrayList;
import java.util.Arrays;

public class NetworkTrainSet {

	public final int INPUT_SIZE;
	public final int OUTPUT_SIZE;
	private ArrayList<double[][]> data = new ArrayList<>();
	
	public NetworkTrainSet(int INPUT_SIZE, int OUTPUT_SIZE) {
		this.INPUT_SIZE = INPUT_SIZE;
		this.OUTPUT_SIZE = OUTPUT_SIZE;
	}
	
	//adding input and target arrays to ArrayList
	public void addData(double[] in, double[] expected) {
		
		if((in.length != INPUT_SIZE) || (expected.length != OUTPUT_SIZE)) return;
		data.add(new double[][] {in, expected});
		
	}
	
	//this method returns "size" number of random TrainSets from a larger bunch of TrainSets
	public NetworkTrainSet extractBatch(int size) {
		
		if((size > 0) && (size <= this.size())) {
			NetworkTrainSet set = new NetworkTrainSet(INPUT_SIZE, OUTPUT_SIZE);
			Integer[] ids = NetworkTools.randomValues(0,  this.size()-1, size);
			for(Integer i:ids) {
				set.addData(this.getInput(i), this.getOutput(i));
			}
			return set;
		} else return this;
	}
	
	//toString method shadowing
	public String toString() {
        String s = "TrainSet ["+INPUT_SIZE+ " ; "+OUTPUT_SIZE+"]\n";
        int index = 0;
        for(double[][] r:data) {
            s += index +":   "+Arrays.toString(r[0]) +"  >-||-<  "+Arrays.toString(r[1]) +"\n";
            index++;
        }
        return s;
    }
	
	public int size() {
        return data.size();
    }
	
	public double[] getInput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[0];
        else return null;
    }
	
	public double[] getOutput(int index) {
        if(index >= 0 && index < size())
            return data.get(index)[1];
        else return null;
    }
	
	public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }
	
	public int getOUTPUT_SIZE() {
        return OUTPUT_SIZE;
    }
	
	public static void main(String[] args) {
        
		NetworkTrainSet set = new NetworkTrainSet(3,2);

        for(int i = 0; i < 8; i++) {
            double[] a = new double[3];
            double[] b = new double[2];
            for(int k = 0; k < 3; k++) {
                a[k] = (double)((int)(Math.random() * 10)) / (double)10;
                if(k < 2) {
                    b[k] = (double)((int)(Math.random() * 10)) / (double)10;
                }
            }
            set.addData(a,b);
        }

        System.out.println(set);
        System.out.println(set.extractBatch(3));
    }
	
}