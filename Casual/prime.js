function prime() {
    const mem = [];
    function mark(from, to, rep) {
        for (let i = from; i <= to; i = i + rep) {
            mem[i] = true;
        }
    }
    function findNextUnmarked(from) {
        let i = from;
        for (i = from; mem[i]; i++) {}
        return i;
    }
    function markTillLimit(limit) {
        let currPrime = 2;
        while (currPrime < limit) {
            mark(currPrime, limit, currPrime);
            const nextPrime = findNextUnmarked(currPrime);
            if (nextPrime > limit) { break; }
            else { currPrime = nextPrime; }
        }
        return currPrime;
    }
    for (let i = 10; true; i++) {
        console.log(markTillLimit(i));
    }
}

prime();
