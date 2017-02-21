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
		div.append(novi_div);
		var a = div.children().last();
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
					'<img src="/projekat_sjs/slike/jela/' + jelo.slika + '" width="180" height="180" >' +
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