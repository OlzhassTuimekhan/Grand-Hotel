package kz.grand_hotel.ui.menu.ui.home.map

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import kz.grand_hotel.databinding.FragmentMapBinding
import android.graphics.Path
import android.graphics.RectF
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel
import kz.grand_hotel.ui.menu.ui.home.Hotel.HotelBottomSheetFragment
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var userLocation: LatLng? = null
    private var targetLocation: LatLng? = null

    companion object {
        private const val ARG_TARGET_LAT = "target_lat"
        private const val ARG_TARGET_LNG = "target_lng"

        fun newInstance(lat: Double, lng: Double): MapFragment =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_TARGET_LAT, lat)
                    putDouble(ARG_TARGET_LNG, lng)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_TARGET_LAT) && it.containsKey(ARG_TARGET_LNG)) {
                targetLocation = LatLng(
                    it.getDouble(ARG_TARGET_LAT),
                    it.getDouble(ARG_TARGET_LNG)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
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
        setupSearch()

        homeViewModel.hotels.observe(viewLifecycleOwner) { hotels ->
            googleMap?.clear()

            userLocation?.let { addUserMarker(it) }

            addHotelMarkers(hotels, selectedLocation = targetLocation)

            googleMap?.setOnMarkerClickListener { marker ->
                hotels.find { it.name == marker.title }?.let {
                    HotelBottomSheetFragment.newInstance(it)
                        .show(parentFragmentManager, null)
                }
                true
            }
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            googleMap!!.isMyLocationEnabled = true
            loadUserLocationAndMarkers()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        targetLocation?.let { loc ->
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f))
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadUserLocationAndMarkers() {
        fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
            loc?.let {
                userLocation = LatLng(it.latitude, it.longitude)
                addUserMarker(userLocation!!)

                if (targetLocation == null) {
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation!!, 15f))
                }
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterHotels(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filterHotels(newText)
                return true
            }
        })
    }

    private fun filterHotels(query: String) {
        googleMap?.clear()
        userLocation?.let { addUserMarker(it) }

        val filtered = homeViewModel.hotels.value
            ?.filter { it.name.contains(query, ignoreCase = true) }
            .orEmpty()

        addHotelMarkers(filtered)

        if (filtered.isNotEmpty()) {
            val target = filtered.first().locationLatLng
            googleMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(target, 15f),
                500,
                null
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

    private fun addHotelMarkers(hotels: List<Hotels>, selectedLocation: LatLng? = null) {
        hotels.forEach { hotel ->
            val isSelected = hotel.locationLatLng == selectedLocation
            val markerWidth = 200
            val markerHeight = 240

            val strokeWidth = 8f

            val radius = (markerWidth / 2f) - strokeWidth
            val centerX = markerWidth / 2f
            val centerY = markerWidth / 2f

            val bitmap = Bitmap.createBitmap(markerWidth, markerHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }.let { paint ->
                canvas.drawCircle(centerX, centerY, radius + strokeWidth, paint)
            }

            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(requireContext(), if(isSelected) R.color.red else R.color.blue)
                style = Paint.Style.STROKE
                this.strokeWidth = strokeWidth
            }.let { paint ->
                canvas.drawCircle(centerX, centerY, radius, paint)
            }

            val saveCount = canvas.save()
            val clipPath = Path().apply {
                addCircle(centerX, centerY, radius - strokeWidth/2f, Path.Direction.CCW)
            }
            canvas.clipPath(clipPath)
            ContextCompat.getDrawable(requireContext(), hotel.image)?.apply {
                setBounds(
                    (centerX - radius).toInt(),
                    (centerY - radius).toInt(),
                    (centerX + radius).toInt(),
                    (centerY + radius).toInt()
                )
                draw(canvas)
            }
            canvas.restoreToCount(saveCount)

            val pillWidth  = 120f
            val pillHeight =  50f
            val pillLeft = centerX - pillWidth/2f
            val pillTop  = centerY + radius - pillHeight/2f
            val pillRect = RectF(pillLeft, pillTop, pillLeft + pillWidth, pillTop + pillHeight)
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }.let { paint ->
                canvas.drawRoundRect(pillRect, pillHeight/2f, pillHeight/2f, paint)
            }

            val starDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)
            val starSize = pillHeight * 0.6f
            val starLeft = pillLeft + 8f
            val starTop  = pillTop + (pillHeight - starSize)/2f
            starDrawable?.setBounds(
                starLeft.toInt(),
                starTop.toInt(),
                (starLeft + starSize).toInt(),
                (starTop + starSize).toInt()
            )
            starDrawable?.draw(canvas)

            val text = hotel.rating.toString()
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.BLACK
                textSize = pillHeight * 0.5f
                typeface = ResourcesCompat.getFont(requireContext(), R.font.jost_semibold)
            }
            val textX = starLeft + starSize + 8f
            val textY = pillTop + pillHeight/2f - (textPaint.descent() + textPaint.ascent())/2f
            canvas.drawText(text, textX, textY, textPaint)

            val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
            googleMap?.addMarker(
                MarkerOptions()
                    .position(hotel.locationLatLng)
                    .title(hotel.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
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
