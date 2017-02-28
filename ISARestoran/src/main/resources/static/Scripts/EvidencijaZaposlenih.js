var emailLogovanog = null;
var cookie = null;
var restoran = null;
var restoranID = null;
var karticeZaposlenih= 0;

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
	
//	$("#datRodj").datepicker({
//		changeMonth: true,
//	    changeYear: true,
//	    altField: "#datRodj",
//	    dateFormat: "yy-mm-dd",
//	    yearRange: "-60:+0",
//	    showOn: "both",
//	    buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
//	    buttonImageOnly: true 
//	});
	
    $("#lozZap").val(makePassword());
    
    getRestoran();
    getZaposleni();
	
});

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

function getZaposleni(){
	$.ajax({
		type: 'GET',
		url: 'restoran/dobaviZaposlene/' + emailLogovanog,
		dataType: 'JSON',
		success: function(data){
			console.log("get Zaposleni" + data);
			if(data != "" && data!=null){
				prikaziZaposlene(data);
			}else {
				console.log("vario je null ili prazno");
			}
		} 
	});
}


function prikaziZaposlene(data){
	console.log("usao u prikaz zaposlenih");
	console.log(data);
	var divZaposleni = $("#kartice_zaposleni");
	karticeZaposlenih = 0;
	divZaposleni.empty();
	var speed = 0;
	$.each(data, function(index, z){
		var card = makeCardZaposleni(z);
		var novi_div = "<div></div>";
		divZaposleni.append(novi_div);
		var a = divZaposleni.children().last();
		a.hide();
		a.append(card);
		a.delay(speed).fadeIn('fast');
		speed+=200;
	});
	divZaposleni.append('</div>');
}

function makeCardZaposleni(zaposleni){
	console.log(zaposleni.datum_rodjenja);
	var html ="";
	karticeZaposlenih++;
	if (karticeZaposlenih % 3 == 0){
		if (karticeZaposlenih != 0){
			html += '</div>';
		}
		html += '<div class="row-content">';
	}
	var zvezdice, detalji;
	if(zaposleni.tip === "KONOBAR"){
		detalji = 'class="konobarDetail" data-toggle="tab" href="#tab_radnik"';
		zvezdice = convertScoreToStars(zaposleni.ocena);
	}else {
		detalji = 'href="#"';
		zvezdice = '<br>';
	}
	html += '<div class="col-md-4 zaposleniBlok">' + 
	'<div class="card text-center">' +
		'<div class="card-block">'+
			'<div class="email_radnika hidden">' + zaposleni.email + '</div>'+ 
			'<a ' + detalji + '><h3 class="card-title">' + zaposleni.ime + ' ' + zaposleni.prezime + '</h3></a>' +
		'</div>'+
		'<img src="../html/konobar.jpg" width="180" height="180" >' +
		'<div class="card-block">' +
			'<table class="table">' +
				'<tbody>'+
					'<tr>'+
						'<th>Uloga: </th>'+
						'<td class="trunc">' + srediTip(zaposleni.tip) + ' </td>'+
					'</tr>'+
					'<tr>'+
						'<th>Datum rođenja: </th>'+
						'<td class="trunc">' + zaposleni.datum_rodjenja + ' </td>'+
					'</tr>'+
					'<tr>'+
						'<th>Konfekcijski broj: </th>' +
						'<td class="trunc">' + zaposleni.konfekcijski_broj + ' </td>'+
					'</tr>' +
					'<tr>'+
						'<th>Veličina obuće: </th>' +
						'<td class="trunc">' + zaposleni.velicina_obuce + ' </td>'+
					'</tr>' +
				'</tbody>' +
			'</table>'+
		'</div>'+
		'<div class="card-block">'+
			zvezdice +
		'</div>'+
	'</div>'+
'</div>';

return html; 
	
}

function convertScoreToStars(score){
	if (score < 0 || score > 5)
		return "";
	var text = '';
	var rounded = Math.round(score*2)/2;
	for (i = 1; i <= 5; i++){
		rounded -= 1;
		if (rounded == -0.5)
			text += '<i class="icon-star-half-full"></i>';
		else if (rounded >= 0)
			text += '<i class="icon-star"></i>';
		else if (rounded < 0.5)
			text += '<i class="icon-star-empty"></i>';
	}
	return text;
}
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
$(document).on("click", ".konobarDetail", function(e){
	console.log("klik na link");  //ovde je mozda bolji dijalog da se otvori
	var email = $(this).parent().children().first().text();
	$(".controlZap").hide();
	$("#kartice_zaposleni").hide();
	$('#tab_radnik').show();
	$.ajax({
		url: 'restoran/oceneKonobara/' + email,
		type: 'GET',
		dataType: 'json',
		success: function(retVal){
			if (retVal != null && retVal != "")
				prikaziKomentare(retVal);
		}
	});
	$("#naslovKonobar").text($(this).children().first().text());
});

