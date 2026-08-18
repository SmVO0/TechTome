(function () {
    var streetInput  = document.getElementById('deliveryStreet');
    var streetDrop   = document.getElementById('streetDropdown');
    var cityInput    = document.getElementById('deliveryCity');

    if (!streetInput || !streetDrop || !cityInput) return;

    var debounceTimer;

    streetInput.addEventListener('input', function () {
        clearTimeout(debounceTimer);
        var city = cityInput.value.trim();
        var q    = streetInput.value.trim();

        if (!city || q.length < 2) {
            streetDrop.hidden = true;
            return;
        }

        debounceTimer = setTimeout(function () {
            fetch('/api/econt/streets?city=' + encodeURIComponent(city) +
                  '&q='    + encodeURIComponent(q))
                .then(function (r) { return r.json(); })
                .then(function (streets) {
                    streetDrop.innerHTML = '';
                    if (!streets.length) { streetDrop.hidden = true; return; }
                    streets.forEach(function (name) {
                        var li = document.createElement('li');
                        li.textContent = name;
                        li.addEventListener('mousedown', function (e) {
                            e.preventDefault();
                            streetInput.value = name;
                            streetDrop.hidden = true;
                        });
                        streetDrop.appendChild(li);
                    });
                    streetDrop.hidden = false;
                })
                .catch(function () { streetDrop.hidden = true; });
        }, 400);
    });

    streetInput.addEventListener('blur', function () {
        setTimeout(function () { streetDrop.hidden = true; }, 150);
    });

    streetInput.addEventListener('focus', function () {
        if (streetDrop.children.length) streetDrop.hidden = false;
    });

    document.addEventListener('click', function (e) {
        if (!streetInput.contains(e.target) && !streetDrop.contains(e.target)) {
            streetDrop.hidden = true;
        }
    });
}());
