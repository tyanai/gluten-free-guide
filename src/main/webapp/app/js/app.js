var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
    // Opera 8.0+ (UA detection to detect Blink/v8-powered Opera)
var isFirefox = typeof InstallTrigger !== 'undefined';   // Firefox 1.0+
var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
var isIE = isieversion();
var firstTimeLoginNew = true;
//var apiProdUrl = "http://gfguide.info:6880/easy"
//var apiProdUrl = "http://localhost:8080/gfguide";
var apiProdUrl = "..";

var desktopBrowser = true;
var mobileHeightInit = false;
var mobileHeight = 0;

function isieversion() {
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0)  {     // If Internet Explorer, return version number
        return true;
    } else if(/MSIE 9/i.test(ua) || /rv:11.0/i.test(ua)){
    	return true;
    }                
    return false;
}

$(document).ready(function(){  
	
	$(function(){
		$('#navbar-collapse-2').on('show.bs.collapse', function(e) {
			$('#navbar-collapse-1').collapse('hide');
		});
		
		$('#navbar-collapse-1').on('show.bs.collapse', function(e) {
			$('#navbar-collapse-2').collapse('hide');
		});
	});
	
	saveVersionDB('new');
	initLoginForm();
	checkLogin();
	
	if(/Android|Windows Phone|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		
		desktopBrowser = false;
		getModalHeight();
		
	}
	
	
	
	//pushLetters();
    //Init the select with dropdown
    //$('.combobox').combobox();
     
	initFreeStyleText();
	initSearchType();
	jQuery.extend(jQuery.validator.messages, {
    required: "שדה חובה.",
    remote: "Please fix this field.",
    email: "Please enter a valid email address.",
    url: "Please enter a valid URL.",
    date: "Please enter a valid date.",
    dateISO: "Please enter a valid date (ISO).",
    number: "Please enter a valid number.",
    digits: "Please enter only digits.",
    creditcard: "Please enter a valid credit card number.",
    equalTo: "Please enter the same value again.",
    accept: "Please enter a value with a valid extension.",
    maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
    minlength: jQuery.validator.format("Please enter at least {0} characters."),
    rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
    range: jQuery.validator.format("Please enter a value between {0} and {1}."),
    max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
    min: jQuery.validator.format("Please enter a value greater than or equal to {0}.")
	});
	
	
	
});  

function showModalTypeSelection(){
	$('#selectTypeModal').modal('show');
	
	$('#startTypeManufactor').on('click', function(event) {
		  event.preventDefault(); 
		  $('#selectTypeModal').modal('hide');
		  switchFlow('bymanufactor',true);
		  
	});
	$('#startTypeCategory').on('click', function(event) {
		  event.preventDefault(); 
		  $('#selectTypeModal').modal('hide');
		  switchFlow('bycategory',true);
		  
	});
	$('#startTypeFree').on('click', function(event) {
		  event.preventDefault(); 
		  $('#selectTypeModal').modal('hide');
		  switchFlow('freetext',true);
		  
	});
}

