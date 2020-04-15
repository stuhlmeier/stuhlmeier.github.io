let gridOn = false;

const switchers = document.querySelectorAll(".view-switcher .button");
const views = document.querySelectorAll(".skills");

function setViewMode(useGrid) {
    gridOn = useGrid;
    for (const switcher of switchers) {
        switcher.innerHTML = gridOn ? "grid_on" : "grid_off";
    }

    for (const view of views) {
        if (view.classList.contains("small")) continue;
        else if (gridOn) {
            view.classList.remove("list");
            view.classList.add("grid");
        } else {
            view.classList.remove("grid");
            view.classList.add("list");
        }
    }
}

function toggleViewMode() {
    setViewMode(!gridOn);
}

for (const switcher of document.querySelectorAll(".view-switcher .button")) {
    switcher.addEventListener("click", toggleViewMode);
}

// Default to list view on small displays
// Keeping the branch for readability.
if (window.matchMedia("(max-width: 768px)").matches) {
    setViewMode(false);
} else {
    setViewMode(true);
}
