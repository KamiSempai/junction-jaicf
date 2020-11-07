package com.justai.jaicf.template.scenario

import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.EmergencyPaths
import com.justai.jaicf.template.state.checkpoints

object StartFloorScenario : Scenario() {

    const val state = "/location/start_floor"
    private const val choosePath = "/location/choosePath"
    private const val elevator = "/location/elevator"
    private const val redButton = "/location/redButton"
    private const val doorOutside = "/location/doorOutside"


    init {
        state(state) {
            action {
                reactions.go(doorOutside)
            }
            state(doorOutside) {
                action {
                    reactions.say("Вышел, дверь закрыть или оставить?")
                }

                state("Yes") {
                    activators {
                        intent("Yes")
                    }

                    action {
                        context.checkpoints.LeaveDoorClosed = true
                        reactions.go(choosePath)
                    }
                }

                fallback {
                    context.checkpoints.LeaveDoorClosed = true
                    reactions.go(choosePath)
                }
            }

            state(choosePath) {
                action {
                    reactions.say("Окей, я в коридоре, тут тоже дым. Непонятно, что и где горит. Надо эвакуироваться. Лестница справа, лифты слева, куда бежать?")
                }

                state("Stair") {
                    activators {
                        regex("right")
                    }

                    action {
                        reactions.say("Да, кажется, что-то такое нам и говорили на инструктаже по безопасности, жаль, я всё прослушал. Бегу.")
                        context.checkpoints.ChooseEmergencyPath = EmergencyPaths.Stair
                        reactions.go(redButton)
                    }
                }

                fallback {
                    reactions.say("Побежал к лифтам")
                    context.checkpoints.ChooseEmergencyPath = EmergencyPaths.Elevator
                    reactions.go(elevator)
                }
            }

            state(elevator) {
                action {
                    reactions.say("Окей, я добежал до лифтов, вызвал.")
                    reactions.say("Похоже, лифты отключились, и я только зря потратил время… Побегу на лестницу.")
                    context.checkpoints.KeepCalm = false
                    reactions.go(redButton)
                }
            }

            state(redButton) {
                action {
                    reactions.say("Тут на стене какая-то красная кнопка под стеклом, нажать или не тратить время?")
                }

                state("Push it!")
                {
                    activators {
                        intent("PressAlarm")
                    }
                    action {
                        reactions.say("Врубилась тревога")
                        context.checkpoints.ActivateFireAlarm = true
                        reactions.go(LadderLocationScenario.state)
                    }
                }

                fallback {
                    context.checkpoints.ActivateFireAlarm = false
                    reactions.go(LadderLocationScenario.state)
                }
            }

            fallback {
                reactions.say("П?????")
            }
        }
    }
}