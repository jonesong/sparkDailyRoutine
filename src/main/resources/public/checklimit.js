/*
 * project.js
 *
 * Created on Oct 17, 2016, 2:40 PM by jonesong@yahoo.com
 * Simple checking of name & notes field limiting to 20 and 100 characters
 * HTML5 or HTML4 compatible
 * Updates
 */

function checkLimitNameNote() {
	var strName = document.getElementById("name").value
	var strNote = document.getElementById("note").value
	
	if (strName.length > 20) {
		return showError();
	} else if (strNote.length > 100) {
		return showError();
	} else { //if null, passed
	}
}

function showError() {
	alert("Name or note is too long. Name is limit to 20 characters & note to 100");
	return false;
}