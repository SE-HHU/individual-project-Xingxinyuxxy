import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
public class Fouroperations {

	public static void main(String[] args)throws IOException {
		
		
		JFrame window = new Window();
		
		
		
    }
	public static class Window extends JFrame {
		int num;
	
		public Window() {
			this.setTitle("四则运算");
			this.setVisible(true);
			this.setSize(600, 400);
			JPanel container = new JPanel();
			this.setContentPane(container);
			JButton button = new JButton("确认");
			container.add(button);
			MyActionListener listener = new MyActionListener();
			button.addActionListener(listener);
			JLabel label = new JLabel("请输入您所需要的题目数量：");
			container.add(label);
			JTextField textField = new JTextField(10);
			container.add(textField);
			num = Integer.valueOf(textField.getText());
		}
		
		private class MyActionListener implements ActionListener{

			
			public void actionPerformed(ActionEvent e) {
				
				try {
					Random random = new Random();
					int number1,number2;
						File file1 = new File("Exercise.txt");
						File file2 = new File("Answers.txt");
						if(file1.exists()==false) {
							file1.createNewFile();
						}
						if(file2.exists()==false) {
							file2.createNewFile();
						}
						FileWriter fw1 = new FileWriter(file1);
						FileWriter fw2 = new FileWriter(file2);
						for(int i = 0;i<num;i++) {
							number1 = random.nextInt(101);
							number2 = (int)(Math.random()*201-100);
							int result;
							result = number1+number2;
							if(number2<0){
								number2 = -number2;
								fw1.write(number1+"-"+number2+"="+System.getProperty("line.separator"));
							}
							else{
								fw1.write(number1+"+"+number2+"="+System.getProperty("line.separator"));
							}	
							fw2.write(result+System.getProperty("line.separator"));
						}
						fw1.close();
						fw2.close();
					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				
			
			
		}
		
	}
	
	
}
}
