package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

	public Connection getConnection() {
		Connection con = null;

		try {
			String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String dbURL = "jdbc:sqlserver://SERVER_NAME;database=DB_NAME;user=USER_NAME;password=PASSWORD";

			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbURL);
			System.out.println("DB Connecting");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Database.getConnection() Error -->" + e.getMessage());
		}
		return con;
	}
    public void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
    