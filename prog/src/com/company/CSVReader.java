package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    private String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    public double[][] readFile() {
        BufferedReader br = null;
        String line = "";
        String separator = ";";
        double[][] array = null;
        ArrayList<String[]> wholeStringArray = new ArrayList<String[]>();

        try {
            br = new BufferedReader(new FileReader(filePath));
            int rowAmount = 0;
            int columnAmount;
            while ((line = br.readLine()) != null) {
                String[] singleRow = line.split(separator);
                wholeStringArray.add(singleRow);
                rowAmount++;
            }
            columnAmount = wholeStringArray.get(0).length;
            array = new double[rowAmount][columnAmount];
            int iteration = 0;
            for (String[] i : wholeStringArray) {
                for (int j = 0; j < columnAmount; j++) {
                    array[iteration][j] = Double.parseDouble(i[j]);
                }
                iteration++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return array;
    }
}
