package template;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GuiInterface extends Remote {
public String[] GuiFile(String name)throws RemoteException;
public String[] sharedGuiFile(String name)throws RemoteException;
}
