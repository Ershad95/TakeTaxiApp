package test.com.test;


// this Class Used For Get and Set Driver Data...
//this Class USed for Gson Lib...
public class DriverInfo {

    //Template Class For Convert Json To Object
    public String username;
    public String name;
    public String lname;
    public String password;
    public boolean check;
    public String status;
    public String token;


    public DriverInfo(String user,String name,String pass,String lname,String st,String t)
    {
        username=user;
        this.name=name;
        password=pass;
        this.lname=lname;
        check=false;
        status = st;
        token =t;
    }
    public DriverInfo()
    {

    }
}
