"use strict";

const url = "http://localhost:7074/api/admin/users"
const urlAuth = "http://localhost:7074/api/auth"
const csrfToken = document.cookie.replace(/(?:(?:^|.;\s)XSRF-TOKEN\s=\s([^;]).$)|^.*$/, '$1');

window.onload = function () {

    let usersTableButton = document.getElementById('nav-users_table-link');
    let newUserButton = document.getElementById('nav-user_form-link');
    let usersTableBody = document.getElementById('nav-users_table');
    let newUsersBody = document.getElementById('nav-user_form');
    let userAddForm = document.getElementById('user-add_form');
    let navbarAdminButton = document.getElementById('navbar-admin-button');
    let navbarUserButton = document.getElementById('navbar-user-button');
    let userPage = document.getElementById('user-page');
    let adminPage = document.getElementById('admin-page');
    let modalDialog = document.getElementById('user-edit-delete');
    let mainContainer = document.getElementById('main-container');

    getInfo();
    setTimeout(function () {
        mainContainer.classList.add("container-visibly");
    }, 100);

    usersTableButton.addEventListener('click', getHomePanel);
    newUserButton.addEventListener('click', getNewUserPanel);

    navbarAdminButton.addEventListener('click', getAdminPanel);
    navbarUserButton.addEventListener('click', getUserPanel);

    userAddForm.addEventListener('submit', addNewUser);


    // активируем вкладку с таблицей со всеми юзерами
    async function getHomePanel() {
        usersTableButton.classList.add('active');
        newUserButton.classList.remove('active');
        usersTableBody.classList.add('active');
        setTimeout(function () {
            usersTableBody.classList.add('show');
        }, 100);
        newUsersBody.classList.remove('active');
        setTimeout(function () {
            newUsersBody.classList.remove('show');
        }, 100);
    }

    // активируем вкладку создания нового юзера
    async function getNewUserPanel() {
        newUserButton.classList.add('active');
        usersTableButton.classList.remove('active');
        newUsersBody.classList.add('active');
        setTimeout(function () {
            newUsersBody.classList.add('show');
        }, 100);
        usersTableBody.classList.remove('show');
        usersTableBody.classList.remove('active');
    }

    // активируем вкладку админа
    async function getAdminPanel() {
        navbarAdminButton.classList.add('btn-primary');
        navbarUserButton.classList.remove('btn-primary');
        navbarAdminButton.classList.remove('btn-light');
        navbarUserButton.classList.add('btn-light');
        adminPage.classList.add('active');
        adminPage.classList.remove('deactive');
        setTimeout(function () {
            adminPage.classList.add('show');
            adminPage.classList.remove('hide');
        }, 100);
        userPage.classList.remove('active');
        userPage.classList.add('deactive');
        setTimeout(function () {
            userPage.classList.remove('show');
            userPage.classList.add('hide');
        }, 100);
    }

    // активируем вкладку ткущего юзера
    async function getUserPanel() {
        navbarAdminButton.classList.remove('btn-primary');
        navbarUserButton.classList.add('btn-primary');
        navbarAdminButton.classList.add('btn-light');
        navbarUserButton.classList.remove('btn-light');
        userPage.classList.add('active');
        userPage.classList.remove('deactive');
        setTimeout(function () {
            userPage.classList.add('show');
            userPage.classList.remove('hide');
        }, 100);
        adminPage.classList.remove('active');
        adminPage.classList.add('deactive');
        setTimeout(function () {
            adminPage.classList.remove('show');
            adminPage.classList.add('hide');
        }, 100);
    }

    // асинхронно с фетчем загружаем всех юзеров на страницу
    async function getAdminPage() {
        let page = await fetch(url);
        if (page.ok) {
            let listAllUser = await page.json();
            loadTableData(listAllUser);
        } else {
            alert(`Error, ${page.status}`)
        }
    }

    // получаем список всех юзеров на страницу, каждую вторую строчку немного затемняем + убираем ROLE_ из названия роли
    function loadTableData(listAllUser) {
        const tableElements = document.querySelectorAll("[name='table-users-row']");
        tableElements.forEach(function (element) {
            element.remove();
        });
        let tableBody = document.getElementById('tbody');
        for (let user of listAllUser) {
            let roles = [];
            for (let role of user.roles) {
                roles.push(" " + role.role.replace("ROLE_", ""))
            }
            const dataHtml = document.createElement("tr");
            if (listAllUser.indexOf(user) % 2 !== 0) {
                dataHtml.setAttribute("class", "table-secondary");
                dataHtml.setAttribute("name", "table-users-row")
            } else {
                dataHtml.setAttribute("class", "table-light");
                dataHtml.setAttribute("name", "table-users-row")
            }
            dataHtml.innerHTML = `<td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.age}</td>
                    <td>${user.username}</td>
                    <td>${roles}</td>
                    <td>
                    <button class="btn btn-info" id="editButtonForUser:${user.id}">Edit</button>
                    </td>
                    <td>
                    <button class="btn btn-danger" id="deleteButtonForUser:${user.id}">Delete</button>
                    </td>`
            tableBody.appendChild(dataHtml);

            document.getElementById("editButtonForUser:" + user.id).addEventListener("click", getModalPanel);
            document.getElementById("deleteButtonForUser:" + user.id).addEventListener("click", getModalPanel);
        }
    }

    // отправляем данные с формы на сервер и создаём нового юзера, обновляем информацию о юзерах и переходим на главную
    async function addNewUser(event) {
        event.preventDefault();
        let listOfRole = [];
        for (let i = 0; i < userAddForm.newRoles.options.length; i++) {
            if (userAddForm.newRoles.options[i].selected) {
                listOfRole.push({
                    id: userAddForm.newRoles.options[i].value,
                    role: userAddForm.newRoles.options[i].text
                });
            }
        }
        let postInfo = {
            method: 'POST',
            headers: {"Content-Type": "application/json", 'X-XSRF-TOKEN': csrfToken},
            body: JSON.stringify({
                name: userAddForm.newName.value,
                surname: userAddForm.newSurname.value,
                age: userAddForm.newAge.value,
                email: userAddForm.newEmail.value,
                password: userAddForm.newPassword.value,
                roles: listOfRole
            })
        }
        await fetch(url, postInfo).then(() => {
            userAddForm.reset();
            getAdminPage();
            getHomePanel();
        });
    }

    // асинхронно с фетчем загружаем текущего юзера на страницу
    async function getInfo() {
        let page = await fetch(urlAuth);
        if (page.ok) {
            let currentUser = await page.json();
            loadCurrentUserInfo(currentUser);
        } else {
            alert(`Error, ${page.status}`)
        }
    }

    // получаем текущего юзера на страницу, в зависимости от роли загружаем данные + убираем ROLE_ из названия роли
    function loadCurrentUserInfo(currentUser) {
        let headerBody = document.getElementById('header-title');
        let newHeaderInfoHtml = '';
        let roles = [];
        for (let role of currentUser.roles) {
            roles.push(" " + role.role.replace("ROLE_", ""))
        }
        if (roles.includes(" ADMIN") || roles.includes(" ADMIN, COMMON") || roles.includes(" COMMON, ADMIN")) {
            getUserPage();
            getAdminPage();
            getAdminPanel();
        } else {
            getUserPage();
            getUserPanel();
            removeAdminInfo();
        }
        newHeaderInfoHtml += `<div>${currentUser.username} with roles ${roles}</div>`
        headerBody.innerHTML = newHeaderInfoHtml;
    }

    // асинхронно с фетчем загружаем страницу юзера
    async function getUserPage() {
        let page = await fetch(urlAuth);
        if (page.ok) {
            let currentUser = await page.json();
            loadCurrentUserData(currentUser);
        } else {
            alert(`Error, ${page.status}`)
        }
    }

    // функция для удаления данных, доступных только админу
    function removeAdminInfo() {
        adminPage.remove();
        modalDialog.remove();
        navbarAdminButton.remove();
    }

    // получаем текущего юзера со всеми полями на личную страницу, + убираем ROLE_ из названия роли
    function loadCurrentUserData(currentUser) {
        let userTable = document.getElementById('current-user-table');
        let newCurrentTableUserHtml = '';
        let roles = [];
        for (let role of currentUser.roles) {
            roles.push(" " + role.role.replace("ROLE_", ""))
        }
        newCurrentTableUserHtml +=
            `<tr class="border-top bg-light">
                <td>${currentUser.id}</td>
                <td>${currentUser.name}</td>
                <td>${currentUser.surname}</td>
                <td>${currentUser.age}</td>
                <td>${currentUser.username}</td>
                <td>${roles}</td>
            </tr>`
        userTable.innerHTML = newCurrentTableUserHtml;
    }

    // функция вызова модального окна и получения данных для полей формы из выбранного юзера
    async function getModalPanel(event) {
        event.preventDefault();
        let strTarget = event.currentTarget.id.toString();
        let indexTwoDotes = strTarget.indexOf(":");
        let userId = Number(strTarget.substring(indexTwoDotes + 1));
        let indexOfB = strTarget.indexOf("B");
        let appointment = strTarget.substring(0, indexOfB);

        let fieldId = document.getElementById("id")
        let fieldFirstName = document.getElementById("firstName")
        let fieldLastName = document.getElementById("lastName")
        let fieldAge = document.getElementById("age")
        let fieldEmail = document.getElementById("email")
        let fieldPassword = document.getElementById("password")
        let fieldRoles = document.getElementById("roles")

        openModal();
        await getUserInfo();

        //получение данных текущего юзера
        async function getUserInfo() {
            let page = await fetch(url);
            if (page.ok) {
                let listAllUser = await page.json();
                loadEditTable(listAllUser, userId);
                changeAppointmentOfModal(appointment);
            } else {
                alert(`Error, ${page.status}`)
            }
        }

        //функция для добавления только одного "верхнего" слушателя
        function addEventListenerOnce(element, event, fn) {
            function onceFn(event) {
                element.removeEventListener(event.type, onceFn);
                fn(event);
            }
            element.addEventListener(event, onceFn);
        }

        //функция для изменения модального окна от типа кнопки (удаление или изменение пользователя)
        function changeAppointmentOfModal(appointment) {
            let button = document.getElementById("save-button");
            let modalTitle = document.getElementById("modal-title");

            if (appointment === "edit") {
                button.classList.remove("btn-danger");
                button.textContent = "Save";
                modalTitle.textContent = "Edit user"
                fieldFirstName.readOnly = false;
                fieldLastName.readOnly = false;
                fieldAge.readOnly = false;
                fieldEmail.readOnly = false;
                fieldPassword.readOnly = false;
                fieldRoles.disabled = false;

                button.classList.add("btn-primary");

                button.addEventListener("click", editUser);
            } else {
                button.classList.remove("btn-primary");

                button.classList.add("btn-danger");
                button.textContent = "Delete";
                modalTitle.textContent = "Delete user"
                fieldFirstName.readOnly = true;
                fieldLastName.readOnly = true;
                fieldAge.readOnly = true;
                fieldEmail.readOnly = true;
                fieldPassword.readOnly = true;
                fieldRoles.disabled = true;

                button.addEventListener("click", deleteUser);
            }
        }

        //функция для загрузки данных выбранного пользователя в модальное окно
        function loadEditTable(listAllUser, userId) {
            let currentUser;
            for (let user of listAllUser) {
                if (Number(user.id) === userId) {
                    currentUser = user;
                }
            }
            let roles = [];
            for (let role of currentUser.roles) {
                roles.push(" " + role.role.replace("ROLE_", ""))
            }
            fieldId.value = currentUser.id;
            fieldFirstName.value = currentUser.name;
            fieldLastName.value = currentUser.surname;
            fieldAge.value = currentUser.age;
            fieldEmail.value = currentUser.email;
            fieldPassword.value = currentUser.password;
            const optionAdmin = document.getElementById("ADMIN");
            const optionCommon = document.getElementById("COMMON");
            if (roles.includes(" ADMIN") && roles.includes(" COMMON")) {
                optionAdmin.selected = true;
                optionCommon.selected = true;
            } else if (roles.includes(" COMMON")) {
                optionAdmin.selected = false;
                optionCommon.selected = true;
            } else if (roles.includes(" ADMIN")) {
                optionAdmin.selected = true;
                optionCommon.selected = false;
            }
        }

        //функция для закрытия модального окна при нажатии на область вне формы
        function onModalBackdropClick(event) {
            if (event.target === this) {
                closeModal();
            }
        }

        //функция для получения модального окна (назначаем кнопкам слушателей для его закрытия, добавление тёмного фона)
        function openModal() {
            modalDialog.classList.add('active-modal');
            const backdrop = document.createElement("div");
            backdrop.setAttribute("name", "backdrop");
            backdrop.setAttribute("id", "backdrop");
            backdrop.setAttribute("class", "modal-backdrop fade")
            document.body.appendChild(backdrop);

            setTimeout(function () {
                modalDialog.classList.add('show');
                document.getElementById("backdrop").classList.add("show");
                document.body.classList.add("modal-open");
            }, 10);
            document.getElementById("close-button").addEventListener("click", closeModal)
            document.getElementById("close-x").addEventListener("click", closeModal)
            addEventListenerOnce(modalDialog, "click", onModalBackdropClick);
        }

        //функция для закрытия модального окна (удаление тёмного фона)
        function closeModal() {
            let button = document.getElementById("save-button");
            button.removeEventListener("click", editUser);
            button.removeEventListener("click", deleteUser);

            document.getElementById('backdrop').classList.remove("show")
            modalDialog.classList.remove('show');

            setTimeout(function () {
                document.body.classList.remove("modal-open");
                modalDialog.classList.remove('active-modal');
                const elements = document.querySelectorAll("[name='backdrop']");
                elements.forEach(function (element) {
                    element.remove();
                });
            }, 300);
        }

        //функция для отправления новой информации о редактируемом пользователе на сервер для его изменения
        async function editUser(event) {
            event.preventDefault();
            let listOfRole = [];
            for (let i = 0; i < fieldRoles.options.length; i++) {
                if (fieldRoles.options[i].selected) {
                    listOfRole.push({
                        id: fieldRoles.options[i].value,
                        role: fieldRoles.options[i].text
                    });
                }
            }
            let patchInfo = {
                method: 'PATCH',
                headers: {"Content-Type": "application/json", 'X-XSRF-TOKEN': csrfToken},
                body: JSON.stringify({
                    id: fieldId.value,
                    name: fieldFirstName.value,
                    surname: fieldLastName.value,
                    age: fieldAge.value,
                    email: fieldEmail.value,
                    password: fieldPassword.value,
                    roles: listOfRole
                })
            }
            await fetch(url, patchInfo).then(() => {
                closeModal();
                getAdminPage();
                getHomePanel();
            });
        }

        //функция для отправления информации об удаляемом пользователе на сервер для его удаления
        async function deleteUser(event) {
            event.preventDefault();
            let deleteInfo = {
                method: 'DELETE',
                headers: {"Content-Type": "application/json", 'X-XSRF-TOKEN': csrfToken},
                body: JSON.stringify({
                    id: fieldId.value,
                    name: fieldFirstName.value,
                    surname: fieldLastName.value,
                    age: fieldAge.value,
                    email: fieldEmail.value,
                    password: fieldPassword.value,
                })
            }
            await fetch(url + "/" + fieldId.value, deleteInfo).then(() => {
                closeModal();
                getAdminPage();
                getHomePanel();
            });
        }
    }
};
