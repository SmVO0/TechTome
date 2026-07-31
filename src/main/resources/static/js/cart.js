(function () {
    'use strict';

    /* ---- Toast helper ---- */
    function showToast(message, isError) {
        let container = document.getElementById('toast-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toast-container';
            document.body.appendChild(container);
        }

        let toast = document.createElement('div');
        toast.className = isError ? 'toast toast-error' : 'toast';
        toast.textContent = message;
        container.appendChild(toast);

        // Slide in on next frame
        requestAnimationFrame(function () {
            requestAnimationFrame(function () {
                toast.classList.add('toast-visible');
            });
        });

        // Slide out after 3 s then remove
        setTimeout(function () {
            toast.classList.remove('toast-visible');
            toast.addEventListener('transitionend', function handler() {
                toast.removeEventListener('transitionend', handler);
                toast.remove();
            });
        }, 3000);
    }

    /* ---- Intercept every "Add to Cart" form on the page ---- */
    document.addEventListener('submit', function (e) {
        let form = e.target;

        // Only intercept our add-to-cart forms
        if (!form.action?.includes('/shopping/add')) {
            return;
        }

        e.preventDefault();

        let itemName = form.dataset('data-item-name') || 'Item';

        fetch(form.action, {
            method: 'POST',
            body: new URLSearchParams(new FormData(form)),
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })
        .then(function (response) {
            if (response.ok) {
                showToast(itemName + ' added to cart', false);
            } else {
                showToast('Could not add item. Please try again.', true);
            }
        })
        .catch(function () {
            showToast('Network error. Please try again.', true);
        });
    });
}());
