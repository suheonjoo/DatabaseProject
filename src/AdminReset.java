

import java.sql.*;
import javax.swing.JOptionPane;

public class AdminReset{

	Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public AdminReset() { 
		conDB();
		ResetAllTables();
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
	
	
	public void ResetAllTables() {
		try {
			String query="";
			stmt=con.createStatement();
			query = "DROP DATABASE IF EXISTS  madang";
			stmt.executeUpdate(query);
			query = "create database madang";
			stmt.executeUpdate(query);
			query = "use madang";
			stmt.executeQuery(query);
			
			query="CREATE TABLE Department ("
					+ "dno INTEGER PRIMARY KEY,"
					+ "dname VARCHAR(15),"
					+ "dphone VARCHAR(15),"
					+ "doffice VARCHAR(15))";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Professor ("
					+ "pno INTEGER PRIMARY KEY,"
					+ "pname VARCHAR(15)NOT NULL,"
					+ "paddress VARCHAR(20),"
					+ "pphone VARCHAR(15),"
					+ "pemail VARCHAR(25),"
					+ "dno INTEGER,"
					+ "FOREIGN KEY(dno) REFERENCES Department (dno) on delete cascade)";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Student ("
					+ "stno INTEGER PRIMARY KEY,"
					+ "stname VARCHAR(15),"
					+ "staddress VARCHAR(20),"
					+ "stphone VARCHAR(15),"
					+ "stemail VARCHAR(25),"
					+ "dname VARCHAR(15),"
					+ "mentorpname VARCHAR(15),"
					+ "payaccount VARCHAR(20),"
					+ "subdname VARCHAR(15))";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Lecture("
					+ "lno INTEGER PRIMARY KEY,"
					+ "classno INTEGER,"
					+ "pname VARCHAR(15),"
					+ "lname VARCHAR(15),"
					+ "lday VARCHAR(5),"
					+ "lperiod INTEGER,"
					+ "lgrade INTEGER,"
					+ "ltime INTEGER,"
					+ "dname VARCHAR(15),"
					+ "lroom VARCHAR(15))";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Circle ("
					+ "cno INTEGER PRIMARY KEY,"
					+ "cname VARCHAR(15),"
					+ "members INTEGER ,"
					+ "chairman VARCHAR(15),"
					+ "pname VARCHAR(15),"
					+ "coffice VARCHAR(15))";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Tuition ("
					+ "stno INTEGER PRIMARY KEY,"
					+ "paymentday INTEGER,"
					+ "semester INTEGER,"
					+ "tuitiontotal INTEGER,"
					+ "paymenttotal INTEGER,"
					+ "lastpaymentday DATE,"
					+ "FOREIGN KEY (stno) REFERENCES Student (stno) on delete cascade)";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE CourseHistory ("
					+ "courseno INTEGER PRIMARY KEY,"
					+ "stno INTEGER ,"
					+ "pno INTEGER ,"
					+ "lno INTEGER ,"
					+ "attendance INTEGER,"
					+ "midterm INTEGER,"
					+ "final INTEGER, "
					+ "extra INTEGER,"
					+ "total INTEGER,"
					+ "grade CHAR,"
					+ "cyear INTEGER,"
					+ "semester INTEGER,"
					+ "FOREIGN KEY (stno) REFERENCES Student (stno) on delete cascade,"
					+ "FOREIGN KEY (pno) REFERENCES Professor (pno) on delete set null,"
					+ "FOREIGN KEY (lno) REFERENCES lecture (lno) on delete set null)";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Mentorship("
					+ "pno INTEGER,"
					+ "stno INTEGER,"
					+ "myear INTEGER,"
					+ "semester INTEGER,"
					+ "FOREIGN KEY(pno) REFERENCES Professor(pno) on delete cascade,"
					+ "FOREIGN KEY(stno) REFERENCES Student (stno) on delete cascade)";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Student_has_Circle ("
					+ "stno INTEGER ,"
					+ "cno INTEGER ,"
					+ "PRIMARY KEY (stno, cno),"
					+ "FOREIGN KEY (stno) REFERENCES Student (stno) on delete cascade,"
					+ "FOREIGN KEY (cno) REFERENCES Circle (cno) on delete cascade)";
			stmt.executeUpdate(query);
			
			query="CREATE TABLE Professor_has_Department ("
					+ "pno INTEGER ,"
					+ "dno INTEGER ,"
					+ "PRIMARY KEY (pno, dno),"
					+ "FOREIGN KEY (pno) REFERENCES Professor (pno) on delete cascade,"
					+ "FOREIGN KEY (dno) REFERENCES Department (dno) on delete cascade)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Department VALUES(1,'컴퓨터공학과','1234-0001','가관 301'),"
					+ "(2,'정보보호학과','1234-0002','가관 302'),"
					+ "(3,'소프트웨어학과','1234-0003','가관 303'),"
					+ "(4,'데이터사이언스학과','1234-0004','가관 401'),"
					+ "(5,'지능기전공학부','1234-0005','가관 402'),"
					+ "(6,'건축공학부','1234-0011','나관 201'),"
					+ "(7,'건설환경공학과','1234-0012','나관 202'),"
					+ "(8,'환경에너지학과','1234-0013','나관 301'),"
					+ "(9,'기계항공우주공학부','1234-0014','나관 302'),"
					+ "(10,'양자원자력공학과','1234-0015','나관 303'),"
					+ "(11,'국방시스템공학과','1234-0016','나관 304'),"
					+ "(12,'항공시스템공학과','1234-0017','나관 305'),"
					+ "(13,'국어국문학과','1234-0031','다관 101'),"
					+ "(14,'국제학부','1234-0032','다관 102'),"
					+ "(15,'역사학과','1234-0033','다관 103'),"
					+ "(16,'교육학과','1234-0034','다관 104'),"
					+ "(17,'행정학과','1234-0035','다관 201'),"
					+ "(18,'법학부','1234-0036','다관 202'),"
					+ "(19,'미디어학과','1234-0037','다관 203'),"
					+ "(20,'경영학부','1234-0038','다관 204'),"
					+ "(21,'경제학부','1234-0039','다관 206'),"
					+ "(22,'수학통계학과','1234-0041','라관 201'),"
					+ "(23,'물리천문학과','1234-0042','라관 202'),"
					+ "(24,'음악과','1234-0051','예관 101'),"
					+ "(25,'체육학과','1234-0052','예관 102')";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Professor VALUES(1,'권수영','대한민국 서울','1234-0001','0001@sjcu.ac.kr',1),"
					+ "(2,'정성은','대한민국 서울','1234-0002','0002@sjcu.ac.kr',2),"
					+ "(3,'하홍준','대한민국 경기도','1234-0003','0003@sjcu.ac.kr',3),"
					+ "(4,'남선호','대한민국 경기도','1234-0004','0004@sjcu.ac.kr',4),"
					+ "(5,'한혜민','대한민국 서울','1234-0005','0005@sjcu.ac.kr',5),"
					+ "(6,'송정혁','대한민국 강원도','1234-0011','0011@sjcu.ac.kr',6),"
					+ "(7,'송상진','대한민국 경기도','1234-0012','0012@sjcu.ac.kr',7),"
					+ "(8,'정경하','대한민국 서울','1234-0013','0013@sjcu.ac.kr',8),"
					+ "(9,'정하민','대한민국 서울','1234-0014','0014@sjcu.ac.kr',9),"
					+ "(10,'고혜빈','대한민국 서울','1234-0015','0015@sjcu.ac.kr',10),"
					+ "(11,'서문영','대한민국 충청도','1234-0016','0016@sjcu.ac.kr',11),"
					+ "(12,'정명준','대한민국 서울','1234-0017','0017@sjcu.ac.kr',12),"
					+ "(13,'강민식','대한민국 서울','1234-0031','0031@sjcu.ac.kr',13),"
					+ "(14,'황보현','대한민국 서울','1234-0032','0032@sjcu.ac.kr',14),"
					+ "(15,'신선욱','대한민국 경기도','1234-0033','0033@sjcu.ac.kr',15),"
					+ "(16,'한동수','대한민국 서울','1234-0034','0034@sjcu.ac.kr',16),"
					+ "(17,'표은남','대한민국 서울','1234-0035','0035@sjcu.ac.kr',17),"
					+ "(18,'노용진','대한민국 서울','1234-0036','0036@sjcu.ac.kr',18),"
					+ "(19,'송문철','대한민국 강원도','1234-0037','0037@sjcu.ac.kr',19),"
					+ "(20,'심미정','대한민국 서울','1234-0038','0038@sjcu.ac.kr',20),"
					+ "(21,'권현준','대한민국 서울','1234-0039','0039@sjcu.ac.kr',21),"
					+ "(22,'Pascal','프랑스 파리','1234-0041','0041@sjcu.ac.kr',22),"
					+ "(23,'백구름','대한민국 서울','1234-0042','0042@sjcu.ac.kr',23),"
					+ "(24,'Beethoven','오스트리아 빈','1234-0051','0051@sjcu.ac.kr',24),"
					+ "(25,'홍한결','대한민국 서울','1234-0052','0052@sjcu.ac.kr',25)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Student VALUES(1,'황기훈','대한민국 경기도','010-0000-3101','3101@gmail.com','컴퓨터공학과','권수영','100-00-00001',NULL),"
					+ "(2,'안남일','대한민국 서울','010-0000-3102','3102@gmail.com','정보보호학과','정성은','100-00-00002',NULL),"
					+ "(3,'문민정','대한민국 강원도','010-0000-3103','3103@naver.com','소프트웨어학과','하홍준','100-00-00003',NULL),"
					+ "(4,'안재식','대한민국 충청도','010-0000-3104','3104@gmail.com','데이터사이언스학과','남선호','100-00-00004','컴퓨터공학과'),"
					+ "(5,'김윤자','대한민국 전라도','010-0000-3105','3105@naver.com','지능기전공학부','한혜민','100-00-00005',NULL),"
					+ "(6,'표세빈','대한민국 경상도','010-0000-3106','3106@naver.com','건축공학부','송정혁','100-00-00006','수학통계학과'),"
					+ "(7,'이상식','대한민국 서울','010-0000-3107','3107@gmail.com','건설환경공학과','송상진','100-00-00007',NULL),"
					+ "(8,'황수아','대한민국 강원도','010-0000-3108','3108@naver.com','환경에너지학과','정경하','100-00-00008',NULL),"
					+ "(9,'설하윤','대한민국 충청도','010-0000-3109','3109@gmail.com','기계항공우주공학부','정하민','100-00-00009',NULL),"
					+ "(10,'양순자','대한민국 경상도','010-0000-3110','3110@daum.net','양자원자력공학과','고혜빈','100-00-00010',NULL),"
					+ "(11,'심영빈','대한민국 서울','010-0000-3111','3111@naver.com','국방시스템공학과','서문영','100-00-00011',NULL),"
					+ "(12,'안윤철','대한민국 강원도','010-0000-3112','3112@gmail.com','항공시스템공학과','정명준','100-00-00012',NULL),"
					+ "(13,'하동훈','대한민국 경기도','010-0000-3113','3113@gmail.com','국어국문학과','강민식','100-00-00013',NULL),"
					+ "(14,'Harden','미국 브루클린','010-0000-3114','3114@gmail.com','국제학부','황보현','100-00-00014',NULL),"
					+ "(15,'고성진','대한민국 전라도','010-0000-3115','3115@naver.com','역사학과','신선욱','100-00-00015',NULL),"
					+ "(16,'노승현','대한민국 서울','010-0000-3116','3116@naver.com','교육학과','한동수','100-00-00016',NULL),"
					+ "(17,'장성아','대한민국 서울','010-0000-3117','3117@gmail.com','행정학과','표은남','100-00-00017','컴퓨터공학과'),"
					+ "(18,'최철진','대한민국 강원도','010-0000-3118','3118@gmail.com','법학부','노용진','100-00-00018',NULL),"
					+ "(19,'백주현','대한민국 경상도','010-0000-3119','3119@naver.com','미디어학과','송문철','100-00-00019','국제학부'),"
					+ "(20,'홍유선','대한민국 충청도','010-0000-3120','3120@naver.com','경영학부','심미정','100-00-00020','경제학부'),"
					+ "(21,'남궁연지','대한민국 서울','010-0000-3121','3121@naver.com','경제학부','권현준','100-00-00021',NULL),"
					+ "(22,'이산','대한민국 서울','010-0000-3122','3122@naver.com','수학통계학과','Pascal','100-00-00022',NULL),"
					+ "(23,'Oliver','영국 런던','010-0000-3123','3123@naver.com','물리천문학과','백구름','100-00-00023',NULL),"
					+ "(24,'Michael','미국 인디애나','010-0000-3124','3124@gmail.com','음악과','Beethoven','100-00-00024',NULL),"
					+ "(25,'강한','대한민국 강원도','010-0000-3125','3125@naver.com','체육학과','홍한결','100-00-00025',NULL)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Lecture VALUES(1,001,'권수영','데이터베이스','월',1,3,1,'컴퓨터공학과','가관 101'),"
					+ "(2,001,'정성은','정보보호기초','화',1,4,1,'정보보호학과','가관 102'),"
					+ "(3,001,'하홍준','C프로그래밍','수',2,2,1,'소프트웨어학과','가관 103'),"
					+ "(4,001,'남선호','딥러닝개론','목',2,3,1,'데이터사이언스학과','가관 201'),"
					+ "(5,001,'한혜민','웨어러블시스템','월',3,4,1,'지능기전공학부','가관 101'),"
					+ "(6,001,'송정혁','건축설비','화',3,2,1,'건축공학부','나관 101'),"
					+ "(7,001,'송상진','건설환경통계학','금',1,3,1,'건설환경공학과','나관 102'),"
					+ "(8,001,'정경하','환경화학','금',2,4,3,'환경에너지학과','나관 103'),"
					+ "(9,001,'정하민','비행체제어원리','월',5,2,1,'기계항공우주공학부','나관 104'),"
					+ "(10,001,'고혜빈','원자로이론','수',3,1,1,'양자원자력공학과','나관 105'),"
					+ "(11,001,'서문영','군사학개론','화',3,3,1,'국방시스템공학과','나관 101'),"
					+ "(12,001,'정명준','모의비행','월',4,3,1,'항공시스템공학과','나관 102'),"
					+ "(13,001,'강민식','국어정서법','화',4,3,1,'국어국문학과','다관 301'),"
					+ "(14,001,'황보현','국제경제학','수',5,4,1,'국제학부','다관 301'),"
					+ "(15,001,'신선욱','중세유럽의문화','목',5,4,1,'역사학과','다관 301'),"
					+ "(16,001,'한동수','교육기초','금',3,2,1,'교육학과','다관 301'),"
					+ "(17,001,'표은남','행정학','금',4,1,1,'행정학과','다관 301'),"
					+ "(18,001,'노용진','민법총칙','월',6,1,1,'법학부','다관 301'),"
					+ "(19,001,'송문철','글로벌미디어','수',7,3,1,'미디어학과','다관 301'),"
					+ "(20,001,'심미정','경영원리','월',8,3,1,'경영학부','다관 301'),"
					+ "(21,001,'권현준','경제학개론','화',6,3,1,'경제학부','다관 301'),"
					+ "(22,001,'Pascal','확률과통계','목',7,3,1,'수학통계학과','라관 101'),"
					+ "(23,001,'백구름','별자리탐구','월',1,1,1,'물리천문학과','라관 102'),"
					+ "(24,001,'Beethoven','발성기초','금',5,1,1,'음악과','예관 103'),"
					+ "(25,001,'홍한결','맨손체조','금',6,1,1,'체육학과','예관 104')";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Circle VALUES(1,'코딩부',3,'황기훈','권수영','동관 101'),(2,'게임창작부',2,'문민정','권수영','동관 102'),"
					+ "(3,'정보보안부',2,'안남일','정성은','동관 103'),"
					+ "(4,'방송부',1,'백주현','황보현','동관 104'),"
					+ "(5,'고전연구회',0,NULL,'강민식','동관 105'),"
					+ "(6,'자본주의연구회',0,NULL,'권현준','동관 106'),"
					+ "(7,'여행동아리',0,NULL,'한동수','동관 107'),"
					+ "(8,'역사기행동아리',0,NULL,'신선욱','동관 108'),"
					+ "(9,'마술동아리',0,NULL,'강민식','동관 109'),"
					+ "(10,'카드게임동아리',0,NULL,'강민식','동관 109'),"
					+ "(11,'마당극동아리',0,NULL,'Beethoven','동관 201'),"
					+ "(12,'바둑동아리',0,NULL,'고혜빈','동관 202'),"
					+ "(13,'서예회',0,NULL,'정명준','동관 203'),"
					+ "(14,'사진동아리',0,NULL,'강민식','동관 204'),"
					+ "(15,'영어동아리',0,NULL,'한혜민','동관 205'),"
					+ "(16,'밴드동아리',0,NULL,'Beethoven','동관 206'),"
					+ "(17,'연극동아리',0,NULL,'심미정','동관 207'),"
					+ "(18,'댄스동아리',0,NULL,'백구름','동관 208'),"
					+ "(19,'무예지도부',0,NULL,'홍한결','동관 209'),"
					+ "(20,'자전거동아리',0,NULL,'Pascal','동관 301'),"
					+ "(21,'농구동아리',0,NULL,'홍한결','동관 302'),"
					+ "(22,'수영동아리',0,NULL,'홍한결','동관 303'),"
					+ "(23,'가톨릭동아리',0,NULL,'Pascal','동관 304'),"
					+ "(24,'합창부',1,'Michael','Beethoven','예관 101'),"
					+ "(25,'테니스부',1,'강한','홍한결','예관 102')";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Tuition VALUES(1,2021,1,25000,25000,STR_TO_DATE('2021-03-01','%Y-%m-%d')),"
					+ "(2,2021,1,5000,5000,STR_TO_DATE('2021-03-02','%Y-%m-%d')),"
					+ "(3,2021,1,10000,10000,STR_TO_DATE('2021-03-03','%Y-%m-%d')),"
					+ "(4,2021,1,15000,15000,STR_TO_DATE('2021-03-04','%Y-%m-%d')),"
					+ "(5,2021,1,20000,20000,STR_TO_DATE('2021-03-05','%Y-%m-%d')),"
					+ "(6,2021,1,25000,25000,STR_TO_DATE('2021-03-01','%Y-%m-%d')),"
					+ "(7,2021,1,30000,30000,STR_TO_DATE('2021-03-02','%Y-%m-%d')),"
					+ "(8,2021,1,35000,35000,STR_TO_DATE('2021-03-03','%Y-%m-%d')),"
					+ "(9,2021,1,40000,40000,STR_TO_DATE('2021-03-04','%Y-%m-%d')),"
					+ "(10,2021,1,45000,40000,STR_TO_DATE('2020-09-01','%Y-%m-%d')),"
					+ "(11,2021,1,5000,5000,STR_TO_DATE('2021-03-01','%Y-%m-%d')),"
					+ "(12,2021,1,10000,10000,STR_TO_DATE('2021-03-02','%Y-%m-%d')),"
					+ "(13,2021,1,15000,15000,STR_TO_DATE('2021-03-03','%Y-%m-%d')),"
					+ "(14,2021,1,20000,20000,STR_TO_DATE('2021-03-04','%Y-%m-%d')),"
					+ "(15,2021,1,25000,25000,STR_TO_DATE('2021-03-05','%Y-%m-%d')),"
					+ "(16,2021,1,30000,30000,STR_TO_DATE('2021-03-01','%Y-%m-%d')),"
					+ "(17,2021,1,35000,35000,STR_TO_DATE('2021-03-02','%Y-%m-%d')),"
					+ "(18,2021,1,40000,40000,STR_TO_DATE('2021-03-03','%Y-%m-%d')),"
					+ "(19,2021,1,45000,45000,STR_TO_DATE('2021-03-04','%Y-%m-%d')),"
					+ "(20,2021,1,5000,5000,STR_TO_DATE('2021-03-05','%Y-%m-%d')),"
					+ "(21,2021,1,10000,10000,STR_TO_DATE('2021-03-01','%Y-%m-%d')),"
					+ "(22,2021,1,15000,15000,STR_TO_DATE('2021-03-02','%Y-%m-%d')),"
					+ "(23,2021,1,20000,20000,STR_TO_DATE('2021-03-03','%Y-%m-%d')),"
					+ "(24,2021,1,25000,25000,STR_TO_DATE('2021-03-04','%Y-%m-%d')),"
					+ "(25,2021,1,30000,25000,STR_TO_DATE('2020-09-05','%Y-%m-%d'))";
			stmt.executeUpdate(query);
			
			query="INSERT INTO CourseHistory VALUES(1,1,1,1,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(2,2,2,2,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(3,3,3,3,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(4,4,4,4,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(5,5,5,5,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(6,6,6,6,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(7,7,7,7,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(8,8,8,8,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(9,9,9,9,20,40,25,0,attendance+midterm+final+extra,'B',2021,1),"
					+ "(10,11,11,11,20,20,25,0,attendance+midterm+final+extra,'D',2021,1),"
					+ "(11,12,12,12,20,20,25,0,attendance+midterm+final+extra,'D',2021,1),"
					+ "(12,13,13,13,20,20,25,0,attendance+midterm+final+extra,'D',2021,1),"
					+ "(13,14,14,14,20,20,25,0,attendance+midterm+final+extra,'D',2021,1),"
					+ "(14,15,15,15,20,20,25,0,attendance+midterm+final+extra,'D',2021,1),"
					+ "(15,16,16,16,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(16,17,17,17,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(17,18,18,18,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(18,19,19,19,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(19,20,20,20,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(20,21,21,21,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(21,22,22,22,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(22,23,23,23,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(23,24,24,24,20,40,30,0,attendance+midterm+final+extra,'A',2021,1),"
					+ "(24,4,1,1,20,40,30,0,attendance+midterm+final+extra,'A',2018,1),"
					+ "(25,6,22,22,20,40,25,0,attendance+midterm+final+extra,'B',2019,2),"
					+ "(26,17,1,1,20,20,25,0,attendance+midterm+final+extra,'D',2019,1),"
					+ "(27,19,14,14,10,10,10,0,attendance+midterm+final+extra,'F',2020,2),"
					+ "(28,20,21,21,20,40,30,0,attendance+midterm+final+extra,'A',2020,1)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Mentorship VALUES(1,1,2021,1),"
					+ "(2,2,2021,1),"
					+ "(3,3,2021,1),"
					+ "(4,4,2021,1),"
					+ "(5,5,2021,1),"
					+ "(6,6,2021,1),"
					+ "(7,7,2021,1),"
					+ "(8,8,2021,1),"
					+ "(9,9,2021,1),"
					+ "(10,10,2021,1),"
					+ "(11,11,2021,1),"
					+ "(12,12,2021,1),"
					+ "(13,13,2021,1),"
					+ "(14,14,2021,1),"
					+ "(15,15,2021,1),"
					+ "(16,16,2021,1),"
					+ "(17,17,2021,1),"
					+ "(18,18,2021,1),"
					+ "(19,19,2021,1),"
					+ "(20,20,2021,1),"
					+ "(21,21,2021,1),"
					+ "(22,22,2021,1),"
					+ "(23,23,2021,1),"
					+ "(24,24,2021,1),"
					+ "(25,25,2021,1)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Student_has_Circle VALUES(1,1),"
					+ "(1,2),"
					+ "(1,3),"
					+ "(2,3),"
					+ "(3,2),"
					+ "(4,1),"
					+ "(17,1),"
					+ "(19,4),"
					+ "(25,25),"
					+ "(24,24)";
			stmt.executeUpdate(query);
			
			query="INSERT INTO Professor_has_Department VALUES(1,1),"
					+ "(2,2),"
					+ "(3,3),"
					+ "(4,4),"
					+ "(5,5),"
					+ "(6,6),"
					+ "(7,7),"
					+ "(8,8),"
					+ "(9,9),"
					+ "(10,10),"
					+ "(11,11),"
					+ "(12,12),"
					+ "(13,13),"
					+ "(14,14),"
					+ "(15,15),"
					+ "(16,16),"
					+ "(17,17),"
					+ "(18,18),"
					+ "(19,19),"
					+ "(20,20),"
					+ "(21,21),"
					+ "(22,22),"
					+ "(23,23),"
					+ "(24,24),"
					+ "(25,25)";
			stmt.executeUpdate(query);
			
			JOptionPane.showMessageDialog(null, "DB 초기화 완료", "관리자 메세지", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e2) {
			System.out.println("쿼리 읽기 실패 :" + e2);
		}
	}
}

