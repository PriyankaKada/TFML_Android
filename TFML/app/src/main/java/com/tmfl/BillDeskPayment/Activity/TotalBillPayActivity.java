package com.tmfl.BillDeskPayment.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.BillDeskPayment.Adapter.CustomAdapter;
import com.tmfl.BillDeskPayment.GetBillDeskMsg;
import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;
import com.tmfl.R;
import com.tmfl.activity.BannerActivity;
import com.tmfl.activity.DrawerBaseActivity;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalBillPayActivity extends DrawerBaseActivity implements View.OnClickListener {

	static String total;
	ListView       listView;
	Contract       contractModel;
	ProgressDialog dialog;
	TextView       totalamounttextview, txtMobileNo, txt_title_contract;
	double totalAmount = 0.0;
	private List< Contract > listOfContract;
	private ImageView        imgDrawerPayment, img_contract;
	String queryString = "";

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		//setContentView( R.layout.list_layout );
		View view = getLayoutInflater().inflate( R.layout.list_layout, frameLayout );
		Toolbar toolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		getSupportActionBar().setTitle( "" );
		listView = ( ListView ) findViewById( R.id.lstView );
//		totalamounttextview = ( TextView ) findViewById( R.id.totalamounttextview );
//txtMobileNo = ( TextView ) findViewById( R.id.txtMobileNo );
		txt_title_contract = ( TextView ) findViewById( R.id.txt_title_contract );
		img_contract = ( ImageView ) findViewById( R.id.img_contract );
		txt_title_contract.setText( "Due Details" );
		img_contract.setOnClickListener( this );
		imgDrawerPayment = ( ImageView ) findViewById( R.id.img_drawer_payament );
		imgDrawerPayment.setOnClickListener( this );
		dialog = new ProgressDialog( TotalBillPayActivity.this );

//		txtMobileNo.setText( ) );

		findViewById( R.id.btnPayNow ).setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {

				showPayNowDialog();


			}
		} );
		getData();
		callPayNow();

	}

	private void callPayNow() {


		if ( listOfContract != null ) {
			for ( Contract contract : listOfContract ) {
				if ( contract.getIsSelected() ) {
					if ( !TextUtils.isEmpty( queryString ) ) {
						queryString = queryString + ",";
					}
					queryString = queryString + contract.getUsrConNo() + "@" + contract.getTotalCurrentDue();
					if ( TextUtils.isDigitsOnly( contract.getTotalCurrentDue() ) ) {
								totalAmount = totalAmount + Double.parseDouble( contract.getTotalCurrentDue() );
					}
				}
			}

			Log.e( "total amount ", " total amount " + totalAmount );
			//   ((TextView) findViewById(R.id.totalamounttextview)).setText(String.valueOf(totalAmount + ""));
		}

		if ( TextUtils.isEmpty( queryString ) ) {
			Toast.makeText( TotalBillPayActivity.this
					, "Select atleast 1 contract "
					, Toast.LENGTH_LONG ).show();
		}
		else {
		}
		callPayNow();

		Log.e( "query string ", " query amount " + queryString );
	}


	private void showPayNowDialog() {

		final Dialog totalAmountDialog = new Dialog( this, android.R.style.Theme_Holo_Dialog_NoActionBar );

		totalAmountDialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		totalAmountDialog.setContentView( R.layout.dialog_paynow );
		WindowManager.LayoutParams params = totalAmountDialog.getWindow().getAttributes();
		params.y = 120;
		params.x = 120;
		params.gravity = Gravity.CENTER | Gravity.CENTER;
		totalAmountDialog.getWindow().setAttributes( params );
		totalAmountDialog.setCancelable( true );
		totalAmountDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		final EditText edtmobileno   = ( EditText ) totalAmountDialog.findViewById( R.id.edt_mobile_no );
		final TextView totalamounttextview      = ( TextView ) totalAmountDialog.findViewById( R.id.total_amount );
		final Button btnloanstatus = ( Button ) totalAmountDialog.findViewById( R.id.btn_loan_status );
		edtmobileno.setText(PreferenceHelper.getString(PreferenceHelper.MOBILE));

		String stringdouble= Double.toString(totalAmount);
		totalamounttextview.setText(stringdouble);

		SetFonts.setFonts( this, btnloanstatus, 2 );
		btnloanstatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

				String monumber  = edtmobileno.getText().toString();
//				String otpnumber = edtotpno.getText().toString();
				if ( TextUtils.isEmpty( monumber ) ) {
					Toast.makeText( TotalBillPayActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT ).show();
				}
				else {
					new GetBillDeskMsg( TotalBillPayActivity.this, queryString, monumber );

				}





			}
		} );
		totalAmountDialog.show();
		totalAmountDialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel( DialogInterface dialog ) {
//				linloanstatus.setBackgroundColor( Color.parseColor( "#004c92" ) );
//				selectedView.setVisibility( View.INVISIBLE );
			}
		} );

	}

	public void getData() {

		TmflApi service = ApiService.getInstance().call();

		dialog.setCanceledOnTouchOutside( false );
		dialog.setCancelable( false );
		dialog.setMessage( "Loading" );
		dialog.show();

		JSONData jsonData = new JSONData();
		jsonData.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
		jsonData.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );

		service.getListData( jsonData ).enqueue( new Callback< Example >() {
			@Override
			public void onResponse( Call< Example > call, Response< Example > response ) {

				List< Contract > list = new ArrayList< Contract >();
				list.addAll( response.body().getData().getActive().getContracts() );
				Log.e( "List Size", String.valueOf( response.body().getData().getActive().getContracts().size() ) );
				for ( int i = 0; i < list.size(); i++ ) {
					list.get( i ).setSelected( false );
				}
				CustomAdapter adapter = new CustomAdapter( TotalBillPayActivity.this, listOfContract = list );
				listView.setAdapter( adapter );
				dialog.dismiss();
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure( Call< Example > call, Throwable t ) {
				dialog.dismiss();
				Log.e( "Error Log", "Error:" + t.getMessage() );
			}
		} );
	}

	public void updateTotalAmount( double amount, int pos ) {
		if ( listOfContract != null ) {
			/*if ( listOfContract.get( pos ).getIsSelected() ) {
				totalAmount = totalAmount + amount;
			}
			else if ( !listOfContract.get( pos ).getIsSelected() ) {
				if ( totalAmount != 0 ) {
					totalAmount = totalAmount - amount;
				}
			}*/
			totalAmount = amount;

		}
		Log.e( "total amount ", " total amount " + totalAmount );
//		( ( TextView ) findViewById( R.id.totalamounttextview ) ).setText( String.valueOf( totalAmount + "" ) );
	}

	@Override
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.img_emi_back:
				finish();
				break;

			case R.id.img_drawer_payament:
				openDrawer();
				break;

			case R.id.img_contract:
				if ( PreferenceHelper.getBoolean( "SaveLogin" ) ) {
					finish();
				}
				else {
					startActivity( new Intent( TotalBillPayActivity.this, BannerActivity.class ) );
					finish();
				}

				break;
		}
	}
}