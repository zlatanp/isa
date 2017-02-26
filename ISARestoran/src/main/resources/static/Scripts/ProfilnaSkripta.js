function profilMenadzera(){
	$divsForHide = $(".formWrapper");
	$divsForHide.hide();
	
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	
	dajSliku();
	
	if(email.search("@")!=-1){
     	$.ajax({ 
             type: 'GET',
             url: 'profile/gost',
             dataType: 'json',
             data: {email : email},
     		 success: function(data){
             			console.log(data);
             			document.getElementById("helloHeder").innerHTML = data.ime+" "+data.prezime;
             			ime = data.ime;
             			prezime = data.prezime;
            		}
     	});
     	$('#glavniDiv').show();
     	$('#leviDiv').show();
     	$('#desniDiv').hide();
     	
     	basicDetails();
     }
}

function dajSliku(){
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	alert(email);
	 $.ajax({
		    url :'picture/dajSliku/'+email,
		    type: "GET",
		    contentType: "image/jpg",
		    dataType: "text",
		    success: function(data) { 
		            $("#mestoZaSliku").html('<img src="data:image/jpg;base64,' + data + '" style="width:260px;height:260px;" />');
		    }
		    });
		}


function basicDetails(){
	var x = document.cookie;
	var delovi = x.split("=");
	var email = delovi[1];
	$.ajax({ 
        type: 'GET',
        url: 'profile/gost',
        dataType: 'json',
        data: {email : email},
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
	$('#zaUpload').html('<form method="POST" action="picture/upload/adminPage/'+email+'" enctype="multipart/form-data">Dodaj novu profilnu sliku: <br><input type="file" id="file" name="file"><input type="hidden" name="email" value="'+email+'"><br><input onclick="return proveraVelicine()" type="submit" value="Učitaj"></form>');
	
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
            url: 'profile/edit/',
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