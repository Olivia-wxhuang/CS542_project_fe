$("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});
$("#side-bar-my-account").click(function () {
    let x = document.getElementById("account-details-content");
    let y = document.getElementById("list-container");
    console.log('Showing account details');
    x.style.display = "block";
    y.style.display = "none";
});
$("#side-bar-my-listings").click(function () {
    let y = document.getElementById("account-details-content");
    let x = document.getElementById("list-container");
    console.log('Showing list container');
    x.style.display = "block";
    y.style.display = "none";
});
$("#side-bar-my-watchlist").click(function () {
    let y = document.getElementById("account-details-content");
    let x = document.getElementById("list-container");
    console.log('Showing list container');
    x.style.display = "block";
    y.style.display = "none";
});




