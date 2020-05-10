package com.castprogramm.investgame

import kotlin.math.exp
enum class LossEvent{
    Pet,
    Family,
    Illness,
    Food,
    Transport,
    Nothing
}
class Expense{

    var loss: Double = 0.0

    fun makeLossEvent(): LossEvent=
        when ((0..100).random()) {
            in 1..15 ->  {
                loss = 20.0
                LossEvent.Illness
            }
            in 15..70 ->  {
                loss = 40.0
                LossEvent.Food
            }
            in 70..71 ->  LossEvent.Nothing
            in 72..90 ->  {
                loss = 20.0
                LossEvent.Transport
            }
            in 90..100 ->  {
                loss = 20.0
                LossEvent.Family
            }
            else -> LossEvent.Nothing
        }

    init {
        makeLossEvent()
    }
}


/*fun notForseeExpense(): Double{
    var evantBad: Array<String> = arrayOf("животное", "семья", "болезнь")
    when (this.expense){
        expense.eventBad["животное"] -> expense = 40.0
        expense.eventBad["семья"] -> expense = 20.0
        expense.eventBad["болезнь"] -> expense = 20.0
    }

}
fun everyDayExpense(): Double{
    var event: Array<String> = arrayOf("питание", "транспорт")
    when (this.expense){
        expense.event["питание"] -> capital = capital - 40
        expense.event["транспорт"] -> capital = capital - 20

    }
}
} */

