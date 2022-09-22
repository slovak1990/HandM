package base;

import lombok.Data;

@Data
public class Card {
    private int id;
    private String brand;
    private String dataCategory;
    private String productLink;
    private String productName;
    private String productArticle;
    private String productImage;
    private String productPrice;
    private ProductsDetails productsDetails;
}
