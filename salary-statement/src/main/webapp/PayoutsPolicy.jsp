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
            display: block;
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

        .policy-type-buttons {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 8px;
        }

        .policy-type-btn.active {
            background: var(--accent);
            border-color: var(--accent);
            color: #ffffff;
        }

        .policy-form-fields {
            display: none;
            gap: 10px;
        }

        .policy-form-fields.active {
            display: grid;
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

        .btn:disabled {
            opacity: 0.55;
            cursor: not-allowed;
        }

        .btn-primary {
            background: var(--accent);
            color: #ffffff;
            border-color: var(--accent);
        }

        @media (max-width: 1000px) {
            .grid-top, .grid-middle {
                grid-template-columns: 1fr;
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
                    <thead><tr><th>Размер</th><th>Должность</th><th>Редактировать</th></tr></thead>
                    <tbody id="byPostTableBody"></tbody>
                </table>
            </div>
        </article>

        <article class="policy-card">
            <div class="card-head"><h2 class="card-title">Стаж</h2></div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Размер</th><th>Срок стажа</th><th>Редактировать</th></tr></thead>
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
                    <thead><tr><th>Размер</th><th>Основание</th><th>Редактировать</th></tr></thead>
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
                    <thead><tr><th>Размер</th><th>Роль</th><th>Редактировать</th></tr></thead>
                    <tbody id="byProjectTableBody"></tbody>
                </table>
            </div>
        </article>
    </section>

    <section class="bottom">
        <article class="editor">
            <div class="card-head"><h2 class="card-title">Добавить</h2></div>
            <div class="form">
                <div class="policy-type-buttons" id="policyTypeButtons">
                    <button class="btn policy-type-btn" type="button" data-policy-type="post">По должностям</button>
                    <button class="btn policy-type-btn" type="button" data-policy-type="experience">Стаж</button>
                    <button class="btn policy-type-btn" type="button" data-policy-type="bonus">Премиальные</button>
                    <button class="btn policy-type-btn" type="button" data-policy-type="project">По проектам</button>
                </div>

                <div class="policy-form-fields" data-policy-fields="post">
                    <div class="form-row"><label for="postPolicyValueInput">Размер</label><input id="postPolicyValueInput" type="number" step="0.01"></div>
                    <div class="form-row"><label for="postPolicyPostInput">Должность</label><input id="postPolicyPostInput" type="text"></div>
                </div>

                <div class="policy-form-fields" data-policy-fields="experience">
                    <div class="form-row"><label for="experiencePolicyValueInput">Размер</label><input id="experiencePolicyValueInput" type="number" step="0.01"></div>
                    <div class="form-row"><label for="experiencePolicyTermInput">Срок стажа</label><input id="experiencePolicyTermInput" type="number" min="0"></div>
                </div>

                <div class="policy-form-fields" data-policy-fields="bonus">
                    <div class="form-row"><label for="bonusPolicyValueInput">Размер</label><input id="bonusPolicyValueInput" type="number" step="0.01"></div>
                    <div class="form-row"><label for="bonusPolicyReasonInput">Основание</label><input id="bonusPolicyReasonInput" type="text"></div>
                </div>

                <div class="policy-form-fields" data-policy-fields="project">
                    <div class="form-row"><label for="projectPolicyValueInput">Размер</label><input id="projectPolicyValueInput" type="number" step="0.01"></div>
                    <div class="form-row"><label for="projectPolicyProjectSelect">Проект</label><select id="projectPolicyProjectSelect"><option value="">Выберите проект</option></select></div>
                    <div class="form-row"><label for="projectPolicyRoleSelect">Роль</label><select id="projectPolicyRoleSelect"><option value="">Выберите роль</option></select></div>
                </div>
            </div>
            <div class="actions">
                <button class="btn" id="cancelAddPolicyBtn" type="button" disabled>Отменить</button>
                <button class="btn btn-primary" id="submitAddPolicyBtn" type="button" disabled>Добавить</button>
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
        const byPostTableBody = document.getElementById("byPostTableBody");
        const byExperienceTableBody = document.getElementById("byExperienceTableBody");
        const bonusTableBody = document.getElementById("bonusTableBody");
        const projectFilterSelect = document.getElementById("projectFilterSelect");
        const byProjectTableBody = document.getElementById("byProjectTableBody");
        const postPolicyPostInput = document.getElementById("postPolicyPostInput");
        const projectPolicyProjectSelect = document.getElementById("projectPolicyProjectSelect");
        const projectPolicyRoleSelect = document.getElementById("projectPolicyRoleSelect");
        const policyTypeButtons = Array.from(document.querySelectorAll(".policy-type-btn"));
        const policyFieldGroups = Array.from(document.querySelectorAll(".policy-form-fields"));
        const cancelAddPolicyBtn = document.getElementById("cancelAddPolicyBtn");
        const submitAddPolicyBtn = document.getElementById("submitAddPolicyBtn");
        let selectedPolicyType = "";
        let editingPolicy = null;

        const asText = function (value) {
            if (value === null || value === undefined) {
                return "";
            }
            if (typeof value === "string") {
                return value.trim();
            }
            return String(value);
        };

        const getActivePolicyInputs = function () {
            if (!selectedPolicyType) {
                return [];
            }

            const activeGroup = document.querySelector(".policy-form-fields[data-policy-fields='" + selectedPolicyType + "']");
            if (!activeGroup) {
                return [];
            }

            return Array.from(activeGroup.querySelectorAll("input, select"));
        };

        const isAddPolicyFormFilled = function () {
            const activeInputs = getActivePolicyInputs();
            return activeInputs.length > 0 && activeInputs.every(function (input) {
                return Boolean(input.value.trim());
            });
        };

        const updateAddPolicyButtons = function () {
            const hasSelectedType = Boolean(selectedPolicyType);
            const hasAnyValue = getActivePolicyInputs().some(function (input) {
                return Boolean(input.value.trim());
            });

            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.disabled = !isAddPolicyFormFilled();
            }
            if (cancelAddPolicyBtn) {
                cancelAddPolicyBtn.disabled = !(hasSelectedType || hasAnyValue);
            }
        };

        const setProjectRoleFieldsReadonly = function (readonly) {
            if (projectPolicyProjectSelect) {
                projectPolicyProjectSelect.disabled = readonly;
            }
            if (projectPolicyRoleSelect) {
                projectPolicyRoleSelect.disabled = readonly;
            }
        };

        const setSelectedPolicyType = function (policyType) {
            selectedPolicyType = policyType || "";
            editingPolicy = null;
            setProjectRoleFieldsReadonly(false);

            policyTypeButtons.forEach(function (button) {
                button.classList.toggle("active", button.dataset.policyType === selectedPolicyType);
            });

            policyFieldGroups.forEach(function (group) {
                const isActive = group.dataset.policyFields === selectedPolicyType;
                group.classList.toggle("active", isActive);
                if (!isActive) {
                    group.querySelectorAll("input, select").forEach(function (field) {
                        field.value = "";
                    });
                }
            });

            updateAddPolicyButtons();
        };

        const resetAddPolicyForm = function () {
            selectedPolicyType = "";
            editingPolicy = null;
            setProjectRoleFieldsReadonly(false);
            policyTypeButtons.forEach(function (button) {
                button.classList.remove("active");
            });
            policyFieldGroups.forEach(function (group) {
                group.classList.remove("active");
                group.querySelectorAll("input, select").forEach(function (field) {
                    field.value = "";
                });
            });
            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.textContent = "Добавить";
            }
            updateAddPolicyButtons();
        };

        const setFieldValue = function (id, value) {
            const field = document.getElementById(id);
            if (!field) {
                return;
            }

            field.value = asText(value);
            field.dispatchEvent(new Event("input", { bubbles: true }));
            field.dispatchEvent(new Event("change", { bubbles: true }));
        };

        const setSelectByText = function (select, text) {
            if (!select) {
                return;
            }

            const normalizedText = asText(text).toLowerCase();
            const option = Array.from(select.options).find(function (item) {
                return asText(item.textContent).toLowerCase() === normalizedText;
            });

            select.value = option ? option.value : "";
            select.dispatchEvent(new Event("input", { bubbles: true }));
            select.dispatchEvent(new Event("change", { bubbles: true }));
        };

        const appendEditCell = function (tr, onClick) {
            const td = document.createElement("td");
            const button = document.createElement("button");
            button.className = "btn";
            button.type = "button";
            button.textContent = "Редактировать";
            button.addEventListener("click", onClick);
            td.appendChild(button);
            tr.appendChild(td);
        };

        const editPostPolicy = function (policy) {
            setSelectedPolicyType("post");
            editingPolicy = {
                type: "post",
                postId: asText(policy.postId || policy.post_id || policy.id)
            };
            setFieldValue("postPolicyValueInput", policy.payoutValue || policy.payout_value);
            setFieldValue("postPolicyPostInput", policy.postName || policy.post_name);
            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.textContent = "Сохранить";
            }
            updateAddPolicyButtons();
        };

        const editExperiencePolicy = function (policy) {
            setSelectedPolicyType("experience");
            editingPolicy = {
                type: "experience",
                experienceId: asText(policy.experienceId || policy.experience_id || policy.id)
            };
            setFieldValue("experiencePolicyValueInput", policy.value || policy.payoutValue || policy.payout_value);
            setFieldValue("experiencePolicyTermInput", policy.workExperience || policy.work_experience);
            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.textContent = "Сохранить";
            }
            updateAddPolicyButtons();
        };

        const editBonusPolicy = function (policy) {
            setSelectedPolicyType("bonus");
            editingPolicy = {
                type: "bonus",
                payoutTypeId: asText(policy.payoutTypeId || policy.payout_type_id || policy.id)
            };
            setFieldValue("bonusPolicyValueInput", policy.value || policy.payoutValue || policy.payout_value);
            setFieldValue("bonusPolicyReasonInput", policy.payoutType || policy.payout_type);
            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.textContent = "Сохранить";
            }
            updateAddPolicyButtons();
        };

        const editRolePolicy = function (policy) {
            setSelectedPolicyType("project");
            editingPolicy = {
                type: "project",
                projectId: asText(policy.projectId || policy.project_id) || (projectFilterSelect ? projectFilterSelect.value : ""),
                roleId: asText(policy.roleId || policy.role_id)
            };
            setFieldValue("projectPolicyValueInput", policy.value || policy.payoutValue || policy.payout_value);
            if (projectPolicyProjectSelect && projectFilterSelect) {
                projectPolicyProjectSelect.value = editingPolicy.projectId;
                projectPolicyProjectSelect.dispatchEvent(new Event("input", { bubbles: true }));
                projectPolicyProjectSelect.dispatchEvent(new Event("change", { bubbles: true }));
            }
            if (projectPolicyRoleSelect && editingPolicy.roleId) {
                projectPolicyRoleSelect.value = editingPolicy.roleId;
                projectPolicyRoleSelect.dispatchEvent(new Event("input", { bubbles: true }));
                projectPolicyRoleSelect.dispatchEvent(new Event("change", { bubbles: true }));
            } else {
                setSelectByText(projectPolicyRoleSelect, policy.roleName || policy.role_name);
                if (projectPolicyRoleSelect) {
                    editingPolicy.roleId = projectPolicyRoleSelect.value;
                }
            }
            setProjectRoleFieldsReadonly(true);
            if (submitAddPolicyBtn) {
                submitAddPolicyBtn.textContent = "Сохранить";
            }
            updateAddPolicyButtons();
        };

        const addPostPolicy = async function () {
            const value = document.getElementById("postPolicyValueInput").value;
            const postName = postPolicyPostInput ? postPolicyPostInput.value.trim() : "";
            if (!value || !postName) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/posts", {
                    method: "POST",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        payoutValue: Number(value),
                        postName: postName
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (add post policy): " + response.status);
                }

                await loadPostPolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка добавления политики выплат по должности:", error);
            }
        };

        const addExperiencePolicy = async function () {
            const value = document.getElementById("experiencePolicyValueInput").value;
            const workExperience = document.getElementById("experiencePolicyTermInput").value;
            if (!value || !workExperience) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/work_experience", {
                    method: "POST",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value),
                        workExperience: Number(workExperience)
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (add work experience policy): " + response.status);
                }

                await loadExperiencePolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка добавления политики выплат по стажу:", error);
            }
        };

        const addBonusPolicy = async function () {
            const value = document.getElementById("bonusPolicyValueInput").value;
            const payoutType = document.getElementById("bonusPolicyReasonInput").value.trim();
            if (!value || !payoutType) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/bonuses", {
                    method: "POST",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value),
                        payoutType: payoutType
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (add bonus policy): " + response.status);
                }

                await loadBonusPolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка добавления премиальной политики выплат:", error);
            }
        };

        const addRolePolicy = async function () {
            const value = document.getElementById("projectPolicyValueInput").value;
            const projectId = projectPolicyProjectSelect ? projectPolicyProjectSelect.value : "";
            const roleId = projectPolicyRoleSelect ? projectPolicyRoleSelect.value : "";
            if (!value || !projectId || !roleId) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/roles", {
                    method: "POST",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value),
                        projectId: projectId,
                        roleId: roleId
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (add role policy): " + response.status);
                }

                if (projectFilterSelect) {
                    projectFilterSelect.value = projectId;
                }
                await loadRolePolicies(projectId);
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка добавления политики выплат по проекту и роли:", error);
            }
        };

        const updatePostPolicy = async function () {
            if (!editingPolicy || editingPolicy.type !== "post" || !editingPolicy.postId) {
                return;
            }

            const value = document.getElementById("postPolicyValueInput").value;
            const postName = postPolicyPostInput ? postPolicyPostInput.value.trim() : "";
            if (!value || !postName) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/posts/" + encodeURIComponent(editingPolicy.postId), {
                    method: "PUT",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        payoutValue: Number(value),
                        postName: postName
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (update post policy): " + response.status);
                }

                await loadPostPolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка сохранения политики выплат по должности:", error);
            }
        };

        const updateExperiencePolicy = async function () {
            if (!editingPolicy || editingPolicy.type !== "experience" || !editingPolicy.experienceId) {
                return;
            }

            const value = document.getElementById("experiencePolicyValueInput").value;
            const workExperience = document.getElementById("experiencePolicyTermInput").value;
            if (!value || !workExperience) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/work_experience/" + encodeURIComponent(editingPolicy.experienceId), {
                    method: "PUT",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value),
                        workExperience: Number(workExperience)
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (update work experience policy): " + response.status);
                }

                await loadExperiencePolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка сохранения политики выплат по стажу:", error);
            }
        };

        const updateBonusPolicy = async function () {
            if (!editingPolicy || editingPolicy.type !== "bonus" || !editingPolicy.payoutTypeId) {
                return;
            }

            const value = document.getElementById("bonusPolicyValueInput").value;
            if (!value) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/bonuses/" + encodeURIComponent(editingPolicy.payoutTypeId), {
                    method: "PUT",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value)
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (update bonus policy): " + response.status);
                }

                await loadBonusPolicies();
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка сохранения премиальной политики выплат:", error);
            }
        };

        const updateRolePolicy = async function () {
            if (!editingPolicy || editingPolicy.type !== "project") {
                return;
            }

            const projectId = editingPolicy.projectId || (projectPolicyProjectSelect ? projectPolicyProjectSelect.value : "");
            const roleId = editingPolicy.roleId || (projectPolicyRoleSelect ? projectPolicyRoleSelect.value : "");
            const value = document.getElementById("projectPolicyValueInput").value;
            if (!projectId || !roleId || !value) {
                updateAddPolicyButtons();
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/roles/" + encodeURIComponent(projectId) + "/" + encodeURIComponent(roleId), {
                    method: "PUT",
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        value: Number(value)
                    })
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (update role policy): " + response.status);
                }

                await loadRolePolicies(projectId);
                resetAddPolicyForm();
            } catch (error) {
                console.error("Ошибка сохранения политики выплат по проекту и роли:", error);
            }
        };

        const renderPostPolicies = function (policies) {
            if (!byPostTableBody) {
                return;
            }

            byPostTableBody.innerHTML = "";
            if (!Array.isArray(policies) || policies.length === 0) {
                byPostTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Политики не найдены</td></tr>";
                return;
            }

            policies.forEach(function (policy) {
                const tr = document.createElement("tr");
                [
                    asText(policy.payoutValue || policy.payout_value) || "—",
                    asText(policy.postName || policy.post_name) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                appendEditCell(tr, function () {
                    editPostPolicy(policy);
                });
                byPostTableBody.appendChild(tr);
            });
        };

        const loadPostPolicies = async function () {
            try {
                const response = await fetch(contextPath + "/payouts/policies/posts", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (post policies): " + response.status);
                }

                renderPostPolicies(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки политик выплат по должностям:", error);
                if (byPostTableBody) {
                    byPostTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
            }
        };

        const renderExperiencePolicies = function (policies) {
            if (!byExperienceTableBody) {
                return;
            }

            byExperienceTableBody.innerHTML = "";
            if (!Array.isArray(policies) || policies.length === 0) {
                byExperienceTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Политики не найдены</td></tr>";
                return;
            }

            policies.forEach(function (policy) {
                const tr = document.createElement("tr");
                [
                    asText(policy.value || policy.payoutValue || policy.payout_value) || "—",
                    asText(policy.workExperience || policy.work_experience) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                appendEditCell(tr, function () {
                    editExperiencePolicy(policy);
                });
                byExperienceTableBody.appendChild(tr);
            });
        };

        const loadExperiencePolicies = async function () {
            try {
                const response = await fetch(contextPath + "/payouts/policies/work_experience", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (work experience policies): " + response.status);
                }

                renderExperiencePolicies(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки политик выплат по стажу:", error);
                if (byExperienceTableBody) {
                    byExperienceTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
            }
        };

        const renderBonusPolicies = function (policies) {
            if (!bonusTableBody) {
                return;
            }

            bonusTableBody.innerHTML = "";
            if (!Array.isArray(policies) || policies.length === 0) {
                bonusTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Политики не найдены</td></tr>";
                return;
            }

            policies.forEach(function (policy) {
                const tr = document.createElement("tr");
                [
                    asText(policy.value || policy.payoutValue || policy.payout_value) || "—",
                    asText(policy.payoutType || policy.payout_type) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                appendEditCell(tr, function () {
                    editBonusPolicy(policy);
                });
                bonusTableBody.appendChild(tr);
            });
        };

        const loadBonusPolicies = async function () {
            try {
                const response = await fetch(contextPath + "/payouts/policies/bonuses", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (bonus policies): " + response.status);
                }

                renderBonusPolicies(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки премиальных политик выплат:", error);
                if (bonusTableBody) {
                    bonusTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
            }
        };

        const renderProjectOptions = function (projects) {
            if (projectFilterSelect) {
                projectFilterSelect.innerHTML = "<option value=''>Выберите проект</option>";
            }
            if (projectPolicyProjectSelect) {
                projectPolicyProjectSelect.innerHTML = "<option value=''>Выберите проект</option>";
            }

            if (!Array.isArray(projects)) {
                return;
            }

            projects.forEach(function (project) {
                const projectId = asText(project.projectId || project.project_id || project.id);
                if (!projectId) {
                    return;
                }

                const option = document.createElement("option");
                option.value = projectId;
                option.textContent = asText(project.projectName || project.project_name || project.name) || "Без названия";
                if (projectFilterSelect) {
                    projectFilterSelect.appendChild(option.cloneNode(true));
                }
                if (projectPolicyProjectSelect) {
                    projectPolicyProjectSelect.appendChild(option);
                }
            });
        };

        const loadProjects = async function () {
            try {
                const response = await fetch(contextPath + "/projects", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (projects): " + response.status);
                }

                renderProjectOptions(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки проектов:", error);
                if (projectFilterSelect) {
                    projectFilterSelect.innerHTML = "<option value=''>Ошибка загрузки</option>";
                }
                if (projectPolicyProjectSelect) {
                    projectPolicyProjectSelect.innerHTML = "<option value=''>Ошибка загрузки</option>";
                }
            }
        };

        const renderRoleOptions = function (roles) {
            if (!projectPolicyRoleSelect) {
                return;
            }

            projectPolicyRoleSelect.innerHTML = "<option value=''>Выберите роль</option>";
            if (!Array.isArray(roles)) {
                return;
            }

            roles.forEach(function (role) {
                const roleId = asText(role.id || role.roleId || role.role_id);
                if (!roleId) {
                    return;
                }

                const option = document.createElement("option");
                option.value = roleId;
                option.textContent = asText(role.roleName || role.role_name) || "Без названия";
                projectPolicyRoleSelect.appendChild(option);
            });
        };

        const loadRoles = async function () {
            try {
                const response = await fetch(contextPath + "/roles", {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (roles): " + response.status);
                }

                renderRoleOptions(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки ролей:", error);
                if (projectPolicyRoleSelect) {
                    projectPolicyRoleSelect.innerHTML = "<option value=''>Ошибка загрузки</option>";
                }
            }
        };

        const renderRolePolicies = function (policies) {
            if (!byProjectTableBody) {
                return;
            }

            byProjectTableBody.innerHTML = "";
            if (!Array.isArray(policies) || policies.length === 0) {
                byProjectTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Политики не найдены</td></tr>";
                return;
            }

            policies.forEach(function (policy) {
                const tr = document.createElement("tr");
                [
                    asText(policy.value || policy.payoutValue || policy.payout_value) || "—",
                    asText(policy.roleName || policy.role_name) || "—"
                ].forEach(function (value) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    tr.appendChild(td);
                });
                appendEditCell(tr, function () {
                    editRolePolicy(policy);
                });
                byProjectTableBody.appendChild(tr);
            });
        };

        const loadRolePolicies = async function (projectId) {
            if (!projectId) {
                if (byProjectTableBody) {
                    byProjectTableBody.innerHTML = "";
                }
                return;
            }

            try {
                const response = await fetch(contextPath + "/payouts/policies/roles/" + encodeURIComponent(projectId), {
                    method: "GET",
                    headers: { "Accept": "application/json" }
                });

                if (!response.ok) {
                    throw new Error("Ошибка HTTP (role policies): " + response.status);
                }

                renderRolePolicies(await response.json());
            } catch (error) {
                console.error("Ошибка загрузки политик выплат по ролям:", error);
                if (byProjectTableBody) {
                    byProjectTableBody.innerHTML = "<tr><td colspan='3' style='text-align:center;'>Ошибка загрузки</td></tr>";
                }
            }
        };

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

        if (projectFilterSelect) {
            projectFilterSelect.addEventListener("change", function () {
                loadRolePolicies(projectFilterSelect.value);
            });
        }

        policyTypeButtons.forEach(function (button) {
            button.addEventListener("click", function () {
                setSelectedPolicyType(button.dataset.policyType || "");
            });
        });

        policyFieldGroups.forEach(function (group) {
            group.querySelectorAll("input, select").forEach(function (field) {
                field.addEventListener("input", updateAddPolicyButtons);
                field.addEventListener("change", updateAddPolicyButtons);
            });
        });

        if (cancelAddPolicyBtn) {
            cancelAddPolicyBtn.addEventListener("click", resetAddPolicyForm);
        }

        if (submitAddPolicyBtn) {
            submitAddPolicyBtn.addEventListener("click", async function () {
                if (editingPolicy) {
                    if (editingPolicy.type === "post") {
                        await updatePostPolicy();
                        return;
                    }
                    if (editingPolicy.type === "experience") {
                        await updateExperiencePolicy();
                        return;
                    }
                    if (editingPolicy.type === "bonus") {
                        await updateBonusPolicy();
                        return;
                    }
                    if (editingPolicy.type === "project") {
                        await updateRolePolicy();
                        return;
                    }
                }

                if (selectedPolicyType === "post") {
                    await addPostPolicy();
                    return;
                }
                if (selectedPolicyType === "experience") {
                    await addExperiencePolicy();
                    return;
                }
                if (selectedPolicyType === "bonus") {
                    await addBonusPolicy();
                    return;
                }
                if (selectedPolicyType === "project") {
                    await addRolePolicy();
                    return;
                }

                updateAddPolicyButtons();
            });
        }

        loadPostPolicies();
        loadExperiencePolicies();
        loadBonusPolicies();
        loadProjects();
        loadRoles();
        resetAddPolicyForm();
    })();
</script>
</body>
</html>
