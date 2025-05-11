document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('CUForm');
    const fullNameInput = document.getElementById('fullName');
    const positionInput = document.getElementById('position');
    const phoneInput = document.getElementById('phone');
    const urlInput = document.getElementById('imageUrl');

    const initialPhoneValue = phoneInput.value || '8';
    phoneInput.dataset.initialValue = initialPhoneValue;

    // Инициализация поля телефона с предустановленной "8"
    if (!phoneInput.value || phoneInput.value.trim() === '') {
        phoneInput.value = '8';
    } else {
        phoneInput.value = formatPhoneNumber(phoneInput.value);
    }

    // Функция для форматирования телефона
    function formatPhoneNumber(phone) {
        // Удаляем все нецифровые символы
        const cleaned = ('' + phone).replace(/\D/g, '');

        // Ограничиваем 11 цифрами (8 + 10 цифр номера)
        const limited = cleaned.length > 11 ? cleaned.substring(0, 11) : cleaned;

        // Форматируем номер
        const match = limited.match(/^(\d{1})(\d{3})(\d{3})(\d{2})(\d{2})$/);

        if (match) {
            return `${match[1]} (${match[2]}) ${match[3]}-${match[4]}-${match[5]}`;
        }
        return limited;
    }

    // Маска для телефона
    phoneInput.addEventListener('input', function(e) {
        const cursorPosition = e.target.selectionStart;
        const inputValue = e.target.value;

        // Удаляем все нецифровые символы
        let cleaned = inputValue.replace(/\D/g, '');

        // Запрещаем удаление начальной "8"
        if (cleaned.length === 0) {
            e.target.value = '8';
            return;
        }

        // Ограничиваем 11 цифрами
        cleaned = cleaned.length > 11 ? cleaned.substring(0, 11) : cleaned;

        // Форматируем номер
        let formatted = '8'; // Всегда начинаем с 8
        if (cleaned.length > 1) {
            const rest = cleaned.substring(1);
            formatted = rest.replace(/^(\d{0,3})(\d{0,3})(\d{0,2})(\d{0,2})/, function(_, p1, p2, p3, p4) {
                let result = '8';
                if (p1) result += ` (${p1}`;
                if (p2) result += `) ${p2}`;
                if (p3) result += `-${p3}`;
                if (p4) result += `-${p4}`;
                return result;
            });
        }

        // Устанавливаем отформатированное значение
        e.target.value = formatted;

        // Восстанавливаем позицию курсора
        const diff = formatted.length - inputValue.length;
        e.target.setSelectionRange(cursorPosition + diff, cursorPosition + diff);

        // Валидация
        if (formatted !== phoneInput.dataset.initialValue) {
            validatePhone(formatted);
        } else {
            phoneInput.classList.remove('is-valid', 'is-invalid');
        }
    });

    // Запрещаем удаление начальной "8"
    phoneInput.addEventListener('keydown', function(e) {
        if ((e.key === 'Backspace' || e.key === 'Delete') &&
            phoneInput.value.length <= 1) {
            e.preventDefault();
        }
    });

    // Валидация телефона
    function validatePhone(phone) {
        const phoneRegex = /^8 \(\d{3}\) \d{3}-\d{2}-\d{2}$/;
        if (!phoneRegex.test(phone)) {
            phoneInput.classList.add('is-invalid');
            phoneInput.classList.remove('is-valid');
            return false;
        } else {
            phoneInput.classList.remove('is-invalid');
            phoneInput.classList.add('is-valid');
            return true;
        }
    }

    // Валидация ФИО
    fullNameInput.addEventListener('input', function() {
        validateField(fullNameInput, fullNameInput.value.trim() !== '');
    });

    // Валидация должности
    positionInput.addEventListener('input', function() {
        validateField(positionInput, positionInput.value.trim() !== '');
    });

    // Валидация URL изображения
    urlInput.addEventListener('input', function() {
        const u = urlInput.value.trim();
        const isValid = u !== '' &&
            /^https?:\/\/.+\..+$/.test(u) &&
            /\.(jpg|jpeg|png|gif)$/i.test(u);
        validateField(urlInput, isValid);
    });

    // Общая функция валидации поля
    function validateField(field, isValid) {
        if (isValid) {
            field.classList.remove('is-invalid');
            field.classList.add('is-valid');
        } else {
            field.classList.add('is-invalid');
            field.classList.remove('is-valid');
        }
    }

    // Валидация при отправке формы
    form.addEventListener('submit', function(e) {
        // Форматируем телефон перед отправкой
        phoneInput.value = formatPhoneNumber(phoneInput.value);

        let formIsValid = true;

        // Проверка ФИО
        if (fullNameInput.value.trim() === '') {
            fullNameInput.classList.add('is-invalid');
            formIsValid = false;
        }

        // Проверка должности
        if (positionInput.value.trim() === '') {
            positionInput.classList.add('is-invalid');
            formIsValid = false;
        }

        // Проверка телефона
        // Проверка телефона (только если изменен)
        if (phoneInput.value !== phoneInput.dataset.initialValue) {
            if (!validatePhone(phoneInput.value)) {
                formIsValid = false;
            }
        } else {
            phoneInput.classList.remove('is-valid', 'is-invalid');
        }

        // Проверка URL изображения
        const urlVal = urlInput.value.trim();
        if (urlVal === '' ||
            !/^https?:\/\/.+\..+$/.test(urlVal) ||
            !/\.(jpg|jpeg|png|gif)$/i.test(urlVal)) {
            urlInput.classList.add('is-invalid');
            formIsValid = false;
        }

        if (!formIsValid) {
            e.preventDefault();
            // Прокрутка к первому невалидному полю
            const firstInvalid = form.querySelector('.is-invalid');
            if (firstInvalid) {
                firstInvalid.scrollIntoView({
                    behavior: 'smooth',
                    block: 'center'
                });
            }
        }
    });
});