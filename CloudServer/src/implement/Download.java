package implement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

import data.Databass;
import template.DownloadInterface;

public class Download extends UnicastRemoteObject implements DownloadInterface {

	public Download() throws RemoteException
	{
		super();
	}
	@Override
	public byte[] downloadFile(String name,String file) throws RemoteException {
		// TODO Auto-generated method stub
		DbxClient cli=Databass.getDbx();
		byte[] data=null;
		try {
			File f=new File("C:\\Users\\bharathbabu\\Documents\\Project x\\Cloud\\temp\\"+file);
			FileOutputStream fout=new FileOutputStream(f);
			cli.getFile("/"+name+"/"+file,null,fout);
			FileInputStream fin=new FileInputStream(f);
			data=new byte[(int) f.length()];
			fin.read(data);
			fout.close();
			fin.close();
			f.delete();
		} catch (DbxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	@Override
	public byte[] downloadSharedFile(String name, String file)
			throws RemoteException {
		// TODO Auto-generated method stub
		String dir;
		ObjectInput in;
		PreparedStatement stm;
		Connection con;
		ResultSet rs;
		ObjectInputStream oin;
		ArrayList<ShareList> list;
		Iterator i;
		ShareList l = null;
		byte[] data=null;
		DbxClient cli=Databass.getDbx();
		ObjectOutput out = null;
		ByteArrayOutputStream bout1;
		ByteArrayInputStream bin1;
		try{
			con=Databass.getConnect();
			stm=con.prepareStatement("select shared from user where name=?");
			stm.setString(1,name);
			rs=stm.executeQuery();
			rs.next();
			in=new ObjectInputStream(rs.getBinaryStream(1));
			list=(ArrayList<ShareList>)in.readObject();
			i=list.iterator();
			while(i.hasNext())
			{
				l=(ShareList) i.next();
				if(l.fname.equals(file))
				{
					break;
				}
			}
			stm=con.prepareStatement("select * from files where name=? and fname=?");
			stm.setString(1,l.owner);
			stm.setString(2,l.fname);
			rs=stm.executeQuery();
			if(l!=null && rs.next())
			{
			dir="/"+l.owner+"/"+l.fname;
			File f=new File("C:\\Users\\Amaresh\\Documents\\Cloud\\temp\\"+file);
			FileOutputStream fout=new FileOutputStream(f);
			cli.getFile(dir,null,fout);
			FileInputStream fin=new FileInputStream(f);
			data=new byte[(int) f.length()];
			fin.read(data);
			fout.close();
			fin.close();
			f.delete();
			}
			else
			{
				i.remove();
				bout1=new ByteArrayOutputStream();
				out=new ObjectOutputStream(bout1);
				out.writeObject(list);
				bin1=new ByteArrayInputStream(bout1.toByteArray());
				stm=con.prepareStatement("update user set shared=? where name=?");
				stm.setBinaryStream(1,bin1,bout1.toByteArray().length);
				stm.setString(2,name);
				stm.executeUpdate();
				out.close();
				return null;
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
