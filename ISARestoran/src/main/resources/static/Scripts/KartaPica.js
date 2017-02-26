var emailLogovanog = null;
var cookie = null;
var restoran = null;
var restoranID = null;
var valuta = null;
var karticaPica = 0;

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
		
	uzmiPica();
	
	$(".OpenUpdateRest").on("click", function(){
		window.location.replace("menadzerPage.html");
	});
	
	$(".OpenZaposleni").on("click", function(){
		window.location.replace("zaposleni.html");
	});
	
	$(".OpenPonude").on("click", function(){
		window.location.replace("ponude.html");
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
});

function uzmiPica(){
	var idRest = getIdFromUrl();
	if(idRest != null){
		restoranID = idRest;
		getRestoran(idRest);
		getPica(idRest);
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

function getPica(idRestorana){
	$.ajax({
		type: 'GET',
		url: 'restoran/getPica/' + idRestorana,
		dataType: 'JSON',
		success: function(retVal){
			if(retVal == null){
				window.location.replace("notFound.html");
				return;
			}else {
				console.log(retVal);
				prikaziPica(retVal);
			}
		},
		error: function(){
			window.location.replace("notFound.html");
		}		
	});
}

function prikaziPica(pica){
	var divPica = $("#cards_pica");
	karticaPica = 0;
	divPica.empty();
	var speed = 0;
	$.each(pica, function(index, k){
		console.log(k.id);
		var piceCard = napraviKarticuZaPice(k);
		var novi_div = "<div></div>";
		divPica.append(novi_div);
		var a = divPica.children().last();
		a.hide();
		a.append(piceCard);
		a.delay(speed).fadeIn('fast');
		speed+=200;
	});
}

function napraviKarticuZaPice(pice){
	var html = "";
	if (karticaPica % 3 == 0){
		if (karticaPica != 0){
			html += '</div>';
		}
		html += '<div class="row-content">';
	}
	karticaPica++;
	html += '<div class="col-md-4">' + 
				'<div class="card text-center my-card" style="margin-top:2%;border-style:solid;border-width:2px;background-color:#ccff99">' +
					'<div class="card-block">' +
						'<h3 class="card-title"><a id="editid' + pice.id + '" class="EditDijalogOpen" href="#" style="color:#003300">' + pice.naziv + '</a></h3>' +				
					'</div>'+
					'<img src="../../../slike/pica/' + pice.slika + '"width="180" height="180" >' +
					'<div class="card-block">'+
						pice.opis +
					'</div>'+
					'<div class="card-block">'+
						'<div class="zvezdiceJelo pull-right">'+
							'<p>' + convertScoreToStars(pice.ocena) + '</p>' +
						'</div>'+
						'<br>'+
						'<div class="pull-right">'+
							pice.brojOcena + ' reviews' +
						'</div>'+
						'<br>'+
						'<h4 class="pull-right" style="text-align:left;">'+
							valuta + ' ' + pice.cena +
						'</h4>'+
						'<br>'+ '<br>'+ 
						'<h4 class="pull-right" style="text-align:left;"><a id="obrisime' + pice.id + '" onclick="dugmePritisnuto(\''+pice.id+'\')" href="#"><i class="glyphicon glyphicon-trash"></i></a></h4>'+
					'</div>' +
				'</div>'+
			'</div>';
	
	return html;		
}

function dugmePritisnuto(id){
	$("#obrisiPiceModal").appendTo("body").modal("show");
	$("#idPicaZaDelete").text(id);
}

$(document).on("click", ".EditDijalogOpen", function(e){
	e.preventDefault();
	$('#modalIzmeniPice').appendTo("body").modal('show');
});

$(document).on("click", "#btnOdustaniDelete", function(e){
	e.preventDefault();
	$('#obrisiPiceModal').modal('toggle');
});

$(document).on("click", "#btnPotvrdiDelete", function(e){
	e.preventDefault();
	var id = $("#idPicaZaDelete").text();
	var pice = new Object();
	pice['id'] = id;
	$.ajax({
		url: 'restoran/obrisiPice',
		type: 'POST',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(pice),	
		dataType: 'json',
		success: function(ret) {
			if(!ret){
				toastr.error("Došlo je do greške prilikom brisanja!");
				return;
			}else {
				toastr.success("Piće je uspešno obrisano");
				$('#obrisiPiceModal').modal('toggle');
				getPica(restoranID);
			}
		}
	});
})


$(document).click(function(event){
	var idTarget = event.target.id;
	if(idTarget.startsWith("editid")){
		var idJela = idTarget.split("editid")[1];
		var naziv = document.getElementById("nazivPica2");
		var opis = document.getElementById("opisPica2");
		var cena = document.getElementById("cenaPica2");
		var id2 = document.getElementById("Id2");
		var piceZaIzmenu = new Object();
		piceZaIzmenu['id'] = idJela;
		$.ajax({
			url: 'restoran/getPice',
			type: 'POST',
			dataType: 'json',
			contentType: "application/json",
			data: JSON.stringify(piceZaIzmenu),
			success: function(ret){
				naziv.value = ret.naziv;
				opis.value = ret.opis;
				cena.value = ret.cena;
				id2.value = ret.id;
			}
		});
	}	
});

$(document).on("click", "#btnIzmeniPice", function(e){
	e.preventDefault();
	if(!validateUpdatePiceForm()){
		return;
	}
	var piceUpdate = new Object();
	var id = $("#Id2").val();
	var naziv = $("#nazivPica2").val();
	var cena = $("#cenaPica2").val();
	var opis = $("#opisPica2").val();
	var slikaString = "";
	var slika = $("#slikaPica2");
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
	
	piceUpdate['id'] = id;
	piceUpdate['naziv'] = naziv;
	piceUpdate['opis'] = opis;
	piceUpdate['cena'] = cena;
	piceUpdate['slika'] = slikaString;
	izmeniPice(JSON.stringify(piceUpdate), file);
});

function izmeniPice(pice, file){
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("pice", pice);
	$.ajax({
		url: 'restoran/izmeniPice',
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
	    		toastr.error("Došlo je do greške prilikom izmene pića!");
				return;
	    	}else {
	    		toastr.success("Piće je uspešno izmenjeno!");
				$("#modalIzmeniPice").modal("toggle");
				getPica(restoranID);
	    	}
	    }
	});
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

function validateUpdatePiceForm(){
	if(!validateField($("#nazivPica2"))){
		toastr.error("Unesite naziv pića!")
		$("#nazivPica2").focus();
		return false;
	}
	if(!validateField($("#opisPica2"))){
		toastr.error("Unesite opis pića!")
		$("#opisPica2").focus();
		return false;
	}
	if(!validateField($("#cenaPica2"))){
		toastr.error("Unesite cenu pića!")
		$("#cenaPica2").focus();
		return false;
	}
	return true;
}

function validateFormDodajPice(){
	if(!validateField($("#nazivPica"))){
		toastr.error("Unesite naziv pića!")
		$("#nazivPica").focus();
		return false;
	}
	if(!validateField($("#opisPica"))){
		toastr.error("Unesite opis pića!")
		$("#opisPica").focus();
		return false;
	}
	if(!validateField($("#cenaPica"))){
		toastr.error("Unesite cenu pića!")
		$("#cenaPica").focus();
		return false;
	}
	return true;
}

$(document).on("click", "#btnDodajPice", function(e){
	e.preventDefault();
	if(!validateFormDodajPice())
		return;
	var novoPice = new Object();
	var naziv = $("#nazivPica").val();
	var opis = $("#opisPica").val();
	var cena = $("#cenaPica").val();
	var slika = $("#slikaPica");
	var slikaString = "";
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
	novoPice['naziv'] = naziv;
	novoPice['opis'] = opis;
	novoPice['cena'] = cena;
	novoPice['slika'] = slikaString;
	dodajNovoPice(JSON.stringify(novoPice), file);
});

function isNumber( obj ) {
    return !jQuery.isArray( obj ) && (obj - parseFloat( obj ) + 1) >= 0;
}


function dodajNovoPice(pice, file){
	var formdata = new FormData();
	formdata.append("uploadfile", file);
	formdata.append("pice", pice);
	$.ajax({
		url: 'restoran/dodajPice/'+ emailLogovanog,
		type: 'POST',
		dataType: 'JSON',
		data: formdata,
		async: false,
		contentType: 'application/json; charset=utf-8',
		xhr: function() {  // Custom XMLHttpRequest
			var myXhr = $.ajaxSettings.xhr();
			return myXhr;
		},
		cache: false,
		contentType: false,
		processData: false,	
	    success: function(ret){
	    	if(!ret){
	    		toastr.error("Došlo je do greške prilikom dodavanja pića!");
				return;
	    	}else {
	    		toastr.success("Piće je uspešno dodato!");
	    		$("#modalPice").find("form")[0].reset();
				$("#modalPice").modal("toggle");
				getPica(restoranID);
	    	}
	    }
	});
}

function validateField(field){
	if(field.val() === ""){
		return false;
	}else {
		return true;
	}
}

$(document).on("click", "#btnTraziPica", function(e){
	e.preventDefault();
	var zaTrazenje = new Object();
	zaTrazenje.val1 = $("#piceImeZaPretragu").val();
	zaTrazenje.val2 = "";
	console.log(zaTrazenje.val1);
	if (/\S/.test(zaTrazenje.val1) || /\S/.test(zaTrazenje.val2)){
		$.ajax({
			url: 'restoran/traziPice/' + restoranID,
			type: 'POST',
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(zaTrazenje),
			success: function(retVal){
				prikaziPica(retVal);
			}
		});		
	}
});

$(document).on("click", "#btnPrikaziPica", function(){
	if(restoranID != null){
		getPica(restoranID);
	}
});

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