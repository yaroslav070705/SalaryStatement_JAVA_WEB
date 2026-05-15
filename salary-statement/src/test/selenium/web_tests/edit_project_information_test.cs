using OpenQA.Selenium;

public static class EditProjectInformationTest
{
    public static void Run(ProjectTestData project)
    {
        using IWebDriver driver = ProjectInformationTestHelper.CreateDriver();

        string newName = project.Name + " Updated " + DateTime.UtcNow.ToString("HHmmss");
        string newStartDate = DateTime.UtcNow.AddDays(-3).ToString("yyyy-MM-dd");
        string newEndDate = DateTime.UtcNow.AddDays(30).ToString("yyyy-MM-dd");

        ProjectInformationTestHelper.OpenProjectInformation(driver, project);

        driver.FindElement(By.Id("editProjectBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "кнопка сохранения проекта доступна", d => d.FindElement(By.Id("saveProjectBtn")).Enabled);

        ProjectInformationTestHelper.SetInputValue(driver, "projectNameInput", newName);
        ProjectInformationTestHelper.SetInputValue(driver, "projectStartDateInput", newStartDate);
        ProjectInformationTestHelper.SetInputValue(driver, "projectEndDateInput", newEndDate);

        driver.FindElement(By.Id("saveProjectBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "новые данные проекта показаны на странице", d =>
        {
            return d.FindElement(By.Id("projectName")).Text.Contains(newName, StringComparison.OrdinalIgnoreCase)
                && d.FindElement(By.Id("projectStartDate")).Text.Contains(newStartDate, StringComparison.OrdinalIgnoreCase)
                && d.FindElement(By.Id("projectEndDate")).Text.Contains(newEndDate, StringComparison.OrdinalIgnoreCase);
        });

        driver.Navigate().Refresh();

        ProjectInformationTestHelper.WaitUntil(driver, "новые данные проекта сохранились после обновления", d =>
        {
            return d.FindElement(By.Id("projectName")).Text.Contains(newName, StringComparison.OrdinalIgnoreCase)
                && d.FindElement(By.Id("projectStartDate")).Text.Contains(newStartDate, StringComparison.OrdinalIgnoreCase)
                && d.FindElement(By.Id("projectEndDate")).Text.Contains(newEndDate, StringComparison.OrdinalIgnoreCase);
        });

        string projectName = driver.FindElement(By.Id("projectName")).Text;
        string projectStartDate = driver.FindElement(By.Id("projectStartDate")).Text;
        string projectEndDate = driver.FindElement(By.Id("projectEndDate")).Text;

        if (!projectName.Contains(newName, StringComparison.OrdinalIgnoreCase)
            || !projectStartDate.Contains(newStartDate, StringComparison.OrdinalIgnoreCase)
            || !projectEndDate.Contains(newEndDate, StringComparison.OrdinalIgnoreCase))
        {
            throw new InvalidOperationException("Проверка не пройдена: после обновления страницы данные проекта не совпадают с новыми.");
        }
    }
}
