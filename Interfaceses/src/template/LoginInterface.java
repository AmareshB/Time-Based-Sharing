package template;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginInterface extends Remote {
	public int loginCheck(String name,String pass)throws RemoteException;
}
