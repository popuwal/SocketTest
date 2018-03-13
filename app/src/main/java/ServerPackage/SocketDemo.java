package ServerPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {
public static void main(String[] args) {
	 try {
		ServerSocket serverSocket = new ServerSocket(10089);
		String ip = serverSocket.getLocalSocketAddress().toString();
		System.out.println(ip);
		Socket socket=serverSocket.accept();
		System.out.println(socket.getLocalSocketAddress().toString()+" :  "+socket.getLocalAddress().toString());
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String s;
		StringBuilder stringBuffer = new StringBuilder();
		while((s = bufferedReader.readLine()) != null) {
			stringBuffer.append(s);
		}
		System.out.println("Get from the client is "+stringBuffer.toString()+" "+socket.getLocalAddress().toString());
		socket.shutdownInput();
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(("Server get request is: "+stringBuffer.toString()).getBytes());
		outputStream.flush();
		
		socket.shutdownOutput();
		outputStream.close();
		
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		
		socket.close();
		serverSocket.close();
	} catch (IOException e) {
	 	System.out.print(e.toString());
		e.printStackTrace();
	} 
}
}
