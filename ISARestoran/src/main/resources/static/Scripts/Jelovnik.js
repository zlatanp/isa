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
	console.log("get restoran id:" + id)
	$.ajax({
		url: 'restoran/restoranProfil/' + id,
		type: 'GET',
		dataType: 'json',
		async: false,
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
				window.location.replace("notFound.html");
			}
		},
		error: function(){
			window.location.replace("notFound.html");
					
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
				window.location.replace("notFound.html");
				return;
			}else {
				prikaziJela(retVal);
			}
		},
		error: function(){
			window.location.replace("notFound.html");
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
				'<div class="card text-center my-card" style="margin-top:2%;border-style:solid;border-width:2px;background-color:#ccff99">' +
					'<div class="card-block">' +
						'<h3 class="card-title"><a id="editid' + jelo.id + '" class="EditDijalogOpen" href="#" style="color:#003300">' + jelo.naziv + '</a></h3>' +				
					'</div>'+
					'<img src="../../../slike/jela/' + jelo.slika + '"width="180" height="180" >' +
					'<div class="card-block">'+
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
						'<h4 class="pull-right" style="text-align:left;">'+
							valuta + ' ' + jelo.cena +
						'</h4>'+
						'<br>'+ '<br>'+ 
						'<h4 class="pull-right" style="text-align:left;"><a id="obrisime' + jelo.id + '" onclick="kantaPritisnuto(\''+jelo.id+'\')" href="#"><i class="glyphicon glyphicon-trash"></i></a></h4>'+
					'</div>' +
				'</div>'+
			'</div>';
	
	return html;		
}


function kantaPritisnuto(id){
	$("#obrisiJeloModal").appendTo("body").modal("show");
	$("#idJelaZaDelete").text(id);
}

$(document).on("click", "#btnTraziJela", function(e){
	e.preventDefault();
	var zaTrazenje = new Object();
	zaTrazenje.val1 = $("#imeZaPretragu").val();
	zaTrazenje.val2 = "";
	console.log(zaTrazenje.val1);
	if (/\S/.test(zaTrazenje.val1) || /\S/.test(zaTrazenje.val2)){
		$.ajax({
			url: 'restoran/traziJelo/' + restoranID,
			type: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(zaTrazenje),
			success: function(retVal){
				prikaziJela(retVal);
			}
		});		
	}
});

$(document).on("click", "#jeloHref", function(e){
	e.preventDefault();
	window.location.replace("jelovnik.html?" + restoranID);
});

$(document).on("click", "#piceHref", function(e){
	e.preventDefault();
	window.location.replace("kartaPica.html?" + restoranID);
});

$(document).on("click", "#sedenjeHref", function(e){
	e.preventDefault();
	window.location.replace("sedenje.html?" + restoranID);
});

$(document).on("click", "#btnPrikaziJela", function(){
	if(restoranID != null){
		getJela(restoranID);
	}
});

$(document).on("click", ".ObrisiDijalogOpen", function(e){
	e.preventDefault();
	$("#obrisiJeloModal").appendTo("body").modal("show");
	var id = $(".ObrisiDijalogOpen").prop("id");
	alert(id);
	var idPravi = id.split("obrisime")[1];
	alert(idPravi)
	$("#idJelaZaDelete").text(idPravi);
});

$(document).on("click", ".EditDijalogOpen", function(e){
	e.preventDefault();
	$('#izmeniJeloModal').appendTo("body").modal('show');
});

$(document).on("click", "#btnOdustaniDelete", function(e){
	e.preventDefault();
	$('#obrisiJeloModal').modal('toggle');
});

$(document).on("click", "#btnPotvrdiDelete", function(e){
	e.preventDefault();
	var id = $("#idJelaZaDelete").text();
	var jelo = new Object();
	jelo['id'] = id;
	$.ajax({
		url: 'restoran/obrisiJelo',
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(jelo),	
		dataType: 'json',
		success: function(ret) {
			if(!ret){
				toastr.error("Došlo je do greške prilikom brisanja!");
				return;
			}else {
				toastr.success("Jelo je uspešno obrisano");
				$('#obrisiJeloModal').modal('toggle');
				getJela(restoranID);
			}
		}
	});
})

