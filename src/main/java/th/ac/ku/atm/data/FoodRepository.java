package th.ac.ku.atm.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FoodRepository {

    private JdbcTemplate jdbcTemplate;

    public FoodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Food> findAll() {
        String query = "SELECT * FROM food";
        List<Food> foods =
                jdbcTemplate.query(query, new FoodMapper());
        return foods;

    }

    public Food findById(int id) {
        String query = "SELECT * FROM food WHERE id = " + id;
        Food food =
                jdbcTemplate.queryForObject(query, new FoodMapper());
        return food;
    }

    public void save(Food food) {
        String query = "INSERT INTO food (id,name,pin) VALUES (?,?,?);";
        Object[] data = new Object[]
                { food.getId(), food.getName(), food.getPin() };
        jdbcTemplate.update(query, data);
    }

    public void deleteById(int id) {
        String query = "DELETE FROM food WHERE id = " + id;
        jdbcTemplate.update(query);
    }

    class FoodMapper implements RowMapper<Food> {

        @Override
        public Food mapRow(ResultSet resultSet, int i)
                throws SQLException {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String pin = resultSet.getString("pin");
            Food food = new Food(id, name, pin);
            return food;
        }
    }


}
