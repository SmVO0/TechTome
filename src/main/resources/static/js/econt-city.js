(function () {
    const searchInput    = document.getElementById('citySearch');
    const dropdown       = document.getElementById('cityDropdown');
    const cityInput      = document.getElementById('deliveryCity');
    const postCodeInput  = document.getElementById('deliveryPostCode');
    const postCodeDisplay = document.getElementById('postCodeDisplay');
    const form           = document.querySelector('.checkout-form');

    const downstreamIds  = ['deliveryStreet', 'deliveryNum', 'deliveryOther'];

    if (!searchInput) return;

    function setDownstreamLocked(locked) {
        downstreamIds.forEach(function (id) {
            var el = document.getElementById(id);
            if (!el) return;
            el.disabled = locked;
            el.style.opacity = locked ? '0.45' : '';
            el.style.cursor  = locked ? 'not-allowed' : '';
        });
        var streetDrop = document.getElementById('streetDropdown');
        if (streetDrop) streetDrop.hidden = true;
    }

    // Lock on load unless a city is already pre-filled (flash-back on validation error)
    setDownstreamLocked(!cityInput.value);

    let debounceTimer;

    searchInput.addEventListener('input', function () {
        clearTimeout(debounceTimer);
        cityInput.value = '';
        postCodeInput.value = '';
        postCodeDisplay.textContent = '';
        setDownstreamLocked(true);
        const q = searchInput.value.trim();
        if (q.length < 2) {
            dropdown.hidden = true;
            dropdown.innerHTML = '';
            return;
        }
        debounceTimer = setTimeout(function () { fetchCities(q); }, 250);
    });

    function fetchCities(q) {
        fetch('/api/econt/cities?q=' + encodeURIComponent(q))
            .then(function (r) { return r.json(); })
            .then(function (cities) {
                dropdown.innerHTML = '';
                if (!cities.length) {
                    dropdown.hidden = true;
                    return;
                }
                cities.forEach(function (city) {
                    let li = document.createElement('li');
                    li.textContent = city.name + ' (' + city.postCode + ')';
                    li.addEventListener('click', function () {
                        searchInput.value     = city.name + ' (' + city.postCode + ')';
                        cityInput.value       = city.name;
                        postCodeInput.value   = city.postCode;
                        postCodeDisplay.textContent = 'Postal code: ' + city.postCode;
                        dropdown.hidden = true;
                        searchInput.setCustomValidity('');
                        setDownstreamLocked(false);
                        fetch('/api/econt/streets/preload?city=' + encodeURIComponent(city.name))
                            .catch(function () {});
                    });
                    dropdown.appendChild(li);
                });
                dropdown.hidden = false;
            })
            .catch(function () { dropdown.hidden = true; });
    }

    document.addEventListener('click', function (e) {
        if (!searchInput.contains(e.target) && !dropdown.contains(e.target)) {
            dropdown.hidden = true;
        }
    });

    form.addEventListener('submit', function (e) {
        var homeFields = document.getElementById('homeDeliveryFields');
        var isOfficeMode = homeFields && homeFields.style.display === 'none';
        if (!isOfficeMode && !cityInput.value) {
            e.preventDefault();
            searchInput.setCustomValidity('Please select a city from the list.');
            searchInput.reportValidity();
        }
    });

    searchInput.addEventListener('input', function () {
        searchInput.setCustomValidity('');
    });
}());
