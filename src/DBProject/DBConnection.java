package DBProject;

import java.sql.* ;
public class DBConnection {
	private static final String url = "jdbc:mysql://localhost:3306/Langbase" ;
	private static final String userName =  "root";
	private static final String password = "Then@R2005";
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url,userName,password);
	}
}
