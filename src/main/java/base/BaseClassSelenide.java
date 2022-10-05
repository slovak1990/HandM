package base;

import com.codeborne.selenide.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class BaseClassSelenide {

    private Document getPages(String url) throws IOException {
        return Jsoup.parse(new URL(url), 12000);
    }

    private static void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = Browsers.CHROME;
        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1400x1020";
        Configuration.headless = false;
    }

    public void parsingToJsonSelenide(String url, String fileName) throws IOException {
        Document page = getPages(url);
        Elements productsClass = page.select("li[class=product-item]");
        int i = 1;
        int j = 1;
        int y = 1;
        String http = "https://www2.hm.com";
        String dotJson = ".json";
        List<Card> cards = new ArrayList<>();
        List<ProductsDetails> productsDetailsList = new ArrayList<>();
        for (Element e : productsClass) {
            Card card = new Card();
            card.setId(i);
            card.setBrand(e.getElementsByClass("hm-product-item").attr("data-brand"));
            card.setDataCategory(e.getElementsByClass("hm-product-item").attr("data-category"));
            card.setProductLink(http + e.getElementsByTag("a").attr("href"));
            card.setProductName(e.getElementsByTag("a").attr("title"));
            card.setProductArticle(e.getElementsByClass("hm-product-item").attr("data-articlecode"));
            card.setProductImage(e.getElementsByTag("img").attr("data-altimage"));
            card.setProductPrice(e.select("span[class=price regular]").text());
            cards.add(card);
            i++;
        }

        for (Element link : productsClass) {
            ProductsDetails detail = new ProductsDetails();
            String productLink = link.getElementsByTag("a").attr("href");
            Document productDetails = Jsoup.connect("https://www2.hm.com" + productLink).get();
            detail.setId(j);

            System.out.println("Парсинг карточки №" + y);

            // настройка драйвера и запуск по карточкам
            setUp();
            Selenide.open(http + productLink);

            // клик по кукам
            while ($x("//button[@id='onetrust-accept-btn-handler']").isDisplayed()) {
                $x("//button[@id='onetrust-accept-btn-handler']").click();
            }

            // добавить фотки из карточки
            // клик по фото
            if ($x("//*[@class='product-detail-main-image-container']").isDisplayed()) {
                $x("//*[@class='product-detail-main-image-container']").click();

                // коллекция кнопок переключения между фото
                ElementsCollection buttonsImage = $$x("//*[@class='pagination-list-item']");
                // коллекция ссылок на фото
                ElementsCollection srcList = $$x("//img[starts-with(@id, 'full')]");
                List<String> listImages = new ArrayList<>();
                int q = 0;
                for (SelenideElement button : buttonsImage) {
                    button.click();
                    try {
                        listImages.add(srcList.get(q).attr("src"));
                    } catch (IndexOutOfBoundsException exception) {
                        exception.getStackTrace();
                    }
                    q++;
                }
                detail.setProductImages(listImages);
            }

            // выйти из режима фоток
            if ($x("//button[@class='close hidden-text']").isDisplayed()) {
                $x("//button[@class='close hidden-text']").click();
            }

            // добавить описание (Description) со всего div
            detail.setDescription($x("//*[@class='BodyText-module--general__32l6J ProductDescription-module--descriptionText__1zy9P BodyText-module--preamble__LjYwZ']").scrollTo().getText());

            // создаём список названий цветов и берем их текстовое значение
            ElementsCollection colorList = $$x("//*[@class='list-item']");
            List<String> listAllColors = new ArrayList<>();
            // вытащить фотки цветов
            List<String> listAllImage = new ArrayList<>();
            // вытащить артикли на эти цвета
            List<String> listAllArticle = new ArrayList<>();
            // вытащить все цены на разные цвета
            List<String> listAllPrices = new ArrayList<>();
            for (SelenideElement element : colorList) {
                listAllColors.add($x("//*[@class='product-input-label']").text());
                detail.setColorName(listAllColors);

                listAllImage.add(productDetails.getElementsByTag("noscript").attr("data-src"));
                detail.setImage(listAllImage);

                listAllArticle.add(productDetails.getElementsByTag("a").attr("data-articlecode"));
                detail.setColorArticle(listAllArticle);

                if ($x("//*[@class='ProductPrice-module--productItemPrice__2i2Hc']").isDisplayed()) {
                    listAllPrices.add($x("//*[@class='ProductPrice-module--productItemPrice__2i2Hc']").text());
                } else if ($x("//*[@id='product-price']/div/span[1]").isDisplayed()) {
                    listAllPrices.add($x("//*[@id='product-price']/div/span[1]").text());
                } else if ($x("//*[@id='product-price']/div/div[2]").isDisplayed()) {
                    listAllPrices.add($x("//*[@id='product-price']/div/div[2]").text());
                }
                detail.setPrices(listAllPrices);
            }

            // рефреш страницы
            Selenide.open(http + productLink);

            // клик на кнопку с размерами
            if ($x("//*[@class='trigger-button    picker-trigger js-picker-trigger small']").isDisplayed()) {
                $x("//*[@class='trigger-button    picker-trigger js-picker-trigger small']").scrollTo().click();
            }

            // нахожу все элементы
            List<SelenideElement> sizeList = $$x("//*[@class='item  js-enable-nib']");
            List<String> listAllSizes = new ArrayList<>();
            for (WebElement element : sizeList) {
                listAllSizes.add(element.getText());
                detail.setSizes(listAllSizes);
            }

            // клик закрыть окно, чтобы выйти из режима выбора размеров
            if ($x("//button[@class='close icon icon-cross-circle-white hidden-text']").isDisplayed()) {
                $x("//button[@class='close icon icon-cross-circle-white hidden-text']").click();
            }


            // клик по рейтингу товара, если он имеется, вход в карточку рейтинга и доставание оценки товара
            if ($x("//*[@class='CTA-module--action__3rYxf CTA-module--medium__QuUkL CTA-module--reset__1N1_1']").isDisplayed()) {
                $x("//*[@class='CTA-module--action__3rYxf CTA-module--medium__QuUkL CTA-module--reset__1N1_1']").scrollTo().click();
                if ($x("//*[@class='BodyText-module--general__32l6J ReviewThoughts-module--starsNumberScore__2MF8N']").isDisplayed()) {
                    detail.setRating($x("//*[@class='BodyText-module--general__32l6J ReviewThoughts-module--starsNumberScore__2MF8N']").scrollTo().text());
                }
            }

            y++;
            j++;
            productsDetailsList.add(detail);
        }
        Selenide.closeWebDriver();

        // создаём мапку и перебираем объекты по Id для заполнения productsDetails
        Map<Integer, ProductsDetails> detailById = productsDetailsList.stream().collect(Collectors.toMap(ProductsDetails::getId, Function.identity()));
        cards.forEach(card -> {
            card.setProductsDetails(detailById.get(card.getId()));
        });

        // создаём маппер для создания файла с форматом .json
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().withDefaultPrettyPrinter().writeValue(Paths.get(fileName + dotJson).toFile(), cards);
    }
}
