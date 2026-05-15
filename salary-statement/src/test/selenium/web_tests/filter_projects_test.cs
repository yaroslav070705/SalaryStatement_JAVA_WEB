using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

public static class FilterProjectsTest
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static void Run(ProjectTestData project)
    {
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

        driver.FindElement(By.Id("filterProjectNameInput")).SendKeys(project.Name);

        WaitUntil(driver, "кнопка Применить фильтр проектов доступна", d => d.FindElement(By.Id("applyProjectFiltersBtn")).Enabled);
        driver.FindElement(By.Id("applyProjectFiltersBtn")).Click();

        WaitUntil(driver, "таблица проектов отфильтровалась", d =>
        {
            IReadOnlyCollection<IWebElement> rows = GetDataRows(d);
            return rows.Count == 1 && rows.First().Text.Contains(project.Name, StringComparison.OrdinalIgnoreCase);
        });

        IReadOnlyCollection<IWebElement> finalRows = GetDataRows(driver);
        if (finalRows.Count != 1)
        {
            throw new InvalidOperationException("Проверка не пройдена: после фильтрации ожидалась 1 строка, получено: " + finalRows.Count);
        }

        string rowText = finalRows.First().Text;
        if (!rowText.Contains(project.Name, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Проверка не пройдена: найденная строка не содержит имя проекта: " + project.Name + ". Текст строки: " + rowText);
        }
    }

    private static IReadOnlyCollection<IWebElement> GetDataRows(IWebDriver driver)
    {
        return driver
            .FindElements(By.CssSelector("#projectsTableBody tr"))
            .Where(row => !row.Text.Contains("Проекты не найдены", StringComparison.OrdinalIgnoreCase))
            .ToList();
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
