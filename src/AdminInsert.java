

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminInsert extends JFrame implements ActionListener {

	JPanel pn, pn1, pnBtn, pnRBn;
	JButton btnStore, btnCancel;
	JLabel lbError, txtLabel[];
	JTextField txtAttribute[];
	JRadioButton rbtDepartment, rbtProfessor, rbtStudent, rbtLecture, rbtCircle, rbtTuition, rbtCourseHistory, rbtStCir,
			rbtMentorship, rbtProDe;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	String strInput[];
	int radioState = 0;

	public AdminInsert() {

		super("관리자 입력");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		layInit();
		conDB();

		setBounds(450, 250, 550, 390);
		this.revalidate();
		setVisible(true);

	}

	public void layInit() {

		pn = new JPanel();
		pnBtn = new JPanel();
		pnRBn = new JPanel();
		pnRBn.setLayout(new GridLayout(3, 4));

		btnStore = new JButton("저장");
		btnStore.addActionListener(this);
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		pnBtn.add(btnStore);
		pnBtn.add(btnCancel);

		setBounds(400, 200, 550, 400); // 가로위치,세로위치,가로길이,세로길이

		txtLabel = new JLabel[12];
		txtAttribute = new JTextField[12];
		strInput = new String[12];

		ButtonGroup grp = new ButtonGroup();
		rbtDepartment = new JRadioButton("학과");
		rbtProfessor = new JRadioButton("교수");
		rbtStudent = new JRadioButton("학생");
		rbtLecture = new JRadioButton("강좌");
		rbtCircle = new JRadioButton("동아리");
		rbtTuition = new JRadioButton("등록금 납부 내역");
		rbtCourseHistory = new JRadioButton("수강내역");
		rbtMentorship = new JRadioButton("지도 관계");
		rbtStCir = new JRadioButton("학생 동아리 가입");
		rbtProDe = new JRadioButton("교수 복수학과 신청");
		grp.add(rbtDepartment);
		grp.add(rbtProfessor);
		grp.add(rbtStudent);
		grp.add(rbtLecture);
		grp.add(rbtCircle);
		grp.add(rbtTuition);
		grp.add(rbtCourseHistory);
		grp.add(rbtMentorship);
		grp.add(rbtStCir);
		grp.add(rbtProDe);
		pnRBn.add(rbtDepartment);
		pnRBn.add(rbtProfessor);
		pnRBn.add(rbtStudent);
		pnRBn.add(rbtLecture);
		pnRBn.add(rbtCircle);
		pnRBn.add(rbtTuition);
		pnRBn.add(rbtCourseHistory);
		pnRBn.add(rbtMentorship);
		pnRBn.add(rbtStCir);
		pnRBn.add(rbtProDe);
		rbtDepartment.addActionListener(this);
		rbtProfessor.addActionListener(this);
		rbtStudent.addActionListener(this);
		rbtLecture.addActionListener(this);
		rbtCircle.addActionListener(this);
		rbtTuition.addActionListener(this);
		rbtCourseHistory.addActionListener(this);
		rbtMentorship.addActionListener(this);
		rbtStCir.addActionListener(this);
		rbtProDe.addActionListener(this);

		setLayout(new FlowLayout());

		add(pnRBn);
		add(pn);
		add(pnBtn);

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
		int n;

		if (e.getSource() == rbtDepartment) { // 학과 라디오 클릭 시 학과 정보 입력
			radioState = 1;
			pn1 = new JPanel();
			n = 4;
			txtLabel[0] = new JLabel("학과 번호");
			txtLabel[1] = new JLabel("학과 이름");
			txtLabel[2] = new JLabel("학과 전화번호");
			txtLabel[3] = new JLabel("학과 사무실");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtProfessor) {// 교수 라디오 클릭 시 교수 정보 입력
			radioState = 2;
			pn1 = new JPanel();
			n = 6;
			txtLabel[0] = new JLabel("교수 번호");
			txtLabel[1] = new JLabel("교수 이름");
			txtLabel[2] = new JLabel("교수 주소");
			txtLabel[3] = new JLabel("교수 전화번호");
			txtLabel[4] = new JLabel("교수 이메일");
			txtLabel[5] = new JLabel("소속 학과번호");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtStudent) {// 학생 라디오 클릭 시 학생 정보 입력
			radioState = 3;
			pn1 = new JPanel();
			n = 9;
			txtLabel[0] = new JLabel("학생 번호");
			txtLabel[1] = new JLabel("학생 이름");
			txtLabel[2] = new JLabel("학생 주소");
			txtLabel[3] = new JLabel("학생 전화번호");
			txtLabel[4] = new JLabel("학생 이메일");
			txtLabel[5] = new JLabel("전공 학과");
			txtLabel[6] = new JLabel("지도 교수");
			txtLabel[7] = new JLabel("등록금 납부 계좌");
			txtLabel[8] = new JLabel("부전공(해당 학생에 한하여 입력)");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtLecture) {// 강좌 라디오 클릭 시 강좌 정보 입력
			radioState = 4;
			pn1 = new JPanel();
			n = 10;
			txtLabel[0] = new JLabel("강좌 번호");
			txtLabel[1] = new JLabel("분반번호");
			txtLabel[2] = new JLabel("강의 교수");
			txtLabel[3] = new JLabel("강좌 이름");
			txtLabel[4] = new JLabel("강의 요일 (월,화,수,목,금 택1)");
			txtLabel[5] = new JLabel("강의 교시 (1~8)");
			txtLabel[6] = new JLabel("취득학점 (1~4)");
			txtLabel[7] = new JLabel("강좌 시간 (1~6)");
			txtLabel[8] = new JLabel("개설 학과");
			txtLabel[9] = new JLabel("강의실 정보");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtCircle) { // 동아리 라디오 클릭 시 동아리 정보 입력
			radioState = 5;
			pn1 = new JPanel();
			n = 6;
			txtLabel[0] = new JLabel("동아리 번호");
			txtLabel[1] = new JLabel("동아리 이름");
			txtLabel[2] = new JLabel("소속 학생 숫자");
			txtLabel[3] = new JLabel("회장 학생 정보");
			txtLabel[4] = new JLabel("동아리 지도 교수 정보");
			txtLabel[5] = new JLabel("동아리방 정보");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtTuition) {// 등록금 납부 내역 라디오 클릭 시 등록금 납부 내역 정보 입력
			radioState = 6;
			pn1 = new JPanel();
			n = 6;
			txtLabel[0] = new JLabel("학생 번호");
			txtLabel[1] = new JLabel("등록금 납부 연도");
			txtLabel[2] = new JLabel("등록금 납부 학기");
			txtLabel[3] = new JLabel("등록금 총액");
			txtLabel[4] = new JLabel("납부 총액");
			txtLabel[5] = new JLabel("마지막 납부 일자");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtCourseHistory) {// 수강내역 라디오 클릭 시 수강내역 정보 입력
			radioState = 7;
			pn1 = new JPanel();
			n = 11;
			txtLabel[0] = new JLabel("수강내역 번호");
			txtLabel[1] = new JLabel("학생 번호");
			txtLabel[2] = new JLabel("교수 번호");
			txtLabel[3] = new JLabel("강의 번호");
			txtLabel[4] = new JLabel("출석 점수");
			txtLabel[5] = new JLabel("중간 점수");
			txtLabel[6] = new JLabel("기말 점수");
			txtLabel[7] = new JLabel("기타 점수");
			txtLabel[8] = new JLabel("연도");
			txtLabel[9] = new JLabel("학기");
			txtLabel[10] = new JLabel("평점(A~F) (생략가능)");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtMentorship) {// 지도 관계 라디오 클릭 시 지도 관계 정보 입력
			radioState = 8;
			pn1 = new JPanel();
			n = 4;
			txtLabel[0] = new JLabel("교수 번호");
			txtLabel[1] = new JLabel("학생 번호");
			txtLabel[2] = new JLabel("연도");
			txtLabel[3] = new JLabel("학기");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtStCir) {// 학생을 동아리에 가입시키는 테이블 입력
			radioState = 9;
			pn1 = new JPanel();
			n = 2;
			txtLabel[0] = new JLabel("학생 번호");
			txtLabel[1] = new JLabel("동아리 번호");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == rbtProDe) {// 교수_학과 테이블 입력
			radioState = 10;
			pn1 = new JPanel();
			n = 2;
			txtLabel[0] = new JLabel("교수 번호");
			txtLabel[1] = new JLabel("학과 번호");
			pn1.setLayout(new GridLayout(n, 2));
			for (int i = 0; i < n; i++) {
				pn1.add(txtLabel[i]);
				txtAttribute[i] = new JTextField(15);
				pn1.add(txtAttribute[i]);

			}
			pn.removeAll();
			pn.add(pn1);
			this.revalidate();
		} else if (e.getSource() == btnStore) { // 저장 버튼
			try {
				String query = "", query2 = "", tmp;
				stmt = con.createStatement();
				if (radioState == 0) { // RadioButton 선택 안했을 시 오류
					JOptionPane.showMessageDialog(null, "입력 테이블을 선택해주세요!", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
				} else if (radioState == 1) { // Department

					txtLabel[0].setText("학과 번호");
					txtLabel[1].setText("학과 이름");
					txtLabel[2].setText("학과 전화번호");
					txtLabel[3].setText("학과 사무실");

					query = "INSERT INTO Department VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "')";

					rs = stmt.executeQuery("select * from Department");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*학과 번호");
							this.revalidate();
							return;
						}
					}
				} else if (radioState == 2) { // Professor

					txtLabel[0].setText("교수 번호");
					txtLabel[1].setText("교수 이름");
					txtLabel[2].setText("교수 주소");
					txtLabel[3].setText("교수 전화번호");
					txtLabel[4].setText("교수 이메일");
					txtLabel[5].setText("소속 학과번호");

					query = "INSERT INTO Professor VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "','" + txtAttribute[5].getText().toString()
							+ "')";

					query2 = "INSERT INTO Professor_has_Department VALUES('" + txtAttribute[0].getText().toString()
							+ "','" + txtAttribute[5].getText().toString() + "')";

					rs = stmt.executeQuery("select * from Professor");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*교수 번호");
							return;
						}
					}
					rs = stmt.executeQuery(
							"select * from Department where dno='" + txtAttribute[5].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[5].setText("*소속 학과번호");
						return;
					}

					// 담당학생, 강좌에 대한 강의가 반드시 있어야 함

					stmt.executeUpdate(query);
					stmt.executeUpdate(query2);
					JOptionPane.showMessageDialog(null, "입력 성공!", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					return; // 두 번째 쿼리문을 실행시켜야 하기 때문에 종료
				} else if (radioState == 3) { // Student

					txtLabel[0].setText("학생 번호");
					txtLabel[1].setText("학생 이름");
					txtLabel[2].setText("학생 주소");
					txtLabel[3].setText("학생 전화번호");
					txtLabel[4].setText("학생 이메일");
					txtLabel[5].setText("전공 학과");
					txtLabel[6].setText("지도 교수");
					txtLabel[7].setText("등록금 납부 계좌");
					txtLabel[8].setText("부전공(해당 학생에 한하여 입력)");

					query = "INSERT INTO Student VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "','" + txtAttribute[5].getText().toString()
							+ "','" + txtAttribute[6].getText().toString() + "','"
							+ txtAttribute[7].getText().toString() + "','" + txtAttribute[8].getText().toString()
							+ "')";

					rs = stmt.executeQuery("select * from Student");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*학생 번호");
							return;
						}
					}

					tmp = "select * from Department where dname='" + txtAttribute[5].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[5].setText("*전공 학과");
						return;
					}

					tmp = "select * from Professor where pname='" + txtAttribute[6].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[6].setText("*지도 교수");
						return;
					}

					stmt.executeUpdate(query);

					rs = stmt.executeQuery(
							"select pno from professor where pname='" + txtAttribute[6].getText().toString() + "'");
					rs.next();
					stmt.executeUpdate("INSERT INTO Mentorship VALUES(" + txtAttribute[0].getText().toString() + ","
							+ rs.getInt(1) + ",2021,1)");

					JOptionPane.showMessageDialog(null, "입력 성공!", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
					return;

				} else if (radioState == 4) { // Lecture

					txtLabel[0].setText("강좌 번호");
					txtLabel[1].setText("분반번호");
					txtLabel[2].setText("강의 교수");
					txtLabel[3].setText("강좌 이름");
					txtLabel[4].setText("강의 요일 (월,화,수,목,금 택1)");
					txtLabel[5].setText("강의 교시 (1~8)");
					txtLabel[6].setText("취득학점 (1~4)");
					txtLabel[7].setText("강좌 시간 (1~6)");
					txtLabel[8].setText("개설 학과");
					txtLabel[9].setText("강의실 정보");

					query = "INSERT INTO Lecture VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "','" + txtAttribute[5].getText().toString()
							+ "','" + txtAttribute[6].getText().toString() + "','"
							+ txtAttribute[7].getText().toString() + "','" + txtAttribute[8].getText().toString()
							+ "','" + txtAttribute[9].getText().toString() + "')";

					rs = stmt.executeQuery("select * from Lecture");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*강좌 번호");
							return;
						}
					}

					tmp = "select * from Professor where pname='" + txtAttribute[2].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[2].setText("*강의 교수");
						return;
					}

					if (!(txtAttribute[4].getText().equals("월") || txtAttribute[4].getText().equals("화")
							|| txtAttribute[4].getText().equals("수") || txtAttribute[4].getText().equals("목")
							|| txtAttribute[4].getText().equals("금"))) {
						JOptionPane.showMessageDialog(null, "등록 가능한 요일이 아닙니다!", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[4].setText("*강의 요일 (월,화,수,목,금 택1)");
						return;
					}

					if (Integer.parseInt(txtAttribute[5].getText()) > 8
							|| Integer.parseInt(txtAttribute[5].getText()) < 1) {
						JOptionPane.showMessageDialog(null, "등록 가능한 강의 교시 범위를 벗어났습니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[5].setText("*강의 교시 (1~8)");
						return;
					}

					if (Integer.parseInt(txtAttribute[6].getText()) > 4
							|| Integer.parseInt(txtAttribute[6].getText()) < 1) {
						JOptionPane.showMessageDialog(null, "등록 가능한 취득 학점 범위를 벗어났습니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[6].setText("*취득학점 (1~4)");
						return;
					}

					if (Integer.parseInt(txtAttribute[7].getText()) > 6
							|| Integer.parseInt(txtAttribute[7].getText()) < 1) {
						JOptionPane.showMessageDialog(null, "등록 가능한 강좌 시간 범위를 벗어났습니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[7].setText("*강좌 시간 (1~6)");
						return;
					}

					tmp = "select * from Department where dname='" + txtAttribute[8].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[8].setText("*개설 학과");
						return;
					}

				} else if (radioState == 5) { // Circle

					txtLabel[0].setText("동아리 번호");
					txtLabel[1].setText("동아리 이름");
					txtLabel[2].setText("소속 학생 숫자");
					txtLabel[3].setText("회장 학생 정보");
					txtLabel[4].setText("동아리 지도 교수 정보");
					txtLabel[5].setText("동아리방 정보");

					query = "INSERT INTO Circle VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "','" + txtAttribute[5].getText().toString()
							+ "')";
					rs = stmt.executeQuery("select * from Circle");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*동아리 번호");
							return;
						}
					}

					tmp = "select * from Student where stname='" + txtAttribute[3].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[3].setText("*회장 학생 정보");
						return;
					}

					tmp = "select * from Professor where pname='" + txtAttribute[4].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 이름입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[4].setText("*동아리 지도 교수 정보");
						return;
					}

				} else if (radioState == 6) { // Tuition

					txtLabel[0].setText("학생 번호");
					txtLabel[1].setText("등록금 납부 연도");
					txtLabel[2].setText("등록금 납부 학기");
					txtLabel[3].setText("등록금 총액");
					txtLabel[4].setText("납부 총액");
					txtLabel[5].setText("마지막 납부 일자");

					query = "INSERT INTO Tuition VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "',STR_TO_DATE('"
							+ txtAttribute[5].getText().toString() + "','%Y-%m-%d')" + ")";
					rs = stmt.executeQuery("select * from Tuition");

					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "이미 등록된 학생의 등록금 납부 이력을 수정하려면 삭제/수정 기능을 이용해주세요!",
									"관리자 메세지", JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*학생 번호");
							return;
						}
					}

					tmp = "select * from student where stname='" + txtAttribute[0].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*학생 번호");
						return;
					}

					if (Integer.parseInt(txtAttribute[2].getText()) != 1
							&& Integer.parseInt(txtAttribute[2].getText()) != 2) {
						JOptionPane.showMessageDialog(null, "입력 가능한 학기는 1, 2 (학기)입니다.", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[2].setText("*등록금 납부 학기");
						return;
					}
				} else if (radioState == 7) { // CourseHistory

					txtLabel[0].setText("수강내역 번호");
					txtLabel[1].setText("학생 번호");
					txtLabel[2].setText("교수 번호");
					txtLabel[3].setText("강의 번호");
					txtLabel[4].setText("출석 점수");
					txtLabel[5].setText("중간 점수");
					txtLabel[6].setText("기말 점수");
					txtLabel[7].setText("기타 점수");
					txtLabel[8].setText("연도");
					txtLabel[9].setText("학기");
					txtLabel[10].setText("평점(A~F) (생략가능)");

					query = "INSERT INTO CourseHistory VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "','"
							+ txtAttribute[4].getText().toString() + "','" + txtAttribute[5].getText().toString()
							+ "','" + txtAttribute[6].getText().toString() + "','"
							+ txtAttribute[7].getText().toString() + "',attendance+midterm+final+extra,'"
							+ txtAttribute[10].getText().toString() + "','" + txtAttribute[8].getText().toString()
							+ "','" + txtAttribute[9].getText().toString() + "')";

					rs = stmt.executeQuery("select * from CourseHistory");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText())) {
							JOptionPane.showMessageDialog(null, "중복된 값이 존재하는 ID입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[0].setText("*수강내역 번호");
							return;
						}
					}

					rs = stmt.executeQuery("select tuitiontotal,paymenttotal from tuition where stno='"
							+ txtAttribute[1].getText().toString() + "'");
					rs.next();
					if (rs.getInt(1) > rs.getInt(2)) {
						JOptionPane.showMessageDialog(null, "등록금 미납!", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[1].setText("*학생 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select * from student where stno='" + txtAttribute[1].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[1].setText("*학생 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select lno from coursehistory where stno='" + txtAttribute[1].getText().toString()
									+ "' and cyear ='" + txtAttribute[8].getText().toString() + "' and semester='"
									+ txtAttribute[9].getText().toString() + "'");
					while (rs.next()) {
						if (rs.getInt(1) == Integer.parseInt(txtAttribute[3].getText().toString())) {
							JOptionPane.showMessageDialog(null, "해당 학기에 이미 신청한 강의입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							txtLabel[3].setText("*강의 번호");
							return;
						}
					}

					tmp = "select * from professor where pno='" + txtAttribute[2].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[2].setText("*교수 번호");
						return;
					}

					tmp = "select * from lecture where lno='" + txtAttribute[3].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 강의 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[3].setText("*강의 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select sum(lgrade) from lecture where lno in (select lno from coursehistory where stno='"
									+ txtAttribute[1].getText().toString() + "' and cyear='"
									+ txtAttribute[8].getText().toString() + "' and semester='"
									+ txtAttribute[9].getText().toString() + "')");
					rs.next();
					int grade = rs.getInt(1);
					rs = stmt.executeQuery(
							"select	lgrade from lecture where lno='" + txtAttribute[3].getText().toString() + "'");
					rs.next();
					grade += rs.getInt(1);
					if (grade > 10) {
						JOptionPane.showMessageDialog(null, "한 학기에 최대 10학점까지만 수강 할 수 있습니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (Integer.parseInt(txtAttribute[4].getText().toString())
							+ Integer.parseInt(txtAttribute[5].getText().toString())
							+ Integer.parseInt(txtAttribute[6].getText().toString())
							+ Integer.parseInt(txtAttribute[7].getText().toString()) > 100) {
						JOptionPane.showMessageDialog(null, "등록 가능한 총점의 범위 (0~100)를 벗어났습니다!", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[4].setText("*출석 점수");
						txtLabel[5].setText("*중간 점수");
						txtLabel[6].setText("*기말 점수");
						txtLabel[7].setText("*기타 점수");
						return;
					}
					
					if (Integer.parseInt(txtAttribute[9].getText()) != 1
							&& Integer.parseInt(txtAttribute[9].getText()) != 2) {
						
						JOptionPane.showMessageDialog(null, "입력 가능한 학기는 1, 2 (학기)입니다.", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[9].setText("*학기");
						return;
						
					}
					
					if (!(txtAttribute[10].getText().equals("A") || txtAttribute[10].getText().equals("B")
							|| txtAttribute[10].getText().equals("C") || txtAttribute[10].getText().equals("D")
							|| txtAttribute[10].getText().equals("F") || txtAttribute[10].getText().equals(""))) {
						
						JOptionPane.showMessageDialog(null, "등록 가능한 평점이 아닙니다!", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[10].setText("*평점(A~F) (생략가능)");
						return;
						
					}
					

				} else if (radioState == 8) { // Mentorship

					txtLabel[0].setText("교수 번호");
					txtLabel[1].setText("학생 번호");
					txtLabel[2].setText("연도");
					txtLabel[3].setText("학기");

					query = "INSERT INTO Mentorship VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "','" + txtAttribute[2].getText().toString()
							+ "','" + txtAttribute[3].getText().toString() + "')";

					rs = stmt.executeQuery("select * from Mentorship");
					while (rs.next()) {
						if ((rs.getInt(1) == Integer.parseInt(txtAttribute[0].getText()))
								&& (rs.getInt(2) == Integer.parseInt(txtAttribute[1].getText())
										&& (rs.getInt(3) == Integer.parseInt(txtAttribute[2].getText()))
										&& (rs.getInt(4) == Integer.parseInt(txtAttribute[3].getText())))) {
							JOptionPane.showMessageDialog(null, "이미 등록된 지도 관계입니다.", "관리자 메세지",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

					tmp = "select * from professor where pno='" + txtAttribute[0].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*교수 번호");
						return;
					}

					tmp = "select * from student where stno='" + txtAttribute[1].getText().toString() + "'";
					rs = stmt.executeQuery(tmp);
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[1].setText("*학생 번호");
						return;
					}

					if (Integer.parseInt(txtAttribute[3].getText()) != 1
							&& Integer.parseInt(txtAttribute[3].getText()) != 2) {
						JOptionPane.showMessageDialog(null, "입력 가능한 학기는 1, 2 (학기)입니다.", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[3].setText("*학기");
						return;
					}

				} else if (radioState == 9) { // 학생_동아리 테이블

					txtLabel[0].setText("학생 번호");
					txtLabel[1].setText("동아리 번호");

					query = "INSERT INTO Student_has_Circle VALUES('" + txtAttribute[0].getText().toString() + "','"
							+ txtAttribute[1].getText().toString() + "')";

					rs = stmt.executeQuery(
							"select * from student where stno='" + txtAttribute[0].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학생 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*학생 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select * from Circle where cno='" + txtAttribute[1].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 동아리 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[1].setText("*동아리 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select * from Student_has_Circle where stno='" + txtAttribute[0].getText().toString()
									+ "' and cno ='" + txtAttribute[1].getText().toString() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "해당 학생은 이미 해당 동아리에 가입했습니다.", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*학생 번호");
						txtLabel[1].setText("*동아리 번호");
						return;
					}

					rs = stmt.executeQuery("select count(*) from Student_has_Circle where cno='"
							+ txtAttribute[1].getText().toString() + "'");
					rs.next();
					int members = rs.getInt(1) + 1;
					stmt.executeUpdate("Update Circle set members='" + members + "' where cno='"
							+ txtAttribute[1].getText().toString() + "'");

				} else if (radioState == 10) { // 교수_학과 테이블

					txtLabel[0].setText("교수 번호");
					txtLabel[1].setText("학과 번호");

					query = "INSERT INTO Professor_has_Department VALUES('" + txtAttribute[0].getText().toString()
							+ "','" + txtAttribute[1].getText().toString() + "')";

					rs = stmt.executeQuery(
							"select * from Professor where pno='" + txtAttribute[0].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 교수 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*교수 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select * from Department where dno='" + txtAttribute[1].getText().toString() + "'");
					if (!rs.next()) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 학과 ID입니다.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
						txtLabel[1].setText("*학과 번호");
						return;
					}

					rs = stmt.executeQuery(
							"select * from Professor_has_Department where pno='" + txtAttribute[0].getText().toString()
									+ "' and dno ='" + txtAttribute[1].getText().toString() + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "해당 교수는 이미 해당 학과에 속해있습니다.", "관리자 메세지",
								JOptionPane.ERROR_MESSAGE);
						txtLabel[0].setText("*교수 번호");
						txtLabel[1].setText("*학과 번호");
						return;
					}

				}
				stmt.executeUpdate(query);
				JOptionPane.showMessageDialog(null, "입력 성공!", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} catch (Exception e1) {

				JOptionPane.showMessageDialog(null, "쿼리 읽기 실패.", "관리자 메세지", JOptionPane.ERROR_MESSAGE);
			}

		} else { // 취소 버튼 클릭 시
			this.dispose();
		}

	}
}
