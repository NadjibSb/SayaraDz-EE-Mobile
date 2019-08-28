package sayaradz.ui.addAnnonce

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.CollectionUtils.listOf
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.add_annonce_fragment.*
import kotlinx.android.synthetic.main.activity_mes_annonces.TopToolbar
import sayaradz.authentification.R
import sayaradz.dataClasses.Marque
import sayaradz.dataClasses.Modele
import sayaradz.dataClasses.Version
import sayaradz.ui.fragment.adapter.ListAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat


val  marqueList : List<Marque>  = listOf(Marque("1","Renault"," ") , Marque("2","Peugeout",""), Marque("3","Ford",""), Marque("4","Mercedes",""),Marque("1","Renault"," ") , Marque("2","Peugeout",""), Marque("3","Ford",""), Marque("4","Mercedes",""))//For test
val colors = listOf<String>("white","blue","red")
val modelList: List<Modele>  = listOf(Modele("1","301","1", colors),Modele("2","308","2", colors),Modele("3","301","3", colors))//For test
val  versionList: List<Version>  = listOf(Version("1","v1","301",""),Version("2","v2","308",""),Version("3","v3","308",""))//For test
class AddAnnonceActivity : AppCompatActivity() {
    val TAKE_PICTURE = 1
    val SELECT_PICTURE = 2
    lateinit var rvMarque: RecyclerView
    lateinit var rvModel: RecyclerView
    lateinit var rvVersion : RecyclerView
    lateinit var btnConfirm : View
    lateinit var btnAddPhoto : View
    var currentPath : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_annonce_fragment)
        setSupportActionBar(TopToolbar)
        rvMarque = findViewById <View>(R.id.marqueListView) as RecyclerView
        rvModel=findViewById <View>(R.id.modelListView) as RecyclerView
        rvVersion=findViewById <View>(R.id.versionListView) as RecyclerView
        rvMarque.adapter = ListAdapter(marqueList, ListAdapter.ViewHolderType.MARQUE, this@AddAnnonceActivity, "token")
        rvModel.adapter = ListAdapter(modelList, ListAdapter.ViewHolderType.MODEL, this@AddAnnonceActivity, "token")
        rvVersion.adapter = ListAdapter(versionList, ListAdapter.ViewHolderType.VERSION, this@AddAnnonceActivity, "token")
        setUpRecycleView(rvMarque)
        setUpRecycleView(rvModel)
        setUpRecycleView(rvVersion)
        btnConfirm = findViewById(R.id.btn_confirm)
        btnAddPhoto = findViewById(R.id.btn_add_pic)
      /*  btnConfirm.setOnClickListener {

          //  startActivity(Intent(this@AddAnnonceActivity, MesAnnoncesActivity::class.java))

        }*/


        //Photo Edition

        btnAddPhoto.setOnClickListener{
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.pop_up_photo_view,null)


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
                popupWindow.setFocusable(true)
                popupWindow.update()
            }
            // popupWindow.setBackgroundDrawable(BitmapDrawable())

            // Get the widgets reference from custom view
            var btnGallery = view.findViewById<ImageButton>(R.id.btn_gallery)
            var btnCamera = view.findViewById<ImageButton>(R.id.btn_camera)

            btnGallery.setOnClickListener {
                Toast.makeText(this,"GALLERY", Toast.LENGTH_SHORT).show()
                DispatchGalleryIntent ()

            }
            btnCamera.setOnClickListener {
                Toast.makeText(this,"CAMERA", Toast.LENGTH_SHORT).show()
                DispatchCameraIntent ()


            }


            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(root_layout)
            popupWindow.showAtLocation(
                    root_layout, // Location to display popup window
                    Gravity.CENTER_HORIZONTAL, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
            )
        }










    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top_toolbar, menu)
        return true
    }

    private fun setUpRecycleView(rv : RecyclerView) {
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.itemAnimator = SlideInUpAnimator()
        rv.setHasFixedSize(true)
    }
    private fun DispatchGalleryIntent()
    {
        val intent = Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"selectionner une image") , SELECT_PICTURE)
    }
    private fun DispatchCameraIntent() {
        val intent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null)
        {
            var photoFile: File? = null
            try
            {
                photoFile = createImage()

            }catch (e : IOException)
            {
                e.printStackTrace()
            }
            if ( photoFile != null )
            {
                var photoUri = FileProvider.getUriForFile(this ,"sayaraDz.fileProvider"
                        ,photoFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                startActivityForResult(intent,TAKE_PICTURE)
            }

        }
    }

    fun createImage () : File
    {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss")
        val imageName = "JPEG_"+timeStamp+"_"
        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName,".jpg",storageDir)
        currentPath = image.absolutePath
        return image
    }


}
