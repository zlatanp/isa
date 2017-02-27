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
	if (!validateField($("#opisRest"))) {
		toastr.error("Ne možete izmeniti restoran. Unesite opis restorana!")
		$("#opisRest").focus();
		return false;
	}
	return true;
}

function validateField(field) {
	if (field.val() == "") {
		return false;
	} else {
		return true;
	}
}

$(document).on("click", "#jeloHref", function(e){
	e.preventDefault();
	window.location.replace("jelovnik.html?" + restoran.id);
});

$(document).on("click", "#piceHref", function(e){
	e.preventDefault();
	window.location.replace("kartaPica.html?" + restoran.id);
});

$(document).on("click", "#sedenjeHref", function(e){
	e.preventDefault();
	window.location.replace("sedenje.html?" + restoran.id);
});

$(document).on("click", ".OpenZaposleni", function(e){
	e.preventDefault();
	window.location.replace("zaposleni.html?" + restoranID);
});

$(document).on("click", ".OpenPonude", function(e){
	e.preventDefault();
	window.location.replace("ponude.html?" + restoranID);
});

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

function profilmidaj() {
	$('#logoDiv').hide();
	$('.hideMe').hide();

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