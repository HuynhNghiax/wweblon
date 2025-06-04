document.addEventListener('DOMContentLoaded', function() {
    console.log(document.getElementById('searchInput')); // thêm dòng này
    let timer;
    const searchInput = document.getElementById('searchInput');
    const suggestionsDiv = document.getElementById('suggestions');

    searchInput.addEventListener('input', function() {
        clearTimeout(timer);
        const query = this.value.trim();

        if (query.length === 0) {
            suggestionsDiv.innerHTML = '';
            return;
        }

        timer = setTimeout(() => {
            fetch('SearchSuggestions?query=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(data => {
                    renderSuggestions(data);
                })
                .catch(error => {
                    console.error('Error fetching suggestions:', error);
                });
        }, 3000);
    });

    function renderSuggestions(suggestions) {
        if (suggestions.length === 0) {
            suggestionsDiv.innerHTML = '<div class="p-2 text-muted">No results</div>';
            return;
        }
        suggestionsDiv.innerHTML = suggestions.map(item =>
            `<div class="p-2 border-bottom">${item}</div>`
        ).join('');
    }

    suggestionsDiv.addEventListener('click', function(e) {
        if (e.target && e.target.matches('div')) {
            searchInput.value = e.target.innerText;
            suggestionsDiv.innerHTML = '';
        }
    });
});
