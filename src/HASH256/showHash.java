package HASH256;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class showHash extends JFrame{
	private JTextField inputtext;
	private JButton action ;
	private JLabel resultLabel;
	private JTextArea result ;
	
	public showHash(){
		init();
	}
	
	public void init(){		
		setLayout(null);
		inputtext= new JTextField(10);
		action = new JButton("生成");
		resultLabel = new JLabel("生成的摘要:");
		result = new JTextArea(40,30);
		
		inputtext.setBounds(20, 10, 200, 30);
		action.setBounds(250, 10, 100, 30);
		result.setLineWrap(true);

		JScrollPane scroll= new JScrollPane(result);
		scroll.setBounds(20, 50, 350, 250);
		
		this.action.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				Hash256 hash = new Hash256();
				String data = inputtext.getText();
				
				String hashdata = new String(hash.Encrypt(data));
				result.setText(hashdata);
			}
		});

		
		add(action);
		add(inputtext);
		add(resultLabel);
		add(scroll);
	}
	
	public static void main(String[] args) {
		showHash show = new showHash();
		show.setBounds(100, 100, 400, 400);
		show.setVisible(true);
		show.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
