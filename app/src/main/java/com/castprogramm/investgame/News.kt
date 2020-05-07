package com.castprogramm.investgame

class News {
    var country: String? = null
    var countryM: Array<String> = arrayOf("Россия", "Пендосия", "Украина")
    var eventType: String? = null
    var eventTypeM: Array<String> = arrayOf("повышение", "понижение")
    var msg: String = ""
    init {
        country = countryM[(0..countryM.size-1).random()]
        eventType = eventTypeM[(0..eventTypeM.size-1).random()]
        msg = "В стране $country произошло $eventType цен"
    }

}