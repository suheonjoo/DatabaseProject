
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
import javax.swing.JTextField;

public class PCheckMentee extends JFrame implements ActionListener{

	JButton btnShow,btnCancel;
	JTextField professorName;
	JPanel primary ,btnPanel;
	int cnt=0;
	
	// DB 부분
		Connection con;
		Statement stmt;
		ResultSet rs;
		String Driver = "";
		String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
		String userid = "madang";
		String pwd = "madang";
		
		String pName;

	public PCheckMentee() {
		super("PCheckMentee");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		conDB();
		layInit();
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
		
		setBounds(600, 200, 300, 300); // 가로위치,세로위치,가로길이,세로길이
		
		professorName = new JTextField(30);

		professorName.setHorizontalAlignment(JLabel.CENTER);
		

		primary.add(new JLabel("교수이름"));
		primary.add(professorName);
		

		primary.setLayout(new FlowLayout());
		primary.add(btnPanel);
		add(primary);
		
	}

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
	
	public void isExist(){		
		
		try {
			stmt = con.createStatement();
			//실제
			String query1 ="select EXISTS (select * from professor where pname='"+pName+"') as success";
			System.out.println(pName);
			rs = stmt.executeQuery(query1);
			while (rs.next()) {	
				cnt=rs.getInt(1);		//행 개수 구하기
			}
			System.out.println(cnt);
			
		} catch (Exception e4) {		//예외 발생시 예외 처리
			System.out.println("쿼리 읽기 실패 :" + e4);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnShow) {
			
			pName = professorName.getText();
			
			isExist();		
			
			if(cnt==1) {		//pname가 존재하면
				System.out.println(cnt);
				new ShowProfessorMentee(pName);
			}else {		//존재하지 않으면
				JOptionPane.showMessageDialog(null, "정확한 교수이름를 입력하세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				
			}
			
			
		}
		if (e.getSource() == btnCancel) {
			this.dispose();
		}
		
	}
		
}