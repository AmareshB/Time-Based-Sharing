package template;

import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

//import com.healthmarketscience.rmiio.RemoteInputStream;

public interface UploadInterface extends Remote {
public byte[] uploadFile(byte[] data,String name,String file,int size) throws RemoteException;
}
