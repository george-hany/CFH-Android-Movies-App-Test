package com.core.data.model.login

import com.google.gson.annotations.SerializedName

class LoginRequestModel {
    @SerializedName("username")
    var userName: String = ""

    @SerializedName("password")
    var password: String = ""
}
