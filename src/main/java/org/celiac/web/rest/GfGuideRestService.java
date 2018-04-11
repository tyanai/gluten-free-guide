package org.celiac.web.rest;

import java.io.InputStream;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.celiac.datatype.ListDao;
import org.celiac.datatype.QueryDao;
import org.celiac.datatype.ResultDao;
import org.celiac.datatype.StringDao;
import org.celiac.util.Logger;
import org.celiac.util.RestUtil;


@Path("/")
public class GfGuideRestService {

	RestUtil service = new RestUtil();

	@Context
	HttpHeaders headers;

	@GET
	@Path("/check/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public StringDao check(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		//System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.check(product, manufacture, category);
	}
	
	@GET
	@Path("/web_check/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ResultDao web_check(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		//System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.web_check(product, manufacture, category);
	}
	
	@POST
	@Path("/web_check_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ResultDao web_check_post(QueryDao query) throws Exception {		
		service.validateRestUser(headers);
		return service.web_check(query.getProduct(), query.getManufactor(), query.getCategory());
	}
        
        @POST
        @Path("/upload_users")
        @Produces(APPLICATION_JSON)
        @Consumes("application/octet-stream")
        public StringDao upload_users(InputStream fileInputStream) throws Throwable {
                StringDao output = new StringDao();
                boolean result = false;
                try{
                    service.validateRestUser(headers);
                    result = service.validateInputUserFile(fileInputStream);
                } catch (Exception e){
                    output.setValue("Failed to upload users. " + e.getMessage());
                }
                if (result) output.setValue("OK");
                
		return output;
        }
	
	@GET
	@Path("/web_login_validate")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public StringDao web_login_validate() throws Exception {
		StringDao output = new StringDao();
		service.validateRestUser(headers);
		output.setValue("OK");
		return output;
	}

	@GET
	@Path("/manufactures")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao manufactures() throws Exception {
		return getManufactores("null"); // json of sorted list of companies
	}
	
	@GET
	@Path("/web_manufactures")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_manufactures() throws Exception {
		return getManufactores("null"); // json of sorted list of companies
	}

	@GET
	@Path("/manufactures/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao manufactures(@PathParam("category") String category) throws Exception {
		Logger.print("Android: got category - " + category);
		System.out.println("Android: got category - " + category);
		return getManufactores(category);

	}
	
	@GET
	@Path("/web_manufactures/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_manufactures(@PathParam("category") String category) throws Exception {
		//Logger.print("Android: got category - " + category);
		//System.out.println("Android: got category - " + category);
		return getManufactores(category);

	}
	
	@POST
	@Path("/web_manufactures_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_manufactures_post(QueryDao query) throws Exception {
		if ("null".equals(query.getProduct()) && "null".equals(query.getCategory())) {
			return getManufactores("null"); 
		} else {
			return getManufactores(query.getCategory());
		}

	}

	@GET
	@Path("/products/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao products(@PathParam("manufacture") String manufacture) throws Exception {
		return getProducts(manufacture, "null");

	}
	
	@GET
	@Path("/web_products/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_products(@PathParam("manufacture") String manufacture) throws Exception {
		return getProducts(manufacture, "null");

	}
	
	@POST
	@Path("/web_products_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_products_post(QueryDao query) throws Exception {
		if ("null".equals(query.getCategory()) ) {
			return getProducts(query.getManufactor(), "null");
		} else {
			return getProducts(query.getManufactor(), query.getCategory());
		}

	}

	@GET
	@Path("/products/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao products(@PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		return getProducts(manufacture, category);

	}
	
	@GET
	@Path("/web_products/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_products(@PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		return getProducts(manufacture, category);

	}

	@GET
	@Path("/categories")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao categories() throws Exception {
		return getCategories("null", "null");

	}
	
	@GET
	@Path("/web_categories")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_categories() throws Exception {
		return getCategories("null", "null");

	}

