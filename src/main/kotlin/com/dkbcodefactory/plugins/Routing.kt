package com.dkbcodefactory.plugins

import com.dkbcodefactory.routes.urlShorterRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        urlShorterRouting()
    }
}
