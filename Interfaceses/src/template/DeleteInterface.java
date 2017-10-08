package template;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DeleteInterface extends Remote {
public void fileDelete(String f,String name) throws RemoteException;
}
