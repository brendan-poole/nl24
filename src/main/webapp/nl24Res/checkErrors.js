var index = -1;
var KeepTesting = true;

var images = document.getElementsByTagName('img');

for (i = 0; i < images.length; i++) {
    if ((images[i].src.toLowerCase().match("excl.gif") != null) && KeepTesting) {
        index = i;
        KeepTesting = false;
    }
}

if (index > -1) {
    var currentObject = images[index];
    errorTarget = currentObject.previousSibling;
    while (currentObject.parentNode.tagName.toLowerCase() != "body") {
        currentObject = currentObject.parentNode;
        if (currentObject.id.toLowerCase().match("tabcontent"))
            SwitchTab(1 * currentObject.id.toLowerCase().replace("tabcontent", ""));
    }
    if (errorTarget != null && !(errorTarget.tagName.toLowerCase() == "input" && errorTarget.type.toLowerCase() == "hidden"))
        errorTarget.focus();
}