package com.defaultxyz.skyline.domain

import com.defaultxyz.skyline.domain.api.AddLocationModel
import com.defaultxyz.skyline.domain.api.ApiClient
import com.defaultxyz.skyline.domain.db.LocationDao
import com.defaultxyz.skyline.domain.db.UserDao
import com.defaultxyz.skyline.domain.db.entity.toEntity
import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val userDao: UserDao,
    private val locationDao: LocationDao,
    private val apiClient: ApiClient
) {
    fun retrieveLocations(): Observable<List<Location>> =
        apiClient.getLocations()
            .subscribeOn(Schedulers.io())
            .map {
                it.data?.also { list ->
                    locationDao.insertAll(list.map(Location::toEntity))
                }.orEmpty()
            }

    fun addLocation(location: Location, review: Review): Disposable =
        userDao.getUser()
            .subscribeOn(Schedulers.io())
            .subscribe { entity ->
                apiClient.addLocation(entity.email, AddLocationModel(location, review))
            }
}