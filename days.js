// Given 2 dates e.g. January 15, 2019 and February 28, 2019
// Return the number of days in between e.g. 44 for above

function days(date1, date2) {
    const months = {"January":1, "February":2, "March":3, "April":4, "May":5, "June": 6,
                    "July":7, "August":8, "September":9, "October":10, "November":11, "December": 12};
    const daysInMonth = [];
    function initializeDaysInMonth() {
        for (let i = 1; i <= 12; i++) {
            if (i === 2) { daysInMonth[i] = 28; }
            else if ((i <= 7 && (i % 2 === 0)) || (i > 7 && !(i % 2 === 0))) { daysInMonth[i] = 30; }
            else { daysInMonth[i] = 31; } 
        }
    }
    function parseDate(date) {
        const parsed = date.split(" ");
        parsed[0] = months[parsed[0]];
        parsed[1] = parseInt(parsed[1].substring(0, parsed[1].length - 1), 10);
        parsed[2] = parseInt(parsed[2], 10);
        return parsed;
    }
    function earlierDate(date1, date2) {
        return date1[2] < date2[2] ? 1
             : date1[2] > date2[2] ? 2
                : date1[0] < date2[0] ? 1
                : date1[0] > date2[0] ? 2
                    : date1[1] < date2[1] ? 1
                    : 2;
    }
    function yearDiff(date1, date2) {
        const diff = date2[2] - date1[2];
        const diffDays = diff * 365;
        return diffDays + Math.floor(diff / 4);
    }
    function monthDiff(date1, date2) {
        let diff = 0;
        for (let m = date1[0]; m !== date2[0]; m += date1[0] < date2[0] ? 1 : -1) {
            const mDays = daysInMonth[m];
            diff += date1[0] < date2[0] ? mDays : -mDays;
        }
        return diff;
    }
    function dayDiff(date1, date2) {
        return date2[1] - date1[1];
    }
    const parsed1 = parseDate(date1);
    const parsed2 = parseDate(date2);
    if (earlierDate(parsed1, parsed2) === 2) {
        return days(date2, date1);
    } else {
        initializeDaysInMonth();
        return yearDiff(parsed1, parsed2) + 
               monthDiff(parsed1, parsed2) + 
               dayDiff(parsed1, parsed2);
    }
}

const res = days("January 15, 2019", "January 15, 2023");
console.log(res);