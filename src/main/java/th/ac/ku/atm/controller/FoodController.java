package th.ac.ku.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.Food;
import th.ac.ku.atm.service.FoodService;

@Controller
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    public FoodController(FoodService FoodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public String getFoodPage(Model model) {
        model.addAttribute("allFoods", foodService.getFoods());
        return "food";
    }

    @PostMapping
    public String registerFood(@ModelAttribute Food food, Model model) {
        foodService.createFood(food);
        model.addAttribute("allFoods", foodService.getFoods());
        return "redirect:food";
    }
}

