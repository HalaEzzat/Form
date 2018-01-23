
package form;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Row
{
    int ID;
    String Name,Email,Password;
    public Row(){}
    public Row(int id,String name,String email,String pass){
        ID=id;
        Name=name;
        Email=email;
        Password=pass;
    }
    public void setID(int id){ID=id;}
    public void setName(String name){Name=name;}
    public void setEmail(String email){Email=email;}
    public void setPass(String pass){Password=pass;}
    
    public int getID(){return ID;}
    public String getName(){return Name;}
    public String getEmail(){return Email;}
    public String getPass(){return Password;}
    
}
public class DBManager {

    Connection con;
    Statement stmt;
    ResultSet rs;
   public DBManager()
   {
       try
       {
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/ITI", "root", "*********");  
            stmt = con.createStatement(rs.TYPE_SCROLL_SENSITIVE,rs.CONCUR_UPDATABLE); 
            rs=stmt.executeQuery("select * from Student");
            //stmt.close();
            
       }catch(SQLException e)
       {
            System.out.println("problem with constructing a db connection !");
       }
   }

   public Row Next(){
       Row row=null;
        try {
            rs.next();
            row=new Row(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return row;
        } catch (SQLException ex) {
            //System.out.println("Error in Next() function in class DBManager");
        }
        return row;
   }
   
    public Row prev(){
       Row row=null;
        try {
            rs.previous();
            row=new Row(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return row;
        } catch (SQLException ex) {
            //System.out.println("Error in prev() function in class DBManager");
        }
        return row;
   }
   
     public Row first(){
       Row row=null;
        try {
            rs.first();
            row=new Row(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return row;
        } catch (SQLException ex) {
            System.out.println("Error in first() function in class DBManager");
        }
        return row;
   }
     
      public Row last(){
       Row row=null;
        try {
            rs.last();
            row=new Row(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            return row;
        } catch (SQLException ex) {
            System.out.println("Error in last() function in class DBManager");
        }
        return row;
   }
      
      public boolean search(String Email){
        boolean flag=false;
        try {
            while(rs.next()){
                if(rs.getString(3).equalsIgnoreCase(Email)){
                    flag=true;
                    break;
                }
                
            }
        } catch (SQLException ex) {
        }
        return flag;
      }
      
        public boolean search4ID(int id){
        boolean flag=false;
        try {
            while(rs.next()){
                if(rs.getInt(1)==id){
                    flag=true;
                    break;
                }
                
            }
        } catch (SQLException ex) {
        }
        return flag;
      }
      
      
      public boolean update(Row row){
          boolean flag=false,flag2=false;
          flag=search(row.getEmail());
          if(flag){
              flag2=false;
          }else{
               
              try {
                  PreparedStatement preparedStmt = con.prepareStatement("update Student set name = ? ,email= ?,password=? where id = ?");
                  preparedStmt.setString(1, row.getName());
                  preparedStmt.setString(2, row.getEmail());
                  preparedStmt.setString(3, row.getPass());
                  preparedStmt.setInt(4, row.getID());
                  preparedStmt.executeUpdate();
                  //con.close();
                  flag2=true;
                   rs=stmt.executeQuery("select * from Student");
              } catch (SQLException ex) {
              }
          }
          return flag2;
      }
      public boolean insert(Row row){
          boolean flag=false,flag2=false;
          flag=search(row.getEmail());
          if(flag){
              flag2=false;
          }else{
               
              try {
                  PreparedStatement preparedStmt = con.prepareStatement("insert into Student (name,email,password) values (?,?,?)");
                  preparedStmt.setString(1, row.getName());
                  preparedStmt.setString(2, row.getEmail());
                  preparedStmt.setString(3, row.getPass());
                  preparedStmt.executeUpdate();
                  //con.close();
                  flag2=true;
                   rs=stmt.executeQuery("select * from Student");
              } catch (SQLException ex) {
              }
          }
          return flag2;
      }
       public boolean delete(int id){
          boolean flag=false;
               
              try {
                  PreparedStatement preparedStmt = con.prepareStatement("delete from Student where id=?");
                  preparedStmt.setInt(1, id);
                  preparedStmt.executeUpdate();
                  //con.close();
                  flag=true;
                   rs=stmt.executeQuery("select * from Student");
              } catch (SQLException ex) {
              }
          
          return flag;
      }
    
}
