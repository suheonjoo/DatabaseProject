

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class AdminShowAllTables extends JPanel implements ActionListener {

	JFrame frame;
	JRadioButton rbtDepartment, rbtProfessor, rbtStudent, rbtLecture, rbtCircle, rbtTuition, rbtCourseHistory,
			rbtMentorship, rbtStuCir, rbtProDe;
	JPanel primary, pn, pn1;
	JButton btnPrevious;
	JTextArea txtResult;
	JTable table;
	DefaultTableModel model;

	Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public AdminShowAllTables(JFrame frame) {

		this.frame=frame;
		layInit();
		conDB();

		setBounds(350, 200, 700, 600);
		this.setVisible(true);
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

	public void layInit() {
		primary=new JPanel();
		
		model = new DefaultTableModel();
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(true); 
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane pane = new JScrollPane(table); 
		pane.setPreferredSize(new Dimension(700, 450));
		add(pane);

		rbtDepartment = new JRadioButton("학과");
		rbtProfessor = new JRadioButton("교수");
		rbtStudent = new JRadioButton("학생");
		rbtLecture = new JRadioButton("강좌");
		rbtCircle = new JRadioButton("동아리");
		rbtTuition = new JRadioButton("등록금 납부 내역");
		rbtCourseHistory = new JRadioButton("수강내역");
		rbtMentorship = new JRadioButton("지도 관계");
		rbtStuCir=new JRadioButton("동아리 가입");
		rbtProDe=new JRadioButton("학과 소속 교수");
		btnPrevious = new JButton("이전");

		ButtonGroup grp = new ButtonGroup();
		grp.add(rbtDepartment);
		grp.add(rbtProfessor);
		grp.add(rbtStudent);
		grp.add(rbtLecture);
		grp.add(rbtCircle);
		grp.add(rbtTuition);
		grp.add(rbtCourseHistory);
		grp.add(rbtMentorship);
		grp.add(rbtStuCir);
		grp.add(rbtProDe);

		pn = new JPanel();
		pn1 = new JPanel();
		pn.add(rbtDepartment);
		pn.add(rbtProfessor);
		pn.add(rbtStudent);
		pn.add(rbtLecture);
		pn.add(rbtCircle);
		pn.add(rbtTuition);
		pn.add(rbtCourseHistory);
		pn.add(rbtMentorship);
		pn.add(rbtStuCir);
		pn.add(rbtProDe);
		pn1.add(btnPrevious);

		pn.setLayout(new GridLayout(2,10));
		add(pn);
		add(pn1);

		rbtDepartment.addActionListener(this);
		rbtProfessor.addActionListener(this);
		rbtStudent.addActionListener(this);
		rbtLecture.addActionListener(this);
		rbtCircle.addActionListener(this);
		rbtTuition.addActionListener(this);
		rbtCourseHistory.addActionListener(this);
		rbtMentorship.addActionListener(this);
		rbtStuCir.addActionListener(this);
		rbtProDe.addActionListener(this);
		btnPrevious.addActionListener(this);
		
	}

	public void resizeColumnWidth(JTable table) { // 자동으로 column 크기 조정하고 글자 가운데로 정렬하는 함수
		final TableColumnModel columnModel = table.getColumnModel();
		DefaultTableCellRenderer celcenter;

		celcenter = new DefaultTableCellRenderer();
		celcenter.setHorizontalAlignment(JLabel.CENTER);
		for (int col = 0; col < table.getColumnCount(); col++) {
			int width = 50; // 최소 너비
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, col);
				Component comp = table.prepareRenderer(renderer, row, col);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(col).setPreferredWidth(width);
			columnModel.getColumn(col).setCellRenderer(celcenter);
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == rbtDepartment) {
				String tableName[] = { "학과 번호", "학과이름", "학과 전화번호", "학과 사무실" };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Department"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) { 
						Object str[] = { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtProfessor) {
				String tableName[] = { "교수 번호", "교수이름", "교수주소", "교수 전화번호", "교수 이메일", "소속학과 번호" };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Professor"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) { 
						Object str[] = { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtStudent) {
				String tableName[] = { "학생 번호", "학생이름", "학생주소", "학생 전화번호", "학생 이메일", "전공 학과", "지도 교수", "등록금 납부 계좌",
						"부전공" };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Student"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) { 
						Object str[] = { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtLecture) {
				String tableName[] = { "강좌 번호", "분반 번호", "강의 교수", "강좌 이름", "강의 요일", "강의 교시", "취득학점", "강좌 시간", "개설 학과",
						"강의실 정보" };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Lecture"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) { 
						Object str[] = { rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtCircle) {
				String tableName[] = { "동아리 번호", "동아리 이름", "소속 학생 숫자", "회장 학생 정보", "동아리 지도 교수 정보", "동아리방 정보", };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Circle"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5),
								rs.getString(6) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtTuition) {
				String tableName[] = { "학생 번호", "등록금 납부 연도", "등록금 납부 학기", "등록금 총액", "납부 총액", "마지막 납부일", };
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Tuition"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
								rs.getString(6) };
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtCourseHistory) {
				String tableName[] = { "수강내역 번호", "학생 번호", "교수 번호", "강좌 번호", "출석 점수", "중간 점수", "기말 점수", "기타 점수", "총점", "평점", "년도", "학기"};
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM CourseHistory";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
								rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10), rs.getInt(11), rs.getInt(12)};
						model.addRow(str);
					}
				} catch (Exception e4) {
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtMentorship) {
				String tableName[] = { "교수  번호", "학생  번호", "연도", "학기"};
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Mentorship"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)};
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			} else if (e.getSource() == rbtStuCir) {
				String tableName[] = { "학생 번호", "동아리 번호"};
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Student_has_Circle"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getInt(2)};
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			}else if (e.getSource() == rbtProDe) {
				String tableName[] = { "교수 번호", "학과 번호"};
				model.setColumnIdentifiers(tableName); 
				model.getDataVector().removeAllElements();
				table.setModel(model);
				try {
					stmt = con.createStatement();
					String query = "SELECT * FROM Professor_has_Department"; 
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						Object str[] = { rs.getInt(1), rs.getInt(2)};
						model.addRow(str); 
					}
				} catch (Exception e4) { 
					System.out.println("쿼리 읽기 실패 :" + e4);
				}
			}else if (e.getSource() == btnPrevious) { // 관리자 메뉴로 돌아가기
				frame.getContentPane().remove(this);
				frame.getContentPane().add(new AdminUI(frame));
				frame.revalidate();
			}
			resizeColumnWidth(table);
		} catch (Exception e2) {
			System.out.println("쿼리 읽기 실패 :" + e2);
		}

	}
}
