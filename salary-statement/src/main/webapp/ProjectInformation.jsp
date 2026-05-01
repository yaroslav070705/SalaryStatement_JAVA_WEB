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
                <div class="field"><span class="label">Название</span><span class="value">Нет данных</span></div>
                <div class="field"><span class="label">Дата начала</span><span class="value">Нет данных</span></div>
                <div class="field"><span class="label">Дата конца</span><span class="value">Нет данных</span></div>
            </div>
            <div class="actions">
                <button class="btn" type="button">Редактировать</button>
                <button class="btn btn-primary" type="button">Сохранить</button>
            </div>
        </div>
    </section>

    <section class="card top-right">
        <div class="card-head">
            <h2 class="title">Список работников</h2>
            <button class="btn btn-danger" type="button">Удалить проект</button>
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
                <button class="btn" type="button">Отменить</button>
                <button class="btn btn-danger" type="button">Удалить</button>
            </div>
        </article>

        <article class="card">
            <div class="card-head"><h2 class="title">Добавить работника</h2></div>
            <div class="form">
                <div class="form-row"><label>ФИО</label><input type="text"></div>
                <div class="form-row"><label>Роль</label><select><option value="">Выберите роль</option></select></div>
            </div>
            <div class="actions" style="padding: 12px; justify-content: flex-end;">
                <button class="btn" type="button">Отменить</button>
                <button class="btn btn-primary" type="button">Добавить</button>
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
    })();
</script>
</body>
</html>
