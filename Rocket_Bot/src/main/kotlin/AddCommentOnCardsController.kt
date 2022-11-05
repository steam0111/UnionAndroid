import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddCommentOnCardsController(
    private val httpClient: HttpClient,
    private val authInfo: AuthInfo
) {
    suspend fun addComment(comment: String, listId: String) = withContext(Dispatchers.IO) {
        val cards = httpClient.get {
            url("https://api.trello.com/1/lists/$listId/cards")
            parameter(authInfo.token.first, authInfo.token.second)
            parameter(authInfo.key.first, authInfo.key.second)
        }.body<List<Card>>()

        cards.forEach { card ->
            httpClient.post {
                url("https://api.trello.com/1/cards/${card.id}/actions/comments")
                parameter(authInfo.token.first, authInfo.token.second)
                parameter(authInfo.key.first, authInfo.key.second)
                parameter("text", comment)
            }
        }
    }
}