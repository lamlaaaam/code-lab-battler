// Input an array of stair heights e.g. [3,2,8,4] --> output array of no. of ways
// Each step is 1 or 2.
function multistairs(arr) {
    // Global memory, index is stair height, element is no. of ways
    const mem = [1, 1];
    function stairs(N) {
        if (N === 0 || N === 1) {
            return 1;
        } else {
            const climb1 = stairs(N - 1);
            const climb2 = stairs(N - 2);
            mem[N] = climb1 + climb2;
            return climb1 + climb2;
        }
    }
    // Get largest stairs in array
    const len = arr.length;
    let largest = -Infinity;
    for (let i = 0; i < len; i++) {
        largest = arr[i] > largest ? arr[i] : largest;
    } 
    stairs(largest);
    // Create array for no. of ways
    const resultArr = [];
    for (let i = 0; i < len; i++) {
        resultArr[i] = mem[arr[i]];
    }
    return resultArr;
}

const res = multistairs([3,4,2,5]);
console.log(res);
