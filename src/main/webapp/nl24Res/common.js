function winFocus()
{
	window.focus();
}

function ConfirmOrganization(node)
{
	var name = node.getAttribute("name");
	var guid = node.getAttribute("guid");
	var targetDocument = window.opener.document;
	var labelControl = targetDocument.getElementById(document.getElementById("HiddenControlLabelId").value);
	var valueControl = targetDocument.getElementById(document.getElementById("HiddenControlValueId").value);
	labelControl.value = name;
	valueControl.value = guid;
	window.close();
}

function ConfirmUser(UserId)
{
	var targetDocument = window.opener.document;
	var control = targetDocument.getElementById(document.getElementById("HiddenControlId").value);								
	control.value = UserId;				
	window.close();
}

function fireButton(buttonId)
{
	if (event.keyCode == 13)
	{
		event.returnValue = false;
		event.cancel = true;
		document.getElementById(buttonId).click();
	}
}

function openPolicyRules()
{
	window.open('about:blank','PasswordPolicyRules','width=400,height=300,left=400,top=100,scrollbars,status=false,resizable');
	return false;
}

function SetTabClassByIndex(objIndex, cssClass) {
    document.getElementById("Tab" + objIndex + "_Left").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Center").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Right").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Link").className = cssClass;
}

function SwitchHomeTab(n) {
    
    var tabCount = 3;

    var link = document.getElementById("Tab" + n + "_Link");
    if (link.className.toLowerCase() == "nonactivefolder") {
        if ((n >= 0) && (n < tabCount)) {
            for (i = 0; i < tabCount; i++) {
                if (i != n) {
                    SetTabClassByIndex(i, "nonActiveFolder");
                    document.getElementById("TabContent" + i).className = "TabContent ScreenHidden";
                }
            }
            SetTabClassByIndex(n, "activeFolder");
            document.getElementById("TabContent" + n).className = "TabContent ScreenVisible";
        }
    }
    return false;
}

function SwitchNotesTab(n) {
    
    var tabCount = 2;

    var link = document.getElementById("Tab" + n + "_Link");
    if (link.className.toLowerCase() == "nonactivefolder") {
        if ((n >= 0) && (n < tabCount)) {
            for (i = 0; i < tabCount; i++) {
                if (i != n) {
                    SetTabClassByIndex(i, "nonActiveFolder");
                    document.getElementById("TabContent" + i).className = "TabContent ScreenHidden";
                }
            }
            SetTabClassByIndex(n, "activeFolder");
            document.getElementById("TabContent" + n).className = "TabContent ScreenVisible";
        }
    }
    return false;
}