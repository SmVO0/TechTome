(function () {
    'use strict';

    function formatPrice(value) {
        return '€' + Number(value).toFixed(2);
    }

    function updateCartTotals(newCartTotal) {
        var formatted = formatPrice(newCartTotal);
        document.querySelectorAll('.cart-total-value, .cart-subtotal-value').forEach(function (el) {
            el.textContent = formatted;
        });
    }

    document.addEventListener('submit', function (e) {
        let form = e.target;

        if (!form.closest('.cart-qty-controls')) return;

        e.preventDefault();

        let isAdd = form.action.includes('/shopping/add');
        let ajaxUrl = isAdd ? '/shopping/add/ajax' : '/shopping/remove/ajax';
        let row = form.closest('tr');

        fetch(ajaxUrl, {
            method: 'POST',
            body: new URLSearchParams(new FormData(form)),
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })
        .then(function (response) {
            if (!response.ok) throw new Error('Request failed');
            return response.json();
        })
        .then(function (data) {
            if (data.itemRemoved) {
                if (row) row.remove();

                if (document.querySelector('.cart-table')?.querySelectorAll('tbody tr').length === 0) {
                    location.reload();
                }
            } else if (row) {
                let qtySpan = row.querySelector('.cart-qty-value');
                if (qtySpan) qtySpan.textContent = data.newQuantity;

                let subtotalCell = row.querySelector('.cart-item-subtotal');
                if (subtotalCell) subtotalCell.textContent = formatPrice(data.newSubtotal);
            }

            updateCartTotals(data.newCartTotal);
        })
        .catch(function () {
            location.reload();
        });
    });
}());
