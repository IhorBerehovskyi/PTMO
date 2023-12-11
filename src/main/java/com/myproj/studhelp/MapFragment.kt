package com.myproj.studhelp

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.Manifest
import android.content.Context.LOCATION_SERVICE
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.myproj.studhelp.databinding.FragmentMapBinding



class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var binding : FragmentMapBinding
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        if (mapFragment == null) {
            val newMapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction()
                .replace(R.id.map, newMapFragment)
                .commit()
            newMapFragment.getMapAsync(this)
        } else {
            mapFragment.getMapAsync(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


    companion object {

        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var point =  LatLng(49.834786, 24.008059)
        mMap.addMarker(MarkerOptions().position(point).title("asdf"))
        mMap.isBuildingsEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10.0f, this)
            } catch (ex: Exception) {
                ex.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude

        val userLatLng = LatLng(latitude, longitude)
        //currentPosition?.remove()
        var options = MarkerOptions().position(userLatLng).title("User's Location")
        //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        //currentPosition =
            mMap.addMarker(options)
    }


}