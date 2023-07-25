function checkMemberLogin() {
    if (document.getElementById("username_holder").value.length > 1) {
        document.getElementById("signup_link").style.visibility = "hidden";
        document.getElementById("login_link").style.visibility = "hidden";
        document.getElementById("logout_link").style.visibility = "visible";
    }
}