package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;

public class Databass {
private Connection con;
private DbxClient cli;
private static Databass d=new Databass();
private Databass()
{
	try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud","root","amar");
		/*if(con!=null)
			System.out.println("null");*/
		String access="7Et32BCON-sAAAAAAAAABbyAJu5xXaCwAQV-Fv6LpsnfLXk3TBFAvNCECphorcLT";
		DbxRequestConfig config = new DbxRequestConfig(
	            "JavaTutorial/1.0", Locale.getDefault().toString());
		cli=new DbxClient(config,access);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static Connection getConnect()
{
	return d.con;
}
public static DbxClient getDbx()
{
	return d.cli;
}
}
