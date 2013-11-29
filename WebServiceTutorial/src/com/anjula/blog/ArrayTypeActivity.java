package com.anjula.blog;

import java.io.IOException;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.anjula.blog.ComplexTypeActivity.LoadDetailsTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ArrayTypeActivity extends Activity {

	Button btnShowDetails;
	TextView textViewResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complextypetute);

		btnShowDetails = (Button) findViewById(R.id.btnShowDetails);
		textViewResult = (TextView) findViewById(R.id.textViewResult);
		
		btnShowDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoadDetailsTask task = new LoadDetailsTask();
				task.execute("ID01");
			}
		});
	}

	class LoadDetailsTask extends AsyncTask<String, Void, SimpleObject[]> {

		private final String NAMESPACE = "http://service.blog.anjula.com/";
		private final String URL = "http://112.135.137.22:8080/BlogWebService/BlogWebService?WSDL";
		private final String SOAP_ACTION = "http://service.blog.anjula.com/getArrayDetails";
		private final String METHOD_NAME = "getArrayDetails";
		SimpleObject result[] = new SimpleObject[3];

		@Override
		protected SimpleObject[] doInBackground(String... idList) {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo idListPropety = new PropertyInfo();		
			idListPropety.setName("id");
			idListPropety.setValue(idList[0]);
			idListPropety.setType(idList[0].getClass());
			request.addProperty(idListPropety);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			// envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			envelope.addMapping(NAMESPACE, "SimpleObject",
					new SimpleObject().getClass());

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.debug = true;
				androidHttpTransport.call(SOAP_ACTION, envelope);

				Vector<SoapObject> response = (Vector<SoapObject>) envelope
						.getResponse();

				for (int i = 0; i < response.size(); i++) {
					SoapObject tempObject = response.get(i);
					if (tempObject != null) {
						SimpleObject temp = new SimpleObject();
						temp.setId(tempObject.getProperty("id").toString());
						temp.setName(tempObject.getProperty("name").toString());
						temp.setAddress(tempObject.getProperty("address")
								.toString());
						result[i] = temp;
					}
				}

				// SoapPrimitive response = (SoapPrimitive)
				// envelope.getResponse();

				return result;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(SimpleObject[] result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			String text = "";
			if (result == null) {
				Log.d("NULL", "This is null");
			} else {
				for (int i = 0; i < result.length; i++) {
					if (result[i] != null) {
						
						text=text+"Id: " + result[i].getId() + "\nName: "
								+ result[i].getName() + "\nAddress: "
								+ result[i].getAddress()+"\n";

					}
				}
				textViewResult.setText(text);
			}

		}

	}

}
