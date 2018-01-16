package eec.cse;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import eec.cse.dao.ClassAnalysis;
import eec.cse.dao.Department;
import eec.cse.dao.FailureAnalysis;
import eec.cse.dao.GpaCgpa;

// The Java class will be hosted at the URI path "/resultAnalysis"
@Path("/resultAnalysis")
public class ResultAnalysis {
    
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }
    
	@Path("/passPercentage")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String passPercentage(  @DefaultValue("ece") @QueryParam("dept") String dept, @DefaultValue("1") @QueryParam("sem") String sem,  @DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("B") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(ClassAnalysis.classResult(dept,sem,batch,section));
	}
	
	
	
	@Path("/getStrength")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String getStrength(   @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("B") @QueryParam("section") String section,@DefaultValue("1") @QueryParam("sem") String sem){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(ClassAnalysis.getStrength(dept,batch,section));
	}
	@Path("/failnone")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failnone(  @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("1") @QueryParam("noOfSub") String noOfSub,  @DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.failuresListNone(dept,batch,sem,section));
	}
	@Path("/failone")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failone(  @DefaultValue("ece") @QueryParam("dept") String dept,  @DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.failuresListOne(dept,batch,sem ,section));
	}
	@Path("/failtwo")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failtwo(  @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("1") @QueryParam("noOfSub") String noOfSub,  @DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.failuresListTwo(dept,batch,sem ,section));
	}
	
	@Path("/failmore")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failmore(  @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.failuresListMore(dept,batch,sem ,section));
	}
	
	@Path("/failin")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failin(  @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("cs6201") @QueryParam("ccode") String ccode,@DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.failuresIn(dept,ccode,batch,sem,section));
	}
	@Path("/getsub")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String getsub( @DefaultValue("2") @QueryParam("sem") String sem,@DefaultValue("2015") @QueryParam("batch") String batch,@DefaultValue("ece") @QueryParam("dept") String dept){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.getSub(sem,batch,dept));
	}
	@Path("/calculategpa")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String calculategpa( @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(GpaCgpa.calculateGPA(dept,batch,sem,section));
	}
	
	
	@Path("/calculatecgpa")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String calculatecgpa( @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("A") @QueryParam("section") String section,@DefaultValue("1") @QueryParam("sem") String sem){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(GpaCgpa.calculateCGPA(dept,batch,section));
	}
	
	@Path("/failcount")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failcount(  @DefaultValue("ece") @QueryParam("dept") String dept,@DefaultValue("2013") @QueryParam("batch") String batch,  @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		if(sem.equals("3")||sem.equals("4"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-1);
		}
		if(sem.equals("5")||sem.equals("6"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-2);
		}
		if(sem.equals("7")||sem.equals("8"))
		{
			batch=String.valueOf(Integer.parseInt(batch)-3);
		}
		Gson gson = new Gson();
		return gson.toJson(FailureAnalysis.count(dept,batch,sem,section));
	}
	
	@Path("/department")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String department( @DefaultValue("2015") @QueryParam("batch") String batch,@DefaultValue("7") @QueryParam("sem") String sem){
		Gson gson = new Gson();
		return gson.toJson(Department.classResult(batch,sem));
	}
	/*@Path("/failsub")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failsub(  @DefaultValue("ge6151") @QueryParam("ccode") String ccode,  @DefaultValue("2015") @QueryParam("batch") String batch, @DefaultValue("A") @QueryParam("section") String section){
		Gson gson = new Gson();
		return gson.toJson(FailuresList.subjectFailuresList("cs6202","2015","A"));
	}
	@Path("/failcount")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public String failcount(  @DefaultValue("2013") @QueryParam("batch") String batch,  @DefaultValue("1") @QueryParam("sem") String sem, @DefaultValue("A") @QueryParam("section") String section){
		Gson gson = new Gson();
		return gson.toJson(FailuresList.count(batch,sem,section));
	}*/
}