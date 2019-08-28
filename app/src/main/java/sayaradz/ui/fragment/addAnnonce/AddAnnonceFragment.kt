package sayaradz.ui.fragment.addAnnonce

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alespero.expandablecardview.ExpandableCardView
import com.google.android.gms.common.util.CollectionUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.add_annonce_fragment.*
import sayaradz.authentification.R
import sayaradz.authentification.databinding.AddAnnonceFragmentBinding
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.adapter.ListAdapter
import sayaradz.ui.fragment.marque.MarqueViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

class AddAnnonceFragment  : Fragment () {
    companion object {
        var active = false
        var marqueId=""
        var modeleId =""
        var versionId=""
        lateinit var lifecycleRegistry : LifecycleRegistry
        lateinit var rvModel: RecyclerView
        lateinit var rvVersion: RecyclerView
        lateinit var btnAddPhoto: Button
        lateinit var contextAdd  : Context
        lateinit var ecvMarque :  ExpandableCardView
        lateinit var ecvModel :  ExpandableCardView
        lateinit var ecvVersion :  ExpandableCardView
        lateinit var ecvOthers :  ExpandableCardView
        var token = "token"

        fun setUpRecycleView(rv: RecyclerView ,c  : Context) {
            rv.layoutManager = GridLayoutManager(c,3)
            rv.itemAnimator = SlideInUpAnimator()
            rv.setHasFixedSize(true)
        }
       fun fullContext( c: Context) {

           contextAdd=c
       }
    }

        /***Static Data for test ***/
    //val marqueList: List<Marque> = CollectionUtils.listOf(Marque("1", "Renault", " "), Marque("2", "Peugeout", ""), Marque("3", "Ford", ""), Marque("4", "Mercedes", ""), Marque("1", "Renault", " "), Marque("2", "Peugeout", ""), Marque("3", "Ford", ""), Marque("4", "Mercedes", ""))//For test
//    val colors = CollectionUtils.listOf<String>("white", "blue", "red")
  //  val modelList: List<Modele> = CollectionUtils.listOf(Modele("1", "301", "1", colors), Modele("2", "308", "2", colors), Modele("3", "301", "3", colors))//For test
   // val versionList: List<Version> = CollectionUtils.listOf(Version("1", "v1", "301", ""), Version("2", "v2", "308", ""), Version("3", "v3", "308", ""))//For test
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2
    lateinit var rvMarque: RecyclerView
    lateinit var btnConfirm: FloatingActionButton

    var currentPath: String? = null

    val TAG = "TAG-AddAnnonceFragment"
    lateinit var binding: AddAnnonceFragmentBinding

    private lateinit var addAnnonceViewModel: AddAnnonceViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_annonce_fragment, container, false)
        addAnnonceViewModel = ViewModelProviders.of(this).get(AddAnnonceViewModel::class.java)

        //*****//
        fullContext(activity!!.applicationContext)
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
        //////****////
        ecvMarque =binding.root.findViewById(R.id.ecv_marque)
        ecvModel=binding.root.findViewById(R.id.ecv_model)
        ecvVersion=binding.root.findViewById(R.id.ecv_version)
        ecvOthers =binding.root.findViewById(R.id.ecv_others)
        rvMarque = binding.root.findViewById(R.id.marqueListView)
        rvModel = binding.root.findViewById(R.id.modelListView)
        rvVersion = binding.root.findViewById(R.id.versionListView)
        addAnnonceViewModel.marques.observe(this, Observer { marques ->
            rvMarque.adapter = ListAdapter(marques, ListAdapter.ViewHolderType.MARQUE, this@AddAnnonceFragment.context!!, "token")
        })
        setUpRecycleView(rvMarque)

        /****/

        ecvModel.setOnClickListener {
            if (ecvModel.isExpanded) ecvModel.collapse()
            else ecvModel.expand()
        }
        ecvVersion.setOnClickListener {
            if (ecvVersion.isExpanded) ecvVersion.collapse()
            else ecvVersion.expand()
        }

        /****/

        btnConfirm = binding.root.findViewById(R.id.btn_confirm)
        btnAddPhoto = binding.root.findViewById(R.id.btn_add_pic)



        val action = AddAnnonceFragmentDirections.actionAddAnnonceToMyAnnonceFragment()
        btnConfirm.setOnClickListener {
            v: View ->

            // Verify all  else  return and message

                v.findNavController().navigate(action)


        }





        //Photo Edition


        btnAddPhoto.setOnClickListener {
            val inflater: LayoutInflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.pop_up_photo_view, null)


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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut
                popupWindow.setFocusable(true)
                popupWindow.update()
            }


            // Get the widgets reference from custom view
            var btnGallery = view.findViewById<ImageButton>(R.id.btn_gallery)
            var btnCamera = view.findViewById<ImageButton>(R.id.btn_camera)

            btnGallery.setOnClickListener {
                Toast.makeText(this@AddAnnonceFragment.context!!, "GALLERY", Toast.LENGTH_SHORT).show()
                DispatchGalleryIntent()

            }
            btnCamera.setOnClickListener {
                Toast.makeText(this@AddAnnonceFragment.context!!, "CAMERA", Toast.LENGTH_SHORT).show()
                DispatchCameraIntent()


            }


            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(binding.rootLayout)
            popupWindow.showAtLocation(
                    binding.rootLayout, // Location to display popup window
                    Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }



        return binding.root
    }


    private fun setUpRecycleView(rv: RecyclerView) {
        rv.layoutManager = GridLayoutManager(this@AddAnnonceFragment.context!!, 3)
        rv.itemAnimator = SlideInUpAnimator()
        rv.setHasFixedSize(true)
    }

    private fun DispatchGalleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "selectionner une image"), SELECT_PICTURE)
    }

    private fun DispatchCameraIntent() {

      var context : Context = activity!!.applicationContext
        var packageManager :PackageManager  = context.getPackageManager()
        if( !packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT).show()
        return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImage()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
               var photoUri = FileProvider.getUriForFile(this@AddAnnonceFragment.context!!, "sayaraDz.fileProvider" , photoFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, TAKE_PICTURE)
                Log.i(TAG, photoUri.toString())
            }

        }
        else {

            Log.i(TAG,"ERROR NULL URI")
        }
    }

    fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss")
        val imageName = "JPEG_" + timeStamp + "_"
        var storageDir = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
        active=true

    }

    override fun onStop() {
        super.onStop()
        active = false
    }
}