$(function() {
    'use strict'
    console.log("app main.js");
    console.log("pathname", $(location).attr("pathname"));
    console.log("href", $(location).attr("href"));
    console.log("origin", $(location).attr("origin"));
    let currentPage = $(location).attr("pathname");
    if (currentPage.startsWith("/menu/apple")) {
        $("li.apple").addClass("active");
    } else if (currentPage.startsWith("/menu/banana")) {
        $("li.banana").addClass("active");
    } else if (currentPage.startsWith("/menu/cherry")) {
        $("li.cherry").addClass("active");
    }
})