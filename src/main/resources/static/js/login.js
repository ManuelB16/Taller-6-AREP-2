document.getElementById("login-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include", // Importante para mantener la sesión
        body: JSON.stringify({ username, password }) // Enviar JSON
    });

    if (response.ok) {
        window.location.href = "home.html"; // Redirigir si el login es exitoso
    } else {
        alert("Error en el login");
    }
});
