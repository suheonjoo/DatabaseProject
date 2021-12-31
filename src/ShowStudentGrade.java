

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ShowStudentGrade extends JFrame  {

	
	
	JPanel primary ,btnPanel;
	JFrame frame;
	JTable table;
	DefaultTableModel model;
	String tableName[] = {"수강정보ID", "학생ID","교수ID","강의ID","출석","중간성적",
			"기말성적","추가점수" ,"총점","Grade","년도","학기"};
	String query;
	String sID;
	int cnt;
	double total,lgrade;
	
	JTextField GPA;
	
	// DB 부분
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	 
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public ShowStudentGrade(String sID) {
		super("ShowStudentGrade");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.sID=sID;		
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
		
		primary = new JPanel();
		
		setLayout(new GridLayout(2, 1,10,10));
		
		setBounds(300, 200, 900, 400);	//창크기
		
		
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
		add("North",pane);

		
		}
	
	public void showTable(){		//테이블에 보여줄화면 정의
		String Table_Name[] = null;		
		String Course[] = { "수강정보ID", "학생ID","교수ID","강의ID","출석","중간성적",
				"기말성적","추가점수" ,"총점","Grade","년도","학기"};
		
		//초기 테이블 설정
		System.out.println("해당과목 학생정보를 보여줍니다.");
		Table_Name=Course;		
		model.setColumnIdentifiers(Table_Name); //테이블 모델의 '행'부분 정의
		model.getDataVector().removeAllElements();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		try {
			stmt = con.createStatement();
			
			//데베에 쿼리문 설정하고
			
			//실험 데이터
			//String query = "select * from coursehistory where stno=4";
			
			//실제 사용할거
			String query = "select * from coursehistory where stno="+sID;

			rs = stmt.executeQuery(query);
			while (rs.next()) {		//테이블의 각 데이터  정보 가져옴
				Object str[] = {rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),
						rs.getInt(9),rs.getString(10),rs.getInt(11),rs.getInt(12)};
				model.addRow(str);	//그래서 '열'에 추가
			}
			
			String query1 ="SELECT COUNT(*) FROM coursehistory where stno="+sID;
			
			rs = stmt.executeQuery(query1);
			while (rs.next()) {		
				cnt=rs.getInt(1);		//행 개수 구하기
			}
			
		} catch (Exception e4) {		//예외 발생시 예외 처리
			System.out.println("쿼리 읽기 실패 :" + e4);
		}
		
		computeGPA();
		
	}
	public void computeGPA() {
		
		String g="";
		String grade[] = {"A", "B","C","D","F" };	//a 4.5 b 4.0 c 3.0 d 2.0 f 1.0
		String lno="";
		double lgradef=0;
		double lgradefTotal=0;
		int i=0;
		
		total=0;
		
		System.out.println(cnt+":  수강과목 개수");
		
		
		//a 4.5 b 4.0 c 3.0 d 2.0 f 1.0
		for(i=0;i<cnt;i++) {
			g=table.getModel().getValueAt(i, 9).toString();	//성적계산
			lno=table.getModel().getValueAt(i, 3).toString();
			
			System.out.println(g);
			
			try {		//해당과목학점 구하기
				stmt = con.createStatement();
				String query1 ="select lgrade from lecture where lno="+lno;
				
				rs = stmt.executeQuery(query1);
				
				while (rs.next()) {		
					lgrade=rs.getInt(1);		//행 개수 구하기
				}
				lgradef=(double)lgrade;
				
				System.out.println(lgradef+":  수강과목 학점");
				lgradefTotal=lgradefTotal+lgradef;
				System.out.println(lgradefTotal+":  총 수강과목 학점");
				
			} catch (Exception e4) {		//예외 발생시 예외 처리
				System.out.println("쿼리 읽기 실패 :" + e4);
			}
			
			
			
			if(g.equals(grade[0])) {		//A
				total=total+ lgradef*4.5;
			}else if(g.equals(grade[1])){	//B			
				total=total+ lgradef*4.0;
				
			}else if(g.equals(grade[2])){	//C
				total=total+ lgradef*3.0;
				
			}else if(g.equals(grade[3])){	//D			
				total=total+ lgradef*2.0;
				
			}else if(g.equals(grade[4])){	//F
				total=total+ lgradef*1.0;
			}
			System.out.println(total+":  총 곱");
		}
		total=total/lgradefTotal;
		
		primary = new JPanel();
		primary.add(new JLabel("GPA: "+total));
		add("South",primary);
		
		
		
	}

	
}
