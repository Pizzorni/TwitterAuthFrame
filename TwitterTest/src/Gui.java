

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;



public class Gui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 42L;
	
	private int rows = 5;
	private int cols = 2;
	JTextField name, donation, message, phone, email;
	JButton confirm;

	public Gui(){

		super("Support Sommer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(rows, cols, 10, 10));
		
		panel.add(new JLabel("Name:"));
		JTextField name = new JTextField();
		panel.add(name);
		
		panel.add(new JLabel("Quantity:"));
		JTextField donation = new JTextField();
		panel.add(donation);
		
		panel.add(new JLabel("Message:"));
		JTextField message = new JTextField();
		panel.add(message);
		
		panel.add(new JLabel("Phone #:"));
		JTextField phone = new JTextField();
		panel.add(phone);
		
		panel.add(new JLabel("Email:"));
		JTextField email = new JTextField();
		panel.add(email);
		
		
		confirm = new JButton("ok");
		c.add(panel, BorderLayout.CENTER);
		c.add(confirm, BorderLayout.SOUTH);
		c.setVisible(true);
		setSize(400,400);
		setVisible(true);



		
 
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confirm){
			Donation myDonation = new Donation(name.getText(), donation.getText(), message.getText(), email.getText(), phone.getText());
			System.out.println(myDonation);
		}
		
		
	}
	
	


	}










