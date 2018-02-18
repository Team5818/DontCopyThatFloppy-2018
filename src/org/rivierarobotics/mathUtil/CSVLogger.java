package org.rivierarobotics.mathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

import edu.wpi.first.wpilibj.DriverStation;

public class CSVLogger {

    PrintWriter writer;
    String[] csvFields;
    ConcurrentLinkedDeque<String> lineBuffer = new ConcurrentLinkedDeque<String>();
    File csvFile;
    
    public CSVLogger(String filename, String... fields) {
        DateFormat form = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        csvFile = new File(filename.substring(0, filename.length() - 4) + "_"+ form.format(date) + ".csv");
        
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer = new PrintWriter(csvFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        csvFields = fields;
        StringBuffer header = new StringBuffer();
        for(String s : fields) {
            if(header.length() > 0) {
                header.append(", ");
            }
            header.append(s);
        }
        writer.println(header.toString());
    }
    
    public String getValueLine(double...values) {
        if(values.length != csvFields.length) {
            throw new IllegalArgumentException("Wrong number of fields");
        }
        StringBuffer line = new StringBuffer();
        for(double d : values) {
            if(line.length() > 0) {
                line.append(", ");
            }
            line.append("" + d);
        }
        return line.toString();
    }
    public void storeValue(double...values) {
        lineBuffer.add(getValueLine(values));
    }
    
    public synchronized void flushToDisk() {
        for(String s : lineBuffer) {
            writer.println(s);
        }
    }
    
    public void writeImmediately(double...values) {
        writer.println(getValueLine(values));
    }
    
    public void close() {
        writer.close();
    }
}
