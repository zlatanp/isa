$(document).ready(function(){
	$modal = $("#myModal");
	$close = $(".close");
	
	$modal.css("display", "block");
});

$(document).on("click", ".close", function(){
	$modal = $("#myModal");
	$modal.css("display", "none");
	var url = "http://localhost:8080/index.html";
	$(location).attr('href', url);
});

$(document).on("click", "#btnOK", function(e){
	e.preventDefault();
	if(!validateForm()){
		return;
	}
	var newPassword = new Object();
	newPassword["email"] = $("#email").val();
	newPassword["stara"] = $("#stara").val();
	newPassword["nova"] = $("#nova").val();
	promeniLozinku(JSON.stringify(newPassword));
});


function promeniLozinku(newPassword){
	$.ajax({
		url: 'korisnik/changeFirstPass',
		type: 'POST',
		data: newPassword,
		contentType : 'application/json',
		success: function(ret){
			console.log(ret);
			if(ret == null || ret == ""){
				toastr.error("Neuspešna promena lozinke. Ponovite postupak.");
				$("#PromeniLozinkuForm")[0].reset();
				return;
			}else {
				toastr.success("Uspešno ste promenili lozinku.")
				var tip = ret.tip;
				preusmeriGa(tip);
			}
		},
		error: function(xhr, textStatus, errorThrown) { 
	        toastr.error('Error!  Status = ' + xhr.status); 
		}
	});
}

function preusmeriGa(tip){
	var url = "";
	if(tip === "MENADZERSISTEMA"){
		url= "http://localhost:8080/adminPage.html";
		$(location).attr('href', url);
	}else if(tip === "MENADZERRESTORANA"){
		url = "http://localhost:8080/menadzerPage.html";
		$(location).attr('href', url);			
	}
	// ovde dodaj i za ostale koji se budu pravili
}


function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}

function compareTwoFields(field1, field2) {
	if (field1.val() != field2.val()) {
		return false;
	} else {
		return true;
	}
}

function validateForm(){
	if(!validateField($("#email"))){
		toastr.error("Niste uneli ime email");
		$("#email").focus();
		return false;
	}
	if(!validateField($("#stara"))){
		toastr.error("Niste uneli lozinku koja vam je dostavljena na email.");
		$("#stara").focus();
		return false;
	}
	if(!validateField($("#nova"))){
		toastr.error("Niste uneli novu lozinku.");
		$("#nova").focus();
		return false;
	}
	if(!validateField($("#novaPon"))){
		toastr.error("Niste uneli ponovljenu lozinku.");
		$("#novaPon").focus();
		return false;
	}
	if(!compareTwoFields($("#novaPon"),$("#nova"))){
		$("#nova").focus();
		$("#novaPon").focus();
		toastr.error("Lozinke se ne poklapaju! Ponovite unos.")
		return false;
	}
	if($("#nova").val().length < 5){
		$("#nova").focus();
		toastr.error("Lozinka mora imati minimum 5 karaktera.");
		return false;
	}
	return true;
}
