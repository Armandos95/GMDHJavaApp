package com.company;

public class LinearModel implements Comparable<LinearModel>{

    public double a0;
    public double a1;
    public double a2;
    public double a3;

    public double criterion;
    public int x1Index;
    public int x2Index;
    public int level;
    public double[] studyingResults;
    public double[] checkingResults;
    public double tempResult;

    public LinearModel(double[] coeffs, double[] studyingResults) {
        this.a0 = coeffs[0];
        this.a1 = coeffs[1];
        this.a2 = coeffs[2];
        this.a3 = coeffs[3];
        this.studyingResults = studyingResults;
    }

    public double countResult(double x1, double x2) {
        return (a0 + a1 * x1 + a2 * x2 + a3 * x1 * x2);
    }

    public int compareTo(LinearModel m) {
        return criterion > m.criterion ? 1 : criterion == m.criterion ? 0 : -1;
    }

    public String makeStartModel() {
        return ("" + a0 + " + " + a1 + "x1 + " + a2 + "x2 + " + a3 + "x1x2");
    }
}