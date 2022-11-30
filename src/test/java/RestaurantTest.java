import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    
    @BeforeEach
    public void createrestaurants_before_executing_each_testcase()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime presentTime = LocalTime.of(13,12,35);
        Restaurant res = Mockito.spy(restaurant);
        Mockito.when(res.getCurrentTime()).thenReturn(presentTime);
        boolean b = res.isRestaurantOpen();
        assertTrue(b);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime presentTime = LocalTime.of(23,45,35);
        Restaurant res = Mockito.spy(restaurant);
        Mockito.when(res.getCurrentTime()).thenReturn(presentTime);
        boolean b = res.isRestaurantOpen();
        assertFalse(b);
        //WRITE UNIT TEST CASE HERE
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    
    @Test
    public void calculated_bill_amount_for_selected_items_should_not_be_zero(){
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Vegetable lasagne");
        selectedItems.add("Sweet corn soup");
        int amount = restaurant.calcuateBillAmount(selectedItems);
        assertEquals(388, amount);
    }

    @Test
    public void calculate_bill_amount_for_no_item_selected_should_be_zero(){
        List<String> selectedItems = new ArrayList<>();
        int amount = restaurant.calcuateBillAmount(selectedItems);
        assertEquals(0, amount);
    }
}