function initLoginForm(){

	
	
	$('#loginForm').validate({
            focusInvalid: false,
            submitHandler: function(form) {
				var user,pass;
				user = $('#username').val().trim();
				pass = $('#password').val().trim();
				saveCredentialInDB(user,pass);
				checkLogin();
				
            },
			
            rules: {
                username: {
                    minlength: 1,
					
                    required: true
                },
				password: {
                    minlength: 1,
					
                    required: true
                }
				
            },
            highlight: function(element) {
                $(element).closest('.input-group').addClass('has-error');

            },
            unhighlight: function(element) {
                $(element).closest('.input-group').removeClass('has-error');
            },
            errorElement: 'span',
            errorClass: 'help-block',
            errorPlacement: function(error, element) {
                if(element.parent('.input-group').length) {
                    error.insertAfter(element.parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

}



function checkLogin(){

	$.ajax({
	    type: "GET",
	    url: apiProdUrl + '/rest/web_login_validate',
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
        	if (!firstTimeLoginNew){
        		$('#login_main_body').html('<div id="ajax-loader"></div>');
        		$( "#ajax-loader" ).ajaxLoader( {
        			lineWidth: 5,
        			top: {
        				color: "#4b98eb"
        			}, 
        			bottom: {
        				color: "#377fcc"
        			}
        		} );
        		$("#ajax-loader" ).css('padding-right','50%');
        		$("#ajax-loader" ).css('margin-right','-25px');
        		$("#ajax-loader" ).css('padding-top','15px');
        	}
			xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	$('#loginModal').modal('hide');
	    	$('#gfguide_body').show();
	    	if (!desktopBrowser){
	    		showModalTypeSelection();
	    	}
	    },
	    error: function(jqXHR,err,msg) {
	    	//alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    	$('#login_main_body').html(login_main);
	    	if (!firstTimeLoginNew){
		    	$('#login_error').html("שם משתמש או סיסמא שגויים");
	    	} else {
	    		firstTimeLoginNew = false;
	    		$('#loginModal').modal({
	    			  backdrop: 'static',
	    			  keyboard: false
	    		});
	    	}
	    	
	    	
	    	initLoginForm();
	    },
	    complete: function(){

	    }
	});
}


function switchFlow(flow,launch){
	if (flow == 'freetext'){
		$('#freetext').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש חופשי');
		$('#bycategory').html('חיפוש לפי קטגוריה');
		$('#bymanufactor').html('חיפוש לפי יצרן ');
		$('#gfguide_button_label').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש חופשי');
		
		$('#gfguide_flow_free').show();
		$('#gfguide_flow_manufactor').hide();
		$('#gfguide_flow_category').hide();
		$('#freeProductValue').val('');
		$('#freeManufactorValue').val('');
		
		
		
		
	} else if (flow == 'bycategory'){
		$('#freetext').html('חיפוש חופשי');
		$('#bycategory').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש לפי קטגוריה');
		$('#bymanufactor').html('חיפוש לפי יצרן ');
		$('#gfguide_button_label').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש לפי קטגוריה');
		
		$('#gfguide_flow_manufactor').hide();
		$('#gfguide_flow_category').show();
		$('#gfguide_flow_free').hide();
		
		initByCategory();
		if (launch){
			categoryStart();
		}
		
	} else{
		$('#freetext').html('חיפוש חופשי');
		$('#bycategory').html('חיפוש לפי קטגוריה');
		$('#bymanufactor').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש לפי יצרן ');
		$('#gfguide_button_label').html('<span class="glyphicon glyphicon-ok" style="color:#424882"></span>&nbsp;&nbsp;חיפוש לפי יצרן ');
		
		$('#gfguide_flow_manufactor').show();
		$('#gfguide_flow_category').hide();
		$('#gfguide_flow_free').hide();
		
		
		initByManufactor();
		if (launch){
			manufactorStart();
		}
	}
	saveSearchTypeDB(flow);
	$('#navbar-collapse-1').collapse('hide');
}



saveCredentialInDB = function (username,password) {
    if (typeof window.localStorage !== 'undefined') {
    	
        window.localStorage.setItem('gfguide_credential',  btoa(username + ":" + password));
        
    } 
}


loadCredentialFromDB = function () {
    if (typeof window.localStorage !== 'undefined') {
		var loadObject = window.localStorage.getItem('gfguide_credential');
		if (!loadObject){
			return 'YmdiZ2JnYmdiZ2JnOmJnYmdiZ2JnYmdiZw==';
		} else {
			
			return loadObject;
		}
    }
}

removeCredentialFromDB = function () {
    if (typeof window.localStorage !== 'undefined') {
        window.localStorage.removeItem('gfguide_credential');
    }
    $('#login_main_body').html(login_main);
    $('#loginModal').modal({
		  backdrop: 'static',
		  keyboard: false
	});
    initLoginForm();
	$('#gfguide_body').hide();
    
}

saveSearchTypeDB = function (value) {
    if (typeof window.localStorage !== 'undefined') {
    	
        window.localStorage.setItem('gfguide_search_type',  value);
    } 
}


loadSearchTypeDB = function () {
    if (typeof window.localStorage !== 'undefined') {
		var loadObject = window.localStorage.getItem('gfguide_search_type');
		if (!loadObject){
			return 'empty';
		} else {	
			return loadObject;
		}
    }
}



saveVersionDB = function (value) {
    if (typeof window.localStorage !== 'undefined') {
        window.localStorage.setItem('gfguide_version',  value);
    } 
}




function initSearchType(){
	var searchType = loadSearchTypeDB();
	if (searchType != 'empty'){
		switchFlow(searchType,false);
	}
}

function escapeRegExp(str) {
    return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
}

function replaceAll(str, find, replace) {
	  return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

function showModal(){
	
	if (desktopBrowser){
		$('#selectorFotter').show();
	}
	
	
	
	
	$('#selectModal').modal('show');
	
	if (isSafari){
			 $("#iphone-search").css('text-align','left');
			 $("#iphone-search").css('position','inherit');
			 
	} else if (isIE){
		$("#iphone-search").css('right','auto');
		$("#iphone-search").css('margin-right','15px');
	}
	
}

function showModalWait(isRecomendation){
	//Load the modal
	  
	  var modalHeight = getModalHeight();
	
	  $('#iphone-scroll').html('<div id="ajax-loader"></div>');
	  $( "#ajax-loader" ).ajaxLoader( {
			lineWidth: 5,
			top: {
				color: "#4b98eb"
			},
			bottom: {
				color: "#377fcc"
			}
		} );
	  
	   if (isRecomendation){
		  
		   $('#iphone-scroll').css('border-right','0px');
	   } else {
		   $('#iphone-scroll').css('border-right','1px solid #000000');
	   }
	   var halfHeight = Math.floor(modalHeight/2) - 60 ;
	   
	   
	   if (isRecomendation){
		   halfHeight = Math.floor($('#iphone-scroll').height()/2) - 60 ;
	   } else {
		   $('#iphone-scroll').css('height',modalHeight + 'px');
	   }
	   $("#ajax-loader" ).css('padding-right','50%');
	   $("#ajax-loader" ).css('margin-right','-25px');
	   $("#ajax-loader" ).css('padding-top',halfHeight + 'px');
}

function getModalHeight(){
	
	
	
	
	var modalHeight;
	if (desktopBrowser){
		 modalHeight = $(window).height() - 170;
	} else {
		 modalHeight = $(window).height() - 100;
	}
	
	
	if (modalHeight > 700){
		  modalHeight = 700;
	 }
	
	
	if (!desktopBrowser){
		if (!mobileHeightInit){
			mobileHeightInit = true;
			mobileHeight = modalHeight;
		} else {
			return mobileHeight;
		}
			
	}
	
	
	
	 return modalHeight;
}




function checkRecommendation(product,manufactor,category,origin){
	
	  var modalHeight = getModalHeight();
	  if (desktopBrowser){
			 
	  } else {
			 modalHeight = modalHeight - 70;
			 $('#iphone-scrollcontainer').height(modalHeight);
	  }
	  //$('#iphone-scrollcontainer').height(modalHeight);
	  //$('#iphone-scroll').css('height',modalHeight + 'px');
	  
	  $("#nav-indicator-fixed").css('width','0px');
	  //Now open new manufactor select
	  $('#backButton').hide();
	  $('#modal_title_browse').html('המלצה');
	  $('#selectorFotter').show();
	  $('#modalExit').hide();
	  
	  $('#endSearch').val('סיום');
	  
	  //init the back button:
	  $('#backButton').unbind('click');
	  //$('#backButton').hide();
	  $('#backButtonDown').unbind('click');
	  $('#backButtonDown').on('click', function(event) {
		  event.preventDefault(); 
		  if (origin == 'categoryFlow'){
			  categoryCallProduct(manufactor);
		  } else if (origin == 'manufactorFlow'){
			  manufactorCallCategory(product);
		  } else if (origin == 'manufactorSelectProduct'){
			  manufactorCallProduct(manufactor);
		  } else if (origin == 'categoryCallManufactor'){
			  categoryCallManufactor(category);
		  }
		  
	  });
	  
	  
	  if (origin != 'free'){
		  $('#backButtonDown').show();
	  } 
	  
	  
	  
	  $('#iphone-search').hide();
	  $('#iphone-scroll').css('margin-right','0px');
	  //$('#iphone-scroll').css('height','');
	  
	  showModalWait(true);
	  showModal();
	  
	  var apiName = 'web_check/' + product + "/" + manufactor + "/" + category;
	  
	  $('#iphone-search').hide();
	  
	  //loadGFData(apiName,'המלצה','',true);
	  loadGFDataPost('web_check_post','המלצה','',true,product,manufactor,category);
	  
}

function loadGFData(api,name,methodName,checkRecomendation){

	
	
	$.ajax({
	    type: "GET",
	    url: apiProdUrl + '/rest/' + api,
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	var modalHeight = getModalHeight();
	    		    	
	    	 if (checkRecomendation){
	    		 preperReccomendation(data.resultDao);
	    		 if (desktopBrowser){
	    			 modalHeight = modalHeight - 50;
	    		 } else {
	    			 modalHeight = modalHeight - 120;
	    		 }
	    		 $('#iphone-scrollcontainer').height(modalHeight);
	    		 $('#iphone-scroll').css('height',modalHeight + 'px');
	    	 } else {
	    		 $('#iphone-scroll').html(buildIndex(data.listDao,methodName));
	    	 }
	    	 
	    	 
	    	 setTimeout(function () {
	    		 $('#iphone-scroll').css('height','');
		    	 //alert($('#iphone-scroll').height() + " "+ modalHeight);
		    	 if ($('#iphone-scroll').height() < modalHeight){
		    		 $('#iphone-scroll').css('height',modalHeight + 'px');
		    	 } 
	    	 }, 500);
	    	 
	    	        	
			 //initNavIndicators();
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){

	    }
	});
}

function loadGFDataPost(api,name,methodName,checkRecomendation,product,manufactor,category){

	var queryObj = {};
	queryObj['manufactor'] = manufactor;
	queryObj['product'] = product;
	queryObj['category'] = category;
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	
	
	$.ajax({
	    type: "POST",
	    url: apiProdUrl + '/rest/' + api,
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	var modalHeight = getModalHeight();
	    		    	
	    	 if (checkRecomendation){
	    		 preperReccomendation(data.resultDao,product,manufactor,category);
	    		 if (desktopBrowser){
	    			 modalHeight = modalHeight - 50;
	    		 } else {
	    			 modalHeight = modalHeight - 120;
	    		 }
	    		 $('#iphone-scrollcontainer').height(modalHeight);
	    		 $('#iphone-scroll').css('height',modalHeight + 'px');
	    		 
	    		 
	    	 } else {
	    		 $('#iphone-scroll').html(buildIndex(data.listDao,methodName));
	    	 }
	    	 
	    	 
	    	 setTimeout(function () {
	    		 $('#iphone-scroll').css('height','');
		    	 //alert($('#iphone-scroll').height() + " "+ modalHeight);
		    	 if ($('#iphone-scroll').height() < modalHeight){
		    		 $('#iphone-scroll').css('height',modalHeight + 'px');
		    	 } 
	    	 }, 500);
	    	 
	    	        	
			 //initNavIndicators();
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){

	    }
	});
}


function getSmallImage(data,product,manufactor,category){
	var queryObj = {};
	queryObj['manufactor'] = manufactor;
	queryObj['product'] = product;
	queryObj['category'] = category;
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	var image = '';
	
	$.ajax({
	    type: "POST",
	    url: apiProdUrl + '/rest/web_smallimage_post',
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	
	    	
	    	if (data.stringDao){
	    		if (data.stringDao.value != 'EMPTY'){
	    			image = '<span class="smallImage"><a href="javascript:openLargeImage(\''+product+'\',\'' + manufactor + '\',\'' +category +'\');"><img id="recSmallImage"  src="data:image/gif;base64,$$TT$$" width="80px"/></a></span>';
	    			image = replaceAll(image,'$$TT$$',data.stringDao.value);
	    			
	    		}
	    	}
	    	
	    	
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){
	    	
	    	preperReccomendationPartTwo(data,product,manufactor,category,image);
	    	
	    }
	});
}


function openLargeImage(product,manufactor,category){
	
	var queryObj = {};
	queryObj['manufactor'] = manufactor;
	queryObj['product'] = product;
	queryObj['category'] = category;
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	var image = '';
	
	var modalWidth = $('#iphone-scroll').width();
	
	
	
	
	$.ajax({
	    type: "POST",
	    url: apiProdUrl + '/rest/web_largeimage_post',
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
        	  $('#bigImageModal').modal('show');
        	  var imageHeightByModal = getModalHeight();
        	  if (desktopBrowser){
        			imageHeightByModal = imageHeightByModal + 32;
        	  } else {
        			imageHeightByModal = imageHeightByModal - 32;
        	  }
        	  $('#largeImageModal').height(imageHeightByModal);  
        	  $('#largeImageModal').html('<div id="ajax-loader"></div>');
        	  $( "#ajax-loader" ).ajaxLoader( {
        			lineWidth: 5,
        			top: {
        				color: "#4b98eb"
        			},
        			bottom: {
        				color: "#377fcc"
        			}
        	   });
        	  $("#ajax-loader" ).css('padding-right','50%');
      		  $("#ajax-loader" ).css('margin-right','-25px');
      		  $("#ajax-loader" ).css('padding-top','40%');
      		  $("#ajax-loader" ).css('margin-top','-25px');
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	
	    	
	    	
	    	
	    	if (data.stringDao){
	    		if (data.stringDao.value != 'EMPTY'){
	    			//$('#bigImageModal').modal('show');
	    			
	    				
		    			var imageSrc = 'data:image/gif;base64,' + data.stringDao.value;
		    			
		    			
		    			//Check if the image width is larger than the modal width
		    			
		    			
		    			var myImage = new Image();
		    			myImage.name = "image";
		    			myImage.src = imageSrc;
		    			setTimeout(function () {
		    				$('#largeImageModal').html('<img id="largeImage" style="display: table;margin: 0 auto;"/>');
		    				setImageHeight(myImage,modalWidth);
	   	    	 		}, 1000);
	    			
	    			
	    			
	    		}
	    	}
	    	
	    	
	    	
	    },
	    error: function(jqXHR) {
	    	alert('Response: ' + jqXHR.status + ' ' +jqXHR.statusText + '.<br>Reason: ' + jqXHR.responseText);
	    },
	    complete: function(){
	    	
	    	
	    	
	    }
	});
}

function setImageHeight(image,modalWidth) {
    //alert("'Image' is " + image.width + " by " + image.height + " pixels in size.  Modal Width is " +modalWidth);
    
    
    var imageHeightByModal = getModalHeight();
	if (desktopBrowser){
		imageHeightByModal = imageHeightByModal + 32;
	} else {
		imageHeightByModal = imageHeightByModal - 32;
	}
	
	var ignoreModalHeight = false;
	
	
	if (image.height < imageHeightByModal){
		ignoreModalHeight = true;
	}
	
	var newHeight;
    var imageHeight;
    $('#largeImageModal').height(imageHeightByModal);  
    if (image.width > image.height) {
    
    	newHeight = (image.height/image.width) * imageHeightByModal * 1.8 ;
    	//alert(image.height/image.width);
    	var tempWidth =(image.width/image.height)*newHeight;
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	if (tempWidth > (modalWidth - 20)){
    		newHeight = newHeight * 0.9;
    		tempWidth =(image.width/image.height)*newHeight;
    	}
    	
    	
    	if (ignoreModalHeight && image.height < newHeight){    		
    		//check that the resolution is not too big:
    		newHeight = image.height;
        }
    } else {
    	imageHeight =  image.height;
    	if (!ignoreModalHeight){
    		
       	 	newHeight = imageHeightByModal ;
    	} else {
    		
    		newHeight = imageHeight ;
        }
    }
    
    
    
    
    $('#largeImage').attr('height',newHeight+'px');
	$('#largeImage').attr('src',image.src);
    
   
}

function preperReccomendation(data,product,manufactor,category){
	
	
	getSmallImage(data,product,manufactor,category);
	
	
}

function preperReccomendationPartTwo(data,product,manufactor,category,image){
	var tmpValue;
	
	var recWidth = '';
	if (image != ''){
		recWidth = 'style="width:50%;"';
	}
	if (/[a-zA-Z0-9-_ ]/.test(data.value.charAt(0))){
		$('#iphone-scroll').html(image + '<p dir="ltr" class="recommendation" ' + recWidth + '>' + data.value + '</p>');
	} else {
		$('#iphone-scroll').html(image + '<p dir="rtl" class="recommendation" ' + recWidth + '>' + data.value + '</p>');
	}
	
	
	
	if (data.result == 'GFGRE'){
		$('#resultHeader').css('background-color','#9ced9c');
		$('#recomendationHeaderText').html('ללא גלוטן');
		$("#recomendationHeaderImg").attr('src', 'img/ok.png'); 
		$("#endSearch").attr('class', 'btn btn-success  btn-lg'); 
	} else if (data.result == 'CFRED'){
		$('#resultHeader').css('background-color','#f7a093');
		$('#recomendationHeaderText').html('מכיל גלוטן');
		$("#recomendationHeaderImg").attr('src', 'img/not_ok.png'); 
		$("#endSearch").attr('class', 'btn btn-danger  btn-lg'); 
	} else if (data.result == 'CFRED'){
		$('#resultHeader').css('background-color','#7FAAFF');
		$('#recomendationHeaderText').html('ללא גלוטן - פסח');
		$("#recomendationHeaderImg").attr('src', 'img/ok.png'); 
		$("#endSearch").attr('class', 'btn btn-success  btn-lg'); 
	} else {
		$('#resultHeader').css('background-color','#31b0d5');
		$('#recomendationHeaderText').html('שים לב');
		$("#recomendationHeaderImg").attr('src', 'img/info.png'); 
		$("#endSearch").attr('class', 'btn btn-info  btn-lg'); 
	}
	
	
	
	
			
	$('#searchHeader').hide();
	$('#resultHeader').show();
}

function buildIndex(data,methodName){
	
	var output = indexFormPart1;
	
	if (data){
		if (data.items){
			var lastItemIndex = 'N/A'
			var tmpItem;
			var firstItem = true;
			var itemIsHebrew = true;
			if (data.items.value){
				var firstChar = data.items.value.toString().charAt(0);
				output = output + itemInIndexForm1;
				output = replaceAll(output,'#X#X',firstChar);
				if (/[a-zA-Z0-9-_ ]/.test(firstChar)){
					  itemIsHebrew = false;
					  output = replaceAll(output,'U#U#',firstChar);
				} else {
					  itemIsHebrew = true;  
					  output = replaceAll(output,'U#U#',getMapByHebrewLetter(firstChar));
				}
				tmpItem = singleIndexItem;
				  
				  data.items.value = replaceAll(data.items.value.toString(),'"',"&#34;");
				  
				  var foundSpecialOP = false;
				  if (data.items.value == 'OP') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','מוצרים אחרים');
					  foundSpecialOP = true;
				  } else if (data.items.value == 'OPMTO') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','מוצרים אחרים');
					  foundSpecialOP = true;
				  } else if (data.items.value == 'AP') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','כל המוצרים');
					  foundSpecialOP = true;
				  } else if (data.items.value == 'APMTO') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','כל המוצרים');
					  foundSpecialOP = true;
				  }
				  tmpItem = replaceAll(tmpItem,'V#V#V',data.items.value);
				  tmpItem = replaceAll(tmpItem,'V#V#I',data.items.value);
				  
				  tmpItem = replaceAll(tmpItem,'#$RR$#',methodName);
				  if (itemIsHebrew || foundSpecialOP){
					  tmpItem = replaceAll(tmpItem,'S#S#S','');
					  tmpItem = replaceAll(tmpItem,'%H&&H%','');
				  } else {
					  tmpItem = replaceAll(tmpItem,'S#S#S','text-align: left;');
					  tmpItem = replaceAll(tmpItem,'%H&&H%','dir="ltr"');
				  }
				  output = output + tmpItem;
				
			} else {
				pushLetters();
				var allTheLetters = letterOrder;
				for (var i=0; i<data.items.length; i++) {
				  var firstChar = data.items[i].value.toString().charAt(0);
				  if (firstChar != lastItemIndex){
					  //Check of not first item, close the last one:
					  if (!firstItem){
						  output = output + itemInIndexForm2;
					  } else {
						  firstItem = false;
					  }
					  lastItemIndex = firstChar;
					  
					  var tmpLetter;
					  
					  for (var j=0; j<allTheLetters.length; j++){
						  tmpLetter = allTheLetters.shift();
						  if (lastItemIndex != tmpLetter){
							  if (/[a-zA-Z0-9-_ ]/.test(tmpLetter)){
								  output = output + replaceAll(itemIndexLetter,'#X#N',tmpLetter);
							  } else {
								  output = output + replaceAll(itemIndexLetter,'#X#N',getMapByHebrewLetter(tmpLetter));
							  }
						  } else {
							  break;
						  }
					  }
					  
					 
					  output = output + itemInIndexForm1;
					  
					  
					  output = replaceAll(output,'#X#X',lastItemIndex);
					  
					  if (/[a-zA-Z0-9-_ ]/.test(lastItemIndex)){
						  itemIsHebrew = false;
						  output = replaceAll(output,'U#U#',lastItemIndex);
						  output = replaceAll(output,'#X#N',lastItemIndex);
					  } else {
						  itemIsHebrew = true;  
						  output = replaceAll(output,'U#U#',getMapByHebrewLetter(lastItemIndex));
						  output = replaceAll(output,'#X#N',getMapByHebrewLetter(lastItemIndex));
					  }
					  
				  }
				  tmpItem = singleIndexItem;
				  
				  data.items[i].value = replaceAll(data.items[i].value.toString(),'"',"&#34;");
				 
				  var foundSpecialOP = false;
				  if (data.items[i].value == 'OP') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','מוצרים אחרים');
					  foundSpecialOP = true;
				  } else if (data.items[i].value == 'OPMTO') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','מוצרים אחרים');
					  foundSpecialOP = true;
				  } else if (data.items[i].value == 'AP') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','כל המוצרים');
					  foundSpecialOP = true;
				  } else if (data.items[i].value == 'APMTO') {
					  tmpItem = replaceAll(tmpItem,'V#V#V','כל המוצרים');
					  foundSpecialOP = true;
				  }
				  tmpItem = replaceAll(tmpItem,'V#V#V',data.items[i].value);
				  tmpItem = replaceAll(tmpItem,'V#V#I',data.items[i].value);
				  
				  tmpItem = replaceAll(tmpItem,'#$RR$#',methodName);
				  if (itemIsHebrew || foundSpecialOP){
					  tmpItem = replaceAll(tmpItem,'S#S#S','');
					  tmpItem = replaceAll(tmpItem,'%H&&H%','');
					  
				  } else {
					  tmpItem = replaceAll(tmpItem,'S#S#S','text-align: left;');
					  tmpItem = replaceAll(tmpItem,'%H&&H%','dir="ltr"');
					  
				  }
				  output = output + tmpItem;
			      
				}
			}
			
			output = output + itemInIndexForm2;
			
		}
	}
	
	output = output + indexFormPart2;
	
		
	return output;
	
}

