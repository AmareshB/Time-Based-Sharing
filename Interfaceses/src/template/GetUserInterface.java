package template;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GetUserInterface extends Remote {
public ArrayList getUser() throws RemoteException;
}
