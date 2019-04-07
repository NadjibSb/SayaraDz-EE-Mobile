package sayaradz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sayaradz.authentification.R

class DefaultFragment: Fragment() {
    companion object {
        fun getInstance() = DefaultFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.default_fragment, container, false)

        return rootView
    }
}