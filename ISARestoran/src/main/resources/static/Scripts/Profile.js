function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

$(document).ready(function(){
	$("#timepickerOD").timepicker();
	$("#timepickerDO").timepicker();
	
	$('.selectpicker').selectpicker({
		  style: 'btn-info',
		  size: 4
		});

	$divsForHide = $(".formWrapper");
	$divsForHide.hide();
	
	$(".OpenNewRest").on("click", function(){
		$divsForHide.hide();
		$("#NewRest").show();
	});

	$(".OpenNewManForRest").on("click", function(){
		$divsForHide.hide();
		$("#NewManForRest").show();
	});

	$(".OpenNewSystemMan").on("click", function(){
		$divsForHide.hide();
		$("#NewSystemMan").show();
	});
	
});

$(document).on("click", ".OpenNewRest", function(e){
	$.ajax({
		url: 'restoran/tipovi',
		type: 'GET',
		dataType: 'JSON',
		success: function(data){
			//console.log(data);
			var selektPolje = $("#tipRestSel");
			$.each(data, function(index, tip){
				selektPolje.append("<option value=\"" + tip + "\">"+ tip + "</option>");
			});
		}
	});
});

$(document).on("click", "#btnDodajRestoran", function(e){
	//Proveri formu da li je validna
	e.preventDefault();
	if(!validateFormRestoran()){
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

// neki komentar
function registerRestoran(restoranJSON){
	$.ajax({
		url: 'restoran/register',
		type: 'POST',
		contentType: 'application/json',
		data: restoranJSON,
		success: function(ret){
			console.log(ret);
			toastr.success("Restoran je uspešno registrovan!")
			$("#NewRest").hide();
		}
	});
}

function validateFormRestoran(){
	if(!validateField($("#nameRest"))){
		toastr.error("Ne možete dodati restoran. Unesite naziv restorana!")
		$("#nameRest").focus();
		return false;
	}
	if(!validateField($("#addressRest"))){
		toastr.error("Ne možete dodati restoran. Unesite adresu restorana!")
		$("#addressRest").focus();
		return false;
	}
	if(!validateField($("#cityRest"))){
		toastr.error("Ne možete dodati restoran. Unesite grad restorana!")
		$("#cityRest").focus();
		return false;
	}
	if(!validateField($("#phoneRest"))){
		toastr.error("Ne možete dodati restoran. Unesite telefon restorana!")
		$("#phoneRest").focus();
		return false;
	}
	if(!validateField($("#emailRest"))){
		toastr.error("Ne možete dodati restoran. Unesite e-mail restorana!")
		$("#emailRest").focus();
		return false;
	}
	if(!validateField($("#descriptionRest"))){
		toastr.error("Ne možete dodati restoran. Unesite opis restorana!")
		$("#descriptionRest").focus();
		return false;
	}
	return true;
}

function validateField(field){
	if(field.val() == ""){
		return false;
	}else {
		return true;
	}
}



