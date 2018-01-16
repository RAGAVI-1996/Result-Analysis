package eec.cse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassAnalysis {
	private static Connection connect;
	private static Statement statement1;
	private static Statement statement2;
	private static Statement statement3;
	private static	java.sql.PreparedStatement preparedStatement;
	private static String db="ra";
	static{
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/ra?"
              + "user=root&password=password");
		      
		      

		      // Statements allow to issue SQL queries to the database
		      statement1 = connect.createStatement();
		      statement2 = connect.createStatement();
		  } catch (Exception e) {
			  System.out.println(e);
			  e.printStackTrace();
		    }

	  }
	public static HashMap<String,String> passPercentage(String dept,String ccode, String batch,String section,String re){
		try {
			String grade="";
			Statement statement4 = connect.createStatement();
			Statement statement5 = connect.createStatement();
			Statement statement6 = connect.createStatement();
			Statement statement7 = connect.createStatement();
			Statement statement8 = connect.createStatement();
			Statement statement9 = connect.createStatement();
			Statement statement10 = connect.createStatement();
			Statement statement11 = connect.createStatement();
			Statement statement12 = connect.createStatement();
			ResultSet r7 = statement7.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 's'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet r8 = statement8.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 'a'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet r9 = statement9.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 'b'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet r10 = statement10.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 'c'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet r11 = statement11.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 'd'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet r12 = statement12.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" like 'e'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			
			
			while(r7.next())
			{
				grade+=" S Grade - "+String.valueOf(r7.getInt(1))+"<br>";
			}
			while(r8.next())
			{
				grade+=" A Grade - "+String.valueOf(r8.getInt(1))+"<br>";
			}
			while(r9.next())
			{
				grade+=" B Grade - "+String.valueOf(r9.getInt(1))+"<br>";
			}
			while(r10.next())
			{
				grade+=" C Grade - "+String.valueOf(r10.getInt(1))+"<br>";
			}
			while(r11.next())
			{
				grade+=" D Grade - "+String.valueOf(r11.getInt(1))+"<br>";
			}
			while(r12.next())
			{
				System.out.println(String.valueOf(r12.getInt(1)));
				grade+=" E Grade - "+String.valueOf(r12.getInt(1));
			}
			System.out.println(ccode);

			ResultSet rs = statement4.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+" not like 'ua'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			ResultSet rs1 = statement5.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated where "+ccode+"  like 'u'  and reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' ) ;"); // total failed in this sub			
			ResultSet rs2 = statement6.executeQuery("select * from "+db+".be"+dept+batch+"_"+section+"_staff where Subject_code like '"+ccode+"';");  //to retrieve staff name
			double totalAppeared = 0, totalFailed = 0;
			String st=null,sn=null;
			while(rs.next()){
				totalAppeared = rs.getInt(1);
				System.out.println("*************"+totalAppeared);

			}
			while(rs1.next()){
				totalFailed = rs1.getInt(1);
			}
			while(rs2.next()){
				st=rs2.getString("Name_of_faculty");
				sn=rs2.getString("Subject_name");
			}
			double totalPassed = totalAppeared - totalFailed;
			double passPercentage = (totalPassed/totalAppeared)*100;
			HashMap<String,String> passMap = new HashMap<String, String>();
			passMap.put("subject",sn);
			passMap.put("ccode",ccode);
			passMap.put("totalAppeared", Double.toString(totalAppeared));
			passMap.put("totalPassed",Double.toString(totalPassed));
			passMap.put("totalFailed",Double.toString(totalFailed));
			String pp=String.format("%.2f", passPercentage);
			passMap.put("passPercentage",pp);
			passMap.put("only",Integer.toString(FailureAnalysis.onlyIn(dept,ccode, batch, section)));
			passMap.put("staff",st);
			passMap.put("grades", grade);
			return passMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	 
	public static ArrayList<HashMap<String,String>> classResult(String dept,String semester,String batch,String section){
		try{
			Statement statement7=connect.createStatement();
			ArrayList<HashMap<String,String>> ar = new ArrayList<HashMap<String, String>>();
			Statement statement8=connect.createStatement();
			int bt=Integer.parseInt(batch);
			int regu=0;
			ResultSet rs4=statement8.executeQuery("select * from "+db+".regulation where regulation <= "+bt+";");
			while(rs4.next())
			{
				if(rs4.getInt("regulation")>regu)
					regu=rs4.getInt("regulation");
			}
			String re=String.valueOf(regu);
			if(batch.equals("2015"))
				re="2013";
			else if(batch.equals("2014"))
				re="2013";
			else if(batch.equals("2013"))
				re="2013";
			else if(batch.equals("2012"))
			{
				System.out.println("****************");
				re="2008";
			}
			ResultSet rs3=statement7.executeQuery("select *from "+db+".course_credits_info"+dept+re+" where semester="+semester+";");
			while(rs3.next())
			{
				HashMap<String,String> temp=passPercentage(dept,rs3.getString(1),batch,section,re);
				ar.add(temp);
			}
			return ar;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	public static HashMap<String,String> getStrength(String dept,String batch,String sec)
	{
		//int total;
		try
		{
		HashMap<String,String> total=new HashMap<String,String>();
		Statement statement4=connect.createStatement();
		ResultSet rs = statement2.executeQuery("select count(*) from "+db+".be"+dept+batch+"_allinfo where section like '"+sec+"' ;");  //total appeared in this subject
		while(rs.next())
		{
			total.put("total",Integer.toString(rs.getInt(1)));
		}
		
		return total;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	public static void main(String args[])
	{
		System.out.println(classResult("cse","1","2015","A"));
	}
}
