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

function getRestoran(){
	$.ajax({	     
		type: 'GET',
		url: 'restoran/dobaviRestoran', 
		dataType: 'JSON',
        data: {email : emailLogovanog},
        success: function(data){
 			if(data != null){
 				$("#logo").text(data.naziv);
 				$("#opis").text(data.opis);
 			}
        }
	});
}

function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

function makePassword(){
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

//valiacija EMAIL-a
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

function validateFormPon(){
	if(!validateField($("#imePon"))){
		toastr.error("Morate uneti ime ponuđača.");
		$("#imePon").focus();
		return false;
	}
	if(!validateField($("#przPon"))){
		toastr.error("Morate uneti prezime ponuđača.");
		$("#przPon").focus();
		return false;
	}
	if(!validateField($("#emailPon"))){
		toastr.error("Morate uneti e-mail ponuđača.");
		$("#emailPon").focus();
		return false;
	}
	if(!validateEmail($("#emailPon").val())){
		toastr.error("E-mail ponuđača nije validan.");
		$("#emailPon").focus();
		return false;
	}
	return true;
}


$(document).ready(function(){
	cookie = document.cookie;
	emailLogovanog = cookie.split("=")[1];
	
	restoranID = getIdFromUrl();
	$("#lozPon").val(makePassword()); 
	 getRestoran();
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

// SPECIFICNI

$(document).on("click", "#btnRegPonudjaca", function(e){
	e.preventDefault();
	if(!validateFormPon()){
		return;
	}
	var noviPonudjac = new Object();
	noviPonudjac['ime'] = $("#imePon").val();
	noviPonudjac['prezime'] = $("#przPon").val();
	noviPonudjac['email'] = $("#emailPon").val();
	noviPonudjac['password'] = $("#lozPon").val();
	noviPonudjac['tip'] = 6;
	registrujPonudjaca(JSON.stringify(noviPonudjac));
});

function registrujPonudjaca(ponudjac){
	$.ajax({
		url: 'korisnik/registerPonudjac',
		type : 'POST',
		contentType: "application/json",
		dataType: 'JSON',
		data: ponudjac,
		success: function(ret){
			if(ret){
				toastr.success("Uspešno ste registrovali ponuđača.");
				$("#modalNoviPonudjac").modal("toggle");
			}else {
				toastr.success("Došlo je do greške prilikom registracije ponuđača. Zauzet e-mail.");
				return;
			}
		}		
	});
}

$(document).on("click", "#dugmePrikaziSve", function(e){
	e.preventDefault();
	$.ajax({
		url: 'ponude/dobaviPonudjace',
		type: 'GET',
		dataType: 'JSON',
		success: function(ret){
			if(ret != "" && ret!=null){
				prikaziPonudjace(ret);
			}else {
				console.log("vario je null ili prazno");
			}
		}
	});
});

function prikaziPonudjace(ponudjaci){
	var trHTML = '';
    $.each(ponudjaci, function (i, p) {
        trHTML += '<tr><td>' + p.ime + '</td><td>' + p.prezime + '</td><td>' + p.email + '</td></tr>';
    });
    $("#tabla_ponudjaci").append(trHTML);
}