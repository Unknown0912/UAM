package Assessment.Project;

import java.net.URI;

import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class Userops {
	public Response register(String username,String emailid,String password ) throws SQLException, ClassNotFoundException, URISyntaxException{
		db d = new db();
    	d.create_user(username, emailid, password);
    	URI uri = new URI("/Project");
        return Response.seeOther(uri).build();
    }
    public boolean login(String username2,String password2 ) throws SQLException, ClassNotFoundException, URISyntaxException{
    	db d = new db();
    	boolean b = d.validate_user(username2, password2);
    	return b;    	
    } 
    public String checkresource(String uname) throws SQLException, ClassNotFoundException {
    	db d = new db();
    	return d.getresource(uname);   	
    	
    }
	public String checkapprovals(String uname) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.checkapprovals(uname);
	}
	public String upload_request(String value,String username) throws SQLException, ClassNotFoundException {
		db d = new db();
		return d.upload_request(value,username);
	}
	public String getapproval_req(String level) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.getapproval_req(level);
	}
	public String show_team(String uname) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.show_team(uname);
	}
	public String add_user_team(String uname,String memname) throws SQLException, ClassNotFoundException {
		db d = new db();
		return d.add_user_team(uname,memname);
	}
	public String admin_req(String uname,String futurem) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.admin_req(uname,futurem);
	}
	public String checkrolerequests(String level,String uname) throws ClassNotFoundException, SQLException {
		db d = new db();
		String data = d.getrolerequestsdata(level,uname);
		return data;
		
	}
	public String checkresourcerequests(String level,String uname) throws SQLException, ClassNotFoundException {
		db d = new db();
		String data = d.getresourcerequestsdata(level,uname);
		return data;
	}
	public String checkallresource() throws ClassNotFoundException, SQLException {
		db d = new db();
    	return d.getallresource();   
	}
	public void addresource(String value) throws ClassNotFoundException, SQLException {
		db d = new db();
		d.addres(value);
	}
	public String getusersofresources(String value) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.getuserofresources(value);
		// TODO Auto-generated method stub
		
	}
	public String getresouresofuser(String value) throws ClassNotFoundException, SQLException {
		db d = new db();
		return d.getresouresofuser(value);
		
		// TODO Auto-generated method stub
		
	}
	public void userrequest(String value,String uname) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.userequest(value,uname);
	}
	public void accept(String username,String role) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.accept(username,role);
	}
	public void reject(String selectedvalue) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		db d = new db();
		d.reject(selectedvalue);
	}
	public void acceptres(String selectedvalue,String value) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.acceptres(selectedvalue,value);
	}
	public void rejectres(String selectedvalue) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		db d = new db();
		d.rejectres(selectedvalue);
	}
	public void deletefromdb(String selectedvalue) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.deletefromdb(selectedvalue);
	}
	public void deletefromuser(String username,String selectedvalue) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		db d = new db();
		d.deletefromuser(username,selectedvalue);
	}
	public String reqnewresource(String uname) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		return d.reqnewresource(uname);
	}
	public void add_resource_request(String selectedvalue,String username) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.add_resource_request(selectedvalue,username);
	}
	public void delete_self_resource(String selectedvalue, String uname) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		db d = new db();
		d.delete_self_resource(selectedvalue,uname);
	}
}
