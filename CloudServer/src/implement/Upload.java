package implement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.Cipher;

import template.UploadInterface;

public class Upload extends UnicastRemoteObject implements UploadInterface {

	public Upload() throws RemoteException
	{
		super();
	}
	 private static byte[] encrypt(byte[] inpBytes, PrivateKey prvk,
		      String xform) throws Exception {
		    Cipher cipher = Cipher.getInstance(xform);
		    cipher.init(Cipher.ENCRYPT_MODE, prvk);
		    return cipher.doFinal(inpBytes);
		  }
	@Override
	public byte[] uploadFile(byte[] data,String name,String file,int size) throws RemoteException {
		// TODO Auto-generated method stub
		byte[] keyss=null;
	    try {
			OutputStream out=new FileOutputStream(new File("C:\\Users\\Amaresh\\Documents\\Cloud\\"+name+"\\"+file));
	        
			
			
			//encrypt here
			// String xform = "RSA/NONE/PKCS1PADDING";
			String xform = "RSA";  
			//String xform = "DES/CTR/NoPadding";
			// Generate a key-pair
			  KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); // Original
		//	KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA", "SUN");
		//	KeyPairGenerator kpg = KeyPairGenerator.getInstance("ElGamal");
			    kpg.initialize(1024); // 512 is the keysize.//try 1024 biit
		//	SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		//	kpg.initialize(1024, random);
			    KeyPair kp = kpg.genKeyPair();
			    PublicKey pubk = kp.getPublic();
			    PrivateKey prvk = kp.getPrivate();
			    
			   // Save the public key for future
			     keyss = pubk.getEncoded();
			/*    FileOutputStream keyfos = new FileOutputStream(new File("C:\\Users\\Bharath Babu\\Documents\\Project x\\Cloud\\"+name+"\\Keys"+"pubk"));
			    keyfos.write(keyss);
			    keyfos.close();*/
			   //encryption here
			    byte[] encBytes = encrypt(data, prvk, xform);
			System.out.println("Keypair generated");
	        out.write(encBytes, 0, encBytes.length);
	        out.close();
	       
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return keyss;
	}

}
