package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.checkpoints

object LadderLocationScenario : Scenario() {

    const val state = "/location/ladder"
    private const val smokedLadder = "/locations/ladder/downstairs"
    private const val forthFlat = "/location/forthFlat"
    private const val kitchen = "/locations/ladder/kitchen"

    init {
        state(state) {
            action {
                reactions.say("Так, я на лестнице, бегу вниз. Тут, правда, очень темно, поэтому я лучше ускорюсь, чтобы поскорее отсюда выбраться.")
            }

            state("Don't run") {
                activators {
                    intent("No")
                }

                action {
                    reactions.say("Ступенька, ещё ступенька и… дошёл, отлично.")
                    context.checkpoints.RunOnStair = false
                    reactions.go(forthFlat)
                }
            }

            fallback {
                reactions.say("Чёрт, я споткнулся на лестнице и, кажется, подвернул ногу! Как же больно. Хорошо хоть идти могу. Но такими темпами я могу и не успеть выбраться!")
                context.checkpoints.RunOnStair = true
                context.checkpoints.KeepCalm = false
                reactions.go(forthFlat)
            }

            state(forthFlat) {
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
                        intent("GoForward")
                    }
                    action {
                        reactions.say("Кх-кх-кх! Ффу! Ничего не видно, я только зря дыма наглотался. Пойду лучше закроюсь в комнате и буду ждать пожарных.")
                        reactions.go(FloorFiveLocationScenario.state)
                    }
                }
            }
        }
    }
}