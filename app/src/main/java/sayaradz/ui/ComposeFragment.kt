package sayaradz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sayaradz.authentification.R


class ComposeFragment: Fragment() {
    companion object {
        fun getInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.compose_fragment, container, false)

        return rootView
    }
}