package template;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreateInterface extends Remote {
	public int createUser(String name,String pass)throws RemoteException;
}
