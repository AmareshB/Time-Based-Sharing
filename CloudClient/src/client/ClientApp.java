package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;

import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import template.CreateInterface;
import template.DownloadInterface;
import template.FatoryInterface;
import template.GuiInterface;
import template.LoginInterface;
import template.LogoutInterface;
import template.UploadInterface;
public class ClientApp {

	public Scanner in;
	FatoryInterface f;
	Registry r;
	String name,pass;
	private long copy;
	public ClientApp()
	{
		in=new Scanner(System.in);
		try {
			r=LocateRegistry.getRegistry("10.106.184.216", 1901);
			f=(FatoryInterface) r.lookup("Factory");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public File fileChooser(File fi)
	{
		JFileChooser j;
		if(fi==null)
		j=new JFileChooser();
		else
		j=new JFileChooser(fi);	
		int ch=j.showOpenDialog(null);
		if(ch==JFileChooser.APPROVE_OPTION)
		return j.getSelectedFile();
		else
			return null;
	}
	/*public void copyFile(InputStream i,OutputStream o)
	{
		byte[] b=new byte[100];
		try {
			while(i.read(b)>0)
			{
				o.write(b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	public void upload()
	{
		File fi;
		fi=fileChooser(null);
		try {
			InputStream in=new FileInputStream(fi);
			byte[] data=new byte[(int)fi.length()];
			int size=in.read(data);
			in.close();
			UploadInterface u=(UploadInterface) r.lookup(f.returnUpload());
			u.uploadFile(data,name,fi.getName(),size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void login()
	{
		try{
		LoginInterface l=(LoginInterface)r.lookup(f.returnLogin());
		System.out.print("Enter the Name:");
		name=in.next();
		System.out.print("Enter the Password:");
		pass=in.next();
		if(l.loginCheck(name, pass)==1)
			System.out.println("Logged in");
		else
			System.out.println("Not Logged in");
			}
			catch(Exception e)
			{
			System.out.println(e);
			}
	}
	public void create()
	{
		String name,pass;
		int temp;
		try{
		CreateInterface c=(CreateInterface)r.lookup(f.returnCreate());
		System.out.print("Enter the Name:");
		name=in.next();
		System.out.print("Enter the Password:");
		pass=in.next();
		temp=c.createUser(name, pass);
		if(temp==1)
			System.out.println("Created");
		if(temp==2)
			System.out.println("user already exist");
		if(temp==0)
			System.out.println("Server error");
			}
			catch(Exception e)
			{
			System.out.println(e);
			}
		
	}
	public void logout()
	{
		try {
			LogoutInterface l=(LogoutInterface) r.lookup(f.returnLogout());
			int i=l.logoutUser(name);
			if(i==1)
				System.out.println("Bye");
			else
				System.out.println("Internal error");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void download()
	{
		try {
			GuiInterface gui=(GuiInterface) r.lookup(f.returnGui());
			DownloadInterface down=(DownloadInterface) r.lookup(f.returnDownload());
			File fi=gui.GuiFile(name);
			File fii=fileChooser(fi);
			if(fii!=null)
			{
			byte[] data=down.downloadFile(name,fii.getName());
			OutputStream out=new FileOutputStream(new File("C:\\Users\\Bharath Babu\\Downloads\\"+fii.getName()));
			out.write(data, 0, data.length);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientApp t=new ClientApp();
		int choice=1;
        while(true)
        {
      	  System.out.println("1.Login\n2.Create\n3.Upload\n4.Download\n5.Logout\nEnter your choice:");
      	  choice=t.in.nextInt();
        switch(choice)
        {
        case 1:t.login();
               break;
        case 2:t.create();
               break;
        case 3:t.upload();
               break;
        case 4:t.download();
        	   break;
        case 5:t.logout();
        	   break;
        case 0:System.exit(0);
        }
        }
	}

}
