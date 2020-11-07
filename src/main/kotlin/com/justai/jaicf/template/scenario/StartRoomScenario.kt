package com.justai.jaicf.template.scenario

import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.helpers.ssml.audio
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.state.*

object StartRoomScenario : Scenario() {

    const val state = "/location/start_room"
    private const val window = "/location/window"
    private const val door = "/location/door"
    private const val things = "/location/things"

    private fun ActionContext.handleSmoke() {
        StartLocationSmoke.handleSmoke(this)
    }

    private fun ActionContext.goToState(state: String) {
        handleSmoke()
        reactions.go(state)
    }

    init {
        state(state) {
            action {
                handleSmoke()
            }

            state("call firefighters") {
                activators {
                    intent("CallFirefighters")
                }

                action {
                    reactions.say("Да, пожарных я уже вызвал, но когда они ещё приедут, а мне нужно что-то делать…")
                    checkpoints.CallFirefighters = true
                    checkpoints.KeepCalm = true
                    goToState(window)
                }
            }

            state("keep calm") {
                activators {
                    intent("KeepCalm")
                }

                action {
                    reactions.say("Вдох-выдох, вдох-выдох")
                    context.checkpoints.CallFirefighters = false
                    context.checkpoints.KeepCalm = true
                    goToState(window)
                }

            }

            fallback {
                handleSmoke()
                reactions.say("АААААААААа!!!!!!")
                context.checkpoints.CallFirefighters = false
                context.checkpoints.KeepCalm = false
                reactions.go(window)
            }
        }

        fun ActionContext.openWindow() {
            reactions.say("Кажется, стало только хуже, дыма резко стало больше, кажется, я даже вижу огонёк.")
            context.checkpoints.OpenWindow = true
            StartLocationSmoke.increaseVelocity(this)
            goToState(door)
        }

        state(window) {
            action {
                reactions.say("Думаю открыть окно. Стоит?")
            }

            state("No") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Отлично, понял.")
                    context.checkpoints.OpenWindow = false
                    goToState(door)
                }
            }

            state("Yes") {
                activators {
                    intent("Yes")
                }
                action {
                    openWindow()
                }
            }

            fallback {
                reactions.say("Открыл.")
                openWindow()
            }
        }

        state(door) {
            action {
                reactions.say("Надо выбираться отсюда, сейчас только дверь открою.")
            }

            state("Check doorknob") {
                activators {
                    intent("CheckDoorknob")
                }

                action {
                    reactions.say("Сейчас проверю")
                    context.checkpoints.CheckDoorknob = true
                    reactions.go(things)
                }
            }

            fallback {
                context.checkpoints.CheckDoorknob = false
                goToState(things)
            }
        }

        state(things) {
            action {
                reactions.say("Стоп, надо собрать с собой учебники библиотечные и конспекты, новые джинсы, а вдруг сгорят? Так, где тут чемодан…")
            }
            
            state("No") {
                activators {
                    intent("No")
                }
                action {
                    reactions.say("Окей. Но, если что, ты будешь разбираться с библиотекарем.")
                    context.checkpoints.GetExtraClothes = false
                    goToState(StartFloorScenario.state)
                }
            }

            fallback {
                reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_вещи_сокр.wav"))
                reactions.say(audio("https://248305.selcdn.ru/demo_bot_static/Keep_talking_чемодан.wav"))
                reactions.say("Ай. Чемодан не проходит в дверь! Ну и ладно, побегу налегке.")
                context.checkpoints.GetExtraClothes = true
                goToState(StartFloorScenario.state)
            }
        }
    }
}