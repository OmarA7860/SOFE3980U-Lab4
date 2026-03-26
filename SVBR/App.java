package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.*;

public class App {
    public static void main(String[] args) {
        String[] files = {"model_1.csv", "model_2.csv", "model_3.csv"};

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

            int TP=0,FP=0,TN=0,FN=0;
            float BCE=0;
            float epsilon=0.000001f;

            for(String[] row:allData){

                int y_true = Integer.parseInt(row[0]);
                float y_pred = Float.parseFloat(row[1]);

                BCE += y_true*Math.log(y_pred+epsilon) + (1-y_true)*Math.log(1-y_pred+epsilon);

                int predicted = y_pred >= 0.5 ? 1:0;

                if(predicted==1 && y_true==1) TP++;
                if(predicted==1 && y_true==0) FP++;
                if(predicted==0 && y_true==0) TN++;
                if(predicted==0 && y_true==1) FN++;
            }

            BCE = -BCE/allData.size();

            float accuracy = (float)(TP+TN)/(TP+TN+FP+FN);
            float precision = TP==0?0:(float)TP/(TP+FP);
            float recall = TP==0?0:(float)TP/(TP+FN);
            float F1 = (precision+recall)==0?0:2*(precision*recall)/(precision+recall);

            System.out.println("Model: "+filePath);
            System.out.println("BCE = "+BCE);

            System.out.println("Confusion Matrix");
            System.out.println("TP="+TP+" FP="+FP);
            System.out.println("FN="+FN+" TN="+TN);

            System.out.println("Accuracy = "+accuracy);
            System.out.println("Precision = "+precision);
            System.out.println("Recall = "+recall);
            System.out.println("F1 = "+F1);

            System.out.println("------------------------------------");
        }
    }
}
