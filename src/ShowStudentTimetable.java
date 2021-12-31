

import java.awt.GridLayout;
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

public class ShowStudentTimetable extends JFrame   {

	JScrollPane scroll,timescroll;
	JButton btnShow;
	JPanel primary ,btnPanel;
	JFrame frame;
	JTable table,timetable;
	DefaultTableModel model,timemodel;
	String tableName[] = {"강의ID", "과목ID","교수이름","강의이름","요일","교시",
			"취득학점","강의시간","학과","장소 "};
	String timetableName[]= {"요일", "월","화","수","목","금"};
	String query,query1;
	String sID, year,semester;
	String[] [] data;
	int cnt;
	
	
	// DB 부분
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	 
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public ShowStudentTimetable(String sID, String year, String semester) {
		super("ShowStudentTimetable");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.sID=sID;
		this.year=year;
		this.semester=semester;
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
		
		setBounds(400, 200, 900, 600);	//창크기

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
		String lecture[] = {"강의ID", "과목ID","교수이름","강의이름","요일","교시","취득학점","강의시간","학과","장소 "};
		
		
		
		//초기 테이블 설정
		System.out.println("해당 강의목록을 부여줍니다");
		Table_Name=lecture;		
		model.setColumnIdentifiers(Table_Name); //테이블 모델의 '행'부분 정의
		model.getDataVector().removeAllElements();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		try {
			stmt = con.createStatement();
			
			
			//실제 데이터
			String query = "select * from lecture where lno in (select lno from coursehistory where stno="+sID+" and semester="+semester+" and cyear="+year+")";
			//여러개의 강의 lno 가 있을 수 있으니깐 in연산자 사용
			
			//실험 사용할거
			//String query = "select * from lecture where lno=(select lno from coursehistory where stno=1 and semester=1 and cyear=2021)";

			rs = stmt.executeQuery(query);
			while (rs.next()) {		//테이블의 각 데이터  정보 가져옴
				Object str[] = {rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getString(8),
						rs.getString(9),rs.getString(10)};
				model.addRow(str);	//그래서 '열'에 추가
			}
			
			//실험
			//String query1 ="SELECT COUNT(*) FROM lecture where lno=(select lno from coursehistory where pno=4 and semester=1 and cyear=2021)";
			//실제
			String query1 ="SELECT COUNT(*) FROM lecture where lno=(select lno from coursehistory where stno="+sID+" and semester="+semester+" and cyear="+year+")";
			
			
			rs = stmt.executeQuery(query1);
			while (rs.next()) {		
				
				cnt=rs.getInt(1);		//행 개수 구하기
				
			}
			
			
			
		} catch (Exception e4) {		//예외 발생시 예외 처리
			System.out.println("쿼리 읽기 실패 :" + e4);
		}
		
		showTimetable();
		
	}
	
	
	public void showTimetable() {
		
		String day[] = {"교시", "월","화","수","목","금" };
	
		String d="";
		
		data = new String[9][6];		
		
		//교시 설정
		data[0][0]="1교시";
		data[1][0]="2교시";
		data[2][0]="3교시";
		data[3][0]="4교시";
		data[4][0]="5교시";
		data[5][0]="6교시";
		data[6][0]="7교시";
		data[7][0]="8교시";
		data[8][0]="9교시";
		  
		String lname, dname, lroom;
		//이 table은 lecture을 말하는 것임
		
		
		for(int i=0;i<cnt;i++) {		//튜플 개수 만큼 바꿔야 함
			d=table.getModel().getValueAt(i, 4).toString();
			
			System.out.println(d+"555533333333355555");
			System.out.println(day[1]+"55555555555555");
			
			if(d.equals(day[1])) {		//월

				int lperiod=Integer.parseInt(table.getModel().getValueAt(i, 5 ).toString());	//교시
				int ltime=Integer.parseInt(table.getModel().getValueAt(i, 7 ).toString());	//강의시간
				
				System.out.println(lperiod+"교시");
				System.out.println(ltime+"강의시간");
				lname=  table.getModel().getValueAt(i, 3 ).toString();
				dname= table.getModel().getValueAt(i, 8 ).toString();
				lroom= table.getModel().getValueAt(i, 9 ).toString();
				
				for(int j=0;j<ltime;j++) {
					data[lperiod+j-1][1]=lname+"/"+dname+"/"+lroom;
				}	
				
			}else if(d.equals(day[2])) {	//화

				int lperiod=Integer.parseInt(table.getModel().getValueAt(i, 5 ).toString());	//교시
				int ltime=Integer.parseInt(table.getModel().getValueAt(i, 7 ).toString());	//강의시간
				
				System.out.println(lperiod+"교시");
				System.out.println(ltime+"강의시간");
				lname=  table.getModel().getValueAt(i, 3 ).toString();
				dname= table.getModel().getValueAt(i, 8 ).toString();
				lroom= table.getModel().getValueAt(i, 9 ).toString();
				
				for(int j=0;j<ltime;j++) {
					data[lperiod+j-1][2]=lname+"/"+dname+"/"+lroom;
				}
			}else if(d.equals(day[3])) {//수

				int lperiod=Integer.parseInt(table.getModel().getValueAt(i, 5 ).toString());	//교시
				int ltime=Integer.parseInt(table.getModel().getValueAt(i, 7 ).toString());	//강의시간
				
				System.out.println(lperiod+"교시");
				System.out.println(ltime+"강의시간");
				lname= table.getModel().getValueAt(i, 3 ).toString();
				dname=table.getModel().getValueAt(i, 8 ).toString();
				lroom= table.getModel().getValueAt(i, 9 ).toString();
				
				for(int j=0;j<ltime;j++) {
					data[lperiod+j-1][3]=lname+"/"+dname+"/"+lroom;
				}
			}else if(d.equals(day[4])) { 		//목

				int lperiod=Integer.parseInt(table.getModel().getValueAt(i, 5 ).toString());	//교시
				int ltime=Integer.parseInt(table.getModel().getValueAt(i, 7 ).toString());	//강의시간
				
				System.out.println(ltime+"교시");
				System.out.println(lperiod+"강의시간");
				
				lname= table.getModel().getValueAt(i, 3 ).toString();
				dname=table.getModel().getValueAt(i, 8 ).toString();
				lroom= table.getModel().getValueAt(i, 9 ).toString();
				
				for(int j=0;j<ltime;j++) {
					//강의시간 만큼 반목
					data[lperiod+j-1][4]=lname+"/"+dname+"/"+lroom;
					
				}
			}else if(d.equals(day[5])) {		//금
				int lperiod=Integer.parseInt(table.getModel().getValueAt(i, 5 ).toString());	//교시
				int ltime=Integer.parseInt(table.getModel().getValueAt(i, 7 ).toString());	//강의시간
				
				System.out.println(lperiod+"교시");
				System.out.println(ltime+"강의시간");
				lname=  table.getModel().getValueAt(i, 3 ).toString();
				dname=table.getModel().getValueAt(i, 8 ).toString();
				lroom= table.getModel().getValueAt(i, 9 ).toString();
				
				for(int j=0;j<ltime;j++) {
					data[lperiod+j-1][5]=lname+"/"+dname+"/"+lroom;
				}
			}
		}
		
		timetable=new JTable(data,day);
		JScrollPane timepanel = new JScrollPane(timetable);
		 add("South",timepanel);
	}
}
	


