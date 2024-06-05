var passwordFields = document.querySelectorAll('.password-field');


passwordFields.forEach(function (passwordField) {
    var toggleButton = passwordField.nextElementSibling;

    toggleButton.addEventListener('mousedown', function () {
        passwordField.type = 'text';
    });

    toggleButton.addEventListener('mouseup', function () {
        passwordField.type = 'password';
    });

    toggleButton.addEventListener('mouseout', function () {
        passwordField.type = 'password';
    });
});