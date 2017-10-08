package template;

import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ShareInterface extends Remote {
public int ShareFile(ArrayList<String> user,String f,String name) throws RemoteException, IOException;
}
