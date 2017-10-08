package implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.Databass;
import template.LogoutInterface;

public class Logout extends UnicastRemoteObject implements LogoutInterface {

	public Logout() throws RemoteException
	{
		super();
	}
	@Override
	public int logoutUser(String name) throws RemoteException {
		// TODO Auto-generated method stub
		PreparedStatement stm;
		Connection con=Databass.getConnect();
		try {
			stm=con.prepareStatement("update user set stat=0 where name=? and stat=1");
			stm.setString(1, name);
			int i=stm.executeUpdate();
			if(i>0)
			return 1;
			else
				return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