$(document).click(function(event){
	var idTarget = event.target.id;
	if(idTarget.startsWith("editid")){
		var idJela = idTarget.split("editid")[1]; //editid2
		var naziv = document.getElementById("nazivJela2");
		var opis = document.getElementById("opisJela2");
		var klasa = document.getElementById("klasaJelaSel2");
		var tip = document.getElementById("tipJelaSel2");
		var cena = document.getElementById("cenaJela2");
		var id2 = document.getElementById("Id2");
		var jeloZaIzmenu = new Object();
		jeloZaIzmenu['id'] = idJela;
		$.ajax({
			url: 'restoran/getJelo',
			type: 'POST',
			dataType: 'json',
			contentType: "application/json",
			data: JSON.stringify(jeloZaIzmenu),
			success: function(ret){
				naziv.value = ret.naziv;
				opis.value = ret.opis;
				cena.value = ret.cena;
				klasa.value = ret.klasaJela;
				tip.value = ret.tipJela;
				id2.value = ret.id;
			}
		});
	}	
});


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

$(document).on("click", "#btnIzmeniJelo", function(e){
	e.preventDefault();
	if(!validateUpdateJeloForm()){
		return;
	}
	var jeloUpdate = new Object();
	var id = $("#Id2").val();
	var naziv = $("#nazivJela2").val();
	var cena = $("#cenaJela2").val();
	var opis = $("#opisJela2").val();
	var klasa = $("#klasaJelaSel2").val();
	var tip = $("#tipJelaSel2").val();
	var slikaString = "";
	var slika = $("#slikaJela2");
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
	
	jeloUpdate['id'] = id;
	jeloUpdate['naziv'] = naziv;
	jeloUpdate['opis'] = opis;
	jeloUpdate['cena'] = cena;
	jeloUpdate['klasaJela'] = klasa;
	jeloUpdate['tipJela'] = tip;
	jeloUpdate['slika'] = slikaString;
	izmeniJelo(JSON.stringify(jeloUpdate), file);
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
	dodajNovoJelo(JSON.stringify(novoJelo), file);
});

function izmeniJelo(jeloJSON, file){
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("jelo", jeloJSON);
	$.ajax({
		url: 'restoran/izmeniJelo',
		type: 'POST',
		async: false,
		contentType: 'application/json; charset=utf-8',
		data: formdata,
		dataType: 'JSON',
		xhr: function() { 
			var myXhr = $.ajaxSettings.xhr();
			return myXhr;
		},
		cache: false,
		contentType: false,
		processData: false,	
	    success: function(ret){
	    	if(!ret){
	    		toastr.error("Došlo je do greške prilikom izmene jela!");
				return;
	    	}else {
	    		toastr.success("Jelo je uspešno izmenjeno!");
				$("#izmeniJeloModal").modal("toggle");
				getJela(restoranID);
	    	}
	    }
	});
}

