const response = await fetch('http://localhost:8080/csrf-token');
const csrfToken = await response.json();
console.log(csrfToken);

const submitButton = document.getElementById('submit');
submitButton.onsubmit = (e) =>
    e.preventDefault();
    fetch('http://localhost:8080/csrf-success', {
    headers: {
        'X-CSRF-Token': csrfToken.token
    },
    method: 'POST'
});
