package com.core.data.model.login

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("access_token")
    var accessToken: String? = null,
    @SerializedName("expires_in")
    var expiresIn: Int? = null,
    @SerializedName("id_token")
    var idToken: String? = null,
    @SerializedName("refresh_token")
    var refreshToken: String? = null,
    @SerializedName("token_type")
    var tokenType: String? = null,
)
