package sign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ShowSign extends JFrame {

	private JButton action ;
	private JLabel infoLabel ;
	private JLabel priLabel;
	private JLabel resultLabel ;
	private JTextArea priInputArea ;
	private JTextArea resultArea ;
	private JTextArea inputArea;
	
	public ShowSign(){
		init();
	}

	public void init(){
		setLayout(null);

		infoLabel = new JLabel("请输入待签名的信息:");
		priLabel = new JLabel("请输入私钥:");
		resultLabel = new JLabel("生成的数字签名：");
		priInputArea = new JTextArea(40,30);
		resultArea = new JTextArea(40,30);
		inputArea= new JTextArea(20,10);
		action = new JButton("生成");
		
		infoLabel.setBounds(10, 0, 200, 40);
		priLabel.setBounds(10, 80, 200, 40);
		resultLabel.setBounds(10, 200, 200, 40);
		action.setBounds(200, 400, 100, 40);
		inputArea.setBounds(18, 40, 300, 40);
		resultArea.setBounds(18, 240, 300, 80);
		priInputArea.setBounds(18, 120, 300, 80);
		
		//自动换行
		inputArea.setLineWrap(true);
		priInputArea.setLineWrap(true);
		resultArea.setLineWrap(true);
		
		//滚动条
		JScrollPane inputScroll = new JScrollPane(inputArea);
		JScrollPane priScroll = new JScrollPane(priInputArea);
		JScrollPane resultScroll = new JScrollPane(resultArea);
		
		inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		priScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		resultScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		
		inputScroll.setBounds(18, 40, 300, 40);
		priScroll.setBounds(18, 120, 300, 80);
		resultScroll.setBounds(18, 240, 300, 80);
		
		action.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String inputData = inputArea.getText();
				String priData = priInputArea.getText();
				Signtest sign = new Signtest();
				
				byte [] byte_result= sign.sign(inputData,priData);	//需要赞这里传入文本和私钥
				String result = new String (byte_result);
				
				resultArea.setText(result);
			}
		});
		
		add(infoLabel);
		add(priLabel);
		add(resultLabel);
		add(action);
		add(inputScroll);
		add(priScroll);
		add(resultScroll);
	}
	
	public static void main(String[] args) {
		ShowSign show = new ShowSign();

		show.setBounds(10, 10, 350, 500);
		show.setLocationByPlatform(true);
		show.setResizable(false);
		show.setVisible(true);
		show.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
}
