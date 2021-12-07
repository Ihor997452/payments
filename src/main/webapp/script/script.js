let loginForm = document.getElementById("login");
let registerForm = document.getElementById("register");

document.getElementById("signIn").onclick = function () {
    if (registerForm.classList.contains("visible")) {
        registerForm.classList.toggle("visible");
        registerForm.classList.toggle("invisible");
    }

    loginForm.classList.toggle("invisible");
    loginForm.classList.toggle("visible");
}

document.getElementById("signUp").onclick = function () {
    if (loginForm.classList.contains("visible")) {
        loginForm.classList.toggle("visible");
        loginForm.classList.toggle("invisible");
    }

    registerForm.classList.toggle("invisible");
    registerForm.classList.toggle("visible");
}