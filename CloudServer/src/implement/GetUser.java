package implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Databass;
import template.GetUserInterface;

public class GetUser extends UnicastRemoteObject implements GetUserInterface {

	public GetUser() throws RemoteException
	{
		super();
	}
	@Override
	public ArrayList getUser() throws RemoteException {
		// TODO Auto-generated method stub
		Connection con=Databass.getConnect();
		ArrayList<String>user=new ArrayList();
		try {
			PreparedStatement pst=con.prepareStatement("Select name from user");
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				user.add(rs.getString(1));
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
