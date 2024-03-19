import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Functions.JHyperlink;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUI extends JFrame {
    
	private JPanel contentPane;
	private JTextField opWorkIn;
	private boolean isNumber(String input) {
		try {
			Double.parseDouble(input);
				return true;
			} catch(Exception e) {
				return false;
			}
	}
	private TheInitializer agency = new TheInitializer();
	public GUI() throws HeadlessException{
		///////////////////////////////
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 436);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hear me now, me name be Ali G representing this agen C ");
		lblNewLabel.setBounds(10, 0, 587, 30);
		lblNewLabel.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.MAGENTA);
		lblNewLabel.setForeground(Color.ORANGE);
		contentPane.add(lblNewLabel);
		
		JLabel lblAaaaaaaaiiiiiiiiiiiiiiiiiiii = new JLabel("AAAAAAAAIIIIIIIIIIIIIIIIIIII");
		lblAaaaaaaaiiiiiiiiiiiiiiiiiiii.setHorizontalAlignment(SwingConstants.CENTER);
		lblAaaaaaaaiiiiiiiiiiiiiiiiiiii.setForeground(Color.ORANGE);
		lblAaaaaaaaiiiiiiiiiiiiiiiiiiii.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 20));
		lblAaaaaaaaiiiiiiiiiiiiiiiiiiii.setBackground(Color.MAGENTA);
		lblAaaaaaaaiiiiiiiiiiiiiiiiiiii.setBounds(20, 30, 587, 30);
		contentPane.add(lblAaaaaaaaiiiiiiiiiiiiiiiiiiii);
		
		JLabel lblNewLabel_1 = new JLabel("here we be deciding the operators worktime: ");
		lblNewLabel_1.setForeground(new Color(255, 204, 0));
		lblNewLabel_1.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setBounds(10, 121, 327, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel execText = new JLabel("check this extra executives box if ya want more manpowa");
		execText.setForeground(new Color(255, 204, 0));
		execText.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 13));
		execText.setBounds(10, 188, 354, 30);
		contentPane.add(execText);
		
		JLabel missionAmountLabel = new JLabel("how many missions is we doing today?");
		missionAmountLabel.setForeground(new Color(255, 204, 0));
		missionAmountLabel.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 13));
		missionAmountLabel.setBounds(10, 257, 279, 14);
		contentPane.add(missionAmountLabel);
		
		opWorkIn = new JTextField();
		opWorkIn.setBounds(347, 127, 42, 20);
		contentPane.add(opWorkIn);
		opWorkIn.setColumns(10);
		
		
		JSpinner extraExecSpinner = new JSpinner();
		extraExecSpinner.setModel(new SpinnerNumberModel(0, 0, 8, 1));
		extraExecSpinner.setBounds(347, 194, 42, 20);
		contentPane.add(extraExecSpinner);
		
		JSpinner howManyMissions = new JSpinner();
		howManyMissions.setBounds(347, 255, 42, 20);
		contentPane.add(howManyMissions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 13));
		btnExit.setBounds(370, 334, 89, 23);
		contentPane.add(btnExit);
		
		JButton startButton = new JButton("Start");
		startButton.setFont(new Font("Segoe UI Semilight", Font.BOLD | Font.ITALIC, 13));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				////////////what needs to happen here
				int missionAmount = (int) howManyMissions.getValue();
				int extraExec = (int) extraExecSpinner.getValue();
				String operWorkTime = opWorkIn.getText();
				if(isNumber(operWorkTime) == false) {
					System.out.println("illegal number");
				}
				double opWorkT = Double.parseDouble(operWorkTime);
				agency.setMissionQuota(missionAmount);
				agency.setExecNumber(extraExec);
				agency.setOperatorWorktime(opWorkT);
				
				agency.startDay();
			}
		});
		startButton.setBounds(138, 334, 89, 23);
		contentPane.add(startButton);
		/// number check
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
