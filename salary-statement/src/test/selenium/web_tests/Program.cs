EmployeeTestData? createdEmployee = null;
ProjectTestData? createdProject = null;
PostPolicyTestData? createdPostPolicy = null;
ExperiencePolicyTestData? createdExperiencePolicy = null;
BonusPolicyTestData? createdBonusPolicy = null;
RolePolicyTestData? createdRolePolicy = null;
RoleAssignmentTestData? createdRoleAssignment = null;

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

RunTest("AddPostPayoutPolicyTest", () =>
{
    createdPostPolicy = AddPostPayoutPolicyTest.Run();
});

RunTest("EditPostPayoutPolicyTest", () =>
{
    if (createdPostPolicy == null)
    {
        throw new InvalidOperationException("Нет данных политики должности из AddPostPayoutPolicyTest.");
    }

    EditPostPayoutPolicyTest.Run(createdPostPolicy);
});

RunTest("AddExperiencePayoutPolicyTest", () =>
{
    createdExperiencePolicy = AddExperiencePayoutPolicyTest.Run();
});

RunTest("EditExperiencePayoutPolicyTest", () =>
{
    if (createdExperiencePolicy == null)
    {
        throw new InvalidOperationException("Нет данных политики стажа из AddExperiencePayoutPolicyTest.");
    }

    EditExperiencePayoutPolicyTest.Run(createdExperiencePolicy);
});

RunTest("AddBonusPayoutPolicyTest", () =>
{
    createdBonusPolicy = AddBonusPayoutPolicyTest.Run();
});

RunTest("EditBonusPayoutPolicyTest", () =>
{
    if (createdBonusPolicy == null)
    {
        throw new InvalidOperationException("Нет данных премиальной политики из AddBonusPayoutPolicyTest.");
    }

    EditBonusPayoutPolicyTest.Run(createdBonusPolicy);
});

RunTest("AddRolePayoutPolicyTest", () =>
{
    if (createdProject == null)
    {
        throw new InvalidOperationException("Нет данных проекта из CreateProjectTest.");
    }

    createdRolePolicy = AddRolePayoutPolicyTest.Run(createdProject);
});

RunTest("EditRolePayoutPolicyTest", () =>
{
    if (createdRolePolicy == null)
    {
        throw new InvalidOperationException("Нет данных политики проекта и роли из AddRolePayoutPolicyTest.");
    }

    EditRolePayoutPolicyTest.Run(createdRolePolicy);
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

    createdRoleAssignment = AddProjectEmployeeTest.Run(createdProject, createdEmployee);
});

RunTest("EmployeeInformationPostHistoryTest", () =>
{
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }

    EmployeeInformationPostHistoryTest.Run(createdEmployee);
});

RunTest("EmployeeInformationProjectsTableTest", () =>
{
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }
    if (createdRoleAssignment == null)
    {
        throw new InvalidOperationException("Нет данных назначения в проект из AddProjectEmployeeTest.");
    }

    EmployeeInformationProjectsTableTest.Run(createdEmployee, createdRoleAssignment);
});

RunTest("EmployeeInformationRolesHistoryTest", () =>
{
    if (createdEmployee == null)
    {
        throw new InvalidOperationException("Нет данных работника из AddEmployeeTest.");
    }
    if (createdRoleAssignment == null)
    {
        throw new InvalidOperationException("Нет данных назначения в проект из AddProjectEmployeeTest.");
    }

    EmployeeInformationRolesHistoryTest.Run(createdEmployee, createdRoleAssignment);
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
