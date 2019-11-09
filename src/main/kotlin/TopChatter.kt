/**
 * Keeps track of number of sent messages in chat and can return a top list of users who has sent most messages.
 */

object TopChatter: Observer {

    var messagesSent = mutableMapOf<String, Int>()

    override fun msgUpdate(msg: ChatMessage) {
        var i = messagesSent[msg.user]
        if (i != null) {
            i+=1
            messagesSent[msg.user] = i
        }
    }

    override fun toString(): String {
        var results = messagesSent.toList().sortedBy { (_, value) -> value}.reversed().toMap()
        var chatters = "--Top chatters are: \n"

        for (r in results){
            chatters += "User: " + r.key + ", Messages: " + r.value + "\n"
        }
        return chatters
    }
}