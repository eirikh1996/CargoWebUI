package io.github.eirikh1996.cargowebui

import com.sun.net.httpserver.HttpServer
import org.bukkit.scheduler.BukkitRunnable
import sun.net.httpserver.HttpServerImpl
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicBoolean

object WebHandler {
    val serverStarted = AtomicBoolean()
    val server = HttpServer.create(InetSocketAddress(CWUIMain.instance.port), 0)

    fun startServer() {
        val serverTask = object : BukkitRunnable() {
            override fun run() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.runTaskAsynchronously(CWUIMain.instance)
    }
}