$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status =  validateHospitalForm();

	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();

		return;
	}

	// If valid-------------------------
	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "HospitalAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onHospitalSaveComplete(response.responseText, status);
		}
	});

});

function onHospitalSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")

		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidHospitalIDSave").val("");
	$("#formHospital")[0].reset();
}

// UPDATE==========================================

$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidHospitalIDSave").val(
					$(this).closest("tr").find('#hidHospitalIDUpdate').val());
			$("#hosName").val(
					$(this).closest("tr").find('td:eq(0)').text());
			$("#hosTelephone").val($(this).closest("tr").find('td:eq(1)').text());
			$("#hosEmail").val($(this).closest("tr").find('td:eq(2)').text());
			$("#hosAddress").val($(this).closest("tr").find('td:eq(3)').text());

		});

// REMOVE ===============

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "HospitalAPI",
		type : "DELETE",
		data : "hosId=" + $(this).data("hosid"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});

// Delete=================
function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

function validateHospitalForm() {
	// Hospital Name-------------------
	if ($("#hosName").val().trim() == "") {
		return "Insert Hospital name.";
	}
	
	var letterReg3 = /^[A-Za-z]+$/;
	var tmpfHosName =  $("#hosName").val().trim();
	if(!tmpfHosName.match(letterReg3)){
		return "Hospital Name must have alphabetic charaters only...!";
	}
	// Telephone Number-----------------
	if ($("#hosTelephone").val().trim() == "") {
		return "Insert Hospital Telephone.";
	}
	//is Numeric value
	var tmhostel =$("#hosTelephone").val().trim();
	if(!$.isNumeric(tmhostel)){
		
		return "Insert a numerical value for telephone";
	}
	
	//is ten digit Telephone numbers
	var tenhosTel = $("#hosTelephone").val().trim();
	if(tenhosTel.length !=10){
		
		return "Insert valid 10 Digit number"
	}
	
	
	// Email-------------------------------
	if ($("#hosEmail").val().trim() == "") {
		return "Insert Hospital Email.";
	}
	
	//is Valid Email
	var tmpEmil=$ ("#hosEmail").val().trim();
	var rag=/^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if(!rag.test(tmpEmil)){
		return "Enter valid Email";
		
	}
	
	
	// Address
	if ($("#hosAddress").val().trim() == "") {
		return "Insert Hospital Address.";
	}

	return true;
}
