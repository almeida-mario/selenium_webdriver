package testeSelenium;

import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

public class TestSelenium {
	private WebDriver driver,driver_chrom,driver_ie;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:80";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
	}

	@Test
	public void testeBuscaTitulos() throws Exception {
		
		//File file = new File("C:/driver_selenium/chromedriver_win32/chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
		//driver_chrom = new ChromeDriver();

		driver.get(baseUrl + "/?search");
		driver.findElement(By.id("searchString")).clear();
		driver.findElement(By.id("searchString")).sendKeys("classpath");
		driver.findElement(By.name("searchType")).click();

		try {

			assertTrue(isElementPresent(By.xpath("//table[@id='searchResultsTable']/thead/tr/th[2]")));

		} catch (Exception e) {

			verificationErrors.append(e.toString());

		}
	}

	@Test
	public void testeBuscaConteudos() throws Exception {
		
		//File file = new File("C:/driver_selenium/chromedriver_win32/chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
		//driver_chrom = new ChromeDriver();
		
		driver.get(baseUrl + "/FrontPage?search");
		driver.findElement(By.id("searchString")).clear();
		driver.findElement(By.id("searchString")).sendKeys("classpath");
		driver.findElement(By.id("root")).click();
		driver.findElement(By.xpath("(//input[@name='searchType'])[2]")).click();

		try {

			assertTrue(isElementPresent(By.xpath("//table[@id='searchResultsTable']/thead/tr/th[2]")));

		} catch (Exception e) {

			verificationErrors.append(e.toString());

		}
	}

	@Test
	public void botaoVoltar() throws Exception {
		driver.get(baseUrl + "/FrontPage?search");
		driver.findElement(By.linkText("Back")).click();
	}

	@Test
	public void testeBuscaConteudosVazio() throws Exception {
		driver.get(baseUrl + "/FrontPage?search");
		driver.findElement(By.id("searchString")).clear();
		driver.findElement(By.id("searchString")).sendKeys("");
		driver.findElement(By.id("root")).click();
		driver.findElement(By.xpath("(//input[@name='searchType'])[2]")).click();

		try {

			assertEquals("No pages matched your search criteria.", driver.findElement(By.id("feedback")).getText());

		} catch (Exception e) {

			verificationErrors.append(e.toString());

		}
	}

	@Test
	public void testProperties() throws Exception {
		driver.get(baseUrl + "/FrontPage?responder=search&searchString=&searchScope=root&searchType=Search+Titles");
		driver.findElement(By.linkText("Properties search")).click();
		driver.findElement(By.id("Test")).click();
		driver.findElement(By.cssSelector("#properties > fieldset.buttons > input[name=\"searchType\"]")).click();
		try {
			assertTrue(isElementPresent(By.id("feedback")));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@Test
	public void testPropertiesVazio() throws Exception {
		driver.get(baseUrl + "/FrontPage?responder=search&searchString=&searchScope=root&searchType=Search+Titles");

		driver.findElement(By.linkText("Properties search")).click();
		driver.findElement(By.id("Suite")).click();
		driver.findElement(By.cssSelector("#properties > fieldset.buttons > input[name=\"searchType\"]")).click();
		driver.findElement(By.id("Test")).click();
		driver.findElement(By.id("Suite")).click();
		driver.findElement(By.cssSelector("#properties > fieldset.buttons > input[name=\"searchType\"]")).click();
		try {
			assertEquals("No pages matched your search criteria.", driver.findElement(By.id("feedback")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@Test
	public void testeHome() throws Exception {
		driver.get(baseUrl + "/FrontPage");
		driver.findElement(By.linkText("Tools")).click();
		driver.findElement(By.linkText("Search")).click();
           

		try {
			assertEquals("FrontPage", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
