package com.defaultxyz.skyline.test

import com.defaultxyz.skyline.domain.LocationRepository
import com.defaultxyz.skyline.domain.api.AddLocationModel
import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.api.Response
import com.defaultxyz.skyline.domain.db.LocationDao
import com.defaultxyz.skyline.domain.db.UserDao
import com.defaultxyz.skyline.domain.db.entity.LocationEntity
import com.defaultxyz.skyline.domain.db.entity.UserEntity
import com.defaultxyz.skyline.domain.model.Location
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationRepositoryTest {
    @Mock
    lateinit var userDao: UserDao
    @Mock
    lateinit var locationDao: LocationDao
    @Mock
    lateinit var apiClient: ApiClient

    private lateinit var repository: LocationRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(userDao.getUser()).thenReturn(Single.just(mockUser))

        whenever(apiClient.getLocations()).thenReturn(
            Observable.just(testSuccessResponse)
                .subscribeOn(Schedulers.trampoline())
        )

        repository = LocationRepository(userDao, locationDao, apiClient)
    }

    @Test
    fun `Should retrieve locations`() {
        repository.retrieveLocations().test()
        verify(apiClient).getLocations()
    }

    @Test
    fun `Should save locations to database`() {
        repository.retrieveLocations().test()
        verify(locationDao).insertAll(listOf(mockLocationEntity))
    }

    @Test
    fun `Should add location`() {
        val expectedRequestModel = AddLocationModel(mockLocation, mock())

        repository.addLocation(mockLocation, expectedRequestModel.review).test()

        verify(apiClient).addLocation(any(), eq(expectedRequestModel))
    }

    companion object {
        private val mockUser = mock<UserEntity> {
            on { email } doReturn "Test"
        }

        private val mockLocationEntity = mock<LocationEntity>()
        private val mockLocation = mock<Location> {
            on { toEntity() } doReturn mockLocationEntity
        }

        private val testSuccessResponse = Response("Success", listOf(mockLocation))
    }
}