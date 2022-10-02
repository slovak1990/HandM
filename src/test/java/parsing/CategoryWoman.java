package parsing;

import base.BaseClass;
import categoryPOJO.WomanCategoryAllUrl;
import categoryPOJO.WomanCategoryNamesJson;

import java.io.IOException;

public class CategoryWoman {
    private static final BaseClass baseClass = new BaseClass();
    private static final WomanCategoryAllUrl womanUrl = new WomanCategoryAllUrl();
    private static final WomanCategoryNamesJson womanName = new WomanCategoryNamesJson();

    public static void main(String [] args) throws IOException {
        // Парсинг подкатегории - платья
        baseClass.parsingToJson(womanUrl.getDressUrl(), womanName.getDressName());

        // Парсинг подкатегории - рубашки и блузки
        baseClass.parsingToJson(womanUrl.getShirtBlousesUrl(), womanName.getBlousesName());

        // Парсинг подкатегории - куртки
        baseClass.parsingToJson(womanUrl.getJacketUrl(), womanName.getJacketName());

        // Парсинг подкатегории - толстовки
        baseClass.parsingToJson(womanUrl.getHoodyUrl(), womanName.getHoodyName());

        // Парсинг подкатегории - свитера
        baseClass.parsingToJson(womanUrl.getSweaterUrl(), womanName.getSweaterName());

        // Парсинг подкатегории - пальто
        baseClass.parsingToJson(womanUrl.getCoatUrl(), womanName.getCoatName());

        // Парсинг подкатегории - обувь
        baseClass.parsingToJson(womanUrl.getShoesUrl(), womanName.getShoesName());

        // Парсинг подкатегории - сумки
        baseClass.parsingToJson(womanUrl.getBagsUrl(), womanName.getBagsName());

        // Парсинг подкатегории - аксессуары
        baseClass.parsingToJson(womanUrl.getAccessoryUrl(), womanName.getAccessoryName());

        // Парсинг подкатегории - топы
        baseClass.parsingToJson(womanUrl.getTopsUrl(), womanName.getTopsName());

        // Парсинг подкатегории - брюки
        baseClass.parsingToJson(womanUrl.getTrousersUrl(), womanName.getTrousersName());

        // Парсинг подкатегории - джинсы
        baseClass.parsingToJson(womanUrl.getJeansUrl(), womanName.getJeansName());

        // Парсинг подкатегории - шорты
        baseClass.parsingToJson(womanUrl.getShortsUrl(), womanName.getShortsName());

        // Парсинг подкатегории - юбка
        baseClass.parsingToJson(womanUrl.getSkirtUrl(), womanName.getSkirtName());

        // Парсинг подкатегории - пляжное
        baseClass.parsingToJson(womanUrl.getBeachUrl(), womanName.getBeachName());

        // Парсинг подкатегории - на каждый день
        baseClass.parsingToJson(womanUrl.getEveryDayUrl(), womanName.getEveryDayName());

        // Парсинг подкатегории - комбинезоны и шорты
        baseClass.parsingToJson(womanUrl.getOverallUrl(), womanName.getOverallName());

        // Парсинг подкатегории - нижнее бельё
        baseClass.parsingToJson(womanUrl.getLingerieUrl(), womanName.getLingerieName());

        // Парсинг подкатегории - домашнее
        baseClass.parsingToJson(womanUrl.getHomeUrl(), womanName.getHomeName());

        // Парсинг подкатегории - пижамы
        baseClass.parsingToJson(womanUrl.getPajamasUrl(), womanName.getPajamasName());

        // Парсинг подкатегории - носки и колготки
        baseClass.parsingToJson(womanUrl.getSocksTightsUrl(), womanName.getSocksTightsName());

        // Парсинг подкатегории - спортивное
        baseClass.parsingToJson(womanUrl.getSportUrl(), womanName.getSportName());

        // Парсинг подкатегории - для беременных
        baseClass.parsingToJson(womanUrl.getForPregnantUrl(), womanName.getForPregnantName());

        // Парсинг подкатегории - большие размеры
        baseClass.parsingToJson(womanUrl.getBigSizeUrl(), womanName.getBigSizeName());

        // Парсинг подкатегории - для собак
        baseClass.parsingToJson(womanUrl.getForDogUrl(), womanName.getForDogName());

        // Парсинг подкатегории - средства по уходу
        baseClass.parsingToJson(womanUrl.getTechUrl(), womanName.getTechName());

        // Парсинг подкатегории - одежда премиум
        baseClass.parsingToJson(womanUrl.getPremiumUrl(), womanName.getPremiumName());
    }
}
