package com.pepgo.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlaywrightIT {

    // Simple headless test
    @Test(groups={"playwright","all"})
    public void playwrightExampleBasic() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headless mode
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://playwright.dev");
            System.out.println("Page title is: " + page.title());
            Assert.assertNotEquals(page.title(),"");
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handles a new page that gets opened up by a link with a target="_blank" attribute
    @Test(groups={"playwright","all"})
    public void playwrightExampleHandleNewPage() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headless mode
            Browser browser = playwright.chromium().launch();
            Page pageOne = browser.newPage();
            pageOne.navigate("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_links_target");
            System.out.println("Page 1 title is : " + pageOne.title());
            // Get new page after clicking on button with a target="_blank" link (which creates a new page)
            Page pageTwo = pageOne.waitForPopup(() -> {
                pageOne.frame("iframeResult").click("text=Visit W3Schools!");
            });
            System.out.println("Page 2 title is : " + pageTwo.title());
            Assert.assertNotEquals(pageOne.title(),pageTwo.title());
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Saves a page as a PDF file (works with headless Chromium only)
    @Test(groups={"playwright","all"})
    public void playwrightExamplePDF() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headless mode
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://playwright.dev");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            // PDF generation only works in headless Chromium
            Path path = Paths.get(System.getProperty("playwrightPDFdirectory") + "/Playwright.pdf");
            page.pdf(new Page.PdfOptions().setFormat("A4").setPath(path));
            // Check that the PDF file exists
            Assert.assertTrue(path.toFile().exists());
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creates a full page screenshot and an element screenshot
    @Test(groups={"playwright","all"})
    public void playwrightExampleScreenshots() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headless mode
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://playwright.dev/");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            // Full page screenshot
            Path path = Paths.get(System.getProperty("playwrightScreenshotDirectory") + "/FullPageScreenshot.png");
            page.screenshot(new Page.ScreenshotOptions().setPath(path));
            // Check that the screenshot file exists
            Assert.assertTrue(path.toFile().exists());
            ElementHandle elementHandle = page.querySelector("//input[@aria-label='Search']");
            // Screenshot of the search feature (Element Screenshot)
            path = Paths.get(System.getProperty("playwrightScreenshotDirectory") + "/ElementScreenshot.png");
            elementHandle.screenshot(new ElementHandle.ScreenshotOptions().setPath(path));
            // Check that the screenshot file exists
            Assert.assertTrue(path.toFile().exists());
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Stores a trace in file "Trace.zip"
    // View "trace.zip" from command-line with:
    // mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace playwrightExamplesOutput/trace/Trace.zip"
    @Test(groups={"playwright","all"})
    public void playwrightExampleTraceRecording() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headless mode
            Browser browser = playwright.chromium().launch();
            BrowserContext context = browser.newContext();
            // Start tracing before creating / navigating a page
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
            Page page = context.newPage();
            page.navigate("https://playwright.dev");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            // Stop tracing and export it into a zip archive
            Path path = Paths.get(System.getProperty("playwrightTraceDirectory") + "/Trace.zip");
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(path));
            // Check that the trace file exists
            Assert.assertTrue(path.toFile().exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Videos have generated unique names. They get saved upon context closure, so "browser.close()" is essential!
    @Test(groups={"playwright","all"})
    public void Video() {
        try (Playwright playwright = Playwright.create()) {
            // Launches browser in headed mode and runs in slow motion
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get(System.getProperty("playwrightVideoDirectory"))));
            Page page = context.newPage();
            page.navigate("https://playwright.dev/");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            Assert.assertNotEquals(page.title(),"");
            page.navigate("https://microsoft.com/");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            Assert.assertNotEquals(page.title(),"");
            page.navigate("https://aws.amazon.com/");
            page.waitForLoadState();
            System.out.println("Page title is: " + page.title());
            Assert.assertNotEquals(page.title(),"");
            Thread.sleep(3000);
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}