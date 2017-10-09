package implement;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import template.GuiInterface;

public class Gui extends UnicastRemoteObject implements GuiInterface {

	public Gui() throws RemoteException
	{
		super();
	}
	@Override
	public File GuiFile(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return new File("C:\\Users\\Amaresh\\Documents\\Cloud\\"+name);
	}

}
