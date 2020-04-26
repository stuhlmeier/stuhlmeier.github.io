const $flair = document.getElementById("so-flair");

function setFlairTheme(theme) {
    if (theme === "theme-dark") {
        $flair.src = "https://stackoverflow.com/users/flair/7366707.png?theme=dark";
    } else {
        $flair.src = "https://stackoverflow.com/users/flair/7366707.png";
    }
}

setFlairTheme(window.theme);

window.addThemeObserver((__, newTheme) => {
    setFlairTheme(newTheme);
});
