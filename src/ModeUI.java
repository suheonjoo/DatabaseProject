

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class ModeUI extends JPanel implements ActionListener {
	JFrame frame;
	JButton btnAdmin,btnProfessor,btnStudent;
	JPanel pn;
	JTextArea txtStatus;
	
	Connection con;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public ModeUI(JFrame frame) {
		super();
		this.frame=frame;
		layInit();
		conDB();
		setVisible(true);
	}
	
	public void conDB() {
	      try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         //System.out.println("드라이버 로드 성공");
	         txtStatus.append("드라이버 로드 성공...\n");
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	      }
	      
	      try { /* 데이터베이스를 연결하는 과정 */
	          //System.out.println("데이터베이스 연결 준비...");
	    	  txtStatus.append("데이터베이스 연결 준비...\n");
	          con = DriverManager.getConnection(url, userid, pwd);
	          //System.out.println("데이터베이스 연결 성공");
	          txtStatus.append("데이터베이스 연결 성공...\n");
	       } catch (SQLException e1) {
	          e1.printStackTrace();
	       }
	   }
	
	public void layInit() {
		setLayout(new GridLayout(2, 1));
		pn=new JPanel();
		
		btnAdmin=new JButton("관리자");
		btnProfessor=new JButton("교수");
		btnStudent=new JButton("학생");
		
		pn.add(btnAdmin);
		pn.add(btnProfessor);
		pn.add(btnStudent);
		
		btnAdmin.addActionListener(this);
		btnProfessor.addActionListener(this);
		btnStudent.addActionListener(this);
		pn.setBounds(300,300,600,300);
		add(pn);
		
		txtStatus=new JTextArea();
	      JScrollPane scroll = new JScrollPane(txtStatus);
		
		add(scroll);
	}
	
	
	
	public void actionPerformed(ActionEvent e){

		frame.getContentPane().remove(this);
		
		if(e.getSource()==btnAdmin) {

			frame.getContentPane().add(new AdminUI(this.frame));
			frame.revalidate();
            
		}
		else if(e.getSource()==btnProfessor) {

			frame.getContentPane().add(new ProfessorUI(this.frame));
			frame.invalidate();
            frame.validate();
			
		}else { // btnStudent 클릭 시

			frame.getContentPane().add(new StudentUI(this.frame));
			frame.invalidate();
            frame.validate();
			
		}
	}
}
