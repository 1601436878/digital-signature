package sign;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ShowVerify extends JFrame {

	private JButton action ;
	private JLabel textLabel;
	private JLabel secLabel;
	private JLabel publicKeyLabel;
	private JTextArea textArea;
	private JTextArea secArea ;
	private JTextArea pubArea ;
	private JLabel result;

	
	public ShowVerify(){
		init();
	}

	public void init(){
		setLayout(null);

		textLabel = new JLabel("请输入原文:");
		secLabel = new JLabel("请输入数字签名:");
		publicKeyLabel = new JLabel("请输入公钥：");
		secArea = new JTextArea(40,30);
		pubArea = new JTextArea(40,30);
		textArea= new JTextArea(20,10);
		action = new JButton("生成");
		result = new JLabel("");
		
		textLabel.setBounds(10, 0, 200, 40);
		secLabel.setBounds(10, 80, 200, 40);
		publicKeyLabel.setBounds(10, 200, 200, 40);
		action.setBounds(200, 400, 100, 40);
		textArea.setBounds(18, 40, 300, 40);
		pubArea.setBounds(18, 240, 300, 140);
		secArea.setBounds(18, 120, 300, 80);
		result.setBounds(50, 400, 200, 40);
		result.setFont(new Font("微软雅黑",Font.BOLD,16));
		
		//自动换行
		textArea.setLineWrap(true);
		secArea.setLineWrap(true);
		pubArea.setLineWrap(true);
		
		//滚动条
		JScrollPane textScroll = new JScrollPane(textArea);
		JScrollPane secScroll = new JScrollPane(secArea);
		JScrollPane pubScroll = new JScrollPane(pubArea);
		
		textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		secScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		pubScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		
		textScroll.setBounds(18, 40, 300, 40);
		secScroll.setBounds(18, 120, 300, 80);
		pubScroll.setBounds(18, 240, 300, 140);
		
		action.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Signtest sign = new Signtest();
				String text = textArea.getText();
				String sectext = secArea.getText();
				String pubKeyStr = pubArea.getText();
				boolean flag = sign.verify(text, sectext, pubKeyStr);
				if(flag)	result.setText("签名成功");
				else  result.setText("签名失败");

			}
		});
		
		add(textLabel);
		add(secLabel);
		add(publicKeyLabel);
		add(action);
		add(textScroll);
		add(secScroll);
		add(pubScroll);
		add(result);
	}
	
	
	public static void main(String [] a){
		ShowVerify showVerify = new ShowVerify();
		
		showVerify.setBounds(10, 10, 350, 500);
		showVerify.setLocationByPlatform(true);
		showVerify.setResizable(false);
		showVerify.setVisible(true);
		showVerify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
