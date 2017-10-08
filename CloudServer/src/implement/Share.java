package implement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import data.Databass;
import template.ShareInterface;

public class Share extends UnicastRemoteObject implements ShareInterface {

	public Share() throws RemoteException
	{
		super();
	}
	@Override
	public int ShareFile(ArrayList<String> user,String fi,String name) throws IOException {
		// TODO Auto-generated method stub
		ShareList l=new ShareList(name,fi);
		Iterator i=user.iterator();
		ArrayList<ShareList> list;
		ObjectInput in = null;
		ObjectOutput out = null;
		ByteArrayOutputStream bout1;
		ByteArrayInputStream bin1;
		Connection con=Databass.getConnect();
		PreparedStatement pst;
		ResultSet rs;
		while(i.hasNext())
		{
			String n=(String) i.next();
			try {
				pst=con.prepareStatement("select shared from user where name=?");
				pst.setString(1,n);
				rs=pst.executeQuery();
				if(rs.next())
				in=new ObjectInputStream(rs.getBinaryStream(1));
				list=(ArrayList<ShareList>) in.readObject();
				list.add(l);
				in.close();
				bout1=new ByteArrayOutputStream();
				out=new ObjectOutputStream(bout1);
				out.writeObject(list);
				bin1=new ByteArrayInputStream(bout1.toByteArray());
				pst=con.prepareStatement("update user set shared=? where name=?");
				pst.setBinaryStream(1,bin1,bout1.toByteArray().length);
				pst.setString(2,n);
				pst.executeUpdate();
				out.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(n);
 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}

}
