package com.pepgo.extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
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
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String fileName = "Report_" + sdf.format(today) + ".html";
        String reportDirectory = System.getProperty("reportDirectory");
        System.out.println("Report Directory = " + reportDirectory);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportDirectory + File.separator  + fileName);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setReportName(fileName);
        htmlReporter.loadXMLConfig(new File("extent-config.xml"));

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}