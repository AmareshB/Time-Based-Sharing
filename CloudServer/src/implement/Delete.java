package implement;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

import data.Databass;
import template.DeleteInterface;

public class Delete extends UnicastRemoteObject implements DeleteInterface {

	public Delete() throws RemoteException
	{
		super();
	}

	@Override
	public void fileDelete(String f,String name) throws RemoteException {
		// TODO Auto-generated method stub
		DbxClient cli=Databass.getDbx();
		Connection con=Databass.getConnect();
		PreparedStatement pst;
		ResultSet rs;
		try {
			cli.delete("/"+name+"/"+f);
			pst=con.prepareStatement("delete from files where name=? and fname=?");
			pst.setString(1,name);
			pst.setString(2,f);
			pst.execute();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
