package th.ac.ku.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    private List<Customer> customerList;

    @PostConstruct
    public void postConstruct() {
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer) {
//         hash pin which I don't know how to do it
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        repository.save(customer);
    }
//    public void createCustomer(Customer customer) {
////         hash pin which I don't know how to do it
//        int Pin = customer.getPin();
//        customer.setPin(Pin);
//        customerList.add(customer);
//    }


    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    public Customer findCustomer(int id) {
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Customer checkPin(Customer inputCustomer) {
        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
        Customer storedCustomer = findCustomer(inputCustomer.getId());

        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
        if (storedCustomer != null) {
            String hashPin = storedCustomer.getPin();

            if (BCrypt.checkpw(inputCustomer.getPin(), hashPin))
                return storedCustomer;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }

//        public Customer checkPin(Customer inputCustomer) {
//        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
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

