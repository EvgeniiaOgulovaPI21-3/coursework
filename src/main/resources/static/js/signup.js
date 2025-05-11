document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registerForm');
    const loginInput = document.getElementById('login');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    // Добавляем обработчик для очистки ошибки логина при вводе
    loginInput.addEventListener('input', function() {
        if (this.classList.contains('is-invalid')) {
            this.classList.remove('is-invalid');
            const feedback = this.nextElementSibling.nextElementSibling;
            feedback.style.display = 'none';
        }
    });

    // Проверка при вводе (для интерактивных подсказок)
    passwordInput.addEventListener('input', validatePassword);
    confirmPasswordInput.addEventListener('input', validatePasswordMatch);

    // Основная проверка при отправке формы
    form.addEventListener('submit', function(e) {
        let isValid = true;

        // Проверка логина
        if (loginInput.value.trim() === '') {
            showError(loginInput, 'Введите логин');
            isValid = false;
        }

        // Проверка пароля
        if (passwordInput.value === '') {
            showError(passwordInput, 'Введите пароль');
            isValid = false;
        } else if (passwordInput.value.length < 6) {
            showError(passwordInput, 'Пароль должен содержать не менее 6 символов');
            isValid = false;
        }

        // Проверка подтверждения пароля
        if (confirmPasswordInput.value === '') {
            showError(confirmPasswordInput, 'Подтвердите пароль');
            isValid = false;
        } else if (passwordInput.value !== confirmPasswordInput.value) {
            showError(confirmPasswordInput, 'Пароли не совпадают');
            isValid = false;
        }

        if (!isValid) {
            e.preventDefault();
            document.querySelector('.is-invalid').scrollIntoView({
                behavior: 'smooth',
                block: 'center'
            });
        }
    });

    // Валидация пароля в реальном времени
    function validatePassword() {
        if (this.value.length > 0 && this.value.length < 6) {
            showHint(this, 'Пароль должен содержать не менее 6 символов');
        } else {
            clearHint(this);
            validatePasswordMatch();
        }
    }

    // Валидация совпадения паролей в реальном времени
    function validatePasswordMatch() {
        if (confirmPasswordInput.value !== '' &&
            passwordInput.value !== confirmPasswordInput.value) {
            showHint(confirmPasswordInput, 'Пароли не совпадают');
        } else {
            clearHint(confirmPasswordInput);
        }
    }

    function showError(input, message) {
        const feedback = input.nextElementSibling.nextElementSibling;
        input.classList.add('is-invalid');
        feedback.innerHTML = `<i class="bi bi-exclamation-circle me-1"></i>${message}`;
        feedback.style.display = 'block';
    }

    function showHint(input, message) {
        const hint = document.createElement('div');
        hint.className = 'form-text text-danger';
        hint.innerHTML = `<i class="bi bi-info-circle me-1"></i>${message}`;

        const existingHint = input.parentNode.querySelector('.form-text.text-danger');
        if (existingHint) input.parentNode.removeChild(existingHint);

        input.parentNode.appendChild(hint);
    }

    function clearHint(input) {
        const hint = input.parentNode.querySelector('.form-text.text-danger');
        if (hint) input.parentNode.removeChild(hint);
    }
});