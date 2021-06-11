import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:hive2://bigdata129.depts.bingosoft.net:22129/user33_db";
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(url,"user33","pass@bingo33");
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM t_rk_jbxx limit 10";
        System.out.println("Running"+sql);
        ResultSet res = stmt.executeQuery(sql);
        while(res.next()){
            System.out.println("asjbh:"+res.getString(4)+"\tajmc: "+res.getString(1)+"\tbamjbh: "+res.getString(8));
        }
    }

}
