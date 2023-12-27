package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	static Connection connection = null;

	public static Connection getConnection()
	{
		String propstr=DBPropertyUtil.getPropertyString("./src/main/database.properties");
		connection=DBConnUtil.getConnection(propstr);
		return connection;
	}
}
