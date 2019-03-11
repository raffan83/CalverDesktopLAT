package it.calverDesktopLAT.bo;

import java.util.Arrays;

public class Statistic {
    static double[] data;
    static int size;   

    public Statistic(double[] data) {
        this.data = data;
        size = data.length;
    }   

    static double getMean() {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    static double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

     public static double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public double median() {
       Arrays.sort(data);
       if (data.length % 2 == 0)
          return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
       return data[data.length / 2];
    }
}
