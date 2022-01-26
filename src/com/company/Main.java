package com.company;

import java.io.IOException;
import java.util.Calendar;


public class Main {

    public static void main  (String[] args) throws IOException {
        String filePath = "C:\\Users\\aiur-\\Documents\\Java\\";
        String fileName = "ImmoWelt";
        String extension = ".csv";
        String dateOfParsing;
        Calendar cal = Calendar.getInstance();
        dateOfParsing = String.valueOf(cal.get(Calendar.DATE))+ "-" + String.valueOf(cal.get(Calendar.MONTH));

        ImmoWeltParser ip = new ImmoWeltParser();
        ip.ImmoWeltRunner();
        ip.toFile(new StringBuilder().append(filePath).append(dateOfParsing).append(fileName).append(extension).toString());

        // write your code here
    }
}
