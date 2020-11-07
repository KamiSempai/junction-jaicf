package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object LadderLocationScenario : Scenario() {

    const val state = "/location/ladder"

    init {
        state(state) {
            action {
                reactions.say("Я на площадке пятого этажа, но снизу валит дым! Что мне делать?")
            }
            
             state("next_good"){
                activators{
                    intent("Room")
                }
                action {
                    reactions.say("Отлично, кухня открыта, запрусь здесь.")
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }

            state("next_bad") {
                activators {
                    intent("GoForvard")
                }
                action {
                    reactions.say("Кх-кх-кх! Ффу! Ничего не видно, я только зря дыма наглотался. Пойду лучше закроюсь в комнате и буду ждать пожарных.")
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }
        }
    }
}