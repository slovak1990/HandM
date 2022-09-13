package base;

import lombok.Data;

import java.util.List;

@Data
public class Card {
    private int id;
    private String dataCategory;
    private String productLink;
    private String productName;
    private String productArticle;
    private String productImage;
    private String productPrice;
    private List<String> sizes;
}
