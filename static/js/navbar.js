const
    dw = document.getElementById("description-wrapper"),
    na = document.getElementById("navbar-arrow"),
    n = document.getElementById("navbar-top");

let navbarOpen = false;

function toggleNavbar() {
    try {
        if (!navbarOpen) {
            na.classList.add("clicked");
            n.classList.add("opened");
        } else {
            na.classList.remove("clicked");
            n.classList.remove("opened");
        }
    } finally {
        navbarOpen = !navbarOpen;
    }
}

dw.addEventListener("click", toggleNavbar);
na.addEventListener("click", toggleNavbar);
