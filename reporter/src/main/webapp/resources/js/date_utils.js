function getMonday(date) {
    var d = new Date(date);
    var day = d.getDay(),
        diff = d.getDate() - day + (day == 0 ? -6:1); // adjust when day is sunday
    return d.setDate(diff);
}

function firstDayOfMonth(date){
    y = date.getFullYear();
    m = date.getMonth();
    return new Date(y, m, 1);
}