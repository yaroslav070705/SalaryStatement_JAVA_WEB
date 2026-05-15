using OpenQA.Selenium;

public static class PayoutPoliciesTestHelper
{
    private const string BaseUrl = "http://localhost:8080/salary-statement-1.0-SNAPSHOT/";

    public static IWebDriver OpenPayoutPolicies()
    {
        IWebDriver driver = ProjectInformationTestHelper.CreateDriver();
        driver.Navigate().GoToUrl(BaseUrl);

        ProjectInformationTestHelper.WaitUntil(driver, "кнопка меню видима", d => d.FindElement(By.Id("menuBtn")).Displayed);
        driver.FindElement(By.Id("menuBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "кнопка Политики выплат видима", d => d.FindElement(By.Id("menuPoliciesBtn")).Displayed);
        driver.FindElement(By.Id("menuPoliciesBtn")).Click();

        ProjectInformationTestHelper.WaitUntil(driver, "страница политик выплат открылась", d => d.FindElement(By.Id("submitAddPolicyBtn")).Displayed);
        return driver;
    }

    public static void ChoosePolicyType(IWebDriver driver, string policyType)
    {
        driver.FindElement(By.CssSelector(".policy-type-btn[data-policy-type='" + policyType + "']")).Click();
    }

    public static void ClickSubmit(IWebDriver driver)
    {
        ProjectInformationTestHelper.WaitUntil(driver, "кнопка отправки политики доступна", d => d.FindElement(By.Id("submitAddPolicyBtn")).Enabled);
        driver.FindElement(By.Id("submitAddPolicyBtn")).Click();
    }

    public static IWebElement FindRow(IWebDriver driver, string tbodySelector, params string[] expectedParts)
    {
        ProjectInformationTestHelper.WaitUntil(driver, "строка найдена в таблице " + tbodySelector, d =>
        {
            return GetRows(d, tbodySelector).Any(row => expectedParts.All(part => row.Text.Contains(part, StringComparison.OrdinalIgnoreCase)));
        });

        return GetRows(driver, tbodySelector).First(row => expectedParts.All(part => row.Text.Contains(part, StringComparison.OrdinalIgnoreCase)));
    }

    public static IReadOnlyCollection<IWebElement> GetRows(IWebDriver driver, string tbodySelector)
    {
        return driver
            .FindElements(By.CssSelector(tbodySelector + " tr"))
            .Where(row => !row.Text.Contains("не найдены", StringComparison.OrdinalIgnoreCase)
                && !row.Text.Contains("Ошибка загрузки", StringComparison.OrdinalIgnoreCase))
            .ToList();
    }

    public static void ClickEdit(IWebElement row)
    {
        row.FindElement(By.TagName("button")).Click();
    }

    public static void SelectOptionByText(IWebDriver driver, string elementId, string text)
    {
        ((IJavaScriptExecutor)driver).ExecuteScript(
            "const select = document.getElementById(arguments[0]); const text = arguments[1].toLowerCase(); const option = Array.from(select.options).find(o => (o.textContent || '').toLowerCase().includes(text)); if (!option) throw new Error('option not found: ' + arguments[1]); select.value = option.value; select.dispatchEvent(new Event('input', { bubbles: true })); select.dispatchEvent(new Event('change', { bubbles: true }));",
            elementId,
            text
        );
    }

    public static string SelectFirstNonEmptyOption(IWebDriver driver, string elementId)
    {
        ProjectInformationTestHelper.WaitUntil(driver, "список " + elementId + " загружен", d => d.FindElements(By.CssSelector("#" + elementId + " option")).Count > 1);

        IWebElement select = driver.FindElement(By.Id(elementId));
        IWebElement? option = select.FindElements(By.TagName("option")).FirstOrDefault(item => !string.IsNullOrWhiteSpace(item.GetAttribute("value")));
        if (option == null)
        {
            throw new InvalidOperationException("В списке " + elementId + " нет доступных значений.");
        }

        ProjectInformationTestHelper.SetInputValue(driver, elementId, option.GetAttribute("value")!);
        return option.Text;
    }
}

public static class AddPostPayoutPolicyTest
{
    public static PostPolicyTestData Run()
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string suffix = DateTime.UtcNow.ToString("yyyyMMddHHmmss");
        string postName = "Selenium Post " + suffix;
        string value = "101";

        PayoutPoliciesTestHelper.ChoosePolicyType(driver, "post");
        ProjectInformationTestHelper.SetInputValue(driver, "postPolicyValueInput", value);
        driver.FindElement(By.Id("postPolicyPostInput")).SendKeys(postName);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#byPostTableBody", value, postName);
        return new PostPolicyTestData(postName, value);
    }
}

public static class EditPostPayoutPolicyTest
{
    public static void Run(PostPolicyTestData policy)
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string newPostName = policy.PostName + " Edited";
        string newValue = "202";

