<%
	try{
	if ( showPirsomot && (User.getUser().getPERMISSIONS().equals("admin"))){%> 
    
		<td valign="top">
		<table align="left" width="100%"  >
			<td valign="top" align="left">
		<!--<td valign="top" nowrap bgcolor="#f5f5e1" class="textSmall" >-->
		<%@ include file="../include/pirsomot.jsp" %>
		</td>
	  </table>
	  </td>
	
	  <%}
	  }catch (Exception f){
	}%>
</tr>



</td></tr>
</table>
<tr><td valign="bottom" align="center">

<table   height="25" cellpadding="0" cellspacing="0" border="0" bgcolor="#f5f5e1">
	<tr height="2 ><td colspan="100%"></td></tr>
	<tr valign="bottom" >
		<br><br>
		<td class="headerNotUnderLine" align="left" width="30%" nowrap="nowrap"><a href="mailto:tal@yanai.org.il">Created by Tal Yanai</a></td>
		<td class="headerNotUnderLine" width="50%"></td>
		<td class="headerNotUnderLine" align="right" nowrap="nowrap"><%=org.celiac.util.TemplateReader.getHebrewMapWord("TERMS")%></td>
		<td valign="bottom" class="headerNotUnderLine" width="10%"></td>

		
	</tr>
</table>
</td></tr>
</td></tr>
</table>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-5180341-1");
pageTracker._trackPageview();
</script>
</body>
</html>

<%
}catch (Exception e){

	org.celiac.util.Logger.error("An error occured",e);
	String theErrorOut = e.getMessage();
	session.setAttribute("gfsmsLoginError",theErrorOut);
	response.sendRedirect("../index.jsp");
}
%>
