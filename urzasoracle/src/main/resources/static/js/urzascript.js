function reformatText() {
    var replaceCandidates = document.querySelectorAll(".replaceCandidate")
    replaceCandidates.forEach(e => {
        var originalText = e.innerHTML;

        var editedText = originalText.replace(/\{([^}]+)\}/g, (value, content) => {

            content = content.replace("/", "")
            var wrapperIcon = `<abbr class="card-symbol card-symbol-${content}">${value}</abbr>`;
            return wrapperIcon;
        })
        e.innerHTML = editedText;
        e.classList.remove("replaceCandidate");
    });


}

document.addEventListener('DOMContentLoaded', () => { reformatText() })

function togglePasswordSection() {
    const section = document.getElementById("passwordSection");
    const btn = document.getElementById("togglePasswordBtn");

    if (section.style.display === "none") {
        section.style.display = "block";
        btn.style.display = "none";
    } else {
        section.style.display = "none";
        btn.style.display = "inline-block";
    }
}

function toggleAdminSection() {
    const section = document.getElementById("adminSection");
    const btn = document.getElementById("toggleAdminBtn");

    if (section.style.display === "none") {
        section.style.display = "block";
        btn.textContent = "Nascondi Gestione Utenti";
    } else {
        section.style.display = "none";
        btn.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-people-fill me-2" viewBox="0 0 16 16"><path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"/></svg>Gestisci Utenti Registrati`;
    }
}

function showEditModalFromData(button) {
    const userId = button.getAttribute("data-user-id");
    const username = button.getAttribute("data-username");
    const displayName = button.getAttribute("data-displayname");
    const email = button.getAttribute("data-email");

    document.getElementById("editUserId").value = userId;
    document.getElementById("editUsername").value = username;
    document.getElementById("editDisplayName").value = displayName;
    document.getElementById("editEmail").value = email;

    document.getElementById("editUserForm").action =
        "/profile/edit-user/" + userId;

    var modal = new bootstrap.Modal(
        document.getElementById("editUserModal")
    );
    modal.show();
}

function showDeleteModal(button) {
    const userId = button.getAttribute("data-user-id");
    const username = button.getAttribute("data-username");

    document.getElementById("deleteUserId").value = userId;
    document.getElementById("deleteUsername").value = username;
    document.getElementById("deleteUsernameDisplay").textContent = username;
    document.getElementById("confirmUsername").value = "";
    document.getElementById("usernameError").style.display = "none";
    document
        .getElementById("confirmUsername")
        .classList.remove("border-danger");

    var modal = new bootstrap.Modal(
        document.getElementById("deleteUserModal")
    );
    modal.show();
}

function confirmDelete() {
    const username = document.getElementById("deleteUsername").value;
    const confirmUsername =
        document.getElementById("confirmUsername").value;
    const userId = document.getElementById("deleteUserId").value;

    if (confirmUsername === username) {
        document.getElementById("deleteUserForm").action =
            "/profile/delete-user/" + userId;
        document.getElementById("deleteUserForm").submit();
    } else {
        document.getElementById("usernameError").style.display = "block";
        document
            .getElementById("confirmUsername")
            .classList.add("border-danger");
    }
}

const confirmUsernameInput = document.getElementById("confirmUsername");
if (confirmUsernameInput) {
    confirmUsernameInput.addEventListener("input", function () {
        document.getElementById("usernameError").style.display = "none";
        this.classList.remove("border-danger");
    });
}
