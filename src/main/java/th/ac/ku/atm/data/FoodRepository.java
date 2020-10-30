package th.ac.ku.atm.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.ac.ku.atm.model.Food;

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
        String query = "SELECT * FROM foodtable";
        List<Food> foods =
                jdbcTemplate.query(query, new FoodMapper());
        return foods;

    }

    public Food findByFoodName(String foodName) {
        String query = "SELECT * FROM foodtable WHERE foodName = " + foodName;
        Food food =
                jdbcTemplate.queryForObject(query, new FoodMapper());
        return food;
    }

    public void save(Food food) {
        String query = "INSERT INTO foodtable (foodName,foodPrice) VALUES (?,?);";
        Object[] data = new Object[]
                { food.getFoodName(), food.getFoodPrice()};
        jdbcTemplate.update(query, data);
    }

    public void deleteByFoodName(String foodName) {
        String query = "DELETE FROM foodtable WHERE foodName = " + foodName;
        jdbcTemplate.update(query);
    }

    class FoodMapper implements RowMapper<Food> {

        @Override
        public Food mapRow(ResultSet resultSet, int i)
                throws SQLException {

            String foodName = resultSet.getString("foodName");
            int foodPrice = resultSet.getInt("foodPrice");
            Food food = new Food(foodName, foodPrice);
            return food;
        }
    }


}
