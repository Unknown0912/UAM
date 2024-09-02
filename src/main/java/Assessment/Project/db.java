package Assessment.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class db {
	String driver = "com.mysql.jdbc.Driver";
	String url="jdbc:mysql://localhost:3306/b6";
	String userName = "root";
	String password = "root";
	Connection c ;

	db() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	    c = DriverManager.getConnection(url, userName, password);		
	}
	public String create_user (String username,String emailid,String password) throws SQLException {
		PreparedStatement myStmt;
		ResultSet rs;
		   myStmt = c.prepareStatement("SELECT COUNT(*) FROM employee_details");
	        rs = myStmt.executeQuery();
	        
	        // Determine the user level
	        String userLevel;
	        if (rs.next() && rs.getInt(1) == 0) {
	            userLevel = "admin";  // First user
	        } else {
	            userLevel = "user";   // Subsequent users
	        }
	        
		myStmt = c.prepareStatement("insert into employee_details (username,email,pass_word,userlevel,manager_name) Values (?,?,?,?,?)");
		myStmt.setString(1,username);
		myStmt.setString(2,emailid);
		myStmt.setString(3,password);
		myStmt.setString(4,userLevel);
		myStmt.setString(5,username);
		myStmt.executeUpdate();
		return "done";
        }
	public boolean validate_user(String username2, String password2) throws SQLException {
		PreparedStatement myStmt;
		myStmt = c.prepareStatement("select * from employee_details where username = ?");
		myStmt.setString(1,username2);
		ResultSet rs = myStmt.executeQuery();
		if(rs.next() == false) {
			
			return false;
		}
		String pass = rs.getString("pass_word");
		if(pass.equals(password2)) {
			return true;
		}
		return false;
	}
	public String select_user_type(String username) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("select * from employee_details where username = ?");
		myStmt.setString(1,username);
		ResultSet rs = myStmt.executeQuery();
		if(rs.next() == false) {
			
			return false+"";
		}
		String userlevel = rs.getString("userlevel");
		return userlevel;
	}
	public String getresource(String username) throws SQLException {
		PreparedStatement myStmt = c.prepareStatement("select * from resource_allocation where username = ?");
		myStmt.setString(1, username);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("resource_allocated");
		}
		return resources;
	}
	public String getapproval_req(String value) throws SQLException {
		PreparedStatement myStmt = c.prepareStatement("select * from approvals where req_assigned = ?");
		myStmt.setString(1,value);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("employee_name")+" "+rs.getString("status")+"\n";
		}
		return resources;
	}
	public String upload_request(String value,String username) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("Insert into approvals (req_assigned_by,employee_name,status) values (?,?,?)");
		myStmt.setString(1,value);
		myStmt.setString(2,username);
		myStmt.setString(3,"pending");
		ResultSet rs = myStmt.executeQuery();
		String resources = "done";
		return resources;
	}
	public String checkapprovals(String uname) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("select * from approvals where employee_name = ?");
		myStmt.setString(1,uname);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("req_assigned")+" "+rs.getString("status")+"\n";
		}
		return resources;
	}
	public String show_team(String uname) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("select * from employee_details where manager_name = ?");
		myStmt.setString(1,uname);
		ResultSet rs = myStmt.executeQuery();
		String resources ="";
		while(rs.next()) {
			resources = resources+" "+rs.getString("username")+" "+"\n";
		}
		if(resources == "") {
			resources = "no team found";
		}
		return resources;
	}
	public String add_user_team(String mname,String memname) throws SQLException {
		// TODO Auto-generated method stub
//		PreparedStatement myStmt = c.prepareStatement("Select * from employee_details where username = ?");
//		myStmt.setString(1,mname);
//		ResultSet rs = myStmt.executeQuery();
//		rs.next();
//		String manager_name = rs.getString("manager_name");
////		if(manager_name != null) {
////			return "manager exists";
////		}
		PreparedStatement myStmt2 = c.prepareStatement("update employee_details set manager_name = ? where username = ?");
		myStmt2.setString(1,mname);
		myStmt2.setString(2,memname);
		int r = myStmt2.executeUpdate();
		return "added member";
	}
	public String admin_req(String username,String futurem) throws SQLException{
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("Insert into role_requests (approver,role_change,requester,status) values (?,?,?,?)");
		myStmt.setString(1,"admin");
		myStmt.setString(2,"admin");
		myStmt.setString(3,username);
		myStmt.setString(4,"pending");
		myStmt.executeUpdate();
		PreparedStatement myStmt1 = c.prepareStatement("Insert into role_requests (approver,role_change,requester,status) values (?,?,?,?)");
		myStmt1.setString(1,"admin");
		myStmt1.setString(2,"manager");
		myStmt1.setString(3,futurem);
		myStmt1.setString(4,"pending");
		myStmt1.executeUpdate();
		String resources = "done";
		return resources;
	}
	public String getallresource() throws SQLException {
		PreparedStatement myStmt = c.prepareStatement("select * from resource_names");
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("resource_name");
		}
		return resources;
	}
	public String getrolerequestsdata(String level,String uname) throws SQLException {
		StringBuilder resources = new StringBuilder();

	    if (level.equals("admin")) {
	        PreparedStatement myStmt = null;
	        ResultSet rs = null;
	            myStmt = c.prepareStatement("SELECT * FROM role_requests WHERE approver = ?");
	            myStmt.setString(1, "admin");
	            rs = myStmt.executeQuery();

	            while (rs.next()) {
	                String requester = rs.getString("requester");
	                String role_change = rs.getString("role_change");

	                resources.append("<a>")
	                        .append(rs.getString("approver")).append(" ")
	                        .append(requester).append(" ")
	                        .append(rs.getString("status")).append(" ")
	                        .append(rs.getString("role_change")).append(" ")
	                        .append("</a> ")
	                        .append("<button onclick=\"approveApi('").append(requester).append("', '").append(role_change).append("')\">Approve</button> ")
	                        .append("<button onclick=\"rejectApi('").append(requester).append("', '").append(role_change).append("')\">Reject</button> ")
	                        .append("</a><br>\n");
	            }

	            String script = "<script>\r\n" +
	                    "    function approveApi(requester,role_change) {\r\n" +
	                    "        const url = `http://localhost:6970/Project/webapi/myresource/accept/${encodeURIComponent(requester)},${encodeURIComponent(role_change)}`;\r\n" +
	                    "        location.replace(url);\r\n" +
	                    "    }\r\n" +
	                    "    function rejectApi(requester,role_change) {\r\n" +
	                    "        const url = `http://localhost:6970/Project/webapi/myresource/reject/${encodeURIComponent(requester)}`;\r\n" +
	                    "        location.replace(url);\r\n" +
	                    "    }\r\n" +
	                    "</script>";

	            return resources.toString() + script;
	        }
		PreparedStatement myStmt = c.prepareStatement("Select * from role_requests where requester = ?");
		myStmt.setString(1, uname);
		ResultSet rs = myStmt.executeQuery();
		String res = "";
		while(rs.next()) {
			res = res+"<a>"+" "+rs.getString("approver")+" "+rs.getString("requester")+" "+rs.getString("status")+" "+rs.getString("role_change")+" "+"</a>"+"<br>"+"\n";
		}
		return res;
	}
	public String getresourcerequestsdata(String level,String uname) throws SQLException {
		if (level.equals("admin")) {
		    PreparedStatement myStmt = c.prepareStatement("SELECT * FROM resource_requests WHERE approver = ?");
		    myStmt.setString(1, "admin");
		    ResultSet rs = myStmt.executeQuery();
		    StringBuilder resources = new StringBuilder();
		    
		    while (rs.next()) {
		        String requester = rs.getString("requester");
		        String resourceRequested = rs.getString("resource_requested");
		        String approver = rs.getString("approver");
		        String status = rs.getString("status");
		        
		        resources.append("<a>")
		                 .append(requester).append(" ")
		                 .append(resourceRequested).append(" ")
		                 .append(approver).append(" ")
		                 .append(status).append(" ")
	                        .append("<button onclick=\"approveApi('").append(requester).append("', '").append(resourceRequested).append("')\">Approve</button> ")
	                        .append("<button onclick=\"rejectApi('").append(requester).append("', '").append(resourceRequested).append("')\">Reject</button> ")
		                 .append("</a><br>\n");
		    }
		    
		    String script = "<script>\r\n" + 
		                    "    function approveApi(requester,resourceRequested) {\r\n" + 
		                    "        const url = `http://localhost:6970/Project/webapi/myresource/acceptres/${encodeURIComponent(requester)},${encodeURIComponent(resourceRequested)}`;\r\n" + 
		                    "        location.replace(url);\r\n" + 
		                    "    }\r\n" + 
		                    "    function rejectApi(requester) {\r\n" + 
		                    "        const url = `http://localhost:6970/Project/webapi/myresource/rejectres/${encodeURIComponent(requester)}`;\r\n" + 
		                    "        location.replace(url);\r\n" + 
		                    "    }\r\n" + 
		                    "</script>";
		    
		    return resources.toString() + script;
		}
		PreparedStatement myStmt = c.prepareStatement("Select * from resource_requests where approver = ?");
		myStmt.setString(1, uname);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+"<a>"+" "+rs.getString("requester")+" "+rs.getString("resource_requested")+" "+rs.getString("approver")+" "+" "+"</a>"+" "+"\n";
		}
		return resources;
	}
	public void addres(String value) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("Insert into resource_names (resource_name) values (?)");
		myStmt.setString(1,value);
		myStmt.executeUpdate();
	}
	public String getuserofresources(String value) throws SQLException {
		PreparedStatement myStmt = c.prepareStatement("select * from resource_allocation where resource_allocated = ?");
		myStmt.setString(1,value);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("username");
		}
		return resources;
		
	}
	public String getresouresofuser(String value) throws SQLException {
		PreparedStatement myStmt = c.prepareStatement("select * from resource_allocation where username = ?");
		myStmt.setString(1,value);
		ResultSet rs = myStmt.executeQuery();
		String resources = "";
		while(rs.next()) {
			resources = resources+" "+rs.getString("resource_allocated");
		}
		return resources;
		
	}
	public void userequest(String role_change,String username) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("Insert into role_requests (approver,role_change,requester,status) values (?,?,?,?)");
		myStmt1.setString(1,"admin");
		myStmt1.setString(2,role_change);
		myStmt1.setString(3,username);
		myStmt1.setString(4,"pending");
		myStmt1.executeUpdate();
	}
	public void accept(String username,String role) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("Update role_requests set status = ? where requester = ?");
		myStmt1.setString(1,"accepted");
		myStmt1.setString(2,username);
		myStmt1.executeUpdate();
		
		
		if(role.equals("manager")) {
			PreparedStatement myStmt4 = c.prepareStatement("Select * from employee_details where username = ?") ;
			myStmt4.setString(1,username);
			ResultSet rs = myStmt4.executeQuery();
			rs.next();
			String manager_id = rs.getString("manager_name");
			PreparedStatement myStmt3 = c.prepareStatement("Update employee_details set manager_name = ? where manager_name = ?") ;
			myStmt3.setString(1,username);
			myStmt3.setString(2,manager_id);
			myStmt3.executeUpdate();
		}
		if(role.equals("admin")) {
			PreparedStatement myStmt3 = c.prepareStatement("Update employee_details set manager_name = ? where username = ?") ;
			myStmt3.setString(1,username);
			myStmt3.setString(2,username);
			myStmt3.executeUpdate();
		}
		PreparedStatement myStmt2 = c.prepareStatement("Update employee_details set userlevel = ? where username = ?");
		myStmt2.setString(1,role);
		
		myStmt2.setString(2,username);
		myStmt2.executeUpdate();
		
		
		
		
	}
	public void reject(String requester) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("Update role_requests set status = ? where requester = ?");
		myStmt1.setString(1,"rejected");
		myStmt1.setString(2,requester);
		myStmt1.executeUpdate();
	}
	public void acceptres(String requester,String resource) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("Update resource_requests set status = ? where requester = ?");
		myStmt1.setString(1,"accepted");
		myStmt1.setString(2,requester);
		myStmt1.executeUpdate();
		PreparedStatement myStmt2 = c.prepareStatement("Insert into resource_allocation (username,resource_allocated) values (?,?)");
		myStmt2.setString(1,requester);
		myStmt2.setString(2,resource);
		myStmt2.executeUpdate();
		
	}
	public void rejectres(String requester) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("Update resource_requests set status = ? where requester = ?");
		myStmt1.setString(1,"rejected");
		myStmt1.setString(2,requester);
		myStmt1.executeUpdate();
	}
	public void deletefromdb(String selectedvalue) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("delete from resource_names where resource_name = ?");
		myStmt1.setString(1,selectedvalue);
		myStmt1.executeUpdate();


	}
	public void deletefromuser(String username,String selectedvalue) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("delete from resource_allocation where username = ? AND resource_allocated = ? ");
		myStmt1.setString(1,username);
		myStmt1.setString(2,selectedvalue);
		myStmt1.executeUpdate();
	}
	public String reqnewresource(String uname) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt = c.prepareStatement("Select * from resource_allocation where username = ?") ;
		myStmt.setString(1,uname);
		ResultSet rs = myStmt.executeQuery();
		List<String> resources = new ArrayList<String>() ;
		while(rs.next()) {
			resources.add(rs.getString("resource_allocated"));
		}
		
		PreparedStatement myStmt2 = c.prepareStatement("Select * from resource_names") ;
		ResultSet rs1 = myStmt2.executeQuery();
		String r = "";
		while(rs1.next()) {
			String resource_allocated = rs1.getString("resource_name");
			if(!resources.contains(resource_allocated)) {
				r = r+" "+resource_allocated;
			}
		}
		return r;
	}
	public void add_resource_request(String selectedvalue,String uname) throws SQLException {
		PreparedStatement myStmt1 = c.prepareStatement("Insert into resource_requests (requester,resource_requested,approver,status) values (?,?,?,?)");
		myStmt1.setString(1,uname);
		myStmt1.setString(2,selectedvalue);
		myStmt1.setString(3,"admin");
		myStmt1.setString(4,"pending");
		myStmt1.executeUpdate();
	}
	public void delete_self_resource(String selectedvalue, String uname) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStmt1 = c.prepareStatement("delete from resource_allocation where username = ? AND resource_allocated = ?");
		myStmt1.setString(1,uname);
		myStmt1.setString(2,selectedvalue);
		myStmt1.executeUpdate();
	}	

}
