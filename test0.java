package test0;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;


public class test0 {
	// TODO Auto-generated method stub
	public static void main(String[] args) throws Exception {
		Security.addProvider(new  BouncyCastleFipsProvider());
		Utils_BC Utils_BC = new Utils_BC(1024); 
		AES_BC AES_BC = new AES_BC();
		MAC_BC mac = new MAC_BC(); 
		IvParameterSpec iv = AES_BC.generateIv();
		String password = "12345678";
		byte[] salt = AES_BC.createSalt(20);
		try {
			SecretKeySpec key = AES_BC.getKeyFromPassword(password, salt);
			
			 File file1 =new  File("G:\\test_project3\\data_encrip\\video1.jpg"); 
			 File file2 =new  File("G:\\test_project3\\data_encrip\\video2.encrypt"); 
			 File file3 =new  File("G:\\test_project3\\data_encrip\\video3.mac"); 
			 File file4 =new  File("G:\\test_project3\\data_encrip\\video4.prot"); 
			 
			 File file5 =new  File("G:\\test_project3\\data_encrip\\video5.macecrp");
			 File file6 =new  File("G:\\test_project3\\data_encrip\\video6.oecrp");
			 File file7 =new  File("G:\\test_project3\\data_encrip\\video7.jpg");
			 try {
				if (file2.createNewFile() && file3.createNewFile()&&file4.createNewFile()
						&&file5.createNewFile()&&file6.createNewFile()&&file7.createNewFile()) {
					    System.out.println("File created: " + file2.getName());
					  } else {
					    System.out.println("File already exists.");
					  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AES_BC.encriptFile(key, iv, file1, file2);
			System.out.println("done encrypt");

			mac.encryptFileWithHmac(key, file2, file3);
			
			System.out.println("encrypt mac done !");
			 boolean resutlCheck =  mac.checkHmac(file3, file2, key); 
			 System.out.println("check mac "+resutlCheck);
			System.out.println("done");	
			Utils_BC.createFileProtected(file2, file3, file4);
			 Utils_BC.encrypfileFromFileProtected(file4, file5, file6);
			 AES_BC.decryptFile(key, iv, file6, file7);
			System.out.println( mac.checkHmac(file5, file6, key));
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}