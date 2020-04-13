package kg.tezal.tezal_back.dao;

import kg.tezal.tezal_back.model.EventFullModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventDao {
    @Value("${spring.datasource.driver-class-name}")
    private String JDBC_DRIVER;
    @Value("${spring.datasource.jdbc-url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASS;

    public List<EventFullModel> getEventByOrgId(Long orgId, Long lastId, String lastDate, Integer limit) {
        List<EventFullModel> result = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = connection.createStatement();

            String query = "SELECT " +
                    "e.id, e.name, e.date_from, e.date_to, e.description " +
                    "FROM event e " +
                    "WHERE ((e.date_to > DATE('" + lastDate + "') OR e.date_to = date('" + lastDate + "')) AND e.id > " + lastId + " ) AND e.organization_id =  " + orgId +
                    " ORDER BY e.date_to ASC LIMIT " + limit;

            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                EventFullModel dto = new EventFullModel();
                dto.setId(resultSet.getLong("id"));
                dto.setName(resultSet.getString("name"));
                dto.setDateFrom(resultSet.getDate("date_from"));
                dto.setDateTo(resultSet.getDate("date_to"));
                dto.setDescription(resultSet.getString("description"));
                result.add(dto);
            }

        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    connection.close();
            } catch (SQLException se) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }
}
