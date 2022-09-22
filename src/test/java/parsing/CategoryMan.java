package parsing;

import base.BaseClass;
import categoryPOJO.ManCategoryAllNamesJson;
import categoryPOJO.ManCategoryAllUrl;

import java.io.IOException;

public class CategoryMan {

    private static final BaseClass baseClass = new BaseClass();
    private static final ManCategoryAllUrl manUrl = new ManCategoryAllUrl();
    private static final ManCategoryAllNamesJson manName = new ManCategoryAllNamesJson();


    public static void main(String [] args) throws IOException {
        // Парсинг подкатегории - толстовки
        baseClass.parsingToJson(manUrl.getHoodysUrl(), manName.getHoodysName());

        // Парсинг подкатегории - куртки
        baseClass.parsingToJson(manUrl.getJacketsCoatsUrl(), manName.getJacketsCoatsName());

        // Парсинг подкатегории - брюки
        baseClass.parsingToJson(manUrl.getTrousersUrl() , manName.getTrousersName());

        // Парсинг подкатегории - футболки
        baseClass.parsingToJson(manUrl.getTShirtsUrl() , manName.getTShirtsName());

        // Парсинг подкатегории - рубашки
        baseClass.parsingToJson(manUrl.getShirtsUrl() , manName.getShirtsName());

        // Парсинг подкатегории - джинсы
        baseClass.parsingToJson(manUrl.getJeansUrl() , manName.getJeansName());

        // Парсинг подкатегории - аксессуары
        baseClass.parsingToJson(manUrl.getAccessoriesUrl() , manName.getAccessoriesName());

        // Парсинг подкатегории - обувь
        baseClass.parsingToJson(manUrl.getShoesUrl() , manName.getShirtsName());

        // Парсинг подкатегории - кардиганы
        baseClass.parsingToJson(manUrl.getCardigansUrl() , manName.getCardigansName());

        // Парсинг подкатегории - нижнее бельё
        baseClass.parsingToJson(manUrl.getLingerieUrl() , manName.getLingerieName());

        // Парсинг подкатегории - шорты
        baseClass.parsingToJson(manUrl.getShortsUrl(), manName.getShortsName());

        // Парсинг подкатегории - базовая
        baseClass.parsingToJson(manUrl.getBasicClothesUrl() , manName.getBasicClothesName());

        // Парсинг подкатегории - спортивная
        baseClass.parsingToJson(manUrl.getSportUrl() , manName.getSportName());

        // Парсинг подкатегории - костюмы
        baseClass.parsingToJson(manUrl.getSuitsUrl() , manName.getSuitsName());

        // Парсинг подкатегории - пижамы
        baseClass.parsingToJson(manUrl.getPajamasUrl() , manName.getPajamasName());

        // Парсинг подкатегории - пляжное
        baseClass.parsingToJson(manUrl.getBeachUrl() , manName.getBeachName());

        // Парсинг подкатегории - носки
        baseClass.parsingToJson(manUrl.getSocksUrl() , manName.getSocksName());

        // Парсинг подкатегории - вязаное
        baseClass.parsingToJson(manUrl.getKnittedUrl() , manName.getKnittedName());

        // Парсинг подкатегории - премиум
        baseClass.parsingToJson(manUrl.getPremiumUrl() , manName.getPremiumName());
    }
}
