package kg.tezal.tezal_back.dao;

import kg.tezal.tezal_back.model.BalanceHistoryLongModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BalanceHistoryDao {

    private final DataSource dataSource;

    @Autowired
    public BalanceHistoryDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }


    public List<BalanceHistoryLongModel> getCashierOperationHistory(Long userId, String dateFrom, String dateTo) {
        List<BalanceHistoryLongModel> historyList = new ArrayList();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select his.amount, his.created_date, his.invoice_amount, c.first_name, c.last_name" +
                    " from balance_history his " +
                    "    join balance b on his.balance_id = b.id " +
                    "    join client c on b.client_id = c.id " +
                    "where his.users_id = " + userId;
            if (dateFrom != null && dateTo !=null){
                sql = sql  + " and  his.created_date between '" + dateFrom + "' and '" + dateTo + "'";
            }
            sql = sql + " order by his.created_date DESC";
            ResultSet resultSet = stmt.executeQuery(sql);

            if(resultSet.next()){
                do {
                    BalanceHistoryLongModel history = new BalanceHistoryLongModel ();
                    history.setAmount(resultSet.getDouble("amount"));
                    history.setInvoiceAmount(resultSet.getDouble("invoice_amount"));
                    history.setDate(resultSet.getString("created_date"));
                    history.setFirstName(resultSet.getString("first_name"));
                    history.setLastName(resultSet.getString("last_name"));
                    historyList.add(history);
                } while (resultSet.next());
            } else {
                System.out.println("result set is empty");
            }
            resultSet.close();
        } catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    connection.close();
            } catch (SQLException se) {
                System.out.println(se);
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return historyList;
    }


}
