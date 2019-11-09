interface Observable {

    fun addConnector(c:ChatConnector)
    fun removeConnector(c:ChatConnector)
    fun notifyObservers (message:ChatMessage)
}