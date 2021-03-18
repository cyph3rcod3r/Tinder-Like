package com.example.tinder.viewModel

import com.example.tinder.MainViewModel
import com.example.tinder.domain.Repository
import com.example.tinder.domain.dao.PersonDao
import com.example.tinder.domain.model.Base
import com.example.tinder.domain.model.Person
import com.example.tinder.domain.source.LocalSource
import com.example.tinder.domain.source.RemoteSource
import com.example.tinder.domain.states.TinderState
import com.example.tinder.service.ApiService
import com.example.tinder.util.ManagedCoroutineScope
import com.example.tinder.util.SwipeResult
import com.example.tinder.util.TestScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.Exception

class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)

    private lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var personDao: PersonDao

    lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = Repository(LocalSource(personDao), RemoteSource(apiService))
        mainViewModel = MainViewModel(repository)
    }

    @Test
    fun testEmpty() {
        runBlocking {
            `when`(repository.getTinderProfiles()).thenReturn(Base())
        }
        mainViewModel.getProfiles()
        Assert.assertEquals(TinderState.Success(emptyList()), mainViewModel.state.value)
    }

    @Test
    fun testErr() {
        runBlocking {
            `when`(repository.getTinderProfiles()).thenReturn(null)
        }
        mainViewModel.getProfiles()
        Assert.assertEquals(TinderState.Thr(null), mainViewModel.state.value)
    }

    @Test
    fun swipe() {
        runBlocking {
            `when`(repository.savePersonProfile(Person(1, "a"))).thenReturn(1L)
        }
        mainViewModel.executeNext(SwipeResult.ACCEPT, Person(1, "a"))
        Assert.assertEquals(1L, mainViewModel.update)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}