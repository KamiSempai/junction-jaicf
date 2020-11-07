package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario

object LadderLocationScenario : Scenario() {

    const val state = "/location/ladder"
    private const val smokedLadder = "/locations/ladder/downstairs"
    private const val kitchen = "/locations/ladder/kitchen"

    init {
        state(state) {
            action {
                reactions.say("Я на площадке пятого этажа, но снизу валит дым! Что мне делать?")
            }
            
             state(kitchen){
                activators{
                    intent("Room")
                }
                action {
                    reactions.say("Отлично, кухня открыта, запрусь здесь.")
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }

            state(smokedLadder) {
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