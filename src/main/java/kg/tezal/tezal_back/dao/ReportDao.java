package kg.tezal.tezal_back.dao;

import kg.tezal.tezal_back.model.PurchaseShortModel;
import kg.tezal.tezal_back.model.SalesShortModel;
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
public class ReportDao {

    private final DataSource dataSource;

    @Autowired
    public ReportDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }


    public List<PurchaseShortModel> getPurchaseByOrgId(Long orgId, String dateFrom, String dateTo) {
        List<PurchaseShortModel> arrayList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select p.count, p.summ, p.price_for_one," +
                    "       r.name, o.name, s.name , p.create_date" +
                    "                    from purchase p" +
                    "                    join raw_material r on p.raw_material_id = r.id" +
                    "                    join organization o on p.organization_id = o.id" +
                    "                    join supplier s on p.supplier_id = s.id" +
                    "                    where p.organization_id = "+ orgId ;
            if (dateFrom != null && dateTo !=null){
                sql = sql  + " and  p.create_date between '" + dateFrom + "' and '" + dateTo + "'";
            }
            sql = sql + " order by p.create_date DESC";
            ResultSet resultSet = stmt.executeQuery(sql);

            if(resultSet.next()){
                do {
                    PurchaseShortModel model = new PurchaseShortModel ();
                    model.setCount(resultSet.getInt(1));
                    model.setSumm(resultSet.getFloat(2));
                    model.setPriceForOne(resultSet.getFloat(3));
                    model.setRawMaterialName(resultSet.getString(4));
                    model.setOrganizationName(resultSet.getString(5));
                    model.setSupplierName(resultSet.getString(6));
                    model.setDate(resultSet.getString(7));
                    arrayList.add(model);
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
        return arrayList;
    }

    public List<SalesShortModel> getSalesByOrgId(Long orgId, String dateFrom, String dateTo) {
        List<SalesShortModel> arrayList = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        String sql;
        try{
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            // QUERY
            sql = "select cl.first_name, cl.last_name, org.name, rm.name, o_p.number_of, o_p.sum_of, o.create_date " +
                    "    from orders_product o_p " +
                    "         join orders o on o_p.orders_id = o.id " +
                    "         join raw_material rm on o_p.raw_material_id = rm.id " +
                    "         join organization org on o.organization_id = org.id " +
                    "         join client cl on o.client_id = cl.id " +
                    "where o.organization_id = " + orgId + " and o.orders_status='DELIVERED'  and  o.create_date between '" + dateFrom + "' and '" + dateTo + "' order by o.create_date DESC" ;

            ResultSet resultSet = stmt.executeQuery(sql);

            if(resultSet.next()){
                do {
                    SalesShortModel model = new SalesShortModel ();
                    model.setFirstName(resultSet.getString(1));
                    model.setLastName(resultSet.getString(2));
                    model.setOrganizationName(resultSet.getString(3));
                    model.setRawMaterialName(resultSet.getString(4));
                    model.setCount(resultSet.getInt(5));
                    model.setSumm(resultSet.getFloat(6));
                    model.setDate(resultSet.getString(7));
                    arrayList.add(model);
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
        return arrayList;
    }


}
