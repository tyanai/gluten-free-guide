

function initNavIndicators()
{
	if (isOpera || isSafari || isFirefox){
		$("#iphone-search").css('position','inherit');
	} else {
		
	
	//Check that you are not changing twice:
	var lastLetterUsed = 'N/A';
	
	// First time, the indicator needs a character
	
	$("#nav-indicator-fixed").html("");
	//$("#nav-indicator-fixed").append("A");
	
	
	var searchWidth = $('#iphone-scroll').width();
	
	//alert($('#iphone-scroll').width());
	
	// Fading out the search bar
	$("#iphone-search").fadeTo(1, 0.85);
	
	
	
	
	// When scrolling, this function checks if the indicator needs to be changed
	//Init a varible to hold last letter when empty
	var lastLetterSeen = '';
	var firstLetterFound = false;
	
	var cur0_last = lastLetterSeen; var cur0 = checkIndexForLetterasignment($("#nav-0"),'0');
	var cur1_last = lastLetterSeen; var cur1 = checkIndexForLetterasignment($("#nav-1"),'1');
	var cur2_last = lastLetterSeen; var cur2 = checkIndexForLetterasignment($("#nav-2"),'2');
	var cur3_last = lastLetterSeen; var cur3 = checkIndexForLetterasignment($("#nav-3"),'3');
	var cur4_last = lastLetterSeen; var cur4 = checkIndexForLetterasignment($("#nav-4"),'4');
	var cur5_last = lastLetterSeen; var cur5 = checkIndexForLetterasignment($("#nav-5"),'5');
	var cur6_last = lastLetterSeen; var cur6 = checkIndexForLetterasignment($("#nav-6"),'6');
	var cur7_last = lastLetterSeen; var cur7 = checkIndexForLetterasignment($("#nav-7"),'7');
	var cur8_last = lastLetterSeen; var cur8 = checkIndexForLetterasignment($("#nav-8"),'8');
	var cur9_last = lastLetterSeen; var cur9 = checkIndexForLetterasignment($("#nav-9"),'9');
	
	var cura_last = lastLetterSeen; var cura = checkIndexForLetterasignment($("#nav-a"),'A');
	var curb_last = lastLetterSeen; var curb = checkIndexForLetterasignment($("#nav-b"),'B');
	var curc_last = lastLetterSeen; var curc = checkIndexForLetterasignment($("#nav-c"),'C');
	var curd_last = lastLetterSeen; var curd = checkIndexForLetterasignment($("#nav-d"),'D');
	var cure_last = lastLetterSeen; var cure = checkIndexForLetterasignment($("#nav-e"),'E');
	var curf_last = lastLetterSeen; var curf = checkIndexForLetterasignment($("#nav-f"),'F');
	var curg_last = lastLetterSeen; var curg = checkIndexForLetterasignment($("#nav-g"),'G');
	var curh_last = lastLetterSeen; var curh = checkIndexForLetterasignment($("#nav-h"),'H');
	var curi_last = lastLetterSeen; var curi = checkIndexForLetterasignment($("#nav-i"),'I');
	var curj_last = lastLetterSeen; var curj = checkIndexForLetterasignment($("#nav-j"),'J');
	var curk_last = lastLetterSeen; var curk = checkIndexForLetterasignment($("#nav-k"),'K');
	var curl_last = lastLetterSeen; var curl = checkIndexForLetterasignment($("#nav-l"),'L');
	var curm_last = lastLetterSeen; var curm = checkIndexForLetterasignment($("#nav-m"),'M');
	var curn_last = lastLetterSeen; var curn = checkIndexForLetterasignment($("#nav-n"),'N');
	var curo_last = lastLetterSeen; var curo = checkIndexForLetterasignment($("#nav-o"),'O');
	var curp_last = lastLetterSeen; var curp = checkIndexForLetterasignment($("#nav-p"),'P');
	var curq_last = lastLetterSeen; var curq = checkIndexForLetterasignment($("#nav-q"),'Q');
	var curr_last = lastLetterSeen; var curr = checkIndexForLetterasignment($("#nav-r"),'R');
	var curs_last = lastLetterSeen; var curs = checkIndexForLetterasignment($("#nav-s"),'S');
	var curt_last = lastLetterSeen; var curt = checkIndexForLetterasignment($("#nav-t"),'T');
	var curu_last = lastLetterSeen; var curu = checkIndexForLetterasignment($("#nav-u"),'U');
	var curv_last = lastLetterSeen; var curv = checkIndexForLetterasignment($("#nav-v"),'V');
	var curw_last = lastLetterSeen; var curw = checkIndexForLetterasignment($("#nav-w"),'W');
	var curx_last = lastLetterSeen; var curx = checkIndexForLetterasignment($("#nav-x"),'X');
	var cury_last = lastLetterSeen; var cury = checkIndexForLetterasignment($("#nav-y"),'Y');
	var curz_last = lastLetterSeen; var curz = checkIndexForLetterasignment($("#nav-z"),'Z');
	
	
	var curalef_last = lastLetterSeen; var curalef = checkIndexForLetterasignment($("#nav-alef"),'א');
	var curbeit_last = lastLetterSeen; var curbeit = checkIndexForLetterasignment($("#nav-beit"),'ב');
	var curgimel_last = lastLetterSeen; var curgimel = checkIndexForLetterasignment($("#nav-gimel"),'ג');
	var curdaled_last = lastLetterSeen; var curdaled = checkIndexForLetterasignment($("#nav-daled"),'ד');
	var curhe_last = lastLetterSeen; var curhe = checkIndexForLetterasignment($("#nav-he"),'ה');
	var curvav_last = lastLetterSeen; var curvav = checkIndexForLetterasignment($("#nav-vav"),'ו');
	var curzain_last = lastLetterSeen; var curzain = checkIndexForLetterasignment($("#nav-zain"),'ז');
	var curhet_last = lastLetterSeen; var curhet = checkIndexForLetterasignment($("#nav-het"),'ח');
	var curtet_last = lastLetterSeen; var curtet = checkIndexForLetterasignment($("#nav-tet"),'ט');
	var curyod_last = lastLetterSeen; var curyod = checkIndexForLetterasignment($("#nav-yod"),'י');
	var curcaf_last = lastLetterSeen; var curcaf = checkIndexForLetterasignment($("#nav-caf"),'כ');
	var curlamed_last = lastLetterSeen; var curlamed = checkIndexForLetterasignment($("#nav-lamed"),'ל');
	var curmem_last = lastLetterSeen; var curmem = checkIndexForLetterasignment($("#nav-mem"),'מ');
	var curnun_last = lastLetterSeen; var curnun = checkIndexForLetterasignment($("#nav-nun"),'נ');
	var cursamech_last = lastLetterSeen; var cursamech = checkIndexForLetterasignment($("#nav-samech"),'ס');
	var curain_last = lastLetterSeen; var curain = checkIndexForLetterasignment($("#nav-ain"),'ע');
	var curpe_last = lastLetterSeen; var curpe = checkIndexForLetterasignment($("#nav-pe"),'פ');
	var curzadik_last = lastLetterSeen; var curzadik = checkIndexForLetterasignment($("#nav-zadik"),'צ');
	var curkof_last = lastLetterSeen; var curkof = checkIndexForLetterasignment($("#nav-kof"),'ק');
	var curresh_last = lastLetterSeen; var curresh = checkIndexForLetterasignment($("#nav-resh"),'ר');
	var curshin_last = lastLetterSeen; var curshin = checkIndexForLetterasignment($("#nav-shin"),'ש');
	var curtaf_last = lastLetterSeen; var curtaf = checkIndexForLetterasignment($("#nav-taf"),'ת');
	
	
	
	function changeNavIndicator(value)
	{
		//$("#nav-indicator-fixed").replaceWith("<div id=\"nav-indicator-fixed\">"+value+"</div>");
		if (lastLetterUsed != value){
			$("#nav-indicator-fixed").html(value);
			
			lastLetterUsed = value;
		}
	}
	
	function checkIndexForLetterasignment(objectNav,letter){
		if (objectNav.position()){ 
			lastLetterSeen=letter;
			if (!firstLetterFound){
				$("#nav-indicator-fixed").append(letter);
				firstLetterFound = true;
			}
			
			return objectNav.position().top; 
		} else {
			return 'empty'; 
		}
	}
	
	function switchIndexLetter(elementLetter,objectNav, last, to)
	{
		if((elementLetter != 'empty') && objectNav.position().top < 20 && objectNav.position().top > -20)
		{
			
			if(elementLetter < objectNav.position().top)
				changeNavIndicator(last);
			else
				changeNavIndicator(to);;
			return objectNav.position().top;
		}
		return elementLetter;
	}
	
	$("#iphone-scrollcontainer").scroll(function()
	{
		
		
		cur0 = switchIndexLetter(cur0,$("#nav-0"),cur0_last,"0");
		cur1 = switchIndexLetter(cur1,$("#nav-1"),cur1_last,"1");
		cur2 = switchIndexLetter(cur2,$("#nav-2"),cur2_last,"2");
		cur3 = switchIndexLetter(cur3,$("#nav-3"),cur3_last,"3");
		cur4 = switchIndexLetter(cur4,$("#nav-4"),cur4_last,"4");
		cur5 = switchIndexLetter(cur5,$("#nav-5"),cur5_last,"5");
		cur6 = switchIndexLetter(cur6,$("#nav-6"),cur6_last,"6");
		cur7 = switchIndexLetter(cur7,$("#nav-7"),cur7_last,"7");
		cur8 = switchIndexLetter(cur8,$("#nav-8"),cur8_last,"8");
		cur9 = switchIndexLetter(cur9,$("#nav-9"),cur9_last,"9");	
		
		cura = switchIndexLetter(cura,$("#nav-a"),cura_last,"A");
		curb = switchIndexLetter(curb,$("#nav-b"),curb_last,"B");
		curc = switchIndexLetter(curc,$("#nav-c"),curc_last,"C");
		curd = switchIndexLetter(curd,$("#nav-d"),curd_last,"D");
		cure = switchIndexLetter(cure,$("#nav-e"),cure_last,"E");
		curf = switchIndexLetter(curf,$("#nav-f"),curf_last,"F");
		curg = switchIndexLetter(curg,$("#nav-g"),curg_last,"G");
		curh = switchIndexLetter(curh,$("#nav-h"),curh_last,"H");
		curi = switchIndexLetter(curi,$("#nav-i"),curi_last,"I");
		curj = switchIndexLetter(curj,$("#nav-j"),curj_last,"J");
		curk = switchIndexLetter(curk,$("#nav-k"),curk_last,"K");
		curl = switchIndexLetter(curl,$("#nav-l"),curl_last,"L");
		curm = switchIndexLetter(curm,$("#nav-m"),curm_last,"M");
		curn = switchIndexLetter(curn,$("#nav-n"),curn_last,"N");
		curo = switchIndexLetter(curo,$("#nav-o"),curo_last,"O");
		curp = switchIndexLetter(curp,$("#nav-p"),curp_last,"P");
		curq = switchIndexLetter(curq,$("#nav-q"),curq_last,"Q");
		curr = switchIndexLetter(curr,$("#nav-r"),curr_last,"R");
		curs = switchIndexLetter(curs,$("#nav-s"),curs_last,"S");
		curt = switchIndexLetter(curt,$("#nav-t"),curt_last,"T");
		curu = switchIndexLetter(curu,$("#nav-u"),curu_last,"U");
		curv = switchIndexLetter(curv,$("#nav-v"),curv_last,"V");
		curw = switchIndexLetter(curw,$("#nav-w"),curw_last,"W");
		curx = switchIndexLetter(curx,$("#nav-x"),curx_last,"X");
		cury = switchIndexLetter(cury,$("#nav-y"),cury_last,"Y");
		curz = switchIndexLetter(curz,$("#nav-z"),curz_last,"Z");
		
		curalef = switchIndexLetter(curalef,$("#nav-alef"),curalef_last,'א');
		curbeit = switchIndexLetter(curbeit,$("#nav-beit"),curbeit_last,'ב');
		curgimel = switchIndexLetter(curgimel,$("#nav-gimel"),curgimel_last,'ג');
		curdaled = switchIndexLetter(curdaled,$("#nav-daled"),curdaled_last,'ד');
		curhe = switchIndexLetter(curhe,$("#nav-he"),curhe_last,'ה');
		curvav = switchIndexLetter(curvav,$("#nav-vav"),curvav_last,'ו');
		curzain = switchIndexLetter(curzain,$("#nav-zain"),curzain_last,'ז');
		curhet = switchIndexLetter(curhet,$("#nav-het"),curhet_last,'ח');
		curtet = switchIndexLetter(curtet,$("#nav-tet"),curtet_last,'ט');
		curyod = switchIndexLetter(curyod,$("#nav-yod"),curyod_last,'י');
		curcaf = switchIndexLetter(curcaf,$("#nav-caf"),curcaf_last,'כ');
		curlamed = switchIndexLetter(curlamed,$("#nav-lamed"),curlamed_last,'ל');
		curmem = switchIndexLetter(curmem,$("#nav-mem"),curmem_last,'מ');
		curnun = switchIndexLetter(curnun,$("#nav-nun"),curnun_last,'נ');
		cursamech = switchIndexLetter(cursamech,$("#nav-samech"),cursamech_last,'ס');
		curain = switchIndexLetter(curain,$("#nav-ain"),curain_last,'ע');
		curpe = switchIndexLetter(curpe,$("#nav-pe"),curpe_last,'פ');
		curzadik = switchIndexLetter(curzadik,$("#nav-zadik"),curzadik_last,'צ');
		curkof = switchIndexLetter(curkof,$("#nav-kof"),curkof_last,'ק');
		curresh = switchIndexLetter(curresh,$("#nav-resh"),curresh_last,'ר');
		curshin = switchIndexLetter(curshin,$("#nav-shin"),curshin_last,'ש');
		curtaf = switchIndexLetter(curtaf,$("#nav-taf"),curtaf_last,'ת');
		
		
		// Fade the indicator, staying CSS2.1 valid
		$("#nav-indicator-fixed").fadeTo(1, 0.85);
		$("#nav-indicator-fixed").css('width',searchWidth + 'px');
		$("#nav-indicator-fixed").css('background-color','#337ab7');
		
	});
	}
}

