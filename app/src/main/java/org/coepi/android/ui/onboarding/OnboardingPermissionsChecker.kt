package org.coepi.android.ui.onboarding

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.BLUETOOTH
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.PublishSubject.create
import org.coepi.android.MainActivity.RequestCodes
import org.coepi.android.system.log.log


class OnboardingPermissionsChecker {

    val observable: PublishSubject<Boolean> = create()

    private val requestCode = RequestCodes.onboardingPermissions

    fun checkForPermissions(activity: Activity) {
        val permissions = arrayOf(BLUETOOTH, ACCESS_COARSE_LOCATION)
        val hasAllPermissions = permissions.all {
            checkSelfPermission(activity, it) == PERMISSION_GRANTED
        }
        if (hasAllPermissions) {
            observable.onNext(true)
        } else {
            requestPermissions(activity, permissions, requestCode)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                   grantResults: IntArray) {

        if (requestCode != this.requestCode) return

        if (grantResults.all { it == PERMISSION_GRANTED }) {
            log.i("Permissions granted")
            observable.onNext(true)
        } else {
            log.i("Permissions denied")
            observable.onNext(false)
        }
    }
}
