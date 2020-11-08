package com.justai.jaicf.template.scenario

import com.justai.jaicf.helpers.ssml.audio
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.fallbackOrSilence
import com.justai.jaicf.template.state.checkpoints

object StairsLocationScenario : Scenario() {

    const val state = "/location/ladder"
    private const val forthFlat = "/location/forthFlat"

    init {
        state(state) {
            action {
                reactions.say("Так, я на лестнице, бег+у вниз. Тут, правда, очень темно, поэтому я лучше ускорюсь, чтобы поскорее отсюда выбраться.")
            }

            state("Don't run") {
                activators {
                    intent("DoNotRun")
                }

                action {
                    reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_бег+дыхание.wav"))
                    reactions.say("Ступенька, ещё ступенька, и дошёл. Отлично.")
                    context.checkpoints.RunOnStair = false
                    reactions.go(forthFlat)
                }
            }

            fallbackOrSilence {
                reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_бег+дыхание.wav"))
                reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_падение_new.wav"))
                reactions.say("Чёрт, я споткнулся на лестнице и, кажется, подвернул ногу! Как же больно. Хорошо хоть могу идти. Но этак я могу и не успеть выбраться!")
                context.checkpoints.RunOnStair = true
                context.checkpoints.KeepCalm = false
                reactions.go(forthFlat)
            }
        }

        state(forthFlat) {
            action {
                reactions.say("Я на площадке пятого этажа, но снизу валит дым! Что мне делать?")
            }

            state("kitchen") {
                activators {
                    intent("Room")
                }
                action {
                    reactions.say("Отлично, кухня открыта. Запрусь здесь.")
                    context.checkpoints.GoToSmoke = false
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }

            state("smokedLadder") {
                activators {
                    intent("GoForward")
                }
                action {
                    reactions.say("${audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_кашель3с.wav")}")
                    reactions.say("Ничего не видно, я только зря дыма наглотался. Пойду лучше закроюсь в комнате и буду ждать пожарных.")
                    context.checkpoints.GoToSmoke = true
                    reactions.go(FloorFiveLocationScenario.state)
                }
            }

            fallbackOrSilence {
                reactions.say("Я тебя не расслышал, но тут есть открытая кухня. Пойду туда.")
                reactions.go(FloorFiveLocationScenario.state)
            }
        }
    }
}
