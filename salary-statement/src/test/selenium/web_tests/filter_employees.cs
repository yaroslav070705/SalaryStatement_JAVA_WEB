using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

public static class FilterEmployeesTest
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static void Run(EmployeeTestData employee)
    {
        ChromeOptions options = new ChromeOptions();
        options.BinaryLocation = "/usr/bin/google-chrome";

        using IWebDriver driver = new ChromeDriver(options);
        driver.Manage().Timeouts().ImplicitWait = TimeSpan.Zero;

        driver.Navigate().GoToUrl(BaseUrl);

        WaitUntil(driver, "кнопка Фильтры видима", d => d.FindElement(By.Id("toggleFiltersBtn")).Displayed);
        driver.FindElement(By.Id("toggleFiltersBtn")).Click();

        WaitUntil(driver, "панель фильтров открылась", d => (d.FindElement(By.Id("filtersPanel")).GetAttribute("class") ?? "").Contains("active"));

        driver.FindElement(By.Id("filterNameInput")).SendKeys(employee.Name);
        driver.FindElement(By.Id("filterSurnameInput")).SendKeys(employee.Surname);
        driver.FindElement(By.Id("filterMiddleNameInput")).SendKeys(employee.MiddleName);

        driver.FindElement(By.Id("applyFiltersBtn")).Click();

        WaitUntil(driver, "таблица работников отфильтровалась", d =>
        {
            IReadOnlyCollection<IWebElement> rows = GetDataRows(d);
            return rows.Count == 1 && rows.First().Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase);
        });

        IReadOnlyCollection<IWebElement> finalRows = GetDataRows(driver);
        if (finalRows.Count != 1)
        {
            throw new InvalidOperationException("Проверка не пройдена: после фильтрации ожидалась 1 строка, получено: " + finalRows.Count);
        }

        string rowText = finalRows.First().Text;
        if (!rowText.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Проверка не пройдена: найденная строка не содержит ФИО работника: " + employee.FullName + ". Текст строки: " + rowText);
        }
    }

    private static IReadOnlyCollection<IWebElement> GetDataRows(IWebDriver driver)
    {
        return driver
            .FindElements(By.CssSelector("#employeesTableBody tr"))
            .Where(row => !row.Text.Contains("Сотрудники не найдены", StringComparison.OrdinalIgnoreCase))
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
