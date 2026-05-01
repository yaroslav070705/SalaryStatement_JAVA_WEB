<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список работников</title>
    <style>
        :root {
            --bg-body: #f5f7fa;
            --bg-card: #ffffff;
            --text-main: #2d3436;
            --text-muted: #636e72;
            --primary: #0984e3;
            --primary-hover: #0671c2;
            --accent: #00b894;
            --accent-hover: #009975;
            --border: #dfe6e9;
            --shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
            --radius: 8px;
        }

        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: system-ui, -apple-system, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
            background-color: var(--bg-body);
            color: var(--text-main);
            min-height: 100vh;
        }

        .btn-menu {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 100;
            width: 44px;
            height: 40px;
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            cursor: pointer;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            gap: 4px;
            box-shadow: var(--shadow);
            transition: background 0.2s;
        }
        .btn-menu:hover { background: #f1f3f5; }
        .btn-menu span {
            width: 20px;
            height: 2px;
            background-color: var(--text-main);
            border-radius: 2px;
        }
        .menu-panel {
            position: fixed;
            top: 68px;
            left: 20px;
            z-index: 120;
            width: 220px;
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 8px;
            display: none;
        }
        .menu-panel.active {
            display: block;
        }
        .menu-item {
            width: 100%;
            border: 1px solid transparent;
            border-radius: 8px;
            background: #fff;
            color: var(--text-main);
            text-align: left;
            padding: 10px 12px;
            cursor: pointer;
            font-size: 14px;
        }
        .menu-item:hover {
            background: #f1f3f5;
            border-color: var(--border);
        }

        .btn-add {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 100;
            padding: 10px 18px;
            background: var(--primary);
            color: #fff;
            border: none;
            border-radius: var(--radius);
            font-weight: 500;
            cursor: pointer;
            box-shadow: var(--shadow);
            transition: background 0.2s;
        }
        .btn-add:hover { background: var(--primary-hover); }

        .create-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.35);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 300;
            padding: 16px;
        }
        .create-overlay.active {
            display: flex;
        }
        .create-panel {
            width: 100%;
            max-width: 520px;
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 16px;
        }
        .create-title {
            font-size: 20px;
            font-weight: 600;
            margin-bottom: 12px;
        }
        .create-fields {
            display: grid;
            gap: 10px;
            margin-bottom: 14px;
        }
        .create-field {
            display: grid;
            gap: 6px;
        }
        .create-field label {
            font-size: 13px;
            color: var(--text-muted);
        }
        .create-field input,
        .create-field select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid var(--border);
            border-radius: 8px;
            font-size: 14px;
            color: var(--text-main);
            background: #fff;
        }
        .create-actions {
            display: flex;
            gap: 10px;
        }
        .create-actions .btn {
            flex: 1;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 10px 12px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            background: #fff;
            color: var(--text-main);
        }
        .create-actions .btn-primary {
            background: var(--primary);
            border-color: var(--primary);
            color: #fff;
        }
        .create-actions .btn-primary:hover {
            background: var(--primary-hover);
        }

        .layout {
            display: flex;
            min-height: 100vh;
            padding-top: 80px;
        }

        .sidebar {
            width: 240px;
            flex-shrink: 0;
            padding: 0 20px;
            display: flex;
            align-items: flex-start;
        }
        .filters-wrap {
            width: 460px;
            max-width: 100%;
            position: relative;
            margin-top: 56px;
        }
        .btn-filter {
            width: 100%;
            padding: 14px;
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: 0.2s;
        }
        .btn-filter:hover { background: #f1f3f5; }
        .filters-panel {
            position: absolute;
            top: calc(100% + 8px);
            left: 0;
            width: 520px;
            max-width: calc(100vw - 48px);
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 12px;
            display: none;
            z-index: 130;
        }
        .filters-panel.active {
            display: block;
        }
        .filters-fields {
            display: grid;
            gap: 10px;
        }
        .filters-row {
            display: grid;
            grid-template-columns: 150px minmax(0, 1fr);
            gap: 8px;
            align-items: center;
        }
        .filters-row label {
            font-size: 13px;
            color: var(--text-main);
        }
        .filters-row input,
        .filters-row select {
            width: 100%;
            min-width: 0;
            padding: 8px 10px;
            border: 1px solid var(--border);
            border-radius: 8px;
            font-size: 13px;
            background: #fff;
            color: var(--text-main);
        }
        .filters-actions {
            margin-top: 10px;
            display: flex;
            justify-content: flex-end;
        }
        .btn-apply-filters {
            padding: 8px 14px;
            border: 1px solid var(--primary);
            border-radius: 8px;
            background: var(--primary);
            color: #fff;
            cursor: pointer;
            font-size: 13px;
            font-weight: 600;
        }
        .btn-apply-filters:hover {
            background: var(--primary-hover);
        }

        .content {
            flex: 1;
            padding: 0 30px 40px;
        }

        .page-title {
            text-align: right;
            font-size: 26px;
            font-weight: 600;
            margin-bottom: 20px;
            color: var(--text-main);
        }
        .workers-count {
            width: 12.5%;
            min-width: 180px;
            margin-left: auto;
            margin-bottom: 10px;
            font-size: 14px;
            color: var(--text-main);
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            padding: 8px 12px;
            box-shadow: var(--shadow);
        }

        .data-table {
            width: 100%;
            border-collapse: collapse;
            background: var(--bg-card);
            border-radius: var(--radius);
            overflow: hidden;
            box-shadow: var(--shadow);
        }
        .table-scroll {
            width: 66.67%;
            margin-left: auto;
            max-height: 330px;
            overflow-y: auto;
            border-radius: var(--radius);
            box-shadow: var(--shadow);
        }
        .data-table th, .data-table td {
            padding: 14px 16px;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }
        .data-table th {
            background: #f8f9fa;
            font-weight: 600;
            color: var(--text-muted);
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            position: sticky;
            top: 0;
            z-index: 2;
        }
        .data-table tr:last-child td { border-bottom: none; }
        .data-table tbody tr:hover { background: #fcfcfc; }

        .emp-btn {
            display: inline-block;
            padding: 6px 12px;
            background: var(--accent);
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 13px;
            cursor: pointer;
            transition: 0.2s;
        }
        .emp-btn:hover { background: var(--accent-hover); }

        .request-result {
            margin-top: 20px;
            padding: 16px;
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            white-space: pre-wrap;
            word-break: break-word;
        }

        @media (max-width: 768px) {
            .layout { flex-direction: column; padding-top: 70px; }
            .sidebar { width: 100%; padding: 10px 20px; align-items: flex-start; }
            .filters-wrap { width: 100%; }
            .filters-row { grid-template-columns: 1fr; }
            .content { padding: 10px 16px 30px; }
            .page-title { text-align: left; }
            .workers-count { width: 100%; }
            .table-scroll { width: 100%; }
            .data-table th, .data-table td { padding: 10px 12px; font-size: 14px; }
        }
    </style>
</head>
<body>

    <button class="btn-menu" id="menuBtn" aria-label="Открыть главное меню">
        <span></span>
        <span></span>
        <span></span>
    </button>
    <div class="menu-panel" id="menuPanel">
        <button class="menu-item" type="button">Работники</button>
        <button class="menu-item" id="menuProjectsBtn" type="button">Проекты</button>
        <button class="menu-item" id="menuPoliciesBtn" type="button">Политики выплат</button>
    </div>

    <button class="btn-add" id="addEmployeeBtn">Добавить работника</button>

    <div class="create-overlay" id="createOverlay">
        <div class="create-panel">
            <h2 class="create-title">Добавить работника</h2>
            <div class="create-fields">
                <div class="create-field">
                    <label for="createNameInput">Имя</label>
                    <input id="createNameInput" type="text">
                </div>
                <div class="create-field">
                    <label for="createSurnameInput">Фамилия</label>
                    <input id="createSurnameInput" type="text">
                </div>
                <div class="create-field">
                    <label for="createMiddleNameInput">Отчество</label>
                    <input id="createMiddleNameInput" type="text">
                </div>
                <div class="create-field">
                    <label for="createBirthDateInput">Дата рождения</label>
                    <input id="createBirthDateInput" type="date">
                </div>
                <div class="create-field">
                    <label for="createEducationInput">Образование</label>
                    <input id="createEducationInput" type="text">
                </div>
                <div class="create-field">
                    <label for="createPostInput">Должность</label>
                    <select id="createPostInput"></select>
                </div>
            </div>
            <div class="create-actions">
                <button class="btn" id="cancelCreateBtn" type="button">Отменить</button>
                <button class="btn btn-primary" id="submitCreateBtn" type="button">Добавить</button>
            </div>
        </div>
    </div>

    <main class="layout">
        <aside class="sidebar">
            <div class="filters-wrap">
                <button class="btn-filter" id="toggleFiltersBtn" type="button">Фильтры</button>
                <div class="filters-panel" id="filtersPanel">
                    <div class="filters-fields">
                        <div class="filters-row">
                            <label for="filterNameInput">Имя</label>
                            <input id="filterNameInput" type="text">
                        </div>
                        <div class="filters-row">
                            <label for="filterSurnameInput">Фамилия</label>
                            <input id="filterSurnameInput" type="text">
                        </div>
                        <div class="filters-row">
                            <label for="filterMiddleNameInput">Отчество</label>
                            <input id="filterMiddleNameInput" type="text">
                        </div>
                        <div class="filters-row">
                            <label for="filterBirthDateInput">Дата рождения</label>
                            <input id="filterBirthDateInput" type="date">
                        </div>
                        <div class="filters-row">
                            <label for="filterPostSelect">Должность</label>
                            <select id="filterPostSelect"></select>
                        </div>
                        <div class="filters-row">
                            <label for="filterProjectSelect">Проект</label>
                            <select id="filterProjectSelect"></select>
                        </div>
                        <div class="filters-row">
                            <label for="filterRoleSelect">Роль в проекте</label>
                            <select id="filterRoleSelect"></select>
                        </div>
                        <div class="filters-row">
                            <label for="filterBonusSelect">Премирован</label>
                            <select id="filterBonusSelect">
                                <option value="">Не выбрано</option>
                                <option value="true">Да</option>
                                <option value="false">Нет</option>
                            </select>
                        </div>
                    </div>
                    <div class="filters-actions">
                        <button class="btn-apply-filters" id="applyFiltersBtn" type="button">Применить</button>
                    </div>
                </div>
            </div>
        </aside>

        <section class="content">
            <h1 class="page-title">Список работников</h1>
            <div class="workers-count" id="workersCount">Количество работников: 0</div>

            <div class="table-scroll">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ФИО</th>
                            <th>Должность</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody id="employeesTableBody">
                    </tbody>
                </table>
            </div>

            <div id="requestResult" class="request-result">Здесь будет результат GET /employees</div>
        </section>
    </main>

    <script>
        const menuBtn = document.getElementById("menuBtn");
        const menuPanel = document.getElementById("menuPanel");
        const menuProjectsBtn = document.getElementById("menuProjectsBtn");
        const menuPoliciesBtn = document.getElementById("menuPoliciesBtn");
        const addEmployeeBtn = document.getElementById("addEmployeeBtn");
        const createOverlay = document.getElementById("createOverlay");
        const toggleFiltersBtn = document.getElementById("toggleFiltersBtn");
        const filtersPanel = document.getElementById("filtersPanel");
        const cancelCreateBtn = document.getElementById("cancelCreateBtn");
        const submitCreateBtn = document.getElementById("submitCreateBtn");
        const createPostInput = document.getElementById("createPostInput");
        const requestResult = document.getElementById("requestResult");
        const workersCount = document.getElementById("workersCount");
        const employeesTableBody = document.getElementById("employeesTableBody");
        const contextPath = "${pageContext.request.contextPath}";
        let postsLoaded = false;

        employeesTableBody.addEventListener("click", function (event) {
            const btn = event.target.closest(".emp-btn");
            if (!btn) {
                return;
            }

            const employeeId = btn.dataset.id || "";
            const url = contextPath + "/EmployeeInformation.jsp" +
                (employeeId ? ("?employeeId=" + encodeURIComponent(employeeId)) : "");
            window.location.href = url;
        });

        if (menuBtn && menuPanel) {
            menuBtn.addEventListener("click", function (event) {
                event.stopPropagation();
                if (filtersPanel) {
                    filtersPanel.classList.remove("active");
                }
                menuPanel.classList.toggle("active");
            });

            document.addEventListener("click", function (event) {
                if (!menuPanel.contains(event.target) && !menuBtn.contains(event.target)) {
                    menuPanel.classList.remove("active");
                }
            });
        }

        if (menuProjectsBtn) {
            menuProjectsBtn.addEventListener("click", function () {
                window.location.href = contextPath + "/Projects.jsp";
            });
        }

        if (menuPoliciesBtn) {
            menuPoliciesBtn.addEventListener("click", function () {
                window.location.href = contextPath + "/PayoutsPolicy.jsp";
            });
        }

        if (toggleFiltersBtn && filtersPanel) {
            toggleFiltersBtn.addEventListener("click", function (event) {
                event.stopPropagation();
                filtersPanel.classList.toggle("active");
            });

            document.addEventListener("click", function (event) {
                if (!filtersPanel.contains(event.target) && !toggleFiltersBtn.contains(event.target)) {
                    filtersPanel.classList.remove("active");
                }
            });
        }

        const loadEmployees = async function () {
            requestResult.textContent = "Загрузка...";
            requestResult.style.display = "block";

            try {
                const response = await fetch(contextPath + "/employees", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP: " + response.status);
                }

                const employees = await response.json();
                requestResult.style.display = "none";

                employeesTableBody.innerHTML = "";
                if (workersCount) {
                    workersCount.textContent = "Количество работников: " + (Array.isArray(employees) ? employees.length : 0);
                }

                if (!Array.isArray(employees) || employees.length === 0) {
                    employeesTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Сотрудники не найдены</td></tr>";
                    return;
                }

                const asText = (value) => {
                    if (typeof value === "string") {
                        const trimmed = value.trim();
                        return trimmed.length > 0 ? trimmed : "";
                    }
                    if (typeof value === "number") {
                        return String(value);
                    }
                    return "";
                };

                const firstText = (...values) => {
                    for (const value of values) {
                        const text = asText(value);
                        if (text) return text;
                    }
                    return "";
                };

                employees.forEach(emp => {
                    const tr = document.createElement("tr");
                    const name = firstText(emp.name, emp.firstName, emp.first_name);
                    const surname = firstText(emp.surname, emp.lastName, emp.last_name);
                    const middleName = firstText(emp.middleName, emp.middle_name, emp.patronymic);
                    const fullName = [surname, name, middleName]
                        .filter(part => part.length > 0)
                        .join(" ");
                    const postLabel = firstText(
                        emp.postName
                    );
                    const buttonLabel = "Перейти";
                    const employeeId = asText(emp.employeeId || emp.id);

                    tr.innerHTML =
                        "<td>" + (fullName || "—") + "</td>" +
                        "<td>" + (postLabel || "—") + "</td>" +
                        "<td>" +
                            "<button class=\"emp-btn\" data-id=\"" + employeeId + "\">" +
                                buttonLabel +
                            "</button>" +
                        "</td>";
                    employeesTableBody.appendChild(tr);
                });

            } catch (error) {
                console.error("Ошибка загрузки:", error);
                if (workersCount) {
                    workersCount.textContent = "Количество работников: 0";
                }
                requestResult.textContent = "Ошибка: " + error.message;
                requestResult.style.display = "block";
            }
        };

        const loadPostsForCreate = async function () {
            if (postsLoaded) {
                return;
            }
            const response = await fetch(contextPath + "/posts", {
                method: "GET",
                headers: { "Accept": "application/json" }
            });
            if (!response.ok) {
                throw new Error("Ошибка HTTP (posts): " + response.status);
            }

            const posts = await response.json();
            createPostInput.innerHTML = "<option value=\"\">Выберите должность</option>";
            (Array.isArray(posts) ? posts : []).forEach(post => {
                const option = document.createElement("option");
                option.value = post.id || post.postId || post.post_id || "";
                option.textContent = post.postName || post.post_name || "Без названия";
                createPostInput.appendChild(option);
            });
            postsLoaded = true;
        };

        const resetCreateForm = function () {
            const nameInput = document.getElementById("createNameInput");
            const surnameInput = document.getElementById("createSurnameInput");
            const middleNameInput = document.getElementById("createMiddleNameInput");
            const birthDateInput = document.getElementById("createBirthDateInput");
            const educationInput = document.getElementById("createEducationInput");

            if (nameInput) nameInput.value = "";
            if (surnameInput) surnameInput.value = "";
            if (middleNameInput) middleNameInput.value = "";
            if (birthDateInput) birthDateInput.value = "";
            if (educationInput) educationInput.value = "";
            if (createPostInput) createPostInput.value = "";
        };

        if (addEmployeeBtn) {
            addEmployeeBtn.addEventListener("click", async function () {
                try {
                    await loadPostsForCreate();
                } catch (e) {
                    console.error("Ошибка загрузки должностей:", e);
                }
                resetCreateForm();
                createOverlay.classList.add("active");
            });
        }

        if (cancelCreateBtn) {
            cancelCreateBtn.addEventListener("click", function () {
                createOverlay.classList.remove("active");
            });
        }

        if (submitCreateBtn) {
            submitCreateBtn.addEventListener("click", async function () {
                const nameInput = document.getElementById("createNameInput");
                const surnameInput = document.getElementById("createSurnameInput");
                const middleNameInput = document.getElementById("createMiddleNameInput");
                const birthDateInput = document.getElementById("createBirthDateInput");
                const educationInput = document.getElementById("createEducationInput");

                const payload = {
                    name: nameInput ? nameInput.value : "",
                    surname: surnameInput ? surnameInput.value : "",
                    middleName: middleNameInput ? middleNameInput.value : "",
                    birthDate: birthDateInput && birthDateInput.value ? birthDateInput.value : null,
                    education: educationInput ? educationInput.value : "",
                    postId: createPostInput && createPostInput.value ? createPostInput.value : null,
                    workExperience: 0,
                    fired: false
                };

                try {
                    const response = await fetch(contextPath + "/employees/", {
                        method: "POST",
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(payload)
                    });

                    if (!response.ok) {
                        throw new Error("Ошибка HTTP (create employee): " + response.status);
                    }

                    createOverlay.classList.remove("active");
                    await loadEmployees();
                } catch (e) {
                    console.error("Ошибка добавления сотрудника:", e);
                    requestResult.textContent = "Ошибка: " + e.message;
                    requestResult.style.display = "block";
                }
            });
        }

        loadEmployees();
    </script>

</body>
</html>
