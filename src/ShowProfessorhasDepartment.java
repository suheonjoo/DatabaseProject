

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ShowProfessorhasDepartment extends JFrame  {

	
	
	JPanel primary ,btnPanel;
	JFrame frame;
	JTable table;
	DefaultTableModel model;
	String tableName[] = {"학과ID", "학과 이름","학과 전화번호", "학과실"};
	String query;
	Object pID;
	
	// DB 부분
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	 
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public ShowProfessorhasDepartment(Object pID) {
		super("ShowProfessorhasDepartment");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pID=pID;		
		layInit();
		conDB();
		
		this.invalidate();
		this.validate();
		this.setResizable(false);
		setVisible(true);
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
		
		setLayout(new GridLayout(1, 1,10,10));
		
		setBounds(300, 200, 600, 300);	//창크기
		
		
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

		
		}
	
	public void showTable(){		//테이블에 보여줄화면 정의
		String Table_Name[] = null;		
		String Student[] = {"학과ID", "학과 이름","학과 전화번호", "학과실"};
		
		//초기 테이블 설정
		System.out.println("학과 정보를 보여줍니다.");
		Table_Name=Student;		
		model.setColumnIdentifiers(Table_Name); //테이블 모델의 '행'부분 정의
		model.getDataVector().removeAllElements();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		try {
			stmt = con.createStatement();
			
			//실험 데이터
			//String query = "select * from coursehistory where stno=4";
			
			//실제 사용할거
			String query = "select * from department where dno in (select dno from professor_has_department where pno="+pID+")";

			rs = stmt.executeQuery(query);
			while (rs.next()) {		//테이블의 각 데이터  정보 가져옴
				Object str[] = {rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4)};
				model.addRow(str);	//그래서 '열'에 추가
			}
		} catch (Exception e4) {		//예외 발생시 예외 처리
			System.out.println("쿼리 읽기 실패 :" + e4);
		}
		
	}

	
}