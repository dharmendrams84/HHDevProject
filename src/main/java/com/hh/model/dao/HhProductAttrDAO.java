/**
 *  ====================================================================================================
 *  Description : camel product csv  record
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ====================================================================================================
 *  1.0         Ashwinee Salunkhe           11/01/17      camel product csv  record
 *  ====================================================================================================
 */
package com.hh.model.dao;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
/**
 * Product CSV record
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 1, 2017
 */
@CsvRecord(separator = ",")
public class HHProductAttrDAO {
	
	private static final int _1 = 1;

	@DataField(pos=_1)
	private String productID;
	
	@DataField(pos=2)
	private String productDesc;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
		

}
