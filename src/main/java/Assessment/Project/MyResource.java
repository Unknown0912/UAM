package Assessment.Project;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	static String uname;
	static String level;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    @POST
    @Path("register")
    public Response register(@FormParam("fullName")String username,@FormParam("email")String emailid,@FormParam("password")String password ) throws SQLException, ClassNotFoundException, URISyntaxException{
    	Userops user = new Userops();
    	return user.register(username, emailid, password);
    }
    @POST
    @Path("login")
    public Response login(@FormParam("uname")String username2,@FormParam("pass")String password2) throws SQLException, ClassNotFoundException, URISyntaxException{
    	Userops user = new Userops();
    	this.uname = username2;
    	boolean b =user.login(username2,password2);
    	db d = new db();
    	if(b) {  
    		String s = d.select_user_type(username2);
    		this.level = s;
    		if(s.equals("admin")) {
    			URI uri = new URI("/Project/admin.html");
    			return Response.seeOther(uri).build();
    		}
    		else if(s.equals("manager")) {
    			URI uri = new URI("/Project/manager.html");
    			return Response.seeOther(uri).build();
    		}
    		else {
    			URI uri = new URI("/Project/user.html");
    			return Response.seeOther(uri).build();
    		}
    	}
    	else {
        	URI uri = new URI("/Project/authfail.html");
            return Response.seeOther(uri).build();
    	}
    } 
    @GET
    @Path("check_all_resource")
    public String check_all_resource() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	String resourceData = user.checkallresource();
        return resourceData;
    }
    @GET
    @Path("check_all_res")
    public String check_all_res() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkallresource();
    }
    @GET
    @Path("check_resource")
    public String check_resource() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkresource(uname);
    }
    @GET
    @Path("get_user_resources/{selectedvalue}")
    public String get_user_resources(@PathParam("selectedvalue")String selectedvalue) throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkresource(selectedvalue);
    }
    @GET
    @Path("check_approvals")
    public String check_approvals() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkapprovals(uname);
    }
    @GET
    @Path("deletefromdb/{selectedValue}")
    public String del_f_db(@PathParam("selectedValue")String selectedvalue) throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	user.deletefromdb(selectedvalue);
    	return selectedvalue;
    }
    @GET
    @Path("deletefromuser/{selectedValue},{username}")
    public String del_f_user(@PathParam("selectedValue")String selectedvalue,@PathParam("username")String username) throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	user.deletefromuser(username,selectedvalue);
    	return "done";
    }
    
    @GET
    @Path("user_req/{value}")
    public String user_request(@PathParam("value")String value) throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	user.userrequest(value,uname);
    	return "done";
    }
    @GET
    @Path("show_team")
    public String show_team() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.show_team(uname);
    }
    @GET
    @Path("add_user/{selectedValue}")
    public String add_user(@PathParam("selectedValue")String selectedvalue) throws ClassNotFoundException, SQLException {
    	Userops user =new Userops();
    	return user.add_user_team(uname,selectedvalue);
    }
    @GET
    @Path("admin_req/{selectedValue}")
    public String admin_req(@PathParam("selectedValue")String futurem) throws ClassNotFoundException, SQLException {
    	Userops user =new Userops();
    	return user.admin_req(uname,futurem);
    }
    @GET
    @Path("check_role_requests")
    public String check_rol__req() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkrolerequests(level,uname);
    }
    @GET 
    @Path("check_resource_requests")
    public String check_res_req() throws ClassNotFoundException, SQLException {
    	Userops user = new Userops();
    	return user.checkresourcerequests(level,uname);
    }
    @GET 
    @Path("test")
    public String test(){
    	return "dvssd";
    }
    @GET 
    @Path("requestnew")
    public String requestnew() throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	return user.reqnewresource(uname);
    }
    @GET 
    @Path("deleteresource")
    public String deleteresource() throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	return user.checkresource(uname);
    }
    @GET 
    @Path("add_resource/{value}")
    public String addresource(@PathParam("value")String value) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.addresource(value);
    	return "resource added";
    }
    @GET 
    @Path("user_f_res/{value}")
    public String userfr(@PathParam("value")String value) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	return user.getusersofresources(value);
    	
    }
    @GET 
    @Path("res_f_user/{value}")
    public String resfu(@PathParam("value")String value) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	return user.getresouresofuser(value);
    }
    @GET 
    @Path("accept/{SelectedValue},{value}")
    public String accept(@PathParam("SelectedValue")String selectedvalue,@PathParam("value")String value) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.accept(selectedvalue,value);
    	return selectedvalue+value;
    }
    @GET 
    @Path("reject/{SelectedValue}")
    public String reject(@PathParam("SelectedValue")String selectedvalue) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.reject(selectedvalue);
    	return "done";
    }
    @GET 
    @Path("acceptres/{SelectedValue},{value}")
    public String acceptres(@PathParam("SelectedValue")String selectedvalue,@PathParam("value")String value) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.acceptres(selectedvalue,value);
    	return "done";
    }
    @GET 
    @Path("rejectres/{SelectedValue}")
    public String rejectres(@PathParam("SelectedValue")String selectedvalue) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.rejectres(selectedvalue);
    	return "done";
    }
    @GET 
    @Path("add_resource_request/{selectedValue}")
    public String add_resource_request(@PathParam("selectedValue")String selectedvalue) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.add_resource_request(selectedvalue,uname);
    	return "done";
    }
    @GET 
    @Path("delete_self_resource/{selectedValue}")
    public String delete_self_resource(@PathParam("selectedValue")String selectedvalue) throws ClassNotFoundException, SQLException{
    	Userops user = new Userops();
    	user.delete_self_resource(selectedvalue,uname);
    	return "done";
    }
}
