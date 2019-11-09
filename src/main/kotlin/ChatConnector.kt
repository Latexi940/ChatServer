import kotlinx.serialization.json.JSON
import java.io.PrintWriter
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Handles the data traffic between client and server. First it asks valid username from users and after that it is ready receive chat messages on its infeed.
 * It creates ChatMessage-objects from those messages and sends them to ChatHistory-singleton.
 * Then it receives it back with along other ChatConnectors and prints messages to outfeed. Also different commands from user are handled here.
 */

class ChatConnector(private val ins: Scanner, private val out: PrintWriter):Runnable, Observer {

    override fun msgUpdate(msg: ChatMessage) {
        out.println(msg)
    }

    fun systemMessage(msg:String){
        out.println(msg)
    }

    override fun run() {

        var username = "noName"
        var userLoggedIn = false

        try {
            out.println("PLEASE ENTER YOUR USERNAME:")
            var tryName = ins.nextLine()

            var nameAsCM = JSON.parse(ChatMessage.serializer(), tryName)
            var possibleName = nameAsCM.msg

            while (!Users.checkIfAvailable(possibleName)) {
                out.println("--Username $possibleName not available. Please choose another one.")
                tryName = ins.nextLine()
                nameAsCM = JSON.parse(ChatMessage.serializer(), tryName)
                possibleName = nameAsCM.msg
            }

            username = possibleName
            userLoggedIn = true

        } catch (e: Exception) {
            println("Unknown user disconnected while trying to login with error: --- $e")
        }
        if (userLoggedIn) {
            ChatHistory.addConnector(this)
            Users.addUser(username)
            TopChatter.messagesSent.putIfAbsent(username, 0)
            ChatHistory.systemMessage("--$username joined chat!")
            out.println("--Type \"/help\" for commands ")

            while (true) {
                try {
                    val line = ins.nextLine()
                    if (line.contains("/quit")) {
                        out.println("--Chat closed")
                        Users.removeUser(username)
                        break
                    } else if (line.contains("/time")) {
                        out.println("-- Clock is now " + java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    } else if (line.contains("/history")) {
                        out.println(ChatHistory.toString())
                    } else if (line.contains("/top")) {
                        out.println(TopChatter.toString())
                    } else if (line.contains("/users")) {
                        out.println(Users.userList.toString())
                    } else if (line.contains("/help")) {
                        out.println("--Type /quit to close the chat \n--Type /history to see older messages \n--Type /top to see top chatters \n--Type /users to see who is online \n--Type /time to see current time")
                    } else if (line == null || line == "") {
                        continue
                    } else {
                        var msg = JSON.parse(ChatMessage.serializer(), line)
                        ChatHistory.insert(msg)
                    }
                } catch (e: Exception) {
                    Users.removeUser(username)
                    println("$username disconnected with error: --- $e")
                    break
                }
            }
            ChatHistory.systemMessage("--$username left the chat")
            ChatHistory.removeConnector(this)
        }
    }
}
