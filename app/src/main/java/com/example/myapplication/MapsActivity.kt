package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mapFragment : SupportMapFragment

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var lastLocation : Location
    var fusedLocationProviderClient: FusedLocationProviderClient?=null
    var currentLocation : Location?=null
    var currentMarker : Marker?= null
    //var currentAddress : String = ""
    var currentCity : String = ""
    var lat : String = ""
    var long : String = ""
    //val chooseLocation: Button?=null
    val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }




    var mAuth: FirebaseAuth? = null
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_maps)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val chooseLocation: Button =findViewById<Button>(R.id.chooseLocation)
        //val chooseLocation : Button = findViewById<Button>(R.id.chooseLocation)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        binding.chooseLocation.setOnClickListener{
            val intent = Intent(this, WeatherActivity::class.java).apply {
                putExtra("currentCity", currentCity)
                putExtra("lat", lat)
                putExtra("lon", long)
            }
            startActivity(intent)
        }



     //   val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
      //  val navView: NavigationView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.openDrawer, R.string.closeDrawer)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.mobileNumber-> it.title = mAuth?.currentUser?.phoneNumber.toString()

                R.id.historyScreen->{
                    val intent = Intent(this, HistoryActivity::class.java).apply {
                        putExtra("currentCity", currentCity)
                        putExtra("lat", lat)
                        putExtra("lon", long)
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }

                R.id.logout-> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirm")
                    builder.setMessage("Are you sure, you want to logout")
                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        mAuth!!.signOut()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                    builder.show()
                }
            }
            true
        }
        mAuth = FirebaseAuth.getInstance()

        //val welcomeText : TextView = findViewById<TextView>(R.id.welcomeText)
        //val signOutButton : Button = findViewById<Button>(R.id.signOutButton)

        val menu: Menu = binding.navView.getMenu()
        val nav_mobile: MenuItem = menu.findItem(R.id.mobileNumber)
        nav_mobile.title = mAuth?.currentUser?.phoneNumber.toString()
        // welcomeText.text ="Logged in from: " + mAuth?.currentUser?.phoneNumber.toString()
        /* signOutButton.setOnClickListener{
             mAuth!!.signOut()
             val intent = Intent(this, MainActivity::class.java)
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
             startActivity(intent)
             finish()
         }*/
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),1000)
            return
        }

        @SuppressLint("MissingPermission")
        var task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener {location->
            if(location!=null){
                this.currentLocation = location
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
                val view = mapFragment.view
                view?.isClickable = true

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            1000-> if (grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        var latLong = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        drawMarker(latLong)

        googleMap.setOnMarkerDragListener(object :GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {
                TODO("Not yet implemented")
            }

            @SuppressWarnings("unchecked")
            override fun onMarkerDragEnd(p0: Marker) {
                if(currentMarker!=null)
                    currentMarker?.remove()
                val newLatLong = LatLng(p0?.position!!.latitude, p0?.position.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(p0.getPosition()));
                drawMarker(newLatLong)
            }

            override fun onMarkerDragStart(p0: Marker) {
                TODO("Not yet implemented")
            }
        })

        // googleMap = it
        //googleMap.isMyLocationEnabled = true
//        this.googleMap = googleMap
//        this.googleMap.uiSettings.isZoomControlsEnabled = true
//        this.googleMap.setOnMarkerClickListener(this)
        //setUpMap()

        // Add a marker in Sydney and move the camera
        /*val latlong = LatLng(22.0, 77.0)
        this.googleMap.addMarker(MarkerOptions().position(latlong).title("My location    `"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlong))
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 10f))*/


//        this.googleMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val india = LatLng(22.0, 77.0)
//        this.googleMap.addMarker(MarkerOptions().position(india).title("Marker in India"))
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(india))
    }

    private fun drawMarker(latLong: LatLng) {

        //placeMarkerOnMap(latLong)
        val markerOptions = MarkerOptions().position(latLong).snippet(getAddress(latLong.longitude, latLong.latitude)).draggable(true)
        markerOptions.title("$latLong")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLong))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 14f))
        // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong))
        currentMarker = googleMap.addMarker(markerOptions)
        currentMarker?.isDraggable = true
        currentMarker?.showInfoWindow()
    }

    private fun getAddress(longitude: Double, latitude: Double): String? {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude,1)
        //currentAddress = addresses[0].getAddressLine(0).toString()
        currentCity = addresses[0].locality.toString()
        lat = addresses[0].latitude.toString()
        long = addresses[0].longitude.toString()
        return addresses[0].getAddressLine(0).toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}