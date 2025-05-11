document.addEventListener('DOMContentLoaded', function() {
    const form       = document.getElementById('CUForm');
    const nameInput  = document.getElementById('name');
    const priceInput = document.getElementById('price');
    const urlInput   = document.getElementById('imageUrl');

    // 1) Интерактивная валидация по input:

    nameInput.addEventListener('input', () => {
        if (nameInput.value.trim() === '') {
            nameInput.classList.add('is-invalid');
            nameInput.classList.remove('is-valid');
        } else {
            nameInput.classList.remove('is-invalid');
            nameInput.classList.add('is-valid');
        }
    });

    priceInput.addEventListener('input', () => {
        const v = priceInput.value.trim();
        if (v === '' || Number(v) < 0) {
            priceInput.classList.add('is-invalid');
            priceInput.classList.remove('is-valid');
        } else {
            priceInput.classList.remove('is-invalid');
            priceInput.classList.add('is-valid');
        }
    });

    urlInput.addEventListener('input', () => {
        const u = urlInput.value.trim();
        if (u === '' ||
            !/^https?:\/\/.+\..+$/.test(u) ||
            !/\.(jpg|jpeg|png|gif)$/i.test(u)) {
            urlInput.classList.add('is-invalid');
            urlInput.classList.remove('is-valid');
        } else {
            urlInput.classList.remove('is-invalid');
            urlInput.classList.add('is-valid');
        }
    });

    // 2) Проверка при отправке формы:

    form.addEventListener('submit', function(e) {
        let formIsValid = true;

        // Название не пустое
        if (nameInput.value.trim() === '') {
            nameInput.classList.add('is-invalid');
            formIsValid = false;
        }

        // Цена не пустая и не отрицательная
        const priceVal = priceInput.value.trim();
        if (priceVal === '' || Number(priceVal) < 0) {
            priceInput.classList.add('is-invalid');
            formIsValid = false;
        }

        // URL: не пустой, корректный формат и расширение
        const urlVal = urlInput.value.trim();
        if (urlVal === '' ||
            !/^https?:\/\/.+\..+$/.test(urlVal) ||
            !/\.(jpg|jpeg|png|gif)$/i.test(urlVal)) {
            urlInput.classList.add('is-invalid');
            formIsValid = false;
        }

        if (!formIsValid) {
            e.preventDefault();
            // Прокрутить к первому полю с ошибкой
            const firstInvalid = form.querySelector('.is-invalid');
            if (firstInvalid) {
                firstInvalid.scrollIntoView({behavior: 'smooth', block: 'center'});
            }
        }
    });
});