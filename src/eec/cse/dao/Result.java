package eec.cse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Result {
	private static Connection connect;
	private static Statement statement1;

	private static	java.sql.PreparedStatement preparedStatement;
	private static String db="aportal";
	static{
		  try {
		      // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		      // Setup the connection with the DB
		      connect = DriverManager
		          .getConnection("jdbc:mysql://localhost:3306/aportal?"
		              + "user=root&password=dna");

		      // Statements allow to issue SQL queries to the database
		      statement1 = connect.createStatement();
		  } catch (Exception e) {
			  System.out.println(e);
			  e.printStackTrace();
		    }

	  }
	public static String name(String dept,String reg,String batch)
	{
		try
		{
			ResultSet rs = statement1.executeQuery("select * from "+db+".be"+dept+batch+"_allinfo where reg like '"+reg+"';");
			while(rs.next())
				return (rs.getString("name"));
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	public static String result1(String dept,String sem,String batch,String section){
		try {
			String ans="";
			Statement statement4 = connect.createStatement();
			ans+="<tr><th>cy6151 Engineering Chemistry</th><th>ge6151 Computer programming</th><th>ge6152 Engineering Graphics</th><th>ge6161 Computer Practices Laboratory</th>";
			ans+="<tr><th>ge6162 Engineering Practices Laboratory</th><th>ge6163 Physics and Chemistry Laboratory</th><th>hs6151 Technical English I</th><th>ma6151 Mathematics I</th><th>ph6151 Engineering Physics I</th></tr>";

			ResultSet rs = statement4.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated reg in  (select reg from "+db+".becse"+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			while(rs.next())
			{
				ans+="<tr><td>"+rs.getString("reg")+"</td><td>"+name(dept,rs.getString("reg"),batch)+"</td>";
				ans+="<td>"+rs.getString("cy6151")+"</td>";
				ans+="<td>"+rs.getString("ge6151")+"</td>";
				ans+="<td>"+rs.getString("ge6152")+"</td>";
				ans+="<td>"+rs.getString("ge6162")+"</td>";
				ans+="<td>"+rs.getString("ge6162")+"</td>";
				ans+="<td>"+rs.getString("ge6163")+"</td>";
				ans+="<td>"+rs.getString("hs6151")+"</td>";
				ans+="<td>"+rs.getString("ma6151")+"</td>";
				ans+="<td>"+rs.getString("ph6151")+"</td></tr>";
			}
			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static String result2(String dept,String sem,String batch,String section){
		try {
			String ans="<center><h4>Semester 3 Academic year 2015-2016</h4><center><table>";
			Statement statement4 = connect.createStatement();
			ans+="<tr><th>cs6301 Programming and data structures II</th><th>cs6302 Database Management Systems</th><th>cs6303 Computer Architecture</th>";
			ans+="<tr><th>cs6304 Analog and digital Communication</th><th>cs6311 Programming and data structures Laboratory</th><th>hs6312 Database Management Systems Laboratory</th><th>ge6351 Environmental Science and Engineering</th><th>ma6351 Transforms and Partial differential Equations</th></tr>";

			ResultSet rs = statement4.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated reg in  (select reg from "+db+".becse"+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			while(rs.next())
			{
				ans+="<tr><td>"+rs.getString("reg")+"</td><td>"+name(dept,rs.getString("reg"),batch)+"</td>";
				ans+="<td>"+rs.getString("cs6301")+"</td>";
				ans+="<td>"+rs.getString("cs6302")+"</td>";
				ans+="<td>"+rs.getString("cs6303")+"</td>";
				ans+="<td>"+rs.getString("cs6304")+"</td>";
				ans+="<td>"+rs.getString("cs6311")+"</td>";
				ans+="<td>"+rs.getString("cs6312")+"</td>";
				ans+="<td>"+rs.getString("ge6351")+"</td>";
				ans+="<td>"+rs.getString("ma6351")+"</td><tr>";
			}
			ans+="</table>";
			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static String result3(String dept,String sem,String batch,String section){
		try {
			String ans="<center><h4>Semester 5 Academic year 2015-2016</h4><center><table>";
			Statement statement4 = connect.createStatement();
			ans+="<tr><th>cs6501 Internet Programming</th><th>cs6502 Object Oriented Analysis and Design</th><th>cs6503 Theory of computation</th>";
			ans+="<tr><th>cs6504 Computer Graphics</th><th>cs6511 Case tools Laboratory Laboratory</th><th>cs6512 Internet Programming Laboratory</th><th>cs6513 Computer Graphics Laboratory</th><th>ma6566 Discrete Mathematics</th></tr>";

			ResultSet rs = statement4.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated reg in  (select reg from "+db+".becse"+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			while(rs.next())
			{
				ans+="<tr><td>"+rs.getString("reg")+"</td><td>"+name(dept,rs.getString("reg"),batch)+"</td>";
				ans+="<td>"+rs.getString("cs6501")+"</td>";
				ans+="<td>"+rs.getString("cs6502")+"</td>";
				ans+="<td>"+rs.getString("cs6503")+"</td>";
				ans+="<td>"+rs.getString("cs6504")+"</td>";
				ans+="<td>"+rs.getString("cs6511")+"</td>";
				ans+="<td>"+rs.getString("cs6512")+"</td>";
				ans+="<td>"+rs.getString("cs6513")+"</td>";
				ans+="<td>"+rs.getString("ma6566")+"</td></tr>";
			}
			ans+="</table>";
			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static String result4(String dept,String sem,String batch,String section){
		try {
			String ans="<center><h4>Semester 7 Academic year 2015-2016</h4><center><table>";
			Statement statement4 = connect.createStatement();
			ans+="<tr><th>mg2453 Engineering Economics and Financial Accounting</th><th>cs2401 Computer Graphics</th><th>cs2402 Mobile and Pervasive Computing</th>";
			ans+="<tr><th>cs2403 Digital Signal Processing</th><th>cs2032 Data Warehousing  and Data Mining</th><th>cs2041 C# and .NET Framework</th><th>ge2022 Total Quality Management</th><th>cs2405 Computer Graphics Lab</th><th>cs2406Open Source Lab</th></tr>";

			ResultSet rs = statement4.executeQuery("select count(*) from "+db+".be"+dept+batch+"_consolidated reg in  (select reg from "+db+".becse"+batch+"_allinfo where section like '"+section+"' );");  //total appeared in this subject
			while(rs.next())
			{
				ans+="<tr><td>"+rs.getString("reg")+"</td><td>"+name(dept,rs.getString("reg"),batch)+"</td>";
				ans+="<td>"+rs.getString("mg2452")+"</td>";
				ans+="<td>"+rs.getString("cs2401")+"</td>";
				ans+="<td>"+rs.getString("cs2402")+"</td>";
				ans+="<td>"+rs.getString("cs2403")+"</td>";
				ans+="<td>"+rs.getString("cs2032")+"</td>";
				ans+="<td>"+rs.getString("cs2032")+"</td>";
				ans+="<td>"+rs.getString("cs2041")+"</td>";
				ans+="<td>"+rs.getString("ge2022")+"</td>";
				ans+="<td>"+rs.getString("cs2405")+"</td>";
				ans+="<td>"+rs.getString("cs2406")+"</td></tr>";

			}
			ans+="</table>";
			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static HashMap<String,String> result(String dept,String sem,String batch,String section)
	{
		HashMap<String,String> ans1=new HashMap<String,String>();
		String ans="";
		if(sem.equals("1"))
		{
			ans=result1(dept,sem,batch,section);
		}
		else if(sem.equals("3"))
		{
			ans=result2(dept,sem,batch,section);
		}
		else if(sem.equals("5"))
		{
			ans=result3(dept,sem,batch,section);
		}
		else if(sem.equals("7"))
		{
			ans=result4(dept,sem,batch,section);
		}
		ans1.put("res", ans);
		return ans1;
	}
	public static void main(String args[])
	{
		System.out.println(result("cse","1","2015","A"));
	}
}
