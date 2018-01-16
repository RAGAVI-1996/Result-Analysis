package eec.cse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.jasper.tagplugins.jstl.core.Set;

public class Department {
	private static Connection connect;
	private static Statement statement1;
	private static Statement statement2;
	private static Statement statement8;
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
		      statement8 = connect.createStatement();
		  } catch (Exception e) {
			  System.out.println(e);
			  e.printStackTrace();
		    }

	  }
	
	public static LinkedHashMap<String,String> getCourseMap(String dept,String sem,String batch){
		try{
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
				re="2008";
			ResultSet rsc = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where semester="+sem+";");
			LinkedHashMap<String,String> courseMap = new LinkedHashMap<String,String>();
			while(rsc.next()){
				courseMap.put(rsc.getString(1), rsc.getString(2));
			}
			System.out.println(courseMap);
			return courseMap;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static LinkedHashMap<String,String> resultList(String dept,String batch,String sem){
		String course="",grade="";
		ResultSet consol_rs;
		try{
			LinkedHashMap<String,String> ans = new LinkedHashMap<String,String>();
			LinkedHashMap<String,String> courseMap = getCourseMap(dept,sem,batch);
			float total=0,totFailed=0;
			int flag=1;
			 consol_rs = statement2.executeQuery("select * from "+db+".be"+dept+batch+"_consolidated; ");
			while(consol_rs.next())
			{
				
				Iterator<String> it = courseMap.keySet().iterator();
				while(it.hasNext()){
					course = it.next().toString();
					grade = consol_rs.getString(course);
					if(grade != null){
						if(grade.equalsIgnoreCase("ua"))
							flag=0;
						if(grade.equalsIgnoreCase("u"))
							flag=0;
					}
				}
				if(flag==0)
				{
					totFailed++;
				}
				total++;
				flag=1;
			}
			ans.put("fail", String.valueOf(totFailed));
			ans.put("total", String.valueOf(total));
			System.out.println(ans);
			return ans;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return null;
	}
	
	public static HashMap<String,String> passPercentage(String batch,String dept,String dname,String sem){
		LinkedHashMap<String,String> ans=new LinkedHashMap<String,String>();
		try {
			String first="";
			String second="";
			String third="";
			String fourth="";
			String firstbatch="";
			String secondbatch="";
			String thirdbatch="";
			String fourthbatch="";
			if(sem.equals("odd"))
			{
				first="1";
				firstbatch=batch;
				second="3";
				secondbatch=String.valueOf(Integer.parseInt(batch)-1);
				third="5";
				thirdbatch=String.valueOf(Integer.parseInt(batch)-2);
				fourth="7";
				fourthbatch=String.valueOf(Integer.parseInt(batch)-3);
			}
			else if(sem.equals("even"))
			{
				first="2";
				firstbatch=batch;
				second="4";
				secondbatch=String.valueOf(Integer.parseInt(batch)-1);
				third="6";
				thirdbatch=String.valueOf(Integer.parseInt(batch)-2);
				fourth="8";
				fourthbatch=String.valueOf(Integer.parseInt(batch)-3);
			}
			
			LinkedHashMap<String,String> pass1=resultList(dept,firstbatch,first);
			LinkedHashMap<String,String> pass2=resultList(dept,secondbatch,second);
			LinkedHashMap<String,String> pass3=resultList(dept,thirdbatch,third);
			LinkedHashMap<String,String> pass4=resultList(dept,fourthbatch,fourth);
			float fail1=0,fail2=0,fail3=0,fail4=0,fail=0,total1=0,total2=0,total3=0,total4=0,total=0;
			
			Collection c=pass1.values();
			Iterator it=c.iterator();
			if(it.hasNext())
			{
					fail1=Float.parseFloat((String) it.next());
			}
			if(it.hasNext())
			{
					total1=Float.parseFloat((String) it.next());
			}
			
			c=pass2.values();
			it=c.iterator();
			if(it.hasNext())
			{
					fail2=Float.parseFloat((String) it.next());
			}
			if(it.hasNext())
			{
					total2=Float.parseFloat((String) it.next());
			}
			
			c=pass3.values();
			it=c.iterator();
			if(it.hasNext())
			{
					fail3=Float.parseFloat((String) it.next());
			}
			if(it.hasNext())
			{
					total3=Float.parseFloat((String) it.next());
			}
			
			c=pass4.values();
			it=c.iterator();
			if(it.hasNext())
			{
					fail4=Float.parseFloat((String) it.next());

			}
			if(it.hasNext())
			{
					total4=Float.parseFloat((String) it.next());
			}
			
			fail=fail1+fail2+fail3+fail4;
			total=total1+total2+total3+total4;
						
			float pass=((total-fail)*100)/total;
			float p1,p2,p3,p4;
			p1=(((total1-fail1)*100)/total1);
			p2=(((total2-fail2)*100)/total2);
			p3=(((total3-fail3)*100)/total3);
			p4=(((total4-fail4)*100)/total4);

			ans.put("dept", dname);
			ans.put("first", String.format("%.2f",p1));
			ans.put("second", String.format("%.2f",p2));
			ans.put("third", String.format("%.2f",p3));
			ans.put("fourth", String.format("%.2f",p4));
			ans.put("pass", String.format("%.2f",pass));
			return ans;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception");
		}
		return null;
		
	}
	
	public static ArrayList<HashMap<String,String>> classResult(String batch,String sem){
		try{
			Statement statement3=connect.createStatement();
			ArrayList<HashMap<String,String>> ar = new ArrayList<HashMap<String, String>>();

			ResultSet rs3=statement3.executeQuery("select *from "+db+".departments");
			while(rs3.next())
			{
				System.out.println(rs3.getString("dept_code"));
				HashMap<String,String> temp=passPercentage(batch,rs3.getString("dept_code"),rs3.getString("dept_name"),sem);
				ar.add(temp);
			}
			System.out.println(ar);
			return ar;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	public static void main(String args[])
	{
		System.out.println(classResult("2015","odd"));
	}
}