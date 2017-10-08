package template;

import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UploadInterface extends Remote {
public void uploadFile(byte[] data,String name,String file,int size) throws RemoteException;
}
