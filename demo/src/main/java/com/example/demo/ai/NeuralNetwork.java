package com.example.demo.ai;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NeuralNetwork {
  private final double treshold=2D;
  private final double coeficient=2D;
  
  private double calculateInput(List<Synapse> synapses){
      return  synapses.stream().mapToDouble(s->s.getWeight()).reduce(0D,(sum,d)->sum+d);
  }
    
  private double calculateOutputStep(double input){
      return input>treshold ? 1 : 0;   
  }
  
  public double CalculateOutputSigmoid(double input)
    {
        return (1 / (1 + Math.exp(-input * coeficient)));
    }
    
 public double CalculateOutputRectifier(double input)
    {
        return Math.max(0, input);
    }
    
    
    
}
