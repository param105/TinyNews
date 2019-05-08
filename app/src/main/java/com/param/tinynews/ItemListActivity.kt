package com.param.tinynews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.param.tinynews.adapters.NewsAdapter
import kotlinx.android.synthetic.main.activity_item_list.*
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


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var recyclerView: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null
    private val articleList: ArrayList<Article>? = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
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

        fetchNewsJSON()
    }

    /**
     * Function to fetch JSON
     * Used Pcasso use fetch photo from json
     */
    private fun fetchNewsJSON() {

        val api:ApiService = RetrofitClient.getApiService()
        /**
         * Calling JSON
         */
      val call:Call <NewsList>  = api.newsListJSON

        /**
         * Enqueue Callback will be call when get response...
         */

        call.enqueue(object : Callback<NewsList> {
            override fun onFailure(call: Call<NewsList>?, t: Throwable?) {
                //Toast.makeText(this, t!!.message,Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call<NewsList>?, response: Response<NewsList>?) {
                val newsListData : NewsList? = response!!.body()
                if(response.isSuccessful) {
                    articleList?.addAll(newsListData?.articles!!)
                    writeNewsRecycler()
                }else{
                    Toast.makeText(this@ItemListActivity, "Fetching issue",Toast.LENGTH_SHORT).show()


                }
            }


        })


    }

   private fun writeNewsRecycler(){

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
                       articleObj?.content)

                   modelRecyclerArrayList.add(articleModel)
                   NewsModelRecycler.addItem(articleModel)

               }

               newsAdapter = NewsAdapter(this,this,false, modelRecyclerArrayList)
               recyclerView?.setAdapter(newsAdapter)
               recyclerView?.setLayoutManager(
                   LinearLayoutManager(
                       applicationContext,
                       RecyclerView.VERTICAL,
                       false
                   )
               )


        }


    }
