package com.flash;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;


public class MainActivity extends Activity {
	
	static private Camera camera;
	private Boolean isFlashOn;
	private Boolean isFlash;
	Parameters parametros;
	ToggleButton btnOnOff;
	
	@Override
	protected void onPause(){
		super.onPause();
		OffCamera();
		
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		getCamera();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		if(camera != null){ //si esta en uso la variable
			camera.release(); //libera la camara para otra app
			camera = null;// la reinicia
		}
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        btnOnOff=(ToggleButton) findViewById(R.id.btnFlash);
        btnOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					OnCamera();
				}else{
					OffCamera();
				}
			}
		});
        
        isFlash = this.getApplicationContext().getPackageManager().
        		hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        isFlashOn = false;
        getCamera();
    }

	protected void OnCamera() {
		// TODO Auto-generated method stub
		if(!isFlashOn){
			if(camera == null || parametros == null){
				return;
			}
			
			parametros = camera.getParameters();
			parametros.setFlashMode(parametros.FLASH_MODE_TORCH);
			camera.setParameters(parametros);
			camera.startPreview();
			isFlashOn = true;
			Log.v("App Camera", String.valueOf(isFlashOn));
			
		}
	}

	protected void OffCamera() {
		// TODO Auto-generated method stub
		if(isFlashOn){
			if(camera == null || parametros == null){
				return;
			}
			
			parametros = camera.getParameters(); //toma los parametros de la camara
			parametros.setFlashMode(parametros.FLASH_MODE_OFF); //agrega un nuevo parametro a los parametros
			camera.setParameters(parametros);//establece ese parametro a la camara
			camera.startPreview(); //tiene que estar para iniciar la vista previa de la imagen a capturar
			isFlashOn = false; //reinicio de la variable
			Log.v("App Camera", String.valueOf(isFlashOn)); //mensaje de consola
			 
			
		}
	}
    
	private void getCamera() {
		if(camera == null){
			try{camera = camera.open(); //abre la camara
			parametros = camera.getParameters();
			}catch (Exception e){ //si ocurre una excepcion la atrapa y manda lo de abajo
				e.printStackTrace(); //Se utiliza para imprimir el registro del stack donde se ha iniciado la excepción.

			}
		}
	}
    

}
