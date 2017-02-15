package com.leixun.smartcushion.view;  
  
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.leixun.smartcushion.R;
  
public class TemperateDialog extends Dialog {  
  
    public TemperateDialog(Context context) {  
        super(context);  
    }  
  
    public TemperateDialog(Context context, int theme) {  
        super(context, theme);  
    }  
  
    public static class Builder {  
        private Context context;  
        private View contentView;  
        private SeekBar seekBar;
        private TextView tvTemperatureValue;
        private DialogInterface.OnClickListener positiveButtonClickListener;  
        private DialogInterface.OnClickListener negativeButtonClickListener;  
    	public int mtemperatureValue = 20;
    	
        public Builder(Context context,int temperatureValue) {  
            this.context = context; 
            this.mtemperatureValue  = temperatureValue;
        }  
  
  
        public Builder setContentView(View v) {  
            this.contentView = v;  
            return this;  
        }  
  
        /** 
         * Set the positive button resource and it's listener 
         *  
         * @param positiveButtonText 
         * @return 
         */  
        public Builder setPositiveButton( DialogInterface.OnClickListener listener) {  
            this.positiveButtonClickListener = listener;  
            return this;  
        }  
  
        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {  
            this.negativeButtonClickListener = listener;  
            return this;  
        }  
  
  
        public CustomDialog create() {  
            LayoutInflater inflater = (LayoutInflater) context  
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            // instantiate the dialog with the custom Theme  
            final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);  
            View layout = inflater.inflate(R.layout.dialog_temperature_layout, null);  
            dialog.addContentView(layout, new LayoutParams(  
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)); 
            seekBar = (SeekBar) layout.findViewById(R.id.seekbar_temperature);
            tvTemperatureValue = (TextView) layout.findViewById(R.id.tv_temperature);
            seekBar.setProgress(mtemperatureValue-20); 
            tvTemperatureValue.setText(mtemperatureValue+"℃");
            seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					tvTemperatureValue.setText(progress+20+"℃");
					mtemperatureValue = progress+20;
				}
			});

                if (positiveButtonClickListener != null) {  
                    ((Button) layout.findViewById(R.id.btn_dialog_ok))  
                            .setOnClickListener(new View.OnClickListener() {  
                                public void onClick(View v) {  
                                    positiveButtonClickListener.onClick(dialog,  
                                            DialogInterface.BUTTON_POSITIVE); }  
                      });  
                }  
            // set the cancel button  

                if (negativeButtonClickListener != null) {  
                    ((Button) layout.findViewById(R.id.btn_dialog_cancle))  
                            .setOnClickListener(new View.OnClickListener() {  
                                public void onClick(View v) {  
                                    negativeButtonClickListener.onClick(dialog,  
                                            DialogInterface.BUTTON_NEGATIVE);  
                                }  
                            });  
                }  
            dialog.setContentView(layout);  
            return dialog;  
        }  
    }  
}  