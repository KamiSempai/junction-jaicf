package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.checkpoints

object FloorFiveLocationScenario : Scenario() {

    const val state = "/location/floor5"
    private const val saved = "/location/floor5/saved"
    private const val firefighters = "/location/floor5/firefighters"

    init {
        state(state) {
            action {
                reactions.say("Закрыл дверь, что дальше с ней делать?")
            }

            state("door") {
                activators {
                    intent("CloseDoor")
                    intent("UseCloth")
                }
                action {
                    reactions.say("Кажется, я слышу сирену! Пожарные приехали, ура, меня спасут! Можно расслабиться и ничего не делать.")
                    context.checkpoints.FillGap = true
                    reactions.go(firefighters)
                }
                fallback {
                    reactions.say("Кажется, я слышу сирену! Пожарные приехали, ура, меня спасут! Можно расслабиться и ничего не делать.")
                    context.checkpoints.FillGap = false
                    reactions.go(firefighters)
                }
            }
        }

        state(firefighters) {
            state(saved) {
                activators {
                    intent("GiveSign")
                }
                action {
                    reactions.say("Меня сразу же заметили и подали штурмовую лестницу, и буквально через пару минут я уже был внизу. Ура, я спасён!")
                    context.checkpoints.AlertFirefighters = true
                    reactions.go(EndGameScenario.state)
                }

                fallback {
                    reactions.say("Пять, десять, пятнадцать минут… Меня когда-нибудь найдут? Что же делать?! О, ура, кажется, я слышу пожарных в коридоре, наконец-то!")
                    context.checkpoints.AlertFirefighters = false
                    context.checkpoints.KeepCalm = false
                    reactions.go(EndGameScenario.state)
                }
            }
        }
    }
}
