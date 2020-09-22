$(function () {
    'use strict'

    // マスター選択画面
    $("form.master-select").submit(function () {
        console.log("master select submit");
        let masterName = $("form.master-select option:selected").val();
        console.log(masterName);
        $(this).attr("action", window.app.context + "master/select/" + masterName);
    });

    // buttonId
    // popverId
    // positionId
    // inputId
    // toggleId
    initPageSizePopover("buttonId", "popverId", "positionId");
    $("#inputId").on("keypress", function (event) {
        if (event.which == 13) {
            $("#toggleId").click();
            return false;
        }
    });
    $("#toggleId").on("click", function (event) {
        console.log("toggleId click");
        $("#popverId").toggle();
    });
})