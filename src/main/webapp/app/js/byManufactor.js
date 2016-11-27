var chosedCategory1, chosedManufactor1, chosedProduct1, MTOType;

function initByManufactor(){
	
	$('#byManufactorButton2').prop('disabled', true);
	$('#byManufactorButton3').prop('disabled', true);
	
	$('#byManufactorButton1').unbind('click');
	$('#byManufactorButton2').unbind('click');
	$('#byManufactorButton3').unbind('click');
	
	$('#byManufactorButton1').on('click', function(event) {
		  event.preventDefault(); 
		  
		  manufactorStart();
		  
	});
	
	$('#byManufactorButton2').on('click', function(event) {
		  event.preventDefault(); 
		  manufactorCallProduct(chosedManufactor1);
		  
	});
	
	$('#byManufactorButton3').on('click', function(event) {
		  event.preventDefault(); 
		  manufactorCallCategory(chosedProduct1);
		  
	});
	
	
	
	
}


function manufactorStart(){
		var modalHeight = getModalHeight();
	  
	  var lineHeight = Math.floor((modalHeight/(350/13))/0.7) ;
	  var fontSize = Math.floor((modalHeight/(350/12))/0.7) ;
	  if (fontSize > 15){
		  fontSize = 15;
	  }
	  $("#byManufactorButton2").attr('class', 'btn btn-default btn-lg btn-block');
	  $('#byManufactorButton2').prop('disabled', true);
	  $('#byManufactorButton3').prop('disabled', true);
	  $("#byManufactorButton3").attr('class', 'btn btn-default btn-lg btn-block');
	  $('#productName2').html('בחר מוצר');
	  $('#categoryName3').html('בחר קטגוריה');
	  
	  $('#iphone-search').css('line-height',lineHeight+'px');
	  $('#iphone-search').css('font-size',fontSize+'px');
	  $('#iphone-scrollcontainer').height(modalHeight);
	  $('#modal_title_browse').html('בחירת יצרן');
	  $('#backButton').hide();
	  if (!desktopBrowser){
		  $('#selectorFotter').hide();
	  	  $('#modalExit').show();
	  } else {
		  $('#selectorFotter').show();
		  $('#modalExit').hide();
		  $('#backButton').hide();
		  $('#endSearch').val('ביטול');
		  $('#endSearch').attr('class','btn btn-primary btn-lg');
		  $('#backButtonDown').hide();
		  
		  
		  
	  }
	  $('#searchHeader').show();
	  $('#resultHeader').hide();
	  
	  $("#nav-indicator-fixed").css('width','0px');
	  $('#iphone-search').show();
	  $('#iphone-scroll').css('margin-right','50px');
	  
	  //load the modal
	  showModalWait();
	  showModal();
	  $('#iphone-search').show();
	  //loadGFData('web_manufactures','יצרנים','manufactorCallProduct',false);
	  loadGFDataPost('web_manufactures_post','יצרנים','manufactorCallProduct',false,'null','null','null');
}

function manufactorCallProduct(value){
	
	chosedManufactor1 = value;
	
	$('#manufactorName1').html(value);
	$("#byManufactorButton2").attr('class', 'btn btn-primary btn-lg btn-block');
	$('#byManufactorButton2').prop('disabled', false);
	$('#byManufactorButton3').prop('disabled', true);
	$("#byManufactorButton3").attr('class', 'btn btn-default btn-lg btn-block');
	$('#productName2').html('בחר מוצר');
	$('#categoryName3').html('בחר קטגוריה');
	if (!desktopBrowser){
		  $('#selectorFotter').hide();
		  $('#selectorFotter').hide();
	  	  $('#modalExit').show();
	  	  $('#backButton').show();
	  	  //init the back button:
		  $('#backButton').unbind('click');
		  $('#backButton').on('click', function(event) {
			  event.preventDefault(); 
			  manufactorStart();
		  });
	  } else {
		  $('#backButton').hide();
		  $('#selectorFotter').show();
		  $('#modalExit').hide();
		  $('#endSearch').val('ביטול');
		  $('#endSearch').attr('class','btn btn-primary btn-lg');
		  $('#backButtonDown').show();
		  $('#backButtonDown').unbind('click');
		  $('#backButtonDown').on('click', function(event) {
			  event.preventDefault(); 
			  manufactorStart();
			  
		  });
	  }
	$('#searchHeader').show();
	$('#resultHeader').hide();
	
	  var modalHeight = getModalHeight();
	  $('#iphone-scrollcontainer').height(modalHeight);
	  $('#iphone-scroll').css('height',modalHeight + 'px');
	  
	  $("#nav-indicator-fixed").css('width','0px');
	  //Now open new manufactor select
	
	  $('#modal_title_browse').html('בחירת מוצר');
	  
	  
	  
	  $('#iphone-search').show();
	  $('#iphone-scroll').css('margin-right','50px');
	  
	  showModalWait();
	  showModal();
	  var apiName = 'web_products/' + value ;//+ '}';
	  
	  //$('#iphone-search').hide();
	  
	  //loadGFData(apiName,'יצרנים','manufactorCallCategory',false);
	  loadGFDataPost('web_products_post','יצרנים','manufactorCallCategory',false,'null',value,'null');
	  
	  
	
}

