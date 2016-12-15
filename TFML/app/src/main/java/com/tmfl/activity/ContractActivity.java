package com.tmfl.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.adapter.ContractsListAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.fragment.ApplyLoanFragment;
import com.tmfl.fragment.ComplaintsFragment;
import com.tmfl.fragment.ReferFriendFragment;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.model.ContractResponseModel.ContractsInputModel;
import com.tmfl.model.ContractResponseModel.ContractsResponseModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractActivity extends DrawerBaseActivity implements View.OnClickListener {
	View                   selectedView;
	TmflApi                tmflApi;
	ContractsInputModel    contractsInputModel;
	ContractsResponseModel contractsResponseModel;
	String                 strApiToken, strUserId, strTerCount, strOverdue, strTotal;
	private TextView txtTitleContract, txtTotalCount, txtTerminatedCount, txtOverDueCount, txtContractNo, txtRcNo, txtNextDueDate, txtCurrentEmi, txtLastPayment, txtPreviousEmi, txtOverdueAmount, txtRepaymentMode, txtTerminitedContracName, txtTerminatedContractDate, txtSchemes, txtApplyLoan, txtReferFriend, txtLoanStatus, txtContactUs;
	private LinearLayout linSchemes, linApplyLoan, linReferFriend, linLoanStaus, linContactUs;
	private ImageView imgDrawer;
	private ListView  lstCotractList;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		View    view    = getLayoutInflater().inflate( R.layout.activity_contract, frameLayout );
		Toolbar toolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		getSupportActionBar().setTitle( "" );
		tmflApi = ApiService.getInstance().call();
		init();
	}


	public void init() {
		txtTitleContract = ( TextView ) findViewById( R.id.txt_title_contract );
		txtTotalCount = ( TextView ) findViewById( R.id.txt_total_count );
		txtTerminatedCount = ( TextView ) findViewById( R.id.txt_terminated_count );
		txtOverDueCount = ( TextView ) findViewById( R.id.txt_overdue_count );
		txtSchemes = ( TextView ) findViewById( R.id.txtSchemes );
		txtApplyLoan = ( TextView ) findViewById( R.id.txtApplyLoan );
		txtReferFriend = ( TextView ) findViewById( R.id.txtReferFriend );
		txtLoanStatus = ( TextView ) findViewById( R.id.txtLoanStatus );
		txtContactUs = ( TextView ) findViewById( R.id.txtContactUs );
		lstCotractList = ( ListView ) findViewById( R.id.lstContact );
		SetFonts.setFonts( this, txtSchemes, 2 );
		SetFonts.setFonts( this, txtApplyLoan, 2 );
		SetFonts.setFonts( this, txtReferFriend, 2 );
		SetFonts.setFonts( this, txtLoanStatus, 2 );
		SetFonts.setFonts( this, txtContactUs, 2 );
		linSchemes = ( LinearLayout ) findViewById( R.id.llSchemes );
		linApplyLoan = ( LinearLayout ) findViewById( R.id.llApplyLoan );
		linReferFriend = ( LinearLayout ) findViewById( R.id.linReferFriend );
		linLoanStaus = ( LinearLayout ) findViewById( R.id.linLoanStaus );
		linContactUs = ( LinearLayout ) findViewById( R.id.lin_contact_us );
		imgDrawer = ( ImageView ) findViewById( R.id.img_drawer );
//		selectedView = ( View ) findViewById( R.id.viewId );
		SetFonts.setFonts( this, txtTitleContract, 2 );
		linSchemes.setOnClickListener( this );
		linApplyLoan.setOnClickListener( this );
		linReferFriend.setOnClickListener( this );
		linLoanStaus.setOnClickListener( this );
		linContactUs.setOnClickListener( this );
		imgDrawer.setOnClickListener( this );
		contractsInputModel = new ContractsInputModel();
		contractsResponseModel = new ContractsResponseModel();

		strApiToken = PreferenceHelper.getString( PreferenceHelper.API_TOKEN );
		strUserId = PreferenceHelper.getString( PreferenceHelper.USER_ID );
		Log.e( "strApiToken", strApiToken );
		Log.e( "strUserId", strUserId );

		callContractWebservice();
		CommonUtils.showProgressDialog( ContractActivity.this, "Getting Your Information" );
		{
			loadContractDetailService( contractsInputModel );
		}

	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.llSchemes:
				Intent intentSchema = new Intent( this, SchemesActivity.class );
				intentSchema.putExtra( "TAB_SELECTED", Constant.ISSCHEMASTABSELECT );
				startActivity( intentSchema );
				break;
			case R.id.llApplyLoan:
				/*Intent intentApplyLoan = new Intent( this, SchemesActivity.class );
				intentApplyLoan.putExtra( "TAB_SELECTED", Constant.ISAPPLYLOANSELECT );
				startActivity( intentApplyLoan );*/

				getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );

				getSupportFragmentManager().beginTransaction()
						.addToBackStack( getClass().getName() )
						.replace( R.id.frame_container_contract, new ApplyLoanFragment() )
						.commit();

				break;
			case R.id.linReferFriend:
				/*Intent intentReferFriend = new Intent( this, SchemesActivity.class );
				intentReferFriend.putExtra( "TAB_SELECTED", Constant.ISREFERFREINDSELECT );
				startActivity( intentReferFriend );*/

				getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );

				getSupportFragmentManager().beginTransaction()
						.addToBackStack( getClass().getName() )
						.replace( R.id.frame_container_contract, new ReferFriendFragment() )
						.commit();

				break;
			case R.id.linLoanStaus:
			/*	linLoanStausClick();
				SocialUtil.loanStatusDialog( ContractActivity.this, linLoanStaus, selectedView );*/

				if ( new ComplaintsFragment().isAdded() ) {
					getSupportFragmentManager().beginTransaction().replace( R.id.frame_container_contract, new ComplaintsFragment() ).commit();
				}
				else {
					getSupportFragmentManager().beginTransaction().add( R.id.frame_container_contract, new ComplaintsFragment() ).commit();
				}

				break;
			case R.id.lin_contact_us:
				contactDialog();
				break;
			case R.id.img_drawer:
				openDrawer();
				break;
		}
	}

	public void callContractWebservice() {
		contractsInputModel.setUser_id( strUserId );
		contractsInputModel.setApi_token( strApiToken );

	}

	public void loadContractDetailService( ContractsInputModel contractsInputModel ) {

		tmflApi.getContractListData( contractsInputModel ).enqueue( new Callback< ContractsResponseModel >() {
			@Override
			public void onResponse( Call< ContractsResponseModel > call, Response< ContractsResponseModel > response ) {
				CommonUtils.closeProgressDialog();
				Log.e( "ResponseBody", new Gson().toJson( response.body() ) );
				// List<ContractModel>model= (List<ContractModel>) response.body().getData();
				ArrayList< ContractModel > models = new ArrayList< ContractModel >();
				if ( response.body().getData().getActive().getContracts() != null ) {
					models.addAll( response.body().getData().getActive().getContracts() );
				}
				else {
					Toast.makeText( getBaseContext(), "No Data Founnd", Toast.LENGTH_SHORT ).show();

				}

				if ( response.body().getData().getTerminated().getCount() != 0 ) {
					models.add( null );
				}
				models.addAll( response.body().getData().getTerminated().getContracts() );
				lstCotractList.setAdapter( new ContractsListAdapter( ContractActivity.this, models ) );
				strTotal = response.body().getData().getTotal().toString();
				strTerCount = response.body().getData().getTerminated().getCount().toString();
				strOverdue = response.body().getData().getActive().getCount().toString();
				if ( strTotal != null ) {
					txtTotalCount.setVisibility( View.VISIBLE );
					txtTotalCount.setText( strTotal );
				}
				if ( strTerCount != null ) {
					txtTerminatedCount.setVisibility( View.VISIBLE );
					txtTerminatedCount.setText( strTerCount );
				}
				if ( strOverdue != null ) {
					txtOverDueCount.setVisibility( View.VISIBLE );
					txtOverDueCount.setText( strOverdue );
				}
				Log.e( "REsponseModel", response.body().getData().getTotal().toString() );
			}

			@Override
			public void onFailure( Call< ContractsResponseModel > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
				Log.e( "Response Error", "Error Data" );
			}
		} );
	}

	public void linLoanStausClick() {
		int width = linLoanStaus.getWidth();
		Log.e( "Widthoflin", "" + width );
		  /*ON SELECTED*/

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
			linLoanStaus.setBackgroundColor( Color.parseColor( "#003668" ) );
			selectedView.setVisibility( View.VISIBLE );

		}
	}

	public void contactDialog() {
		final Dialog contactDialog = new Dialog( ContractActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar );
		contactDialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		contactDialog.setContentView( R.layout.dialog_contact_us );
		WindowManager.LayoutParams params = contactDialog.getWindow().getAttributes();
		params.y = 65;
		params.x = 40;
		params.gravity = Gravity.BOTTOM | Gravity.RIGHT | Gravity.CENTER;
		contactDialog.getWindow().setAttributes( params );
		contactDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		contactDialog.setCancelable( true );
		final ImageView imgMessage   = ( ImageView ) contactDialog.findViewById( R.id.imgmsg );
		final ImageView imgMap       = ( ImageView ) contactDialog.findViewById( R.id.imgmap );
		final ImageView imgWhatsApp  = ( ImageView ) contactDialog.findViewById( R.id.imgwhatsapp );
		final ImageView imgPhoneCall = ( ImageView ) contactDialog.findViewById( R.id.imgcall );
		final ImageView imgcancel    = ( ImageView ) contactDialog.findViewById( R.id.imgcancel );
		if ( CommonUtils.isNetworkAvailable( ContractActivity.this ) ) {
			SocialUtil.getContactList();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
		imgcancel.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				contactDialog.dismiss();
			}
		} );
		imgMessage.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.sendMail( ContractActivity.this, SocialUtil.email );
			}
		} );
		imgPhoneCall.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.dialPhoneCall( ContractActivity.this, SocialUtil.phoneNo );
			}
		} );
		imgWhatsApp.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.sendWhatsAppMsg( ContractActivity.this, SocialUtil.whatsAppNo );
			}
		} );
		imgMap.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				Toast.makeText( ContractActivity.this, "Map service not avialable", Toast.LENGTH_SHORT ).show();
			}
		} );
		contactDialog.show();

	}


}