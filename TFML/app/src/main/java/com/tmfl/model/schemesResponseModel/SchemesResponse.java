package com.tmfl.model.schemesResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by webwerks on 29/7/16.
 */

public class SchemesResponse implements Serializable {

	@SerializedName( "data" )
	@Expose
	private OfferData offerData;

	public OfferData getOfferData() {
		return offerData;
	}

	public void setOfferData( OfferData offerData ) {
		this.offerData = offerData;
	}


	/*@SerializedName( "per_page" )
	@Expose
	private Integer perPage;
	@SerializedName( "current_page" )
	@Expose
	private Integer currentPage;
	@SerializedName( "next_page_url" )
	@Expose
	private Object  nextPageUrl;
	@SerializedName( "prev_page_url" )
	@Expose
	private Object  prevPageUrl;
	@SerializedName( "from" )
	@Expose
	private Integer from;
	@SerializedName( "to" )
	@Expose
	private Integer to;
	@SerializedName( "data" )
	@Expose
	private ArrayList< Datum > data = new ArrayList< Datum >();

	*//**
	 * @return The perPage
	 *//*
	public Integer getPerPage() {
		return perPage;
	}

	*//**
	 * @param perPage The per_page
	 *//*
	public void setPerPage( Integer perPage ) {
		this.perPage = perPage;
	}

	*//**
	 * @return The currentPage
	 *//*
	public Integer getCurrentPage() {
		return currentPage;
	}

	*//**
	 * @param currentPage The current_page
	 *//*
	public void setCurrentPage( Integer currentPage ) {
		this.currentPage = currentPage;
	}

	*//**
	 * @return The nextPageUrl
	 *//*
	public Object getNextPageUrl() {
		return nextPageUrl;
	}

	*//**
	 * @param nextPageUrl The next_page_url
	 *//*
	public void setNextPageUrl( Object nextPageUrl ) {
		this.nextPageUrl = nextPageUrl;
	}

	*//**
	 * @return The prevPageUrl
	 *//*
	public Object getPrevPageUrl() {
		return prevPageUrl;
	}

	*//**
	 * @param prevPageUrl The prev_page_url
	 *//*
	public void setPrevPageUrl( Object prevPageUrl ) {
		this.prevPageUrl = prevPageUrl;
	}

	*//**
	 * @return The from
	 *//*
	public Integer getFrom() {
		return from;
	}

	*//**
	 * @param from The from
	 *//*
	public void setFrom( Integer from ) {
		this.from = from;
	}

	*//**
	 * @return The to
	 *//*
	public Integer getTo() {
		return to;
	}

	*//**
	 * @param to The to
	 *//*
	public void setTo( Integer to ) {
		this.to = to;
	}

	*//**
	 * @return The data
	 *//*
	public ArrayList< Datum > getData() {
		return data;
	}

	*//**
	 * @param data The data
	 *//*
	public void setData( ArrayList< Datum > data ) {
		this.data = data;
	}
*/


}
