package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.*;

public class App {
    public static void main(String[] args) {

        String filePath = "model.csv";

        FileReader filereader;
        List<String[]> allData;

        try {
            filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            allData = csvReader.readAll();
        } catch (Exception e) {
            System.out.println("Error reading CSV");
            return;
        }

        int[][] confusion = new int[5][5];
        float CE = 0;

        for(String[] row : allData){

            int y_true = Integer.parseInt(row[0]);

            float[] probs = new float[5];

            for(int i=0;i<5;i++){
                probs[i] = Float.parseFloat(row[i+1]);
            }

            CE += -Math.log(probs[y_true-1]);

            int predicted = 0;
            float max = probs[0];

            for(int i=1;i<5;i++){
                if(probs[i] > max){
                    max = probs[i];
                    predicted = i;
                }
            }

            confusion[predicted][y_true-1]++;
        }

        CE = CE / allData.size();

        System.out.println("CE = "+CE);

        System.out.println("\nConfusion Matrix");

        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.print(confusion[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
