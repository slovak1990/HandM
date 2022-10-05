package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseClass {

    private static Document getPages(String url) throws IOException {
        return Jsoup.parse(new URL(url), 8000);
    }

    public void parsingToJson(String url, String fileName) throws IOException {

        Document page = getPages(url);
        Elements productsClass = page.select("li[class=product-item]");
        int i = 1;
        int j = 1;
        int y = 1;
        String http = "https://www2.hm.com";
        String dotJson = ".json";
        List<Card> cards = new ArrayList<>();
        List<ProductsDetails> productsDetailsList = new ArrayList<>();
        // парсим с помощью Jsoup
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

        // парсим с помощью Selenium
        // проходим по всем ссылкам на карточки и собираем информацию
        for (Element link : productsClass) {
            ProductsDetails detail = new ProductsDetails();
            String productLink = link.getElementsByTag("a").attr("href");
            Document productDetails = Jsoup.connect("https://www2.hm.com" + productLink).get();
            detail.setId(j);


            System.out.println("Парсинг карточки №" + y);

            // включаем webdriver
            System.setProperty("webdriver.chrome.driver", "D:/WebDriver/bin/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("window-size=1500,900");
            WebDriver webDriver = new ChromeDriver(options);

            // направляем на url карточки
            webDriver.get(http + productLink);

            // клик по кукам
            if (webDriver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']")).isEnabled()) {
                WebElement cookieButton = webDriver.findElement(By.xpath("//button[@id='onetrust-accept-btn-handler']"));
                cookieButton.click();
            } else {
                WebElement cookieFirstButton = webDriver.findElement(By.xpath("//button[@id='onetrust-pc-btn-handler']"));
                cookieFirstButton.click();
                WebElement cookieButton = webDriver.findElement(By.xpath("//button[@id='accept-recommended-btn-handler']"));
                cookieButton.click();
            }


            // создаём список названий цветов и берем их текстовое значение
            List<WebElement> colorList = webDriver.findElements(By.xpath("//*[@class='list-item']"));
            List<String> listAllColors = new ArrayList<>();
            // вытащить фотки цветов
            List<String> listAllImage = new ArrayList<>();
            // вытащить артикли на эти цвета
            List<String> listAllArticle = new ArrayList<>();
            // вытащить все цены на разные цвета
            List<String> listAllPrices = new ArrayList<>();
            for (WebElement element : colorList) {
                try {
                    element.click();
                    WebElement colorName = webDriver.findElement(By.xpath("//*[@class='product-input-label']"));
                    listAllColors.add(colorName.getText());
                    detail.setColorName(listAllColors);

                    listAllImage.add(productDetails.getElementsByTag("noscript").attr("data-src"));
                    detail.setImage(listAllImage);

                    listAllArticle.add(productDetails.getElementsByTag("a").attr("data-articlecode"));
                    detail.setColorArticle(listAllArticle);

                    if (webDriver.findElement(By.xpath("//*[@class='ProductPrice-module--productItemPrice__2i2Hc']")).isDisplayed()) {
                        listAllPrices.add(webDriver.findElement(By.xpath("//*[@class='ProductPrice-module--productItemPrice__2i2Hc']")).getText());
                    }
                    else if (webDriver.findElement(By.xpath("//*[@id='product-price']/div/span[1]")).isDisplayed()) {
                        listAllPrices.add(webDriver.findElement(By.xpath("//*[@id='product-price']/div/span[1]")).getText());
                    } else  {
                        listAllPrices.add(webDriver.findElement(By.xpath("//*[@id='product-price']/div/div[2]")).getText());
                    }
                    detail.setPrices(listAllPrices);
                } catch (ElementNotInteractableException | NoSuchElementException | TimeoutException ex) {
                    ex.getStackTrace();
                }
            }

            // клик на кнопку с размерами
            try {
                WebElement sizeButton = webDriver.findElement(By.xpath("//*[@class='trigger-button    picker-trigger js-picker-trigger small']"));
                Actions actions = new Actions(webDriver);
                actions.moveToElement(sizeButton);
                actions.click(sizeButton);
                sizeButton.click();
            } catch (ElementClickInterceptedException | NoSuchElementException exception) {
                exception.getStackTrace();
            }

            // нахожу все элементы
            List<WebElement> sizeList = webDriver.findElements(By.xpath("//*[@class='item  js-enable-nib']"));
            List<String> listAllSizes = new ArrayList<>();
            for (WebElement element : sizeList) {
                listAllSizes.add(element.getText());
                detail.setSizes(listAllSizes);
            }

            // выбор на первый размер, клик на него, чтобы закрыть выбор размеров и найти рейтинг товара
            try {
                WebElement checkSize = webDriver.findElement(By.xpath("//*[@id='picker-1']/ul/li[2]/div/button/span"));
                checkSize.click();
            } catch (ElementNotInteractableException | NoSuchElementException exception) {
                exception.getStackTrace();
            }

            // клик по рейтингу товара, если он имеется, вход в карточку рейтинга и доставание оценки товара
            if (webDriver.findElements(By.xpath("//*[@class='CTA-module--action__3rYxf CTA-module--medium__QuUkL CTA-module--reset__1N1_1']")).isEmpty()) {
                webDriver.quit();
            } else {
                WebElement ratingButton = webDriver.findElement(By.xpath("//*[@class='CTA-module--action__3rYxf CTA-module--medium__QuUkL CTA-module--reset__1N1_1']"));
                ratingButton.click();
                // достаём значение рейтинга
                try {
                    WebElement ratingField = webDriver.findElement(By.xpath("//*[@class='BodyText-module--general__32l6J ReviewThoughts-module--starsNumberScore__2MF8N']"));
                    detail.setRating(ratingField.getText());
                } catch (NoSuchElementException exception) {
                    exception.getStackTrace();
                }
            }
            y++;
            j++;
            productsDetailsList.add(detail);
            webDriver.quit();
        }


        // создаём мапку и перебираем объекты по Id для заполнения productsDetails
        Map<Integer, ProductsDetails> detailById = productsDetailsList.stream().collect(Collectors.toMap(ProductsDetails::getId, Function.identity()));
        cards.forEach(card -> {card.setProductsDetails(detailById.get(card.getId()));});

        // создаём маппер для создания файла с форматом .json
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().withDefaultPrettyPrinter().writeValue(Paths.get(fileName + dotJson).toFile(), cards);
    }
}
