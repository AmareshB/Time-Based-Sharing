package implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import template.FatoryInterface;

public class Factory extends UnicastRemoteObject implements FatoryInterface {

	public Factory() throws RemoteException
	{
		super();
	}
	@Override
	public String returnLogin() throws RemoteException {
		// TODO Auto-generated method stub
		return "Login";
	}

	@Override
	public String returnCreate() throws RemoteException {
		// TODO Auto-generated method stub
		return "Create";
	}

	@Override
	public String returnUpload() throws RemoteException {
		// TODO Auto-generated method stub
		return "Upload";
	}

	@Override
	public String returnDownload() throws RemoteException {
		// TODO Auto-generated method stub
		return "Download";
	}
    public String returnGui() throws RemoteException{
    	return "Gui";
    }
    public String returnLogout() throws RemoteException{
    	return "Logout";
    }
}
