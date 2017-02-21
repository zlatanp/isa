var emailLogovanog = null;
var cookie = null;
var restoran = null;
var restoranID = null;
var valuta = null;
var karticaJela = 0;

function getIdFromUrl(){
	var id = null;
	if (window.location.href.indexOf('?') != -1){
		id = window.location.href.split('?')[1];
	}
	return id;
}

$(document).ready(function(){
	cookie = document.cookie;
	emailLogovanog = cookie.split("=")[1];
	
	restoranID = getIdFromUrl();
		
	uzmiJela();
	
	$(".OpenUpdateRest").on("click", function(){
		window.location.replace("menadzerPage.html");
	});
	
	$(".OpenZaposleni").on("click", function(){
		window.location.replace("zaposleni.html");
	});
	
	$(".OpenPonude").on("click", function(){
		window.location.replace("ponude.html");
	});
});

function uzmiJela(){
	var idRest = getIdFromUrl();
	if(idRest != null){
		restoranID = idRest;
		getRestoran(idRest);
		getJela(idRest);
	}else {
		alert("Nemam id u linku.");
	}
}

function getRestoran(id){
	$.ajax({
		url: 'restoran/restoranProfil/' + id,
		type: 'GET',
		dataType: 'json',
		//async: false, 				//izvrsi ovaj ajax poziv pre svega ostalog
		success: function(retVal){
			if (retVal != "" && retVal != null){
				$("#logo").text(retVal.naziv);
				$("#opis").text(retVal.opis);
				restoran = retVal;
				if(retVal.valuta=="DOLAR")
					valuta = "$";
				if(retVal.valuta=="DINAR")
					valuta = "RSD";
				if(retVal.valuta=="EVRO")
					valuta = "€";
			}else{
				alert("upao ovde");
				window.location.replace('not_found.html');
			}
		},
		error: function(){
			window.location.replace('not_found.html');
		}
	});	
}

function getJela(idRestorana){
	$.ajax({
		type: 'GET',
		url: 'restoran/getJela/' + idRestorana,
		dataType: 'JSON',
		success: function(retVal){
			if(retVal == null){
				alert("nema jela");
				window.location.replace('not_found.html');
				return;
			}else {
				prikaziJela(retVal);
			}
		},
		error: function(){
			window.location.replace('not_found.html');
		}		
	});
}

function prikaziJela(jela){
	var divJela = $("#cards_jela");
	karticeJela = 0;
	divJela.empty();
	var speed = 0;
	$.each(jela, function(index, k){
		var jeloCard = napraviKarticuZaJelo(k);
		var novi_div = "<div></div>";
		divJela.append(novi_div);
		var a = divJela.children().last();
		a.hide();
		a.append(jeloCard);
		a.delay(speed).fadeIn('fast');
		speed+=200;
	});
}

function napraviKarticuZaJelo(jelo){
	var html = "";
	if (karticeJela % 3 == 0){
		if (karticeJela != 0){
			html += '</div>';
		}
		html += '<div class="row-content">';
	}
	karticeJela++;
	html += '<div class="col-md-4">' + 
				'<div class="card text-center my-card" style="margin-top:2%;">' +
					'<div class="card-block">' +
						'<h3 class="card-title trunc-name"><a id="editid' + jelo.id + '" class="otvoriModal2">' + jelo.naziv + '</a></h3>' +
					'</div>'+
					'<img src="/restoran/slike/jela/default_jelo.jpg" width="180" height="180" >' +
					'<div class="card-block trunc-2-lines">'+
						jelo.opis +
					'</div>'+
					'<div class="card-block">'+
						'<div class="zvezdiceJelo pull-right">'+
							'<p>' + convertScoreToStars(jelo.ocena) + '</p>' +
						'</div>'+
						'<h5 style="text-align:left;">'+
							jelo.klasaJela +
						'</h5>'+
						'<br>'+
						'<div class="reviewsJelo pull-right">'+
							jelo.brojOcena + ' reviews' +
						'</div>'+
						'<br>'+
						'<h4 class="cena-jela pull-right" style="text-align:left;">'+
							valuta + ' ' + jelo.cena +
						'</h4>'+
					'</div>' +
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

function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

$("#dugmeDodaj").on("click", function(e){
	e.preventDefault();
	$('#modalJelo').appendTo("body").modal('show');
});

$(document).on("click", "#btnDodajJelo", function(e){
	e.preventDefault();
	if(!validateJeloForm()){
		return;
	}
	// obrisi sva polja u successu
	var novoJelo = new Object();
	
	var naziv = $("#nazivJela").val();
	var cena = $("#cenaJela").val();
	var opis = $("#opisJela").val();
	var klasa = $("#klasaJelaSel").val();
	var tip = $("#tipJelaSel").val();
	var slikaString = "";
	var slika = $("#slikaJela");
	var file = null;
	if(slika.val() != ""){
		file = slika.get(0).files[0];
		if (file != null && !file.type.match('image.*')){
			toastr.warrning("Pogrešan format fajla. Morate odabrati sliku!");
			return;
		}
		slikaString = "1";
	}
	
	if(!isNumber(cena)){
		toastr.error("Cena mora biti decimalan broj!");
		return;
	}
	if(parseFloat(cena)<=0){
		toastr.error("Cena mora biti pozitivan broj!");
		return;
	}
	
	novoJelo['naziv'] = naziv;
	novoJelo['opis'] = opis;
	novoJelo['cena'] = cena;
	novoJelo['klasaJela'] = klasa;
	novoJelo['tipJela'] = tip;
	novoJelo['slika'] = slikaString;
	alert(JSON.stringify(novoJelo));
	dodajNovoJelo(JSON.stringify(novoJelo), file);
});

function dodajNovoJelo(novoJelo, file){
	alert(novoJelo);
	alert(file);
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("jelo", novoJelo);
	console.log(formdata);
	$.ajax({
		url: 'restoran/dodajJelo/'+emailLogovanog,
		type: 'POST',
		async: false,
		contentType: 'application/json; charset=utf-8',
		data: formdata,
		dataType: 'JSON',
		xhr: function() {  // Custom XMLHttpRequest
			var myXhr = $.ajaxSettings.xhr();
			return myXhr;
		},
		cache: false,
		contentType: false,
		processData: false,	
	    success: function(ret){
	    	if(!ret){
	    		toastr.error("Došlo je do greške prilikom dodavanja!");
				return;
	    	}else {
	    		toastr.info("Jelo uspesno dodano!");
				$("#modalJelo").modal("toggle");
				getJela(restoranID);
	    	}
	    }
	});
}

function isNumber( obj ) {
    return !jQuery.isArray( obj ) && (obj - parseFloat( obj ) + 1) >= 0;
}

function validateJeloForm(){
	if(!validateField($("#nazivJela"))){
		toastr.error("Unesite naziv jela!")
		$("#nazivJela").focus();
		return false;
	}
	if(!validateField($("#cenaJela"))){
		toastr.error("Unestie cenu jela!")
		$("#cenaJela").focus();
		return false;
	}
	if(!validateField($("#opisJela"))){
		toastr.error("Unestie opis jela!")
		$("#opisJela").focus();
		return false;
	}
	return true;
}

function validateField(field){
	if(field.val() === ""){
		return false;
	}else {
		return true;
	}
}