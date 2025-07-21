package hu.tb.core.domain.network

sealed interface Result<out D, out E: NetworkError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E : NetworkError>(val error: E): Result<Nothing, E>
}