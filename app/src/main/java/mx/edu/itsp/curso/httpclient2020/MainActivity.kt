package mx.edu.itsp.curso.httpclient2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.
            Builder().permitNetwork().build())
    }

    fun enviarValor(view: View){
        var pagina:String=""
        val etBusqueda=findViewById<EditText>(R.id.etPalabra)
        var url:URL=URL("https://www.google.com/search?q="
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
            tvSalida.setText(pagina)
        }else
        {
            tvSalida.setText("Error " +
                    "de conexion:${conexion.errorStream}")
        }
        conexion.disconnect()

    }
}
