var emailLogovanog = null;
var cookie = null;
var restoran = null;
var restoranID = null;

function getIdFromUrl(){
	var id = null;
	if (window.location.href.indexOf('?') != -1){
		id = window.location.href.split('?')[1];
	}
	return id;
}

//POSTAVKA
$(document).ready(function(){
	cookie = document.cookie;
	emailLogovanog = cookie.split("=")[1];
	
	restoranID = getIdFromUrl();
	
	$("#datRodj").datepicker({
		changeMonth: true,
	    changeYear: true,
	    yearRange: "-20:+0",
	    showOn: "both",
	    buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
	    buttonImageOnly: true 
	});
	
    $("#lozZap").val(makePassword());
    
	$.ajax({	     
		type: 'GET',
		url: 'restoran/dobaviRestoran', 
		dataType: 'JSON',
        data: {email : emailLogovanog},
        success: function(data){
 			console.log(data);
 			if(data != null){
 				$("#logo").text(data.naziv);
 				$("#opis").text(data.opis);
 			}
        }
	});
});


//KLIKOVI NAVBAR UVEK ISTI 
$(document).on("click", "#jeloHref", function(e) {
	e.preventDefault();
	window.location.replace("jelovnik.html?" + restoranID);
});

$(document).on("click", "#piceHref", function(e) {
	e.preventDefault();
	window.location.replace("kartaPica.html?" + restoranID);
});

$(document).on("click", "#sedenjeHref", function(e) {
	e.preventDefault();
	window.location.replace("sedenje.html?" + restoranID);
});

$(document).on("click", ".OpenPonude", function(e) {
	e.preventDefault();
	window.location.replace("ponude.html?" + restoranID);
});

$(document).on("click", ".OpenZaposleni", function(e) {
	e.preventDefault();
	window.location.replace("zaposleni.html?" + restoranID);
});

$(document).on("click", ".OpenUpdateRest", function(e) {
	e.preventDefault();
	window.location.replace("menadzerPage.html");
});

//KLIKOVI SPECIFICNI
$(document).on("click", "#btnRegZaposlenog", function(e){
	e.preventDefault();
	if(!validateFormRegZap()){
		return;
	}
	
	var noviZaposleni = new Object();
	var ime = $("#imeZap").val();
	var prezime = $("#przZap").val();
	var email = $("#emailZap").val();
	var loz = $("#lozZap").val();
	var datumRodj = $("#datRodj").val();
	var konfBr = $("#konfBr").val();
	var obuca = $("#velObuce").val();
	var tip = $("#tipSel").val();
	
	
	noviZaposleni['ime'] = ime;
	noviZaposleni['prezime'] = prezime;
	noviZaposleni['email'] = email;
	noviZaposleni['password'] = loz;
	noviZaposleni['datum_rodjenja'] = datumRodj;
	noviZaposleni['konfekcijski_broj'] = konfBr;
	noviZaposleni['velicina_obuce'] = obuca;
	noviZaposleni['tip'] = tip;
    noviZaposleni['radi_u'] = restoranID;
    	
    	
	if(tip === "KONOBAR"){
		dodajKonobara(JSON.stringify(noviZaposleni));
	}else if(tip === "KUVAR"){
		dodajKuvara(JSON.stringify(noviZaposleni));
	}else {
		alert("odabrao sankera");
	}
});


function dodajKuvara(kuvarJSON){
	
}

function dodajKonobara(konobarJSON){
	
}

function dodajSankera(sankerJSON){
	
}

// VALIDACIJA FORME
function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}

function validateFormRegZap(){
	if(!validateField($("#imeZap"))){
		toastr.error("Morate uneti ime zaposlenog.");
		$("#imeZap").focus();
		return false;
	}
	if(!validateField($("#przZap"))){
		toastr.error("Morate uneti prezime zaposlenog.");
		$("#przZap").focus();
		return false;
	}
	if(!validateField($("#emailZap"))){
		toastr.error("Morate uneti e-mail zaposlenog.");
		$("#emailZap").focus();
		return false;
	}
	if(!validateField($("#datRodj"))){
		toastr.error("Morate uneti datum rođenja zaposlenog.");
		$("#datRodj").focus();
		return false;
	}
	if(!validateField($("#tipZapSel"))){
		toastr.error("Morate odabrati tip zaposlenog.");
		$("#tipZapSel").focus();
		return false;
	}
	if(!validateField($("#konfBr"))){
		toastr.error("Morate uneti konfekcijski broj za zaposlenog.");
		$("#konfBr").focus();
		return false;
	}
	if(!validateField($("#velObuce"))){
		toastr.error("Morate uneti veličinu obuće za zaposlenog.");
		$("#velObuce").focus();
		return false;
	}
	var obuca = $("#velObuce").val();
	var konf = $("#konfBr").val();
	if(isNaN(obuca) || obuca < 35 || obuca > 50){
		toastr.error("Veličina obuće mora biti pozitivan broj od 35 do 50.");
		$("#velObuce").focus();
		return false;
	}
	if(isNaN(konf) || konf < 32 || konf > 50){
		toastr.error("Konfekcijski broj mora biti pozitivan broj od 32 do 50.");
		$("#konfBr").focus();
		return false;
	}
	return true;
}

function makePassword(){
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}