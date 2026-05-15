EmployeeTestData? createdEmployee = null;
ProjectTestData? createdProject = null;

RunTest("AddEmployeeTest", () =>
{
    createdEmployee = AddEmployeeTest.Run();
});

RunTest("FilterEmployeesTest", () =>
{
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }

    FilterEmployeesTest.Run(createdEmployee);
});

RunTest("CreateProjectTest", () =>
{
    createdProject = CreateProjectTest.Run();
});

RunTest("FilterProjectsTest", () =>
{
    if (createdProject == null)
    {
        throw new InvalidOperationException("Нет данных проекта из CreateProjectTest.");
    }

    FilterProjectsTest.Run(createdProject);
});

RunTest("AddProjectEmployeeTest", () =>
{
    if (createdProject == null)
    {
        throw new InvalidOperationException("Нет данных проекта из CreateProjectTest.");
    }
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }

    AddProjectEmployeeTest.Run(createdProject, createdEmployee);
});

RunTest("DeleteProjectEmployeeTest", () =>
{
    if (createdProject == null)
    {
        throw new InvalidOperationException("Нет данных проекта из CreateProjectTest.");
    }
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }

    DeleteProjectEmployeeTest.Run(createdProject, createdEmployee);
});

RunTest("EditProjectInformationTest", () =>
{
    if (createdProject == null)
    {
        throw new InvalidOperationException("Нет данных проекта из CreateProjectTest.");
    }

    EditProjectInformationTest.Run(createdProject);
});

static void RunTest(string testName, Action action)
{
    try
    {
        action();
        Console.WriteLine(testName + " OK ✓");
    }
    catch
    {
        Console.WriteLine(testName + " NOT OK ✗");
        throw;
    }
}
