package edu.eci.cvds.labReserves.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TokenResponse represents the response containing authentication tokens.
 * It includes an access token and a refresh token for session management.
 *
 * @param accessToken The access token used for authenticating API requests.
 * @param refreshToken The refresh token used to obtain a new access token.
 */
public record TokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) {
    /**
     * Returns the access token.
     *
     * @return The JWT access token.
     */
    public String getJwtToken() {
        return accessToken;
    }

    /**
     * Returns the refresh token.
     *
     * @return The refresh token string.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
