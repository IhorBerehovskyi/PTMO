package com.myproj.studhelp

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.myproj.studhelp.databinding.FragmentMapBinding
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.model.TravelMode


class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var binding : FragmentMapBinding
    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap
    private var currentPosition: Marker? = null
    private var isInitialLocationUpdate = false


    private var pointsMap: Map<String, LatLng> = mapOf(
        "Main building" to LatLng( 49.83500097293507, 24.01447421201443),
        "Building №1" to LatLng(49.83510061296115,  24.0105404673798),
        "Building №2" to LatLng(49.835872633787446,  24.012640465005553),
        "Building №3" to LatLng(49.836381218153456, 24.01379567668947),
        "Building №4" to LatLng(49.83661363414533, 24.011016512071123),
        "Building №5" to LatLng(49.83480318686737, 24.0081430641997),
        "Building №6" to LatLng(49.835023975286425, 24.006566859919904),
        "Building №7" to LatLng(49.83445120770019, 24.00965766968663),
        "Building №8" to LatLng(49.8375267323081, 24.012746537629425),
        "Building №9" to LatLng(49.8361256936255, 24.01444144858137),
        "Building №10" to LatLng(49.8366281676076, 24.015460394815783),
        "Building №11" to LatLng(49.835871, 24.016391),
        "Building №12" to LatLng(49.83612549740473, 24.014526109446653),
        "Building №13" to LatLng(49.83612549740473, 24.014526109446653),
        "Building №20" to LatLng(49.83728179406068, 24.033333744283965)
    )


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
        { super.onViewCreated(view, savedInstanceState) }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 10.0f, this)
            } catch (ex: Exception) {
                ex.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }

        pointsMap.forEach { (buildingName, latLng) ->
            mMap.addMarker(MarkerOptions().position(latLng).title(buildingName))
        }

        mMap.setOnMarkerClickListener { marker ->

            mMap.clear()
            pointsMap.forEach { (buildingName, latLng) ->
                mMap.addMarker(MarkerOptions().position(latLng).title(buildingName))
            }

            mMap.addMarker(MarkerOptions().position(currentPosition!!.position).title("User's Location").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

            val buildingName = marker.title

            val building = mMap.addMarker(MarkerOptions().position(marker.position).title(marker.title))
            building!!.showInfoWindow()

            if (pointsMap.containsKey(buildingName)) {
                val clickedBuildingLatLng = pointsMap[buildingName]
                if (clickedBuildingLatLng != null && clickedBuildingLatLng != currentPosition!!.position) {

                    try {
                        mMap.drawRouteOnMap(
                            mapsApiKey = "AIzaSyBInBx7LRAWobYAkDQc_ahFSzpO1imQ0_o",
                            context = requireContext(),
                            source = currentPosition!!.position,
                            destination = clickedBuildingLatLng,
                            markers = false,
                            polygonWidth = 10,
                            travelMode = TravelMode.WALKING
                        )
                    }
                    catch (e: Exception){
                        Toast.makeText(requireContext(), "Impossible to route", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
    }

    override fun onLocationChanged(location: Location) {

        val latitude = location.latitude
        val longitude = location.longitude

        val userLatLng = LatLng(latitude, longitude)
        currentPosition?.remove()
        val options = MarkerOptions().position(userLatLng).title("User's Location").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        currentPosition = mMap.addMarker(options)

        if (!isInitialLocationUpdate){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition!!.position, 15.0F));
            isInitialLocationUpdate = !isInitialLocationUpdate
        }
    }
}