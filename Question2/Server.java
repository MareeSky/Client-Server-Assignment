import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Server extends JFrame {
	//Text area for displaying contents
	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args) {
		new Server();
	}
	public Server() {
		//Place text area on the frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
	
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!
	
		try {
			//Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server started at " + new Date() + '\n');
			int counter = 0;
			
			while(true) {
				counter++;
				// Listen for a connection request
				Socket socket = serverSocket.accept();
				
				System.out.println("Client No: " + counter + " started");
				
				//requesting separate thread
				ServerClientThread sct = new ServerClientThread(socket, counter); //send  the request to a separate thread
			    sct.start();
			}
		}
		catch(IOException ex) {
		      System.err.println(ex);
	    }
	}
	
	class ServerClientThread extends Thread{
	
		Socket socket;
		int clientNo;
		
		ServerClientThread(Socket inSocket, int counter){
			socket = inSocket;
			clientNo = counter;
		}
		
		public void begin() {	 
			try {
				// Create data input and output streams
				DataInputStream inputFromClient = new DataInputStream(
						socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(
						socket.getOutputStream());
			
				while(true) {
					//Receive weightInKilograms from the client
					double weightInKilograms = inputFromClient.readDouble();
					//Receive heightInMeters from the client
					double heightInMeters = inputFromClient.readDouble();
					//Compute BMI
					double BMI = weightInKilograms / (heightInMeters * heightInMeters);
				
					//send area back to the client
					outputToClient.writeDouble(BMI);
					
					jta.append("Weight In Kilograms received from client: " + weightInKilograms + '\n');
					jta.append("Height In Meters received from client: " + heightInMeters + "\n");
					jta.append("BMI found: " + BMI + '\n');
				}
			}
		catch (IOException ex) {
			System.err.println(ex);
		}
		finally {
			System.out.println("Client: "+ clientNo + " End.");
		}
		}
	}
}

