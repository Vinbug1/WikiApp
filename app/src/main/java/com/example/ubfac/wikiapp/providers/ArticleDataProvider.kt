package com.example.ubfac.wikiapp.providers


import com.example.ubfac.wikiapp.model.Urls
import com.example.ubfac.wikiapp.model.WikiResult
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.response
import com.github.kittinunf.fuel.httpGet
import java.io.Reader

class ArticleDataProvider{





    fun search(term: String, skip: Int, take: Int, responseHandler: (WikiResult) -> Unit?){
        Urls.getSearchUrl(term, skip, take).httpGet().response(WikipediaDataDeserializer()){_,response, result ->

            // check response result
            if (response.httpStatusCode != 200) {
                throw Exception("Unable to get articles")
            }
            // do some thing with the http response
            val(data, _ ) = result
            responseHandler.invoke((data as  WikiResult))

        }
    }
     fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?){
         Urls.getRandomUrl(take).httpGet().response(WikipediaDataDeserializer()) { _, response, result ->

             // check response result
             if (response.httpStatusCode != 200) {
                 throw Exception("Unable to get articles")
             }

             // do some thing with the http response
//             val (data, _) = result
//             responseHandler.invoke(data as WikiResult)
         }

     }

   class  WikipediaDataDeserializer: ResponseDeserializable<WikiResult>{
             override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult ::class.java)

   }


}