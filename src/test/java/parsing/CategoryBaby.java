package parsing;

import categoryPOJO.BabyCategoryAllNames;
import categoryPOJO.BabyCategoryAllUrl;
import base.BaseClass;

import java.io.IOException;

public class CategoryBaby {
    private static final BaseClass baseClass = new BaseClass();
    private static final BabyCategoryAllUrl babyUrl = new BabyCategoryAllUrl();
    private static final BabyCategoryAllNames babyName = new BabyCategoryAllNames();

    public static void main(String [] args) throws IOException {
        // парсинг подкатегории - всё для новорожденных
        baseClass.parsingToJson(babyUrl.getNewBornBabyAllUrl(), babyName.getNewBornBabyAllName());
        // парсинг подкатегории - всё для маленьких девочек
        baseClass.parsingToJson(babyUrl.getBabyGirlAllUrl(), babyName.getBabyGirlAllName());
        // парсинг подкатегории - всё для маленьких мальчиков
        baseClass.parsingToJson(babyUrl.getBabyBoyAllUrl(), babyName.getBabyBoyAllName());
        // парсинг подкатегории - всё для девочек 2-8 лет
        baseClass.parsingToJson(babyUrl.getKidGirl2_8YearsOldAllUrl(), babyName.getKidGirl2_8YearsOldAllName());
        // парсинг подкатегории - всё для мальчиков 2-8 лет
        baseClass.parsingToJson(babyUrl.getKidBoy2_8YearsOldAllUrl(), babyName.getKidBoy2_8YearsOldAllName());
        // парсинг подкатегории - всё для девочек 9-14 лет
        baseClass.parsingToJson(babyUrl.getKidGirl9_14YearsOldAllUrl(), babyName.getKidGirl9_14YearsOldAllName());
        // парсинг подкатегории - всё для мальчиков 9-14 лет
        baseClass.parsingToJson(babyUrl.getKidBoy9_14YearsOldAllUrl(), babyName.getKidBoy9_14YearsOldAllName());
    }
}
