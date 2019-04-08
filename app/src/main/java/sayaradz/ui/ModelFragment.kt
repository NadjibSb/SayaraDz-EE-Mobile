package sayaradz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import sayaradz.authentification.R

class ModelFragment:Fragment() {

    companion object {
        fun getInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.model_fragment, container, false)
        var t = rootView.findViewById<TextView>(R.id.textView)
        val args = ModelFragmentArgs.fromBundle(arguments!!)
        Log.i("args",args.marqueId+" t")
        t.text = args.marqueId
        return rootView
    }
}