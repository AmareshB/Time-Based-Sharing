package implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxStreamWriter;
import com.dropbox.core.DbxWriteMode;

import data.Databass;
import template.UploadInterface;

public class Upload extends UnicastRemoteObject implements UploadInterface {

	public Upload() throws RemoteException
	{
		super();
	}
	@Override
	public void uploadFile(byte[] data,String name,String file,int size) throws RemoteException {
		Connection con=Databass.getConnect();
		DbxClient cli=Databass.getDbx();
		PreparedStatement pst;
		try {
			cli.uploadFile("/"+name+"/"+file,DbxWriteMode.add(),data.length,new DbxStreamWriter.ByteArrayCopier(data,0,data.length));
			pst=con.prepareStatement("insert into files value(?,?)");
			pst.setString(1,name);
			pst.setString(2,file);
			pst.execute();
		} catch (DbxException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
