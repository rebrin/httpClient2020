package mx.edu.itsp.curso.httpclient2020

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.lang.StringBuilder

class ManejadorXML : DefaultHandler() {
    lateinit var TotalResults:String
    var stringBuilder: StringBuilder= StringBuilder()

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        stringBuilder.setLength(0)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        stringBuilder.append(ch,start,length)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if(localName.equals("totalResults")){
             TotalResults=stringBuilder.toString()
        }
    }
}