	@GET
	@Path("/categories/{product}/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao categories(@PathParam("product") String product, @PathParam("manufacture") String manufacture) throws Exception {
		return getCategories(product, manufacture);

	}
	
	
	
	@GET
	@Path("/web_categories/{product}/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_categories(@PathParam("product") String product, @PathParam("manufacture") String manufacture) throws Exception {
		return getCategories(product, manufacture);

	}
	
	@POST
	@Path("/web_categories_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_categories_post(QueryDao query) throws Exception {
		if ("null".equals(query.getProduct()) && "null".equals(query.getManufactor())) {
			return getCategories("null", "null");
		} else {
			return getCategories(query.getProduct(), query.getManufactor());
		}

	}
	
	@GET
	@Path("/searchproduct/{product}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao searchproduct(@PathParam("product") String product) throws Exception {
		return search(product,null);

	}
	
	@GET
	@Path("/web_searchproduct/{product}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_searchproduct(@PathParam("product") String product) throws Exception {
		return search(product,null);

	}
	
	@POST
	@Path("/web_searchproduct_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_searchproduct_post(QueryDao query) throws Exception {
		//if (!"null".equals(query.getProduct()) && !"null".equals(query.getManufactor())) {
		//	return getProducts(query.getManufactor(), "null");
		//} else {
			return search(query.getProduct(),null);
		//}

	}
	
	@GET
	@Path("/searchmanufacture/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public ListDao autocomplete(@PathParam("manufacture") String manufacture) throws Exception {
		return search(null,manufacture);

	}
	
	@GET
	@Path("/web_searchmanufacture/{manufacture}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_autocomplete(@PathParam("manufacture") String manufacture) throws Exception {
		return search(null,manufacture);

	}
	
	@POST
	@Path("/web_searchmanufacture_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public ListDao web_searchmanufacture_post(QueryDao query) throws Exception {
		return search(null,query.getManufactor());

	}
	
	

	private ListDao search(String product, String manufacture) throws Exception {
		service.validateRestUser(headers);
		return service.list(service.search(product,manufacture));
	}
	
	
	private ListDao getManufactores(String category) throws Exception {
		service.validateRestUser(headers);
		return service.list(service.getManufactureList(category));
	}

	private ListDao getProducts(String manufacture, String category) throws Exception {
		service.validateRestUser(headers);
		return service.list(service.getProductList(manufacture, category));
	}

	private ListDao getCategories(String product, String manufacture) throws Exception {
		service.validateRestUser(headers);
		return service.list(service.getCategoryList(product, manufacture));
	}
	
	@GET
	@Path("/smallimage/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public StringDao smallImage(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.smallImage(product, manufacture, category);
	}
	
	@GET
	@Path("/web_smallimage/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public StringDao web_smallImage(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.smallImage(product, manufacture, category);
	}
	
	@POST
	@Path("/web_smallimage_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public StringDao web_smallimage_post(QueryDao query) throws Exception {
		service.validateRestUser(headers);
		return service.smallImage(query.getProduct(), query.getManufactor(), query.getCategory());

	}
	
	@GET
	@Path("/largeimage/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes("text/plain;charset=UTF-8")
	public StringDao largeImage(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.largeImage(product, manufacture, category);
	}
	
	@GET
	@Path("/web_largeimage/{product}/{manufacture}/{category}")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public StringDao web_largeImage(@PathParam("product") String product, @PathParam("manufacture") String manufacture, @PathParam("category") String category) throws Exception {
		System.out.println(product + " " + manufacture + " " + category);
		service.validateRestUser(headers);
		return service.largeImage(product, manufacture, category);
	}
	
	@POST
	@Path("/web_largeimage_post")
	@Produces(APPLICATION_JSON)
	@Consumes(APPLICATION_JSON)
	public StringDao web_largeimage_post(QueryDao query) throws Exception {
		service.validateRestUser(headers);
		return service.largeImage(query.getProduct(), query.getManufactor(), query.getCategory());

	}

}
