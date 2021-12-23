package ru.sfedu.brms;

import ru.sfedu.brms.dataProviders.IDataProvider;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.models.rules.RuleByCountOfGoods;
import ru.sfedu.brms.models.rules.RuleByPurchaseCount;
import ru.sfedu.brms.models.rules.RuleByTime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class DataSourceGenerator {
    private final String[] retailNames = new String[]{"Globus", "Ashan", "Okey", "Lenta", "Perecrestok", "Metro", "Magnit"};
    private final String[] ruleNames = new String[]{"Cobblestone", "Creditor", "Descent", "Distorted", "Documentary", "Downplay", "Idol", "worship", "Sandbox", "Treasure", "Wrecking", "Bars", "Bickford", "Catavasia", "Compass", "Deru", "Donite", "Motivation", "Omnivorous", "Oriented", "Stubbornness", "Antonia", "Bend", "out", "Buried", "Disapproval", "Fragrant", "Ignite", "Ionize", "Lukomorje", "Simultaneous", "Sord", "Bereitor", "Fright", "Involuntary", "Orthopedics", "Overdoite", "Quintet", "Salittle", "Shading", "Tragicomic", "Tumay", "Dictionary", "Kalmyk", "Mighty", "Move", "off", "Obvious", "Overtop", "Rubber", "Separate", "smour", "Striped"};
    private final String[] customerNames = new String[]{"Avgust", "Avdej", "Averkij", "Aver'yan", "Aleksej", "Amvrosij", "Amos", "Ananij", "Anatolij", "Andrej", "Andrian", "Andron", "Andronik", "Anikej", "Anikita", "Anisim", "Antip", "Anton", "Apollinarij", "Apollon", "Arefij", "Aristarh", "Arkadij", "Arsenij", "Artem", "Artemij", "Arhip", "Askol'd", "Afanasij", "Afinogen", "Bazhen", "Bogdan", "Boleslav", "Boris", "Borislav", "Veniamin", "Vikentij", "Viktor", "Viktorin", "Vissarion", "Vitalij", "Vladimir", "Vladislav", "Vlas", "Vsevolod", "Vyacheslav", "Gavriil", "Gavrila", "Galaktion", "Gedeon", "Dementij", "Demid", "Dem'yan", "Denis", "Dmitrij", "Dorofej", "Evgenij", "Evgraf", "Evdokim", "Evlampij", "Evsej", "Evstafij", "Evstignej", "Egor", "Elizar", "Elisej", "Emel'yan", "Epifan", "Eremej", "Ermil", "Ermolaj", "Erofej", "Efim", "Efrem", "Zahar", "Zinovij", "Zosima", "Ivan", "Ignatij", "Igor'", "Izmail", "Izot", "Izyaslav", "Illarion", "Il'ya", "Innokentij", "Konstantin", "Kornej", "Kornil", "Kornilij", "Kuz'ma", "Lavr", "Lavrentij", "Ladislav", "Lazar'", "Lev", "Leon", "Leonid", "Leontij", "Longin", "Luka", "Luk'yan", "Luchezar", "Lyubomir", "Makar", "Maksim", "Maksimil'yan", "Marian", "Mark", "Martyn", "Matvej", "Mefodij", "Mechislav", "Milan", "Milen", "Mina", "Miron", "Miroslav", "Mitrofan", "Mihail", "Mihej", "Modest", "Moisej", "Mokij", "Mstislav", "Nazar", "Natan", "Naum", "Nestor", "Nikandr", "Nikanor", "Orest", "Osip", "Ostap", "Pavel", "Pankrat", "Pantelejmon", "Paramon", "Parfem", "Pahom", "Petr", "Pimen", "Platon", "Polikarp", "Porfirij", "Potap", "Prokl", "Prohor", "Radim", "Ratibor", "Ratmir", "Rodion", "Roman", "Rostislav", "Semen", "Serafim", "Sergej", "Sidor", "Sila", "Sil'vestr", "Simon", "Sokrat", "Solomon", "Sofron", "Spartak", "Spiridon", "Stanimir", "Stanislav", "Stepan", "Stoyan", "Taras", "Tverdislav", "Terentij", "Timofej", "Timur", "Tit", "Tihon", "Trifon", "Trofim", "Ul'yan", "Ustin", "Faddej", "Fedor", "Fedosij", "Fedot", "Feliks", "Feoktist", "Feofan", "Ferapont", "Filaret", "Filimon", "Filipp", "Florentin", "Foka", "Foma", "Fortunat", "Fotij", "Frol", "Hariton", "Harlampij", "CHeslav", "Eduard", "Emil'", "Erast", "Ernest", "Ernst", "YUlian", "YUlij", "YUrij", "YAkov", "YAkub", "YAn", "YAroslav"};
    private final String[] domains = new String[]{"yandex", "gmail", "yahoo", "sfedu", "mail", "email", "vk"};

    public DataSourceGenerator() {
    }

    public void generate(IDataProvider dataProvider) {
        List<UUID> retailsId = generateRetails(dataProvider);
        List<UUID> rulesId = generateRules(dataProvider, retailsId);
        List<UUID> customersId = generateCustomers(dataProvider, retailsId);
        List<UUID> checksId = generateChecks(dataProvider, customersId);
    }

    private List<UUID> generateRetails(IDataProvider dataProvider) {
        return Arrays.stream(retailNames).map(retailName -> {
            Retail retail = new Retail();
            retail.setName(retailName);
            retail.setCountOfStores(new Random().nextInt(250) + 1);
            return dataProvider.saveRetail(retail);
        }).collect(Collectors.toList());
    }

    private List<UUID> generateChecks(IDataProvider dataProvider, List<UUID> customersId) {
        return customersId.stream().flatMap(customerId -> {
            Random rnd = new Random();
            int countOfChecks = rnd.nextInt(50) + 1;
            ArrayList<UUID> list = new ArrayList<>();
            for (int i = 0; i < countOfChecks; i++) {
                StoreCheck check = generateCheck(customerId, rnd);
                list.add(dataProvider.saveCheck(check));
            }
            return list.stream();
        }).collect(Collectors.toList());
    }

    private List<UUID> generateCustomers(IDataProvider dataProvider, List<UUID> retailsId) {
        return retailsId.stream().flatMap(retailId -> {
            Random rnd = new Random();
            int countOfCustomers = rnd.nextInt(10) + 1;
            ArrayList<UUID> list = new ArrayList<>();
            for (int i = 0; i < countOfCustomers; i++) {
                Customer customer = generateCustomer(retailId, rnd);
                list.add(dataProvider.saveCustomer(customer));
            }
            return list.stream();
        }).collect(Collectors.toList());
    }

    private List<UUID> generateRules(IDataProvider dataProvider, List<UUID> retailsId) {
        return retailsId.stream().flatMap(retailId -> {
            ArrayList<UUID> list = new ArrayList<>();
            int randomCount = new Random().nextInt(4) + 1;
            for (int i = 0; i < randomCount; i++) {
                Rule rule = generateRandomRule();
                rule.setRetailId(retailId);
                list.add(dataProvider.saveRule(rule));
            }
            return list.stream();
        }).collect(Collectors.toList());
    }

    private StoreCheck generateCheck(UUID customerId, Random rnd) {
        StoreCheck check = new StoreCheck();
        check.setCost(rnd.nextInt(9000) + 1000);
        check.setCountOfGoods(rnd.nextInt(10) + 1);
        check.setTime(Instant.now().minus(rnd.nextInt(70), ChronoUnit.DAYS));
        check.setCustomerId(customerId);
        return check;
    }

    private Customer generateCustomer(UUID retailId, Random rnd) {
        Customer customer = new Customer();
        customer.setName(customerNames[rnd.nextInt(customerNames.length)]);
        customer.setPhoneNumber(generatePhone("+79", 9));
        customer.setEmail(String.format("%s%d@%s.com",
                customer.getName(),
                rnd.nextInt(5000),
                domains[rnd.nextInt(domains.length)]));
        customer.setRetailId(retailId);
        return customer;
    }

    private String generatePhone(String start, int countOfDigits) {
        StringBuilder phone = new StringBuilder(start);
        Random rnd = new Random();
        for (int i = 0; i < countOfDigits; i++) {
            phone.append(rnd.nextInt(10));
        }
        return phone.toString();
    }

    private Rule generateRandomRule() {
        Random rnd = new Random();
        Rule rule;
        switch (rnd.nextInt(RuleTypes.values().length)) {
            case 0:
                RuleByCountOfGoods ruleByCountOfGoods = new RuleByCountOfGoods();
                ruleByCountOfGoods.setDiscount(rnd.nextInt(1000) + 1);
                ruleByCountOfGoods.setMinimalCountOfGoods(rnd.nextInt(50));
                rule = ruleByCountOfGoods;
                break;
            case 1:
                RuleByTime ruleByTime = new RuleByTime();
                ruleByTime.setStartTime(Instant.now().minus(rnd.nextInt(60), ChronoUnit.DAYS));
                ruleByTime.setEndTime(Instant.now().plus(rnd.nextInt(100) + 1, ChronoUnit.DAYS));
                ruleByTime.setDiscount(rnd.nextInt(100) + rnd.nextFloat());
                rule = ruleByTime;
                break;
            case 2:
            default:
                RuleByPurchaseCount ruleByPurchaseCount = new RuleByPurchaseCount();
                ruleByPurchaseCount.setMinimalCost(rnd.nextInt(5000) + 100);
                ruleByPurchaseCount.setDiscountPercent(rnd.nextFloat());
                rule = ruleByPurchaseCount;
                break;
        }
        rule.setName(ruleNames[rnd.nextInt(retailNames.length)]);
        rule.setEnable(rnd.nextFloat() > 0.5f);
        return rule;
    }
}