function getMapByHebrewLetter(letter){
	
	if (letter == 'א') return 'alef';
	else if (letter == 'ב') return 'beit';
	else if (letter == 'ג') return 'gimel';
	else if (letter == 'ד') return 'daled'; 
	else if (letter == 'ה') return 'he'; 
	else if (letter == 'ו') return 'vav'; 
	else if (letter == 'ז') return 'zain'; 
	else if (letter == 'ח') return 'het'; 
	else if (letter == 'ט') return 'tet'; 
	else if (letter == 'י') return 'yod'; 
	else if (letter == 'כ') return 'caf'; 
	else if (letter == 'ל') return 'lamed'; 
	else if (letter == 'מ') return 'mem'; 
	else if (letter == 'נ') return 'nun'; 
	else if (letter == 'ס') return 'samech';
	else if (letter == 'ע') return 'ain'; 
	else if (letter == 'פ') return 'pe'; 
	else if (letter == 'צ') return 'zadik'; 
	else if (letter == 'ק') return 'kof'; 
	else if (letter == 'ר') return 'resh';
	else if (letter == 'ש') return 'shin'; 
	else if (letter == 'ת') return 'taf'; 
}

var letterOrder = [];

function pushLetters(){
	letterOrder = [];
	letterOrder.push('0');
	letterOrder.push('1');
	letterOrder.push('2');
	letterOrder.push('3');
	letterOrder.push('4');
	letterOrder.push('5');
	letterOrder.push('6');
	letterOrder.push('7');
	letterOrder.push('8');
	letterOrder.push('9');
	letterOrder.push('A');
	letterOrder.push('B');
	letterOrder.push('C');
	letterOrder.push('D');
	letterOrder.push('E');
	letterOrder.push('F');
	letterOrder.push('G');
	letterOrder.push('H');
	letterOrder.push('I');
	letterOrder.push('J');
	letterOrder.push('K');
	letterOrder.push('L');
	letterOrder.push('M');
	letterOrder.push('N');
	letterOrder.push('O');
	letterOrder.push('P');
	letterOrder.push('Q');
	letterOrder.push('R');
	letterOrder.push('S');
	letterOrder.push('T');
	letterOrder.push('U');
	letterOrder.push('V');
	letterOrder.push('W');
	letterOrder.push('X');
	letterOrder.push('Y');
	letterOrder.push('Z');
	letterOrder.push('א');
	letterOrder.push('ב');
	letterOrder.push('ג');
	letterOrder.push('ד');
	letterOrder.push('ה');
	letterOrder.push('ו');
	letterOrder.push('ז');
	letterOrder.push('ח');
	letterOrder.push('ט');
	letterOrder.push('י');
	letterOrder.push('כ');
	letterOrder.push('ל');
	letterOrder.push('מ');
	letterOrder.push('נ');
	letterOrder.push('ס');
	letterOrder.push('ע');
	letterOrder.push('פ');
	letterOrder.push('צ');
	letterOrder.push('ק');
	letterOrder.push('ר');
	letterOrder.push('ש');
	letterOrder.push('ת');
}


