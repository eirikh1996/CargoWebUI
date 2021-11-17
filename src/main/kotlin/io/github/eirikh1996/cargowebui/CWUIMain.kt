package io.github.eirikh1996.cargowebui

import com.degitise.minevid.dtlTraders.Main
import io.github.cccm5.CargoMain
import org.bukkit.plugin.java.JavaPlugin

class CWUIMain : JavaPlugin() {
    lateinit var ignoreMerchants : List<String>;
    var port = 6000
    lateinit var dtlTradersPlugin : Main
    lateinit var cargoPlugin : CargoMain

    override fun onEnable() {
        val dtlTraders = server.pluginManager.getPlugin("dtlTraders")
        if (dtlTraders !is Main || !dtlTraders.isEnabled) {
            logger.severe("dtlTraders is required, but was not found or is disabled. CargoWebUI will therefore be disabled")
            server.pluginManager.disablePlugin(this)
            return
        }
        dtlTradersPlugin = dtlTraders
        val cargo = server.pluginManager.getPlugin("Cargo")
        if (cargo !is CargoMain || !dtlTraders.isEnabled) {
            logger.severe("Cargo is required, but was not found or is disabled. CargoWebUI will therefore be disabled")
            server.pluginManager.disablePlugin(this)
            return
        }
        cargoPlugin = cargo
        if (config.contains("ignoreMerchants")) {
            ignoreMerchants = config.getStringList("ignoreMerchants")
        } else {
            ignoreMerchants = ArrayList()
        }
        port = config.getInt("port", 6000)

    }
    companion object {
        lateinit var instance : CWUIMain
    }
}