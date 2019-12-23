function read_hist(hist) {
    let highestFreq = 0;
    let freqOfTwo = false;
    for (let d = 1; d <= 6; d++) {
        const freq = hist[d];
        highestFreq = freq > highestFreq ? freq : highestFreq;
        freqOfTwo = freqOfTwo ? true 
                              : freq === 2;
    }
    return highestFreq === 5 ? "You Win! (Grand)"
         : highestFreq === 4 ? "You Win! (Poker)"
         : highestFreq === 3 && freqOfTwo ? "You Win! (Full House)"
         : "No Win :(";
}

function display_dice_table(dice_table) {
    console.log("----------------------------");
    let diceStr = "| Dice | ";
    let rollStr = "| Rolls| ";
    for (let i = 1; i <= 5; i++) {
        diceStr += i.toString() + " | ";
        rollStr += dice_table[i].toString() + " | ";
    }
    console.log(diceStr + "\n" + rollStr);
    console.log("----------------------------");
}

function get_input() {
    const hist = [0,0,0,0,0,0,0];
    const rolls = [0];
    function addToHist(dice) {
        hist[dice] += 1;
    }
    const readline = require('readline').createInterface({
      input: process.stdin,
      output: process.stdout
    })
    console.clear();
    function read(diceNo) {
        readline.question("Dice " + diceNo.toString() + ": ", (dice) => {
            rolls[rolls.length] = dice;
            addToHist(dice);
            if (diceNo < 5) {
                read(diceNo + 1);
            } else {
                readline.close();
                console.clear();
                display_dice_table(rolls);
                console.log(read_hist(hist) );
            }
        })
    }
    read(1);
}

get_input();
