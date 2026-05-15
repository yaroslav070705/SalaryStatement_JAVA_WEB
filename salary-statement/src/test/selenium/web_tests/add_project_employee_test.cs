using OpenQA.Selenium;

public static class AddProjectEmployeeTest
{
    public static void Run(ProjectTestData project, EmployeeTestData employee)
    {
        using IWebDriver driver = ProjectInformationTestHelper.CreateDriver();

        ProjectInformationTestHelper.OpenProjectInformation(driver, project);

        ProjectInformationTestHelper.WaitUntil(driver, "мини-таблица работников загрузилась", d => d.FindElement(By.Id("addWorkersTableBody")).Displayed);
        driver.FindElement(By.Id("addWorkerSearchInput")).SendKeys(employee.FullName);

        ProjectInformationTestHelper.WaitUntil(driver, "работник найден в мини-таблице", d =>
        {
            IReadOnlyCollection<IWebElement> rows = d.FindElements(By.CssSelector("#addWorkersTableBody tr"));
            return rows.Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        });

        IWebElement employeeRow = driver
            .FindElements(By.CssSelector("#addWorkersTableBody tr"))
            .First(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        employeeRow.Click();

        ProjectInformationTestHelper.SelectFirstAvailableRole(driver);

        ProjectInformationTestHelper.WaitUntil(driver, "кнопка Добавить работника в проект доступна", d => d.FindElement(By.Id("submitAddWorkerBtn")).Enabled);
        driver.FindElement(By.Id("submitAddWorkerBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "работник появился в таблице проекта", d =>
        {
            return ProjectInformationTestHelper.GetProjectWorkerRows(d)
                .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        });

        bool employeeExists = ProjectInformationTestHelper.GetProjectWorkerRows(driver)
            .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));

        if (!employeeExists)
        {
            throw new InvalidOperationException("Проверка не пройдена: работник не появился в таблице проекта: " + employee.FullName);
        }
    }
}
