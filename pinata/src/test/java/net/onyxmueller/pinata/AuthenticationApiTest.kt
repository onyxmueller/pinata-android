package net.onyxmueller.pinata

import kotlinx.coroutines.test.runTest
import net.onyxmueller.pinata.authentication.AuthenticationApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class AuthenticationApiTest : ApiAbstract<AuthenticationApi>() {
    private lateinit var api: AuthenticationApi

    @Before
    fun initApi() {
        api = createService(AuthenticationApi::class.java)
    }

    @Test
    fun testAuthenticationApiTest() =
        runTest {
            enqueueResponse("test.json")

            val response = api.test()
            val responseBody = requireNotNull((response as PinataApiResponse.Success).data)

            assertThat(responseBody.message, `is`("Congratulations! You are communicating with the Pinata API!"))
        }
}
