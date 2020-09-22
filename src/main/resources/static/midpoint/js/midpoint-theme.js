/*
 * Copyright (c) 2010-2013 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// current ace editor version v1.2.9

var ACE_EDITOR_POSTFIX = "_editor";
var DISABLED_CLASS = "disabled";
$.aceEditors = {};

function initEditor(textAreaId, readonly, resize, height, minHeight, mode) {
    var jqTextArea = '#' + textAreaId;
    var editorId = textAreaId + ACE_EDITOR_POSTFIX;
    var jqEditor = '#' + editorId;

    $('<div id="' + editorId + '" class="aceEditor"></div>').insertAfter($('#' + textAreaId));

    $(jqEditor).text($(jqTextArea).val());
    $(jqTextArea).hide();

    var langTools = ace.require("ace/ext/language_tools");
    //todo implement completer based
    // var completer = {
    //
    //     getCompletions: function(editor, session, pos, prefix, callback) {
    //         //example
    //         var completions = [];
    //         completions.push({ name:"testing1", value:"testing1", meta: "code1" });
    //         completions.push({ name:"testing2", value:"testing2", meta: "code2" });
    //         callback(null, completions);
    //     }
    // }
    // langTools.addCompleter(completer);

    var editor = ace.edit(editorId);

    editor.setOptions({
        enableBasicAutocompletion: true
    });

    editor.getSession().setTabSize(3);

    editor.setTheme("ace/theme/eclipse");
    if (mode != null) {
        editor.getSession().setMode(mode);
    }
    editor.setShowPrintMargin(false);
    editor.setFadeFoldWidgets(false);
    setReadonly(jqEditor, editor, readonly);
    editor.on('blur', function () {
        $(jqTextArea).val(editor.getSession().getValue());
        $(jqTextArea).trigger('onBlur');
    });
    editor.on('change', function () {
        $(jqTextArea).val(editor.getSession().getValue());
        $(jqTextArea).trigger('onChange');
    });

    //add editor to global map, so we can find it later
    $.aceEditors[editorId] = editor;

    //todo handle readonly for text area [lazyman] add "disabled" class to .ace_scroller

    $(document).ready(function () {
        if (height < minHeight) {
            height = minHeight;
        }

        if (resize) {
            resizeToMaxHeight(editorId, minHeight);
        } else {
            resizeToFixedHeight(editorId, height);
        }
    });
}

function resizeToMaxHeight(editorId, minHeight) {
    //38 + 1 + 21 is menu outer height
    var newHeight = $(document).innerHeight() - $('section.content-header').outerHeight(true)
        - $('section.content').outerHeight(true) - $('footer.main-footer').outerHeight(true)
        - $('header.main-header').outerHeight(true);
    if (newHeight < minHeight) {
        newHeight = minHeight;
    }

    resizeToFixedHeight(editorId, newHeight);
}

function resizeToFixedHeight(editorId, height) {
    $('#' + editorId).height(height.toString() + "px");
    $('#' + editorId + '-section').height(height.toString() + "px");

    $.aceEditors[editorId].resize();
}

function refreshReadonly(textAreaId, readonly) {
    var jqTextArea = '#' + textAreaId;

    var editorId = textAreaId + ACE_EDITOR_POSTFIX;
    var jqEditor = '#' + editorId;

    var editor = $.aceEditors[editorId];
    setReadonly(jqEditor, editor, readonly);
    editor.focus();
}

function setReadonly(jqEditor, editor, readonly) {
    editor.setReadOnly(readonly);
    if (readonly) {
        $(jqEditor).addClass(DISABLED_CLASS);
    } else {
        $(jqEditor).removeClass(DISABLED_CLASS);
    }
};
/*
 * Copyright (c) 2010-2015 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

$(window).load(function() {
    //dom not only ready, but everything is loaded MID-3668
    $("body").removeClass("custom-hold-transition");

    initAjaxStatusSigns();

    Wicket.Event.subscribe('/ajax/call/failure', function( attrs, jqXHR, textStatus, jqEvent, errorThrown ) {
        console.error("Ajax call failure:\n" + JSON.stringify(attrs.target.location)
            + "\nStatus:\n" + JSON.stringify(textStatus));
    });

    fixContentHeight();
    $(window, ".wrapper").resize(function () {
        fixContentHeight();
    });
});

// I'm not sure why sidebar has 15px padding -> and why I had to use 10px constant here [lazyman]
function fixContentHeight() {
    if ($(".main-footer").length > 0) {
        return;
    }

    var window_height = $(window).height();
    var sidebar_height = $(".sidebar").height() || 0;

    if (window_height < sidebar_height) {
        $(".content-wrapper, .right-side").css('min-height', sidebar_height + 10); // footer size
    }
}

function clickFuncWicket6(eventData) {
    var clickedElement = (window.event) ? event.srcElement : eventData.target;
    if ((clickedElement.tagName.toUpperCase() == 'BUTTON'
        || clickedElement.tagName.toUpperCase() == 'A'
        || clickedElement.parentNode.tagName.toUpperCase() == 'A'
        || (clickedElement.tagName.toUpperCase() == 'INPUT'
            && (clickedElement.type.toUpperCase() == 'BUTTON'
                || clickedElement.type.toUpperCase() == 'SUBMIT')))
        && clickedElement.parentNode.id.toUpperCase() != 'NOBUSY'
        && clickedElement.disabled == 'false') {
        showAjaxStatusSign();
    }
}

function initAjaxStatusSigns() {
    document.getElementsByTagName('body')[0].onclick = clickFuncWicket6;
    hideAjaxStatusSign();
    Wicket.Event.subscribe('/ajax/call/beforeSend', function( attributes, jqXHR, settings ) {
        showAjaxStatusSign();
    });
    Wicket.Event.subscribe('/ajax/call/complete', function( attributes, jqXHR, textStatus) {
        hideAjaxStatusSign();
    });
}

function showAjaxStatusSign() {
    document.getElementById('ajax_busy').style.visibility = 'visible';
}

function hideAjaxStatusSign() {
    document.getElementById('ajax_busy').style.visibility = 'hidden';
}

/**
 * InlineMenu initialization function
 */
