function confirmbox() {
	var r = confirm("Are you sure do you want to delete this?");
	if (r != true) {
		return false;
	}
}

function getdatetime() {
	var d = new Date();
	document.getElementById("date_started").value = d.getFullYear() + "-"
			+ ("0" + (d.getMonth() + 1)).slice(-2) + "-" + ("0" + d.getDate()).slice(-2);
	document.getElementById("time_start").value = d.toTimeString().replace(
			/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
}

function gettimeend() {
	var e = new Date();
	document.getElementById("time_end").value = e.toTimeString().replace(
			/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
}

function checkbeforeupdate() {
	if (document.getElementById("time_start").value === ""
			|| document.getElementById("time_end").value === "") {
		alert("No Progress to update");
		return false;
	} else {
		var start = removeFormat(document.getElementById("time_start").value);
		var end = removeFormat(document.getElementById("time_end").value);
		var total = end - start;
		var str = total.toString();
		document.getElementById("time_total").value = addFormat(formatTime(str));
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
			name = zero + zero + ":" + zero + zero + ":"
					+ name.substring(0, length);
		} else {
			name = zero + zero + ":" + zero + zero + ":" + zero
					+ name.substring(0, length);
		}
	}
	return name;
}
