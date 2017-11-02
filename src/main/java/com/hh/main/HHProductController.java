/**
 *  ===========================================================================================================
 *  Description : camel product controller for accessing camel product context
 *  Created By  : Ashwinee Salunkhe
 *  
 *  VERSION     MODIFIED BY               (MM/DD/YY)      Description
 *  ============================================================================================================
 *  1.0         Ashwinee Salunkhe           11/01/17      camel product controller for accessing camel context
 *  ============================================================================================================
 */
package com.hh.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Controller class to access camel context
 * 
 * @author Ashwinee Salunkhe
 * @created Nov 1, 2017
 */
public class HHProductController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			executeSpringDSL();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	@SuppressWarnings("resource")
	public static void executeSpringDSL() throws Exception {
		new ClassPathXmlApplicationContext("camel-product-context.xml");
				
	}


}
