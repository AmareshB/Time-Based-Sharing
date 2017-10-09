package template;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DownloadInterface extends Remote {
public byte[] downloadFile(String name,String file,File key_file)throws RemoteException;
}
