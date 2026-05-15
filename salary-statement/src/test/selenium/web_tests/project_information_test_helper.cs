using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

public static class ProjectInformationTestHelper
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static IWebDriver CreateDriver()
    {
        ChromeOptions options = new ChromeOptions();
        options.BinaryLocation = "/usr/bin/google-chrome";

        IWebDriver driver = new ChromeDriver(options);
        driver.Manage().Timeouts().ImplicitWait = TimeSpan.Zero;
        return driver;
    }

    public static void OpenProjectInformation(IWebDriver driver, ProjectTestData project)
    {
        driver.Navigate().GoToUrl(BaseUrl);

        WaitUntil(driver, "кнопка меню видима", d => d.FindElement(By.Id("menuBtn")).Displayed);
        driver.FindElement(By.Id("menuBtn")).Click();

        WaitUntil(driver, "кнопка Проекты видима", d => d.FindElement(By.Id("menuProjectsBtn")).Displayed);
        driver.FindElement(By.Id("menuProjectsBtn")).Click();

        WaitUntil(driver, "страница проектов открылась", d => d.FindElement(By.Id("projectsTableBody")).Displayed);

        WaitUntil(driver, "проект найден в таблице", d => GetProjectRows(d).Any(row => row.Text.Contains(project.Name, StringComparison.OrdinalIgnoreCase)));

        IWebElement projectRow = GetProjectRows(driver).First(row => row.Text.Contains(project.Name, StringComparison.OrdinalIgnoreCase));
        projectRow.Click();

        WaitUntil(driver, "страница проекта открылась", d =>
        {
            string projectName = d.FindElement(By.Id("projectName")).Text;
            return d.Url.Contains("ProjectInformation.jsp", StringComparison.OrdinalIgnoreCase)
                && projectName.Contains(project.Name, StringComparison.OrdinalIgnoreCase);
        });
    }

    public static IReadOnlyCollection<IWebElement> GetProjectWorkerRows(IWebDriver driver)
    {
        return driver
            .FindElements(By.CssSelector("#projectWorkersTableBody tr"))
            .Where(row => !row.Text.Contains("Работники не найдены", StringComparison.OrdinalIgnoreCase))
            .ToList();
    }

    public static IReadOnlyCollection<IWebElement> GetDeleteWorkerRows(IWebDriver driver)
    {
        return driver
            .FindElements(By.CssSelector("#deleteWorkersTableBody tr"))
            .Where(row => !row.Text.Contains("Работники не найдены", StringComparison.OrdinalIgnoreCase))
            .ToList();
    }

    public static string SelectFirstAvailableRole(IWebDriver driver)
    {
        WaitUntil(driver, "загрузился список ролей", d => d.FindElements(By.CssSelector("#addWorkerRoleSelect option")).Count > 1);

        IWebElement roleSelect = driver.FindElement(By.Id("addWorkerRoleSelect"));
        IWebElement? firstRoleOption = roleSelect
            .FindElements(By.TagName("option"))
            .FirstOrDefault(option => !string.IsNullOrWhiteSpace(option.GetAttribute("value")));

        if (firstRoleOption == null)
        {
            throw new InvalidOperationException("Список ролей пуст, невозможно добавить работника в проект.");
        }

        SetInputValue(driver, "addWorkerRoleSelect", firstRoleOption.GetAttribute("value")!);
        return firstRoleOption.Text;
    }

    public static void SetInputValue(IWebDriver driver, string elementId, string value)
    {
        ((IJavaScriptExecutor)driver).ExecuteScript(
            "const element = document.getElementById(arguments[0]); element.value = arguments[1]; element.dispatchEvent(new Event('input', { bubbles: true })); element.dispatchEvent(new Event('change', { bubbles: true }));",
            elementId,
            value
        );
    }

    public static void WaitUntil(IWebDriver driver, string description, Func<IWebDriver, bool> condition)
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

    private static IReadOnlyCollection<IWebElement> GetProjectRows(IWebDriver driver)
    {
        return driver
            .FindElements(By.CssSelector("#projectsTableBody tr"))
            .Where(row => !row.Text.Contains("Проекты не найдены", StringComparison.OrdinalIgnoreCase))
            .ToList();
    }
}
