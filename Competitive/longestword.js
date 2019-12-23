function longestWord(sen) {
    const senArr = sen.match(/\b(\w+)\b/g);
    senArr.sort((w1, w2) => w1.length < w2.length ? 1 : -1);
    console.log("Here are your words sorted from longest to shortest:");
    senArr.forEach(word => console.log(word));
}

// Below is for getting user inputs because Node CLI prompting
// is a piece of shit
const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
})

readline.question("Enter sentence: ", (sen) => {
    longestWord(sen);
    readline.close();
})
