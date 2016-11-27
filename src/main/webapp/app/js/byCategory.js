var chosedCategory, chosedManufactor, chosedProduct;

function initByCategory(){
	
	$('#byCategoryButton2').prop('disabled', true);
	$('#byCategoryButton3').prop('disabled', true);
	
	$('#byCategoryButton1').unbind('click');
	$('#byCategoryButton2').unbind('click');
	$('#byCategoryButton3').unbind('click');
	
	$('#byCategoryButton1').on('click', function(event) {
		  event.preventDefault(); 
		  
		  categoryStart();
		  
	});
	
	$('#byCategoryButton2').on('click', function(event) {
		  event.preventDefault(); 
		  categoryCallManufactor(chosedCategory);
		  
	});
	
	$('#byCategoryButton3').on('click', function(event) {
		  event.preventDefault(); 
		  categoryCallProduct(chosedManufactor);
		  
	});
	
	
	
	
}



function categoryStart(){
		var modalHeight = getModalHeight();
	  
	  var lineHeight = Math.floor((modalHeight/(350/13))/0.7) ;
	  var fontSize = Math.floor((modalHeight/(350/12))/0.7) ;
	  if (fontSize > 15){
		  fontSize = 15;
	  }
	  $("#byCategoryButton2").attr('class', 'btn btn-default btn-lg btn-block');
	  $('#byCategoryButton2').prop('disabled', true);
	  $('#byCategoryButton3').prop('disabled', true);
	  $("#byCategoryButton3").attr('class', 'btn btn-default btn-lg btn-block');
	  $('#manufactorName2').html('בחר יצרן');
	  $('#productName3').html('בחר מוצר');
	  
	  $('#iphone-search').css('line-height',lineHeight+'px');
	  $('#iphone-search').css('font-size',fontSize+'px');
	  $('#iphone-scrollcontainer').height(modalHeight);
	  $('#modal_title_browse').html('בחירת קטגוריה');
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
	  //loadGFData('web_categories','קטגוריות','categoryCallManufactor',false);
	  loadGFDataPost('web_categories_post','קטגוריות','categoryCallManufactor',false,'null','null','null');
}

function categoryCallManufactor(value){
	
	chosedCategory = value;
	
	$('#categoryName1').html(value);
	$("#byCategoryButton2").attr('class', 'btn btn-primary btn-lg btn-block');
	$('#byCategoryButton2').prop('disabled', false);
	$('#byCategoryButton3').prop('disabled', true);
	$("#byCategoryButton3").attr('class', 'btn btn-default btn-lg btn-block');
	$('#manufactorName2').html('בחר יצרן');
	$('#productName3').html('בחר מוצר');
	
	
	if (!desktopBrowser){
		  $('#selectorFotter').hide();
		  $('#selectorFotter').hide();
	  	  $('#modalExit').show();
	  	  $('#backButton').show();
	  	  //init the back button:
		  $('#backButton').unbind('click');
		  $('#backButton').on('click', function(event) {
			  event.preventDefault(); 
			  categoryStart();
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
			  categoryStart();
			  
		  });
		  
		  
	  }
	
	
	$('#searchHeader').show();
	$('#resultHeader').hide();
	
	  var modalHeight = getModalHeight();
	  $('#iphone-scrollcontainer').height(modalHeight);
	  $('#iphone-scroll').css('height',modalHeight + 'px');
	  
	  $("#nav-indicator-fixed").css('width','0px');
	  //Now open new manufactor select
	 
	  $('#modal_title_browse').html('בחירת יצרן');
	  
	  
	  
	  $('#iphone-search').show();
	  $('#iphone-scroll').css('margin-right','50px');
	  
	  showModalWait();
	  showModal();
	  var apiName = 'web_manufactures/' + value ;//+ '}';
	  
	  //$('#iphone-search').hide();
	  
	  //loadGFData(apiName,'יצרנים','categoryCallProduct',false);
	  loadGFDataPost('web_manufactures_post','יצרנים','categoryCallProduct',false,'null','null',value);
	  
	
}

function categoryCallProduct(value){
	
	chosedManufactor = value;
	
	
	$('#manufactorName2').html(value);
	$("#byCategoryButton3").attr('class', 'btn btn-primary btn-lg btn-block');
	$('#byCategoryButton3').prop('disabled', false);
	
	checkIfLastProductObject();
		  
	
}

function lastBeforeCheckCategories(value,singleItem){
	
	var forShow;
	backFlow = 'categoryFlow';
	chosedProduct = value;
	if (value == 'OP') {
		 forShow = 'מוצרים אחרים';
		 backFlow = 'categoryCallManufactor';
	} else if (value == 'OPMTO') {
		  forShow = 'מוצרים אחרים';
		  chosedProduct = 'MULTIPLE';
		  backFlow = 'categoryCallManufactor';
	} else if (value == 'AP') {
		  forShow = 'כל המוצרים';
		  backFlow = 'categoryCallManufactor';
	} else if (value == 'APMTO') {
			forShow = 'כל המוצרים';	
			backFlow = 'categoryCallManufactor';
	} else if (singleItem){
		forShow = value;
		backFlow = 'categoryCallManufactor';
	} else {
		forShow = value;	 
	 }
	
	$('#productName3').html(forShow);
	checkRecommendation(chosedProduct,chosedManufactor,chosedCategory,backFlow);
	
}

function checkIfLastProductObject(){
	
	var queryObj = {};
	queryObj['manufactor'] = chosedManufactor;
	queryObj['product'] = 'null';
	queryObj['category'] = chosedCategory;
	
	var queryDao = {};
	queryDao['queryDao'] = queryObj;
	
	
	$.ajax({
	    type: "POST",
	    url: apiProdUrl + '/rest/web_products_post',
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
	    			lastBeforeCheckCategories(data.listDao.items.value,true);
	    		} else {
	    			$("#byCategoryButton2").attr('class', 'btn btn-primary btn-lg btn-block');
	    			$('#byCategoryButton2').prop('disabled', false);
	    			if (!desktopBrowser){
	    				  $('#selectorFotter').hide();
	    				  $('#selectorFotter').hide();
	    			  	  $('#modalExit').show();
	    			  	  $('#backButton').show();
	    			  	  //init the back button:
	    				  $('#backButton').unbind('click');
	    				  $('#backButton').on('click', function(event) {
	    					  event.preventDefault(); 
	    					  categoryCallManufactor(chosedCategory);
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
	    					  categoryCallManufactor(chosedCategory);
	    					  
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
	    			 
	    			  loadGFDataPost('web_products_post','מוצרים','lastBeforeCheckCategories',false,'null',chosedManufactor,chosedCategory);
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