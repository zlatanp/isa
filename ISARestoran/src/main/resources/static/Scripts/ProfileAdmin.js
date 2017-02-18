function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

$(document).ready(function() {
	$("#timepickerOD").timepicker();
	$("#timepickerDO").timepicker();

	$('.selectpicker').selectpicker({
		style : 'btn-info',
		size : 4
	});

	$divsForHide = $(".formWrapper");
	$divsForHide.hide();

	$(".OpenNewRest").on("click", function() {
		$divsForHide.hide();
		$("#NewRest").show();
	});

	$(".OpenNewManForRest").on("click", function() {
		$divsForHide.hide();
		$("#NewManForRest").show();
	});

	$(".OpenNewSystemMan").on("click", function() {
		$divsForHide.hide();
		$("#NewSystemMan").show();
	});

});

$(document).on(
		"click",
		".OpenNewRest",
		function(e) {
			$.ajax({
				url : 'restoran/tipovi',
				type : 'GET',
				dataType : 'JSON',
				success : function(data) {
					var selektPolje = $("#tipRestSel");
					$.each(data, function(index, tip) {
						selektPolje.append("<option value=\"" + tip + "\">"
								+ tip + "</option>");
					});
				}
			});
		});

$(document).on("click", "#btnDodajRestoran", function(e) {
	e.preventDefault();
	if (!validateFormRestoran()) {
		return;
	}
	var noviRestoran = new Object();
	noviRestoran["naziv"] = $("#nameRest").val();
	noviRestoran["adresa"] = $("#addressRest").val();
	noviRestoran["grad"] = $("#cityRest").val();
	noviRestoran["telefon"] = $("#phoneRest").val();
	noviRestoran["email"] = $("#emailRest").val();
	noviRestoran["opis"] = $("#descriptionRest").val();
	noviRestoran["vremeOD"] = $("#timepickerOD").val();
	noviRestoran["vremeDO"] = $("#timepickerDO").val();
	noviRestoran["tip"] = $("#tipRestSel").val();
	registerRestoran(JSON.stringify(noviRestoran));
});

$(document).on("click", "#btnDodajAdmina", function(e) {
	e.preventDefault();
	if (!validateFormAdmin()) {
		return;
	}
	var noviAdmin = new Object();
	noviAdmin["ime"] = $("#imeAdm").val();
	noviAdmin["prezime"] = $("#przAdm").val();
	noviAdmin["email"] = $("#emailAdm").val();
	noviAdmin["password"] = $("#imeAdm").val();
	registerAdmin(JSON.stringify(noviAdmin));
});

function registerRestoran(restoranJSON) {
	$.ajax({
		url : 'restoran/register',
		type : 'POST',
		contentType : 'application/json',
		data : restoranJSON,
		success : function(ret) {
			console.log(ret);
			toastr.success("Restoran je uspešno registrovan!")
			$("#NewRest").hide();
			$("#NewRestForm")[0].reset();
		}
	});
}

function registerAdmin(adminJSON) {
	$.ajax({
		url : 'korisnik/registerAdmin',
		type : 'POST',
		contentType : 'application/json; charset=UTF-8',
		data : adminJSON,
		success : function(ret) {
			console.log(ret);
			toastr.success("Uspešno registrovan novi menadžer sistema.");
			$("#NewAdmForm")[0].reset();
			$("#NewSystemMan").hide();
		}
	});
}

function validateFormRestoran() {
	if (!validateField($("#nameRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite naziv restorana!")
		$("#nameRest").focus();
		return false;
	}
	if (!validateField($("#addressRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite adresu restorana!")
		$("#addressRest").focus();
		return false;
	}
	if (!validateField($("#cityRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite grad restorana!")
		$("#cityRest").focus();
		return false;
	}
	if (!validateField($("#phoneRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite telefon restorana!")
		$("#phoneRest").focus();
		return false;
	}
	if (!validateField($("#emailRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite e-mail restorana!")
		$("#emailRest").focus();
		return false;
	}
	if (!validateField($("#descriptionRest"))) {
		toastr.error("Ne možete dodati restoran. Unesite opis restorana!")
		$("#descriptionRest").focus();
		return false;
	}
	return true;
}

function validateFormAdmin() {
	if (!validateField($("#imeAdm"))) {
		toastr.error("Niste uneli ime!");
		$("#imeAdm").focus();
		return false;
	}
	if (!validateField($("#przAdm"))) {
		toastr.error("Niste uneli prezime!");
		$("#przAdm").focus();
		return false;
	}
	if (!validateField($("#emailAdm"))) {
		toastr.error("Niste uneli email!");
		$("#emailAdm").focus();
		return false;
	}
	if (!validateField($("#passAdm"))) {
		toastr.error("Niste uneli lozinku!");
		$("#passAdm").focus();
		return false;
	}
	if (!validateField($("#passAdmRep"))) {
		toastr.error("Niste uneli ponovljenu lozinku!");
		$("#passAdmRep").focus();
		return false;
	}
	if (!compareTwoFields($("#passAdmRep"), $("#passAdm"))) {
		$("#passAdmRep").focus();
		$("#passAdm").focus();
		toastr.error("Ponovljena lozinka se ne poklapa. Ponovite unos!")
		return false;
	}
	return true;
}

function compareTwoFields(field1, field2) {
	if (field1.val() != field2.val()) {
		return false;
	} else {
		return true;
	}
}

function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}
