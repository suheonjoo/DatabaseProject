

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdateGrade extends JFrame implements ActionListener  {

	JButton btnShow,btnCancel;
	JTextField attendance,midterm,fina, extra,total,grade;
	JPanel primary ,btnPanel;
	String a,m,f,ex,t,g;
	int intt;
	
	
	// DB 부분
		Connection con;
		Statement stmt;
		ResultSet rs;
		String Driver = "";
		String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
		String userid = "madang";
		String pwd = "madang";
		
	Object stno,lno;

		public void conDB() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("드라이버 로드 성공");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("DB 연결 준비");
				con = DriverManager.getConnection(url, userid, pwd);
				System.out.println("DB 연결 성공");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	public UpdateGrade(Object stno,Object lno) {
		super("UpdateGrade");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		conDB();
		layInit();
		this.stno=stno;
		this.lno=lno;
		
		this.invalidate();
		this.validate();
		this.setResizable(false);
		setVisible(true);
		
	}
	public void layInit() {

		primary = new JPanel();
		btnPanel = new JPanel();
		btnShow = new JButton("입력");
		btnShow.addActionListener(this);
		btnPanel.add(btnShow);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		btnPanel.add(btnCancel);
		
		setBounds(600, 200, 450, 500); // 가로위치,세로위치,가로길이,세로길이
		
		attendance = new JTextField(50);
		midterm = new JTextField(50);
		fina = new JTextField(50);		
		extra = new JTextField(50);	
		total = new JTextField(50);	
		grade = new JTextField(50);	

		attendance.setHorizontalAlignment(JLabel.CENTER);
		midterm.setHorizontalAlignment(JLabel.CENTER);
		fina.setHorizontalAlignment(JLabel.CENTER);
		extra.setHorizontalAlignment(JLabel.CENTER);
		total.setHorizontalAlignment(JLabel.CENTER);
		grade.setHorizontalAlignment(JLabel.CENTER);
		
		primary.add(new JLabel("출석 (정수로 입력하세요)"));
		primary.add(attendance);
		primary.add(new JLabel("중간성적 (정수로 입력하세요)"));
		primary.add(midterm);
		primary.add(new JLabel("기말성적 (정수로 입력하세요)"));
		primary.add(fina);
		primary.add(new JLabel("추가점수 (정수로 입력하세요)"));
		primary.add(extra);
		primary.add(new JLabel("총점=(0<=출석+중간성적+기말성적+추가점수<=100)"));
		primary.add(total);
		primary.add(new JLabel("평점 (A:90이상 B:80이상 C:70이상 D:60이상 F:60미만 중 하나로 입력하세요)"));
		primary.add(grade);
		

		primary.setLayout(new FlowLayout());
		primary.add(btnPanel);
		add(primary);
	}
	
	public void update() {
		
		
		
		System.out.println(a+" "+m+" "+f+" "+ex+" "+t+" "+g);
		
		try {
			String query="";
			stmt=con.createStatement();
			query="Update coursehistory set attendance="+a+", midterm="+m+", final="+f+", extra="+ex+",total="+t+", grade='"+g+"'  where stno="+stno+" and lno="+lno;
			
			stmt.executeUpdate(query);
			} catch (Exception e2) {
			System.out.println("쿼리 읽기 실패ee :" + e2);
			}
	}
	
	
	//@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object B = e.getSource();
		if (B == btnShow) {
			
			a=attendance.getText();
			m = midterm.getText();
			f= fina.getText();
			ex= extra.getText();
			t= total.getText();
			g= grade.getText();
						
			intt=Integer.parseInt(t);
			
			if(0<=intt & intt<=100) {	//총점 범위 예외처리
				update();
				new ShowUpdateGrade(stno,lno);
			}
			else {
				JOptionPane.showMessageDialog(null, "정확한 범위내에 총점을 입력하세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (e.getSource() == btnCancel) {
			this.dispose();
		}
		
	}

}
