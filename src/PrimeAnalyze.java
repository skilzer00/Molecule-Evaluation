import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.Button;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import java.io.PrintWriter;
import java.io.Writer;
import javax.swing.JLabel;
import java.awt.Color;

public class PrimeAnalyze {

	private JFrame frmPrimeanalyzer;
	private JTextField txtInputNumber;
	private static boolean isCanceled;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrimeAnalyze window = new PrimeAnalyze();
					window.frmPrimeanalyzer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PrimeAnalyze() {
		initialize();
	}
	
	/**
	 * Method to check prime.
	 */
	public static boolean checkifPrime(int n)
    {
        // Corner cases
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
  
        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;
  
        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
  
        return true;
    }
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrimeanalyzer = new JFrame();
		frmPrimeanalyzer.setBackground(Color.BLACK);
		frmPrimeanalyzer.setForeground(Color.BLACK);
		frmPrimeanalyzer.setTitle("PrimeAnalyzer");
		frmPrimeanalyzer.setBounds(100, 100, 449, 342);
		frmPrimeanalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPrimeanalyzer.getContentPane().setLayout(null);
		
		
		//Output
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 37, 223, 176);
		frmPrimeanalyzer.getContentPane().add(textArea);
		
		txtInputNumber = new JTextField();
		txtInputNumber.setEditable(false);
		txtInputNumber.setText("Input Number :");
		txtInputNumber.setBounds(10, 11, 86, 20);
		frmPrimeanalyzer.getContentPane().add(txtInputNumber);
		txtInputNumber.setColumns(10);
		
		
		//Input
		TextField input = new TextField();
		input.setBounds(102, 11, 39, 20);
		frmPrimeanalyzer.getContentPane().add(input);
		
		
		
		//Check Button
		Button button = new Button("Check");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Store Value input from user into string
				String str = input.getText();
				//Convert string to int using parse
				int num = Integer.parseInt(str);
			
				
				if (checkifPrime(num)) {
					textArea.setText(textArea.getText() + num + " -PRIME\n");
					input.setText("");
				}
				else {
					textArea.setText(textArea.getText()+num+ " -COMPOSITE\n");
					input.setText("");
				}
				
			}
		});
		button.setBounds(161, 11, 70, 22);
		frmPrimeanalyzer.getContentPane().add(button);
		
		
		//Reset button
		Button reset = new Button("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				input.setText("");
			}
		});
		reset.setBounds(10, 219, 77, 22);
		frmPrimeanalyzer.getContentPane().add(reset);
		
		
		
		//Save Button
		Button save = new Button("Save File in C:/Temp");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 

			        String content = textArea.getText(); //saving input queries

			        File file = new File("C:/temp/inputFile.txt"); //making sure file does not exist

			        // if file does not exists, then we create it
			        if (!file.exists()) {   // checks whether the file is Exist or not
			            file.createNewFile(); 
			        }

			        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); // creating fileWriter object with the file
			        BufferedWriter bw = new BufferedWriter(fw); // creating bufferWriter which is used to write the content into the file
			        bw.write(content); // write method is used to write the given content into the file
			        bw.close(); // Closes to preven any IO exceptions. 

			        System.out.println("Queries have been Saved");

			    } catch (IOException e1) { // if any exception occurs it will catch
			        e1.printStackTrace();
			    }
				}
			}
		);
		save.setBounds(255, 139, 131, 20);
		frmPrimeanalyzer.getContentPane().add(save);
		
		
		//Multi-Thread for loading new file
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (!isCancelled()) {
				//Load Data From Text File
				BufferedReader br = null;
				String s;
				try {
					br = new BufferedReader(new FileReader("C:/temp/Numbers.txt"));
					while((s = br.readLine()) != null) {
						Thread.sleep(1000);
						int num = Integer.parseInt(s);

											
						if (checkifPrime(num)) {
							textArea.setText(textArea.getText() + num + " -PRIME\n");
						}
						else {
							textArea.setText(textArea.getText()+num+ " -COMPOSITE\n");
						}
					}
				}
					catch(Exception e1) {
						System.out.println(e1);
					}
					finally {
						try {
							br.close();
						}catch(Exception e1) {
							System.out.println(e1);
						}
					}
			}
				return null;
			}
		};
			
		//Load Button
		JButton btnNewButton = new JButton("Load File From C:/Temp");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.execute();
				
				}			
				
				
		});
		btnNewButton.setBounds(245, 109, 163, 23);
		frmPrimeanalyzer.getContentPane().add(btnNewButton);
		
		
		//Cancel the load code button
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
				
				}			
				
				
		});
		cancel.setBounds(156, 219, 77, 22);
		frmPrimeanalyzer.getContentPane().add(cancel);
		
		
	}
}
