package org.rivierarobotics.util;

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

    private PrintWriter writer;
    private String[] csvFields;
    private ConcurrentLinkedDeque<String> lineBuffer = new ConcurrentLinkedDeque<String>();
    private File csvFile;

    public CSVLogger(String rootDir, String... fields) {
        DateFormat form = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        csvFile = new File(rootDir + "_" + form.format(date) + ".csv");

        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            String s = e.getMessage();
            DriverStation.reportError(s, false);
        }
        try {
            writer = new PrintWriter(csvFile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        csvFields = fields;
        StringBuffer header = new StringBuffer();
        for (String s : fields) {
            if (header.length() > 0) {
                header.append(", ");
            }
            header.append(s);
        }
        if (writer != null) {
            writer.println(header.toString());
            writer.flush();
        }
    }

    public String getValueLine(double... values) {
        if (values.length != csvFields.length) {
            throw new IllegalArgumentException("Wrong number of fields");
        }
        StringBuffer line = new StringBuffer();
        for (double d : values) {
            if (line.length() > 0) {
                line.append(", ");
            }
            line.append(d);
        }
        return line.toString();
    }

    public synchronized void storeValue(double... values) {
        lineBuffer.add(getValueLine(values));
    }

    public synchronized void flushToDisk() {
        if (writer != null) {
            for (String s : lineBuffer) {
                writer.println(s);
                writer.flush();
            }
        }
    }

    public void writeImmediately(double... values) {
        if (writer != null) {
            writer.println(getValueLine(values));
            writer.flush();
        }
    }

    public void close() {
        writer.close();
    }
}
