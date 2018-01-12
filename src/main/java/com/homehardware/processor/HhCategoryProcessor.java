package com.homehardware.processor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hh.integration.constants.Constant;
import com.homehardware.model.Hierarchy;
import com.homehardware.model.Images;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.content.Document;
import com.mozu.api.contracts.content.DocumentCollection;
import com.mozu.api.contracts.productadmin.Category;
import com.mozu.api.contracts.productadmin.CategoryLocalizedContent;
import com.mozu.api.contracts.productadmin.CategoryLocalizedImage;
import com.mozu.api.contracts.productadmin.CategoryPagedCollection;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.content.documentlists.DocumentResource;

public class HhCategoryProcessor {

	protected static final Logger logger = Logger.getLogger(HhCategoryProcessor.class);

	public void transformHhHierarchyToKiboCategory(final List<Hierarchy> list,
			final ApiContext apiContext) {
		final CategoryResource categoryResource 
		= new CategoryResource(apiContext);
		try{
			for(Hierarchy hierarchy :list){
				final CategoryPagedCollection categoryCollection = categoryResource.getCategories(null, null, null, 
							Constant.QUERY_CATEGORYCODE_EQ + hierarchy.getDivision(), null);	
				Category mozuCategory = null;
				String imagename = "9999888";
				if( null != categoryCollection && 
						!categoryCollection.getItems().isEmpty()){
					final List<Category> items 
						= categoryCollection.getItems();
					mozuCategory = items.get(0);
					convertCategory(mozuCategory, hierarchy);
					transformImages(imagename,mozuCategory, apiContext);
					categoryResource.updateCategory(mozuCategory,
							mozuCategory.getId());
					logger.info("Category " + mozuCategory.getId() +
							" updated successfully!!!!!");
				} else {
					mozuCategory = new Category();
					
					mozuCategory.setCatalogId(Constant.master_catalog_id);
					convertCategory(mozuCategory, hierarchy);
					transformImages(imagename,mozuCategory, apiContext);
					categoryResource.addCategory(mozuCategory);
					logger.info("Category with code " + 
							mozuCategory.getCategoryCode() + 
							" add successfully to kibo!!!!!");
				}
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void transformImages(final String imageName,
			final Category mozuCategory,
			final ApiContext apiContext){
		
		final File file = new File(Constant.IMAGES_FOLDER_LOC + imageName + ".jpg");
		final String cmsId = saveFileToFileManager(file, imageName, apiContext);
		List<CategoryLocalizedImage> listImages
			= mozuCategory.getContent().getCategoryImages();
		if (listImages == null) {
			listImages 
				= new ArrayList<CategoryLocalizedImage>();
		}
		boolean containsCmsId = false;
		for (CategoryLocalizedImage c : listImages) {
			if (c.getCmsId().equalsIgnoreCase(cmsId)) {
				containsCmsId = true;
			}
		}
		if (!containsCmsId) {
			final CategoryLocalizedImage categoryLocalizedImage 
				= new CategoryLocalizedImage();
			categoryLocalizedImage.setCmsId(cmsId);
			listImages.add(categoryLocalizedImage);
		}
	
		mozuCategory.getContent().setCategoryImages(listImages);

	}
	
	/**
	 * @param mozuCategory.
	 * @param hierarchy.
	 */
	public void convertCategory(final Category mozuCategory, 
			final Hierarchy hierarchy) {
		
		/*mozuCategory.setParentCategoryName(hierarchy.get);
		mozuCategory.setParentCategoryCode(parentCategoryCode);
		mozuCategory.setParentCategoryId(parentCategoryId);*/
		
		//mozuCategory.setCatalogId(2);
		
		mozuCategory
		.setCategoryCode(new Integer(
				hierarchy.getDivision()).toString());
		
		
		/*content.setMetaTagDescription(hierarchy.getDivisionName());
		content.setMetaTagKeywords(hierarchy.getDivisionName());
		content.setMetaTagTitle(hierarchy.getDivisionName());
		content.setPageTitle(hierarchy.getDivisionName());*/
		CategoryLocalizedContent content = mozuCategory.getContent();
		if (content == null) {
			content = new CategoryLocalizedContent();
		}
		content.setName(hierarchy.getDivisionName());
		
		content.setDescription(hierarchy.getMrkCatLevel1());
		content.setSlug("slug");
		content.setLocaleCode(Constant.LOCALE);
		mozuCategory.setContent(content);
		mozuCategory.setIsDisplayed(true);
		mozuCategory.setIsActive(true);
		
	}
	
	/**
	 * @param file.
	 * @param imageName.
	 * @param context.
	 * @return.
	 */
	public String saveFileToFileManager(
			final File file,final String imageName,final ApiContext context) {
		Document retDoc = null;
		try {
			
			final DocumentResource docResource = new DocumentResource(context);
			
		
			final Document doc = new Document();
			
			createDocumentObject(doc, imageName, file);
			
			final Document newDoc = createOrUpdateDocument(docResource, doc, imageName);
			
			retDoc = docResource.getDocument(Constant.FILES_MOZU, newDoc.getId());
			
			final FileInputStream fin = new FileInputStream(file);
			docResource.updateDocumentContent(
					fin, Constant.FILES_MOZU,
					retDoc.getId(), Constant.EXTENSION_JPG);
			
			
		} catch (Exception e) {
			logger.error("Exception while uploading image "
					+ imageName);
			
		}
		return retDoc.getId();
	}

	/**
	 * @param docResource.
	 * @param doc.
	 * @param imageName.
	 * @return.
	 */
	public Document createOrUpdateDocument(
			final DocumentResource docResource, final Document doc,
			final String imageName) {
		Document newDoc = null ;
		try {
			final DocumentCollection existingDoc 
			    = docResource.getDocuments(
			    		Constant.FILES_MOZU, "name eq " + imageName, null,
					1, 0, Boolean.TRUE, null);

			//Document newDoc = null;
			if (existingDoc == null || existingDoc.getItems().isEmpty()) {
				newDoc = docResource.createDocument(doc, Constant.FILES_MOZU);
				logger.info("Creating Document with name " + imageName);
			} else {
				newDoc = existingDoc.getItems().get(0);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error while creating or updating document " + e.getMessage());
		}
		return newDoc;
	}

	/**
	 * @param doc.
	 * @param imageName.
	 * @param file.
	 */
	public static void createDocumentObject(
			final Document doc,final String imageName,final File file) {
		doc.setContentMimeType(Constant.IMAGE_JPG);
		doc.setExtension(Constant.EXTENSION_JPG);
		doc.setName(imageName);
		doc.setDocumentTypeFQN(Constant.IMAGE_MOZU);
		if (file.exists()) {
			final long length = Long.valueOf(file.length());
			doc.setContentLength(length);
			logger.info("Content length is " + length);
		}

	}

}
