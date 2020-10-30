package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.data.FoodRepository;
import th.ac.ku.atm.model.Food;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    private List<Food> foodList;

    @PostConstruct
    public void postConstruct() {
        this.foodList = new ArrayList<>();
    }

    public void createFood(Food food) {
//         hash pin which I don't know how to do it
        String foodName = food.getFoodName();
        food.setFoodName(foodName);
        repository.save(food);
    }

    public List<Food> getFoods() {
        return repository.findAll();
    }

    public Food findFood(String foodName) {
        try {
            return repository.findByFoodName(foodName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Food checkPin(Food inputFood) {
        // 1. หา food ที่มี foodName ตรงกับพารามิเตอร์
        Food storedFood = findFood(inputFood.getFoodName());

        // 2. ถ้ามี foodName ตรง ให้เช็ค foodName ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
        if (storedFood != null) {
            String hashPin = storedFood.getFoodName();

            if (BCrypt.checkpw(inputFood.getFoodName(), hashPin))
                return storedFood;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }

//        public Customer checkPin(Customer inputCustomer) {
//        // 1. หา food ที่มี id ตรงกับพารามิเตอร์
//        Customer storedCustomer = findCustomer(inputCustomer.getId());
//
//        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
//        if (storedCustomer != null) {
//            int Pin = storedCustomer.getPin();
//
//            if (inputCustomer.getPin() == Pin)
//                return storedCustomer;
//        }
//        // 3. ถ้าไม่ตรง ต้องคืนค่า null
//        return null;
//    }


    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }

}

