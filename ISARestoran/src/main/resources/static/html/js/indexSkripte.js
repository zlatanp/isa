var x = document.cookie;
var delovi = x.split("=");
var emailUlogovanog = delovi[1];

window.onload = function() {
	
	
    var modal = new RModal(document.getElementById('modal'), {
        //content: 'Abracadabra'
        beforeOpen: function(next) {
            console.log('beforeOpen');
            next();
        }
        , afterOpen: function() {
            console.log('opened');
        }

        , beforeClose: function(next) {
            console.log('beforeClose');
            next();
        }
        , afterClose: function() {
            console.log('closed');
        }
        // , bodyClass: 'modal-open'
        // , dialogClass: 'modal-dialog modal-dialog-lg'
        // , dialogOpenClass: 'animated fadeIn'
        // , dialogCloseClass: 'animated fadeOut'

        // , focus: true
        // , focusElements: ['input.form-control', 'textarea', 'button.btn-primary']

        // , escapeClose: true
    });

    document.addEventListener('keydown', function(ev) {
        modal.keydown(ev);
    }, false);

    document.getElementById('showModal').addEventListener("click", function(ev) {
        ev.preventDefault();
        modal.open();
    }, false);

    window.modal = modal;
    
    
}


function ulogujSe(){
	var i = document.getElementsByName('email');
	var emailValue = i[0].value;
	var j = document.getElementsByName('password');
	var passValue = j[0].value;
	
	
// 	if(emailValue!="" && passValue!=""){
// 	$.ajax({ 
//         type: 'POST',
//         url: 'gost/login',
//         dataType: 'json',
//         data: {email : emailValue, password : passValue},
// 		success: function(data){
//         	console.log(data);
//         	alert("Prodavac sa korisnickim imenom "+emailValue+" uspesno obrisan!");
//         	window.location.replace("restoran.html");
//        }
// 	});
// 	}else{
// 		alert("Username or password can't be empty!");
// 	}

if(emailValue=="" || passValue==""){
	alert("Korisničko ime i šifra ne smeju biti prazni!");
	return false;
}else{
	document.cookie = "emailValue=" + emailValue;
	return true;
}
}

function registrujSe(){
	var i = document.getElementsByName('nameRegister');
	var nameValue = i[0].value;
	
	var j = document.getElementsByName('lastnameRegister');
	var lastnameValue = j[0].value;
	
	var k = document.getElementsByName('emailRegister');
	var emailValue = k[0].value;
	
	var l = document.getElementsByName('passwordRegister');
	var passwordValue = l[0].value;
	var m = document.getElementsByName('password1Register');
	var password1Value = m[0].value;

	
	if(nameValue=="" || lastnameValue=="" || emailValue=="" || passwordValue=="" || password1Value==""){
		alert("You must enter all fields!");
		return false;
    }else{
    	if(passwordValue.length <8){
    		alert("Password must contain 8-30 characters!")
    		return false;
    	}
    	if(passwordValue.length > 30){
    		alert("Password must contain 8-30 characters!")
    		return false;
    	}
    	
    	if(passwordValue != password1Value){
    		alert("Password does not match the confirm password!")
    		return false;
    	}
    	 console.log("idemdalje");
    	$.ajax({ 
            type: 'POST',
            url: 'korisnik/register',
            dataType: 'json',
            data: {nameRegister : nameValue, lastnameRegister : lastnameValue, emailRegister : emailValue, passwordRegister : passwordValue, password1Register : password1Value},
    		 success: function(data){
    			 console.log("jauspeo");
    			 			//location.reload(true);
            			}
    	});
    	return true;
    }
}

function registrujSe(){
	var i = document.getElementsByName('nameRegister');
	var nameValue = i[0].value;
	
	var j = document.getElementsByName('lastnameRegister');
	var lastnameValue = j[0].value;
	
	var k = document.getElementsByName('emailRegister');
	var emailValue = k[0].value;
	
	var l = document.getElementsByName('passwordRegister');
	var passwordValue = l[0].value;
	var m = document.getElementsByName('password1Register');
	var password1Value = m[0].value;

	
	if(nameValue=="" || lastnameValue=="" || emailValue=="" || passwordValue=="" || password1Value==""){
		alert("You must enter all fields!");
		return false;
    }else{
    	if(passwordValue.length <8){
    		alert("Password must contain 8-30 characters!")
    		return false;
    	}
    	if(passwordValue.length > 30){
    		alert("Password must contain 8-30 characters!")
    		return false;
    	}
    	
    	if(passwordValue != password1Value){
    		alert("Password does not match the confirm password!")
    		return false;
    	}
    	 console.log("idemdalje");
//    	$.ajax({ 
//            type: 'POST',
//            url: 'korisnik/registerr',
//            dataType: 'json',
//            data: {nameRegister : nameValue, lastnameRegister : lastnameValue, emailRegister : emailValue, passwordRegister : passwordValue, password1Register : password1Value},
//    		 success: function(data){
//    			 console.log("jauspeo");
//    			 			//location.reload(true);
//            			},
//            			error: function (request, status, error) {
//            		        alert(request.responseText);
//            		    }
//            
//    	});
    	return true;
    }
}

