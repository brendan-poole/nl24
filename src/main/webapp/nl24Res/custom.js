// Write custom Javascript here
// custom Javascript

function Scroller_GetCoords() {
	var scrollY;
	if (document.all) {
		if (!document.documentElement.scrollTop) {
			scrollY = document.body.scrollTop;
		} else {
			scrollY = document.documentElement.scrollTop;
		}
	} else {
		scrollY = window.pageYOffset;
	}

	// set the scroll on an input with id: RapidScrollYPosition
	if (document.forms[0].RapidScrollYPosition) {
		document.forms[0].RapidScrollYPosition.value = scrollY;
	}
}

function Scroller_Scroll() {
//	alert("Scroller_Scroll");
	// set the scroll from an input with id: RapidScrollYPosition
	var y;
	if (document.forms[0].RapidScrollYPosition) {
		y = document.forms[0].RapidScrollYPosition.value;
		if (y != '') {
			window.scrollTo(0, y);
		}
	}
}

function Focuser_Focus() {
//	alert("Focuser_Focus");
	// 
	var elem;
	for (i = 0; i < document.forms[0].elements.length; i++) {
		elem = document.forms[0].elements[i];
		elem.onfocus = function() {
			if (document.forms[0].FocusField) {
				document.forms[0].FocusField.value = this.id;
			}
		};
	}

	// set the focus from an input with id: FocusField
	var f;
	if (document.forms[0].FocusField) {
		f = document.forms[0].FocusField.value;
		if (f != '') {
			if (null != document.getElementById(f))
				document.getElementById(f).focus();
		}
	}
}

function SwitchToLastTab(){
	var lastTab = document.getElementById("LastTab_0");
	if (lastTab) {
		if(lastTab.value != ""){
			SwitchTab(lastTab.value);
			lastTab.value = "";	
		}	
	}
}

function ReplaceVersionLabel(){
	//only on mainPage and viewAll
	if(location.href.indexOf("mainPage") != -1
		|| location.href.indexOf("login") != -1
		|| location.href.indexOf("myWorklist" != -1)){
		var tabelCellTD = document.getElementsByTagName("td");//both IE and FF compatible
		if(tabelCellTD){
			for(i=0;i<tabelCellTD.length;i++){
				if(tabelCellTD[i].getAttribute("id") == "processVersionTD"){//id="processVersionTD" modified in table.xhtml, dataGridProcesses.xhtml
					tabelCellTD[i].innerHTML = ModifyVersionLabel(tabelCellTD[i].innerHTML);
				}
				if(tabelCellTD[i].getAttribute("id") == "userCR" || tabelCellTD[i].getAttribute("id") == "userCOMP"){//id="userCR" and id="userCOMP" modified in table.xhtml, dataGridProcesses.xhtml
					ModifyTRClass(tabelCellTD[i], tabelCellTD[i].innerHTML);				
				}						
			}
		}
	}
	
}

function ModifyVersionLabel(labelText){
	//substring the version of the process (Process name(x.x))
	var startIndex = labelText.lastIndexOf("(");
	var endIndex = labelText.lastIndexOf(")");	
	return labelText.substring(startIndex + 1, endIndex);
}

function ModifyTRClass(tabelCell, tabelCellValue){
	var currentUserId = document.getElementById("headerControl_LabelUserAsName").innerHTML;
	if(currentUserId == tabelCellValue){
		tabelCell.parentNode.setAttribute("class", "highlightWorklistTR");      //FF
		tabelCell.parentNode.setAttribute("className", "highlightWorklistTR");	//IE
	}
}

function CalculateMainApplicant(){
	var name;
	var surname;
	var middleName;
	var mainApplicantName;
	if(document.getElementById("MainApplicantName"))
		name = document.getElementById("MainApplicantName").value;
	if(document.getElementById("MainApplicantSurname"))
		surname = document.getElementById("MainApplicantSurname").value;
	if(document.getElementById("MainApplicantMiddleName"))
		middleName = document.getElementById("MainApplicantMiddleName").value;
	
	mainApplicantName = surname;
	if(middleName != "")
		mainApplicantName += ", " + middleName;
	if(name != "")
		mainApplicantName += " " + name;
	if(document.getElementById("MainApplicant"))	
		document.getElementById("MainApplicant").value = mainApplicantName;
}

function Window_OnLoad() {
	ReplaceVersionLabel();
	SwitchToLastTab();
	Scroller_Scroll();
	Focuser_Focus();	
}

window.onload = Window_OnLoad;
window.onscroll = Scroller_GetCoords;