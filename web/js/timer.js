function deleteElement(elementId) {
    var elem = document.getElementById(elementId);
    elem.parentNode.removeChild(elem);
}

function hideAfter(elementId, time) {
    window.setTimeout(function () {
        deleteElement(elementId)
    }, time);
}
