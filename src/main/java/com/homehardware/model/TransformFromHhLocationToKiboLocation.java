package com.homehardware.model;

import com.homehardware.beans.Store;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.FulfillmentType;
import com.mozu.api.contracts.location.Hours;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.RegularHours;
import com.mozu.api.contracts.location.ShippingOriginContact;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public final class TransformFromHhLocationToKiboLocation {

	public static void testTransformHhLocationToKiboLocation
	(final Location location, final Store store) {

		// transform hh location address obj to kibo location address
		setAddress(location, store);

		location.setAllowFulfillmentWithNoStock(false);

		setOtherDetails(location, store);

		// transform hh location store code obj to kibo location store code

		final List<FulfillmentType> fulfillmentTypes = new ArrayList<>();

		location.setFulfillmentTypes(fulfillmentTypes);

		// transform hh location geo obj to kibo location geo
		setGeoCoordinates(location, store);

		// transform hh location address obj to kibo location address
		setRegularHours(location, store);

		setShippingoriginContact(location, store);

		/*
		 * List<LocationAttribute> attributes = new
		 * ArrayList<LocationAttribute>(); //LocationAttribute
		 * whse=(LocationAttribute)store.getWhse(); //attributes.add(whse);
		 * 
		 * location.setAttributes(attributes);
		 */

	}

	protected static void setOtherDetails(final Location location, final Store store) {
		location.setIsDisabled(false);

		final List<LocationType> locationTypes = new ArrayList<>();

		LocationType locationType = new LocationType();
		locationType.setCode("WH1");
		locationType.setName("WareHouse");
		locationTypes.add(locationType);
				
		location.setLocationTypes(locationTypes);

		// transform hh location store code obj to kibo location store code
		location.setName(store.getStoreName());

		// transform hh location content obj to kibo location content(note)
		location.setNote(store.getStoreContent());

		// transform hh location phone number obj to kibo location phone number
		location.setPhone(store.getPhoneNumber());

		location.setCode(store.getStoreNumber());

		location.setDescription("DEFAULT ");

		location.setFax("4444444444");
	}

	protected static void setGeoCoordinates(final Location location, final Store store) {
		// TODO Auto-generated method stub
		final Coordinates geo = new Coordinates();
		geo.setLat(store.getLatitude());
		geo.setLng(store.getLongitiude());
		location.setGeo(geo);

	}

	protected static void setRegularHours(final Location location, final Store store) {
		// TODO Auto-generated method stub
		// get regular hrs details from db obj and set those in to kibo regular
		// hrs obj
		try {
			final JSONObject jsonObject = new JSONObject(store.getRegularHours());
			final RegularHours regularHours = new RegularHours();
			if (jsonObject != null) {
				final Hours hours = new Hours();
				final JSONObject friday = (JSONObject) jsonObject.get("friday");
				hours.setLabel(friday.getString("label"));
				regularHours.setFriday(hours);

				final JSONObject wednesday = (JSONObject) jsonObject.get("wednesday");
				hours.setLabel(wednesday.getString("label"));
				regularHours.setWednesday(hours);
				setDays(jsonObject, hours, regularHours);
			}
			location.setRegularHours(regularHours);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	protected static void setDays(final JSONObject jsonObject, final Hours hours, final RegularHours regularHours) {
		try {
			final JSONObject saturday = (JSONObject) jsonObject.get("saturday");
			hours.setLabel(saturday.getString("label"));
			regularHours.setSaturday(hours);

			final JSONObject sunday = (JSONObject) jsonObject.get("sunday");
			hours.setLabel(sunday.getString("label"));
			regularHours.setSunday(hours);

			setOtherDays(jsonObject, hours, regularHours);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	protected static void setOtherDays(final JSONObject jsonObject, final Hours hours,
			final RegularHours regularHours) {
		try {
			final JSONObject thursday = (JSONObject) jsonObject.get("thursday");
			hours.setLabel(thursday.getString("label"));
			regularHours.setThursday(hours);

			final JSONObject monday = (JSONObject) jsonObject.get("monday");
			hours.setLabel(monday.getString("label"));
			regularHours.setMonday(hours);

			final JSONObject tuesday = (JSONObject) jsonObject.get("tuesday");
			hours.setLabel(tuesday.getString("label"));
			regularHours.setTuesday(hours);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	protected static void setTuesDays(final JSONObject jsonObject, final Hours hours,

			final RegularHours regularHours) {
		try {
			final JSONObject tuesday = (JSONObject) jsonObject.get("tuesday");
			hours.setLabel(tuesday.getString("label"));
			regularHours.setTuesday(hours);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected static void setAddress(final Location location, final Store store) {
		// TODO Auto-generated method stub
		final Address address = new Address();
		// get address details from db obj and set those in to kibo address obj
		address.setAddress1(store.getStoreAdd1());
		address.setAddress2(store.getStoreAdd2());
		address.setAddress3("");
		address.setAddress4("");
		
		address.setCountryCode("CA");
		address.setCityOrTown(store.getCity());
		address.setAddressType("Commercial");
		address.setIsValidated(false);
		setZipCode(address, store);
		location.setAddress(address);
		
		//Address address = new Address();
		address.setAddress1("Clarke & Clarke Home Hardware Building Centre");
		//address.setCountryCode("CA");
		/*address.setStateOrProvince("NL");
		address.setCityOrTown("Bell Island");
		address.setPostalOrZipCode("A0A 4H0");
		address.setAddressType("Commercial");
		address.setIsValidated(false);*/

	}

	protected static void setZipCode(final Address address, final Store store) {
		address.setStateOrProvince(store.getProvince());
		address.setPostalOrZipCode(store.getPostalCode());
		address.setCountryCode("CA");
	}

	protected static void setShippingoriginContact(final Location location, final Store store) {
		final ShippingOriginContact shippingOriginContact = new ShippingOriginContact();
		
		shippingOriginContact.setFirstName("Alex");
		shippingOriginContact.setLastNameOrSurname("Graham");
		shippingOriginContact.setCompanyOrOrganization("HH");
		shippingOriginContact.setEmail("ag@hh.ca");
		shippingOriginContact.setPhoneNumber("1111111111");
				
		location.setShippingOriginContact(shippingOriginContact);
	}
}
