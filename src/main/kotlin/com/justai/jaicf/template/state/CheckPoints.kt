package com.justai.jaicf.template.state

import com.justai.jaicf.context.BotContext

class CheckPoints(
    context: BotContext
) {
    var KeepCalm: Boolean? by context.session
    var CallFirefighters: Boolean? by context.session
    var OpenWindow: Boolean? by context.session
    var GetExtraClothes: Boolean? by context.session
    var CheckDoorknob: Boolean? by context.session
    var LeaveDoorClosed: Boolean? by context.session
    var ChooseEmergencyPath: EmergencyPaths? by context.session
    var ActivateFireAlarm: Boolean? by context.session
    var RunOnStair: Boolean? by context.session
    var GoToSmoke: Boolean? by context.session
    var FillGap: Boolean? by context.session
    var AlertFirefighters: Boolean? by context.session
}