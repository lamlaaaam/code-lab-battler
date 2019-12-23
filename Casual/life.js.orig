function make_cell(r, c) {
    return [r, c];
}
function col(cell) {
    return cell[1];
}
function row(cell) {
    return cell[0];
}
function cell_value(cell, board) {
    const maxRows = board.length;
    const maxCols = board[0].length;
    function inBounds(cell, board) {
        return row(cell) >= 0 && row(cell) < maxRows && 
               col(cell) >= 0 && col(cell) < maxCols;
    }
    return inBounds(cell, board) ? board[row(cell)][col(cell)]
                                 : undefined;
}
function live_cells_around(cell, board) {
    let liveCount = 0;
    for (let r = row(cell) - 1; r < row(cell) + 2; r++) {
        for (let c = col(cell) - 1; c < col(cell) + 2; c++) {
            if (!(r === row(cell) && c === col(cell))) {
                const adj_cell = make_cell(r, c);
                liveCount += cell_value(adj_cell, board) !== undefined
                           ? cell_value(adj_cell, board)
                           : 0;
            }
        }
    }
    return liveCount;
}
function cell_next_state(cell, board) {
    const live_cells_adj = live_cells_around(cell, board);
    return live_cells_adj < 2 || live_cells_adj > 3 ? 0
         : cell_value(cell, board) === 0 && live_cells_adj === 2 ? 0
         : 1;
}
function board_next_state(board) {
    const maxRows = board.length;
    const maxCols = board[0].length;
    const next_state = [];
    for (let r = 0; r < maxRows; r++) {
        next_state[r] = [];
        for (let c = 0; c < maxCols; c++) {
            next_state[r][c] = cell_next_state(make_cell(r, c), board);
        }
    }
    return next_state;
}
function update_board(board) {
    board = board_next_state(board); 
    console.clear();
    console.table(board);
    return board;
}
function get_input() {
    const board = [];
    let keepReading = true;
    function line_to_array(line) {
        if (line === '') { 
            keepReading = false;
        } else {
            board[board.length] = Array.from(line, Number);
        }
    }
    const readline = require('readline').createInterface({
      input: process.stdin,
      output: process.stdout
    })
    console.clear();
    console.log("Enter a string of 1s & 0s for live and dead cells respectively.\nTerminate entry with empty string:")
    function read() {
        readline.question("", (line) => {
            line_to_array(line); 
            if (keepReading) {
                read();
            } else {
                readline.close();
                run(board, 100);
            }
        })
    }
    read();
}
function run(board, ticks) {
    board = update_board(board);
    setTimeout(() => run(board, ticks), ticks);
}

get_input();
