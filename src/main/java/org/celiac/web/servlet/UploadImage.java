package org.celiac.web.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.celiac.util.database.DBQueryFactory;

public class UploadImage extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//PreparedStatement pst = null;
		//ResultSet rs = null;
		//Statement stmt = null;
		//Connection conn = null;
		try {

			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			@SuppressWarnings("rawtypes")
			List items = upload.parseRequest(request);

			@SuppressWarnings("rawtypes")
			Iterator iter = items.iterator();

			int count = 0;
			byte[] data = null;

			String productSelect = null;
			String categorySelect = null;
			String companySelect = null;
			String typeOfInsert = null;

			while (iter.hasNext()) {

				count++;
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					String name = item.getFieldName();

					if ("productSelect".equals(name)) {
						productSelect = item.getString("UTF-8");
					} else if ("companySelect".equals(name)) {
						companySelect = item.getString("UTF-8");
					} else if ("categorySelect".equals(name)) {
						categorySelect = item.getString("UTF-8");
					} else if ("typeOfInsert".equals(name)) {
						typeOfInsert = item.getString("UTF-8");
					}

				} else {
					
					data = item.get();
				}

			}

			if ("new".equals(typeOfInsert)) {

				DBQueryFactory.getDBHandler().insertImage(productSelect,
						companySelect, categorySelect, data);
			} else if ("update".equals(typeOfInsert)) {
				DBQueryFactory.getDBHandler().updateImage(productSelect,
						companySelect, categorySelect, data);
			} else if ("delete".equals(typeOfInsert)) {
				DBQueryFactory.getDBHandler().deleteImage(productSelect,
						companySelect, categorySelect);
			}

			String nextJSP = "/jsp/uploadImage.jsp?" + "productSelect="
					+ productSelect + "&companySelect=" + companySelect
					+ "&categorySelect=" + categorySelect;
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
}
