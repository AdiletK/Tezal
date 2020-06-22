package kg.tezal.tezal_back.dao;

import kg.tezal.tezal_back.model.StatisticsModel;
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
public class StatisticsDao {

    private final DataSource dataSource;

    @Autowired
    public StatisticsDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }


    public List<StatisticsModel> getSoldProductsByOrgId(Long orgId) {
        List<StatisticsModel> arrayList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select rm.name, sum(o_p.number_of) " +
                    "                        from orders_product o_p " +
                    "                             join orders o on o_p.orders_id = o.id " +
                    "                             join raw_material rm on o_p.raw_material_id = rm.id " +
                    "                             join organization org on o.organization_id = org.id " +
                    "                             join client cl on o.client_id = cl.id " +
                    "                    where o.organization_id = " + orgId + " and o.orders_status='DELIVERED' " +
                    "                    group by rm.name " +
                    "                    order by sum(o_p.number_of) desc " +
                    "                    limit 10";

            getStatisticModel(arrayList, stmt, sql);
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
        return arrayList;
    }

    public List<StatisticsModel> getOrderStatisticByOrgId(Long orgId, String dateFrom, String dateTo) {
        List<StatisticsModel> arrayList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select EXTRACT(MONTH FROM o.create_date) as month, count(o.id) " +
                    "from orders o " +
                    "         join organization org on o.organization_id = org.id " +
                    "         join client cl on o.client_id = cl.id " +
                    "where o.organization_id = " + orgId + " and orders_status='DELIVERED' and o.create_date between '" + dateFrom + "' and '" + dateTo + "' " +
                    "group by month " +
                    "order by month ";

            getStatisticModel(arrayList, stmt, sql);

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
        return arrayList;
    }

    public List<StatisticsModel> getUsersStatisticByOrgId(Long orgId, String dateFrom, String dateTo) {
        List<StatisticsModel> arrayList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select u.username, count(o.id) " +
                    "from orders o " +
                    "         join organization org on o.organization_id = org.id " +
                    "         join client cl on o.client_id = cl.id " +
                    "         join users u on o.users_id = u.id " +
                    "where o.organization_id = " + orgId + " and o.orders_status='DELIVERED' and u.rolenameshort='cashier' and o.create_date " +
                    "    between '" + dateFrom + "' and '" + dateTo + "' " +
                    "group by u.username";

            getStatisticModel(arrayList, stmt, sql);
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
        return arrayList;
    }

    private void getStatisticModel(List<StatisticsModel> arrayList, Statement stmt, String sql) throws SQLException {
        ResultSet resultSet = stmt.executeQuery(sql);

        if (resultSet.next()) {
            do {
                StatisticsModel model = new StatisticsModel();
                model.setName(resultSet.getString(1));
                model.setCount(resultSet.getDouble(2));
                arrayList.add(model);
            } while (resultSet.next());
        } else {
            System.out.println("result set is empty");
        }
        resultSet.close();
    }

}
