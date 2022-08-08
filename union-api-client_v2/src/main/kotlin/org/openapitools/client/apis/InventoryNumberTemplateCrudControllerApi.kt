package org.openapitools.client.apis


import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import org.openapitools.client.models.ApiSecurityUserRolesGetRequestsParametersParameterV2

import org.openapitools.client.models.DeleteResponseV2
import org.openapitools.client.models.GetAllResponseV2
import org.openapitools.client.models.GetResponseV2
import org.openapitools.client.models.InventoryNumberTemplateDtoV2
import org.openapitools.client.models.PageableV2
import org.openapitools.client.models.PostResponseV2
import org.openapitools.client.models.PutResponseV2

interface InventoryNumberTemplateCrudControllerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param requestsParameters 
     * @param pageable 
     * @return [GetAllResponseV2]
     */
    @GET("api/catalogs/inventory-number-template")
    suspend fun apiCatalogsInventoryNumberTemplateGet(@Query("requestsParameters") requestsParameters: ApiSecurityUserRolesGetRequestsParametersParameterV2, @Query("pageable") pageable: PageableV2): Response<GetAllResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [DeleteResponseV2]
     */
    @DELETE("api/catalogs/inventory-number-template/{id}")
    suspend fun apiCatalogsInventoryNumberTemplateIdDelete(@Path("id") id: kotlin.String): Response<DeleteResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @return [GetResponseV2]
     */
    @GET("api/catalogs/inventory-number-template/{id}")
    suspend fun apiCatalogsInventoryNumberTemplateIdGet(@Path("id") id: kotlin.String): Response<GetResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param id 
     * @param inventoryNumberTemplateDtoV2 
     * @return [PutResponseV2]
     */
    @PUT("api/catalogs/inventory-number-template/{id}")
    suspend fun apiCatalogsInventoryNumberTemplateIdPut(@Path("id") id: kotlin.String, @Body inventoryNumberTemplateDtoV2: InventoryNumberTemplateDtoV2): Response<PutResponseV2>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param inventoryNumberTemplateDtoV2 
     * @return [PostResponseV2]
     */
    @POST("api/catalogs/inventory-number-template")
    suspend fun apiCatalogsInventoryNumberTemplatePost(@Body inventoryNumberTemplateDtoV2: InventoryNumberTemplateDtoV2): Response<PostResponseV2>

}
