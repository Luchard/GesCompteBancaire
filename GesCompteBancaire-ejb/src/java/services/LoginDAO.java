package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



public class LoginDAO {  

	public static boolean login(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		DBCon dbcon = new DBCon();
		try {
			con = dbcon.getConnection();
			ps = con.prepareStatement(
					"SELECT username, password FROM UserLogin WHERE username= ? and password= ? ");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{
				System.out.println(rs.getString("username"));
				return true;
			}
			else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"LoginDAO!",
						"Wrong password message test!"));
				return false;
			}
		} 
		catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Database Error",
					"Unable to connect database"));            
			System.out.println("Error in login() -->" + ex.getMessage());
			return false;
		} finally {
			dbcon.close(con);
		}
	}

	
}