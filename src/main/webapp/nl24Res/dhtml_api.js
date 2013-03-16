// DHTML Library

// function: swapDetailView()
// hides/shows the details of an HTML block
// note: the table structure must be the following
//<xh:tr>
//	<xh:td>
//		<xh:a href="#" onClick="swapDetailView(this);">ARROW IMAGE (o del +/-)</xh:a>
//	</xh:td>
//	<xh:td>
//		...Data 1 always visible...
//	</xh:td>
//	<xh:td>
//		...Data 1 always visible...
//	</hx:td>
//</xh:tr>
//<xh:tr>
//	<xh:td>
//	</xh:td>
//	<xh:td colspan="2">  <-- NOTE: colspan is determined by the number of data of the previous row
//		...details to show/hide...
//		...NOTE: hete another table is a nice solution <table>.
//	</xh:td>
//</xh:tr>

function CreateExitLinks() {
    if (document.getElementById("objQuit") && document.getElementById("tmpQuit")) {
        document.getElementById("objQuit").innerHTML = document.getElementById("tmpQuit").innerHTML;
        document.getElementById("tmpQuit").innerHTML = "";
    }
}

// to avoid a double submit
var submitting = false;

function ValidateForm(form) {
    if (submitting) return false;

    if ((!form.ClientValidation) || (form.ClientValidation && form.ClientValidation.value != "True")) {
        submitting = true;
        return true;
    }

    var j = 0;
    var k = 0;
    var result = true;
    var tmpClass
    var countNode = 0;

    $(':input').filter('[class~="RequiredField"]').each(function(index) {
    	element = this;
        tmpClassArray = element.className.split(" ");
        tmpClass = "";
        for (h = 0; h < tmpClassArray.length - 1; h++) {
            tmpClass += tmpClassArray[h] + " ";
        }

        switch (element.type) {
            case "select-one":
                if (element.selectedIndex == 0) {
                    element.className = tmpClass + "MissingField";
                    result = false;
                }
                else
                    element.className = tmpClass + "VerifiedField";
                break;
            case "text":
                if (element.value == "") {
                    element.className = tmpClass + "MissingField";
                    result = false;
                }
                else
                    element.className = tmpClass + "VerifiedField";
                break;
            case "textarea":
                if (element.className.indexOf("mceEditor") < 0) {
                    // normal textarea
                    if (element.value == "") {
                        element.className = tmpClass + "MissingField";
                        result = false;
                    }
                    else
                        element.className = tmpClass + "VerifiedField";
                }
                else {
                    // TinyMCE
                    var tinymce = tinyMCE.get(element.id);
                    if (tinymce.getContent() == "") {
                        element.className = tmpClass + "MissingField";
                        result = false;
                    }
                    else {
                        element.className = tmpClass + "VerifiedField";
                    }
                    // copy background style from textarea to TinyMCE
                    tinymce.dom.setStyle(tinymce.dom.select('body'), 'background', getBackground(element));
                }
                break;
            case "checkbox":
                if (element.checked == false) {
                    element.className = tmpClass + "MissingField";
                    result = false;
                }
                else
                    element.className = tmpClass + "VerifiedField";
                break;
        }
    });
    
    if (result == false)
        alert(MissingFieldsMessage);

    form.ClientValidation.value = "False";
    if (result == false)
        return false;
    else {
        submitting = true;
        return true;
    }
}

function getBackground(x) {
    if (x.currentStyle) // Explorer
        var y = x.currentStyle["backgroundColor"];
    else if (window.getComputedStyle) // other Browsers
        var y = document.defaultView.getComputedStyle(x, null).getPropertyValue("background-color");
    return y;
}

function ClearAndNavigate(obj) {
    if (obj.tagName)
        switch (obj.tagName.toUpperCase()) {
        case "INPUT":
        case "SELECT":
            switch (obj.type.toLowerCase()) {
                case "text":
                    obj.value = "";
                    break;
                case "checkbox":
                    obj.checked = false;
                    break;
                case "select-one":
                    obj.selectedIndex = 0;
                    break;
            }
            break;
        case "TEXTAREA":
            obj.value = "";
            break;
    }

    if (obj.childNodes && obj.childNodes.length > 0) {
        var k;
        for (k = 0; k < obj.childNodes.length; k++)
            ClearAndNavigate(obj.childNodes[k]);
    }
}

function ClearForm() {
    var i;
    for (i = 0; i < tabCount; i++)
        ClearCurrentTab(i);
    return false;
}

function PrintForm() {
    self.print();
}

function ClearCurrentTab(tabIndex) {
    tab = document.getElementById("TabContent" + tabIndex);
    ClearAndNavigate(tab);
    return false;
}

function swapDetailView(obj) {
    if (obj.parentElement.parentElement.nextSibling)
        if (obj.parentElement.parentElement.nextSibling.style.display == 'none') {
        obj.parentElement.parentElement.nextSibling.style.display = 'block';
        obj.src = "./images/meno.gif";
    }
    else {
        obj.parentElement.parentElement.nextSibling.style.display = 'none';
        obj.src = "./images/piu.gif";
    }
    return false;
}

