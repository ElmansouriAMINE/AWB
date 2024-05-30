package com.example.testoo

import com.example.testoo.Data.Repository.UserRepositoryImpl
import com.example.testoo.Domain.models.*
import com.google.firebase.database.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var mockDatabase: FirebaseDatabase
    private lateinit var mockReference: DatabaseReference

    @Before
    fun setUp() {
        mockDatabase = mockk(relaxed = true)
        mockReference = mockk(relaxed = true)
        userRepository = UserRepositoryImpl()

        mockkStatic(FirebaseDatabase::class)
        every { FirebaseDatabase.getInstance() } returns mockDatabase
    }


    @Test
    fun testGetCurrentUser() = runBlocking {
        val userId = "testUserId"
        val mockUser = User(id = userId, userName = "AMINE ELMANSOURI", email = "amine@gmail.com")

        every { mockDatabase.getReference("users") } returns mockReference
        every { mockReference.orderByChild("id").equalTo(userId) } returns mockReference
        coEvery { mockReference.get().await() } returns mockDataSnapshot(listOf(mockUser))

        val result = userRepository.getCurrentUser(userId)

        assertNotNull(result)
        assertEquals(mockUser, result)
    }



    // Helper function to create a mock DataSnapshot
    private fun <T> mockDataSnapshot(value: List<T>): DataSnapshot {
        val mockSnapshot = mockk<DataSnapshot>()
        every { mockSnapshot.children } returns value.map {
            val childSnapshot = mockk<DataSnapshot>()
            every { childSnapshot.getValue(any<Class<T>>()) } returns it
            childSnapshot
        }.asIterable()
        return mockSnapshot
    }
}
