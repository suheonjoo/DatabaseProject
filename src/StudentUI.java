

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class StudentUI extends JPanel implements ActionListener {

	JButton btnYearSemester,btnLecture, btnCircle, btnGrade,btnPrevious;
	
	
	
	JPanel  pn1, pn2, pn3;
	JFrame frame;
	
	JButton btnYearSemesterShow, btnMenteeShow,btnDepartmentShow,btnLectureShow,btnInsertGradeShow;
	
	JTextField StudentID, YearSemesterID, LectureID;

	
	JTable table;
	JButton btn1, btn2;
	
	DefaultTableModel model;
	
	ButtonGroup radioGroup;
	
	String tableName[] = {"학생ID", "학생이름", "주소", "전화번호", "학생 이메일", "전공학과","담당교수","계좌","부전공"};
	
	String query;
	
	
	
	// DB 부분
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	 
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public StudentUI(JFrame frame) {
		super();
		this.frame = frame;
		layInit();
		conDB();
		this.setVisible(true);
		
		showTable();

	}

	public void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Admin드라이버 로드 성공");
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
	
	public class ForcedListSelectionModel extends DefaultListSelectionModel {

	    public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
	    }

	}
	
	public void layInit() {
		
		
		pn1= new JPanel();
		
		model = new DefaultTableModel(tableName, 0) {		//JTable 사용해서 테이블 표시해 줌
			public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		table = new JTable(model);		//JTable 생성
		table.getTableHeader().setReorderingAllowed(false);		//테이블 헤더 재정렬 가능하도록 허용
		table.setSelectionModel(new ForcedListSelectionModel());
		JScrollPane pane = new JScrollPane(table);		//테이블에 스크롤 생성및 추가
		add(pane);

		
		//각각 버튼을 생성하고
		btnYearSemester = new JButton("년도/학기");
		btnLecture = new JButton("강의 시간표");
		btnCircle = new JButton("동아리");
		btnGrade = new JButton("성적");
		btnPrevious=new JButton("이전");
				

				
		//버튼에 대한 이벤트를 넣는다
		btnYearSemester.addActionListener(this);
		btnLecture.addActionListener(this);
		btnCircle.addActionListener(this);
		btnGrade.addActionListener(this);
		btnPrevious.addActionListener(this);
				
		//패널에 버튼 추가
		pn1.add(btnYearSemester);	
		pn1.add(btnLecture);
		pn1.add(btnCircle);
		pn1.add(btnGrade);
		pn1.add(btnPrevious);
				
		add(pn1);
				
				
		}
	
	public void showTable(){		//테이블에 보여줄화면 정의
		String Table_Name[] = null;		
		String Student[] = { "학생ID", "학생이름", "주소", "전화번호", "학생 이메일", "전공학과","담당교수","계좌","부전공" };

		//초기 테이블 설정
		
			System.out.println("Student 정보를 보여줍니다.");
			Table_Name=Student;		
			model.setColumnIdentifiers(Table_Name); //테이블 모델의 '행'부분 정의
			model.getDataVector().removeAllElements();
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setModel(model);
			try {
				stmt = con.createStatement();
				String query = "select * from student";	//데베에 쿼리문 설정하고
				rs = stmt.executeQuery(query);
				while (rs.next()) {		//테이블의 각 데이터  정보 가져옴
					Object str[] = {rs.getInt(1), rs.getString(2) , rs.getString(3) , rs.getString(4), rs.getString(5),
							rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
					model.addRow(str);	//그래서 '열'에 추가
				}
			} catch (Exception e4) {		//예외 발생시 예외 처리
				System.out.println("쿼리 읽기 실패 :" + e4);
			}
		
		}
	

	
	public void actionPerformed(ActionEvent e) {		//버튼클릭시 이벤트 생성
		Object B = e.getSource();
		if (B == btnYearSemester) {
			new SCheckYearSemester();
		}
		if (B == btnLecture) {
			new SCheckLecture();
		}
		
		if (B == btnCircle) {
			new SCheckCircle();
		}
		
		if (B == btnGrade) {
			new SCheckGrade();
		}
		if (B == btnPrevious) {
			frame.getContentPane().remove(this);
			frame.getContentPane().add(new ModeUI(frame));
			frame.invalidate();
            frame.validate();			
		}
		
		
	}
	
	
	
}

