package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.intent.intent
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.helpers.ssml.audio
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.delayS
import com.justai.jaicf.template.fallbackOrSilence
import com.justai.jaicf.template.state.FloorFiveRoomLocationSmoke
import com.justai.jaicf.template.state.checkpoints

object FloorFiveLocationScenario : Scenario() {

    const val state = "/location/floor5"
    private const val window = "$state/window"
    private const val firefighters = "$state/firefighters"


    private fun ActionContext.handleSmoke() {
        FloorFiveRoomLocationSmoke.handleSmoke(this)
    }

    private fun ActionContext.goToState(state: String) {
        handleSmoke()
        reactions.go(state)
    }

    init {
        state(state) {
            action {
                reactions.say("Закрыл дверь. Что дальше с ней делать?")
            }

            state("door") {
                activators {
                    intent("UseWetCloth")
                    intent("UseCloth")
                }
                action {
                    context.checkpoints.FillGap = true
                    context.checkpoints.FillGapWithWetCloth = this.activator.intent?.intent == "UseWetCloth"
                    reactions.go(window)
                }
            }

            fallbackOrSilence {
                context.checkpoints.FillGap = false
                reactions.go(window)
            }
        }

        state(window) {
            action {
                reactions.say("Тут у окна есть высокое дерево. Я точно смогу до него допрыгнуть. Нужно только разбежаться.")
            }

            state("DoNotJump") {
                activators {
                    intent("DoNot")
                    intent("DoNotJump")
                }
                action {
                    reactions.say("Да. Думаю, это была плохая идея. Лучше дождусь пожарных.")
                    context.checkpoints.WindowJump = false
                    reactions.go(firefighters)
                }
            }

            state("Jump") {
                activators {
                    intent("Yes")
                    intent("Jump")
                }
                action {
                    reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_падение_new.wav"))
                    reactions.say("Уфф. Я жив. Но, кажется, сломал ногу")
                    context.checkpoints.WindowJump = true
                    reactions.go(EndGameScenario.state)
                }
            }

            fallbackOrSilence {
                reactions.say("Мне страшно. Думаю, это была плохая идея. Лучше дождусь пожарных.")
                context.checkpoints.WindowJump = null
                reactions.go(firefighters)
            }
        }

        state(firefighters){
            action {
                reactions.say("Кажется, я слышу сирену! Пожарные приехали! Ура! Меня спасут! Можно расслабиться и ничего не делать.")
            }

            state("Saved"){
                activators{
                    intent("GiveSign")
                }
                action{
                    reactions.say("Похоже, пожарные меня заметили! А вот и штурмовая лестница. Вылезаю через окно, спускаюсь. ${delayS(2)}Я в безопасности!")
                    context.checkpoints.AlertFirefighters = true
                    reactions.go(EndGameScenario.state)
                }
            }

            fallbackOrSilence {
                reactions.say("Пять. Десять. Пятнадцать минут. Ну когда же меня найдут? Что же делать?! ${delayS(2)}Ура! Кажется, я слышу пожарных в коридоре. Наконец-то!")
                context.checkpoints.AlertFirefighters = false
                context.checkpoints.KeepCalm = false
                reactions.go(EndGameScenario.state)
            }
        }
    }
}