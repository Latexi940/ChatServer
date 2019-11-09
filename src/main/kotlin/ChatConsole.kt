/**
 * Prints all chat messages to console.
 */

object ChatConsole: Observer {
    override fun msgUpdate(msg: ChatMessage) {
        println(msg)
    }

    fun systemMessage(msg:String){
        println(msg)
    }
}
