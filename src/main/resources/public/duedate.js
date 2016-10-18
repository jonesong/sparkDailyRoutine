/*
 * duedate.js
 *
 * Created on Sep 19, 2016, 9:55 AM by jonesong@yahoo.com
 * Simple checking of due date field
 * HTML5 or HTML4 compatible
 * Updates
 */

function checkFormatDueDate() {
	var d = new Date();
	var strDate = document.getElementById("due_date").value
	var strLength = strDate.length;
	if (strLength == 4 || strLength == 10) {
		if ((strLength == 10) && (strDate.includes("-"))) { //10 digit with symbol -
			if (strDate.substring(4,5).includes("-") && strDate.substring(7,8).includes("-")){
				if ( !checkMonth(strDate.substring(5,7)) || !checkDay(strDate.substring(8,10))){
					return showError();
				}
			} else {
				return showError(); //if false, the string has wrong position of symbol 2016-09-16
			}
		} else if (strLength == 4) {
			if ( !checkMonth(strDate.substring(0,2)) || !checkDay(strDate.substring(2,4))){
				return showError();
			} else {
				document.getElementById("due_date").value = d.getFullYear() 
				+ "-" + strDate.substring(0,2) + "-" + strDate.substring(2,4);
			}
		} else { //10 digits without symbol /
			return showError();
		}
	} else if (strLength > 0) { //after checking 4 or 10, if > 0 then
		return showError();
	} else { //if null, passed
	}
}

function checkMonth(x){
	var num = parseInt(x);
	if (num > 0 && num < 13) {
		return true;
	}
	return false;
}

function checkDay(x) {
	var num = parseInt(x);
	if (num > 0 && num < 32) {
		return true;
	}
	return false;
}

function showError() {
	alert("Invalid input date. e.g. 0916 MMdd or 2016-09-16 yyyy-MM-dd");
	return false;
}