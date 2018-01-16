package eec.cse.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class FailureAnalysis {
	private static Connection connect;
	private static Statement statement1;
	private static Statement statement2;
	private static Statement statement3;
	private static Statement statement8;
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
		      statement8 = connect.createStatement();
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
	public static String sname(String ccode,String batch,String dept)
	{
		try
		{
			int regu=0;
			int bt=Integer.parseInt(batch);
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
			ResultSet rs = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where course_code like '"+ccode+"';");
			while(rs.next())
				return (rs.getString("course_title"));
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
		public static LinkedHashMap<String,String> getCourseMap(String sem,String batch,String dept){
		try{
			int regu=0;
			int bt=Integer.parseInt(batch);
			ResultSet rs4=statement8.executeQuery("select * from "+db+".regulation where regulation <= "+bt+";");
			while(rs4.next())
			{
				if(rs4.getInt("regulation")>regu)
					regu=rs4.getInt("regulation");
			}
			String re=String.valueOf(regu);
			ResultSet rsc = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where semester="+sem+";");
			LinkedHashMap<String,String> courseMap = new LinkedHashMap<String,String>();
			while(rsc.next()){
				courseMap.put(rsc.getString(1), rsc.getString(2));
			}
			return courseMap;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
		private static LinkedHashMap<String,ArrayList<String>> resultList(String dept,String batch,String sem,String section){
			try{
				int regu=0;
				int bt=Integer.parseInt(batch);
				ResultSet rs4=statement8.executeQuery("select *from regulation where regulation <= "+bt+";");
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
				LinkedHashMap<String,String> courseMap = getCourseMap(sem,batch,dept);
				ResultSet consol_rs = statement2.executeQuery("select * from "+db+".be"+dept+batch+"_consolidated  where reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+section+"' ) ;; ");//where reg like '310614104001';");
				LinkedHashMap<String,ArrayList<String>> arrearMap = new LinkedHashMap<String, ArrayList<String>>();
				while(consol_rs.next()){
					ArrayList<String> arrearList = new ArrayList<String>();
					Iterator it = courseMap.keySet().iterator();
					while(it.hasNext()){
						String course = it.next().toString();
						String grade = consol_rs.getString(course);
						if(grade != null){
							//if(grade.equalsIgnoreCase("u")) arrearList.add(course+" "+ courseMap.get(course));
							if(grade.equalsIgnoreCase("u")) arrearList.add(course);
						}
					}
					arrearMap.put(consol_rs.getString("reg"),arrearList );				
				}
				return arrearMap;
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;	
		}
		
		public static HashMap<String,String> nameRet(String dept,String reg,String batch)
		{
			HashMap<String,String> lhm=new HashMap<String,String>();
			try{
					ResultSet rs= statement2.executeQuery("select * from "+db+".be"+dept+batch+"_allinfo where reg like '"+reg+"'" );//where reg like '310614104001';");
					rs.next();
					lhm.put("reg", reg);
					lhm.put("name", rs.getString("name"));
				return lhm;
			
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			return null;
		} 
		public static int onlyIn(String dept,String ccode,String batch,String sec)
		{
			int count=0;
			String sem=null;
			try
			{
				int regu=0;
				int bt=Integer.parseInt(batch);
				ResultSet rs4=statement8.executeQuery("select * from "+db+".regulation where regulation <= "+bt+";");
				while(rs4.next())
				{
					if(rs4.getInt("regulation")>regu)
						regu=rs4.getInt("regulation");
				}
				String re=String.valueOf(regu);
				Statement statement4=connect.createStatement();
				ResultSet rs=statement4.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where course_code like '"+ccode+"';");
				while(rs.next())
				{
				sem=rs.getString("semester");
				}
			}
			catch(Exception e){ }
			LinkedHashMap<String,ArrayList<String>> rl=resultList(dept,batch,sem,sec);
			Iterator<String> studentList = rl.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				if(rl.get(student).size() == 1)
				{
					if((rl.get(student).get(0)).equalsIgnoreCase(ccode))	
					{
						count++;
					}
				}
			}
			return count;
		}
		public static ArrayList<LinkedHashMap<String,String>> failuresListNone(String dept,String batch,String sem,String section){
			
			ArrayList<LinkedHashMap<String,String>> ans=new ArrayList<LinkedHashMap<String,String>>();
			HashMap<String,ArrayList<String>> allFailuresList = resultList(dept,batch,sem,section);
			System.out.println(allFailuresList);
			Iterator<String> studentList = allFailuresList.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				
				if(allFailuresList.get(student).size() == 0)
				{
						LinkedHashMap<String,String> temp = new LinkedHashMap<String,String>();
						temp.put("reg", student);
						temp.put("name", name(dept,student,batch));
						ans.add(temp);

				}
			}
			return ans;
		}
		public static ArrayList<LinkedHashMap<String,String>> failuresListOne(String dept,String batch,String sem,String section){
			
			ArrayList<LinkedHashMap<String,String>> ans=new ArrayList<LinkedHashMap<String,String>>();
			HashMap<String,ArrayList<String>> allFailuresList = resultList(dept,batch,sem,section);
			System.out.println(allFailuresList);
			Iterator<String> studentList = allFailuresList.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				
				if(allFailuresList.get(student).size() == 1)
				{
						LinkedHashMap<String,String> temp = new LinkedHashMap<String,String>();
						String ccode=allFailuresList.get(student).get(0);
						temp.put("ccode", ccode);
						temp.put("reg", student);
						temp.put("name", name(dept,student,batch));
						temp.put("sname", sname(ccode,batch,dept));
						ans.add(temp);

				}
			}
			return ans;
		}
public static ArrayList<LinkedHashMap<String,String>> failuresListTwo(String dept,String batch,String sem,String section){
			
			ArrayList<LinkedHashMap<String,String>> ans=new ArrayList<LinkedHashMap<String,String>>();
			HashMap<String,ArrayList<String>> allFailuresList = resultList(dept,batch,sem,section);
			System.out.println(allFailuresList);
			Iterator<String> studentList = allFailuresList.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				
				if(allFailuresList.get(student).size() == 2)
				{
						LinkedHashMap<String,String> temp = new LinkedHashMap<String,String>();
						String ccode1=allFailuresList.get(student).get(0);
						String ccode2=allFailuresList.get(student).get(1);
						temp.put("ccode1", ccode1);
						temp.put("ccode2", ccode2);
						temp.put("reg", student);
						temp.put("name", name(dept,student,batch));
						temp.put("sname1", sname(ccode1,batch,dept));
						temp.put("sname2", sname(ccode2,batch,dept));
						ans.add(temp);
				}
			}
			return ans;
		}
public static String getSubName(String ccode,String dept,String batch){
	try{
		int regu=0;
		int bt=Integer.parseInt(batch);
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
		ResultSet rsc = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where course_code like '"+ccode+"';");
		if(rsc.next()){
			return rsc.getString("course_title");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
		public static ArrayList<LinkedHashMap<String,String>> failuresListMore(String dept,String batch,String sem,String section){
			ArrayList<LinkedHashMap<String,String>> ans=new ArrayList<LinkedHashMap<String,String>>();
			HashMap<String,ArrayList<String>> allFailuresList = resultList(dept,batch,sem,section);
			System.out.println(allFailuresList);
			Iterator<String> studentList = allFailuresList.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				
				if(allFailuresList.get(student).size() >= 3)
				{
					LinkedHashMap<String,String> temp = new LinkedHashMap<String,String>();
					temp.put("reg", student);
					temp.put("name", name(dept,student,batch));
					String sub="";
					int count=0;
					for(int i=0;i<allFailuresList.get(student).size();i++)
					{
						sub+=allFailuresList.get(student).get(i);
						sub+="  ";
						sub+=getSubName(allFailuresList.get(student).get(i),dept,batch);
						sub+="<br>";
						count++;
					}
					temp.put("count", Integer.toString(count));
					temp.put("sub", sub);
					ans.add(temp);
				}
			}
			return ans;
		}
		public static ArrayList<HashMap<String,String>> failuresIn(String dept,String ccode,String batch,String sem,String section){
			
			ArrayList<HashMap<String,String>> ans=new ArrayList<HashMap<String,String>>();
						
			LinkedHashMap<String,ArrayList<String>> allFailuresListMap = resultList(dept,batch,sem,section);
			System.out.println(allFailuresListMap);
			Iterator<String> studentList = allFailuresListMap.keySet().iterator();
			while(studentList.hasNext()){
				String student = studentList.next();
				
				int no = allFailuresListMap.get(student).size();
				for(int i=0;i<no;i++)
				{
							
					if(allFailuresListMap.get(student).get(i).equalsIgnoreCase(ccode))
					{
						HashMap<String,String> temp=nameRet(dept,student,batch);
						ans.add(temp);
						continue;
					}
				}
			}
			return ans;			
		}
		public static int getStrength(String dept,String batch,String sec)
		{
			//int total;
			try
			{
			int total=0;
			Statement statement4=connect.createStatement();
			ResultSet rs = statement2.executeQuery("select count(*) from "+db+".be"+dept+batch+"_allinfo where section like '"+sec+"' ;");  //total appeared in this subject
			while(rs.next())
			{
				total=rs.getInt(1);
			}
			
			return total;
			}
			catch(Exception e)
			{
				
			}
			return 0;
		}
		public static ArrayList<String> count(String dept,String batch,String sem,String sec)
		{
			
			int pass,one,two,three;
			ArrayList<String> ct=new ArrayList<String>() ;
			ArrayList<LinkedHashMap<String,String>> temp=new ArrayList<LinkedHashMap<String,String>>();
			int strength=getStrength(dept,batch,sec);
			
			temp=failuresListNone(dept,batch,sem,sec);
			pass=temp.size();
			float passed=(float) pass;
			float pass1=(passed*100)/strength;

			String pp=String.format("%.2f", pass1);
			
			temp=failuresListOne(dept,batch,sem,sec);
			one=temp.size();
			temp=failuresListTwo(dept,batch,sem,sec);
			two=temp.size();
			temp=failuresListMore(dept,batch,sem,sec);
			three=temp.size();
			ct.add(Integer.toString(pass)+"<br>"+String.valueOf(pp)+"%");
			ct.add(Integer.toString(one));
			ct.add(Integer.toString(two));
			ct.add(Integer.toString(three));			
			return ct;
		}

		public static ArrayList<LinkedHashMap<String,String>> getSub(String sem,String batch,String dept){
			try{
				int regu=0;
				int bt=Integer.parseInt(batch);
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
				ArrayList<LinkedHashMap<String,String>> ans = new ArrayList<LinkedHashMap<String,String>>();
				
				while(rsc.next()){
					LinkedHashMap<String,String> temp=new LinkedHashMap<String,String>();

					temp.put("ccode", rsc.getString("course_code"));
					temp.put("sname", rsc.getString("course_title"));
					ans.add(temp);
				}
				return ans;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		public static void main(String args[])
		{
			//System.out.println(getStrength("cse","2014","A"));
			//System.out.println(failuresIn("cse","ge6152","2014","1","A"));
			System.out.print(count("cse","2015","1","B"));
		}
}