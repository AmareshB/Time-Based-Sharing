package serverpack;

import implement.Create;
import implement.Download;
import implement.Factory;
import implement.Gui;
import implement.Login;
import implement.Logout;
import implement.Upload;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       try {
    	int ch;
    	Scanner in=new Scanner(System.in);
		Registry r=LocateRegistry.createRegistry(1901);
		r.rebind("Factory", new Factory());
		r.rebind("Login", new Login());
		r.rebind("Create", new Create());
		r.rebind("Logout", new Logout());
		r.rebind("Upload", new Upload());
		r.rebind("Download", new Download());
		r.rebind("Gui", new Gui());
		System.out.print("Server is Started(press 0 to stop):");
		ch=in.nextInt();
		if(ch==0)
		{
			r.unbind("Factory");
			r.unbind("Login");
			r.unbind("Create");
			r.unbind("Logout");
			r.unbind("Upload");
			r.unbind("Download");
			r.unbind("Gui");
			UnicastRemoteObject.unexportObject(r,true);
			System.out.println("Stopped");
			System.exit(0);
		}
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NotBoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
	}

}
