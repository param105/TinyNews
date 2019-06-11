package com.param.tinynews

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.param.tinynews.adapters.NewsAdapter
import kotlinx.android.synthetic.main.item_list.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.param.tinynews.model.NewsModelRecycler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.param.tinynews.model.Article
import com.param.tinynews.model.NewsList
import com.param.tinynews.util.ApiService
import com.param.tinynews.util.RetrofitClient
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.PersistableBundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.param.tinynews.util.PermissionUtil
import kotlinx.android.synthetic.main.nav_header_main.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BaseActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var recyclerView: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private val articleList: ArrayList<Article>? = ArrayList<Article>()

    companion object{
        private val PICK_PHOTO_FOR_AVATAR = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BaseActivity","OnCreate")

        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        setDrawerWithToggle()
        fetchNewsJSON()
    }



    override fun onStart() {
        super.onStart()
        Log.d("BaseActivity","OnStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("BaseActivity","OnReStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("BaseActivity","OnResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("BaseActivity","OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("BaseActivity","OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("BaseActivity","onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("BaseActivity","onSavedInstanceState")
    }



    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.d("BaseActivity","onConfigChanged")
    }

    /**
     * initialize the drawer with toggle facility
     */
    private fun setDrawerWithToggle() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.setDrawerIndicatorEnabled(true)
        toggle.syncState()

        //set profile image if user has selected image from device
        var navigationView = drawer?.findViewById<NavigationView>(R.id.design_navigation_view)
        var header = navigationView.getHeaderView(0)
        var profileImage = header?.findViewById<ImageView>(R.id.imageView_profile)
        profileImage?.setOnClickListener(onClickListener)

    }

    val onClickListener: View.OnClickListener = View.OnClickListener { it ->
        when (it?.id) {
            R.id.imageView_profile -> pickImageFromCameraOrGallary()
        }
    }

    /***
     * this is function to get image through camera or gallery
     */
    private fun pickImageFromCameraOrGallary(): Unit {
        /*if (! PermissionUtil(this).checkPermissionForCamera()) {
            requestPermissionForCamera()
        }

        if(!PermissionUtil(this).checkPermissionForExternalStorage()){
            requestPermissionForExternalStorage(EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_GALLERY)
        }
        else {*/
           openImageChooserDialog()
        //}
    }

    /**
     * Function to fetch JSON
     * Used Pcasso use fetch photo from json
     */
    private fun fetchNewsJSON() {

        val api: ApiService = RetrofitClient.getApiService()
        /**
         * Calling JSON
         */
        val call: Call<NewsList> = api.newsListJSON

        /**
         * Enqueue Callback will be call when get response...
         */

        call.enqueue(object : Callback<NewsList> {
            override fun onFailure(call: Call<NewsList>?, t: Throwable?) {
                //Toast.makeText(this, t!!.message,Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call<NewsList>?, response: Response<NewsList>?) {
                val newsListData: NewsList? = response!!.body()
                if (response.isSuccessful) {
                    articleList?.addAll(newsListData?.articles!!)
                    writeNewsRecycler()
                } else {
                    Toast.makeText(this@MainActivity, "Fetching issue", Toast.LENGTH_SHORT).show()


                }
            }


        })


    }


    private fun writeNewsRecycler() {

        val modelRecyclerArrayList = ArrayList<NewsModelRecycler.NewsDataModel>()
        val dataArray = articleList

        for (i in 0 until dataArray?.size!!) {

            val articleObj = articleList?.get(i)
            val articleModel = NewsModelRecycler.NewsDataModel(
                i.toString(),
                articleObj?.author,
                articleObj?.title,
                articleObj?.description,
                articleObj?.url,
                articleObj?.urlToImage,
                articleObj?.publishedAt,
                articleObj?.content
            )

            modelRecyclerArrayList.add(articleModel)
            NewsModelRecycler.addItem(articleModel)

        }

        newsAdapter = NewsAdapter(this, this, false, modelRecyclerArrayList)
        recyclerView?.setAdapter(newsAdapter)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL,
                false
            )
        )


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_CAMERA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //permission granted successfully
                openImageChooserDialog()

            } else {

                //permission denied

            }
        }
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
             val imageBitmap = data?.extras?.get("data") as Bitmap
             imageView_profile.setImageBitmap(imageBitmap)
         }
    }


}
