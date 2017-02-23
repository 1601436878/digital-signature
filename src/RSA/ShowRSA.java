package RSA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ShowRSA extends JFrame{
	
	private JButton action ;
	private JLabel priLabel ;
	private JLabel pubLabel ;
	private JTextArea privateKeyArea ;
	private JTextArea publicKeyArea ;
	
	public ShowRSA(){
		init();
	}
	
	public void init(){
		
		setLayout(null);
		action = new JButton("Éú³É");
		priLabel = new JLabel("Ë½Ô¿ :");
		pubLabel = new JLabel("¹«Ô¿:");
		privateKeyArea = new JTextArea(40,30);
		publicKeyArea = new JTextArea(40,30);
		
		action.setBounds(250, 520, 100, 30);

		pubLabel.setBounds(20, 10, 100, 30);
		priLabel.setBounds(20, 210, 100, 30);

		privateKeyArea.setLineWrap(true);
		publicKeyArea.setLineWrap(true);
		
		JScrollPane scroll1 = new JScrollPane(privateKeyArea);
		scroll1.setBounds(20, 250, 350, 250);
		JScrollPane scroll2 = new JScrollPane(publicKeyArea);
		scroll2.setBounds(20, 50, 350, 150);
		
		action.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RSA rsa = new RSA();
				privateKeyArea.setText(new String(rsa.getPrivateKeyByte()));
				publicKeyArea.setText(new String(rsa.getPublicKeyByte()));
			}
		});
		
		add(action);
		add(priLabel);
		add(pubLabel);
		add(scroll1);
		add(scroll2);
	}
	

	public static void main(String[] args) {
		ShowRSA show = new ShowRSA();
		show.setBounds(100, 100, 400, 600);
		show.setVisible(true);
		show.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
