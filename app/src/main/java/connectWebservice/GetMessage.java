package connectWebservice;

import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import object.Message;

public class GetMessage extends AsyncTask<String,Void,Message> {

    private final String TAG = "getMessageByAccount";
    private final String NAMESPACE = "http://tempuri.org/tvsapi";
    private final String SOAP_ACTION = "http://tempuri.org/tvsapi/getMessageByAccount";
    private final String MENTHOD_NAME = "getMessageByAccount";
    private final String URL = "http://www.gianoibo.com/webservice/androidapi.asmx";
    Message message;
    public GetMessage() {
        super();
    }
    @Override protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override protected void onPostExecute(Message message)                                    {
        super.onPostExecute(message);
        Log.i(TAG,"Thuc thi AsyncTask ... ");
        // start show message  in here
        return;
    }
    @Override protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override protected Message doInBackground(String... params)                               {
        Log.i(TAG,"Đang chạy dưới nền ... ");

         message=  loadMessage(params[0].toString()); //test
        if (message!= null)
        {
        Log.i(TAG,message.toString());
        return message;
        }
        return null;
    }
    public  Message loadMessage(String idUser)                                                 {
        SoapObject request = new SoapObject(NAMESPACE,MENTHOD_NAME);
        request.addProperty("acc",idUser);
        SoapSerializationEnvelope envilope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envilope.dotNet = true;
        envilope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
        try
        {
            httpTransportSE.call(SOAP_ACTION,envilope);
            SoapPrimitive respon = (SoapPrimitive) envilope.getResponse();
            String s = respon.toString();
            Log.i(TAG,s+".");
            if (respon.toString() == null) return null ;
            else{
            Message message = new Message();             // parse message
            message.setSMS(respon.toString());
            // sen pendding intent
            return message;
        }
        }
        catch (Exception e)
        {
            return  null;
        }
    }
}
