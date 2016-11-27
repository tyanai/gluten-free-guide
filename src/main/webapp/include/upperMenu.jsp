		<%@ page contentType="text/html; charset=UTF-8"%>
		
		
		<table width="100%">
		<tr>
			<tr>
			<td>
		<form name="console_form" action="../jsp/smsSimulator.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SMS_SIMULATOR")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
		<tr>
			<td>
		<form name="console_form" action="../jsp/diffBase.jsp" method="POST">
		<input name="console" type="submit" id="console" value="מה עודכן לאחרונה" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
		<%if ((User.getUser().getPERMISSIONS().equals("admin"))){%> 
		
			<tr>
			<td>
		<form name="console_form" action="../jsp/contains.jsp" method="POST">
		<input name="console" type="submit" id="console" value="ממה צריך להיזהר" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
			<tr>
			<td>
		<form name="console_form" action="../jsp/notContains.jsp" method="POST">
		<input name="console" type="submit" id="console" value="השוואה לפי קטגוריה" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
		<%}%>
		
		
		
		
		
		<tr>
			<td>
		<form name="console_form" action="../jsp/editUserDetails.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("EDIT_USER_DETAILS")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
		<tr>
			<td>
		<form name="console_form" action="../jsp/cellSimulator.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SIMULATOR_FOR_CELL")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
			<tr>
			<td>
		<form name="console_form" action="../jsp/help.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("HELP")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
		</tr>
		
		<%if (!(User.getUser().getPERMISSIONS().equals("admin"))){%> 
			<tr>
	  	<td>	
		<form name="console_form" action="../jsp/nofshim.jsp" method="POST" target="_blank">
		<input name="console" type="submit" target="_blank" id="console" value="נופשים ללא גלוטן" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  onSubmit="window.open('', '_blank')">	
		</form>
			</td>
		</tr>
		<tr>
	  	<td>	
		<form name="console_form" action="../jsp/trufot.jsp" method="POST" target="_blank">
		<input name="console" type="submit" target="_blank" id="console" value="תרופות ותוספי מזון" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  onSubmit="window.open('', '_blank')">	
		</form>
			</td>
		</tr>
		<tr>
	  	<td>	
		<form name="console_form" action="../jsp/cosmetic.jsp" method="POST" target="_blank">
		<input name="console" type="submit" target="_blank" id="console" value="מוצרי טיפוח" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';"  onSubmit="window.open('', '_blank')">	
		</form>
			</td>
		</tr>
		<%}%>
		
		
		
		<%if ((User.getUser().getPERMISSIONS().equals("admin"))){%> 
		<tr>
		<td>
		<form name="console_form" action="../jsp/smsTest.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("SMS_TEST")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
	</td>
	  </tr>
	  <%}
	  if ((User.getUser().getPERMISSIONS().equals("admin")) || (User.getUser().getPERMISSIONS().equals("mitnadev"))){
	  %> 
	  <tr>
	  	<td>
		<form name="console_form" action="../jsp/manageUser.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("MANAGE_USERS")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
		</td>
		</tr>
		
		
	
		
		
		
		<%}
		if ((User.getUser().getPERMISSIONS().equals("admin"))){%> 
		
		
	  <tr>
	  	<td>
		<form name="console_form" action="../jsp/uploadConsole.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("DB_LOADER")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
		</td>
		</tr>
		<tr>
	  	<td>
		<form name="console_form" action="../jsp/manageJobs.jsp" method="POST">
		<input name="console" type="submit" id="console" value="תזמון משימות" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
		</td>
		</tr>
		<tr>
	  	<td>
		<form name="console_form" action="../jsp/emailSender.jsp" method="POST">
		<input name="console" type="submit" id="console" value="שליחת אימייל - ממשק כללי" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
		</td>
		</tr>
		<tr>
	  	<td>
		<form name="console_form" action="../jsp/statistics.jsp" method="POST">
		<input name="console" type="submit" id="console" value="<%=org.celiac.util.TemplateReader.getHebrewMapWord("STATISTICS")%>" style="width: 100%" class="button" enabled onMouseOver="this.style.background = '#d5e0e6';" onMouseOut="this.style.background = '#e4e4e4';">	
		</form>
		
		
		
			<%}%>
		</td>
		</tr>
		
		
		<tr>
	  	<td>
		<table width="100%" height="100%" border="1">
		<tr>
	  	<td valign="top" align="center" nowrap bgcolor="#FFFFFF" class="textSmall" > 
				<a href="https://market.android.com/details?id=com.oren.celiac" target="_blank"><img src="../images/and-app.png"  height="60" border="0"/></a>
				<img src="../images/hlg.png"  height="60" border="0"> 
				<a href="https://itunes.apple.com/us/app/hyym-ll-glwtn/id578102373?ls=1&mt=8" target="_blank"><img src="../images/apple.png"  height="60" border="0"/></a> 
			</td>
		</tr>
	</table>
		</td>
		</tr>
		
		</tr>
	</table>
		
	
		<img src="../images/celiac_logo.jpg" >
		
			