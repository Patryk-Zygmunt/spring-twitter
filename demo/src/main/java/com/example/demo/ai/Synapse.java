package com.example.demo.ai;

import java.util.function.Function;

public class Synapse {
    private double weight;
    private double prevWeight;
    private Function<Double,Double> outputFun;
    private Neuron   from;
    private Neuron   to;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrevWeight() {
        return prevWeight;
    }

    public void setPrevWeight(double prevWeight) {
        this.prevWeight = prevWeight;
    }

    public Function<Double, Double> getOutputFun() {
        return outputFun;
    }

    public void setOutputFun(Function<Double, Double> outputFun) {
        this.outputFun = outputFun;
    }
}
