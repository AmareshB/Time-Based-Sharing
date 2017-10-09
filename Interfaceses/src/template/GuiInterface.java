package template;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GuiInterface extends Remote {
public File GuiFile(String name)throws RemoteException;
}
