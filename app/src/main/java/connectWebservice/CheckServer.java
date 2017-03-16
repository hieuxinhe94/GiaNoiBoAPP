package connectWebservice;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CheckServer  {

    public CheckServer()                                                                       {
    }
    public  Boolean checkLogin(String userName,String passWord)                                {
        final String NAMESPACE = "http://tempuri.org/tvsapi";
        final String SOAP_ACTION = "http://tempuri.org/tvsapi/checkLogin";
        final String MENTHOD_NAME = "checkLogin";
        final String URL = "http://www.gianoibo.com/webservice/androidapi.asmx";

        SoapObject request = new SoapObject(NAMESPACE, MENTHOD_NAME);
        request.addProperty("acc", userName);
        request.addProperty("pass", passWord);

        SoapSerializationEnvelope envilope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envilope.dotNet = true;
        envilope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        try {
            httpTransportSE.call(SOAP_ACTION, envilope);

            SoapPrimitive respon = (SoapPrimitive) envilope.getResponse();
            return  (Boolean.parseBoolean(respon.toString()) ) ;
        } catch (Exception e) {

            return false;
        }
    }
    public  int registerLogin(String userName,String passWord)                                 {
        final String NAMESPACE = "http://tempuri.org/tvsapi";
        final String SOAP_ACTION = "http://tempuri.org/tvsapi/register";
        final String METHOD_NAME = "register";
        final String URL = "http://www.gianoibo.com/webservice/androidapi.asmx";

        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);

        request.addProperty("userName",userName);
        request.addProperty("passWord",passWord);

        SoapSerializationEnvelope envilope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envilope.dotNet = true;
        envilope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        try
        {
            httpTransportSE.call(SOAP_ACTION,envilope);
            SoapPrimitive respon = (SoapPrimitive)envilope.getResponse();
            if (respon.toString() == "false" ) return 0 ;
            return  1;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.i("Register",e.getCause().toString());
            return  0;
        }


    }
    public  String getJsonString(String userName,String passWord)                              {

        final String NAMESPACE = "http://tempuri.org/tvsapi";
        final String SOAP_ACTION = "http://tempuri.org/tvsapi/getJsonMessage";
        final String METHOD_NAME = "getJsonMessage";
        final String URL = "http://www.gianoibo.com/webservice/androidapi.asmx";

        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
        request.addProperty("acc",userName);
         request.addProperty("pass",passWord);
        SoapSerializationEnvelope envilope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envilope.dotNet = true;
        envilope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        try
        {
            httpTransportSE.call(SOAP_ACTION,envilope);
            SoapPrimitive respon = (SoapPrimitive)envilope.getResponse();
            if (respon.toString() == "" ) return null ;
            return respon.toString();
        }
        catch (Exception e)
        {
            return  null;
        }


    }
}

