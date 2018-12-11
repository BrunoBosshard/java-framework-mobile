package com.pepgo.tests;

import com.pepgo.AppiumBase;
import com.pepgo.page_objects.CalculatorPageObject;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.appium.java_client.android.Activity;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorIT extends AppiumBase {

    // Data provided from CSV file (using OpenCSV)
    @DataProvider(name = "additionDataProvider")
    public Iterator<Object[]> provider() throws Exception {
        // .withSkipLines(1) skips the first line (headers) in the CSV file
        CSVReader reader = new CSVReaderBuilder(new FileReader("./src/test/resources/AdditionData.csv")).withSkipLines(1).build();
        List<Object[]> myEntries = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            myEntries.add(nextLine);
        }
        reader.close();
        return myEntries.iterator();
    }

// Alternative DataProvider (data in the class)
/*
    @DataProvider
    public Object[][] additionDataProvider() {
        return new Object[][]{
                {"1","1","2"},
                {"1","2","3"},
                {"5","4","9"},
                {"10","21","31"},
                {"101","202","303"}
        };
    }
*/

    @BeforeMethod(alwaysRun = true)
    public void setCorrectActivity() throws Exception {
        String appPackage = "com.google.android.calculator";
        String appActivity = "com.android.calculator2.Calculator";
        getDriver(new Activity(appPackage, appActivity));
    }

    @Test(groups={"smoke","all"})
    public void subtractNumbers() {
        CalculatorPageObject calculatorPageObject = new CalculatorPageObject();

        String result = calculatorPageObject.enterNumber("97")
                .subtract()
                .enterNumber("3")
                .equals();

        assertThat(result).isEqualTo("94");
    }

    @Test(groups={"regression","all"}, dataProvider="additionDataProvider")
    public void addNumbersTogether(final String firstNumber, final String secondNumber, final String expectedResult) {
        CalculatorPageObject calculatorPageObject = new CalculatorPageObject();

        String result = calculatorPageObject.enterNumber(firstNumber)
                .add()
                .enterNumber(secondNumber)
                .equals();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test(groups={"all"})
    public void deliberateFail() {
        CalculatorPageObject calculatorPageObject = new CalculatorPageObject();

        String result = calculatorPageObject.equals();

        // This test is deliberately failing
        assertThat(result).isEqualTo("Fail");
    }
}