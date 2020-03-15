let gridOn;

const switchers = document.querySelectorAll(".view-switcher .button");
const views = document.querySelectorAll(".skills");

function setViewMode(useGrid) {
    gridOn = useGrid;
    for (const switcher of switchers) {
        switcher.innerHTML = gridOn ? "grid_on" : "grid_off";
    }

    for (const view of views) {
        if (gridOn) {
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

setViewMode(true);