        IWebElement row = PayoutPoliciesTestHelper.FindRow(driver, "#byPostTableBody", policy.Value, policy.PostName);
        PayoutPoliciesTestHelper.ClickEdit(row);
        ProjectInformationTestHelper.SetInputValue(driver, "postPolicyValueInput", newValue);
        ProjectInformationTestHelper.SetInputValue(driver, "postPolicyPostInput", newPostName);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#byPostTableBody", newValue, newPostName);
    }
}

public static class AddExperiencePayoutPolicyTest
{
    public static ExperiencePolicyTestData Run()
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string workExperience = DateTime.UtcNow.ToString("ss");
        string value = "303";

        PayoutPoliciesTestHelper.ChoosePolicyType(driver, "experience");
        ProjectInformationTestHelper.SetInputValue(driver, "experiencePolicyValueInput", value);
        ProjectInformationTestHelper.SetInputValue(driver, "experiencePolicyTermInput", workExperience);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#byExperienceTableBody", value, workExperience);
        return new ExperiencePolicyTestData(workExperience, value);
    }
}

public static class EditExperiencePayoutPolicyTest
{
    public static void Run(ExperiencePolicyTestData policy)
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string newWorkExperience = (int.Parse(policy.WorkExperience) + 100).ToString();
        string newValue = "404";

        IWebElement row = PayoutPoliciesTestHelper.FindRow(driver, "#byExperienceTableBody", policy.Value, policy.WorkExperience);
        PayoutPoliciesTestHelper.ClickEdit(row);
        ProjectInformationTestHelper.SetInputValue(driver, "experiencePolicyValueInput", newValue);
        ProjectInformationTestHelper.SetInputValue(driver, "experiencePolicyTermInput", newWorkExperience);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#byExperienceTableBody", newValue, newWorkExperience);
    }
}

public static class AddBonusPayoutPolicyTest
{
    public static BonusPolicyTestData Run()
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string payoutType = "Selenium Bonus " + DateTime.UtcNow.ToString("yyyyMMddHHmmss");
        string value = "505";

        PayoutPoliciesTestHelper.ChoosePolicyType(driver, "bonus");
        ProjectInformationTestHelper.SetInputValue(driver, "bonusPolicyValueInput", value);
        driver.FindElement(By.Id("bonusPolicyReasonInput")).SendKeys(payoutType);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#bonusTableBody", value, payoutType);
        return new BonusPolicyTestData(payoutType, value);
    }
}

public static class EditBonusPayoutPolicyTest
{
    public static void Run(BonusPolicyTestData policy)
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string newValue = "606";

        IWebElement row = PayoutPoliciesTestHelper.FindRow(driver, "#bonusTableBody", policy.Value, policy.PayoutType);
        PayoutPoliciesTestHelper.ClickEdit(row);
        ProjectInformationTestHelper.SetInputValue(driver, "bonusPolicyValueInput", newValue);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#bonusTableBody", newValue, policy.PayoutType);
    }
}

public static class AddRolePayoutPolicyTest
{
    public static RolePolicyTestData Run(ProjectTestData project)
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string value = "707";

        PayoutPoliciesTestHelper.ChoosePolicyType(driver, "project");
        ProjectInformationTestHelper.SetInputValue(driver, "projectPolicyValueInput", value);
        PayoutPoliciesTestHelper.SelectOptionByText(driver, "projectPolicyProjectSelect", project.Name);
        string roleName = PayoutPoliciesTestHelper.SelectFirstNonEmptyOption(driver, "projectPolicyRoleSelect");
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.SelectOptionByText(driver, "projectFilterSelect", project.Name);
        PayoutPoliciesTestHelper.FindRow(driver, "#byProjectTableBody", value, roleName);
        return new RolePolicyTestData(project.Name, roleName, value);
    }
}

public static class EditRolePayoutPolicyTest
{
    public static void Run(RolePolicyTestData policy)
    {
        using IWebDriver driver = PayoutPoliciesTestHelper.OpenPayoutPolicies();
        string newValue = "808";

        PayoutPoliciesTestHelper.SelectOptionByText(driver, "projectFilterSelect", policy.ProjectName);
        IWebElement row = PayoutPoliciesTestHelper.FindRow(driver, "#byProjectTableBody", policy.Value, policy.RoleName);
        PayoutPoliciesTestHelper.ClickEdit(row);
        ProjectInformationTestHelper.SetInputValue(driver, "projectPolicyValueInput", newValue);
        PayoutPoliciesTestHelper.ClickSubmit(driver);

        PayoutPoliciesTestHelper.FindRow(driver, "#byProjectTableBody", newValue, policy.RoleName);
    }
}

public sealed record PostPolicyTestData(string PostName, string Value);
public sealed record ExperiencePolicyTestData(string WorkExperience, string Value);
public sealed record BonusPolicyTestData(string PayoutType, string Value);
public sealed record RolePolicyTestData(string ProjectName, string RoleName, string Value);
