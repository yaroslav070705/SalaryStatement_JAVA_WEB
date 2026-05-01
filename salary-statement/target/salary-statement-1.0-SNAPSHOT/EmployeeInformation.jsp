<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Информация о сотруднике</title>
    <style>
        :root {
            --bg: #eef2f7;
            --card: #ffffff;
            --text: #1f2937;
            --muted: #6b7280;
            --border: #d9e1ea;
            --accent: #0f766e;
            --shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
            --radius: 10px;
        }

        * { box-sizing: border-box; }

        body {
            margin: 0;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(180deg, #f6f9fc 0%, var(--bg) 100%);
            color: var(--text);
        }

        .page {
            max-width: 1400px;
            margin: 0 auto;
            padding: 24px;
            display: grid;
            grid-template-columns: 1fr 1fr;
            grid-template-areas:
                "left topRight"
                "bottom bottom";
            gap: 20px;
            min-height: 100vh;
        }

        .left-panel {
            grid-area: left;
        }

        .top-right {
            grid-area: topRight;
        }

        .bottom-grid {
            grid-area: bottom;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .table-card {
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .table-title {
            margin: 0;
            padding: 14px 16px;
            background: #f8fafc;
            border-bottom: 1px solid var(--border);
            font-size: 16px;
            font-weight: 600;
            color: var(--accent);
        }

        .table-head {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: #f8fafc;
            border-bottom: 1px solid var(--border);
            padding-right: 10px;
        }

        .table-head .table-title {
            border-bottom: none;
            background: transparent;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        .table-scroll {
            max-height: 238px;
            overflow-y: auto;
        }

        th, td {
            padding: 10px 12px;
            border-bottom: 1px solid var(--border);
            text-align: left;
            font-size: 14px;
        }

        th {
            color: var(--muted);
            font-weight: 600;
            background: #fcfdff;
            position: sticky;
            top: 0;
            z-index: 1;
        }

        tr:last-child td {
            border-bottom: none;
        }

        .placeholder {
            color: var(--muted);
        }

        .profile-card {
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 16px;
            align-self: start;
        }

        .profile-fields {
            display: grid;
            gap: 12px;
            margin-bottom: 16px;
        }

        .profile-field {
            display: grid;
            gap: 4px;
            padding: 10px 12px;
            border: 1px solid var(--border);
            border-radius: 8px;
            background: #fcfdff;
        }

        .field-label {
            font-size: 12px;
            color: var(--muted);
            text-transform: uppercase;
            letter-spacing: 0.3px;
        }

        .field-value {
            font-size: 15px;
            font-weight: 600;
        }

        .field-input {
            display: none;
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 6px;
            padding: 8px 10px;
            font-size: 14px;
            color: var(--text);
            background: #ffffff;
        }

        .profile-card.edit-mode .field-value {
            display: none;
        }

        .profile-card.edit-mode .field-input {
            display: block;
        }

        .profile-actions {
            display: flex;
            gap: 10px;
        }

        .btn {
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 10px 14px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            background: #ffffff;
            color: var(--text);
        }

        .btn:disabled {
            opacity: 0.55;
            cursor: not-allowed;
        }

        .btn-primary {
            background: #0f766e;
            color: #ffffff;
            border-color: #0f766e;
        }

        .btn-filter {
            padding: 8px 12px;
            font-size: 13px;
            border: 1px solid var(--border);
            border-radius: 8px;
            background: #ffffff;
            color: var(--text);
            cursor: pointer;
        }

        .delete-panel {
            position: fixed;
            top: 16px;
            right: 16px;
            z-index: 200;
            padding: 0;
            background: transparent;
            border: 1px solid transparent;
            border-radius: 10px;
            box-shadow: none;
            min-width: 220px;
        }

        .delete-panel.active {
            background: #ffffff;
            border: 1px solid var(--border);
            padding: 10px;
            box-shadow: var(--shadow);
        }

        .btn-delete {
            background: #b42318;
            color: #ffffff;
            border: 1px solid #b42318;
            border-radius: 8px;
            padding: 10px 14px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
        }

        .delete-actions {
            display: none;
            gap: 8px;
            margin-top: 8px;
        }

        .delete-panel.active .delete-actions {
            display: flex;
        }

        .delete-actions .btn {
            flex: 1;
        }

        .btn-menu {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 220;
            width: 44px;
            height: 40px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 8px;
            cursor: pointer;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            gap: 4px;
            box-shadow: var(--shadow);
            transition: background 0.2s;
        }
        .btn-menu:hover {
            background: #f1f3f5;
        }
        .btn-menu span {
            width: 20px;
            height: 2px;
            background-color: var(--text);
            border-radius: 2px;
        }
        .menu-panel {
            position: fixed;
            top: 68px;
            left: 20px;
            z-index: 230;
            width: 220px;
            background: var(--card);
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
            color: var(--text);
            text-align: left;
            padding: 10px 12px;
            cursor: pointer;
            font-size: 14px;
        }
        .menu-item:hover {
            background: #f1f3f5;
            border-color: var(--border);
        }

        @media (max-width: 900px) {
            .page {
                grid-template-columns: 1fr;
                grid-template-areas:
                    "left"
                    "topRight"
                    "bottom";
            }

            .bottom-grid {
                grid-template-columns: 1fr;
            }

            .profile-actions {
                flex-wrap: wrap;
            }
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
    <button class="menu-item" id="menuWorkersBtn" type="button">Работники</button>
    <button class="menu-item" id="menuProjectsBtn" type="button">Проекты</button>
    <button class="menu-item" id="menuPoliciesBtn" type="button">Политики выплат</button>
</div>
<div class="delete-panel" id="deletePanel">
    <button class="btn-delete" id="deleteEmployeeBtn" type="button">Удалить работника</button>
    <div class="delete-actions">
        <button class="btn" id="confirmDeleteBtn" type="button">Подтвердить</button>
        <button class="btn" id="cancelDeleteBtn" type="button">Отменить</button>
    </div>
</div>
<main class="page">
    <section class="profile-card left-panel">
        <div class="profile-fields">
            <div class="profile-field">
                <span class="field-label">Фамилия</span>
                <span class="field-value placeholder" id="employeeSurname">Нет данных</span>
                <input class="field-input" id="employeeSurnameInput" type="text">
            </div>
            <div class="profile-field">
                <span class="field-label">Имя</span>
                <span class="field-value placeholder" id="employeeName">Нет данных</span>
                <input class="field-input" id="employeeNameInput" type="text">
            </div>
            <div class="profile-field">
                <span class="field-label">Отчество</span>
                <span class="field-value placeholder" id="employeeMiddleName">Нет данных</span>
                <input class="field-input" id="employeeMiddleNameInput" type="text">
            </div>
            <div class="profile-field">
                <span class="field-label">Дата Рождения</span>
                <span class="field-value placeholder" id="employeeBirthDate">Нет данных</span>
                <input class="field-input" id="employeeBirthDateInput" type="date">
            </div>
            <div class="profile-field">
                <span class="field-label">Должность</span>
                <span class="field-value placeholder" id="employeePost">Нет данных</span>
                <select class="field-input" id="employeePostInput"></select>
            </div>
            <div class="profile-field">
                <span class="field-label">Стаж</span>
                <span class="field-value placeholder" id="employeeExperience">Нет данных</span>
                <input class="field-input" id="employeeExperienceInput" type="number" min="0">
            </div>
        </div>
        <div class="profile-actions">
            <button class="btn" id="editEmployeeBtn" type="button">редакитировать</button>
            <button class="btn btn-primary" id="saveEmployeeBtn" type="button" disabled>сохранить</button>
        </div>
    </section>

    <section class="table-card top-right">
        <h2 class="table-title">Проекты</h2>
        <div class="table-scroll">
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Роль</th>
                </tr>
                </thead>
                <tbody id="projectsTableBody">
                <tr>
                    <td class="placeholder">Нет данных</td>
                    <td class="placeholder">-</td>
                </tr>
                </tbody>
            </table>
        </div>
    </section>

    <section class="bottom-grid">
        <article class="table-card">
            <h2 class="table-title">История Должностей</h2>
            <div class="table-scroll">
                <table>
                    <thead>
                    <tr>
                        <th>Должность</th>
                        <th>Дата с</th>
                        <th>Дата по</th>
                    </tr>
                    </thead>
                    <tbody id="postHistoryTableBody">
                    <tr>
                        <td class="placeholder">Нет данных</td>
                        <td class="placeholder">-</td>
                        <td class="placeholder">-</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>

        <article class="table-card">
            <div class="table-head">
                <h2 class="table-title">История Выплат</h2>
                <button class="btn-filter" type="button">Фильтры</button>
            </div>
            <div class="table-scroll">
                <table>
                    <thead>
                    <tr>
                        <th>Размер</th>
                        <th>Тип</th>
                        <th>Дата</th>
                    </tr>
                    </thead>
                    <tbody id="payoutsTableBody">
                    <tr>
                        <td class="placeholder">Нет данных</td>
                        <td class="placeholder">-</td>
                        <td class="placeholder">-</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>

        <article class="table-card">
            <h2 class="table-title">История ролей</h2>
            <div class="table-scroll">
                <table>
                    <thead>
                    <tr>
                        <th>Проект</th>
                        <th>Роль</th>
                        <th>Дата с</th>
                        <th>Дата по</th>
                    </tr>
                    </thead>
                    <tbody id="rolesHistoryTableBody">
                    <tr>
                        <td class="placeholder">Нет данных</td>
                        <td class="placeholder">-</td>
                        <td class="placeholder">-</td>
                        <td class="placeholder">-</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>

        <article class="table-card">
            <h2 class="table-title">Премии</h2>
            <div class="table-scroll">
                <table>
                    <thead>
                    <tr>
                        <th>Размер</th>
                        <th>Дата</th>
                    </tr>
                    </thead>
                    <tbody id="bonusPayoutsTableBody">
                    <tr>
                        <td class="placeholder">Нет данных</td>
                        <td class="placeholder">-</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </article>
    </section>
</main>
<script>
    (async function () {
        const contextPath = "${pageContext.request.contextPath}";
        const menuBtn = document.getElementById("menuBtn");
        const menuPanel = document.getElementById("menuPanel");
        const menuWorkersBtn = document.getElementById("menuWorkersBtn");
        const menuProjectsBtn = document.getElementById("menuProjectsBtn");
        const menuPoliciesBtn = document.getElementById("menuPoliciesBtn");
        const deletePanel = document.getElementById("deletePanel");
        const deleteEmployeeBtn = document.getElementById("deleteEmployeeBtn");
        const cancelDeleteBtn = document.getElementById("cancelDeleteBtn");
        const profileCard = document.querySelector(".profile-card");
        const editEmployeeBtn = document.getElementById("editEmployeeBtn");
        const saveEmployeeBtn = document.getElementById("saveEmployeeBtn");
        const params = new URLSearchParams(window.location.search);
        const employeeId = params.get("employeeId");
        let postsLoaded = false;
        let currentEmployee = null;

        const fields = [
            { valueId: "employeeSurname", inputId: "employeeSurnameInput" },
            { valueId: "employeeName", inputId: "employeeNameInput" },
            { valueId: "employeeMiddleName", inputId: "employeeMiddleNameInput" },
            { valueId: "employeeBirthDate", inputId: "employeeBirthDateInput" },
            { valueId: "employeeExperience", inputId: "employeeExperienceInput" }
        ];

        if (deleteEmployeeBtn && deletePanel) {
            deleteEmployeeBtn.addEventListener("click", function () {
                deletePanel.classList.add("active");
            });
        }

        if (cancelDeleteBtn && deletePanel) {
            cancelDeleteBtn.addEventListener("click", function () {
                deletePanel.classList.remove("active");
            });
        }

        if (menuBtn && menuPanel) {
            menuBtn.addEventListener("click", function (event) {
                event.stopPropagation();
                menuPanel.classList.toggle("active");
            });

            document.addEventListener("click", function (event) {
                if (!menuPanel.contains(event.target) && !menuBtn.contains(event.target)) {
                    menuPanel.classList.remove("active");
                }
            });
        }

        if (menuWorkersBtn) {
            menuWorkersBtn.addEventListener("click", function () {
                window.location.href = contextPath + "/index.jsp";
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

        const toDisplay = (value) => {
            return (value !== null && value !== undefined && String(value).trim().length > 0)
                ? String(value)
                : "Нет данных";
        };

        const setFieldValue = (valueId, inputId, value) => {
            const valueNode = document.getElementById(valueId);
            const inputNode = document.getElementById(inputId);
            if (valueNode) {
                valueNode.textContent = toDisplay(value);
            }
            if (inputNode) {
                inputNode.value = (value !== null && value !== undefined) ? String(value) : "";
            }
        };

        const loadPosts = async () => {
            if (postsLoaded) {
                return;
            }
            const select = document.getElementById("employeePostInput");
            if (!select) {
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
            select.innerHTML = "";
            (Array.isArray(posts) ? posts : []).forEach(post => {
                const option = document.createElement("option");
                option.value = post.id || post.postId || post.post_id || "";
                option.textContent = post.postName || post.post_name || "Без названия";
                select.appendChild(option);
            });
            if (currentEmployee && currentEmployee.postId) {
                select.value = String(currentEmployee.postId);
            }
            postsLoaded = true;
        };

        if (editEmployeeBtn && profileCard) {
            editEmployeeBtn.addEventListener("click", async function () {
                try {
                    await loadPosts();
                } catch (e) {
                    console.error("Ошибка загрузки списка должностей:", e);
                }
                profileCard.classList.add("edit-mode");
                editEmployeeBtn.disabled = true;
                if (saveEmployeeBtn) {
                    saveEmployeeBtn.disabled = false;
                }
            });
        }

        if (saveEmployeeBtn && profileCard) {
            saveEmployeeBtn.addEventListener("click", async function () {
                if (!employeeId) {
                    return;
                }

                const surnameInput = document.getElementById("employeeSurnameInput");
                const nameInput = document.getElementById("employeeNameInput");
                const middleNameInput = document.getElementById("employeeMiddleNameInput");
                const birthDateInput = document.getElementById("employeeBirthDateInput");
                const postInput = document.getElementById("employeePostInput");
                const experienceInput = document.getElementById("employeeExperienceInput");

                const payload = {
                    name: nameInput ? nameInput.value : "",
                    surname: surnameInput ? surnameInput.value : "",
                    middleName: middleNameInput ? middleNameInput.value : "",
                    birthDate: birthDateInput && birthDateInput.value ? birthDateInput.value : null,
                    workExperience: experienceInput && experienceInput.value ? parseInt(experienceInput.value, 10) : 0,
                    postId: postInput && postInput.value ? postInput.value : null,
                    fired: currentEmployee ? Boolean(currentEmployee.fired) : false
                };

                try {
                    const updateResponse = await fetch(contextPath + "/employees/" + encodeURIComponent(employeeId), {
                        method: "PUT",
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(payload)
                    });
                    if (!updateResponse.ok) {
                        throw new Error("Ошибка HTTP (update employee): " + updateResponse.status);
                    }

                    fields.forEach(field => {
                        const inputNode = document.getElementById(field.inputId);
                        if (!inputNode) {
                            return;
                        }
                        setFieldValue(field.valueId, field.inputId, inputNode.value);
                    });

                    if (postInput) {
                        const selectedOption = postInput.options[postInput.selectedIndex];
                        const postText = selectedOption ? selectedOption.text : "";
                        const postValue = document.getElementById("employeePost");
                        if (postValue) {
                            postValue.textContent = toDisplay(postText);
                        }
                        postInput.value = payload.postId || "";
                    }
                    currentEmployee = {
                        ...(currentEmployee || {}),
                        ...payload
                    };
                } catch (e) {
                    console.error("Ошибка сохранения сотрудника:", e);
                    return;
                }
                profileCard.classList.remove("edit-mode");
                saveEmployeeBtn.disabled = true;
                if (editEmployeeBtn) {
                    editEmployeeBtn.disabled = false;
                }
            });
        }

        if (!employeeId) {
            return;
        }

        const setText = (id, value) => {
            const node = document.getElementById(id);
            if (!node) {
                return;
            }
            node.textContent = (value !== null && value !== undefined && String(value).trim().length > 0)
                ? String(value)
                : "Нет данных";
            const inputId = id + "Input";
            const inputNode = document.getElementById(inputId);
            if (inputNode) {
                inputNode.value = (value !== null && value !== undefined) ? String(value) : "";
            }
        };

        const renderProjects = (projects) => {
            const tbody = document.getElementById("projectsTableBody");
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            if (!Array.isArray(projects) || projects.length === 0) {
                tbody.innerHTML = "<tr><td class=\"placeholder\">Нет данных</td><td class=\"placeholder\">-</td></tr>";
                return;
            }

            const pickText = (...values) => {
                for (const value of values) {
                    if (typeof value === "string" && value.trim().length > 0) {
                        return value.trim();
                    }
                }
                return "";
            };

            projects.forEach(project => {
                const projectName = pickText(
                    project.projectName,
                    project.project_name,
                    project.projectname,
                    project.name,
                    project.projectId,
                    project.project && project.project.projectName,
                    project.project && project.project.project_name
                ) || "Нет данных";

                const roleName = pickText(
                    project.roleName,
                    project.role_name,
                    project.role && project.role.roleName,
                    project.role && project.role.role_name
                ) || "-";

                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + projectName + "</td>" +
                    "<td>" + roleName + "</td>";
                tbody.appendChild(tr);
            });
        };

        const renderPostHistory = (history) => {
            const tbody = document.getElementById("postHistoryTableBody");
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            if (!Array.isArray(history) || history.length === 0) {
                tbody.innerHTML = "<tr><td class=\"placeholder\">Нет данных</td><td class=\"placeholder\">-</td><td class=\"placeholder\">-</td></tr>";
                return;
            }

            history.forEach(item => {
                const postName = item.postName || item.post_name || "Нет данных";
                const startDate = item.startDate || item.start_date || "-";
                const endDate = item.endDate || item.end_date || "-";
                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + postName + "</td>" +
                    "<td>" + startDate + "</td>" +
                    "<td>" + endDate + "</td>";
                tbody.appendChild(tr);
            });
        };

        const renderPayoutHistory = (payouts) => {
            const tbody = document.getElementById("payoutsTableBody");
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            if (!Array.isArray(payouts) || payouts.length === 0) {
                tbody.innerHTML = "<tr><td class=\"placeholder\">Нет данных</td><td class=\"placeholder\">-</td><td class=\"placeholder\">-</td></tr>";
                return;
            }

            payouts.forEach(item => {
                const value = item.value !== null && item.value !== undefined ? item.value : "Нет данных";
                const type = item.payoutTypeName || item.payout_type_name || item.payoutType || item.payout_type || item.payoutTypeId || item.payout_type_id || "-";
                const date = item.date || "-";
                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + value + "</td>" +
                    "<td>" + type + "</td>" +
                    "<td>" + date + "</td>";
                tbody.appendChild(tr);
            });
        };

        const renderRolesHistory = (history) => {
            const tbody = document.getElementById("rolesHistoryTableBody");
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            if (!Array.isArray(history) || history.length === 0) {
                tbody.innerHTML = "<tr><td class=\"placeholder\">Нет данных</td><td class=\"placeholder\">-</td><td class=\"placeholder\">-</td><td class=\"placeholder\">-</td></tr>";
                return;
            }

            history.forEach(item => {
                const project = item.projectName || item.project_name || item.projectId || item.project_id || "Нет данных";
                const role = item.roleName || item.role_name || "-";
                const startDate = item.startDate || item.start_date || "-";
                const endDate = item.endDate || item.end_date || "-";
                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + project + "</td>" +
                    "<td>" + role + "</td>" +
                    "<td>" + startDate + "</td>" +
                    "<td>" + endDate + "</td>";
                tbody.appendChild(tr);
            });
        };

        const renderBonusPayouts = (payouts) => {
            const tbody = document.getElementById("bonusPayoutsTableBody");
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            if (!Array.isArray(payouts) || payouts.length === 0) {
                tbody.innerHTML = "<tr><td class=\"placeholder\">Нет данных</td><td class=\"placeholder\">-</td></tr>";
                return;
            }

            payouts.forEach(item => {
                const value = item.value !== null && item.value !== undefined ? item.value : "Нет данных";
                const date = item.date || "-";
                const tr = document.createElement("tr");
                tr.innerHTML =
                    "<td>" + value + "</td>" +
                    "<td>" + date + "</td>";
                tbody.appendChild(tr);
            });
        };

        const fetchJson = async (url, label) => {
            const response = await fetch(url, {
                method: "GET",
                headers: { "Accept": "application/json" }
            });
            if (!response.ok) {
                throw new Error("Ошибка HTTP (" + label + "): " + response.status);
            }
            return response.json();
        };

        const getBonusPayoutTypeId = async () => {
            const payoutTypes = await fetchJson(contextPath + "/payouts", "payout types");
            if (!Array.isArray(payoutTypes)) {
                return null;
            }

            const bonusType = payoutTypes.find(item => {
                const typeName = item.payoutType || item.payout_type || item.name || "";
                return typeof typeName === "string" && typeName.trim().toLowerCase() === "bonus";
            });

            if (!bonusType) {
                return null;
            }

            return bonusType.payoutTypeId || bonusType.payout_type_id || bonusType.id || null;
        };

        try {
            const emp = await fetchJson(
                contextPath + "/employees/" + encodeURIComponent(employeeId),
                "employee"
            );
            currentEmployee = emp;

            setText("employeeSurname", emp.surname);
            setText("employeeName", emp.name);
            setText("employeeMiddleName", emp.middleName);
            setText("employeeBirthDate", emp.birthDate);
            setText("employeePost", emp.postName);
            setText("employeeExperience", emp.workExperience);

            const requests = [
                (async () => {
                    try {
                        renderProjects(await fetchJson(contextPath + "/projects/" + encodeURIComponent(employeeId), "projects"));
                    } catch (e) {
                        console.error(e);
                    }
                })(),
                (async () => {
                    try {
                        renderPostHistory(await fetchJson(contextPath + "/posts/history/" + encodeURIComponent(employeeId), "posts history"));
                    } catch (e) {
                        console.error(e);
                    }
                })(),
                (async () => {
                    try {
                        renderRolesHistory(await fetchJson(contextPath + "/roles/history/" + encodeURIComponent(employeeId), "roles history"));
                    } catch (e) {
                        console.error(e);
                    }
                })(),
                (async () => {
                    try {
                        renderPayoutHistory(await fetchJson(contextPath + "/employees/" + encodeURIComponent(employeeId) + "/payouts", "payouts"));
                    } catch (e) {
                        console.error(e);
                    }
                })(),
                (async () => {
                    try {
                        const bonusTypeId = await getBonusPayoutTypeId();
                        if (!bonusTypeId) {
                            renderBonusPayouts([]);
                            return;
                        }
                        renderBonusPayouts(
                            await fetchJson(
                                contextPath + "/employees/" + encodeURIComponent(employeeId) + "/payouts?payout_type_id=" + encodeURIComponent(bonusTypeId),
                                "bonus payouts"
                            )
                        );
                    } catch (e) {
                        console.error(e);
                    }
                })()
            ];
            await Promise.allSettled(requests);
        } catch (e) {
            console.error("Ошибка загрузки сотрудника:", e);
        }
    })();
</script>
</body>
</html>
