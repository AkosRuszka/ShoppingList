package homework.shoppinglist.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Akos on 2017. 11. 21..
 */

public class ShoppingItem extends SugarRecord implements Serializable{

    public enum Category {
        FOOD, ELECTRIC, HOME;

        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for(Category cat : Category.values()) {
                if(cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }
    }

    public ShoppingItem() { /* Empty */ }

    public String name;
    public String description;
    public Category category;
    public boolean isBought;
    public int estimatedPrice;
    public String listname;
}
