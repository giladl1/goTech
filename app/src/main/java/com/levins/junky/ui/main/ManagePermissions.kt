import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.levins.junky.MainActivity
import com.levins.junky.R
import kotlinx.android.synthetic.main.main_fragment.*

class ManagePermissions(val activity: Activity, val list: List<String>, val PERMISSION_REQUEST_CODE:Int): FragmentActivity(),ActivityCompat.OnRequestPermissionsResultCallback {
    var requestMultiplePermissions:ActivityResultLauncher<Array<String>>
        = (activity as MainActivity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var result = true
        permissions.entries.forEach {
//                Log.e("DEBUG", "${it.key} = ${it.value}")
            if(it.value==false)
                result=false
        }
//        if(result==true)
//            (activity as MainActivity).moveToAddPileFrag()
    }
    // Check permissions at runtime
    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showAlert()
        } else {
//            (activity as MainActivity).moveToAddPileFrag()
        }
    }
    // Check permissions status
    private fun isPermissionsGranted(): Int {
        // PERMISSION_GRANTED : Constant Value: 0
        // PERMISSION_DENIED : Constant Value: -1
        var counter = 0;
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }
    // Find the first denied permission
    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED) return permission
        }
        return ""
    }
    // Show alert dialog to request permissions
    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("יש צורך בהרשאות")
        builder.setMessage("פרסום של ערימה מחייב אישור כל ההרשאות.")
        builder.setPositiveButton("OK", { dialog, which -> requestPermissions() })
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun aaa(){

        requestMultiplePermissions = (activity as MainActivity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var result = true //will be true if all requests approved
            permissions.entries.forEach {
//                Log.e("DEBUG", "${it.key} = ${it.value}")
                if (it.value == false)
                    result = false
            }

        }
    }
    // Request the permissions at run time
    private fun requestPermissions() {
//        var result = true //will be true if all requests approved
        val permission = deniedPermission()
//        requestMultiplePermissions = (activity as MainActivity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            permissions.entries.forEach {
////                Log.e("DEBUG", "${it.key} = ${it.value}")
//                if(it.value==false)
//                    result=false
//            }
//            if(result==true)
//                (activity as MainActivity).moveToAddPileFrag()
//        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Show an explanation asynchronously
            Toast.makeText(activity, "פרסום של ערימה מחייב אישור כל ההרשאות.", Toast.LENGTH_LONG).show()
            requestMultiplePermissions.launch(list.toTypedArray())
//                arrayOf(
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            )
        } else {
//            requestMultiplePermissions = (activity as MainActivity).registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//                permissions.entries.forEach {
//                    Log.e("DEBUG", "${it.key} = ${it.value}")
//                }
//            }
            requestMultiplePermissions.launch(list.toTypedArray())
//            ActivityCompat.requestPermissions(activity, list.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }
}