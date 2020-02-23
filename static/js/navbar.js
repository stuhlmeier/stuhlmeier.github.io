const
    nw = document.getElementById("navbar-wrapper"),
    na = document.getElementById("navbar-arrow");

let navbarOpen = false;

function toggleNavbar() {
    try {
        if (!navbarOpen) {
            na.classList.add("clicked");
        } else {
            na.classList.remove("clicked");
        }
    } finally {
        navbarOpen = !navbarOpen;
    }
}

nw.addEventListener("click", toggleNavbar);
