import kotlinx.serialization.json.Json

/**
 * Holds the message history of all sent messages and also keeps track of which users (ChatConnectors) are connected to chat.
 * Also handles message delivery from one ChatConnector to all other ChatConnectors.
 */

object ChatHistory: Observable {

    var history = mutableListOf<ChatMessage>()
    var connectors = mutableSetOf<ChatConnector>()

    fun insert(msg: ChatMessage){
        history.add(msg)
        notifyObservers(msg)
        TopChatter.msgUpdate(msg)
    }

    override fun notifyObservers(msg: ChatMessage) {
        ChatConsole.msgUpdate(msg)
        for(c in connectors){
            c.msgUpdate(msg)
        }
    }

    fun systemMessage(msg: String){
        ChatConsole.systemMessage(msg)
        for(c in connectors){
            c.systemMessage(msg)
        }
    }

    override fun addConnector(c: ChatConnector){
        connectors.add(c)
    }

    override fun removeConnector(c: ChatConnector) {
        connectors.remove(c)
    }

    override fun toString(): String{
        var r = "--Message history: \n"
        for(m in history){
            r += "$m \n"
        }
        return r
    }
}