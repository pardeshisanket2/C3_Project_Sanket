import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup(){
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
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("12:30:00");
        // Can also parse anytime between the opening and closing time

        //LocalTime currentTime = LocalTime.now().minusHours(2);
        // 2 hours before now should be between opening and closing time

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);

        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("23:30:00");
        // Can also parse anytime between the opening and closing time

        //LocalTime currentTime = LocalTime.now().minusHours(12);
        // 12 hours before now shouldn't be between opening and closing time

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);

        assertFalse(spiedRestaurant.isRestaurantOpen());;
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

    //TDD

    // When selecting items from menu,
    // The total amount of the selected items should be returned.
    //
    // The only purpose of this feature should be to display the order total.
    // There is no necessity to store the chosen items or the order value
    //
    // The name of the item returned when the user selects the item is always in the menu,
    // hence a fail case scenario/Exception scenario would be unnecessary

    @Test
    public void selecting_items_from_menu_should_return_total_amount(){

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(0,restaurant.getMenu().get(0));
        Amount amount = restaurant.calculateTotalAmount(itemList);
        assertEquals(119,amount.totalAmount);
    }
}