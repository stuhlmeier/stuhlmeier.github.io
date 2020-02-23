const descriptions = Object.freeze([
    "software developer",
    "web designer",
    "open-source lover",
    "Linux enthusiast",
    "Kotlin expert",
]);

function getRandom(array, count) {
    function shuffleArray(array) {
        const copy = [...array];
        for (let i = copy.length - 1; i > 0; --i) {
            const j = Math.floor(Math.random() * (i + 1));
            [copy[i], copy[j]] = [copy[j], copy[i]];
        }
        return copy;
    }

    return shuffleArray(array).slice(0, count);
}

const shuffle = [
    ...getRandom(descriptions.slice(1), 3),
    descriptions[0]
];

function getInitialDescription() {
    return shuffle[0];
}
