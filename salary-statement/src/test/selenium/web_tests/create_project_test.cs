using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

public static class CreateProjectTest
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static ProjectTestData Run()
    {
        string projectName = "Selenium Project " + DateTime.UtcNow.ToString("yyyyMMddHHmmss");

        ChromeOptions options = new ChromeOptions();
        options.BinaryLocation = "/usr/bin/google-chrome";

        using IWebDriver driver = new ChromeDriver(options);
        driver.Manage().Timeouts().ImplicitWait = TimeSpan.Zero;

        driver.Navigate().GoToUrl(BaseUrl);

        WaitUntil(driver, "кнопка меню видима", d => d.FindElement(By.Id("menuBtn")).Displayed);
        driver.FindElement(By.Id("menuBtn")).Click();

        WaitUntil(driver, "кнопка Проекты видима", d => d.FindElement(By.Id("menuProjectsBtn")).Displayed);
        driver.FindElement(By.Id("menuProjectsBtn")).Click();

        WaitUntil(driver, "страница проектов открылась", d => d.FindElement(By.Id("projectsTableBody")).Displayed);

        driver.FindElement(By.Id("createProjectNameInput")).SendKeys(projectName);
        SetInputValue(driver, "createProjectStartDateInput", DateTime.UtcNow.ToString("yyyy-MM-dd"));

        WaitUntil(driver, "кнопка Добавить проект доступна", d => d.FindElement(By.Id("submitCreateProjectBtn")).Enabled);
        driver.FindElement(By.Id("submitCreateProjectBtn")).Click();

        WaitUntil(driver, "проект появился в таблице", d =>
        {
            IReadOnlyCollection<IWebElement> rows = d.FindElements(By.CssSelector("#projectsTableBody tr"));
            return rows.Any(row => row.Text.Contains(projectName, StringComparison.OrdinalIgnoreCase));
        });

        bool projectExists = driver
            .FindElements(By.CssSelector("#projectsTableBody tr"))
            .Any(row => row.Text.Contains(projectName, StringComparison.OrdinalIgnoreCase));

        if (!projectExists)
        {
            throw new InvalidOperationException("Проверка не пройдена: проект не найден в таблице: " + projectName);
        }

        return new ProjectTestData(projectName);
    }

    private static void SetInputValue(IWebDriver driver, string elementId, string value)
    {
        ((IJavaScriptExecutor)driver).ExecuteScript(
            "const element = document.getElementById(arguments[0]); element.value = arguments[1]; element.dispatchEvent(new Event('input', { bubbles: true })); element.dispatchEvent(new Event('change', { bubbles: true }));",
            elementId,
            value
        );
    }

    private static void WaitUntil(IWebDriver driver, string description, Func<IWebDriver, bool> condition)
    {
        DateTime deadline = DateTime.UtcNow.AddSeconds(10);
        Exception? lastError = null;

        while (DateTime.UtcNow < deadline)
        {
            try
            {
                if (condition(driver))
                {
                    return;
                }
            }
            catch (Exception error) when (error is NoSuchElementException or StaleElementReferenceException)
            {
                lastError = error;
            }

            Thread.Sleep(200);
        }

        throw new TimeoutException("Ожидание условия Selenium истекло: " + description + ".", lastError);
    }
}

public sealed record ProjectTestData(string Name);
