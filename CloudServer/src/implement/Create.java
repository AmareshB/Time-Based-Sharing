package implement;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.Databass;
import template.CreateInterface;

public class Create extends UnicastRemoteObject implements CreateInterface {
public Create() throws RemoteException
{
	super();
}

@Override
public int createUser(String name, String pass) throws RemoteException {
	// TODO Auto-generated method stub
	try {
		Connection con=Databass.getConnect();
		PreparedStatement pst=con.prepareStatement("insert into user value(?,?,0)");
		pst.setString(1,name);
		pst.setString(2,pass);
		if(pst.executeUpdate()>0)
		{
			String dir="C:\\Users\\Amaresh\\Documents\\Cloud\\";
			File f=new File(dir+name);
			File s=new File(dir+name+"\\Shared");
			if(f.exists())
			{
			return 0;
			}
			else
			{
			f.mkdir();
			s.mkdir();
			}
			return 1;	
		}
		else
			return 0;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 2;
	}
}
}
