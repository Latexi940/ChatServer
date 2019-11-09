/**
 * Keeps track of which username are reserved and cannot be used.
 */

object Users {

    var userList = HashSet<String>()

    fun addUser(u :String){
        userList.add(u)
    }

    fun removeUser(u:String){
        userList.remove(u)
    }

    fun checkIfAvailable(u: String): Boolean{
        return !(userList.contains(u) || u == "")
    }

    override fun toString(): String {
        var r = "--Users online: \n"
        for(m in userList){
            r += "$m \n"
        }

        return r
    }
}