package br.sp.lhperez.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	/* Obs: para executar de forma paralela pelo seleniumGrid
	 * colocar no arquivo pom do projeto:
	 * <build>
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>2.20</version>
              <configuration>
                  <parallel>all</parallel>
                  <useUnlimitedThreads>true</useUnlimitedThreads>
              </configuration>
          </plugin>
      </plugins>
</build>

	e executar os teste pelo comando
	mvn test
	 */

	public WebDriver acessarAplicacao() throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "C:\\CICD\\seleniumDrivers\\chromedriver.exe");

		//Teste usando driver local
		//WebDriver driver = new ChromeDriver();
		
		//Teste usando selenium hub / teste distribuído
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		
		//WebDriver driver = new RemoteWebDriver(new URL("http://192.168.31.200:4444/wd/hub"), cap); //caminho dado no console do selenium grid
		
		//rodar ipconfig para pegar o ip da maquina que esta rodando o grid
		WebDriver driver = new RemoteWebDriver(new URL("http://172.30.160.1:4444/wd/hub"), cap); //caminho dado no console do selenium grid
		
		//driver.navigate().to("http://localhost:8001/tasks/");
		
		//rodar ipconfig para pegar o ip da maquina que esta rodando a app
		driver.navigate().to("http://172.30.160.1:8001/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// driver.get("http://www.google.com.br/");
		return driver;
	}

	@Test
	public void salvarTarefaValidarMensagemSucesso() throws MalformedURLException {
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
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
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
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
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
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
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
	
	
	
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			//Inserir tarefa
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
			
			//Remover tarefa
			driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
			// Validar mensagem de sucesso
			mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", mensagem);
		} finally {
			// fechar o browser
			driver.quit();
		}
	}

}
