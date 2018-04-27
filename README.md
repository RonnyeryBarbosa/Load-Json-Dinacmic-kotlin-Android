# Load-Json-Dinacmic-kotlin-Android
Loading a dynamic json without knowing the names of the keys


I had trouble trying to read dynamic json.

I came to this solution.

Feel free to suggest changes.

solved my problem


JSON EXAMPLE:
```
{
  "categories": {
    "color": [
      {
        "content": "aaaa",
        "type": "1",
        "id":  "2"
      },
      {
        "content": "aaaa",
        "type": "1",
        "id":  "2"
      }
    ],
    "size": [
      {
        "content": "P",
        "type": "1",
        "id":  "2"
      },
      {
        "content": "m",
        "type": "1",
        "id":  "2"
      }
    ],
    "type": [
      {
        "content": "confort",
        "type": "1",
        "id":  "2"
      },
      {
        "content": "premium",
        "type": "1",
        "id":  "2"
      }
    ],
    "height": [
      {
        "content": "13cm",
        "type": "1",
        "id":  "2"
      },
      {
        "content": "14m",
        "type": "1",
        "id":  "2"
      }
    ]
  },
  "info":
  {
  }
}
```

Key reading

```
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

```

SubKeys Read

```
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

```

