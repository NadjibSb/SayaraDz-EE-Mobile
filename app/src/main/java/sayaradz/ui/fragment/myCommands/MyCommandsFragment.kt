package sayaradz.ui.fragment.myCommands

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sayaradz.authentification.R

class MyCommandsFragment : Fragment() {

    companion object {
        fun newInstance() = MyCommandsFragment()
    }

    private lateinit var viewModel: MyCommandsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_commands_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyCommandsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
