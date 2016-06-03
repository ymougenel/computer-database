/* Date validation (syntax and minMax) */
function dateValidation(date) {
	var before = new Date("1970-01-01");
	var after = new Date("2037-12-31");
	var current = new Date(date);
	return before <= current && current <= after;
};

/* Continuous fields validation */
var nameInput = $("#computerName");
var introducedInput = $("#introduced");
var discontinuedInput = $("#discontinued");

nameInput.on('keyup', function() {
	if (nameInput.val().length === 0) {
		nameInput.parent().removeClass("has-success");
		nameInput.parent().addClass("has-error");
		name = false;
	} else {
		nameInput.parent().removeClass("has-error");
		nameInput.parent().addClass("has-success");
		name = true;
	}
});

introducedInput.on('keyup', function() {
	if (dateValidation(introducedInput.val())) {
		introducedInput.parent().removeClass("has-error");
		introducedInput.parent().addClass("has-success");
		introduced = true;
		introducedBeforeDiscontinued();
	} else {
		introducedInput.parent().removeClass("has-success");
		introducedInput.parent().addClass("has-error");
		introduced = false;
	}
});

discontinuedInput.on('keyup', function() {
	if (dateValidation(discontinuedInput.val())) {
		discontinuedInput.parent().removeClass("has-error");
		discontinuedInput.parent().addClass("has-success");
		discontinued = true;
		introducedBeforeDiscontinued();
	} else {
		discontinuedInput.parent().removeClass("has-success");
		discontinuedInput.parent().addClass("has-error");
		discontinued = false;
	}
});

/* Function applied after the submit button
 * check the name + dates
 * Cancel the submit action if invalid inputs
 */
$(function() {
	var dateFormating = function(date) {
		if (isNaN(date)) {
			return "";
		}

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

						// Check date format
						var introduced = document.forms["computerForm"]["introduced"].value;
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
