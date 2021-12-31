

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ShowUpdateGrade extends JFrame   {

	
	JButton btnShow;
	JPanel primary ,btnPanel;
	JFrame frame;
	JTable table;
	DefaultTableModel model;
	String tableName[] = {"수강정보ID", "학생ID","교수ID","강의ID","출석","중간성적",
			"기말성적","추가점수" ,"총점","Grade","년도","학기"};
	String query;
	Object stno, lno;
	
	
	// DB 부분
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	 
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public ShowUpdateGrade(Object stno, Object lno) {
		super("ShowUpdateGrade");
		//this.frame = frame;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.stno=stno;
		this.lno=lno;
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
		setLayout(new GridLayout(2, 1));		//2행 1열의 크기의 프레임을 만든다
		
		setBounds(600, 200, 900, 400);	//창크기
		
		btnPanel = new JPanel();
		
		model = new DefaultTableModel(tableName, 0) {		//JTable 사용해서 테이블 표시해 줌
			public boolean isCellEditable(int row, int column)
		    {
		      return false;//This causes all cells to be not editable
		    }
		};
		table = new JTable(model);		//JTable 생성
		table.getTableHeader().setReorderingAllowed(false);		
		table.setSelectionModel(new ForcedListSelectionModel());
		
		JScrollPane pane = new JScrollPane(table);		//테이블에 스크롤 생성및 추가
		add("North",pane);

		
		
		}
	
	public void showTable(){		//테이블에 보여줄화면 정의
		String Table_Name[] = null;		
		String lecture[] = {"수강정보ID", "학생ID","교수ID","강의ID","출석","중간성적",
				"기말성적","추가점수" ,"총점","Grade","년도","학기" };
		
		//초기 테이블 설정
		System.out.println("수정된 학생의 성적을 보여줍니다.");
		Table_Name=lecture;		
		model.setColumnIdentifiers(Table_Name); //테이블 모델의 '행'부분 정의
		model.getDataVector().removeAllElements();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		try {
			stmt = con.createStatement();
			
			//데베에 쿼리문 설정하고
			System.out.print(stno);
			System.out.print(lno);
			
			String query = "select * from coursehistory where stno="+stno+" and lno="+lno;
			
			
			rs = stmt.executeQuery(query);
			while (rs.next()) {		//테이블의 각 데이터  정보 가져옴
				Object str[] = {rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),
						rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getInt(12)};
				model.addRow(str);	//그래서 '열'에 추가
			}
		} catch (Exception e4) {		//예외 발생시 예외 처리
			System.out.println("쿼리 읽기 실패 :" + e4);
		}
		
	}

	
	
}

