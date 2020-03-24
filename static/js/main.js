let light = true;

function toggleTheme() {
    const html = document.documentElement;

    light = !light;
    if (light) {
        html.classList.remove("theme-dark");
        html.classList.add("theme-light");
    } else {
        html.classList.remove("theme-light");
        html.classList.add("theme-dark");
    }
    localStorage.setItem("theme", light ? "light" : "dark");
}

if (localStorage.getItem("theme") === "dark") toggleTheme();

window.addEventListener("load", () => {
    document.getElementById("theme-switcher").addEventListener("click", () => {
        toggleTheme();
    });
});