function manufactorCallCategory(value){
	
	  chosedProduct1 = value;
	  var forShow;
		
	  if (value == 'OP') {
		 forShow = 'מוצרים אחרים';
	  } else if (value == 'OPMTO') {
		  forShow = 'מוצרים אחרים';
		  chosedProduct1 = 'MULTIPLE';
		  MTOType = 'OPMTO';
		  $("#byManufactorButton3").attr('class', 'btn btn-primary btn-lg btn-block');
		  $('#byManufactorButton3').prop('disabled', false);
	  } else if (value == 'AP') {
		  forShow = 'כל המוצרים';
	  } else if (value == 'APMTO') {
		  forShow = 'כל המוצרים';
		  MTOType = 'APMTO';
		  $("#byManufactorButton3").attr('class', 'btn btn-primary btn-lg btn-block');
		  $('#byManufactorButton3').prop('disabled', false);
	  } else {
		  forShow = value;
		  $("#byManufactorButton3").attr('class', 'btn btn-primary btn-lg btn-block');
		  $('#byManufactorButton3').prop('disabled', false);
	  }
	
	
	$('#productName2').html(forShow);
	
	if (value == 'OP' || value == 'AP'){
		lastBeforeCheckManufactors(value,false);
	} else {
		checkIfLastSingleObject();
	}
	
}


function lastBeforeCheckManufactors(value,singleItem){
	
	
	
	var forShow;
	var backFlow = 'manufactorFlow';
	if (value == 'OP') {
		 forShow = 'מוצרים אחרים';
		 chosedCategory1 = 'null';
		 backFlow = 'manufactorSelectProduct';
	} else if (value == 'AP') {
		 forShow = 'כל המוצרים';
		 chosedCategory1 = 'null';
		 backFlow = 'manufactorSelectProduct';
	} else if (singleItem){
		chosedCategory1 = value;
		forShow = value;
		backFlow = 'manufactorSelectProduct';
	} else {
		chosedCategory1 = value;
		forShow = value;
	}
	
	$('#categoryName3').html(forShow);
	var productForCheck = chosedProduct1;
	if (chosedProduct1 == 'MULTIPLE'){
		productForCheck = MTOType;
	}
	
	checkRecommendation(productForCheck,chosedManufactor1,chosedCategory1,backFlow);
}

function checkIfLastSingleObject(){
	
		
	var queryObj = {};
	queryObj['manufactor'] = chosedManufactor1;
	queryObj['product'] = chosedProduct1;
	queryObj['category'] = 'null';
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	
	
	$.ajax({
	    type: "POST",
	    url: apiProdUrl + '/rest/web_categories_post',
	    data: JSON.stringify(queryDao),
        crossDomain: true,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
        beforeSend: function(xhr) {
			 xhr.setRequestHeader ("Authorization", "Basic " + loadCredentialFromDB());
        },
	    success: function(data){
	    	if (data.listDao.items){
	    		if (data.listDao.items.value){
	    			
	    			lastBeforeCheckManufactors(data.listDao.items.value,true);
	    		} else {
	    			$("#byManufactorButton2").attr('class', 'btn btn-primary btn-lg btn-block');
	    			$('#byManufactorButton2').prop('disabled', false);
	    			if (!desktopBrowser){
	    				$('#selectorFotter').hide();
	    				$('#selectorFotter').hide();
	    		  	  	$('#modalExit').show();
	    		  	  	$('#backButton').show();
	    		  	  	//init the back button:
	    		  	  	$('#backButton').unbind('click');
	    		  	  	$('#backButton').on('click', function(event) {
	    				  event.preventDefault(); 
	    				  manufactorCallProduct(chosedManufactor1);
	    		  	  	});
	    			} else {
	    				$('#backButton').hide();
	    				$('#selectorFotter').show();
	    				$('#modalExit').hide();
	    				$('#endSearch').val('ביטול');
	    				$('#endSearch').attr('class','btn btn-primary btn-lg');
	    				$('#backButtonDown').show();
	    				$('#backButtonDown').unbind('click');
	    				$('#backButtonDown').on('click', function(event) {
	    				  event.preventDefault(); 
	    				  manufactorCallProduct(chosedManufactor1);
	    				  
	    				});
	    				}
	    				$('#searchHeader').show();
	    				$('#resultHeader').hide();
	    		
	    				var modalHeight = getModalHeight();
	    				$('#iphone-scrollcontainer').height(modalHeight);
	    				$('#iphone-scroll').css('height',modalHeight + 'px');
	    		  
	    				$("#nav-indicator-fixed").css('width','0px');
	    				//Now open new manufactor select
	    		  
	    				$('#modal_title_browse').html('בחירת קטגוריה');
	    		  
	    		 
	    		  
	    				$('#iphone-search').show();
	    				$('#iphone-scroll').css('margin-right','50px');
	    		  	
	    				showModalWait();
	    				showModal();
	    				var apiName = 'web_categories/' + chosedProduct1 + '/' + chosedManufactor1 ;
	    		  
	    		  
	    				loadGFDataPost('web_categories_post','קטגוריות','lastBeforeCheckManufactors',false,chosedProduct1,chosedManufactor1,'null');
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