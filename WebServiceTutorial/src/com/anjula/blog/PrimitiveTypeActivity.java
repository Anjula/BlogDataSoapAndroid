package com.anjula.blog;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrimitiveTypeActivity extends Activity {

	private final String NAMESPACE="http://www.w3schools.com/webservices/";
	private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx?WSDL";
	private final String SOAP_ACTION="http://www.w3schools.com/webservices/CelsiusToFahrenheit";
	private final String METHOD_NAME="CelsiusToFahrenheit";

	private TextView textViewAnswer;
	private Button btnConvert;
	private EditText editTextCValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.primitivetypetute);

		textViewAnswer = (TextView) findViewById(R.id.textViewAnswer);
		btnConvert = (Button) findViewById(R.id.btnConvert);
		editTextCValue = (EditText) findViewById(R.id.editTextCValue);
		
		btnConvert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String inputString = editTextCValue.getText().toString();
				ConvertorAsyncTask task = new ConvertorAsyncTask();
				task.execute(inputString);
			}
		});

	}

	class ConvertorAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo celPI = new PropertyInfo();
			celPI.setName("Celsius");
			celPI.setValue(params[0]);
			celPI.setType(double.class);
			request.addProperty(celPI);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				return response.toString();
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
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result != null)
				textViewAnswer.setText(result + " F");
			else
				textViewAnswer.setText("Result cannot get.Please try again");

		}

	}

}