function initInlineMenu(menuId, hideByDefault) {
    var cog = $('#' + menuId).find('ul.cog');
    var menu = cog.children().find('ul.dropdown-menu');

    var parent = cog.parent().parent();     //this is inline menu div
    if (!hideByDefault && !isCogInTable(parent)) {
        return;
    }

    if (isCogInTable(parent)) {
        //we're in table, we now look for <tr> element
        parent = parent.parent('tr');
    }

    // we only want to hide inline menus that are in table <td> element,
    // inline menu in header must be visible all the time, or every menu
    // that has hideByDefault flag turned on
    cog.hide();

    parent.hover(function () {
        //over
        cog.show();
    }, function () {
        //out
        if (!menu.is(':visible')) {
            cog.hide();
        }
    });
}

function isCogInTable(inlineMenuDiv) {
    return inlineMenuDiv.hasClass('cog') && inlineMenuDiv[0].tagName.toLowerCase() == 'td';
}

function updateHeight(elementId, add, substract) {
    updateHeightReal(elementId, add, substract);
    $(window).resize(function() {
        updateHeightReal(elementId, add, substract);
    });
}

function updateHeightReal(elementId, add, substract) {
    $('#' + elementId).css("height","0px");

    var documentHeight = $(document).innerHeight();
    var elementHeight = $('#' + elementId).outerHeight(true);
    var mainContainerHeight = $('section.content-header').outerHeight(true)
        + $('section.content').outerHeight(true) + $('footer.main-footer').outerHeight(true)
        + $('header.main-header').outerHeight(true);

    console.log("Document height: " + documentHeight + ", mainContainer: " + mainContainerHeight);

    var height = documentHeight - mainContainerHeight - elementHeight - 1;
    console.log("Height clean: " + height);

    if (substract instanceof Array) {
        for (var i = 0; i < substract.length; i++) {
            console.log("Substract height: " + $(substract[i]).outerHeight(true));
            height -= $(substract[i]).outerHeight(true);
        }
    }
    if (add instanceof Array) {
        for (var i = 0; i < add.length; i++) {
            console.log("Add height: " + $(add[i]).outerHeight(true));
            height += $(add[i]).outerHeight(true);
        }
    }
    console.log("New css height: " + height);
    $('#' + elementId).css("height", height + "px");
}

