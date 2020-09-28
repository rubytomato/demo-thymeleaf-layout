$(function() {
    'use strict'
    console.log("app main.js");
    console.log("pathname", $(location).attr("pathname"));
    console.log("href", $(location).attr("href"));
    console.log("origin", $(location).attr("origin"));
    let currentPage = $(location).attr("pathname");
    if (currentPage.startsWith(window.app.context + "sub-menu/apple")) {
        $("li.apple").addClass("active");
    } else if (currentPage.startsWith(window.app.context + "sub-menu/banana")) {
        $("li.banana").addClass("active");
    } else if (currentPage.startsWith(window.app.context + "sub-menu/cherry")) {
        $("li.cherry").addClass("active");
    }
/*
    $("a.page").on("click", function(event) {
        console.log("page click", this);
        let page = $(this).data("page");
        console.log("page", page);
        $("form.page-select input[name='pageNo']").val(page);
        $("form.page-select").submit();
        event.preventDefault();
    });
 */
})
