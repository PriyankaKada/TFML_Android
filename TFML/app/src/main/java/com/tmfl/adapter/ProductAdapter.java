package com.tmfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.model.productResponseModel.ProductListResponseModel;

import java.util.List;

/**
 * Created by webwerks on 23/8/16.
 */

public class ProductAdapter extends ArrayAdapter< ProductListResponseModel > {
	private Context context;

	public ProductAdapter( Context context, List< ProductListResponseModel > data ) {
		super( context, 0, data );
		this.context = context;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		View row = convertView;
		if ( row == null ) {
			row = LayoutInflater.from( context ).inflate( R.layout.layout_spinner_textview, parent, false );
		}
		TextView ProductName = ( TextView ) row.findViewById( R.id.txtValue );
		ProductName.setText( getItem( position ).getProdName() );
		return row;
	}

	@Override
	public View getDropDownView( int position, View convertView, ViewGroup parent ) {
		return getView( position, convertView, parent );
	}
}
