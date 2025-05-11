document.addEventListener("DOMContentLoaded", function() {
    // Обработчик формы поиска
    const form = document.querySelector('form');
    const input = document.querySelector('.search-input');

    if (form) {
        form.addEventListener('submit', function(e) {
            if (input.value.trim() === '') {
                e.preventDefault();
                window.location.href = '/dish';
                return;
            }
        });
    }

    // Функция для разворачивания/сворачивания контента
    function toggleContent(btn, selector) {
        const card = btn.closest('.dish-card');
        const content = card.querySelector(selector);
        const icon = btn.querySelector('i');
        const willShow = (content.style.display !== 'block');
        content.style.display = willShow ? 'block' : 'none';
        icon.style.transform = willShow ? 'rotate(180deg)' : 'rotate(0deg)';
        icon.style.transition = 'transform 0.3s';
    }

    // Обработчики для кнопок описания и КБЖУ
    document.querySelectorAll('.toggle-description').forEach(btn => {
        btn.addEventListener('click', () => toggleContent(btn, '.description-content'));
    });

    document.querySelectorAll('.toggle-kbju').forEach(btn => {
        btn.addEventListener('click', () => toggleContent(btn, '.kbju-content'));
    });
});

