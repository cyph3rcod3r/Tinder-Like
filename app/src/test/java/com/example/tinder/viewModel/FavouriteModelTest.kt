package com.example.tinder.viewModel

import com.example.tinder.FavouriteViewModel
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

class FavouriteModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)

    private lateinit var favouriteViewModel: FavouriteViewModel

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
        favouriteViewModel = FavouriteViewModel(repository)
    }

    @Test
    fun testEmpty() {
        runBlocking {
            `when`(repository.getFavouriteProfiles()).thenReturn(emptyList())
        }
        favouriteViewModel.getProfiles()
        Assert.assertEquals(TinderState.Error("No favourites found!"), favouriteViewModel.state.value)
    }

    @Test
    fun testSome() {
        runBlocking {
            `when`(repository.getFavouriteProfiles()).thenReturn(listOf(Person(1,"a")))
        }
        favouriteViewModel.getProfiles()
        Assert.assertEquals(TinderState.Success(listOf(Person(1, "a"))), favouriteViewModel.state.value)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}