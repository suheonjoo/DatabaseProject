

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.lang.Object;

public class AdminUI extends JPanel implements ActionListener{
	JFrame frame;
	JPanel pn, pn1;
	JButton btnReset, btnInsert,btnDeleteUpdate, btnAllTables, btnPrevious;

	Connection con;
	Statement stmt;
	ResultSet rs;
	
	public AdminUI(JFrame frame) {
		super();
		this.frame=frame;
		layInit();
		
		setBounds(400,200,600,500);
		setVisible(true);
	}
	
	public void layInit() {
		
		btnReset=new JButton("초기화");
		btnInsert=new JButton("입력");
		btnDeleteUpdate=new JButton("삭제/변경");
		btnAllTables=new JButton("전체 테이블 보기");
		btnPrevious=new JButton("이전");
		
		pn=new JPanel();
		
		pn.add(btnReset);
		pn.add(btnInsert);
		pn.add(btnDeleteUpdate);
		pn.add(btnAllTables);
		pn.add(btnPrevious);

		add(pn);
		
		btnReset.addActionListener(this);
		btnInsert.addActionListener(this);
		btnDeleteUpdate.addActionListener(this);
		btnAllTables.addActionListener(this);
		btnPrevious.addActionListener(this);
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
	
	public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnReset) {	//	DB초기화 실행
				new AdminReset();
			}else if(e.getSource()==btnInsert) {	//	데이터 입력
				new AdminInsert();
			}else if(e.getSource()==btnDeleteUpdate){ // 데이터 삭제,수정
				new AdminDeleteUpdate();
			}
			else if(e.getSource()==btnAllTables){	//	전체 테이블 보기
				frame.getContentPane().remove(this);
				frame.getContentPane().add(new AdminShowAllTables(this.frame));
				frame.revalidate();
			}else {	//	btnPrevious
				frame.getContentPane().remove(this);
				frame.getContentPane().add(new ModeUI(frame));
				frame.revalidate();
			}
	}
}
