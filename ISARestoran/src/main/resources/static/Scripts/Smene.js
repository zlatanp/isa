$(document).on("click", "#btnDodajSmenu", function(){
	alert("Klik");
	if(!validateFormSmena()){
		return;
	}
	var novaSmena = new Object();
	novaSmena["naziv"] = $("#nazivSmene").val();
	novaSmena["vremeOD"] = $("#smenaOD").val();
	novaSmena["vremeDO"] = $("#smenaDO").val();
	console.log(novaSmena);
	
	$.ajax({ 
		type: 'POST',
		url: 'smene/novaSmena',
		data: JSON.stringify(novaSmena),
		dataType: 'json',
		contentType:'application/json',
		async: false,
		success: function(data){
			console.log(data);
			if(data){
				toastr.success("Uspešno ste definisali smenu");
				$("#modalDefSmena").modal('toggle');		
			}else {
				toastr.error("Došlo je do greške prilikom definisanja smene.");
				$("#modalDefSmena").modal('toggle');
			}
 		},
 		error: function(xhr, textStatus, errorThrown) { 
	        toastr.error('Error!  Status = ' + xhr.status);
 		}
	});
});


$(document).ready(function(){
	$('#smenaOD').timepicker({
    	showMeridian: false
    });
	$('#smenaDO').timepicker({
		showMeridian: false
	});
});

$(document).on("click", "#dugmePregledSmena", function(e){
	console.log("prikazuje smene")
	e.preventDefault();
	$.ajax({
		url: 'smene/getAllSmene',
		type: 'GET',
		dataType: 'JSON',
		success: function(ret){
			if(ret != "" && ret!=null){
				prikaziSmene(ret);
			}else {
				toastr.warning("Nema definisanih smena za restoran!");
				return;
			}
		}
	});
});

function prikaziSmene(smene){
	var trHTML = '';
    $.each(smene, function (i, s) {
        trHTML += '<tr><td>' + s.naziv + '</td><td>' + s.vremeOD + '</td><td>' + s.vremeDO + '</td></tr>';
    });
    $("#tabla_smene").append(trHTML);
}

function validateField(field){
	if(field.val() === ""){
		return false;
	}else {
		return true;
	}
}

function validateFormSmena(){
	if(!validateField($("#nazivSmene"))){
		toastr.error("Morate uneti naziv smene.")
		$("#nazivSmene").focus();
		return false;
	}
	if(!validateField($("#smenaOD"))){
		toastr.error("Morate uneti početak smene.")
		$("#smenaOD").focus();
		return false;
	}
	if(!validateField($("#smenaDO"))){
		toastr.error("Morate uneti kraj smene.")
		$("#smenaDO").focus();
		return false;
	}
	return true;
}