package sayaradz.services

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import android.widget.NumberPicker
import sayaradz.authentification.R


class SearchFragment : Fragment() , AdapterView.OnItemSelectedListener {

    val TAG = "TAG-SearchFragment"

    companion object {
        fun getInstance() =SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.search_fragment, container, false)


        var spinnerType = rootView.findViewById<View>(R.id.spinner_type) as Spinner
        var spinnerYear = rootView.findViewById<View>(R.id.spinner_yearStart) as Spinner
        var spinnerYearEnd = rootView.findViewById<View>(R.id.spinner_yearEnd) as Spinner
        var spinnerPriceMin = rootView.findViewById<View>(R.id.spinner_price_min) as Spinner
        var spinnerPriceMax = rootView.findViewById<View>(R.id.spinner_price_max) as Spinner

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


        //////Prices /////
        val pricesList: MutableList<Int> = mutableListOf(70)
        //for ( i in 70..100)  pricesList.add()
        var i = 100
        while (  i  < 500)
        {
            pricesList.add(i)
            i+=50
        }

        val adapterPrice = ArrayAdapter<Int>(activity.applicationContext,android.R.layout.simple_spinner_item,pricesList)
        // Set layout to use when the list of choices appear
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerPriceMin!!.setAdapter(adapterPrice)
        spinnerPriceMax!!.setAdapter(adapterPrice)
        spinnerPriceMin.setPrompt("Prix Min")
        spinnerPriceMax.setPrompt("Prix Max")

        spinnerPriceMin.onItemSelectedListener = this
        spinnerPriceMax.onItemSelectedListener = this



       /* val tvMin = rootView.findViewById(R.id.tv_min) as TextView
        val tvMax = rootView.findViewById(R.id.tv_max) as TextView
        val npMin = rootView.findViewById(R.id.np_price_min) as NumberPicker
        val npMax = rootView.findViewById(R.id.np_price_max) as NumberPicker*/

        //Set TextView text color
        //tv.setTextColor(Color.parseColor("#ffd32b3b"))

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
       /* npMin.minValue=70
        npMax.minValue =70
        npMin.maxValue=100
        npMax.maxValue = 100


        npMin.wrapSelectorWheel = true
        npMax.wrapSelectorWheel = true
*/

//        npMin.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
//            //Display the newly selected number from picker
//           // tv.setText("Selected Number : $newVal")
//            //*Step Size */
//            npMin.value = if (newVal < oldVal) oldVal - 5 else oldVal + 5
//            //**//
//            Log.i(TAG,newVal.toString())
//        })
//
//        npMax.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
//            //Display the newly selected number from picker
//            // tv.setText("Selected Number : $newVal")
//            //*Step Siz */
//            npMax.value = if (newVal < oldVal) oldVal - 5 else oldVal + 5
//            //**//
//
//            Log.i(TAG,newVal.toString())
//        })


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







}