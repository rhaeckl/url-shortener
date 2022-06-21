package com.dkbcodefactory.routes

import com.dkbcodefactory.models.urlStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.math.BigInteger
import java.security.MessageDigest

fun Route.urlShorterRouting() {
    route("/api/v1/shorten")
    {
        get("{shortUrl?}") {
            val shortUrl = call.parameters["shortUrl"] ?: return@get call.respondText(
                "Missing Short URL",
                status = HttpStatusCode.BadRequest
            )
            val url = urlStorage[shortUrl] ?: return@get call.respondText(
                "No URL found for Short URL $shortUrl",
                status = HttpStatusCode.NotFound
            )
            call.respond(url)
        }
        post {
            val url = call.receive<String>()
            val hasBytes = MessageDigest.getInstance("MD5").digest(url.toByteArray(Charsets.UTF_8))
            val hasString = String.format("%032x", BigInteger(1, hasBytes))
            val truncatedHashString =  hasString.take(6)
            urlStorage[truncatedHashString] = url
            call.respondText("Short URL has been created", status = HttpStatusCode.Created)
        }
    }
}
