function cycleDescriptions() {
    const
        dw = document.getElementById("description-wrapper"),
        d1 = document.getElementById("description1"),
        d2 = document.getElementById("description2");

    let index = 1;
    d2.innerHTML = shuffle[index];

    d1.addEventListener("animationend", () => {
        if (index < shuffle.length - 1) {
            d1.innerHTML = d2.innerHTML;

            window.setTimeout(() => {
                // Reset animation
                d2.innerHTML = shuffle[index];
                dw.classList.remove("animated");
                void dw.offsetParent;
                dw.classList.add("animated");
            }, 0);

            index++;
        }
    });
}

cycleDescriptions();
