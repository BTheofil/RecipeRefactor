package hu.tb.core.data.network

import hu.tb.core.domain.network.NetworkError
import hu.tb.core.domain.network.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> handleNetworkCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        e.printStackTrace()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return respondToResult(response)
}

suspend inline fun <reified T> respondToResult(response: HttpResponse): Result<T, NetworkError> =
    when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        else -> Result.Error(NetworkError.HTTP_ERROR)
    }