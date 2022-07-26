package org.openapitools.client.apis


import retrofit2.Response
import retrofit2.http.GET


interface AddOrdersControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @return [Unit]
     */
    @GET("api/mock-functional/generate-orders")
    suspend fun apiMockFunctionalGenerateOrdersGet(): Response<Unit>

}
