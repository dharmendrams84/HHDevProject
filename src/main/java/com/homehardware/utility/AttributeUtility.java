package com.homehardware.utility;

import com.hh.integration.constants.Constant;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValue;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueInProductType;
import com.mozu.api.contracts.productadmin.AttributeVocabularyValueLocalizedContent;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

public class AttributeUtility {

	
	/**
	 * @param value.
	 * @.return
	 */
	public static AttributeVocabularyValue 
		createAttributeVocabularyValue(final String value) {
	
		final AttributeVocabularyValueLocalizedContent content
			= new AttributeVocabularyValueLocalizedContent();
		content.setLocaleCode(Constant.LOCALE);
		content.setStringValue(value);
		
		final AttributeVocabularyValue attributeVocabularyValue
			= new AttributeVocabularyValue();
		attributeVocabularyValue.setContent(content);
		attributeVocabularyValue.setValue(value);
		return attributeVocabularyValue;
		
	}
	
	
	/**
	 * @param value.
	 * @.return
	 */
	public static AttributeVocabularyValueInProductType
		createAttrVocInProdType(final String value) {
		
		final AttributeVocabularyValueLocalizedContent content 
			= new AttributeVocabularyValueLocalizedContent();
		content.setLocaleCode(Constant.LOCALE);
		content.setStringValue(value);
		final AttributeVocabularyValueInProductType attributeVocabularyValueInProductType = new AttributeVocabularyValueInProductType();
		final AttributeVocabularyValue attributeVocabularyValue = new AttributeVocabularyValue();
		attributeVocabularyValue.setContent(content);
		attributeVocabularyValue.setValue(value);
		//attributeVocabularyValue.setValueSequence(23);
		attributeVocabularyValueInProductType.setVocabularyValueDetail(attributeVocabularyValue);
		attributeVocabularyValueInProductType.setValue(value);
		return attributeVocabularyValueInProductType;
	}
}
