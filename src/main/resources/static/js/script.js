"use strict";

const url = "http://localhost:7074/admin/users"

window.onload = function () {

    fetch(url).then(function (response) {
        return response.json();
    }).then(function (data) {
        console.log(data);
    }).catch(function (error) {
        console.log(error);
    });

    let usersTableButton = document.getElementById('nav-users_table-link');
    let newUserButton = document.getElementById('nav-user_form-link');
    let usersTableBody = document.getElementById('nav-users_table');
    let newUsersBody = document.getElementById('nav-user_form');
    let userAddForm = document.getElementById('user-add_form');

    usersTableButton.addEventListener('click', function () {
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
    });

    newUserButton.addEventListener('click', function () {
        newUserButton.classList.add('active');
        usersTableButton.classList.remove('active');
        newUsersBody.classList.add('active');
        setTimeout(function () {
            newUsersBody.classList.add('show');
        }, 100);
        usersTableBody.classList.remove('show');
        usersTableBody.classList.remove('active');
    });

    getAdminPage();

    async function getAdminPage() {
        let page = await fetch(url);
        if (page.ok) {
            let listAllUser = await page.json();
            loadTableData(listAllUser);
        } else {
            alert(`Error, ${page.status}`)
        }
    }


    userAddForm.addEventListener('click', (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);

        fetch(url, {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            })
    });

    function loadTableData(listAllUser) {
        let tableBody = document.getElementById('tbody');
        let dataHtml = '';
        for (let user of listAllUser) {
            let roles = [];
            for (let role of user.roles) {
                roles.push(" " + role.role.replace("ROLE_", ""))
                if (listAllUser.indexOf(user) % 2 !== 0) {
                    dataHtml += `<tr class="table-secondary">`;
                } else {
                    dataHtml += `<tr id="table-light">`;
                }
                dataHtml +=
                    `<td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.age}</td>
                <td>${user.username}</td>
                <td>${roles}</td>
                <td>
                    <button class="btn btn-info" data-bs-toogle="modal"
                    data-bs-target="#editModal"
                    onclick="editModalData(${user.id})">Edit</button>
                </td>
                    <td>
                    <button class="btn btn-danger" data-bs-toogle="modal"
                    data-bs-target="#deleteModal"
                    onclick="deleteModalData(${user.id})">Delete</button>
                </td>
                </tr>`
            }
        }
        tableBody.innerHTML = dataHtml;
    }
};