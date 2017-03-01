var restoran = null;
var restoranID = null;


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
	$('#glavniDiv').hide();
	$('#leviDiv').hide();
	$('#desniDiv').hide();
	var cookie = document.cookie;
	var emailLogovanog = cookie.split("=")[1];
	
	$("#vremeDORest").timepicker();
	$("#vremeODRest").timepicker();
	
	$divsForHide = $(".hideMe");
	$divsForHide.hide();
	$selektTip = $("#tipRestSel");
	$selektTip.hide();
	$btnSacuvaj = $("#btnSacuvajIzm");
	$btnSacuvaj.hide();
	$("#idRestorana").hide();
	$("#valutaRestorana").hide();
	$("#divInfo").show();
	
	$(".OpenUpdateRest").on("click", function(){
		$('#glavniDiv').hide();
		$('#leviDiv').hide();
		$('#desniDiv').hide();
		
		$divsForHide.hide();
		$("#divRestoran").show();
	});
	
	
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
		
	$.ajax({	     
		type: 'GET',
		url: 'restoran/dobaviRestoran', 
		dataType: 'JSON',
        data: {email : emailLogovanog},
        success: function(data){
 			console.log(data);
 			if(data != null){
 				upisiPodatke(data);
 				$("#logo").text(data.naziv);
 				$("#opis").text(data.opis);
 				$("#adrTD").html(data.adresa);
 				$("#gradTD").html(data.grad);
 				$("#telTD").html(data.telefon);
 				$("#emailTD").html(data.email);
 				$("#tipTD").html(data.tip);
 				$("#radnoVremeTD").text(data.vremeOD + ' -- ' + data.vremeDO);
 				var putanjaSlike = "../../../slike/restorani/"+data.slika;
 				$("#slikaRestImg").attr("src", putanjaSlike);
 				setMap(data.adresa + ', ' + data.grad + ', Srbija');
 				restoran = data;
 				restoranID = data.id;
 			}
        }
	});
	
});




function upisiPodatke(data) {
	$("#nazivRestorana").text(data.naziv);
	$("#nazivRest").val(data.naziv);
	$("#opisRest").val(data.opis);
	$("#telRest").val(data.telefon);
	$("#emailRest").val(data.email);
	$("#adrRest").val(data.adresa);
	$("#gradRest").val(data.grad);
	$("#tipRest").val(data.tip);
	$("#vremeODRest").val(data.vremeOD);
	$("#vremeDORest").val(data.vremeDO);
	$("#idRestorana").text(data.id);
	$("#valutaRestorana").text(data.valuta);
}

$(document).on("click", "#btnIzmeniRest", function(){

	
	$("#btnIzmeniRest").hide();
	$("#btnSacuvajIzm").show();
	$(".inputRest").attr("readonly", false);
	$("#tipRest").hide();
	$("#tipRestSel").show();
	return false;
});


$(document).on("click", "#btnSacuvajIzm", function(e){
	e.preventDefault();
	if(!validateFormRestoran()){
		return;
	}
	var restoranZaIzmenu = new Object();
	var cookie = document.cookie;
	var emailLogovanog = cookie.split("=")[1];
	
	restoranZaIzmenu.id = $("#idRestorana").text();
	restoranZaIzmenu.naziv = $("#nazivRest").val();
	restoranZaIzmenu.adresa = $("#adrRest").val();
	restoranZaIzmenu.grad = $("#gradRest").val();
	restoranZaIzmenu.telefon = $("#telRest").val();
	restoranZaIzmenu.email = $("#emailRest").val();
	restoranZaIzmenu.opis = $("#opisRest").val();
	restoranZaIzmenu.vremeOD = $("#vremeODRest").val();
	restoranZaIzmenu.vremeDO = $("#vremeDORest").val();
	restoranZaIzmenu.tip = $("#tipRestSel").val();
	restoranZaIzmenu.valuta= $("#valutaRestorana").text();
	console.log(JSON.stringify(restoranZaIzmenu));
	sacuvajIzmene(JSON.stringify(restoranZaIzmenu));
});


function sacuvajIzmene(restoran){
	var cookie = document.cookie;
	var emailLogovanog = cookie.split("=")[1];
	
	$.ajax({
		url: 'restoran/updateRestoran/'+emailLogovanog,
		type: 'POST',
		data: restoran,
		contentType : 'application/json; charset=UTF-8',
		success: function(ret){
			if(ret){
				alert("Uspešno ste izmenili podatke o restoranu!");
				location.reload();
			}else {
				toastr.error("Došlo je do greške!");
			}
		},
		error: function(xhr, textStatus, errorThrown) { 
	        toastr.error('Error!  Status = ' + xhr.status);
		}
	});
}


function validateFormRestoran() {
	if (!validateField($("#nazivRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite naziv restorana!")
		$("#nazivRest").focus();
		return false;
	}
	if (!validateField($("#adrRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite adresu restorana!")
		$("#adrRest").focus();
		return false;
	}
	if (!validateField($("#gradRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite grad restorana!")
		$("#gradRest").focus();
		return false;
	}
	if (!validateField($("#telRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite telefon restorana!")
		$("#telRest").focus();
		return false;
	}
	if (!validateField($("#emailRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite e-mail restorana!")
		$("#emailRest").focus();
		return false;
	}
	if(!validateEmail($("#emailRest").val())){
		toastr.error("E-mail restorana nije validan.");
		$("#emailRest").focus();
		return false;
	}
	if (!validateField($("#opisRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite opis restorana!")
		$("#opisRest").focus();
		return false;
	}
	return true;
}

function validateEmail(email){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}

function setMap(address){
	var geocoder = new google.maps.Geocoder();

	geocoder.geocode({'address': address}, function(results,status){
	
	if (status == google.maps.GeocoderStatus.OK){
		var location = results[0].geometry.location;
		var map_options = {
				center :  location,
				zoom : 14
		}
		
		var addressMap = new google.maps.Map(document.getElementById('container-map'),map_options);
		
		var marker = new google.maps.Marker({
			position: location,
			map: addressMap
		})
	}
});
}