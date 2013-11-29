package com.anjula.blog;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ComplexTypeActivity extends Activity {

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

	class LoadDetailsTask extends AsyncTask<String, Void, SimpleObject> {

		private final String NAMESPACE = "http://service.blog.anjula.com/";
		private final String URL = "http://112.135.137.22:8080/BlogWebService/BlogWebService?WSDL";
		private final String SOAP_ACTION = "http://service.blog.anjula.com/getDetails";
		private final String METHOD_NAME = "getDetails";

		SimpleObject result = new SimpleObject();

		@Override
		protected SimpleObject doInBackground(String... addId) {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			PropertyInfo idListPropety = new PropertyInfo();

			idListPropety.setName("id");
			idListPropety.setValue(addId[0]);
			idListPropety.setType(addId[0].getClass());
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
				SoapObject response = (SoapObject) envelope.getResponse();

				result.setId(response.getProperty("id").toString());
				result.setName(response.getProperty("name").toString());
				result.setAddress(response.getProperty("address").toString());

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
		protected void onPostExecute(SimpleObject result) {
			// TODO Auto-generated method stub
			if (result != null)
				textViewResult.setText("Id: " + result.getId() + "\nName: "
						+ result.getName() + "\nAddress: "
						+ result.getAddress());
		}

	}

}
