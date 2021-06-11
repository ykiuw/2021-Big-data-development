import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class Query {
    JFrame jf = new JFrame("Spark SQL查询分析器");
    private JScrollPane scrollPane;
    private JButton execBn = new JButton("查询");
    //用来输入查询语句的文本框
    private JTextField sqlField = new JTextField(45);
    private static Connection conn;
    private static Statement stmt;

    public Query(String url,String user,String pass){
        try {
            //用Properties类加载属性文件
            Properties prop = new Properties();
            //prop.load(new FileInputStream("mysql.ini"));

            prop.setProperty("driver", "org.apache.hive.jdbc.HiveDriver");
            prop.setProperty("url",url);
            prop.setProperty("user",user);
            prop.setProperty("pass",pass);
        /*
        prop.setProperty("url", "jdbc:hive2://bigdata129.depts.bingosoft.net:22129/user33_db");
        prop.setProperty("user", "user33");
        prop.setProperty("pass", "pass@bingo33");
        */
            String drivers = prop.getProperty("driver");
            String Url = prop.getProperty("url");
            String User = prop.getProperty("user");
            String Pass = prop.getProperty("pass");

            Class.forName(drivers);
            conn = DriverManager.getConnection(Url, User, Pass);
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化界面
    public void init(List<String> list) {
        JPanel top = new JPanel();
        top.add(new JLabel("输入查询语句"));
        top.add(sqlField);
        top.add(execBn);
        JPanel bottom = new JPanel();
        //swing树
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("user");
        String type[]={"Tables","Views","Materialized Views","Functions","Queries","Backups"};
        DefaultMutableTreeNode node = null;
        DefaultMutableTreeNode childNode=null;
        for(int i=0;i<type.length;i++){
            node = new DefaultMutableTreeNode(type[i]);
            if(i==0){
                for(int j=0;j<list.size();j++){
                    childNode = new DefaultMutableTreeNode(list.get(j));
                    node.add(childNode);
                }
            }
            root.add(node);
        }
        JTree tree = new JTree(root);
        bottom.add(tree);
        bottom.setVisible(true);
        //为执行按钮，单行文本框添加事件监听器
        execBn.addActionListener(new ExceListener());
        sqlField.addActionListener(new ExceListener());
        jf.add(bottom,BorderLayout.WEST);
        jf.add(top, BorderLayout.NORTH);
        jf.setSize(680, 480);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
    //定义监听器
    class ExceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            //删除原来的JTable(JTable使用scrollPane来包装)
            if (scrollPane != null) {
                jf.remove(scrollPane);
            }
            try (
                ResultSet rs = stmt.executeQuery(sqlField.getText())) {
                //取出ResultSet的MetaData
                ResultSetMetaData rsmd = rs.getMetaData();
                Vector<String> columnNames = new Vector<>();
                Vector<Vector<String>> data = new Vector<>();
                //把ResultSet的所有列名添加到vector里
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    //columnNames.add(rsmd.getColumnName(i+1));
                    columnNames.add(rsmd.getColumnLabel(i+1));
                }
                //把ResultSet的所有记录添加到vector里
                while (rs.next()) {
                    Vector<String> v = new Vector<>();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        v.add(rs.getString(i+1));
                    }
                    data.add(v);
                }
                //创建新的JTable
                JTable table = new JTable(data, columnNames);
                scrollPane = new JScrollPane(table);
                //添加新的table
                jf.add(scrollPane);
                //更新主窗口
                jf.validate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) throws SQLException {
        System.out.println("Please input the url: ");
        Scanner input=new Scanner(System.in);
        String url = input.next();
        System.out.println("Please input the user name: ");
        String user=input.next();
        System.out.println("Please input the passward: ");
        String pass = input.next();

        Query query = new Query(url,user,pass);

        ResultSet resultSet = stmt.executeQuery("show tables");
        List<String> list=new ArrayList<>();
        try {
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                //输出所有表名
                System.out.println("tableName：" + tableName);
                list.add(tableName);
            }
            resultSet.close();
        } catch (SQLException e){
          e.printStackTrace();
        }
        query.init(list);

    }
}