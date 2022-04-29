package com.levins.junky.ui.main

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.levins.junky.MainActivity
import com.levins.junky.R
import kotlinx.android.synthetic.main.fragment_item_activity.*
import kotlinx.android.synthetic.main.fragment_item_activity.textViewDescription
//import kotlinx.android.synthetic.main.recyclerview_item.*


class ItemActivityFragment : Fragment() {

    companion object {
        fun newInstance() = ItemActivityFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var myHeadline: String
    lateinit var myDescription: String
    lateinit var myPath: String
    lateinit var myPlace: String
    lateinit var myTime: String
    lateinit var myLat: String
    lateinit var myLong: String
    //for the map:
//    lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    lateinit var pileLocation: LatLng
    lateinit var pileMarker: Marker
//    lateinit var myGoogleMap: GoogleMap


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        pileLocation = LatLng(myLat.toDouble(),myLong.toDouble())//32.082056, 34.802089)
        pileMarker = googleMap.addMarker(
            MarkerOptions().position(pileLocation).title("The Pile").icon(
                BitmapDescriptorFactory.fromResource(R.drawable.treasure_small) ) )!!
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pileLocation,15.0f))
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val bundle = this.arguments
        if (bundle != null) {
            myHeadline = bundle.getString("pileHeadline", "headline")
            (activity as MainActivity).setTitle(myHeadline)//(getString(R.string.itemFragmentTitle))
            myDescription = bundle.getString("pileDesc", "description")
            myPath = bundle.getString("imagePath","imagePath")
            myPlace = bundle.getString("place","place")
            myTime = bundle.getString("time", "time")
            myLat = bundle.getString("lat","late")
            myLong = bundle.getString("long","long")
        }

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_item_activity, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //textViewHead.text=myHeadline
        textViewDescription.text=myDescription
        textViewPlace.text=myPlace
        textViewTime.text=myTime
        val fileName = myPath.substringAfterLast("/",myPath)
        val currentPath =  (activity as MainActivity).applicationContext.filesDir.path+ "/images/" + fileName + "/" + fileName
        Glide.with( activity as MainActivity)
            .load(currentPath)
//                    .centerCrop()
            .into(imageView)
        // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}