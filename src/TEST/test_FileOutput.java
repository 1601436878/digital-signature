package TEST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class test_FileOutput {
public static void main(String[] args) {
	try {
		FileOutputStream output = new FileOutputStream(new File("D:/KEY/1.txt"));
		ObjectOutputStream out = new ObjectOutputStream(output);
		String data = "hello world";
		byte [] s = data.getBytes();
		out.writeObject(data);
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File("D:/KEY/1.txt")));
		String datas = (String)input.readObject();
		System.out.println(datas);
		
		
		output.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
