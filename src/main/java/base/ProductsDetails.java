package base;

import lombok.Data;

import java.util.List;

@Data
public class ProductsDetails {
    private int id;
    private List<String> colorName;
    private List<String> image;
    private List<String> colorArticle;
    private List<String> prices;
    private List<String> sizes;
    private String rating;
}
