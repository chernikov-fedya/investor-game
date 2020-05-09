package com.castprogramm.investgame

enum class Companies(var cent: Double, var country: Countries, var industry: Industries, var n: String) {
    Apple(310.13, Countries.USA, Industries.Software,"Apple" ),
    Intel(59.62, Countries.USA, Industries.Software,"Intel"),
    Twitter(29.83, Countries.USA, Industries.Software,"Twitter" ),
    Mailru(16.42, Countries.GreatBritan, Industries.Software,"Mailru"),
    Facebook(212.53, Countries.USA, Industries.Software,"Facebook"),
    Yandex(40.74, Countries.Russia, Industries.Software,"Yandex"),
    Amazon(2379.4, Countries.USA, Industries.Software,"Amazon"),
    MasterCard(282.7, Countries.USA, Industries.BankingIndustry,"MasterCard"),
    IBM(123.2, Countries.USA, Industries.Software,"IBM"),
    GazProm(203.33, Countries.Russia, Industries.OilIndustry,"GazProm"),
    Lukoil(4.83, Countries.Russia, Industries.OilIndustry,"Lukoil"),
    CocaCola(46.14, Countries.Germany, Industries.FoodIndustry,"CocaCola"),
    McDonalds(181.67, Countries.USA, Industries.FoodIndustry,"McDonalds"),
    Microsoft(184.67, Countries.USA, Industries.Software,"Microsoft"),
    Huawei(3.01, Countries.China, Industries.Software,"Huawei")
}