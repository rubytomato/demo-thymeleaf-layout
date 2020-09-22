$(function() {
    'use strict'
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