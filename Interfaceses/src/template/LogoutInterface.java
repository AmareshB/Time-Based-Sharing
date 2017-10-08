package template;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogoutInterface extends Remote {
public int logoutUser(String name) throws RemoteException;
}
