$(document).ready(function() {
    const form = $('form');
    const formEl = form[0];
    const select = $('#guestsInput');
    const success = $('#successMessage');

    // Инициализация полей
    select.val('');
    select.prop('selectedIndex', 0);
    select.removeClass('has-value');
    select.css('color', '#6c757d');

    // Единая функция для управления иконками ошибок
    function updateErrorIcon(element, showError) {
        const wrapper = element.closest('.form-floating, .guests-select-wrapper');
        const icon = wrapper.find('.invalid-icon-custom');
        element.removeClass('is-invalid');

        if (showError) {
            if (icon.length === 0) {
                wrapper.append(`
                    <i class="bi bi-exclamation-circle-fill invalid-icon-custom"
                       style="position: absolute; right: 12px; top: 50%;
                              transform: translateY(-50%); color: #dc3545; z-index: 5;">
                    </i>
                `);
            }
        } else {
            icon.remove();
        }
    }

    // Маска для телефона
    $('#phoneInput').inputmask({
        mask: '8(999)999-99-99',
        placeholder: '8(9__)___-__-__',
        showMaskOnHover: false,
        clearIncomplete: true,
        clearMaskOnLostFocus: true
    });

    // Обработчики изменений для всех полей
    function setupFieldValidation(selector) {
        $(selector).on('input change', function() {
            if ($(this).val().trim()) {
                $(this).removeClass('is-invalid')
                    .next('.invalid-feedback').remove();
                updateErrorIcon($(this), false);
            }
        });
    }

    setupFieldValidation('#nameInput');
    setupFieldValidation('#phoneInput');
    setupFieldValidation('#guestsInput');

    // Инициализация datepicker
    flatpickr('.datepicker', {
        locale: "ru",
        minDate: "today",
        dateFormat: "d.m.Y",
        theme: "light",
        onChange: function() {
            $('#dateInput').siblings('label').css('opacity', '0');
            $('#dateInput').removeClass('is-invalid')
                .next('.invalid-feedback').remove();
            updateErrorIcon($('#dateInput'), false);
        }
    });

    // Инициализация timepicker
    flatpickr('.timepicker', {
        locale: "ru",
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
        minTime: "10:00",
        maxTime: "23:00",
        minuteIncrement: 15,
        theme: "light",
        onChange: function() {
            $('#timeInput').siblings('label').css('opacity', '0');
            $('#timeInput').removeClass('is-invalid')
                .next('.invalid-feedback').remove();
            updateErrorIcon($('#timeInput'), false);
        }
    });

    // Обработка select гостей
    select.on('change', function() {
        const isFilled = !!this.value;
        $(this).toggleClass('has-value', isFilled)
            .css('color', isFilled ? '#212529' : '#6c757d');
    });

    // Отправка формы
    form.on('submit', function(event) {
        event.preventDefault();

        // Проверка полей
        const fields = [
            { el: '#nameInput', val: $('#nameInput').val().trim(), msg: '' },
            { el: '#phoneInput', val: $('#phoneInput').val().trim(), msg: '' },
            { el: '#dateInput', val: $('#dateInput').val().trim(), msg: '' },
            { el: '#timeInput', val: $('#timeInput').val().trim(), msg: '' },
            { el: '#guestsInput', val: select.val(), msg: '' }
        ];

        let allValid = true;
        $('.invalid-feedback').remove();
        $('.is-invalid').removeClass('is-invalid');
        $('.invalid-icon-custom').remove();

        fields.forEach(field => {
            if (!field.val) {
                allValid = false;
                const $el = $(field.el);
                $el.addClass('is-invalid')
                    .after(`<div class="invalid-feedback">${field.msg}</div>`);
                updateErrorIcon($el, true);
            }
        });

        if (!allValid) {
            $('.is-invalid').first()[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
            return;
        }

        // AJAX запрос
        $.ajax({
            url: '/reservation',
            type: 'POST',
            data: form.serialize()
        })
            .done(() => {
                success.fadeIn();
                formEl.reset();
                select.val('');
                select.prop('selectedIndex', 0);
                select.css('color', '#6c757d');
                $('#dateInput, #timeInput').siblings('label').css('opacity', '1');
                select.removeClass('has-value');
                setTimeout(() => success.fadeOut(), 5000);
            })
            .fail(() => {
                alert('Ошибка при отправке заявки. Попробуйте ещё раз.');
            });
    });
});