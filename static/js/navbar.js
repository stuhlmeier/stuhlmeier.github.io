const
    dw = document.getElementById("description-wrapper"),
    nw = document.getElementById("navbar-wrapper"),
    na = document.getElementById("navbar-arrow");

let navbarOpen = false;

function toggleNavbar() {
    // Ignore clicks when the display isn't wide enough
    if (window.matchMedia("(max-width: 768px)").matches) return;

    try {
        if (!navbarOpen) {
            nw.classList.add("opened");
        } else {
            nw.classList.remove("opened");
        }
    } finally {
        navbarOpen = !navbarOpen;
    }
}

dw.addEventListener("click", toggleNavbar);
na.addEventListener("click", toggleNavbar);
