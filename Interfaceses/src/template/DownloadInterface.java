package template;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DownloadInterface extends Remote {
public byte[] downloadFile(String name,String file)throws RemoteException;
public byte[] downloadSharedFile(String name,String file)throws RemoteException;
}
