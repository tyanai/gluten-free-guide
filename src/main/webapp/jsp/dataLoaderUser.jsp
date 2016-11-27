<%@ page language="java" 
         import="org.celiac.web.bean.*, java.util.*, org.apache.commons.fileupload.*, org.apache.commons.fileupload.servlet.*, org.apache.commons.fileupload.disk.* ,be.telio.mediastore.ui.upload.MonitoredDiskFileItemFactory,be.telio.mediastore.ui.upload.UploadListener,org.apache.commons.fileupload.FileItem,org.apache.commons.fileupload.FileItemFactory,org.apache.commons.fileupload.FileUploadException,org.apache.commons.fileupload.servlet.ServletFileUpload" 
         errorPage="loginError.jsp" %>


<html>
<jsp:useBean id="User" scope="session" class="org.celiac.web.bean.User">
  <jsp:setProperty name="User" property="*"/>
</jsp:useBean>

<%@ include file="../include/header.jsp" %>  
<% if (!(User.getUser().getPERMISSIONS().equals("admin"))) throw new Exception("User was not login. Please login."); %>
<%
session.setAttribute("uploadUserStatus","FAIL");
try{
boolean isMultipart = ServletFileUpload.isMultipartContent(request);
// Create a new file upload handler
//DiskFileUpload upload = new DiskFileUpload();
// Create a factory for disk-based file items 

				UploadListener listener = new UploadListener(request, 30);
			
				
				FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
        //DiskFileItemFactory factory = new DiskFileItemFactory(); 

        // Set factory constraints 
        //factory.setSizeThreshold(1000000); 
        //factory.setRepository( ConfigServlet.getTempDirectory() ); 

        // Create a new file upload handler 
        ServletFileUpload upload = new ServletFileUpload(factory); 
        



// Parse the request
List /* FileItem */ items = upload.parseRequest(request);

Iterator iter = items.iterator();
while (iter.hasNext()) {
    FileItem item = (FileItem) iter.next();
		
    if (item.isFormField()) {
        //System.out.println("This is a form upload");
        %><jsp:forward page="uploadConsole.jsp"/><%
    } else {
        // Process a file upload
				if (!item.isFormField()) {
    			String fieldName = item.getFieldName();
    			String fileName = item.getName();
    			String contentType = item.getContentType();
    			boolean isInMemory = item.isInMemory();
    			long sizeInBytes = item.getSize();
    		
    			int locationOfLastFilesep = fileName.lastIndexOf("\\");
    		
    			fileName = fileName.substring((locationOfLastFilesep+1),fileName.length());
    			System.out.println(fieldName + " " + fileName + " " +contentType + " " + isInMemory + " " + sizeInBytes);
    		
    			//Process a file upload into the hard disk
    			if (sizeInBytes == 0){
    				session.setAttribute("uploadUserStatus","FAIL");
    			} else if (true) {
						//String fileLocation = User.validateInputFile(item);
			
						User.validateInputUserFile(item);
			
    				//File uploadedFile = new File(fileLocation + "/" + fileName);
    			
    				//item.write(uploadedFile);
    			
    				session.setAttribute("uploadUserStatus",fileName);
    			
					} 
				}
    }
}
}catch(Exception e){
  session.setAttribute("uploadUserStatus","FAIL");
  session.setAttribute("messageStatus", e.getMessage());
  %><jsp:forward page="uploadConsole.jsp"/><%
}
%>
<jsp:forward page="uploadConsole.jsp"/>
<%@ include file="../include/buttom.jsp" %>  