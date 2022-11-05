import kotlinx.coroutines.runBlocking

/**
 *  java -jar releaseJar/UnionTrelloIntegrate-1.0-SNAPSHOT.jar "22b492657719b482946dc26a33cf197b21f81c8347252bccd00298aad52851ba" "9df1beffd068658909a2d02dcc3a4542" "тестовое сообщение" "63657a168d5d8001234d2974"
 */
fun main(args: Array<String>): Unit = runBlocking {
    val token = args[0]
    val key = args[1]
    val commentText = args[2]
    val listId = args[3]

    val authInfo = AuthInfo(
        token = "token" to token, key = "key" to key
    )

    AddCommentOnCardsController(
        client, authInfo
    ).addComment(commentText, listId)
}