function dodajNovoJelo(novoJelo, file){
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("jelo", novoJelo);
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
	    		toastr.error("Došlo je do greške prilikom dodavanja jela!");
				return;
	    	}else {
	    		toastr.success("Jelo je uspešno dodato!");
	    		$("#modalJelo").find("form")[0].reset();
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

function validateUpdateJeloForm(){
	if(!validateField($("#nazivJela2"))){
		toastr.error("Unesite naziv jela!")
		$("#nazivJela2").focus();
		return false;
	}
	if(!validateField($("#opisJela2"))){
		toastr.error("Unesite opis jela!")
		$("#opisJela2").focus();
		return false;
	}
	if(!validateField($("#cenaJela2"))){
		toastr.error("Unesite cenu jela!")
		$("#cenaJela2").focus();
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

function profilmidaj() {
	$('#logoDiv').hide();
	$('.jelovnikDiv').hide();

	$('#glavniDiv').show();
	$('#leviDiv').show();
	$('#desniDiv').show();
	
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	
	if(email.search("@")!=-1){
     	$.ajax({ 
             type: 'GET',
             url: 'profile/dajMenadzera/'+email,
             dataType: 'json',
     		 success: function(data){
             			console.log(data);
             			document.getElementById("helloHeder").innerHTML = data.ime+" "+data.prezime;
             			ime = data.ime;
             			prezime = data.prezime;
            		}
     	});
     	dajSliku();
     	basicDetails();
	}
}

function dajSliku() {
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	$.ajax({
		url : 'picture/dajSliku/' + email,
		type : "GET",
		contentType : "image/jpg",
		dataType : "text",
		success : function(data) {
			$("#mestoZaSliku").html(
					'<img src="data:image/jpg;base64,' + data
							+ '" style="width:260px;height:260px;" />');
		}
	});
	
}

function basicDetails(){
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	$.ajax({ 
        type: 'GET',
        url: 'profile/dajMenadzera/'+email,
        dataType: 'json',
		 success: function(data){
        			console.log(data);
        			$('#desniDiv').html('<h3><i>Osnovni podaci</i></h3><br><table id="tabela" border="1" ><tr><td>Ime: </td><td><input style="border:none" readonly type="text" id="FirstName" value="'+data.ime+'"></td></tr><tr><td>Prezime: </td><td><input readonly style="border:none" type="text" id="LastName" value="'+data.prezime+'"></td></tr><tr><td>Email adresa: </td><td><input readonly style="border:none" type="text" id="Email" value="'+data.email+'"></td></tr></table><br><div id="zaUpload"></div><br></br><button id="editDetails" onclick="editDetails()">Izmeni osnovne podatke</button><button id="saveDetails" onclick="return saveDetails()" style = "display: none">Sačuvaj</button>&nbsp;<button id="cancelDetails" onclick="basicDetails()" style = "display: none">Odustani od izmena</button><br></br>');
        			$('#desniDiv').show();	
		 }
	});
	
	
}
function editDetails(){
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	
	
	
	$('#editDetails').hide();
	$('#saveDetails').show();
	$('#cancelDetails').show();
	$('#tabela').append('<tr><td>Nova šifra: </td><td><input style="border:none" type="password" id="password"></td></tr><tr><td>Ponovljena nova šifra: </td><td><input style="border:none" type="password" id="password1"></td></tr>');
	$('#zaUpload').html('<form method="POST" action="picture/upload/managerPage/'+email+'" enctype="multipart/form-data">Dodaj novu profilnu sliku: <br><input type="file" id="file" name="file"><input type="hidden" name="email" value="'+email+'"><br><input onclick="return proveraVelicine()" type="submit" value="Učitaj"></form>');
	
	$('#FirstName').attr('readonly', false);
	$('#LastName').attr('readonly', false);
	
}

function proveraVelicine(){
	 if (!window.FileReader) {
	        alert("Pretraživač ne omogućava učitavanje slika!");
	        return false;
	    }
	 
	 input = document.getElementById('file');
	 file = input.files[0];
	 
	 if(!file){
	       alert("Morate izabrati sliku pre pritiskanja 'Učitaj'");
	       return false;
	 }else if(file.size>999998){
		 alert("Maksimalna veličina slike mora biti manja od 1MB!");
		 return false;
	 }else{
		 return true;
	 }
	 
}
	 


function saveDetails(){
	var i = document.getElementById('FirstName');
	var ime = i.value;
	var j = document.getElementById('LastName');
	var prezime = j.value;
	var m = document.getElementById('Email');
	var email = m.value;
	var k = document.getElementById('password');
	var password = k.value;
	var l = document.getElementById('password1');
	var password1 = l.value;
	
	if(ime == "" || prezime == "" || email == "" || password == "" || password1 == ""){
		alert("Morate uneti sva polja!");
		return false;
	}else{
    	if(password.length > 30){
    		alert("Šifra mora da bude između 8-30 karaktera!")
    		return false;
    	}
    	
    	if(password != password1){
    		alert("Nova šifra i ponovljena se ne podudaraju!")
    		return false;
    	}
    	
    	$.ajax({ 
            type: 'GET',
            url: 'profile/editMenadzera',
            dataType: 'json',
            data: {ime : ime, prezime : prezime, email : email, password : password, password1 : password1},
    		 success: function(data){
            			console.log(data);
            			location.reload();
            			return true;
    		 }
    	});
    	
    	return true;
    	
	}
}