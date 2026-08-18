(function () {
    var radios        = document.querySelectorAll('input[name="deliveryTypeRadio"]');
    var homeSection   = document.getElementById('homeDeliveryFields');
    var officeSection = document.getElementById('officeDeliveryFields');
    var officeInput   = document.getElementById('deliveryOfficeCode');
    var officeSearch  = document.getElementById('officeSearch');
    var officeDropdown = document.getElementById('officeDropdown');
    var officeDisplay = document.getElementById('officeDisplay');

    var homeRequiredIds = ['deliveryStreet', 'deliveryNum'];
    var debounceTimer;

    function setHomeMode() {
        homeSection.style.display   = '';
        officeSection.style.display = 'none';
        homeRequiredIds.forEach(function (id) {
            var el = document.getElementById(id);
            if (el) el.required = true;
        });
        var cityEl = document.getElementById('citySearch');
        if (cityEl) { cityEl.required = true; cityEl.setCustomValidity(''); }
        if (officeSearch) officeSearch.required = false;
        if (officeInput) officeInput.value = '';
    }

    function setOfficeMode() {
        homeSection.style.display   = 'none';
        officeSection.style.display = '';
        homeRequiredIds.forEach(function (id) {
            var el = document.getElementById(id);
            if (el) el.required = false;
        });
        var cityEl = document.getElementById('citySearch');
        if (cityEl) { cityEl.required = false; }
        if (officeSearch) officeSearch.required = true;
    }

    radios.forEach(function (radio) {
        radio.addEventListener('change', function () {
            if (this.value === 'OFFICE') setOfficeMode();
            else setHomeMode();
        });
    });

    setHomeMode();

    if (!officeSearch || !officeDropdown) return;

    officeSearch.addEventListener('input', function () {
        clearTimeout(debounceTimer);
        var q = officeSearch.value.trim();
        if (q.length < 2) { officeDropdown.hidden = true; return; }
        debounceTimer = setTimeout(function () {
            fetch('/api/econt/offices?q=' + encodeURIComponent(q))
                .then(function (r) { return r.json(); })
                .then(function (offices) {
                    officeDropdown.innerHTML = '';
                    if (!offices.length) { officeDropdown.hidden = true; return; }
                    offices.forEach(function (o) {
                        var li = document.createElement('li');
                        li.textContent = o.city + ' — ' + o.name;
                        li.addEventListener('mousedown', function (e) {
                            e.preventDefault();
                            officeSearch.value  = o.city + ' — ' + o.name;
                            officeInput.value   = o.code;
                            officeDisplay.textContent = 'Office code: ' + o.code;
                            officeDropdown.hidden = true;
                        });
                        officeDropdown.appendChild(li);
                    });
                    officeDropdown.hidden = false;
                })
                .catch(function () { officeDropdown.hidden = true; });
        }, 300);
    });

    officeSearch.addEventListener('blur', function () {
        setTimeout(function () { officeDropdown.hidden = true; }, 150);
    });

    officeSearch.addEventListener('focus', function () {
        if (officeDropdown.children.length) officeDropdown.hidden = false;
    });
}());
