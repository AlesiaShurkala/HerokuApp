import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HerokuAppTests {
    WebDriver driver;
    public final static String BASE_URL = "http://the-internet.herokuapp.com";

    @BeforeMethod
    public void startActions() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }


    @Test
    public void addRemoveElements() {
        WebElement addRemoveElementsSection = driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]"));
        addRemoveElementsSection.click();
        WebElement findAddElementButton = driver.findElement(By.xpath("//button[contains(text(),'Add Element')]"));
        findAddElementButton.click();
        WebElement findDeleteButtonOne = driver.findElement(By.xpath("//button[contains(text(),'Delete')]"));
        findAddElementButton.click();
        WebElement findDeleteButtonTwo = driver.findElement(By.xpath("//body/div[2]/div[1]/div[1]/div[1]/button[2]"));
        findDeleteButtonTwo.click();
        int count = driver.findElements(By.className("added-manually")).size();
        Assert.assertEquals(count, 1);
    }

    @Test
    public void checkCheckboxes() {
        WebElement openCheckboxesSection = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) ul:nth-child(4) li:nth-child(6) > a:nth-child(1)"));
        openCheckboxesSection.click();
        WebElement checkCheckboxOne = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) div.example:nth-child(1) form:nth-child(2) > input:nth-child(1)"));
        Assert.assertTrue(!checkCheckboxOne.isSelected());
        checkCheckboxOne.click();
        Assert.assertTrue(checkCheckboxOne.isSelected());
        WebElement checkCheckboxTwo = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) div.example:nth-child(1) form:nth-child(2) > input:nth-child(3)"));
        Assert.assertTrue(checkCheckboxTwo.isSelected());
        checkCheckboxTwo.click();
        Assert.assertTrue(!checkCheckboxTwo.isSelected());
    }

    @Test
    public void checkDropdown() {
        WebElement openDropdownSection = driver.findElement(By.linkText("Dropdown"));
        openDropdownSection.click();
        WebElement element = driver.findElement(By.xpath("//select[@id='dropdown']"));
        element.click();
        Select select = new Select(element);
        List<WebElement> allOptions = select.getOptions();
        Assert.assertTrue(true, "Please select an option");
        WebElement optionOne = driver.findElement(By.xpath("//option[contains(text(),'Option 1')]"));
        optionOne.click();
        Assert.assertTrue(optionOne.isSelected());
        element.click();
        WebElement optionTwo = driver.findElement(By.xpath("//option[contains(text(),'Option 2')]"));
        optionTwo.click();
        Assert.assertTrue(optionTwo.isSelected());
    }

    @Test
    public void checkInputs() {
        WebElement openInputSection = driver.findElement(By.xpath("//a[contains(text(),'Inputs')]"));
        openInputSection.click();
        WebElement inputField = driver.findElement(By.tagName("input"));
        inputField.click();
        inputField.sendKeys("12345");
        Assert.assertTrue(true, "12345");
        inputField.clear();
        inputField.sendKeys("-145.555e");
        Assert.assertTrue(true, "-145.555e");
        inputField.clear();
    }

    @Test
    public void checkSortableDataTables() {
        WebElement openIDataTablesSection = driver.findElement(By.xpath("//a[contains(text(),'Sortable Data Tables')]"));
        openIDataTablesSection.click();
        WebElement tableElementOne = driver.findElement(By.xpath("//table[@id = 'table1']/tbody/tr[1]/td[1]"));
        Assert.assertEquals(tableElementOne.getText(),"Smith");
        WebElement tableElementTwo = driver.findElement(By.xpath("//table[@id = 'table1']/tbody/tr[2]/td[3]"));
        Assert.assertEquals(tableElementTwo.getText(),"fbach@yahoo.com");
        WebElement tableElementThree = driver.findElement(By.xpath("//table[@id = 'table1']/tbody/tr[3]/td[4]"));
        Assert.assertEquals(tableElementThree.getText(),"$100.00");
    }

    @Test
    public void checkTypos() {
        WebElement openTyposSection = driver.findElement(By.linkText("Typos"));
        openTyposSection.click();
        WebElement getStringOne = driver.findElement(By.xpath("//p[contains(text(),'This example demonstrates a typo being introduced.')]"));
        WebElement getStringTwo = driver.findElement(By.xpath("//p[contains(text(),\"Sometimes you'll see a typo, other times you won't\")]"));
        Assert.assertEquals(getStringOne.getText(), "This example demonstrates a typo being introduced. It does it randomly on each page load.");
        Assert.assertEquals(getStringTwo.getText(), "Sometimes you'll see a typo, other times you won't.");
    }

    @Test
    public void checkHovers() {
        driver.findElement(By.linkText("Hovers")).click();
        Actions actions = new Actions(driver);
        WebElement elementToMoveTo;
        int i = 1;
        for (int a = 0; a < driver.findElements(By.xpath("//div[@class='figure']")).size(); a++) {
            elementToMoveTo = driver.findElement(By.xpath(String.format("(//div[@class='figure'])[%s]", i)));
            actions.moveToElement(elementToMoveTo).build().perform();
            Assert.assertEquals(elementToMoveTo.findElement(By.xpath(".//h5")).getText(), String.format("name: user%s", i));
            elementToMoveTo.findElement(By.xpath(".//a[@href]")).click();
            Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Not Found");
            driver.navigate().back();
            i++;
        }
    }

    @Test
    public void checkNotification(){
        WebElement hoverNotification = driver.findElement(By.linkText("Notification Messages"));
        hoverNotification.click();
        WebElement notificationOne=driver.findElement(By.xpath("//div[@class='flash notice']"));
        Assert.assertEquals(notificationOne.getText(),"Action successful\n" + "×");
        WebElement clickNotification = driver.findElement(By.xpath("//a[contains(text(),'Click here')]"));
        clickNotification.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebElement notificationTwo=driver.findElement(By.xpath("//div[@class='flash notice']"));
        Assert.assertEquals(notificationTwo.getText(),"Action unsuccesful, please try again\n" + "×");
    }
}
