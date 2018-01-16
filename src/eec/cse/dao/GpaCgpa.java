package eec.cse.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class GpaCgpa{
    private static Connection connect;
	private static Statement statement1;
	private static Statement statement2;
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
	public static LinkedHashMap<String,Integer> getCourseCreditsMapgpa(String sem,String batch,String dept){
		try {
			
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
				re="2008";
			ResultSet rsc = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+" where semester="+sem+" ;");
			LinkedHashMap<String,Integer> creditsMap = new LinkedHashMap<String,Integer>();
			while(rsc.next()){
				//get the ccode and credeits alone and put it in a hash table
				creditsMap.put(rsc.getString(1), Integer.valueOf(rsc.getInt(4)));
			}
			//System.out.println(creditsMap);
					
			return creditsMap;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static LinkedHashMap<String,Integer> getCourseCreditsMapcgpa(String batch,String dept){
		try {
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
			ResultSet rsc = statement1.executeQuery("select * from "+db+".course_credits_info"+dept+re+"  ;");
			LinkedHashMap<String,Integer> creditsMap = new LinkedHashMap<String,Integer>();
			
			
			    while(rsc.next()){
				//get the ccode and credeits alone and put it in a hash table
				creditsMap.put(rsc.getString(1), Integer.valueOf(rsc.getInt(4)));
			    }
			//System.out.println(creditsMap);
					
			return creditsMap;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//calculate gpa
	public static ArrayList<LinkedHashMap<String,String>> calculateGPA(String dept,String batch,String sem,String sec){
		try{
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
				re="2008";
			LinkedHashMap<String,Integer> course_credits = getCourseCreditsMapgpa(sem,batch,dept);
		    ResultSet consol_rs = statement2.executeQuery("select * from "+db+".be"+dept+batch+"_consolidated where reg in  (select reg from be"+dept+batch+"_allinfo where section like '"+sec+"' ); ");//where reg like '310614104001';");
			ArrayList<LinkedHashMap<String,String>> ans = new ArrayList<LinkedHashMap<String,String>> ();
			ResultSet rs1;
			
			while(consol_rs.next()){
				rs1=statement1.executeQuery("select *from "+db+".be"+dept+batch+"_allinfo where reg like'"+consol_rs.getString("reg")+"';");
				rs1.next();
				Iterator it = course_credits.keySet().iterator();
				float temp = 0;int total =0;
				while(it.hasNext()){
					String course = it.next().toString();
					//System.out.print(course);
					String grade = consol_rs.getString(course);
					//System.out.print(grade);;
					if(grade != null){
						if(grade.equalsIgnoreCase("s")) temp += 10 * course_credits.get(course);
						else if (grade.equalsIgnoreCase("a")) temp += 9 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("b")) temp += 8 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("c")) temp += 7 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("d")) temp += 6 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("e")) temp += 5 * course_credits.get(course);
						else  temp += 0 * course_credits.get(course);
						if((!grade.equalsIgnoreCase("u"))&&(!grade.equalsIgnoreCase("ua")))
								total += course_credits.get(course);
						//System.out.println("total:"+total);
						
						//System.out.println("CGPA:" +temp/total);
					}
				}
				LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
				
				temp=temp/total;
				map.put("reg",consol_rs.getString("reg"));
				map.put("name",rs1.getString("name"));
				map.put("gpa",String.format("%.2f", temp));
				ans.add(map);
				
			}
			System.out.println(ans);

			return ans;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	public static ArrayList<LinkedHashMap<String,String>> nameRet(LinkedHashSet<String> reglist,String batch,String dept)
	{
		ArrayList<LinkedHashMap<String,String>> lhm=new ArrayList<LinkedHashMap<String,String>>();
		try{
			Iterator<String> itr=reglist.iterator();
			while(itr.hasNext())
			{
				String t=itr.next();
				ResultSet rs= statement2.executeQuery("select * from "+db+".be"+dept+batch+"_allinfo where reg like '"+t+"'" );
				LinkedHashMap<String,String> temp=new LinkedHashMap<String,String>();
				while(rs.next())
				{
				temp.put("reg", t);
				temp.put("name", rs.getString("name"));
				}
				lhm.add(temp);
			}
			System.out.print(lhm);
			return lhm;
		
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}
	
	//calculate CGPA
	public static ArrayList<LinkedHashMap<String,String>> calculateCGPA(String dept,String batch,String sec){
		try{
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
				re="2008";
			LinkedHashMap<String,Integer> course_credits = getCourseCreditsMapcgpa(batch,dept);
			ResultSet consol_rs = statement2.executeQuery("select * from "+db+".be"+dept+batch+"_consolidated where reg in  (select reg from "+db+".be"+dept+batch+"_allinfo where section like '"+sec+"' ); ");//where reg like '310614104001';");
			ArrayList<LinkedHashMap<String,String>> ans = new ArrayList<LinkedHashMap<String,String>>();
			ResultSet rs1;
			while(consol_rs.next()){
				rs1=statement1.executeQuery("select *from "+db+".be"+dept+batch+"_allinfo where reg like'"+consol_rs.getString("reg")+"';");
				rs1.next();
				Iterator it = course_credits.keySet().iterator();
				float temp = 0;int total =0;
				while(it.hasNext()){
					String course = it.next().toString();
					//System.out.print(course);
					String grade = consol_rs.getString(course);
					//System.out.print(grade);;
					if(grade != null){
						if(grade.equalsIgnoreCase("s")) temp += 10 * course_credits.get(course);
						else if (grade.equalsIgnoreCase("a")) temp += 9 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("b")) temp += 8 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("c")) temp += 7 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("d")) temp += 6 * course_credits.get(course);
						else if(grade.equalsIgnoreCase("e")) temp += 5 * course_credits.get(course);
						else  temp += 0 * course_credits.get(course);
						if(!grade.equalsIgnoreCase("U"))
							total += course_credits.get(course);
						//System.out.println("total:"+total);
						
						//System.out.println("CGPA:" +temp/total);
					}
				}
				LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
				
				temp=temp/total;
				map.put("reg",consol_rs.getString("reg"));
				map.put("name",rs1.getString("name"));
				map.put("cgpa",String.format("%.2f",temp));
				ans.add(map);
			}
			return ans;
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	/*public void updateCGPA(String batch,String sec){
		ArrayList<LinkedHashMap<String,String>> resultMap = calculateCGPA(batch,sec);
		String table_name = null ;
		try {
			 Iterator i =resultMap.keySet().iterator();
			 while(i.hasNext()){
				 String reg = i.next().toString();
				 preparedStatement = connect
					        .prepareStatement("UPDATE becse"+batch+"_consolidated SET CGPA=? WHERE REG like ?");
					    preparedStatement.setFloat(1, (float) resultMap.get(reg));
					    preparedStatement.setString(2, reg);
					    preparedStatement.executeUpdate();
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Update done");

	}*/

				
		
	public static void main(String[] args) {
		
		//CGPA cgpa=new CGPA();
		//cgpa.updateCGPA("2014");
		//System.out.print(GpaCgpa.getCourseCreditsMapcgpa());
		System.out.print(GpaCgpa.calculateGPA("cse","2015","1","A"));
		//System.out.print(GpaCgpa.calculateCGPA("cse","2014","B"));
		
}
}