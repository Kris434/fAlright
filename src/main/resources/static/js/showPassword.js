var passwordInput = document.getElementById('password-input');
var rPasswordInput = document.getElementById('repeat-password-input');
var togglePassword = document.getElementById('toggle-password');
var toggleRPassword = document.getElementById('toggle-rpassword')

togglePassword.addEventListener('mousedown', function () {
    passwordInput.type = 'text';
});

togglePassword.addEventListener('mouseup', function () {
    passwordInput.type = 'password';
});
togglePassword.addEventListener('mouseout', function () {
    passwordInput.type = 'password';
});

toggleRPassword.addEventListener('mousedown', function () {
    rPasswordInput.type = 'text';
});

toggleRPassword.addEventListener('mouseup', function () {
    rPasswordInput.type = 'password';
});
toggleRPassword.addEventListener('mouseout', function () {
    rPasswordInput.type = 'password';
});