function prikaziKomentare(komentari){
	comments_cards_br = 0;
	var div = $('#cards_comments');
	var speed = 0;
	div.empty();
	var ocena = 0;
	$.each(komentari, function(index, k){
		var card = makeCardComment(k);
		comments_cards_br++;
		ocena += k.ocenaUsluge;
		var novi_div = "<div></div>";
		div.append(novi_div);
		var a = div.children().last();
		a.hide();
		a.append(card);
		a.delay(speed).fadeIn('fast');
		speed+=200;
	});
	if (comments_cards_br != 0)
		ocena /= comments_cards_br;
	$('#zvezdice_za_konobara').html(convertScoreToStars(ocena));
}

function makeCardComment(ocena){
	var html =  '<div class="row">' +
					'<div class="col-sm-1">'+
						'<a href="gost.html?' + ocena.recenzent.email + '"><img src="/projekat_sjs/slike/korisnici/' + ocena.recenzent.slika + '" class="img-circle" style="height: 50px;width: 50px;"></a>'+
					'</div>'+
					'<div class="col-sm-3 white-bg1">'+
						'<a href="gost.html?' + ocena.recenzent.email + '" style="color:black;"><p style="text-align: center;"><u>' + ocena.recenzent.ime + ' ' + ocena.recenzent.prezime + '</u></p></a>'+
						'<p>' + ocena.text + '</p>'+
						'<p style="text-align: right;">' + convertScoreToStars(ocena.ocenaUsluge) + '</p>'+
					'</div>'+
				'</div>';
	return html;
}

$(document).on("click", "#nazad", function(e){
	$("#tab_radnik").hide();
	$(".controlZap").show();
	$("#kartice_zaposleni").show();
});

$(document).on("click", "#dugmePregled", function(e){
	e.preventDefault();
	getZaposleni();
});

$(document).on("click", "#btnTraziZaposlenog", function(e){
	e.preventDefault();
	var zaTrazenje = new Object();
	zaTrazenje.val1 = $("#zapZaPretragu").val();
	zaTrazenje.val2 = "";
	console.log(zaTrazenje.val1);
	if (/\S/.test(zaTrazenje.val1) || /\S/.test(zaTrazenje.val2)){
		$.ajax({
			url: 'restoran/traziZaposlenog/' + restoranID,
			type: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(zaTrazenje),
			success: function(retVal){
				prikaziZaposlene(retVal);
			}
		});		
	}
});

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
	console.log("restoranID: " + restoranID);
    noviZaposleni['radi_u'] = restoranID;
    	
    	
	if(tip === "KONOBAR"){
		dodajKonobara(JSON.stringify(noviZaposleni));
	}else if(tip === "KUVAR"){
		dodajKuvara(JSON.stringify(noviZaposleni));
	}else {
		dodajSankera(JSON.stringify(noviZaposleni));
	}
});


function dodajKuvara(kuvarJSON){
	$.ajax({
		url: 'korisnik/registerKuvar',
		type: 'POST',
		data: kuvarJSON,
		contentType: 'application/json; charset=UTF-8',
		success: function(data){
			console.log(data);
			if(data){
				toastr.success("Uspešno ste registrovali zaposlenog.")
				//$("#modalZaposleni").find("form")[0].reset();
				$("#modalZaposleni").modal("toggle");
				getZaposleni();
			}else {
				toastr.success("Došlo je do greške prilikom registracije zaposlenog.");
				return;
			}
		}
	});
}

function dodajKonobara(konobarJSON){
	$.ajax({
		url: 'korisnik/registerKonobar',
		type: 'POST',
		data: konobarJSON,
		contentType: 'application/json; charset=UTF-8',
		success: function(data){
			console.log(data);
			if(data){
				toastr.success("Uspešno ste registrovali zaposlenog.")
				//$("#modalZaposleni").find("form")[0].reset();
				$("#modalZaposleni").modal("toggle");
				getZaposleni();
			}else {
				toastr.error("Došlo je do greške prilikom registracije zaposlenog.");
				return;
			}
		}
	});
}

function dodajSankera(sankerJSON){
	$.ajax({
		url: 'korisnik/registerSanker',
		type: 'POST',
		data: sankerJSON,
		contentType: 'application/json; charset=UTF-8',
		success: function(data){
			console.log(data);
			if(data){
				toastr.success("Uspešno ste registrovali zaposlenog.")
				//$("#modalZaposleni").find("form")[0].reset();
				$("#modalZaposleni").modal("toggle");
				getZaposleni();
			}else {
				toastr.error("Došlo je do greške prilikom registracije zaposlenog.");
				return;
			}
		}
	});
}

// VALIDACIJA FORME
function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}

//valiacija EMAIL-a
function validateEmail(email){
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
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
	if(!validateEmail($("#emailZap").val())){
		toastr.error("E-mail zaposlenog nije validan.");
		$("#emailZap").focus();
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

function srediTip(tip){
	switch(tip){
		case 'KUVAR' : return "KUVAR";
		case 'SANKER' : return "ŠANKER";
		case 'KONOBAR' : return "KONOBAR";
		default: return ""; 
	}
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