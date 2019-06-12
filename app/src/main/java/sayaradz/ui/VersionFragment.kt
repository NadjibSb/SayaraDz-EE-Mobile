package sayaradz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import sayaradz.authentification.R

class VersionFragment: Fragment() {

    lateinit var args: VersionFragmentArgs
    companion object {
        fun getInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.search_fragment, container, false)
        args = VersionFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context,"Marque id: ${args.marqueId}, Model id: ${args.modelId}", Toast.LENGTH_SHORT).show()

        return rootView
    }

}