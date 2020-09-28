$(function() {
    'use strict'
    $("a.page").on("click", function(event) {
        console.log("page click", this);
        let page = $(this).data("page");
        console.log("page", page);
        $("#next input[name='_page']").val(page);

        event.preventDefault();
    });

    $("a.next-page").on("click", function() {
        console.log("a link click");
        $("#next input.additional").remove();
        let frm = $("#next");
        let tds = $(this).closest("tr").find("td");
        tds.each(function(index, ele) {
            let d = $(ele).data();
            console.log(index, d);
            $(frm).append($('<input />', {
                type: 'text',
                class: 'additional',
                name: d.name,
                value: d.value
            }));
        });
    })
})