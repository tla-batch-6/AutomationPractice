package tests;

import base.BaseTest;
import data.pojos.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.UserDBPage;
import pages.UserMgtPage;
import utils.BrowserUtils;

public class UserDBTest extends BaseTest {
    UserMgtPage userMgtPage;
    UserDBPage page;

    @BeforeMethod
    public void setUp(){
        //1. open db page
        driver.findElement(By.xpath("//nav/a[text()='User-Mgt']")).click();
        userMgtPage = new UserMgtPage(driver);
        page = new UserDBPage(driver);
    }

    @Test(testName = "US2003 - New user's password format")
    public void test01(){
        //2. capture first, last and password - done in page class

        //3. validate format is as expected
        userMgtPage.accessDbBtn.click();
        //switching windows
        BrowserUtils.switchToNewWindow(driver);
        String expectedPassword = page.firstNames.get(0).getText() + "." + page.lastNames.get(0).getText() + "$";
        Assert.assertEquals(page.passwords.get(0).getText(), expectedPassword.toLowerCase());

        int index = page.firstNames.size()/2;
        String expectedPassword2 = page.firstNames.get(index).getText() + "." + page.lastNames.get(index).getText() + "$";
        Assert.assertEquals(page.passwords.get(index).getText(), expectedPassword2.toLowerCase());

        int lastIndex = page.firstNames.size() - 1;
        String expectedPassword3 = page.firstNames.get(lastIndex).getText() + "." + page.lastNames.get(lastIndex).getText() + "$";
        Assert.assertEquals(page.passwords.get(lastIndex).getText(), expectedPassword3.toLowerCase());
    }

    @Test(testName = "US2004 - New user's password format")
    public void test02(){
        User user = new User("John", "Cloud", "222-333-4444",
                "john.cloud@test.com", "Student");

        userMgtPage.addNewUser(user.getFirstName(), user.getLastName(), user.getPhone(),
                user.getEmail(), user.getRole());

        userMgtPage.submitToTable.click();

        userMgtPage.accessDbBtn.click();
        BrowserUtils.switchToNewWindow(driver);

        //Test
        String expected = user.getFirstName() + "." + user.getLastName() + "$";
        String actual = page.passwords.get(page.passwords.size() - 1).getText();

        Assert.assertEquals(actual, expected.toLowerCase());
    }


}
