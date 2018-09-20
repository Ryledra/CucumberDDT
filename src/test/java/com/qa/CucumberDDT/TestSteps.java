package com.qa.CucumberDDT;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;

public class TestSteps {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	
	@Before
	public void setup()	{
		Constants.count++;
		report = Constants.report;
		test = report.startTest("Search " + Constants.count);
		System.setProperty(Constants.driverType, Constants.cDFilePath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@Given("^I go to \"([^\"]*)\" website$")
	public void i_go_to_website(String arg1) throws Throwable {
		driver.get(arg1);
		test.log(LogStatus.INFO, "'"+arg1+"' opened");
	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String arg1) throws Throwable {
		BingIndex bingIndexPage = PageFactory.initElements(driver, BingIndex.class);
		bingIndexPage.search(arg1);
		test.log(LogStatus.INFO, "'"+arg1+"' searched and submitted");
		Constants.searchItem = arg1;
	}

	@Then("^I am taken to a list of data for that search$")
	public void i_am_taken_to_a_list_of_data_for_that_search() throws Throwable {
		if (driver.getTitle().equals(Constants.searchItem + " - Bing")) test.log(LogStatus.PASS, "Correct search run");
		else test.log(LogStatus.FAIL, "Incorrect search run");
	}
	
	@After
	public void tearDown()	{
		driver.quit();
		report.endTest(test);
		report.flush();
	}
}
