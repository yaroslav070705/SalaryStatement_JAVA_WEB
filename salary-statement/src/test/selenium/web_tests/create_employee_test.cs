using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;

public static class AddEmployeeTest
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static EmployeeTestData Run()
    {
        string uniqueSuffix = DateTime.UtcNow.ToString("yyyyMMddHHmmss");
        string name = "Иван";
        string surname = "Тестов";
        string middleName = "Селениумович" + uniqueSuffix;
        string expectedFullName = surname + " " + name + " " + middleName;

        ChromeOptions options = new ChromeOptions();
        options.BinaryLocation = "/usr/bin/google-chrome";

        using IWebDriver driver = new ChromeDriver(options);
        driver.Manage().Timeouts().ImplicitWait = TimeSpan.Zero;

        driver.Navigate().GoToUrl(BaseUrl);

        WaitUntil(driver, "кнопка Добавить работника видима", d => d.FindElement(By.Id("addEmployeeBtn")).Displayed);
        driver.FindElement(By.Id("addEmployeeBtn")).Click();

        WaitUntil(driver, "открылась форма добавления работника", d => (d.FindElement(By.Id("createOverlay")).GetAttribute("class") ?? "").Contains("active"));

        driver.FindElement(By.Id("createNameInput")).SendKeys(name);
        driver.FindElement(By.Id("createSurnameInput")).SendKeys(surname);
        driver.FindElement(By.Id("createMiddleNameInput")).SendKeys(middleName);
        SetInputValue(driver, "createBirthDateInput", "1995-05-15");
        driver.FindElement(By.Id("createEducationInput")).SendKeys("Высшее");

        SelectFirstAvailablePost(driver);

        driver.FindElement(By.Id("submitCreateBtn")).Click();

        WaitUntil(driver, "форма закрылась после добавления работника или появилась ошибка", d =>
        {
            bool formClosed = !(d.FindElement(By.Id("createOverlay")).GetAttribute("class") ?? "").Contains("active");
            bool errorShown = d.FindElement(By.Id("requestResult")).Displayed
                && d.FindElement(By.Id("requestResult")).Text.StartsWith("Ошибка:");

            return formClosed || errorShown;
        });

        IWebElement requestResult = driver.FindElement(By.Id("requestResult"));
        if (requestResult.Displayed && requestResult.Text.StartsWith("Ошибка:"))
        {
            throw new InvalidOperationException("Страница показала ошибку после добавления работника: " + requestResult.Text);
        }

        AssertEmployeeAppearedInTable(driver, expectedFullName);

        return new EmployeeTestData(name, surname, middleName);
    }

    private static void AssertEmployeeAppearedInTable(IWebDriver driver, string expectedFullName)
    {
        WaitUntil(driver, "работник появился в таблице работников", d =>
        {
            IReadOnlyCollection<IWebElement> rows = d.FindElements(By.CssSelector("#employeesTableBody tr"));
            return rows.Any(row => row.Text.Contains(expectedFullName, StringComparison.OrdinalIgnoreCase));
        });

        bool employeeExists = driver
            .FindElements(By.CssSelector("#employeesTableBody tr"))
            .Any(row => row.Text.Contains(expectedFullName, StringComparison.OrdinalIgnoreCase));

        if (!employeeExists)
        {
            throw new InvalidOperationException("Проверка не пройдена: работник не найден в таблице: " + expectedFullName);
        }
    }

    private static void SelectFirstAvailablePost(IWebDriver driver)
    {
        WaitUntil(driver, "загрузился список должностей", d => d.FindElements(By.CssSelector("#createPostInput option")).Count > 1);

        IWebElement postSelect = driver.FindElement(By.Id("createPostInput"));
        IReadOnlyCollection<IWebElement> options = postSelect.FindElements(By.TagName("option"));
        IWebElement? firstPostOption = options.FirstOrDefault(option => !string.IsNullOrWhiteSpace(option.GetAttribute("value")));

        if (firstPostOption == null)
        {
            throw new InvalidOperationException("Список должностей пуст, невозможно добавить работника.");
        }

        SetSelectValue(driver, "createPostInput", firstPostOption.GetAttribute("value"));
    }

    private static void SetInputValue(IWebDriver driver, string elementId, string value)
    {
        ((IJavaScriptExecutor)driver).ExecuteScript(
            "const element = document.getElementById(arguments[0]); element.value = arguments[1]; element.dispatchEvent(new Event('input', { bubbles: true })); element.dispatchEvent(new Event('change', { bubbles: true }));",
            elementId,
            value
        );
    }

    private static void SetSelectValue(IWebDriver driver, string elementId, string? value)
    {
        if (string.IsNullOrWhiteSpace(value))
        {
            throw new InvalidOperationException("Не удалось выбрать должность: у option пустой value.");
        }

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

public sealed record EmployeeTestData(string Name, string Surname, string MiddleName)
{
    public string FullName => Surname + " " + Name + " " + MiddleName;
}
