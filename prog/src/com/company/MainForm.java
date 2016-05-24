package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainForm extends JFrame {
    private static String filePath = "C:\\Users\\ROMAN\\Desktop\\input.csv";
    private static int rowAmount;
    private static int columnAmount;
    public static int studyingRowAmount;
    public static int checkingRowAmount;
    public static int examiningRowAmount;
    private static double allowedAmountOfModels = 6;
    public static KolmogorovGaborLinear kolmGab = new KolmogorovGaborLinear();
    public static double[][] data;
    public static int lastLevel;
    public static LinearModel[][] models;
    static double[][] studyingData;
    static double[][] checkingData;
    static double[][] examiningData;

    private JPanel rootPanel = new JPanel();
    private JButton buttonBrowse = new JButton("Огляд");
    private JButton buttonOpenFile = new JButton("Відкрити CSV-файл");
    private JButton buttonCreateTheModel = new JButton("Створити модель");
    private JButton buttonSaveReport = new JButton("Зберегти звіт");
    private JButton buttonSaveCSV = new JButton("Зберегти CSV-файл");
    private JButton buttonCountY = new JButton("Порахувати");
    private JFileChooser fileChooser;
    private JTextField textFilePath = new JTextField();
    private JTextField textStudyingRowAmount = new JTextField();
    private JTextField textCheckingRowAmount = new JTextField();
    private JTextField textExaminingRowAmount = new JTextField();
    private JTextField textX1 = new JTextField();
    private JTextField textX2 = new JTextField();
    private JTextField textX3 = new JTextField();
    private JTextField textX4 = new JTextField();
    private JLabel labelFilePath = new JLabel("Шлях: ");
    private JLabel labelRowAmount = new JLabel();
    private JLabel labelColumnAmount = new JLabel();
    private JLabel labelCheckingRowAmount = new JLabel("Об'єм навчаючої послідовності: ");
    private JLabel labelStudyingRowAmount = new JLabel("Об'єм перевіряючої послідовності: ");
    private JLabel labelExaminingRowAmount = new JLabel("Об'єм екзаменаційної послідовності: ");
    private JLabel labelStatus = new JLabel();
    private JLabel labelX1 = new JLabel("x1");
    private JLabel labelX2 = new JLabel("x2");
    private JLabel labelX3 = new JLabel("x3");
    private JLabel labelX4 = new JLabel("x4");
    private JLabel labelY = new JLabel();

    public MainForm() {
        super("МГУА");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(650, 400);
        setLayout(null);

        add(labelFilePath);
        labelFilePath.setBounds(20, 20, 50, 15);

        add(textFilePath);
        textFilePath.setBounds(80, 15, 250, 25);
        textFilePath.setEnabled(false);

        add(buttonBrowse);
        buttonBrowse.setBounds(350, 15, 80, 25);
        buttonBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFilePath();
            }
        });

        add(buttonOpenFile);
        buttonOpenFile.setBounds(450, 15, 150, 25);
        buttonOpenFile.setEnabled(false);
        buttonOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileByPath(textFilePath.getText());
            }
        });

        add(labelRowAmount);
        labelRowAmount.setBounds(20, 50, 150, 15);
        labelRowAmount.setVisible(false);

        add(labelColumnAmount);
        labelColumnAmount.setBounds(220, 50, 150, 15);
        labelColumnAmount.setVisible(false);

        add(labelCheckingRowAmount);
        labelCheckingRowAmount.setBounds(20, 85, 250, 15);
        labelCheckingRowAmount.setVisible(false);

        add(textCheckingRowAmount);
        textCheckingRowAmount.setBounds(280, 80, 50, 25);
        textCheckingRowAmount.setVisible(false);

        add(labelStudyingRowAmount);
        labelStudyingRowAmount.setBounds(20, 120, 250, 15);
        labelStudyingRowAmount.setVisible(false);

        add(textStudyingRowAmount);
        textStudyingRowAmount.setBounds(280, 115, 50, 25);
        textStudyingRowAmount.setVisible(false);

        add(labelExaminingRowAmount);
        labelExaminingRowAmount.setBounds(20, 155, 250, 15);
        labelExaminingRowAmount.setVisible(false);

        add(textExaminingRowAmount);
        textExaminingRowAmount.setBounds(280, 150, 50, 25);
        textExaminingRowAmount.setVisible(false);

        add(buttonCreateTheModel);
        buttonCreateTheModel.setBounds(20, 180, 200, 25);
        buttonCreateTheModel.setVisible(false);
        buttonCreateTheModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryTheWork();
            }
        });

        add(labelStatus);
        labelStatus.setBounds(20, 210, 560, 25);
        labelStatus.setVisible(false);

        add(labelX1);
        labelX1.setBounds(20, 250, 50, 15);
        labelX1.setVisible(false);

        add(textX1);
        textX1.setBounds(50, 245, 50, 25);
        textX1.setVisible(false);

        add(labelX2);
        labelX2.setBounds(110, 250, 50, 15);
        labelX2.setVisible(false);

        add(textX2);
        textX2.setBounds(140, 245, 50, 25);
        textX2.setVisible(false);

        add(labelX3);
        labelX3.setBounds(200, 250, 50, 15);
        labelX3.setVisible(false);

        add(textX3);
        textX3.setBounds(230, 245, 50, 25);
        textX3.setVisible(false);

        add(labelX4);
        labelX4.setBounds(290, 250, 50, 15);
        labelX4.setVisible(false);

        add(textX4);
        textX4.setBounds(320, 245, 50, 25);
        textX4.setVisible(false);

        add(buttonCountY);
        buttonCountY.setBounds(390, 245, 120, 25);
        buttonCountY.setVisible(false);
        buttonCountY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countOneY();
            }
        });
        buttonCountY.setVisible(false);

        add(labelY);
        labelY.setBounds(20, 280, 200, 15);

        add(buttonSaveReport);
        buttonSaveReport.setBounds(20, 320, 150, 25);
        buttonSaveReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTXTFile();
            }
        });
        buttonSaveReport.setVisible(false);

        add(buttonSaveCSV);
        buttonSaveCSV.setBounds(200, 320, 180, 25);
        buttonSaveCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCSVFile(countAllY());
            }
        });
        buttonSaveCSV.setVisible(false);
    }

    private void setFilePath() {
        fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(MainForm.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textFilePath.setText("" + file.getPath());
            buttonOpenFile.setEnabled(true);
        }
        labelRowAmount.setVisible(false);
        labelColumnAmount.setVisible(false);
        labelStudyingRowAmount.setVisible(false);
        textStudyingRowAmount.setVisible(false);
        labelCheckingRowAmount.setVisible(false);
        textCheckingRowAmount.setVisible(false);
        labelExaminingRowAmount.setVisible(false);
        textExaminingRowAmount.setVisible(false);
        buttonCreateTheModel.setVisible(false);
        labelStatus.setVisible(false);
    }

    private void openFileByPath(String path) {
        // зчитування даних з CSV-файлу
        CSVReader reader = new CSVReader(filePath);
        data = reader.readFile();
        rowAmount = data.length;
        columnAmount = data[0].length;
        labelRowAmount.setText("Кількість рядків: " + rowAmount);
        labelColumnAmount.setText("Кількість стовпчиків: " + columnAmount);
        buttonOpenFile.setEnabled(false);
        labelRowAmount.setVisible(true);
        labelColumnAmount.setVisible(true);
        labelStudyingRowAmount.setVisible(true);
        textStudyingRowAmount.setVisible(true);
        labelCheckingRowAmount.setVisible(true);
        textCheckingRowAmount.setVisible(true);
        labelExaminingRowAmount.setVisible(true);
        textExaminingRowAmount.setVisible(true);
        buttonCreateTheModel.setVisible(true);
    }

    private boolean fillParameters() {
        try {
            studyingRowAmount = Integer.parseInt(textStudyingRowAmount.getText());
            checkingRowAmount = Integer.parseInt(textCheckingRowAmount.getText());
            examiningRowAmount = Integer.parseInt(textExaminingRowAmount.getText());
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private boolean checkSum() {
        if (studyingRowAmount + checkingRowAmount + examiningRowAmount == rowAmount) {
            return true;
        } else {
            return false;
        }
    }

    private void tryTheWork() {
        labelStatus.setText("");
        if (!fillParameters()) {
            labelStatus.setText("Помилка: некоректні дані");
        } else if (!checkSum()) {
            labelStatus.setText("Помилка: сумарна розмірність послідовностей не відповідає загальній кількості рядків");
        } else {
            doTheMajorWork();
            labelStatus.setVisible(true);
            labelStatus.setText("Модель створено успішно! Останній рівень: " + lastLevel);
            labelX1.setVisible(true);
            textX1.setVisible(true);
            labelX2.setVisible(true);
            textX2.setVisible(true);
            labelX3.setVisible(true);
            textX3.setVisible(true);
            labelX4.setVisible(true);
            textX4.setVisible(true);
            buttonCountY.setVisible(true);
            buttonSaveReport.setVisible(true);
            buttonSaveCSV.setVisible(true);
        }
    }

    public void countOneY() {
        labelY.setText("");
        double x1 = Double.parseDouble(textX1.getText());
        double x2 = Double.parseDouble(textX2.getText());
        double x3 = Double.parseDouble(textX3.getText());
        double x4 = Double.parseDouble(textX4.getText());
        double[] array = {x1, x2, x3, x4};
        for (int k = 0; k < 6; k++) {
            models[1][k].tempResult = models[1][k].countResult(array[models[1][k].x1Index], array[models[1][k].x2Index]);
        }
        for (int j = 2; j <= lastLevel; j++) {
            for (int k = 0; k < 6; k++) {
                models[j][k].tempResult = models[j][k].countResult(models[j-1][models[j][k].x1Index].tempResult,
                        models[j-1][models[j][k].x1Index].tempResult);
            }
        }
        labelY.setText("y = " + models[lastLevel][0].tempResult);
    }

    public void saveTXTFile() {
        int returnVal = fileChooser.showSaveDialog(MainForm.this);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println("Список проміжних моделей на всіх рівнях");
            writer.println("");
            for (int i = 1; i <= lastLevel; i++) {
                writer.println("" + i + " рівень: ");
                for (int j = 0; j < 6; j++) {
                    writer.println("Модель 1: " + models[i][j].makeStartModel());
                    if (i == lastLevel) break;
                }
                writer.println("");
            }
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double[] countAllY() {
        double[] array = new double[examiningRowAmount];
        for (int i = 0; i < examiningRowAmount; i++) {
            for (int k = 0; k < 6; k++) {
                models[1][k].tempResult = models[1][k].countResult(examiningData[i][models[1][k].x1Index],
                        examiningData[i][models[1][k].x2Index]);
            }
            for (int j = 2; j <= lastLevel; j++) {
                for (int k = 0; k < 6; k++) {
                    models[j][k].tempResult = models[j][k].countResult(models[j-1][models[j][k].x1Index].tempResult,
                            models[j-1][models[j][k].x1Index].tempResult);
                }
            }
            array[i] = models[lastLevel][0].tempResult;
        }
        return array;
    }

    public void saveCSVFile(double[] newY) {
        int returnVal = fileChooser.showSaveDialog(MainForm.this);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            for (int i = 0; i < examiningRowAmount; i++) {
                for (int j = 0; j < columnAmount; j++) {
                    writer.print("" + examiningData[i][j] + ";");
                }
                writer.print("" + newY[i]);
                writer.println("");
            }
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void doTheMajorWork () {
        // відділення навчальної матриці
        studyingData = new double[studyingRowAmount][columnAmount];
        checkingData = new double[checkingRowAmount][columnAmount];
        examiningData = new double[examiningRowAmount][columnAmount];
        for (int j = 0; j < columnAmount; j++) {
            for (int i = 0; i < studyingRowAmount; i++) {
                studyingData[i][j] = data[i][j];
            }
            for (int i = 0; i < checkingRowAmount; i++) {
                checkingData[i][j] = data[i + studyingRowAmount][j];
            }
            for (int i = 0; i < examiningRowAmount; i++) {
                examiningData[i][j] = data[i + studyingRowAmount + checkingRowAmount][j];
            }
        }

        // ітерації навчання
        models = new LinearModel[1000][10];
        int[] modelsOnEachLevel = new int[1000];
        modelsOnEachLevel[0] = 4;
        double criterionSumOnPastLevel = 1000000000;
        for (int currentLevel = 0; currentLevel < 300; currentLevel++) {
            if (currentLevel == 0) modelsOnEachLevel[currentLevel] = 4;
            else if (currentLevel == 1) modelsOnEachLevel[currentLevel] = 6;
            else modelsOnEachLevel[currentLevel] = 6;
            int modelsOnNextLevel = 0;
            int tempModelAmount = 0;
            LinearModel[] tempModels = new LinearModel[1000];
            for (int i = 0; i < modelsOnEachLevel[currentLevel]; i++) {
                for (int j = i; j < modelsOnEachLevel[currentLevel]; j++) {
                    if (i != j) {
                        if (currentLevel == 0) {
                            tempModels[tempModelAmount] = kolmGab.makeModel(studyingRowAmount,
                                    extractColumn(data, studyingRowAmount, i),
                                    extractColumn(data, studyingRowAmount, j),
                                    extractColumn(data, studyingRowAmount, 4));
                        } else {
                            tempModels[tempModelAmount] = makeElderModel(models[currentLevel][i], models[currentLevel][j]);
                        }
                        tempModels[tempModelAmount].x1Index = i;
                        tempModels[tempModelAmount].x2Index = j;
                        tempModels[tempModelAmount].level = currentLevel + 1;
                        lastLevel = currentLevel;
                        tempModelAmount++;
                    }
                }
            }

            List<LinearModel> tempList = new ArrayList<LinearModel>();
            for (int i = 0; i < tempModelAmount; i++) {
                if (currentLevel == 0) {
                    tempModels[i].criterion = linearModelCriterion(checkingRowAmount, tempModels[i],
                            extractColumn(data, checkingRowAmount, tempModels[i].x1Index),
                            extractColumn(data, checkingRowAmount, tempModels[i].x2Index), extractColumn(data, checkingRowAmount, 4));
                } else {
                    tempModels[i].criterion = elderLinearModelCriterion(checkingRowAmount, tempModels[i],
                            models[currentLevel][tempModels[i].x1Index].checkingResults,
                            models[currentLevel][tempModels[i].x2Index].checkingResults, extractColumn(data, checkingRowAmount, 4));
                }
                tempList.add(tempModels[i]);
            }
            Collections.sort(tempList);

            double currentCritSum = 0;
            for (int i = 0; i < allowedAmountOfModels; i++) {
                models[currentLevel + 1][i] = tempList.get(i);
                modelsOnNextLevel++;
                currentCritSum += models[currentLevel + 1][i].criterion;
            }
            currentCritSum /= modelsOnNextLevel;
            modelsOnEachLevel[currentLevel + 1] = modelsOnNextLevel;
            System.out.println("Level " + currentLevel + " " + currentCritSum);
            if (currentLevel == 5) {
            //if (currentCritSum > criterionSumOnPastLevel) {
                lastLevel--;
                break;
            }
            criterionSumOnPastLevel = currentCritSum;
        }
    }

    public static double[] extractColumn(double[][] data, int rows, int index) {
        double[] res = new double[rows];
        for (int i = 0; i < rows; i++) {
            res[i] = data[i][index];
        }
        return res;
    }

    public static double linearModelCriterion(int N, LinearModel model, double[] x1, double[] x2, double[] y) {
        double res = 0;
        double[] checkingResults = new double[N];
        for (int i = 0; i < N; i++) {
            checkingResults[i] = model.countResult(x1[i], x2[i]);
            res += Math.pow((y[i] - model.countResult(x1[i], x2[i])), 2);
        }
        model.checkingResults = checkingResults;
        res /= N;
        return res;
    }

    public static double elderLinearModelCriterion(int N, LinearModel model, double[] checkingResults1,
                                                   double checkingResults2[], double y[]) {
        double res = 0;
        double[] checkingResults = new double[N];
        for (int i = 0; i < N; i++) {
            checkingResults[i] = model.countResult(checkingResults1[i], checkingResults2[i]);
            res += Math.pow((y[i] - model.countResult(checkingResults1[i], checkingResults2[i])), 2);
        }
        model.checkingResults = checkingResults;
        res /= N;
        return res;
    }

    public static LinearModel makeElderModel (LinearModel y1, LinearModel y2) {
        return kolmGab.makeModel(studyingRowAmount, y1.studyingResults, y2.studyingResults, extractColumn(data, studyingRowAmount, 4));
    }
}