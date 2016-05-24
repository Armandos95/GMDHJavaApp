package com.company;

public class KolmogorovGaborLinear {

    public LinearModel makeModel(int rowAmount, double[] x1, double[] x2, double[] y) {

        double[][] A = new double[rowAmount][4];
        double[] B = new double[rowAmount];

        for (int i = 0; i < rowAmount; i++) {
            A[i][0] = 1;
            A[i][1] = x1[i];
            A[i][2] = x2[i];
            A[i][3] = x1[i] * x2[i];
            B[i] = y[i];
        }

        double[][] A_final = new double[4][4];
        double[] B_final = new double[4];

        for (int i = 0; i < 4; i++) {
            A_final[i][0] = 0;
            A_final[i][1] = 0;
            A_final[i][2] = 0;
            A_final[i][3] = 0;
            B_final[i] = 0;
            for (int j = 0; j < rowAmount; j++) {
                A_final[i][0] += A[j][0] * A[j][i];
                A_final[i][1] += A[j][1] * A[j][i];
                A_final[i][2] += A[j][2] * A[j][i];
                A_final[i][3] += A[j][3] * A[j][i];
                B_final[i] += B[j] * A[j][i];
            }
        }

        double[][] wholeMatrix = new double[4][5];
        double[] answers = new double[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                wholeMatrix[i][j] = (double) A_final[i][j];
            }
            wholeMatrix[i][4] = (double) B_final[i];
        }
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                double coef = wholeMatrix[j][i] / wholeMatrix[i][i];
                for (int k = 0; k < 5; k++) {
                    wholeMatrix[j][k] -= coef * wholeMatrix[i][k];
                }
            }
        }
        answers[3] = wholeMatrix[3][4] / wholeMatrix[3][3];
        answers[2] = (wholeMatrix[2][4] - answers[3] * wholeMatrix[2][3]) / wholeMatrix[2][2];
        answers[1] = (wholeMatrix[1][4] - answers[3] * wholeMatrix[1][3] - answers[2] * wholeMatrix[1][2]) / wholeMatrix[1][1];
        answers[0] = (wholeMatrix[0][4] - answers[3] * wholeMatrix[0][3] - answers[2] * wholeMatrix[0][2]
                - answers[1] * wholeMatrix[0][1]) / wholeMatrix [0][0];

        double[] studyingResults = new double[MainForm.studyingRowAmount];
        for (int i = 0; i < MainForm.studyingRowAmount; i++) {
            studyingResults[i] = answers[0] + answers[1] * x1[i] + answers[2] * x2[i] + answers[3] * x1[i] * x2[i];
        }

        return new LinearModel(answers, studyingResults);
    }
}