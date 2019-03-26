package sayaradz.services

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.util.*
import android.widget.NumberPicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sayaradz.authentification.MarqueFragment
import sayaradz.authentification.R


class SearchFragment : Fragment() , AdapterView.OnItemSelectedListener  {
    var token: String? =""
    val TAG = "TAG-SearchFragment"
    var marquesList=ArrayList<String>()
    var modelsList=ArrayList<String>()
    companion object {
        val url = "https://sayaradz-ee-backend.herokuapp.com/"
        //val url = "http://a7fde9a6.ngrok.io"
        fun getInstance() =SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.search_fragment, container, false)


        var spinnerType = rootView.findViewById<View>(R.id.spinner_type) as Spinner
        var spinnerMarque = rootView.findViewById<View>(R.id.spinner_marque) as Spinner
        var rootLayout = rootView.findViewById<View>(R.id.root_layout) as RelativeLayout
        var btnYear =  rootView.findViewById<View>(R.id.btn_year) as Button
        var btnPrice =rootView.findViewById<View>(R.id.btn_price) as Button
        var btnMoreFilters =rootView.findViewById<View>(R.id.btn_more_filters) as Button


        // TypeSpinner
        ArrayAdapter.createFromResource(
                activity.applicationContext,
                R.array.types_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerType.adapter = adapter
            spinnerType.setPrompt("Type de véhicule")
        }
        //Havinge tge Token to  ACCESS
        token = this.arguments.getString("TOKEN")
        Log.i(TAG, "TOKEN RECEIVED: $token")

        // REMPLIR LIST WITH QUERY
        getLists(rootView, token!!)

        //MarqueSpinner//
        val marqueeList: MutableList<String> = mutableListOf("Marque1","Marque2","Marque3") //For test

        val adapter = ArrayAdapter<String>(activity.applicationContext,android.R.layout.simple_spinner_item,marquesList)
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerMarque.setAdapter(adapter)




        //PopUpYear
        btnYear.setOnClickListener {

            val inflater:LayoutInflater = getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.year_pop_up_layout,null)

            // Initialize a new instance of popup window
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
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }
            popupWindow.setFocusable(true)
            popupWindow.update()
            // Get the widgets reference from custom view
            var spinnerYear = view.findViewById<View>(R.id.spinner_yearStart) as Spinner
            var spinnerYearEnd = view.findViewById<View>(R.id.spinner_yearEnd) as Spinner


            //YearSpinner 10 last years
            val current_year = Calendar.getInstance().get(Calendar.YEAR)
            val yearsList: MutableList<Int> = mutableListOf(current_year)
            for ( i in 1..10)  yearsList.add(current_year-i)

            val adapter = ArrayAdapter<Int>(activity.applicationContext,android.R.layout.simple_spinner_item,yearsList)
            // Set layout to use when the list of choices appear
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerYear!!.setAdapter(adapter)
            spinnerYearEnd!!.setAdapter(adapter)
            spinnerYear.setPrompt("Année Début")
            spinnerYearEnd.setPrompt("Année Fin")

            spinnerYear.onItemSelectedListener = this
            spinnerYearEnd.onItemSelectedListener = this

            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(rootLayout)
            popupWindow.showAtLocation(
                    rootLayout, // Location to display popup window
                    Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }
        //PopUpPrice
        btnPrice.setOnClickListener {

            val inflater:LayoutInflater = getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.price_pop_up_layout,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )
            popupWindow.setFocusable(true)
            popupWindow.update()
            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }

            /*Dismiss while checking out
            popupWindow.setBackgroundDrawable(BitmapDrawable())
            popupWindow.setOutsideTouchable(true); */



            // Get the widgets reference from custom view
            var priceMin = view.findViewById<View>(R.id.et_price_min) as EditText
            var priceMax = view.findViewById<View>(R.id.et_price_max) as EditText

            Toast.makeText(activity.applicationContext,priceMax.text,Toast.LENGTH_SHORT).show()
            Toast.makeText(activity.applicationContext,priceMin.text,Toast.LENGTH_SHORT).show()


            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(rootLayout)
            popupWindow.showAtLocation(
                    rootLayout, // Location to display popup window
                    Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }

      //PopUpMoreFilters
        btnMoreFilters.setOnClickListener {

            val inflater:LayoutInflater = getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.more_filters_pop_up_layout,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )
            popupWindow.setFocusable(true)
            popupWindow.update()
            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }
            /*Dismiss while checking out
            popupWindow.setBackgroundDrawable(BitmapDrawable())
            popupWindow.setOutsideTouchable(true); */

            // Get the widgets reference from custom view
            var kilometrage = view.findViewById<View>(R.id.et_km) as EditText
            var spinnerModel = view.findViewById<View>(R.id.spinner_models) as Spinner
            val adapter = ArrayAdapter<String>(activity.applicationContext,android.R.layout.simple_spinner_item,modelsList)
            // Set layout to use when the list of choices appear
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinnerModel.setAdapter(adapter)

            Toast.makeText(activity.applicationContext,kilometrage.text,Toast.LENGTH_SHORT).show()

            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(rootLayout)
            popupWindow.showAtLocation(
                    rootLayout, // Location to display popup window
                    Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }





        return rootView


    }


    //Spinner
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        var itemSelected = parent.getItemAtPosition(pos).toString()
        Toast.makeText(activity.applicationContext,itemSelected,Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
    private fun getLists(rootView: View, idToken: String) {

        val retrofit = Retrofit.Builder()
                .baseUrl(MarqueFragment.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        Log.i(TAG, "DisplayLists")

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getMarques(idToken) // The request included the token
        val callModel =  jsonPlaceHolderApi.getModels(idToken)
        var marqueRespond: List<Marque>? = null
        var modelRespond: List<Model>? = null

        //Call marque
        call.enqueue(object : Callback<List<Marque>> {
            override fun onResponse(call: Call<List<Marque>>, response: Response<List<Marque>>) {
                Log.i(TAG, "DisplayMarqueList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:"+ response.code().toString())
                    return
                }

                marqueRespond = response.body()  // Getting the marques
                if (marqueRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in marqueRespond!!) {
                        var content = "Name: " + m.NomMarque + "\n"

                        Log.i(TAG, "\n=> CONTENT:  $content")
                        marquesList!!.add(m.NomMarque)


                    }
                }
            }

            override fun onFailure(call: Call<List<Marque>>, t: Throwable) {
                Log.i(TAG, "error CODE:"+t.message)
            }
        })

        //Call Model
        callModel.enqueue(object : Callback<List<Model>> {
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                Log.i(TAG, "DisplayModelList: call enqueue")

                if (!response.isSuccessful()) {
                    Log.i(TAG, "CODE:"+ response.code().toString())
                    return
                }

                modelRespond = response.body()  // Getting the marques
                if (modelRespond != null) {
                    Log.i(TAG, "REPONSES: HERE is ALL THE BRANDS FROM OUR SERVER:")
                    for (m in modelRespond!!) {
                        var content = "Name: " + m.NomModel + "\n"

                        Log.i(TAG, "\n=> CONTENT:  $content")
                        modelsList!!.add(m.NomModel)


                    }
                }
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Log.i(TAG, "error CODE:"+t.message)
            }
        })


    }






}