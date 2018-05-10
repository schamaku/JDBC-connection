import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.*;  
import javax.swing.*;
public class Main{
	static final String DriverName = "oracle.jdbc.driver.OracleDriver";  
	   static final String USER = "schepyal";
	   static final String PASS = "jidree";

	   
	   public static void main(String[] args) {
	   Connection conn = null;
	   int ssn,total=0, n1=0;
	   int[] hr = new int[40];
	   int[] pr = new int[40];
	   String[] dep_name = new String[50];
	   String[] gender1 = new String[50];
	   String[] date1 = new String[50];
	   String[] relation = new String[50];
	   String d;
	   ssn = Integer.parseInt(JOptionPane.showInputDialog("Enter SSN"));
	   //PreparedStatement stmt = null;
	   try{
		   
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      System.out.println("Connecting to a selected database...");
	      conn = DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","schepyal", "jidree");
	      System.out.println("Connected database successfully...");
	      PreparedStatement stmt=conn.prepareStatement("select count(ssn) as ppl from employee e where e.superssn=?");
	      stmt.setInt(1,ssn);
	      ResultSet rs=stmt.executeQuery(); 
	      while(rs.next()){
	         int count  = rs.getInt("ppl");
	      if(count==0)
	    	  JOptionPane.showMessageDialog(null,"You are not a manager");
	      else{
	    	  String fname;
	    	  fname = JOptionPane.showInputDialog("Enter first name");
	    	  String minit;
	    	  minit = JOptionPane.showInputDialog("Enter middle name initial");
	    	  String lname;
	    	  lname = JOptionPane.showInputDialog("Enter last name");
	    	  int ssn1;
	    	  ssn1 = Integer.parseInt(JOptionPane.showInputDialog("Enter SSN"));
	    	  String date;
	    	  date = JOptionPane.showInputDialog("Enter Date of Birth");
	    	  String address;
	    	  address = JOptionPane.showInputDialog("Enter address");
	    	  String gender;
	    	  gender = JOptionPane.showInputDialog("Enter Sex");
	    	  int salary;
	    	  salary = Integer.parseInt(JOptionPane.showInputDialog("Enter Salary"));
	    	  int superssn;
	    	  superssn = Integer.parseInt(JOptionPane.showInputDialog("Enter Manager's SSN"));
	    	  int deptno;
	    	  deptno = Integer.parseInt(JOptionPane.showInputDialog("Enter department number"));
	    	  String email;
	    	  email = JOptionPane.showInputDialog("Enter email");
	    	  int totproj;
	    	  totproj = Integer.parseInt(JOptionPane.showInputDialog("Enter no.of projects to assign"));
	    	  for(int i=0; i<totproj; i++){
	    		  int j=i+1;
	    		  hr[i]= Integer.parseInt(JOptionPane.showInputDialog("Enter hours for project"+ j));
	    		  total = total + hr[i];
	    	  }
	    	  if(total>40){
	    		  JOptionPane.showMessageDialog(null,"40 hours exceeded, cannot add this employee"); System.exit(0);}
	    	  else{
	    	  for(int i=0; i<totproj; i++){
	    		  int j=i+1;
	    		  pr[i]=Integer.parseInt(JOptionPane.showInputDialog("Enter projectID for project"+ j));
	    	  }
	    	  d = JOptionPane.showInputDialog("Does the employee has dependents? enter yes or no");
	    	  if(d.equals("yes")){
	    		 n1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the no.of dependents"));
	    		 for(int i=0; i<n1; i++){
	    			 
	    			   dep_name[i] = JOptionPane.showInputDialog("Enter dependent name");
	    			   
	    			   
	    	           gender1[i] = JOptionPane.showInputDialog("Enter sex");
	    	      
	    	         
	    	           date1[i] = JOptionPane.showInputDialog("Enter date of birth");
	    	          
	    	         
	    	           relation[i] = JOptionPane.showInputDialog("Enter relation");
	    		 }}
	          stmt=conn.prepareStatement("insert into employee values (?,?,?,?,?,?,?,?,?,?,?)");
	          stmt.setString(1, fname);
	          stmt.setString(2, minit);
	          stmt.setString(3, lname);
	          stmt.setInt(4,ssn1);
	          stmt.setString(5, date);
	          stmt.setString(6, address);
	          stmt.setString(7, gender);
	          stmt.setInt(8, salary);
	          stmt.setInt(9, superssn);
	          stmt.setInt(10, deptno);
	          stmt.setString(11, email);
	          int n=stmt.executeUpdate();  
	          for(int i=0; i<totproj; i++){
	        	  stmt = conn.prepareStatement("insert into works_on values(?,?,?)");
	        	  stmt.setInt(1, ssn1);
	        	  stmt.setInt(2, pr[i]);
	        	  stmt.setInt(3, hr[i]);
	        	  stmt.executeUpdate();
	          }
	          if(d.equals("yes")){

		    		 for(int i=0; i<n1; i++){
			        	  stmt = conn.prepareStatement("insert into dependent values(?,?,?,?,?)");
			        	  stmt.setInt(1, ssn1);
			        	  stmt.setString(2, dep_name[i]);
			        	  stmt.setString(3, gender1[i]);
			        	  stmt.setString(4, date1[i]);
			        	  stmt.setString(5, relation[i]);
			        	  stmt.executeUpdate();
			          } 
		    	  }
	    	  }  	  
	    	  
	   	   System.out.print("Employee Details:\n");
		   System.out.print("Firstname:"+fname+"\n");
		   System.out.print("lastname:"+lname+"\n");
		   System.out.print("SSN:"+ssn1+"\n");
		   System.out.print("Date of Birth:"+date+"\n");
		   System.out.print("Address:"+address+"\n");
		   System.out.print("Sex:"+gender+"\n");
		   System.out.print("Salary:"+salary+"\n");
		   System.out.print("Manager SSN:"+superssn+"\n");
		   System.out.print("Department:"+deptno+"\n");
		   System.out.print("Email:"+email+"\n");
		   System.out.print("-------------------------------------------------"+"\n");
		   System.out.print("Total no.of projects:"+totproj+"\n");
		   for(int i=0; i<totproj;i++){
			   System.out.print("Project"+i+"id"+pr[i]+"\t");
			   System.out.println("Hours:"+hr[i]);
		   }
		   System.out.print("-------------------------------------------------"+"\n");
           System.out.print("No.of Dependents"+n1+"\n");
           for(int i=0; i<n1;i++){
        	   System.out.println("Dependent"+i +" name:"+dep_name[i]);
        	   System.out.println("Sex:"+ gender1[i]);
        	   System.out.println("Dete of Birth:"+date1[i]);
        	   System.out.println("Relation:"+relation[i]);
           }
	      }  
	      }
	      rs.close();
	  }catch(SQLException se){
	     se.printStackTrace();
	  }catch(Exception e){
	      e.printStackTrace();
	   }
	   System.out.println("success");
	   System.exit(0);
	}
}