package CRUD;
package ;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


public class paywallet extends JFrame implements ActionListener{
   qwefqwef
   JPanel title1, title2, title3;
   JPanel left, right, bottom;
   TextArea display;
   TextField user_id, user_name, email, tel;
   TextField user_id2, cash_or_not, amount, day, class_in;
   TextField user_id3, memo;
   TextField search_user_id;
   JButton search_memo, memo_add, user_add, search_user, spend_add, search_spend, total, delete, cancel;
   Connection conn;
   Statement stat;
   Statement stat2;
   
   public paywallet() {
      super("PayWallet");
      
      
      setLayout(new BorderLayout());
      
      display = new TextArea();
      display.setEditable(false);
      
      title1 = new JPanel();
      title1.add(new Label("회원가입"));
      Font f1 = new Font("a피노키오B", Font.BOLD, 15);
      title1.setFont(f1);
      //title1.setForeground(Color.orange);

      title2 = new JPanel();
      title2.add(new Label("거래 내역"));
      title2.setFont(f1);
      //title2.setForeground(Color.orange);

      title3 = new JPanel();;
      title3.add(new Label("메모 등록"));
      title3.setFont(f1);
      //title3.setForeground(Color.orange);
      
      left = new JPanel();
      left.setLayout(new GridLayout(18, 1));
      
      // 패널에 붙일 라벨들 정해주기
      JPanel p_user_id = new JPanel();
      p_user_id.add(new Label("ID"));
      p_user_id.add(user_id = new TextField(10));
      

      JPanel p_user_name = new JPanel();
      p_user_name.add(new Label("이 름"));
      p_user_name.add(user_name = new TextField(10));

      JPanel p_email = new JPanel();
      p_email.add(new Label("이메일"));
      p_email.add(email = new TextField(10));

      JPanel p_tel = new JPanel();
      p_tel.add(new Label("휴대폰 번호"));
      p_tel.add(tel = new TextField(11));
      

      JPanel p_user_id2 = new JPanel();
      p_user_id2.add(new Label("ID"));
      p_user_id2.add(user_id2 = new TextField(10));

      JPanel p_cash_or_not = new JPanel();
      p_cash_or_not.add(new Label("카드 or 현금"));
      p_cash_or_not.add(cash_or_not = new TextField(10));

      JPanel p_amount = new JPanel();
      p_amount.add(new Label("금액"));
      p_amount.add(amount = new TextField(10));
      
      JPanel p_day = new JPanel();
      p_day.add(new Label("날짜"));
      p_day.add(day = new TextField(10));
      
      JPanel p_class = new JPanel();
      p_class.add(new Label("분야 (index)"));
      //p_class.add(new Label("[0]:음식점 [1]:교통 [2]:식료품 [3]:쇼핑 [4]:카페/디저트 [5]:뷰티/피트니스 "));
      //p_class.add(new Label("[6]:병원/약국 [7]:애완동물 [8]:교육/육아 [9]:레저/스포츠 [10]:여행/숙박 [11]:공과금  [12]:공공/사회기관"));
      p_class.add(class_in = new TextField(10));
      
      
      JPanel p_user_id3 = new JPanel();
      p_user_id3.add(new Label("ID"));
      p_user_id3.add(user_id3 = new TextField(10));
      
      
      JPanel p_memo = new JPanel();
      p_memo.add(new Label("메모"));
      p_memo.add(memo = new TextField(10));

      //사용자 확인
      left.add(title1);
      left.add(p_user_id);
      left.add(p_user_name);
      left.add(p_email);
      left.add(p_tel);
      
      left.add(user_add = new JButton("회원 가입"));
      user_add.addActionListener(this);
      
      //거래 내역 확인
      left.add(title2);
      left.add(p_user_id2);
      left.add(p_cash_or_not);
      left.add(p_amount);
      left.add(p_day);
      left.add(p_class);
      
      
      left.add(spend_add = new JButton("거래내역 입력하기"));
      spend_add.addActionListener(this);
      
      left.add(title3);
      left.add(p_user_id3);
      left.add(p_memo);

      left.add(memo_add = new JButton("메모 입력하기"));
      memo_add.addActionListener(this);
      
      
      // 조회 및 삭제 버튼들
      right = new JPanel();
      right.setLayout(new GridLayout(16, 1));
      
      JPanel p_search_user_id = new JPanel();
      p_search_user_id.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 20 , 0));

      
      p_search_user_id.add(new Label("ID"));
      p_search_user_id.add(search_user_id = new TextField(10));
      
      right.add( p_search_user_id);
      
      
      
      right.add(search_user = new JButton("사용자 전체 확인 "));
      search_user.addActionListener(this);
      
      right.add(search_spend = new JButton("내역 확인 "));
      search_spend.addActionListener(this);
      
      right.add(total = new JButton("전체 내역 확인"));
      total.addActionListener(this);
      
      right.add(delete = new JButton("내역 삭제"));
      delete.addActionListener(this);
      
      
      right.add(search_memo = new JButton("메모 확인"));
      search_memo.addActionListener(this);
      
      bottom = new JPanel();

      bottom.add(cancel = new JButton("취소"));
      cancel.addActionListener(this);
      
      
      
      

      add("Center", display);
      add("West", left);
      add("East", right);
      add("South", bottom);
      
      setSize(1000, 600);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      database();
   }
   

   private void database() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); //DB연결 객체 생성
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/paywallet","root","1234");
         stat = conn.createStatement();
         System.out.println("DB접속");
      } catch (Exception e) {
         e.printStackTrace(System.out);
      }
   }
   
   private void disconnect() {
      try {
         if (stat != null) {
            stat.close();
         }
         if (conn != null) {
            conn.close();
         }
      } catch (Exception ex) {
      }
   }
   
   public void clear() {
      
      user_id.setText("");
      user_name.setText("");
      email.setText("");
      tel.setText("");

      user_id2.setText("");
      cash_or_not.setText("");
      amount.setText("");
      day.setText("");
      class_in.setText("");

      user_id3.setText("");
      memo.setText("");
      
      search_user_id.setText("");

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      ResultSet rs = null;
      ResultSet rs2 = null;
      Component c = (Component) e.getSource();
      
      try {
         
      
      if(c == user_add) {
         String s_user_id = user_id.getText().trim();
         String s_user_name = user_name.getText().trim();
         String s_email = email.getText().trim();
         String s_tel = tel.getText().trim();
         if (s_user_id == null 
               || s_user_name == null
               || s_email == null || s_tel == null
               || s_user_id.length() == 0
               || s_user_name.length() == 0 
               || s_email.length() == 0
               || s_tel.length() == 0){
            return;
         }
         String sql = "insert into userinfo values(?,?,?,?)";
         // Statement의 메소드를 이용해서 SQL문의 실행

         java.sql.PreparedStatement stat = conn
               .prepareStatement(sql);
         stat.setString(1, s_user_id);
         stat.setString(2, s_user_name);
         stat.setString(3, s_email);
         stat.setString(4, s_tel);
         stat.executeUpdate();
      
         clear();
      

         
         
         
      }else if(c == spend_add) {
         
         String s_day = day.getText().trim();
         String s_user_id = user_id2.getText().trim();
         String s_class_in = class_in.getText().trim();
         String s_cash_or_not = cash_or_not.getText().trim();
         String s_amount = amount.getText().trim();

         if (s_user_id == null 
               || s_cash_or_not == null 
               || s_amount == null
               || s_day == null 
               || s_class_in == null
               || s_user_id.length() == 0
               || s_cash_or_not.length() == 0
               || s_amount.length() == 0
               || s_amount.length() == 0
               || s_day.length() == 0
               || s_class_in.length() == 0) {
            return;
         }
            
         String sql = "insert into spend values(?,?,?,?,?)";
         java.sql.PreparedStatement stat = conn
               .prepareStatement(sql);
         stat.setString(1, s_user_id);
         stat.setString(2, s_cash_or_not);
         stat.setString(3, s_amount);
         stat.setString(4, s_day);
         stat.setString(5, s_class_in);
         stat.executeUpdate();
         clear();
         
      
      
      }else if(c == memo_add) {
         String s_user_id = user_id3.getText().trim();
         String s_memo = memo.getText().trim();
         
         if (s_user_id == null 
               || s_user_id == null
               || s_memo == null 
               || s_user_id.length() == 0
               || s_memo.length() == 0 ) {
            return;
         }
            
         String sql = "insert into memo(user_id, description) values(?,?)";
         // Statement의 메소드를 이용해서 SQL문의 실행

         java.sql.PreparedStatement stat = conn
               .prepareStatement(sql);
         stat.setString(1, s_user_id);
         stat.setString(2, s_memo);
         stat.executeUpdate();
      
         clear();
         
         
         
         
      }else if(c == search_user) {
         
         rs = stat.executeQuery("select * from userinfo");
         
         
         display.append("======================================================================"
               + "\n");
         display.append(" ID\t\t   이 름\t\t   이메일 \t\t    전 화 번 호\t \t  \n");
         display.append("======================================================================"
               + "\n");
         
         while (rs.next()) {
            String user_id = rs.getString(1);
            String user_name = rs.getString(2);
            String email = rs.getString(3);
            String tel = rs.getString(4);
            

            display.append(user_id + " \t \t" + user_name + " \t \t"
                  + user_name + " \t \t" + tel + " \t \t \n");
            System.out.println(user_id + " \t\t" + user_name + "\t \t"
                  + email + "\t \t" + tel + " \t\t\n");
         }

         clear();
         
         
      }
      
      if(c == search_spend) {
         String s_search_user_id = search_user_id.getText().trim();

         if (s_search_user_id == null 
               || s_search_user_id.length() == 0)
            return;
         rs = stat.executeQuery("select * from spend where user_id='"+s_search_user_id+"'");
         display.append("============================================================================================="
               + "\n");
         display.append(" ID\t\t    카드 /현금\t\t   금 액\t \t   날 짜\t    분야\t \n");
         display.append("============================================================================================="
               + "\n");
         while (rs.next()) {
            String user_id = rs.getString(1);
            String cash_or_not = rs.getString(2);
            String amount = rs.getString(3);
            String day = rs.getString(4);
            String class_in = rs.getString(5);
         

            display.append(user_id + " \t\t" 
                  + cash_or_not + "\t \t" + amount + " \t\t"
                  + day + " \t\t" + class_in + "\n");
            System.out.println(user_id + " \t\t" 
                  + cash_or_not + "\t \t" + amount + " \t\t"
                  + day + " \t\t" + class_in + "\n");
         }
      
         clear();
         
         
         
      }else if(c == search_memo) {
         
         String s_search_user_id = search_user_id.getText().trim();

         if (s_search_user_id == null 
               || s_search_user_id.length() == 0)
            return;
         
         rs = stat.executeQuery("select * from memo where user_id='"+s_search_user_id+"'");
         display.append("============================================================================================="
               + "\n");
         display.append(" ID\t\t  메모 번호 \t\t 메모\t\t \n");
         display.append("============================================================================================="
               + "\n");
         while (rs.next()) {
            String user_id = rs.getString(1);
            String mnumber = rs.getString(2);
            String memo = rs.getString(3);
            

            display.append(user_id + " \t\t "+ mnumber + " \t\t" 
                  + memo + "\n");
            System.out.println(user_id + " \t\t "+ mnumber + " \t\t" 
                  + memo + "\n");
         }
      
         clear();
         
         
         
         
      }else if(c == total) {
         
         rs = stat.executeQuery("select * from spend");
         display.append("============================================================================================="
               + "\n");
         display.append(" ID\t\t    카드 /현금\t\t   금 액\t \t   날 짜\t    분야\t \n");
         display.append("============================================================================================="
               + "\n");
         while (rs.next()) {
            String user_id = rs.getString(1);
            String cash_or_not = rs.getString(2);
            String amount = rs.getString(3);
            String day = rs.getString(4);
            String class_in = rs.getString(5);
         

            display.append(user_id + " \t\t" 
                  + cash_or_not + "\t \t" + amount + " \t\t"
                  + day + " \t\t" + class_in + "\n");
            System.out.println(user_id + " \t\t" 
                  + cash_or_not + "\t \t" + amount + " \t\t"
                  + day + " \t\t" + class_in + "\n");
         }
      
         clear();
            
         
         
         
         
      }else if(c == delete) {
         
         String s_search_user_id = search_user_id.getText().trim();
         if (s_search_user_id == null || s_search_user_id.length() == 0)
            return;
         stat.executeUpdate("delete from spend where user_ID='"
               + s_search_user_id + "'");
         
         
         clear();
         
         
         
      }else if(c == cancel) {
         display.setText("");
         clear();
         
      }
      
   }catch(Exception ex) {
      System.out.println(ex);
   }
   }
   
   public static void main(String args[]) {
      paywallet p = new paywallet();
      
      
   }

}