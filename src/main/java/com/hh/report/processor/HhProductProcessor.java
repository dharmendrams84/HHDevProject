/**
 *  =========================================================================================================
 *  Description : camel product processor to get product file data
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  =========================================================================================================
 *  1.0         Ashwinee Salunkhe           11/01/17      camel product processor to get product file data
 *  =========================================================================================================
 */
package com.hh.report.processor;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.hh.model.dao.HHProductAttrDAO;
/**
 * Processor class to processing product record
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 1, 2017
 */
public class HHProductProcessor implements Processor{

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		Collection<HHProductAttrDAO> productDAO = new ArrayList<HHProductAttrDAO>();
		productDAO= (Collection<HHProductAttrDAO>) exchange.getIn().getBody();
		System.out.println("Product dao length = :"+productDAO.size());
		productDAO.forEach(productRow -> {
			System.out.println("Product Code : "+productRow.getProductID());
			System.out.println("Product Description : "+productRow.getProductDesc());
		});
		System.out.println();
	}
}
