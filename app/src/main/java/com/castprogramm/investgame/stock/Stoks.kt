package com.castprogramm.investgame.stock

import com.castprogramm.investgame.EnumClasses.Companies

object Stoks {
    // список для хранения все новостей
    var newsarray: MutableList<String> = mutableListOf()
    // список для хранения всех акций
    var allStoks: MutableList<Stock> = mutableListOf(
        Stock()
            .apply { cost = Companies.Apple.cent; companies = Companies.Apple },
        Stock()
            .apply { cost = Companies.Intel.cent; companies = Companies.Intel},
        Stock()
            .apply { cost = Companies.Twitter.cent; companies = Companies.Twitter},
        Stock()
            .apply { cost = Companies.Mailru.cent; companies = Companies.Mailru},
        Stock()
            .apply { cost = Companies.Facebook.cent; companies = Companies.Facebook},
        Stock()
            .apply { cost = Companies.Yandex.cent; companies = Companies.Yandex},
        Stock()
            .apply { cost = Companies.Amazon.cent; companies = Companies.Amazon},
        Stock()
            .apply { cost = Companies.MasterCard.cent; companies = Companies.MasterCard},
        Stock()
            .apply { cost = Companies.IBM.cent; companies = Companies.IBM},
        Stock()
            .apply { cost = Companies.GazProm.cent; companies = Companies.GazProm},
        Stock()
            .apply { cost = Companies.Lukoil.cent; companies = Companies.Lukoil},
        Stock()
            .apply { cost = Companies.CocaCola.cent; companies = Companies.CocaCola},
        Stock()
            .apply { cost = Companies.McDonalds.cent; companies = Companies.McDonalds},
        Stock()
            .apply { cost = Companies.Microsoft.cent; companies = Companies.Microsoft},
        Stock()
            .apply { cost = Companies.Huawei.cent; companies = Companies.Huawei}
    )
}