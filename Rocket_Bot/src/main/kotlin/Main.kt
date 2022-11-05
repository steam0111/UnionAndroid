import kotlinx.coroutines.runBlocking

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