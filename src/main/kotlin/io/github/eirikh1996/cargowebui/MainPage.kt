package io.github.eirikh1996.cargowebui

import com.degitise.minevid.dtlTraders.guis.gui.TradeGUI
import com.degitise.minevid.dtlTraders.guis.items.TradableGUIItem
import com.degitise.minevid.dtlTraders.utils.citizens.TraderTrait
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import io.github.cccm5.CargoTrait
import io.github.cccm5.Utils
import org.bukkit.scheduler.BukkitRunnable
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MainPage : HttpHandler {
    var page = "Loading...."

    init {
        object : BukkitRunnable() {
            override fun run() {
                try {
                    page = buildPage()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }

        }.runTaskTimerAsynchronously(CWUIMain.instance, 40, 6000)
    }

    fun buildPage() : String {
        var page = "<html>\n"
        page += "<head>\n"
        page += "</head>\n"
        page += "<body>\n"
        val merchantPriceMap = HashMap<String, Map<String, Double>>()
        val merchantList = ArrayList<String>()
        val itemList = ArrayList<String>()
        for (npc in Utils.getNPCsWithTrait(CargoTrait::class.java)) {
            if (CWUIMain.instance.ignoreMerchants.contains(npc.name)) {
                continue
            }
            val guiName = npc.getTrait(TraderTrait::class.java).guiName
            val gui = CWUIMain.instance.dtlTradersPlugin.guiListService.getGUI(guiName) as TradeGUI
            val priceMap = HashMap<String, Double>()
            for (page in gui.pages) {
                for (item in page.getItems("buy")) {
                    if (item !is TradableGUIItem)
                        continue
                    val itemName = if (item.getMainItem().getItemMeta()!!.getDisplayName() != null && item.getMainItem().getItemMeta()!!.getDisplayName().length > 0) item.getMainItem().getItemMeta()!!.getDisplayName() else item.getMainItem().getType().name.toLowerCase()
                    if (!itemList.contains(itemName))
                        itemList.add(itemName)
                    priceMap.put(itemName, item.tradePrice)
                }
            }
            merchantList.add(npc.name)
            merchantPriceMap.put(npc.name, priceMap)
        }
        merchantList.sort()
        itemList.sort()
        page += "<table>\n"
        page += "<tr>\n"
        page += "<th>Cargo item</th>\n"
        for (merchant in merchantList) {
            page += "<th>"
            page += merchant
            page += "</th>\n"
        }
        page += "</tr>\n"
        for (item in itemList) {
            page += "<tr>\n"
            page += "<td>"
            page += item
            page += "</td>\n"
            for (merchant in merchantList) {
                val prices = merchantPriceMap.get(merchant)!!
                page += "<td>"
                page += if (prices.containsKey(item)) prices.get(item) else "-"
                page += "</td>\n"
            }
            page += "</tr>\n"
        }
        page += "</table>\n"
        page += "</body>\n"
        page += "</html>"

        return page
    }

    override fun handle(exchange: HttpExchange?) {
        if (exchange == null)
            return
        val requestParam : String
        if ("GET".equals(exchange.requestMethod)) {
            requestParam = handleGetRequest(exchange)
        } else if ("POST".equals(exchange.requestMethod)) {
            requestParam = handlePostRequest(exchange)
        } else {
            return
        }
    }

    private fun handleGetRequest(exchange: HttpExchange?) : String {

    }

    private fun handlePostRequest(exchange: HttpExchange?) : String {

    }
}