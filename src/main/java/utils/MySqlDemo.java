package utils;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
 
 
public class MySqlDemo {
    public static void main(String[] args) throws Exception {
//       insert();
    	search();
    }
    
    public static void insert()
	{
    	 Connection conn = null;
         String sql;
         // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
         // ������������Ҫָ��useUnicode��characterEncoding
         // ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ����� �������֮ǰ��Ҫ�ȴ���CalcSummarySim���ݿ�
         String url = "jdbc:mysql://202.117.54.81:3306/tong?user=root&password=tong&useUnicode=true&characterEncoding=utf-8";
  
         try {
          
             Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����   // ֮����Ҫʹ��������䣬����ΪҪʹ��MySQL����������������Ҫ��������������
             System.out.println("�ɹ�����MySQL��������");
             conn = DriverManager.getConnection(url);   // һ��Connection����һ�����ݿ�����
             Statement stmt = conn.createStatement();  // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
             sql = "insert into person(name,hight) values('ͬ','123213')";
             int result = stmt.executeUpdate(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
             if (result != -1) {
             	  System.out.println("�����ɹ�");
                 }else{
                   System.out.println("����failed");
                 }
         } catch (SQLException e) {
             System.out.println("MySQL��������");
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             try
			{
				conn.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("�ر�MySQLʧ��");
			}
         }
	}
    
    public static void search()
	{
    	Connection conn = null;
        String sql;
        // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
        // ������������Ҫָ��useUnicode��characterEncoding
        // ִ�����ݿ����֮ǰҪ�����ݿ����ϵͳ�ϴ���һ�����ݿ⣬�����Լ����� �������֮ǰ��Ҫ�ȴ���CalcSummarySim���ݿ�
        String url = "jdbc:mysql://202.117.54.81:3306/tong?user=root&password=tong&useUnicode=true&characterEncoding=utf-8";
 
        try {
         
            Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����   // ֮����Ҫʹ��������䣬����ΪҪʹ��MySQL����������������Ҫ��������������
            System.out.println("�ɹ�����MySQL��������");
            conn = DriverManager.getConnection(url);   // һ��Connection����һ�����ݿ�����
            Statement stmt = conn.createStatement();  // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
            sql = "select *from person";
            ResultSet result = stmt.executeQuery(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
            while (result.next()) {  
                String name = result.getString("name");  
                String year = result.getString("hight");  
                System.out.println(name+ "\t" +year );  
            }
        } catch (SQLException e) {
            System.out.println("MySQL��������");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
			{
				conn.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("�ر�MySQLʧ��");
			}
        }
    	
		
	}
 
}