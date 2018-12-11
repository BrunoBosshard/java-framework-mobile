package com.pepgo.page_objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.touch.offset.ElementOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.appium.java_client.touch.TapOptions.tapOptions;

public class CalculatorPageObject extends BasePageObject {

    private MobileElement one = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_1");
    private MobileElement two = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_2");
    private MobileElement three = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_3");
    private MobileElement four = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_4");
    private MobileElement five = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_5");
    private MobileElement six = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_6");
    private MobileElement seven = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_7");
    private MobileElement eight = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_8");
    private MobileElement nine = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_9");
    private MobileElement zero = (MobileElement) driver.findElementById("com.google.android.calculator:id/digit_0");
    private MobileElement addButton = (MobileElement) driver.findElementByAccessibilityId("plus");
    private MobileElement subtractButton = (MobileElement) driver.findElementByAccessibilityId("minus");
    private MobileElement equalsButton = (MobileElement) driver.findElementByAccessibilityId("equals");
    private MobileElement result = (MobileElement) driver.findElementById("com.google.android.calculator:id/result");

    private final Map<Character, MobileElement> NUMBERS = Collections.unmodifiableMap(
            new HashMap<Character, MobileElement>() {{
                put('1', one);
                put('2', two);
                put('3', three);
                put('4', four);
                put('5', five);
                put('6', six);
                put('7', seven);
                put('8', eight);
                put('9', nine);
                put('0', zero);
            }});

    public CalculatorPageObject enterNumber(String number) {
        for (Character digit : number.toCharArray()) {
            touchAction.tap(tapOptions().withElement(ElementOption.element(NUMBERS.get(digit)))).perform();
        }

        return this;
    }

    public CalculatorPageObject add() {
        touchAction.tap(tapOptions().withElement(ElementOption.element(addButton))).perform();

        return this;
    }

    public CalculatorPageObject subtract() {
        touchAction.tap(tapOptions().withElement(ElementOption.element(subtractButton))).perform();

        return this;
    }

    public String equals() {
        touchAction.tap(tapOptions().withElement(ElementOption.element(equalsButton))).perform();

        return result.getText();
    }
}