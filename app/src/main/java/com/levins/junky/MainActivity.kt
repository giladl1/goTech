package com.levins.junky

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.levins.junky.ui.main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
//import model.FirestoreSnapshotService
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.media.ExifInterface
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import model.Controller

class MainActivity : AppCompatActivity() {
//    lateinit var db: FirebaseFirestore
    private val LOGIN = 2
//    lateinit var galleryIcon: ImageView


    //    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /////////////////////////
//        val controller = Controller()//todo erase it 29.3.22
//        controller.start(piles)
        /////////////////////////
        setContentView(R.layout.main_activity)
//        db = Firebase.firestore

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MainFragment.newInstance()
                )//MainFragment.newInstance())//AddPileFragment.newInstance())//AddPileFragment
                .commitNow()
        }

    }


    override fun onResume() {
        super.onResume()
        val intent = getIntent()
    }

    //happen after an image was chosen for the pile:
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resultCode, requestCode, data)
        //after login finished of user pressed back:
        if (requestCode == LOGIN && resultCode == RESULT_OK) {
        }
    }

    //if a user is logged in , we will move to addPile fragment:

    //    if there is a logged in user:

    override fun onBackPressed() {
        val myFragment: Fragment? = supportFragmentManager.findFragmentByTag("AddPileFrag")
        if (myFragment != null && myFragment.isVisible) {
            moveToMainFrag()
            // add your code here
        } else super.onBackPressed()
        //todo if aner finished his activity add it here
    }

    fun moveToMainFrag() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }


}