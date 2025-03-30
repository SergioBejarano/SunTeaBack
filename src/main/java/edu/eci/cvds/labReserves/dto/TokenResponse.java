package edu.eci.cvds.labReserves.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TokenResponse is a record that represents the response containing the authentication tokens.
 * It includes both an access token and a refresh token, which are used for user authentication and session management.
 */
public record TokenResponse (

        @JsonProperty("access_token")
        String accessToken, //The access token used for authenticating API requests.

        @JsonProperty("refresh_token")
        String refreshToken //The refresh token used to obtain a new access token when the current one expires.
){
}