/**
 * Used in TableConfigurationPanel (table page size)
 *
 * @param buttonId
 * @param popoverId
 * @param positionId
 */
function initPageSizePopover(buttonId, popoverId, positionId) {
    console.log("initPageSizePopover('" + buttonId + "','" + popoverId + "','" + positionId +"')");

    var button = $('#' + buttonId);
    button.click(function () {
        var popover = $('#' + popoverId);

        var positionElement = $('#' + positionId);
        var position = positionElement.position();

        var top = position.top + parseInt(positionElement.css('marginTop'));
        var left = position.left + parseInt(positionElement.css('marginLeft'));
        var realPosition = {top: top, left: left};

        var left = realPosition.left - popover.outerWidth();
        var top = realPosition.top + button.outerHeight() / 2 - popover.outerHeight() / 2;

        popover.css("top", top);
        popover.css("left", left);

        popover.toggle();
    });
}

/**
 * Used in SearchPanel for advanced search, if we want to store resized textarea dimensions.
 *
 * @param textAreaId
 */
function storeTextAreaSize(textAreaId) {
    console.log("storeTextAreaSize('" + textAreaId + "')");

    var area = $('#' + textAreaId);
    $.textAreaSize = [];
    $.textAreaSize[textAreaId] = {
        height: area.height(),
        width: area.width(),
        position: area.prop('selectionStart')
    }
}

/**
 * Used in SearchPanel for advanced search, if we want to store resized textarea dimensions.
 *
 * @param textAreaId
 */
function restoreTextAreaSize(textAreaId) {
    console.log("restoreTextAreaSize('" + textAreaId + "')");

    var area = $('#' + textAreaId);

    var value = $.textAreaSize[textAreaId];

    area.height(value.height);
    area.width(value.width);
    area.prop('selectionStart', value.position);

    // resize also error message span
    var areaPadding = 70;
    area.siblings('.help-block').width(value.width + areaPadding);
}

/**
 * Used in SearchPanel class
 *
 * @param buttonId
 * @param popoverId
 * @param paddingRight value which will shift popover to the left from center bottom position against button
 */
function toggleSearchPopover(buttonId, popoverId, paddingRight) {
    console.log("Called toggleSearchPopover with buttonId=" + buttonId + ",popoverId="
        + popoverId + ",paddingRight=" + paddingRight);

    var button = $('#' + buttonId);
    var popover = $('#' + popoverId);

    var popovers = button.parents('.search-form').find('.popover:visible').each(function () {
        var id = $(this).attr('id');
        console.log("Found popover with id=" + id);

        if (id != popoverId) {
            $(this).hide(200);
        }
    });

    var position = button.position();

    var left = position.left - (popover.outerWidth() - button.outerWidth()) / 2 - paddingRight;
    var top = position.top + button.outerHeight();

    popover.css('top', top);
    popover.css('left', left);

    popover.toggle(200);

    //this will set focus to first form field on search item popup
    popover.find('input[type=text],textarea,select').filter(':visible:first').focus();

    //this will catch ESC or ENTER and fake close or update button click
    popover.find('input[type=text],textarea,select').off('keyup.search').on('keyup.search', function(e) {
        if (e.keyCode == 27) {
            popover.find('[data-type="close"]').click();
        } else if (e.keyCode == 13) {
            popover.find('[data-type="update"]').click();
        }
    });
}

/**
 * used in DropDownMultiChoice.java
 *
 * @param compId
 * @param options
 */
function initDropdown(compId, options) {
    $('#' + compId).multiselect(options);
};