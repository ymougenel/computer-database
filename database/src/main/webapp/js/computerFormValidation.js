$(function() {
	function dateValidation(date) {
		var before = new Date("1970-01-01");
		var after = new Date("2037-12-31");
		var current = new Date(date);
		return before <= current && current <= after;
	}
	;

	var dateFormating = function(date) {
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString(); // getMonth() is zero-based
		var dd = date.getDate().toString();
		return yyyy + "-" + (mm[1] ? mm : "0" + mm[0]) + "-"
				+ (dd[1] ? dd : "0" + dd[0]); // padding
	};

	$('#computerForm')
			.on(
					'submit',
					function(event) {
						var name = document.forms["computerForm"]["computerName"].value;
						// Check name is filled
						if (name == "") {
							event.preventDefault();
							alert("Name must be filled out");
							return false;
						}
						var introduced = document.forms["computerForm"]["introduced"].value;
						// Check date format
						if (introduced != ""
								&& (isNaN(Date.parse(introduced)) || !dateValidation(introduced))) {
							event.preventDefault();
							alert("Introduced date is incorrect");
							return false;
						}
						// If date valid, change the date to a java parsing
						// syntax
						else {
							var current = new Date(introduced);
							document.forms["computerForm"]["introduced"].value = dateFormating(current);
						}
						var discontinued = document.forms["computerForm"]["discontinued"].value;

						if (discontinued != ""
								&& (isNaN(Date.parse(discontinued)) || !dateValidation(discontinued))) {
							event.preventDefault();
							alert("Discontinued date is incorrect");
							return false;
						} else {
							var current = new Date(discontinued);
							document.forms["computerForm"]["discontinued"].value = dateFormating(current);
						}
					});
});
