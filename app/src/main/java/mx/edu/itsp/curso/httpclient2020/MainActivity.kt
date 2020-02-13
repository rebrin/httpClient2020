package mx.edu.itsp.curso.httpclient2020

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import org.xml.sax.InputSource
import org.xml.sax.XMLReader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /* StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.
            Builder().permitNetwork().build())
*/
        button.setOnClickListener({
            BuscarGoogle().execute()
        })
    }

    fun enviarValor(): String {
        var pagina:String=""
        val etBusqueda=findViewById<EditText>(R.id.etPalabra)
        var url:URL=URL("https://books.google.com/books/feeds/volumes?q="
                +URLEncoder.encode(etBusqueda.text.toString(),"UTF8"))
        val conexion:HttpURLConnection= url.openConnection()
                as HttpURLConnection
        //si la respuesta es OK
        if(conexion.responseCode == HttpURLConnection.HTTP_OK){
            val entrada: BufferedReader=
                BufferedReader(InputStreamReader
                    (conexion.inputStream))
            var linea:String=entrada.readLine()
                pagina+=linea
            entrada.close()

        }else
        {
            pagina="existe un error de comunicación"
        }
        conexion.disconnect()
        return pagina
    }

    fun resultadosGoogleBooks():String{
        var url:URL=URL("https://books.google.com/books/feeds/volumes?q="
                +URLEncoder.encode(etPalabra.text.toString(),"UTF8"))
        var factory:SAXParserFactory= SAXParserFactory.newInstance()
        var parser:SAXParser=factory.newSAXParser()
        var reader:XMLReader=parser.xmlReader
        var manejadorXML= ManejadorXML()
        reader.contentHandler=manejadorXML
        reader.parse(InputSource(url.openStream()))
        return  manejadorXML.TotalResults
    }

    inner class BuscarGoogle : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg p0: Void?): String {
            return resultadosGoogleBooks()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tvSalida.text=result
        }

        override fun onCancelled() {
            super.onCancelled()
            tvSalida.text="Ocurrio algún error"
        }
    }


}
