var restoranID = null;
var emailLogovanog = null;
var cookie = null;

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
	
});

function izlogujSe() {
	var x = document.cookie;
	var delovi = x.split("=");
	var emailUlogovanog = delovi[1];

	if (emailUlogovanog.search("@") != -1) {
		document.cookie = "emailValue=" + "";
		window.location.href = "index.html";
	}
}

$(".OpenZaposleni").on("click", function(){
	window.location.replace("zaposleni.html");
});

$(".OpenPonude").on("click", function(){
	window.location.replace("ponude.html?" + restoranID);
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

$(document).on("click", "#zaposleniHref", function(e){
	e.preventDefault();
	window.location.replace("zaposleni.html?" + restoranID);
});

$(document).on("click", "#nabavkeHref", function(e){
	e.preventDefault();
	window.location.replace("nabavke.html?" + restoranID);
});

function profilmidaj() {
	$('#logoDiv').hide();
	$('.sakrijZaProfil').hide();
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
