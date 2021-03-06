package org.coepi.android.cen

import io.realm.Sort.DESCENDING
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.coepi.android.repo.RealmProvider

class RealmCenKeyDao(private val realmProvider: RealmProvider) {
    private val realm get() = realmProvider.realm

    fun lastCENKeys(limit : Int): List<RealmCenKey> =
        realm.where<RealmCenKey>()
            .sort("timestamp", DESCENDING)
            .limit(limit.toLong())
            .findAll()

    fun insert(key: CenKey) {
        realm.executeTransaction {
            val realmObj = realm.createObject<RealmCenKey>()
            realmObj.key = key.key
            realmObj.timestamp = key.timestamp
        }
    }

//    @Query("SELECT * FROM cenkey WHERE :first <= timeStamp AND timeStamp <= :last LIMIT 1")
//    fun findByRange(first: Int?, last: Int?): List<CENKey>?

//    @Delete
//    fun deleteBefore(timestamp : Int)
}
