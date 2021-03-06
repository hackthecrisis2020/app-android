package org.coepi.android.ui.cen

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import org.coepi.android.ble.BleManager
import org.coepi.android.cen.Cen
import org.coepi.android.cen.CenRepo
import org.coepi.android.cen.RealmCenReport
import org.coepi.android.extensions.toLiveData
import org.coepi.android.ui.navigation.RootNavigation

class CENViewModel(
    repo: CenRepo,
    private val rootNav: RootNavigation,
    private val bleManager: BleManager
) : ViewModel() {

    //val curcen = Base64.encode(repo.CEN.value,0);
    // CEN being broadcast by this device
    val myCurrentCEN = repo.generatedCen
        .map { it.toString() }
        .toLiveData()

    // recently observed CENs
    val neighborCENs: LiveData<List<String>> = bleManager.scanObservable
        .scan(emptyList<Cen>()) { acc, element -> acc + element }
        .map { cens ->
            cens.map { it.toString() }
        }
        .toLiveData()

    // TODO
    val cenReports: MutableLiveData<List<RealmCenReport>> by lazy {
        MutableLiveData<List<RealmCenReport>>()
    }

    fun insertPastedCEN( curcen :String = "" ){
        //val curcen = "";
        //repo.insertCEN(curcen)
    }

    private fun update() {
        // contacts.value = contactDao.findByRange(0, 99999999999)
        // symptoms.value = symptomsDao.findByRange(0, 99999999999)
    }
}
