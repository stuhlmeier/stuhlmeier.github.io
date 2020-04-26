let light = true;
window.theme = "theme-light";

const themeObservers = [];
function triggerThemeObservers(oldTheme, newTheme) {
    themeObservers.forEach(o => o(oldTheme, newTheme));
}

window.addThemeObserver = function (o) {
    themeObservers.push(o);
};

function toggleTheme() {
    const html = document.documentElement;

    light = !light;
    if (light) {
        html.classList.remove("theme-dark");
        html.classList.add("theme-light");
        window.theme = "theme-light";
        triggerThemeObservers("theme-dark", "theme-light");
    } else {
        html.classList.remove("theme-light");
        html.classList.add("theme-dark");
        window.theme = "theme-dark";
        triggerThemeObservers("theme-light", "theme-dark");
    }
    localStorage.setItem("theme", light ? "light" : "dark");
}

if (localStorage.getItem("theme") === "dark") toggleTheme();

window.addEventListener("load", () => {
    document.getElementById("theme-switcher").addEventListener("click", () => {
        toggleTheme();
    });
});
