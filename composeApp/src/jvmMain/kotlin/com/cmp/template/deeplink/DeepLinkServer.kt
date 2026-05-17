package com.cmp.template.deeplink

import com.sun.net.httpserver.HttpServer
import java.awt.EventQueue
import java.net.InetSocketAddress

/**
 * Embedded local HTTP server that catches the Singpass (or any OAuth) callback
 * on desktop/JVM without needing OS-level URI scheme registration.
 *
 * The singpass-callback edge function redirects to:
 *   http://localhost:54399/callback?code=xxx&state=xxx
 *
 * This server translates that into a deep link string:
 *   com.cmp.template://callback?code=xxx&state=xxx
 * and invokes [onDeepLink] on the AWT event thread.
 *
 * Usage in main.kt:
 *   DeepLinkServer.start { url -> Rinku.onDeepLinkReceived(url) }
 */
object DeepLinkServer {

    const val PORT = 54399
    private const val SCHEME = "com.cmp.template://callback"

    private var server: HttpServer? = null

    fun start(onDeepLink: (String) -> Unit) {
        if (server != null) return
        try {
            val httpServer = HttpServer.create(InetSocketAddress("localhost", PORT), 0)

            httpServer.createContext("/callback") { exchange ->
                val query = exchange.requestURI.rawQuery ?: ""

                // Build the deep link URL that Rinku understands
                val deepLink = if (query.isNotEmpty()) "$SCHEME?$query" else SCHEME

                // Respond with an auto-closing page so the browser tab closes itself
                val html = """
                    <!DOCTYPE html>
                    <html>
                    <head><meta charset="UTF-8"><title>Returning to app…</title></head>
                    <body style="font-family:sans-serif;text-align:center;padding-top:80px">
                      <p>✅ Authenticated! You can close this tab.</p>
                      <script>window.close();</script>
                    </body>
                    </html>
                """.trimIndent().toByteArray()

                exchange.sendResponseHeaders(200, html.size.toLong())
                exchange.responseBody.use { it.write(html) }

                // Dispatch to the AWT event thread — Compose Desktop collects
                // Rinku's StateFlow on the main (Swing/AWT) thread.
                EventQueue.invokeLater { onDeepLink(deepLink) }
            }

            // Simple health-check endpoint
            httpServer.createContext("/health") { exchange ->
                val body = "ok".toByteArray()
                exchange.sendResponseHeaders(200, body.size.toLong())
                exchange.responseBody.use { it.write(body) }
            }

            httpServer.executor = null // use default executor
            httpServer.start()
            server = httpServer
            println("DeepLinkServer running on http://localhost:$PORT/callback")
        } catch (e: Exception) {
            System.err.println("DeepLinkServer failed to start: ${e.message}")
        }
    }

    fun stop() {
        server?.stop(0)
        server = null
    }
}
