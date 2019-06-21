package sayaradz.ui.fragment.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderLayout
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sayaradz.api.ServiceBuilder
import sayaradz.api.ServiceProvider
import sayaradz.authentification.R
import sayaradz.dataClasses.Car
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.ui.fragment.adapter.CarAdapter
import java.util.*


class SearchFragment : Fragment() {
    var token: String? = ""
    val TAG = "TAG-SearchFragment"
    var brandList = mutableListOf<String>()//ArrayList<String>()
    var modelsList = ArrayList<String>()
    var carsList = ArrayList<Car>()
    var carsList1 = ArrayList<Car>()
    var typeSelected: String? = " "
    var marqueSelected: String? = null
    var modelSelected: String? = null
    var priceMin: String? = null
    var priceMax: String? = null
    var kmMin: String? = null
    var kmMax: String? = null
    var yearMin: String? = null
    var yearMax: String? = null
    lateinit var popup: PopupWindow
    val marqueeList = mutableListOf("Marque", "Ford", "Infiniti", "Renault") //For test
    val modellsList = mutableListOf("Model", "208", "301") //For test
    lateinit var recyclerView: RecyclerView
    val service = ServiceBuilder.buildService(ServiceProvider::class.java)
    lateinit var adapter: CarAdapter


    companion object {
        fun getInstance() = SearchFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.search_fragment, container, false)


        var spinnerType = rootView.findViewById<View>(R.id.spinner_type) as Spinner
        var spinnerMarque = rootView.findViewById<View>(R.id.spinner_marque) as Spinner
        var rootLayout = rootView.findViewById<View>(R.id.root_layout) as RelativeLayout
        var btnYear = rootView.findViewById<View>(R.id.btn_year) as Button
        var btnPrice = rootView.findViewById<View>(R.id.btn_price) as Button
        var btnMore = rootView.findViewById<View>(R.id.btn_more_filters) as Button
        recyclerView = rootView.findViewById(R.id.carListView) as RecyclerView
        setUpRecycleView(carsList)
        getResult(token!!)

        //Havinge tge Token to  ACCESS
        token = ""// this.arguments!!.getString("TOKEN")
        Log.i(TAG, "TOKEN RECEIVED: $token")

        // REMPLIR LIST WITH QUERY
        brandList = getMarquesList(rootView, token!!)
        // REMPLIR LIST WITH QUERY
        getModelsList(rootView, token!!)


        //PopUpYear
        btnYear.setOnClickListener {

            val inflater: LayoutInflater = getActivity()!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.year_pop_up_layout, null)

            popUpFun(view, rootLayout)

            // Get the widgets reference from custom view
            var spinnerYear = view.findViewById<View>(R.id.spinner_yearStart) as Spinner
            var spinnerYearEnd = view.findViewById<View>(R.id.spinner_yearEnd) as Spinner


            //YearSpinner 10 last years
            val current_year = Calendar.getInstance().get(Calendar.YEAR).toString()
            val yearsList: MutableList<String> = mutableListOf(current_year)
            for (i in 1..10) yearsList.add((current_year.toInt() - i).toString())
            yearsList.add(0, "Choisir..")

            val adapter = ArrayAdapter<String>(activity?.applicationContext, R.layout.spinner_item, yearsList)
            // Set layout to use when the list of choices appear
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerYear!!.setAdapter(adapter)
            spinnerYearEnd!!.setAdapter(adapter)


            spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // An item was selected. You can retrieve the selected item using
                    if (parent.getItemAtPosition(pos).toString() != "Choisir..")
                        yearMin = parent.getItemAtPosition(pos).toString()
                    else yearMin = null
                    getResult(token!!)
                    Toast.makeText(activity!!.applicationContext, yearMin, Toast.LENGTH_SHORT).show()
                }
            }
            spinnerYearEnd.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    if (parent.getItemAtPosition(pos).toString() != "Choisir..")
                        yearMax = parent.getItemAtPosition(pos).toString()
                    else yearMax = null
                    getResult(token!!)
                    Toast.makeText(activity?.applicationContext, yearMax, Toast.LENGTH_SHORT).show()
                }
            }
        }
        //PopUpPrice
        btnPrice.setOnClickListener {

            val inflater: LayoutInflater = getActivity()?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.price_pop_up_layout, null)

            popup = popUpFun(view, rootLayout)

            // Get the widgets reference from custom view
            var priceMinEt = view.findViewById<EditText>(R.id.et_min)
            var priceMaxEt = view.findViewById<EditText>(R.id.et_max)
            var btnApply = view.findViewById<Button>(R.id.btn_apply)
            btnApply.setOnClickListener {

                priceMin = priceMinEt.text.toString()
                priceMax = priceMaxEt.text.toString()
                getResult(token!!)
                Toast.makeText(activity!!.applicationContext, priceMin + " " + priceMax, Toast.LENGTH_SHORT).show()
                popup.dismiss()

            }
        }

        //PopUpMoreFilter
        btnMore.setOnClickListener {

            val inflater: LayoutInflater = getActivity()?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.more_filter_pop_up_layout, null)

            // Initialize a new instance of popup window

            popup = popUpFun(view, rootLayout)

            // Get the widgets reference from custom view
            var kmMinEt = view.findViewById<View>(R.id.et_km_min) as EditText
            var kmMaxEt = view.findViewById<View>(R.id.et_km_max) as EditText
            var btnApply = view.findViewById<Button>(R.id.btn_apply)
            btnApply.setOnClickListener {

                kmMin = kmMinEt.text.toString()
                kmMax = kmMaxEt.text.toString()
                getResult(token!!)
                Toast.makeText(activity?.applicationContext, "KmMin" + kmMin + "  KmMax" + kmMax, Toast.LENGTH_SHORT).show()
                Toast.makeText(activity?.applicationContext, modelSelected, Toast.LENGTH_SHORT).show()
                popup.dismiss()


            }
            var spinnerModel = view.findViewById<View>(R.id.spinner_modele) as Spinner


            val adapter = ArrayAdapter<String>(activity?.applicationContext, R.layout.spinner_item, modellsList)
            // Set layout to use when the list of choices appear
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerModel.adapter = adapter
            spinnerModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // An item was selected. You can retrieve the selected item using
                    if (parent?.getItemAtPosition(pos).toString() != "Model")
                        modelSelected = parent.getItemAtPosition(pos).toString()
                    else modelSelected = null

                }
            }

        }


        // TypeSpinner
        ArrayAdapter.createFromResource(
                activity!!.applicationContext,
                R.array.types_array,
                R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerType.adapter = adapter
        }
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                Log.i("TYPE", "IN")
                if (parent.getItemAtPosition(pos).toString().toLowerCase() != "type")
                    typeSelected = parent.getItemAtPosition(pos).toString().toLowerCase()
                else typeSelected = " "
                getResult(token!!)
                Toast.makeText(activity!!.applicationContext, typeSelected, Toast.LENGTH_SHORT).show()
            }
        }

        //MarqueSpinner//
        val adapter = ArrayAdapter<String>(activity!!.applicationContext, R.layout.spinner_item, marqueeList)
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerMarque.setAdapter(adapter)
        spinnerMarque.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.i("MARQUE", "OUT")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                Log.i("MARQUE", "IN")
                if (parent!!.getItemAtPosition(pos).toString() != "Marque")
                    marqueSelected = parent!!.getItemAtPosition(pos).toString()
                else marqueSelected = null
                getResult(token!!)
                Toast.makeText(activity!!.applicationContext, marqueSelected, Toast.LENGTH_SHORT).show()
            }
        }


        ///

        return rootView
    }


    private fun getModelsList(rootView: View?, idToken: String) {


        Log.i(TAG, "DisplayModelList")

        val call = service.getModels(idToken) // The request included the token
        var modelRespond: List<Modele>? = null

        call.enqueue(object : Callback<List<Modele>> {
            override fun onResponse(call: Call<List<Modele>>, response: Response<List<Modele>>) {
                Log.i(TAG, "DisplayModelList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                modelRespond = response.body()  // Getting the marques
                if (modelRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL MODELS FROM OUR SERVER:")
                    for (m in modelRespond!!) {
                        var content = "Name: " + m.name + "\n"

                        Log.i(TAG, "\n=> MODEL:  $content")
                        modelsList!!.add(m.name)


                    }
                }
            }

            override fun onFailure(call: Call<List<Modele>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
    }


    private fun getMarquesList(rootView: View, idToken: String): MutableList<String> {
        var marquesList = mutableListOf<String>()
        Log.i(TAG, "DisplayMarqueList")
        val call = service.getMarques(idToken) // The request included the token

        var marqueRespond: List<Marque>? = null

        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i(TAG, "DisplayMarqueList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }
                marqueRespond = response.body()  // Getting the marques
                if (marqueRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marqueRespond!!) {
                        var content = "Name: " + m.name + "\n"

                        Log.i(TAG, "\n=> CONTENT:  $content")
                        marquesList!!.add(m.name)


                    }
                }

            }

            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
        return marquesList
    }

    private fun getResult(idToken: String) {

        Log.i(TAG, "DisplayCarList")

        val call = service.getResult(idToken, typeSelected, yearMin, yearMax, kmMin, kmMax, marqueSelected, modelSelected, priceMin, priceMax)
        if (typeSelected != null) Log.i("TYPEIN", typeSelected)
        var carRespond: List<Car>? = null

        call.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                Log.i(TAG, "DisplayCarList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                carRespond = response.body()  // Getting the cars
                if (carRespond != null) {
                    carsList1.clear()
                    Log.i(TAG, "REPONSES: HERE is ALL THE CARS FROM OUR SERVER:")
                    for (m in carRespond!!) {
                        var content = "Name: " + m.title + "\n"

                        Log.i(TAG, "\n=> CONTENT:  $content")
                        carsList1.add(m)


                    }
                    updateList()

                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }
        })
    }

    fun popUpFun(view: View, rootLayout: RelativeLayout): PopupWindow {

        val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut
            popupWindow.isFocusable = true
            //popupWindow.setFocusable(true)
            popupWindow.update()
        }

        TransitionManager.beginDelayedTransition(rootLayout)
        popupWindow.showAtLocation(
                rootLayout, // Location to display popup window
                Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
        )

        return popupWindow
    }

    private fun setUpRecycleView(list: ArrayList<Car>) {
        adapter = CarAdapter(list, this@SearchFragment.context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.itemAnimator = SlideInUpAnimator()
        recyclerView.setHasFixedSize(true)
    }

    private fun updateList() {
        carsList.clear()
        carsList.addAll(carsList1)
        adapter.notifyDataSetChanged()

    }


}