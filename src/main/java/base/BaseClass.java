package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BaseClass {

    private static Document getPages(String url) throws IOException {
        return Jsoup.parse(new URL(url), 8000);
    }

    public void parsingToJson(String url, String fileName) throws IOException {
        Document page = getPages(url);
        Elements productsClass = page.select("li[class=product-item]");
        int i = 1;
        String http = "https://www2.hm.com";
        String dotJson = ".json";
        List<Card> cards = new ArrayList<>();
        for(Element e : productsClass) {
            Card card = new Card();
            card.setId(i);
            card.setDataCategory(e.getElementsByClass("hm-product-item").attr("data-category"));
            card.setProductLink(http + e.getElementsByTag("a").attr("href"));
            card.setProductName(e.getElementsByTag("a").attr("title"));
            card.setProductArticle(e.getElementsByClass("hm-product-item").attr("data-articlecode"));
            card.setProductImage(e.getElementsByTag("img").attr("data-altimage"));
            card.setProductPrice(e.select("span[class=price regular]").text());
            cards.add(card);
            i++;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().withDefaultPrettyPrinter().writeValue(Paths.get(fileName + dotJson).toFile(), cards);
    }
}
