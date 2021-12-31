

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDeleteUpdate extends JFrame implements ActionListener {

	JPanel pn, pn1, pnBtn;
	JButton btnDelete, btnUpdate, btnCancel;
	JLabel lbCondi, lbChange, lbDeclare;
	JTextField txtCondi, txtChange;
	Object tables[] = { "Department", "Professor", "Student", "Lecture", "Circle", "Tuition", "CourseHistory",
			"Mentorship", "Student_has_Circle", "Professor_has_Department" };
	JComboBox combo;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	String beforename[];
	int count=0;
	
	public AdminDeleteUpdate() {
		super("관리자 삭제/변경");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		layInit();
		conDB();
		setBounds(500, 250, 400, 200);
		this.revalidate();
		setVisible(true);
	}

	public void layInit() {

		pnBtn = new JPanel();
		pn = new JPanel();
		pn.setLayout(new GridLayout(3, 2));

		combo = new JComboBox(tables);

		btnDelete = new JButton("삭제");
		btnUpdate = new JButton("변경");
		btnCancel = new JButton("취소");

		pnBtn.add(btnDelete);
		pnBtn.add(btnUpdate);
		pnBtn.add(btnCancel);

		setBounds(400, 200, 500, 400); // 가로위치,세로위치,가로길이,세로길이

		setLayout(new FlowLayout());

		txtCondi = new JTextField(15);
		txtChange = new JTextField(15);

		lbDeclare = new JLabel("UPDATE/DELETE (FROM)");
		lbChange = new JLabel("SET");
		lbCondi = new JLabel("WHERE");

		pn.add(lbDeclare);
		pn.add(combo);
		pn.add(lbCondi);
		pn.add(txtCondi);
		pn.add(lbChange);
		pn.add(txtChange);

		add(pn);
		add(pnBtn);

		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		// combo.addActionListener(this);
	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(url, userid, pwd);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		try { // SAFE UPDATES 해제
			stmt = con.createStatement();
			stmt.executeUpdate("SET SQL_SAFE_UPDATES=0");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "SAFE UPDATE 설정 실패.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
		}

		String query = combo.getSelectedItem().toString();
		String condi = txtCondi.getText().toString(), change = txtChange.getText().toString();
		
		if (e.getSource() == btnDelete) { // 삭제 버튼
			try {
				query = "delete from " + query + " where " + condi;

				//// 무결성 및 삭제 불가 조건
				if (combo.getSelectedItem() == tables[0]) {

					/*
					 * rs=stmt.
					 * executeQuery("select * from professor where dno in (select dno from department where "
					 * +condi+")"); if(rs.next()) { JOptionPane.showMessageDialog(null,
					 * "해당 학과에 속하는 교수가 있습니다!", "관리자 메세지", JOptionPane.ERROR_MESSAGE); return; }
					 */

					stmt.executeUpdate(
							"update student set mentorpname=null where mentorpname in (select pname from professor where dno=(select dno from department where "
									+ condi + "))");
					stmt.executeUpdate(
							"update circle set pname=null where pname in (select pname from professor where dno=(select dno from department where "
									+ condi + "))");
					stmt.executeUpdate(
							"update student set dname=null where dname in (select dname from department where " + condi
									+ ")");
					stmt.executeUpdate(
							"update student set subdname=null where subdname in (select dname from department where "
									+ condi + ")");
					stmt.executeUpdate(
							"delete from lecture where dname in (select dname from department where " + condi + ")");

				} else if (combo.getSelectedItem() == tables[1]) { // 교수는 학생을 반드시 담당해야 함

					rs = stmt.executeQuery(
							"select count(*) from professor where dno in (select dno from professor where " + condi
									+ ")");
					rs.next();
					if (rs.getInt(1) <= 1) {
						JOptionPane.showMessageDialog(null, "학과에는 학과장이 1명 있어야 합니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					stmt.executeUpdate(
							"update student set mentorpname=null where mentorpname in (select pname from professor where "
									+ condi + ")");
					stmt.executeUpdate(
							"delete from lecture where pname in (select pname from professor where " + condi + ")");
					stmt.executeUpdate("update Circle set pname=null where pname in (select pname from professor where "
							+ condi + ")");

				} else if (combo.getSelectedItem() == tables[2]) { // 교수는 학생을 반드시 담당해야 함

					rs = stmt.executeQuery(
							"select count(*) from student where mentorpname in (select mentorpname from student where "
									+ condi + ")");
					rs.next();
					if (rs.getInt(1) <= 1) {
						JOptionPane.showMessageDialog(null, "교수는 학생을 반드시 담당해야 합니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					stmt.executeUpdate(
							"update circle set chairman=null where chairman in (select stname from student where "
									+ condi + ")");

				} else if (combo.getSelectedItem() == tables[3]) { // 교수는 강좌에 대한 강의를 반드시 해야 함 + 학과는 개설하는 강좌가 1개 이상 존재해야함

					rs = stmt.executeQuery(
							"select count(*) from lecture where pname in (select pname from lecture where " + condi
									+ ")");
					System.out.println(
							"select count(*) from lecture where pname=(select pname from lecture where " + condi + ")");
					rs.next();
					if (rs.getInt(1) <= 1) {
						JOptionPane.showMessageDialog(null, "교수는 강좌에 대한 강의를 반드시 해야 함!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					rs = stmt.executeQuery(
							"select count(*) from lecture where dname in (select dname from lecture where " + condi
									+ ")");
					rs.next();
					if (rs.getInt(1) <= 1) {
						JOptionPane.showMessageDialog(null, "학과는 개설하는 강좌가 반드시 1개 이상 존재해야함!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

				} else if (combo.getSelectedItem() == tables[4]) { // 동아리 Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[5]) { // 동록금 Tuition 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[6]) { // 수강내역 CourseHistory 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[7]) { // 지도 관계 Mentorship 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[8]) { // 학생_동아리 Student_has_Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[9]) { // 교수_학과 Professor_has_Department 삭제/변경 시

				}

				System.out.println(query);
				stmt.executeUpdate(query);
				change = null;

				JOptionPane.showMessageDialog(null, "삭제 성공!", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "쿼리 읽기 실패.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnUpdate) { // 변경 버튼
			try {
				query = "update " + query + " set " + change + " where " + condi;
				
				if (combo.getSelectedItem() == tables[0]) { // 학과 Department 삭제/변경 시
					
					rs=stmt.executeQuery("select count(*) from department where "+condi);
					rs.next();
					count=rs.getInt(1);
					beforename=new String[count];
					
					rs=stmt.executeQuery("select dname from department where "+condi);
					rs.next();
					for(int i=0;i<count;i++) {
						beforename[i]=rs.getString(1);
					}
					
				} else if (combo.getSelectedItem() == tables[1]) { // 교수 Professor 삭제/변경 시
					
					rs=stmt.executeQuery("select count(*) from Professor where "+condi);
					rs.next();
					count=rs.getInt(1);
					beforename=new String[count];
					
					rs=stmt.executeQuery("select pname from Professor where "+condi);
					rs.next();
					for(int i=0;i<count;i++) {
						beforename[i]=rs.getString(1);
					}
					
				} else if (combo.getSelectedItem() == tables[2]) { // 학생 Student 삭제/변경 시
					
					rs=stmt.executeQuery("select count(*) from Student where "+condi);
					rs.next();
					count=rs.getInt(1);
					beforename=new String[count];
					
					rs=stmt.executeQuery("select stname from Student where "+condi);
					rs.next();
					for(int i=0;i<count;i++) {
						beforename[i]=rs.getString(1);
					}
					
				} else if (combo.getSelectedItem() == tables[3]) { // 강좌 Lecture 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[4]) { // 동아리 Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[5]) { // 동록금 Tuition 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[6]) { // 수강내역 CourseHistory 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[7]) { // 지도 관계 Mentorship 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[8]) { // 학생_동아리 Student_has_Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[9]) { // 교수_학과 Professor_has_Department 삭제/변경 시

				}
				
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "변경 성공!", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);

				//// 무결성 조건
				if (combo.getSelectedItem() == tables[0]) { // 학과 Department 삭제/변경 시
					
					for(int i=0;i<count;i++) {
						stmt.executeUpdate("update student set dname=(select dname from department where "+condi+") where dname = '"+beforename[i]+"'");
						stmt.executeUpdate("update student set subdname=(select dname from department where "+condi+") where subdname = '"+beforename[i]+"'");
						stmt.executeUpdate("update lecture set dname=(select dname from department where "+condi+") where dname = '"+beforename[i]+"'");
					}
					
				} else if (combo.getSelectedItem() == tables[1]) { // 교수 Professor 삭제/변경 시
					
					for(int i=0;i<count;i++) {
						stmt.executeUpdate("update student set mentorpname=(select pname from professor where "+condi+") where mentorpname = '"+beforename[i]+"'");
						stmt.executeUpdate("update lecture set pname=(select pname from professor where "+condi+") where pname = '"+beforename[i]+"'");
						stmt.executeUpdate("update circle set pname=(select pname from professor where "+condi+") where pname = '"+beforename[i]+"'");
					}
					
				} else if (combo.getSelectedItem() == tables[2]) { // 학생 Student 삭제/변경 시
					
					for(int i=0;i<count;i++) {
						stmt.executeUpdate("update circle set chairman=(select stname from student where "+condi+") where chairman = '"+beforename[i]+"'");
					}
					
				} else if (combo.getSelectedItem() == tables[3]) { // 강좌 Lecture 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[4]) { // 동아리 Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[5]) { // 동록금 Tuition 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[6]) { // 수강내역 CourseHistory 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[7]) { // 지도 관계 Mentorship 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[8]) { // 학생_동아리 Student_has_Circle 삭제/변경 시

				} else if (combo.getSelectedItem() == tables[9]) { // 교수_학과 Professor_has_Department 삭제/변경 시

				}

				this.dispose();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "쿼리 읽기 실패.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnCancel) { // 취소 버튼 클릭 시
			this.dispose();
		}

	}
}
