package br.sp.lhperez.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
	
	@Test
	public void healthCheck() throws MalformedURLException, InterruptedException {			

		//System.setProperty("webdriver.chrome.driver", "C:\\CICD\\seleniumDrivers\\chromedriver.exe");
		
		//Teste usando driver local
		//WebDriver driver = new ChromeDriver();
		
		//Teste usando selenium hub / teste distribuído
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		
		//WebDriver driver = new RemoteWebDriver(new URL("http://192.168.31.200:4444/wd/hub"), cap); //caminho dado no console do selenium grid
		
		//rodar ipconfig para pegar o ip da maquina que esta rodando o grid
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.31.200:4444/wd/hub"), cap); //caminho dado no console do selenium grid
		

		try {

			//driver.navigate().to("http://localhost:8001/tasks/");
			
			//rodar ipconfig para pegar o ip da maquina que esta rodando a app
			//driver.navigate().to("http://localhost:9999/tasks/");
			driver.navigate().to("http://192.168.31.200:9999/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//Thread.sleep(2000);
			
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));
	
			// driver.get("http://www.google.com.br/");
		} finally {
			driver.quit();
		}

	}

}
