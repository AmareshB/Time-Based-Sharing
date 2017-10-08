package implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;

import data.Databass;
import template.GuiInterface;

public class Gui extends UnicastRemoteObject implements GuiInterface {

	public Gui() throws RemoteException
	{
		super();
	}
	@Override
	public String[] GuiFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		String[] file=null;
		int count=0;
		Connection con=Databass.getConnect();
		PreparedStatement pst;
		try {
			//System.out.println("in1");
			pst = con.prepareStatement("select fname from files where name=?");
			pst.setString(1,name);
			ResultSet rs=pst.executeQuery();
			rs.last();
			file=new String[rs.getRow()];
			if(rs.first())
			{
				do{
				file[count++]=rs.getString(1);
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return file;
	}
	@Override
	public String[] sharedGuiFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		//System.out.println("in2");
		ObjectInput in = null;
		ArrayList<ShareList> list;
		Iterator i;
		String[] finame;
		ShareList l;
		Connection con=Databass.getConnect();
		PreparedStatement pst;
		ResultSet rs;
		int k=0;
		try {
			pst=con.prepareStatement("select shared from user where name=?");
			pst.setString(1,name);
			rs=pst.executeQuery();
			if(rs.next())
			in=new ObjectInputStream(rs.getBinaryStream(1));
			list=(ArrayList<ShareList>) in.readObject();
			finame=new String[list.size()];
			i=list.iterator();
			while(i.hasNext())
			{
				l=(ShareList) i.next();
				//System.out.println(l.fname);
				finame[k++]=l.fname;
			}
			//System.out.println(finame[0]);
			return finame;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
