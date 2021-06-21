package br.sp.lhperez.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() {
		System.setProperty("webdriver.chrome.driver", "C:\\CICD\\seleniumDrivers\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// driver.get("http://www.google.com.br/");
		return driver;
	}

	@Test
	public void salvarTarefaValidarMensagemSucesso() {
		WebDriver driver = acessarAplicacao();

		try {
			// Clicar add todo
			driver.findElement(By.id("addTodo")).click();

			// Escrever a descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2040");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Success!", mensagem);
		} finally {
			// fechar o browser
			driver.quit();
		}
	}
	
	
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();

		try {
			// Clicar add todo
			driver.findElement(By.id("addTodo")).click();

			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2040");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the task description", mensagem);
		} finally {
			// fechar o browser
			driver.quit();
		}
	}
	
	
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();

		try {
			// Clicar add todo
			driver.findElement(By.id("addTodo")).click();

			// Escrever a descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the due date", mensagem);
		} finally {
			// fechar o browser
			driver.quit();
		}
	}
	
	
	
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();

		try {
			// Clicar add todo
			driver.findElement(By.id("addTodo")).click();

			// Escrever a descricao
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// Escrever a data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Due date must not be in past", mensagem);
		} finally {
			// fechar o browser
			driver.quit();
		}
	}

}
