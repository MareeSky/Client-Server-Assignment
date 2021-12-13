import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {
	// Text field for receiving weightInKilograms
	private JTextField jtf = new JTextField();
	
	// Text field for receiving heightInMeters
	private JTextField jtf2 = new JTextField();
	
	// Text BMI to display contents
	private JTextArea jta = new JTextArea();
	
	// IO streams
	private DataOutputStream outputToServer;
	private DataInputStream inputFromServer;
	
	public static void main(String[] args) {
		new Client();

	}
	
	public Client() {
		// Panel p to hold the label and text field for weight and height
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		
		p.add(new JLabel("Enter weight in kilograms and height in meters"), BorderLayout.NORTH);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		
		p.add(jtf2, BorderLayout.SOUTH);
		jtf2.setHorizontalAlignment(JTextField.RIGHT);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(jta), BorderLayout.CENTER);
		
		jtf.addActionListener(this); // Register listener
		jtf2.addActionListener(this); // Register listener
		
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!
		
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket("localhost", 8000);
			// Socket socket = new Socket("130.254.204.36", 8000);
			// Socket socket = new Socket("drake.Armstrong.edu", 8000);
			 
			// Create an input stream to receive data from the server
			inputFromServer = new DataInputStream(
					socket.getInputStream());
			
			// Create an output stream to send data to the server
			outputToServer =
					new DataOutputStream(socket.getOutputStream());
			}
			catch (IOException ex) {
				jta.append(ex.toString() + '\n');
			}
		}
	
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if(e.getSource() instanceof JTextField) {
				try {
					// Get the weightInKilograms from the text field
					double weightInKilograms = Double.parseDouble(jtf.getText().trim());
					
					// Get the heightInMeters from the text field
					double heightInMeters = Double.parseDouble(jtf2.getText().trim());
					
					// Send the weightInKilograms to the server
					outputToServer.writeDouble(weightInKilograms);
					outputToServer.writeDouble(heightInMeters);
					outputToServer.flush();
					
					//Get bmi from the server
					double bmi = inputFromServer.readDouble();
					
					// Display to the text area
					jta.append("Weight In Kilograms is: " + weightInKilograms + "\n");
					jta.append("Height In Meters is: " + heightInMeters + "\n");
					jta.append("BMI received from the server is: "
							+ bmi + '\n');
				}
				catch (IOException ex) {
					System.err.println(ex);
					
				}
			}
	}

}
