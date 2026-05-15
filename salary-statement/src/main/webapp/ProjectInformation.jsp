<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Информация о проекте</title>
    <style>
        :root {
            --bg: #eef2f7;
            --card: #ffffff;
            --text: #1f2937;
            --muted: #6b7280;
            --border: #d9e1ea;
            --accent: #0f766e;
            --danger: #b42318;
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

        .btn-menu {
            position: fixed;
            top: 20px;
            left: 20px;
            z-index: 200;
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
            z-index: 210;
            width: 220px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 8px;
            display: none;
        }
        .menu-panel.active { display: block; }
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

        .page {
            max-width: 1400px;
            margin: 0 auto;
            padding: 84px 24px 24px;
            display: grid;
            grid-template-columns: 320px 1fr;
            grid-template-areas:
                "left topRight"
                "bottom bottom";
            gap: 20px;
        }

        .left-panel { grid-area: left; }
        .top-right { grid-area: topRight; }

        .bottom-grid {
            grid-area: bottom;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .card {
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .card-head {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 12px 14px;
            border-bottom: 1px solid var(--border);
            background: #f8fafc;
        }

        .title {
            margin: 0;
            font-size: 16px;
            color: var(--accent);
        }

        .profile {
            padding: 14px;
        }

        .fields {
            display: grid;
            gap: 10px;
        }

        .field {
            display: grid;
            gap: 4px;
            padding: 10px 12px;
            border: 1px solid var(--border);
            border-radius: 8px;
            background: #fcfdff;
        }

        .label {
            font-size: 12px;
            color: var(--muted);
            text-transform: uppercase;
        }

        .value {
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

        .left-panel.edit-mode .value {
            display: none;
        }

        .left-panel.edit-mode .field-input {
            display: block;
        }

        .actions {
            display: flex;
            gap: 10px;
            margin-top: 12px;
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
            background: var(--accent);
            color: #ffffff;
            border-color: var(--accent);
        }

        .btn-danger {
            background: var(--danger);
            color: #ffffff;
            border-color: var(--danger);
        }

        .table-wrap {
            max-height: 360px;
            overflow-y: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
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

        tbody tr.worker-row {
            cursor: pointer;
        }

        tbody tr.worker-row:hover,
        tbody tr.worker-row.selected {
            background: #eef8f6;
        }

        tbody tr.add-worker-row {
            cursor: pointer;
        }

        tbody tr.add-worker-row:hover,
        tbody tr.add-worker-row.selected {
            background: #eef8f6;
        }

        .form {
            padding: 12px;
            display: grid;
            gap: 10px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 120px 1fr;
            gap: 10px;
            align-items: center;
        }

        .form-row label {
            font-size: 13px;
            color: var(--text);
        }

        .form-row input,
        .form-row select {
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 8px 10px;
            font-size: 14px;
        }

        .mini-table-wrap {
            max-height: 180px;
            overflow-y: auto;
            border-top: 1px solid var(--border);
            border-bottom: 1px solid var(--border);
        }

        @media (max-width: 900px) {
            .page {
                grid-template-columns: 1fr;
                grid-template-areas:
                    "left"
                    "topRight"
                    "bottom";
            }
            .bottom-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>
<button class="btn-menu" id="menuBtn" aria-label="Открыть главное меню">
    <span></span><span></span><span></span>
</button>
<div class="menu-panel" id="menuPanel">
    <button class="menu-item" id="menuWorkersBtn" type="button">Работники</button>
    <button class="menu-item" id="menuProjectsBtn" type="button">Проекты</button>
    <button class="menu-item" id="menuPoliciesBtn" type="button">Политики выплат</button>
</div>

<main class="page">
    <section class="card left-panel">
        <div class="card-head"><h2 class="title">Проект</h2></div>
        <div class="profile">
            <div class="fields">
                <div class="field">
                    <span class="label">Название</span>
                    <span class="value" id="projectName">Нет данных</span>
                    <input class="field-input" id="projectNameInput" type="text">
                </div>
                <div class="field">
                    <span class="label">Дата начала</span>
                    <span class="value" id="projectStartDate">Нет данных</span>
                    <input class="field-input" id="projectStartDateInput" type="date">
                </div>
                <div class="field">
                    <span class="label">Дата конца</span>
                    <span class="value" id="projectEndDate">Нет данных</span>
                    <input class="field-input" id="projectEndDateInput" type="date">
                </div>
            </div>
            <div class="actions">
                <button class="btn" id="editProjectBtn" type="button">редакитировать</button>
                <button class="btn btn-primary" id="saveProjectBtn" type="button" disabled>сохранить</button>
            </div>
        </div>
    </section>

    <section class="card top-right">
        <div class="card-head">
            <h2 class="title">Список работников</h2>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr><th>Фамилия</th><th>Имя</th><th>Отчество</th><th>Роль</th></tr>
                </thead>
                <tbody id="projectWorkersTableBody"></tbody>
            </table>
        </div>
    </section>

    <section class="bottom-grid">
        <article class="card">
            <div class="card-head"><h2 class="title">Удалить работников</h2></div>
            <div class="table-wrap" style="max-height: 180px;">
                <table>
                    <thead><tr><th>Фамилия</th><th>Имя</th><th>Отчество</th><th>Роль</th></tr></thead>
                    <tbody id="deleteWorkersTableBody"></tbody>
                </table>
            </div>
            <div class="actions" style="padding: 12px; justify-content: flex-end;">
                <button class="btn" id="cancelWorkerSelectionBtn" type="button" disabled>Отменить</button>
                <button class="btn btn-danger" id="deleteWorkerBtn" type="button" disabled>Удалить</button>
            </div>
        </article>

        <article class="card">
            <div class="card-head"><h2 class="title">Добавить работника</h2></div>
            <div class="form">
                <div class="form-row"><label for="addWorkerSearchInput">ФИО</label><input id="addWorkerSearchInput" type="text"></div>
                <div class="form-row"><label for="addWorkerRoleSelect">Роль</label><select id="addWorkerRoleSelect"><option value="">Выберите роль</option></select></div>
            </div>
            <div class="mini-table-wrap">
                <table>
                    <thead><tr><th>Фамилия</th><th>Имя</th><th>Отчество</th><th>Должность</th></tr></thead>
                    <tbody id="addWorkersTableBody"></tbody>
                </table>
            </div>
            <div class="actions" style="padding: 12px; justify-content: flex-end;">
                <button class="btn" id="cancelAddWorkerBtn" type="button" disabled>Отменить</button>
                <button class="btn btn-primary" id="submitAddWorkerBtn" type="button" disabled>Добавить</button>
            </div>
        </article>
    </section>
</main>

<script>
    (function () {
        const contextPath = "${pageContext.request.contextPath}";
        const menuBtn = document.getElementById("menuBtn");
        const menuPanel = document.getElementById("menuPanel");
        const menuWorkersBtn = document.getElementById("menuWorkersBtn");
        const menuProjectsBtn = document.getElementById("menuProjectsBtn");
        const menuPoliciesBtn = document.getElementById("menuPoliciesBtn");
        const projectWorkersTableBody = document.getElementById("projectWorkersTableBody");
        const deleteWorkersTableBody = document.getElementById("deleteWorkersTableBody");
        const deleteWorkerBtn = document.getElementById("deleteWorkerBtn");
        const cancelWorkerSelectionBtn = document.getElementById("cancelWorkerSelectionBtn");
        const projectCard = document.querySelector(".left-panel");
        const editProjectBtn = document.getElementById("editProjectBtn");
        const saveProjectBtn = document.getElementById("saveProjectBtn");
        const addWorkerSearchInput = document.getElementById("addWorkerSearchInput");
        const addWorkerRoleSelect = document.getElementById("addWorkerRoleSelect");
        const addWorkersTableBody = document.getElementById("addWorkersTableBody");
        const cancelAddWorkerBtn = document.getElementById("cancelAddWorkerBtn");
        const submitAddWorkerBtn = document.getElementById("submitAddWorkerBtn");
        const params = new URLSearchParams(window.location.search);
        const projectId = params.get("id") || params.get("projectId");
        const workerActionButtons = [deleteWorkerBtn, cancelWorkerSelectionBtn].filter(Boolean);
        let selectedWorkerId = "";
        let selectedAddWorkerId = "";
        let allEmployees = [];

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

        const asText = function (value) {
            if (value === null || value === undefined) {
                return "";
            }
            if (typeof value === "string") {
                return value.trim();
            }
            return String(value);
        };

        const setText = function (id, value) {
            const element = document.getElementById(id);
            const input = document.getElementById(id + "Input");
            const text = asText(value);
            if (element) {
                element.textContent = text || "Нет данных";
            }
            if (input) {
                input.value = text;
            }
        };

        const setWorkerActionsEnabled = function (enabled) {
            workerActionButtons.forEach(function (button) {
                button.disabled = !enabled;
            });
        };

        const setAddWorkerActionsEnabled = function () {
            const hasSearchText = Boolean(addWorkerSearchInput && addWorkerSearchInput.value.trim());
            const hasRole = Boolean(addWorkerRoleSelect && addWorkerRoleSelect.value);
            const hasSelectedWorker = Boolean(selectedAddWorkerId);

            if (submitAddWorkerBtn) {
                submitAddWorkerBtn.disabled = !(hasSelectedWorker && hasRole);
            }
            if (cancelAddWorkerBtn) {
                cancelAddWorkerBtn.disabled = !(hasSearchText || hasRole || hasSelectedWorker);
            }
        };

        const selectWorker = function (employeeId) {
            selectedWorkerId = employeeId || "";
            document.querySelectorAll("tr.worker-row.selected").forEach(function (row) {
                row.classList.remove("selected");
            });
            if (selectedWorkerId) {
                document.querySelectorAll("tr.worker-row[data-employee-id='" + selectedWorkerId + "']").forEach(function (row) {
                    row.classList.add("selected");
                });
            }
            setWorkerActionsEnabled(Boolean(selectedWorkerId));
        };

        const selectAddWorker = function (employeeId) {
            selectedAddWorkerId = employeeId || "";
            document.querySelectorAll("tr.add-worker-row.selected").forEach(function (row) {
                row.classList.remove("selected");
            });
            if (selectedAddWorkerId) {
                document.querySelectorAll("tr.add-worker-row[data-employee-id='" + selectedAddWorkerId + "']").forEach(function (row) {
                    row.classList.add("selected");
                });
            }
            setAddWorkerActionsEnabled();
        };

        const getEmployeeFullName = function (employee) {
            return [
                asText(employee.surname),
                asText(employee.name),
                asText(employee.middleName || employee.middle_name)
            ].filter(Boolean).join(" ");
        };

        const renderAddWorkerCandidates = function () {
            if (!addWorkersTableBody) {
                return;
            }

            const query = addWorkerSearchInput ? addWorkerSearchInput.value.trim().toLowerCase() : "";
            const workers = allEmployees.filter(function (employee) {
                if (!query) {
                    return true;
                }
                return getEmployeeFullName(employee).toLowerCase().includes(query);
            });

            addWorkersTableBody.innerHTML = "";
            selectedAddWorkerId = "";
            if (!Array.isArray(workers) || workers.length === 0) {
                addWorkersTableBody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Работники не найдены</td></tr>";
                setAddWorkerActionsEnabled();
                return;
            }

            workers.forEach(function (worker) {
                const tr = document.createElement("tr");
                const employeeId = asText(worker.employeeId || worker.employee_id || worker.id);
                tr.className = "add-worker-row";
                tr.dataset.employeeId = employeeId;
                tr.addEventListener("click", function () {
                    selectAddWorker(employeeId);
                });
                [
                    asText(worker.surname) || "—",
                    asText(worker.name) || "—",
                    asText(worker.middleName || worker.middle_name) || "—",
                    asText(worker.postName || worker.post_name) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                addWorkersTableBody.appendChild(tr);
            });
            setAddWorkerActionsEnabled();
        };

        const resetAddWorkerForm = function () {
            if (addWorkerSearchInput) {
                addWorkerSearchInput.value = "";
            }
            if (addWorkerRoleSelect) {
                addWorkerRoleSelect.value = "";
            }
            selectedAddWorkerId = "";
            renderAddWorkerCandidates();
            setAddWorkerActionsEnabled();
        };

        setWorkerActionsEnabled(false);

        if (cancelWorkerSelectionBtn) {
            cancelWorkerSelectionBtn.addEventListener("click", function () {
                selectWorker("");
            });
        }

        if (deleteWorkerBtn) {
            deleteWorkerBtn.addEventListener("click", async function () {
                if (!projectId || !selectedWorkerId) {
                    return;
                }

                try {
                    const response = await fetch(contextPath + "/projects/" + encodeURIComponent(projectId) + "/" + encodeURIComponent(selectedWorkerId), {
                        method: "DELETE",
                        headers: { "Accept": "application/json" }
                    });

                    if (!response.ok) {
                        throw new Error("Ошибка HTTP (delete project employee): " + response.status);
                    }

                    selectWorker("");
                    await loadProjectWorkers();
                } catch (error) {
                    console.error("Ошибка удаления работника из проекта:", error);
                }
            });
        }

        if (addWorkerSearchInput) {
            addWorkerSearchInput.addEventListener("input", function () {
                renderAddWorkerCandidates();
                setAddWorkerActionsEnabled();
            });
        }

        if (addWorkerRoleSelect) {
            addWorkerRoleSelect.addEventListener("change", setAddWorkerActionsEnabled);
        }

        if (cancelAddWorkerBtn) {
            cancelAddWorkerBtn.addEventListener("click", resetAddWorkerForm);
        }

        if (editProjectBtn && projectCard) {
            editProjectBtn.addEventListener("click", function () {
                projectCard.classList.add("edit-mode");
                editProjectBtn.disabled = true;
                if (saveProjectBtn) {
                    saveProjectBtn.disabled = false;
                }
            });
        }

        if (saveProjectBtn && projectCard) {
            saveProjectBtn.addEventListener("click", async function () {
                if (!projectId) {
                    return;
                }

                const projectNameInput = document.getElementById("projectNameInput");
                const projectStartDateInput = document.getElementById("projectStartDateInput");
                const projectEndDateInput = document.getElementById("projectEndDateInput");

                const payload = {
                    projectName: projectNameInput ? projectNameInput.value : "",
                    startDate: projectStartDateInput && projectStartDateInput.value ? projectStartDateInput.value : null,
                    endDate: projectEndDateInput && projectEndDateInput.value ? projectEndDateInput.value : null
                };

                try {
                    const response = await fetch(contextPath + "/projects/" + encodeURIComponent(projectId), {
                        method: "PUT",
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(payload)
                    });

                    if (!response.ok) {
                        throw new Error("Ошибка HTTP (update project): " + response.status);
                    }

                    setText("projectName", payload.projectName);
                    setText("projectStartDate", payload.startDate);
                    setText("projectEndDate", payload.endDate);
                } catch (error) {
                    console.error("Ошибка сохранения проекта:", error);
                    return;
                }

                projectCard.classList.remove("edit-mode");
                saveProjectBtn.disabled = true;
                if (editProjectBtn) {
                    editProjectBtn.disabled = false;
                }
            });
        }

        const renderWorkers = function (workers, tbody) {
            if (!tbody) {
                return;
            }

            tbody.innerHTML = "";
            selectWorker("");
            if (!Array.isArray(workers) || workers.length === 0) {
                tbody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Работники не найдены</td></tr>";
                return;
            }

            workers.forEach(function (worker) {
                const tr = document.createElement("tr");
                const employeeId = asText(worker.employeeId || worker.employee_id || worker.id);
                tr.className = "worker-row";
                tr.dataset.employeeId = employeeId;
                tr.addEventListener("click", function () {
                    selectWorker(employeeId);
                });
                [
                    asText(worker.surname) || "—",
                    asText(worker.name) || "—",
                    asText(worker.middleName || worker.middle_name) || "—",
                    asText(worker.roleName || worker.role_name) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                tbody.appendChild(tr);
            });
        };

        const fetchJson = async function (url, label) {
            const response = await fetch(url, {
                method: "GET",
                headers: { "Accept": "application/json" }
            });
            if (!response.ok) {
                throw new Error("Ошибка HTTP (" + label + "): " + response.status);
            }
            return response.json();
        };

        const loadProjectWorkers = async function () {
            const workers = await fetchJson(contextPath + "/projects/" + encodeURIComponent(projectId) + "/employees", "project employees");
            renderWorkers(workers, projectWorkersTableBody);
            renderWorkers(workers, deleteWorkersTableBody);
        };

        const loadAddWorkerData = async function () {
            try {
                const employees = await fetchJson(contextPath + "/employees", "employees");
                allEmployees = Array.isArray(employees) ? employees : [];
                renderAddWorkerCandidates();
            } catch (error) {
                console.error("Ошибка загрузки работников:", error);
                allEmployees = [];
                if (addWorkersTableBody) {
                    addWorkersTableBody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
                setAddWorkerActionsEnabled();
            }

            try {
                const roles = await fetchJson(contextPath + "/roles", "roles");
                if (addWorkerRoleSelect) {
                    addWorkerRoleSelect.innerHTML = "<option value=''>Выберите роль</option>";
                    if (Array.isArray(roles)) {
                        roles.forEach(function (role) {
                            const roleId = asText(role.id || role.roleId || role.role_id);
                            if (!roleId) {
                                return;
                            }
                            const option = document.createElement("option");
                            option.value = roleId;
                            option.textContent = asText(role.roleName || role.role_name) || "Без названия";
                            addWorkerRoleSelect.appendChild(option);
                        });
                    }
                }
            } catch (error) {
                console.error("Ошибка загрузки ролей:", error);
            }
            setAddWorkerActionsEnabled();
        };

        if (submitAddWorkerBtn) {
            submitAddWorkerBtn.addEventListener("click", async function () {
                if (!projectId || !selectedAddWorkerId || !addWorkerRoleSelect || !addWorkerRoleSelect.value) {
                    return;
                }

                try {
                    const response = await fetch(contextPath + "/projects/" + encodeURIComponent(projectId) + "/employees", {
                        method: "POST",
                        headers: {
                            "Accept": "application/json",
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify({
                            employeeId: selectedAddWorkerId,
                            roleId: addWorkerRoleSelect.value
                        })
                    });

                    if (!response.ok) {
                        throw new Error("Ошибка HTTP (add project employee): " + response.status);
                    }

                    resetAddWorkerForm();
                    await loadProjectWorkers();
                } catch (error) {
                    console.error("Ошибка добавления работника в проект:", error);
                }
            });
        }

        const loadProjectInformation = async function () {
            if (!projectId) {
                console.error("Не указан id проекта");
                return;
            }

            try {
                const project = await fetchJson(contextPath + "/projects/" + encodeURIComponent(projectId), "project");

                setText("projectName", project.projectName || project.project_name || project.name);
                setText("projectStartDate", project.startDate || project.start_date || project.dateStart);
                setText("projectEndDate", project.endDate || project.end_date || project.dateEnd);
            } catch (error) {
                console.error("Ошибка загрузки проекта:", error);
                setText("projectName", "Ошибка загрузки");
                setText("projectStartDate", "");
                setText("projectEndDate", "");
            }

            try {
                await loadProjectWorkers();
            } catch (error) {
                console.error("Ошибка загрузки работников проекта:", error);
                if (projectWorkersTableBody) {
                    projectWorkersTableBody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
                if (deleteWorkersTableBody) {
                    deleteWorkersTableBody.innerHTML = "<tr><td colspan='4' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
            }

            await loadAddWorkerData();
        };

        loadProjectInformation();
    })();
</script>
</body>
</html>
