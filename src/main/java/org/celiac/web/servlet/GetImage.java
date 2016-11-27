package org.celiac.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.celiac.util.database.DBQueryFactory;

public class GetImage extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5144382914591647058L;

	/**
	 * 
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");

			String companySelect = null;
			String productSelect = null;
			String categorySelect = null;
			String imageSize = null;

			HttpSession session = request.getSession(true);
	

			

			if ((String) session.getAttribute("companySelect") != null) {
				companySelect = (String) session.getAttribute("companySelect");

			}

			if ((String) session.getAttribute("productSelect") != null) {
				productSelect = (String) session.getAttribute("productSelect");
			}

			if ((String) session.getAttribute("categorySelect") != null) {
				categorySelect = (String) session
						.getAttribute("categorySelect");
			}

			
			if ((String) request.getParameter("imageSize") != null) {
				imageSize = (String) request.getParameter("imageSize");
			}
			
			
			
			byte[] image = null;
			if ("small".equals(imageSize)){
			image = DBQueryFactory.getDBHandler().getSmallImage(productSelect,
					companySelect, categorySelect);
			}else if ("large".equals(imageSize)){
				image = DBQueryFactory.getDBHandler().getOrigImage(productSelect,
						companySelect, categorySelect);
			}

			if (image != null) {
				
				response.reset();
				response.setContentType("image/gif");
				java.io.OutputStream o = response.getOutputStream();
				
				o.write(image);
				o.flush();
				o.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(request, res);
	}
}
