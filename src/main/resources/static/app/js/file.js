$(function () {
    'use strict'
    // ファイルアップロードキャンセル
    $("#fileUploadCancel").on("click", function () {
        console.log("cancel");
        window.location.href = window.app.context;
    });
    $("#fileUploadAjax").on("click", function () {
        console.log("upload ajax");

        let formdata = new FormData($("form.file-upload").get(0));
        console.log(formdata);
        let url = window.app.context + "api/file-upload/upload";
        console.log(url);

        $.ajax({
            url: url,
            type: "POST",
            data: formdata,
            cache: false,
            contentType: false,
            processData: false,
            dataType: "json"
        })
            .done(function (data, textStatus, jqXHR) {
                console.log(textStatus);
                console.log(data);
                if (textStatus === "success") {
                    console.log("set fileName", data.fileName);
                    console.log("set processingResult", data.processingResult);
                    $("div.fileName").text(data.fileName);
                    $("div.processingResult").text(data.processingResult);
                    $("code.resultList").text(data.resultList);
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus);
                alert("fail");
                $("div.messagePanel").show();
                $("b.box-title").text("エラーが発生しました");
                console.error(errorThrown);
            });
    });
})