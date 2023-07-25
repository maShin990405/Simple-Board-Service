var flag_username = false;
var flag_password = false;
var flag_nickname = false;

function checkInputs() {
    if (flag_username && flag_password && flag_nickname) {
        alert("Sign up successfully!");
    }

    else {
        alert("Username or/and email already exists, or please check your inputs again.");
    }

    flag_username = false;
    flag_password = false;
    flag_nickname = false;
}

function validatePassword_helper(pw) {
    var special = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
    var lowercase = /[a-z]/;
    var uppercase = /[A-Z]/;
    var number = /[0-9]/;
    var result = special.test(pw) && lowercase.test(pw) && uppercase.test(pw) && number.test(pw);
    return result;
}

function validateUsername() {
    var username = document.getElementById('username');
    var username_error = document.getElementById('username_error');

    if (username.value.length < 6 || username.value.length > 12) {
        username_error.innerHTML = "Must be combination of alphabet, number," +
        "and special character, and at least 6 characters and at most 12 characters.";
        flag_username = false;
    }

    else {
        username_error.innerHTML = "";
        flag_username = true;
    }
}

function validatePassword() {
    var password = document.getElementById('password');
    var password_error = document.getElementById('password_error');

    if (password.value.length < 8 || password.value.length > 32) {
        password_error.innerHTML = "Must be at least 8 characters " +
                                    "and at most 32 characters.";
        flag_password = false;
    }

    else {
        if (!validatePassword_helper(password.value.trim())) {
            password_error.innerHTML = "Must contain at least 1 lowercase and " +
                                       "uppercase alphabets, 1 number, and 1 character.";
            flag_password = false;
        }

        else {
            password_error.innerHTML = "";
            flag_password = true;
        }
    }
}

function validateNickname() {
    var nickname = document.getElementById('nickname');
    var nickname_error = document.getElementById("nickname_error");

    if (nickname.value.length < 1 || nickname.value.length > 12) {
        nickname_error.innerHTML = "Must be at least 1 character " +
                                    "and at most 12 characters.";
        flag_nickname = false;
    }

    else {
        nickname_error.innerHTML = "";
        flag_nickname = true;
    }
}
