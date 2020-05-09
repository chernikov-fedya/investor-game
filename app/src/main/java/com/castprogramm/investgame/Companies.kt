package com.castprogramm.investgame

enum class Companies(var cent: Double, var country: Countries, var industry: Industries) {
    Apple(310.13, Countries.USA, Industries.Software),
    Intel(59.62, Countries.USA, Industries.Software),
    Twitter(29.83, Countries.USA, Industries.Software ),
    Mailru(16.42, Countries.GreatBritan, Industries.Software),
    Facebook(212.53, Countries.USA, Industries.Software),
    Yandex(40.74, Countries.Russia, Industries.Software),
    Amazon(2379.4, Countries.USA, Industries.Software),
    MasterCard(282.7, Countries.USA, Industries.BankingIndustry),
    IBM(123.2, Countries.USA, Industries.Software),
    GazProm(203.33, Countries.Russia, Industries.OilIndustry),
    Lukoil(4.83, Countries.Russia, Industries.OilIndustry),
    CocaCola(46.14, Countries.Germany, Industries.FoodIndustry),
    McDonalds(181.67, Countries.USA, Industries.FoodIndustry),
    Microsoft(184.67, Countries.USA, Industries.Software),
    Huawei(3.01, Countries.China, Industries.Software)
}