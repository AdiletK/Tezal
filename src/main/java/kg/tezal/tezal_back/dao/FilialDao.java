package kg.tezal.tezal_back.dao;

import kg.tezal.tezal_back.model.FilialShortModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilialDao {
    @Value("${spring.datasource.driver-class-name}")
    private String JDBC_DRIVER;
    @Value("${spring.datasource.jdbc-url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASS;

    public List<FilialShortModel> getFilialByOrgId(Long orgId, Long lastId, Double lastAverage, Integer limit){
        List<FilialShortModel> result = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = connection.createStatement();

            String query = "SELECT f.id, f.name, f.address, f.description, f.average_rate, f.longitude, f.latitude " +
                    "FROM filial f " +
                    "WHERE ((f.average_rate < " + lastAverage + " OR f.average_rate = " + lastAverage + ") AND f.id > " + lastId + ") AND f.status = true AND f.organization_id = " + orgId +
                    " ORDER BY f.average_rate DESC, f.id ASC LIMIT " + limit;

            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                FilialShortModel dto = new FilialShortModel();
                dto.setId(resultSet.getLong("id"));
                dto.setName(resultSet.getString("name"));
                dto.setAddress(resultSet.getString("address"));
                dto.setDescription(resultSet.getString("description"));
                dto.setAverageRate(resultSet.getDouble("average_rate"));
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
