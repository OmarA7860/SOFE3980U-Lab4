package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.*;

public class App {
    public static void main(String[] args) {

        String[] files = {"model_1.csv","model_2.csv","model_3.csv"};

        for(String filePath:files){

            FileReader filereader;
            List<String[]> allData;

            try{
                filereader = new FileReader(filePath);
                CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
                allData = csvReader.readAll();
            } catch(Exception e){
                System.out.println("Error reading CSV");
                return;
            }

            float MSE = 0;
            float MAE = 0;
            float MARE = 0;
            float epsilon = 0.0000001f;

            for(String[] row:allData){

                float y_true = Float.parseFloat(row[0]);
                float y_pred = Float.parseFloat(row[1]);

                float error = y_true - y_pred;

                MSE += error*error;
                MAE += Math.abs(error);
                MARE += Math.abs(error)/(Math.abs(y_true)+epsilon);
            }

            MSE = MSE/allData.size();
            MAE = MAE/allData.size();
            MARE = MARE/allData.size();

            System.out.println("for "+filePath);
            System.out.println("MSE = "+MSE);
            System.out.println("MAE = "+MAE);
            System.out.println("MARE = "+MARE);
            System.out.println("--------------------------------");
        }
    }
}