// Given a string, print occurrence of each letter

function countLetters(str) {
    const rep = {};
    for (let i = 0; i < str.length; i++) {
        rep[str.charAt(i)] = rep[str.charAt(i)] === undefined ? 1 : rep[str.charAt(i)] + 1;
    }
    for (let l in rep) {
        console.log(l + " appears " + rep[l].toString() + " time(s).");
    }
}
countLetters("abjdwoiancjznmvnfdsnrwiocjkns");
