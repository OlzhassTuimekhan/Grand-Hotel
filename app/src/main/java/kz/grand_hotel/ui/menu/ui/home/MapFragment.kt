package kz.grand_hotel.ui.menu.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kz.grand_hotel.R
import android.graphics.Rect
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var homeViewModel: HomeViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap?.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)

                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))

                    addUserMarker(userLocation)

                    homeViewModel.hotels.observe(viewLifecycleOwner) { hotels ->
                        addHotelMarkers(hotels)
                        googleMap?.setOnMarkerClickListener { marker ->
                            val hotelName = marker.title
                            val hotelLocation = marker.position

                            // Find the matching hotel from your list (you can use a unique identifier, e.g., hotel name)
                            val hotel = hotels.find { it.name == hotelName }

                            // If the hotel is found, show the bottom sheet
                            hotel?.let {
                                val bottomSheetFragment = HotelBottomSheetFragment(it)
                                bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                            }

                            true
                        }
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun addUserMarker(location: LatLng) {
        val height = 80
        val width = 80
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint.color = ContextCompat.getColor(requireContext(), R.color.blue)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), circlePaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = Color.WHITE
        textPaint.textSize = 16f
        textPaint.typeface = ResourcesCompat.getFont(requireContext(), R.font.jost_semibold)
        val text = "You"
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val x = (width - bounds.width()) / 2f
        val y = (height / 2 + bounds.height() / 2).toFloat()
        canvas.drawText(text, x, y, textPaint)

        val icon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)

        googleMap?.addMarker(
            MarkerOptions()
                .position(location)
                .title("Your Location")
                .icon(icon)
        )
    }

    private fun addHotelMarkers(hotels: List<HotelsInMap>) {
        hotels.forEach { hotel ->
            val height = 160
            val width = 160
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // Draw white circle background
            val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            backgroundPaint.color = Color.BLUE
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), backgroundPaint)

            try {
                val hotelDrawable = ContextCompat.getDrawable(requireContext(), hotel.image)
                if (hotelDrawable != null) {
                    val imageSize = 80f
                    val imageX = (width - imageSize) / 2f
                    val imageY = (height - 90f) / 2f // Adjust vertical positioning
                    drawCircularImage(canvas, hotelDrawable, imageX, imageY, imageSize)
                } else {
                    Log.e("MapFragment", "Drawable resource not found: ${hotel.image}")
                }
            } catch (e: Exception) {
                Log.e("MapFragment", "Error loading drawable: ${e.message}")
            }

            // Draw yellow circle for rating
            val ratingCircleRadius = 28f
            val ratingCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            ratingCirclePaint.color = ContextCompat.getColor(requireContext(), R.color.yellow)
            canvas.drawCircle((width / 2).toFloat(), height - ratingCircleRadius - 10f, ratingCircleRadius, ratingCirclePaint)

            // Draw rating text
            val ratingTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            ratingTextPaint.color = Color.BLACK
            ratingTextPaint.textSize = 20f
            ratingTextPaint.typeface = ResourcesCompat.getFont(requireContext(), R.font.jost_semibold)
            val ratingText = hotel.rating.toString()
            val ratingBounds = Rect()
            ratingTextPaint.getTextBounds(ratingText, 0, ratingText.length, ratingBounds)
            val ratingX = (width / 2 - ratingBounds.width() / 2).toFloat()
            val ratingY = height - ratingCircleRadius - 10f + (ratingBounds.height() / 2).toFloat()
            canvas.drawText(ratingText, ratingX, ratingY, ratingTextPaint)

            val icon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)

            googleMap?.addMarker(
                MarkerOptions()
                    .position(hotel.location)
                    .title(hotel.name)
                    .icon(icon)
            )
        }
    }

    private fun drawCircularImage(canvas: Canvas, drawable: Drawable, x: Float, y: Float, size: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val circleCenter = x + size / 2

        val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        maskPaint.color = Color.BLACK

        val bitmap = Bitmap.createBitmap(size.toInt(), size.toInt(), Bitmap.Config.ARGB_8888)
        val tempCanvas = Canvas(bitmap)
        tempCanvas.drawCircle(size / 2, size / 2, size / 2, maskPaint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        drawable.setBounds(0, 0, size.toInt(), size.toInt())
        drawable.draw(tempCanvas)

        canvas.drawBitmap(bitmap, x, y, null)
        paint.xfermode = null
    }


}
