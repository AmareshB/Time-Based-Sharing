package implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import template.DownloadInterface;

public class Download extends UnicastRemoteObject implements DownloadInterface {

	public Download() throws RemoteException
	{
		super();
	}
	
	private static byte[] decrypt(byte[] inpBytes, PublicKey key,
		      String xform) throws Exception{
		    Cipher cipher = Cipher.getInstance(xform);
		    cipher.init(Cipher.DECRYPT_MODE, key);
		    return cipher.doFinal(inpBytes);
		  }
	
	@Override
	public byte[] downloadFile(String user,String file,File keyfi) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("key found");
		File fi=new File("C:\\Users\\Amaresh\\Documents\\Cloud\\"+user+"\\"+file);
		byte[] data=new byte[(int)fi.length()];
		try {
			
			 //String xform = "RSA/NONE/PKCS1PADDING";
			  String xform = "RSA";

			//String xform = "DES/CTR/NoPadding";
			InputStream in=new FileInputStream(fi);
			in.read(data);
			//get the file "pubk in keyfi"
			FileInputStream keyfis = new FileInputStream(keyfi);
			byte[] encKey = new byte[keyfis.available()];  
			keyfis.read(encKey);
            keyfis.close();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
       //     KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey =
            	    keyFactory.generatePublic(pubKeySpec);
            byte[] decBytes = decrypt(data, pubKey, xform);
            System.out.println("decrypted"+decBytes.toString());
			in.close();
			return decBytes;
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
