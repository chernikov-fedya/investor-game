package com.castprogramm.investgame.EnumClasses

import com.castprogramm.investgame.R

enum class Companies(var cent: Double, var country: Countries, var industry: Industries, var n: String, var r: Int) {
    Apple(310.13,
        Countries.USA,
        Industries.Software,"Apple",
        R.mipmap.apple),
    Intel(59.62,
        Countries.USA,
        Industries.Software,"Intel",
        R.mipmap.intel),
    Twitter(29.83,
        Countries.USA,
        Industries.Software,"Twitter",
        R.mipmap.twitter),
    Mailru(16.42,
        Countries.GreatBritan,
        Industries.Software,"Mailru",
        R.mipmap.mailru),
    Facebook(212.53,
        Countries.USA,
        Industries.Software,"Facebook",
        R.mipmap.facebook),
    Yandex(40.74,
        Countries.Russia,
        Industries.Software,"Yandex",
        R.mipmap.yandex),
    Amazon(2379.4,
        Countries.USA,
        Industries.Software,"Amazon",
        R.mipmap.amazon),
    MasterCard(282.7,
        Countries.USA,
        Industries.BankingIndustry,"MasterCard",
        R.mipmap.master_card),
    IBM(123.2,
        Countries.USA,
        Industries.Software,"IBM",
        R.mipmap.ic_launcher),
    GazProm(203.33,
        Countries.Russia,
        Industries.OilIndustry,"GazProm",
        R.mipmap.gazprom),
    Lukoil(4.83,
        Countries.Russia,
        Industries.OilIndustry,"Lukoil",
        R.mipmap.lukoil),
    CocaCola(46.14,
        Countries.Germany,
        Industries.FoodIndustry,"CocaCola",
        R.mipmap.ic_launcher),
    McDonalds(181.67,
        Countries.USA,
        Industries.FoodIndustry,"McDonalds",
        R.mipmap.ic_launcher),
    Microsoft(184.67,
        Countries.USA,
        Industries.Software,"Microsoft",
        R.mipmap.ic_launcher),
    Huawei(3.01,
        Countries.China,
        Industries.Software,"Huawei",
        R.mipmap.ic_launcher)
}