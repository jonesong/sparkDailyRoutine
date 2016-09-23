/*
 * progresstime.js
 *
 * Created on Sep 14, 2016, 2:55 PM by jonesong@yahoo.com
 * Get date, time start, time end & compute time total
 * Used for the web develop in Daily Routine
 * Updates
 */

function getdatetime() {
	var d = new Date();
	document.getElementById("date_started").value = d.getFullYear() + "-" + ("0" + (d.getMonth() + 1)).slice(-2)
			+ "-" + ("0" + d.getDate()).slice(-2);
	document.getElementById("time_start").value = d.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
}

function gettimeend() {
	var e = new Date();
	document.getElementById("time_end").value = e.toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
}

function checkbeforeupdate() {
	if (document.getElementById("time_start").value === "" || document.getElementById("time_end").value === "") {
		alert("Make sure time start & time stop are finished. No Progress to update");
		return false;
	} else {
		var start = removeFormat(document.getElementById("time_start").value);
		var end = removeFormat(document.getElementById("time_end").value);
//		var total = end - start;
		var total = checkAndDoSubtraction(start,end);
//		var str = total.toString();
//		document.getElementById("time_total").value = addFormat(formatTime(str));
		document.getElementById("time_total").value = formatTime(total);
	}
}

function formatTime(x) {
	var length = x.length;
	var name = "";
	for (i = length; i > 0; i--) {
		name = ":" + x.substring(i - 2, i) + name;
		i--;
	}
	return name.substring(1, name.length);
}

function removeFormat(x) {
	var length = 8; // fixed 00:00:00
	var name = "";
	for (i = 0; i < length; i++) {
		name = name + x.substring(i, 2 + i);
		i += 2;
	}
	return name;
}

function addFormat(x) {
	var length = x.length;
	var name = x;
	var zero = "0";
	if (length < 8) {
		if (length > 5) {
			name = zero + name.substring(0, length);
		} else if (length > 4) {
			name = zero + zero + ":" + name.substring(0, length);
		} else if (length > 3) {
			name = zero + zero + ":" + zero + name.substring(0, length);
		} else if (length > 1) {
			name = zero + zero + ":" + zero + zero + ":" + name.substring(0, length);
		} else {
			name = zero + zero + ":" + zero + zero + ":" + zero + name.substring(0, length);
		}
	}
	return name;
}

function checkAndDoSubtraction(x, y) {
	 var start = x.toString();
	 var end = y.toString();
	 var newMinutes = getTwoDigits(end, 2); // setting default if ever no change
	 var newHours = getTwoDigits(end, 0); // setting default if ever no change

	 var tmpSecond = (parseInt(getTwoDigits(end, 4)) - parseInt(getTwoDigits(start, 4))).toString();
	 if (parseInt(tmpSecond) < 0) { // add 60 to seconds then minus 1 from minutes
	  tmpSecond = (parseInt(getTwoDigits(end, 4)) + 60).toString();
	  newMinutes = (parseInt(newMinutes) - 1).toString(); // 1 equals to 60seconds
	 } else { //get the original second
	  tmpSecond = getTwoDigits(end, 4);
	 }
	 
	 var tmpMinute = (parseInt(newMinutes) - parseInt(getTwoDigits(start, 2))).toString();
	 if (parseInt(tmpMinute) < 0) { // add 60 to seconds then minus 1 from minutes
	  newMinutes = (parseInt(newMinutes) + 60).toString();
	  newHours = (parseInt(getTwoDigits(end, 0)) - 1).toString(); // 1 equals to 60minutes
	 }
	 return ("0" + (parseInt(newHours) - parseInt(getTwoDigits(start, 0))).toString()).slice(-2) 
	 + ("0" + (parseInt(newMinutes) - parseInt(getTwoDigits(start, 2))).toString()).slice(-2) 
	 + ("0" + (parseInt(tmpSecond) - parseInt(getTwoDigits(start, 4))).toString()).slice(-2);
}

function getTwoDigits(x,y) {
	//012345 starting ten second for y is 4, starting ten minute for y is 2
	var name = x;
	return name.substring(y, y + 2);
}