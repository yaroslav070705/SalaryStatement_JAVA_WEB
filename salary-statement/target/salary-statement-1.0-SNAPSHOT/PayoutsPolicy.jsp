<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Политики выплат</title>
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
        }

        .title {
            margin: 0 auto 18px;
            width: fit-content;
            border: 1px solid var(--border);
            border-radius: 999px;
            padding: 12px 46px;
            background: var(--card);
            box-shadow: var(--shadow);
            font-size: 28px;
            color: var(--accent);
        }

        .grid-top {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 20px;
        }

        .grid-middle {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 20px;
        }

        .policy-card {
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

        .card-title {
            margin: 0;
            font-size: 16px;
            color: var(--accent);
        }

        .table-wrap {
            max-height: 210px;
            overflow-y: auto;
            padding: 8px;
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

        .project-filter-row {
            display: grid;
            grid-template-columns: 120px 1fr;
            gap: 10px;
            padding: 10px 12px 0;
            align-items: center;
        }

        .project-filter-row label {
            font-size: 13px;
            color: var(--text);
        }

        .project-filter-row select {
            width: 100%;
            border: 1px solid var(--border);
            border-radius: 8px;
            padding: 8px 10px;
            font-size: 14px;
        }

        .bottom {
            display: grid;
            grid-template-columns: 1fr auto;
            gap: 14px;
            align-items: start;
        }

        .editor {
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            overflow: hidden;
            max-width: 540px;
            margin: 0 auto;
            width: 100%;
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

        .actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            padding: 12px;
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
            margin-top: 10px;
        }

        @media (max-width: 1000px) {
            .grid-top, .grid-middle {
                grid-template-columns: 1fr;
            }
            .bottom {
                grid-template-columns: 1fr;
            }
            .btn-danger {
                width: fit-content;
                margin: 0 auto;
            }
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
    <h1 class="title">Политики выплат</h1>

    <section class="grid-top">
        <article class="policy-card">
            <div class="card-head"><h2 class="card-title">По должностям</h2></div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Размер</th><th>Должность</th></tr></thead>
                    <tbody id="byPostTableBody"></tbody>
                </table>
            </div>
        </article>

        <article class="policy-card">
            <div class="card-head"><h2 class="card-title">Стаж</h2></div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Размер</th><th>Срок стажа</th></tr></thead>
                    <tbody id="byExperienceTableBody"></tbody>
                </table>
            </div>
        </article>
    </section>

    <section class="grid-middle">
        <article class="policy-card">
            <div class="card-head"><h2 class="card-title">Премиальные</h2></div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Размер</th><th>Основание</th></tr></thead>
                    <tbody id="bonusTableBody"></tbody>
                </table>
            </div>
        </article>

        <article class="policy-card">
            <div class="card-head"><h2 class="card-title">По проектам</h2></div>
            <div class="project-filter-row">
                <label for="projectFilterSelect">Проект</label>
                <select id="projectFilterSelect"><option value="">Поиск + список</option></select>
            </div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Размер</th><th>Роль</th></tr></thead>
                    <tbody id="byProjectTableBody"></tbody>
                </table>
            </div>
        </article>
    </section>

    <section class="bottom">
        <article class="editor">
            <div class="card-head"><h2 class="card-title">Добавить</h2></div>
            <div class="form">
                <div class="form-row"><label>Тип политики</label><select><option value="">Выберите</option></select></div>
                <div class="form-row"><label>Размер</label><input type="number" step="0.01"></div>
                <div class="form-row"><label>Основание</label><input type="text"></div>
            </div>
            <div class="actions">
                <button class="btn" type="button">Отменить</button>
                <button class="btn btn-primary" type="button">Добавить</button>
            </div>
        </article>

        <button class="btn btn-danger" type="button">Удалить</button>
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
