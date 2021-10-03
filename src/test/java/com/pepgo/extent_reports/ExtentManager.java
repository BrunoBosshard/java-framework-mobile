package com.pepgo.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent reports instance
    public static ExtentReports createInstance() {
        try {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String fileName = "Report_" + sdf.format(today) + ".html";
            String reportDirectory = System.getProperty("reportDirectory");
            System.out.println("Report Directory = " + reportDirectory);
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDirectory + File.separator  + fileName);
            sparkReporter.config().setDocumentTitle(fileName);
            sparkReporter.config().setReportName(fileName);
            sparkReporter.loadXMLConfig(new File("spark-config.xml"));

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        } catch (Exception ex) {
            System.err.println("Unable to create ExtentReports: " + ex);
        }

        return extent;
    }
}
