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
		$('#glavniDiv').hide();
	 	$('#leviDiv').hide();
	 	$('#desniDiv').hide();
		$("#NewRest").show();
	});

	$(".OpenNewManForRest").on("click", function() {
		$divsForHide.hide();
		$('#glavniDiv').hide();
	 	$('#leviDiv').hide();
	 	$('#desniDiv').hide();
		$("#NewManForRest").show();
		var lozinka = makePassword();
		$("#lozMen").val(lozinka);
	});

	$(".OpenNewSystemMan").on("click", function() {
		$divsForHide.hide();
		$('#glavniDiv').hide();
	 	$('#leviDiv').hide();
	 	$('#desniDiv').hide();
		$("#NewSystemMan").show();
		var lozinka = makePassword();
		$("#passAdm").val(lozinka);
	});

});
 
$(document).on("click", ".OpenNewRest", function(e) {
			$("#tipRestSel").find("option").remove();
			$.ajax({
				url : 'restoran/tipovi',
				type : 'GET',
				dataType : 'JSON',
				success : function(data) {
					var selektPolje = $("#tipRestSel");
					$.each(data, function(index, tip) {
						selektPolje.append("<option value=\"" + tip + "\">"	+ tip + "</option>");
					});
				}
			});
		});

$(document).on("click", ".OpenNewManForRest", function(e){
	$("#restoranSel").find("option").remove();
	$.ajax({
		url : 'restoran/dobaviRestorane',
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			var selektPolje = $("#restoranSel");
			$.each(data, function(index, restoran){
				selektPolje.append("<option value=\""+ restoran.id + "\">" + restoran.naziv + "</option>")
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
	noviRestoran["tip"] = $("#tipRestSel").val(); // id-restorana u kojem je zaposlen
	var slikaString = "";
	var slika = $("#slikaRestorana");
	var file = null;
	if(slika.val() != ""){
		file = slika.get(0).files[0];
		if (file != null && !file.type.match('image.*')){
			toastr.warrning("Pogrešan format fajla. Morate odabrati sliku!");
			return;
		}
		slikaString = "1";
	}
	noviRestoran["slika"] = slikaString;
	registerRestoran(JSON.stringify(noviRestoran), file);
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
	noviAdmin["password"] = $("#passAdm").val();
	registerAdmin(JSON.stringify(noviAdmin));
});

$(document).on("click", "#btnDodajMenadzera", function(e){
	e.preventDefault();
	if(!validateFormMenadzer()){
		return;
	}
	var noviMenadzer = new Object();
	noviMenadzer["ime"] = $("#imeMen").val();
	noviMenadzer["prezime"] = $("#przMen").val();
	noviMenadzer["email"] = $("#mailMen").val();
	noviMenadzer["password"] = $("#lozMen").val();
	noviMenadzer["radi_u"] = $("#restoranSel").val();
	registerMenadzer(JSON.stringify(noviMenadzer));
});


function registerRestoran(restoranJSON, file) {
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("restoran", restoranJSON);
	$.ajax({
		url : 'restoran/register',
		type : 'POST',
		contentType : 'application/json',
		dataType: 'JSON',
		data : formdata,
		async: false,
		xhr: function() { 
			var myXhr = $.ajaxSettings.xhr();
			return myXhr;
		},
		cache: false,
		contentType: false,
		processData: false,	
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
			if(!ret){
				toastr.error("Korisnik sa ovakvim emailom već postoji.");
				$("#emailAdm").focus();
				return;
			}else {
				toastr.success("Uspešno registrovan novi menadžer sistema.");
				$("#NewAdmForm")[0].reset();
				$("#NewSystemMan").hide();
			}
		}
	});
}

function registerMenadzer(menadzerJSON){
	$.ajax({
		url: 'korisnik/registerMenadzer',
		type: 'POST',
		contentType: 'application/json; charset=UTF-8',
		data: menadzerJSON,
		success: function(ret){
			if(!ret){
				toastr.error("Korisnik sa ovakvim emailom već postoji!");
				$("#mailMen").focus();
				return;
			}else{
				toastr.success("Uspešno ste registrovali menadžera restorana.");
				$("#NewMenForm")[0].reset();
				$("#NewManForRest").hide();
			}
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
	if(!validateEmail($("#emailRest").val())){
		toastr.error("E-mail restorana nije validan.");
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
	if(!validateEmail($("#emailAdm").val())){
		toastr.error("E-mail administratora nije validan.");
		$("#emailAdm").focus();
		return false;
	}
//	if (!validateField($("#passAdmRep"))) {
//		toastr.error("Niste uneli ponovljenu lozinku!");
//		$("#passAdmRep").focus();
//		return false;
//	}
//	if (!compareTwoFields($("#passAdmRep"), $("#passAdm"))) {
//		$("#passAdmRep").focus();
//		$("#passAdm").focus();
//		toastr.error("Ponovljena lozinka se ne poklapa. Ponovite unos!")
//		return false;
//	}
	return true;
}


function validateFormMenadzer(){
	if(!validateField($("#imeMen"))){
		toastr.error("Niste uneli ime menadžera");
		$("#imeMen").focus();
		return false;
	}
	if(!validateField($("#przMen"))){
		toastr.error("Niste uneli prezime menadžera");
		$("#przMen").focus();
		return false;
	}
	if(!validateField($("#mailMen"))){
		toastr.error("Niste uneli email menadžera");
		$("#mailMen").focus();
		return false;
	}
	if(!validateEmail($("#mailMen").val())){
		toastr.error("E-mail menadžera nije validan.");
		$("#mailMen").focus();
		return false;
	}
	if(!validateField($("#lozMen"))){
		toastr.error("Niste uneli lozinku za menadžera");
		$("#lozMen").focus();
		return false;
	}
//	if(!validateField($("#lozMenRep"))){
//		toastr.error("Niste uneli ponovljenu lozinku za menadžera");
//		$("#lozMenRep").focus();
//		return false;
//	}
//	if (!compareTwoFields($("#lozMen"), $("#lozMenRep"))) {
//		$("#lozMenRep").focus();
//		$("#lozMen").focus();
//		toastr.error("Lozinke se ne poklapaju! Ponovite unos.")
//		return false;
//	}
	return true;
}

function validateEmail(email){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
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

function makePassword()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}