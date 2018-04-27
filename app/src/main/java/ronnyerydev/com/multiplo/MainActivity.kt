package ronnyerydev.com.multiplo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 *Reading Json Dynamically
 *I do not know the amount or keys of the objects returned by the server in json file
 * @author RonnyeryBarbosa ronnyerybarbosa@gmail.com
 *
 */
class MainActivity : AppCompatActivity()
{

    /**
     * List Categories
     */
    var listCategorias: MutableList<Category> = mutableListOf()

    /**
     *List Options
     */
    var listOptions: MutableList<Options> = mutableListOf()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //read json
        loadJson()


    }


    /**
     * Read Json
     */
    fun loadJson()
    {
        //intantiate resources
        val res = resources

        //open file json
        val inputStream  = res.openRawResource(R.raw.json)

        //sccanner file
        val scanner: Scanner = Scanner(inputStream)

        //roam file
        val builder = StringBuilder()

        //roam line
        while (scanner.hasNextLine())
        {
            builder.append(scanner.nextLine())
        }

        //parse json
        parseJson(builder.toString())

    }


    /**
     *Go through string json and take the jey of objects
     */
    fun parseJson( s : String)
    {

        try
        {
            //string to JsonObject
            val root = JSONObject(s)

            //roam keys
            val iterator = root.keys().iterator()

            //until it's over
            while (iterator.hasNext())
            {
                //get key object
                val key = iterator.next() as String

                //get content object
                val content = JSONObject(root.get(key).toString())

                //roam keys
                val iteratorContent = content.keys().iterator()

                //get content object
                while (iteratorContent.hasNext())
                {

                    //get key object
                    val keyContent = iteratorContent.next() as String

                    //create obj category
                    var category = Category(name=keyContent)

                    //convert JSONARRAY to Objects
                    convertJsonArrayToObjects(content.get(keyContent).toString(), category)


                }

            }


        } catch (e: JSONException)
        {
            //treat
        }

    }


    /**
     * Receives STRING array of objects
     * convert json in kotlin objects
     */
    fun convertJsonArrayToObjects(string: String, category: Category)
    {
        //convert string in JSONArray
        val jArray = JSONArray(string)

        //init
        listOptions = mutableListOf()

        //roam JSOnArray
        for (i in 0 until jArray.length())
        {
            //take objects by position
            val obj = jArray.optJSONObject(i)

            //instance gson
            val gson = Gson()

            //convert object in String
            val json = obj.toString()

            //convert string to object kolin
            val options = gson.fromJson(json,Options::class.java)

            listOptions.add(options)

            Log.d("obj", options.content)

            /**
             *Here you can give the key of each object, use if necessary
             */
            /*val iterator = obj.keys()
            while (iterator.hasNext()) {

                val currentKey = iterator.next()

                Log.d("key", currentKey)

            }*/
        }

        //set child in category
        category.list = listOptions

        //add a list category
        listCategorias.add(category)

        /*
         * Here you customize the adapter as you want
         */

        /**
         * Example
         * Show categories
         */
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,listCategorias)

        lista_transacoes_listview.adapter = arrayAdapter

    }


    /**
     * CLASSE OBJECT CHILD
     */
    class Options {

        val name: String = ""
        val content: String = ""
        val type: String = ""
        val id: String = ""

        override fun toString(): String {
            return content
        }


    }

    /**
     * CLASSE OBJECT FATHER
     */
    class Category(val name: String= "",
                  var list: List<Options>? = null)
    {
        override fun toString(): String {
            return name
        }
    }





}
