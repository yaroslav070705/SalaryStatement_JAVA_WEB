using OpenQA.Selenium;

public static class EmployeeInformationTestHelper
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static IWebDriver OpenEmployeeInformation(EmployeeTestData employee)
    {
        IWebDriver driver = ProjectInformationTestHelper.CreateDriver();
        driver.Navigate().GoToUrl(BaseUrl);

        ProjectInformationTestHelper.WaitUntil(driver, "таблица работников открылась", d => d.FindElement(By.Id("employeesTableBody")).Displayed);
        ProjectInformationTestHelper.WaitUntil(driver, "работник найден в таблице", d =>
        {
            return d.FindElements(By.CssSelector("#employeesTableBody tr"))
                .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        });

        IWebElement row = driver
            .FindElements(By.CssSelector("#employeesTableBody tr"))
            .First(item => item.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        row.FindElement(By.CssSelector(".emp-btn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "страница работника открылась", d =>
        {
            return d.Url.Contains("EmployeeInformation.jsp", StringComparison.OrdinalIgnoreCase)
                && d.FindElement(By.Id("employeeName")).Text.Contains(employee.Name, StringComparison.OrdinalIgnoreCase);
        });

        return driver;
    }

    public static void AssertTableContains(IWebDriver driver, string tbodySelector, params string[] expectedParts)
    {
        ProjectInformationTestHelper.WaitUntil(driver, "таблица " + tbodySelector + " содержит ожидаемые данные", d =>
        {
            return d.FindElements(By.CssSelector(tbodySelector + " tr"))
                .Any(row => expectedParts.All(part => row.Text.Contains(part, StringComparison.OrdinalIgnoreCase)));
        });

        bool exists = driver.FindElements(By.CssSelector(tbodySelector + " tr"))
            .Any(row => expectedParts.All(part => row.Text.Contains(part, StringComparison.OrdinalIgnoreCase)));

        if (!exists)
        {
            throw new InvalidOperationException("Проверка не пройдена: таблица " + tbodySelector + " не содержит ожидаемые данные.");
        }
    }
}

public static class EmployeeInformationPostHistoryTest
{
    public static void Run(EmployeeTestData employee)
    {
        using IWebDriver driver = EmployeeInformationTestHelper.OpenEmployeeInformation(employee);
        EmployeeInformationTestHelper.AssertTableContains(driver, "#postHistoryTableBody", employee.PostName);
    }
}

public static class EmployeeInformationProjectsTableTest
{
    public static void Run(EmployeeTestData employee, RoleAssignmentTestData assignment)
    {
        using IWebDriver driver = EmployeeInformationTestHelper.OpenEmployeeInformation(employee);
        EmployeeInformationTestHelper.AssertTableContains(driver, "#projectsTableBody", assignment.ProjectName, assignment.RoleName);
    }
}

public static class EmployeeInformationRolesHistoryTest
{
    public static void Run(EmployeeTestData employee, RoleAssignmentTestData assignment)
    {
        using IWebDriver driver = EmployeeInformationTestHelper.OpenEmployeeInformation(employee);
        EmployeeInformationTestHelper.AssertTableContains(driver, "#rolesHistoryTableBody", assignment.ProjectName, assignment.RoleName);
    }
}
