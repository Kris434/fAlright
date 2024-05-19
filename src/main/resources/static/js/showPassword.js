var passwordInput = document.getElementById('password-input');
var togglePassword = document.getElementById('toggle-password');

togglePassword.addEventListener('mousedown', function () {
    passwordInput.type = 'text';
});

togglePassword.addEventListener('mouseup', function () {
    passwordInput.type = 'password';
});
togglePassword.addEventListener('mouseout', function () {
    passwordInput.type = 'password';
});