function swapDetailView_New(obj) {
    if (obj.parentElement.nextSibling)
        if (obj.parentElement.nextSibling.style.display == 'none') {
        obj.parentElement.nextSibling.style.display = 'block';
        obj.src = "./images/meno.gif";
    }
    else {
        obj.parentElement.nextSibling.style.display = 'none';
        obj.src = "./images/piu.gif";
    }
    return false;
}

function GetCurrentDate(field) {
    var oDate = new Date();
    var oDay = oDate.getDate();
    if (oDay < 10)
        oDay = "0" + oDay;
    var oMonth = oDate.getMonth() + 1;
    if (oMonth < 10)
        oMonth = "0" + oMonth;
    var oYear = oDate.getYear();
    var sDate = oYear + "-" + oMonth + "-" + oDay;
    field.value = sDate;
}

var tabCount = 0;
var CurrentTab = 0;

function getCurrentTab() {
    return document.getElementById("TextBoxCurrentTab").value;
}

function window_onload() {
    var tag_collection = document.getElementsByTagName("div");
    tabCount = 0;
    for (i = 0; i < tag_collection.length; i++) {
        divItem = tag_collection[i];
        if ((divItem.id) && (divItem.id.match("TabContent")))
            tabCount++;
    }
    SwitchTab(getCurrentTab());
    FocusFirstVisibleField(getCurrentTab());
}

function SetTabClassByIndex(objIndex, cssClass) {
    document.getElementById("Tab" + objIndex + "_Left").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Center").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Right").className = cssClass;
    document.getElementById("Tab" + objIndex + "_Link").className = cssClass;
}

function SwitchTab(n) {
    if (tabCount > 0) {
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

                getSelectedTabControl().value = n;
                CurrentTab = parseInt(n);
                document.getElementById("TextBoxCurrentTab").value = CurrentTab;
            }
            //FocusFirstVisibleField(n);
        }
        if (document.getElementById("PrevTab"))
            if (CurrentTab == 0) document.getElementById("PrevTab").disabled = true;
        else document.getElementById("PrevTab").disabled = false;

        if (document.getElementById("NextTab"))
            if (CurrentTab == tabCount - 1) document.getElementById("NextTab").disabled = true;
        else document.getElementById("NextTab").disabled = false;
        return false;
    }
}

var FieldFocused = false;

function FocusFirstVisibleField(n)
{
	if(tabCount > 0)
	{
		window.scrollTo(0,0);
		CurrentTabTempControl = document.getElementById("TabContent" + n );
		FieldFocused = false;
		if(CurrentTabTempControl.all)
		{
			var i;
			for ( i=0; i < CurrentTabTempControl.all.length; i++ )
			{
				obj = CurrentTabTempControl.all(i);
				TestObjectForFocus(obj);
				if(FieldFocused)
					return;
			}
		}
		else
		{
			// TODO: implement for Firefox
			TestAndNavigateObjectForFocus(CurrentTabTempControl);
		}
	}
}

function TestAndNavigateObjectForFocus(obj) {
    TestObjectForFocus(obj);
    if (!FieldFocused) {
        var i;
        for (i = 0; i < obj.childNodes.length; i++)
            if (!FieldFocused)
            TestAndNavigateObjectForFocus(obj.childNodes[i])
    }
}

function TestObjectForFocus(obj)
{
	if( ( ( obj.tagName == "INPUT" && obj.type == "text" ) || obj.tagName == "SELECT" ) && 
			isVisible(obj))
	{
		FieldFocused = true;
		obj.focus();
	}
}

function isVisible(obj) {
	if(obj.style != null && 
			((obj.style.display!=null && obj.style.display=="none") || 
			 (obj.style.visibility!=null &&obj.style.visibility == "hidden"))) {
		return false;
	} else {
		if(obj.parentNode!=null) {
			return isVisible(obj.parentNode);
		} else {
			return true;
		}
	}
}

// Initialize TinyMCE

tinyMCE.init({
    mode: "textareas",
    //theme: "simple",
    theme: "advanced",
    theme_advanced_toolbar_align: config_theme_advanced_toolbar_align,
    theme_advanced_buttons1: config_theme_advanced_buttons1,
    theme_advanced_buttons2: config_theme_advanced_buttons2,
    theme_advanced_buttons3: config_theme_advanced_buttons3,
    editor_selector: "mceEditor",
    editor_deselector: "mceNoEditor",
    encoding: "xml",
    width:"100%",
    setup: function(ed) {
        ed.onInit.add(function(ed) {
            // Sets background onInit
            ed.dom.setStyle(ed.dom.select('body'), 'background', getBackground(ed.getElement()));
        });
    }
});

tinyMCE.init({
    mode: "textareas",
    theme: "advanced",
    editor_selector: "mceEditor_ReadOnly",
    editor_deselector: "mceNoEditor",
    encoding: "xml",
    width: "100%",
    readonly: true,
    setup: function(ed) {
        ed.onInit.add(function(ed) {
            ed.save();
        });
    }
});
