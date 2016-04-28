$(function() {
	function dateValidation (date) {
		var before = new Date("1970-01-01");
		var after = new Date("2037-12-31");
		var current = new Date(date);
		return before <= current && current <= after;
	};
	$('#computerForm')
			.on(
					'submit',
					function(event) {
						var name = document.forms["computerForm"]["computerName"].value;
						if (name == "") {
							event.preventDefault();
							alert("Name must be filled out");
							return false;
						}
						var introduced = document.forms["computerForm"]["introduced"].value;
						if (introduced != "" && (isNaN(Date.parse(introduced)) || !dateValidation(introduced))) {
							event.preventDefault();
							alert("Introduced date is incorrect");
							return false;
						}
						var discontinued = document.forms["computerForm"]["discontinued"].value;
						if (discontinued != ""
								&& (isNaN(Date.parse(discontinued)) || !dateValidation(discontinued))) {
							event.preventDefault();
							alert("Discontinued date is incorrect");
							return false;
						}
					});
});