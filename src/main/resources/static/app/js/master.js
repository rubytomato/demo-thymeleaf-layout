$(function() {
    'use strict'
    $("form.master-select").submit(function() {
        console.log("master select submit");
        let masterName = $("form.master-select option:selected").val();
        console.log(masterName);
        $(this).attr('action', '/master-list/select/' + masterName);
    });
})