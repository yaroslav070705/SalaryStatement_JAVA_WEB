using OpenQA.Selenium;

public static class DeleteProjectEmployeeTest
{
    public static void Run(ProjectTestData project, EmployeeTestData employee)
    {
        using IWebDriver driver = ProjectInformationTestHelper.CreateDriver();

        ProjectInformationTestHelper.OpenProjectInformation(driver, project);

        ProjectInformationTestHelper.WaitUntil(driver, "работник есть в таблице удаления", d =>
        {
            return ProjectInformationTestHelper.GetDeleteWorkerRows(d)
                .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        });

        IWebElement employeeRow = ProjectInformationTestHelper.GetDeleteWorkerRows(driver)
            .First(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        employeeRow.Click();

        ProjectInformationTestHelper.WaitUntil(driver, "кнопка Удалить работника из проекта доступна", d => d.FindElement(By.Id("deleteWorkerBtn")).Enabled);
        driver.FindElement(By.Id("deleteWorkerBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "работник удален из таблицы проекта", d =>
        {
            return !ProjectInformationTestHelper.GetProjectWorkerRows(d)
                .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));
        });

        bool employeeExists = ProjectInformationTestHelper.GetProjectWorkerRows(driver)
            .Any(row => row.Text.Contains(employee.FullName, StringComparison.OrdinalIgnoreCase));

        if (employeeExists)
        {
            throw new InvalidOperationException("Проверка не пройдена: работник остался в таблице проекта: " + employee.FullName);
        }
    }
}
