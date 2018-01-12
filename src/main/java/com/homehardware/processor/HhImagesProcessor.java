package com.homehardware.processor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hh.integration.constants.Constant;
import com.homehardware.model.Images;
import com.homehardware.utility.ProductUtility;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.content.Document;
import com.mozu.api.contracts.content.DocumentCollection;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductInCatalogInfo;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductLocalizedImage;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.content.documentlists.DocumentResource;

public class HhImagesProcessor {

	protected static final Logger logger = Logger.getLogger(HhImagesProcessor.class);	
	
	/**
	 * @param product.
	 * @param images.
	 * @param productResource.
	 * @param apiContext.
	 */
	public void transformHhImageToKiboImage(final Product product,
               final List<Images> images,
               final ProductResource productResource,final ApiContext apiContext) {
		final ProductLocalizedContent content = product.getContent();
		List<ProductLocalizedImage> productLocalizedImages = content.getProductImages();
		if (productLocalizedImages == null) {
			productLocalizedImages = new ArrayList<ProductLocalizedImage>();
			product.getContent().setProductImages(productLocalizedImages);
		}
		
		if (images != null && images.size() != 0) {
			for (Images img : images) {
				final File file = new File(
						Constant.IMAGES_FOLDER_LOC
				        + img.getImageId() + ".jpg");
				if (file.exists()) { 
					uploadImages(file, apiContext,
								img, productLocalizedImages);
			        } else {
			        	logger.info("No image exists with image id " + img);
			        }
			}
			content.setProductImages(productLocalizedImages);
		}
		final List<ProductInCatalogInfo> list = product.getProductInCatalogs();
		setFrCatalogImages(productLocalizedImages, list);
		
	}
	
	/**
	 * @param productLocalizedImages.
	 * @param list.
	 */
	public void setFrCatalogImages(
		final List<ProductLocalizedImage> productLocalizedImages,
		final List<ProductInCatalogInfo> list) {
		if (list != null && list.size() != 0) {
			for (ProductInCatalogInfo p : list) {
				if (p.getCatalogId() == Constant.FR_CATALOG_ID) {
					p.getContent().setProductImages(productLocalizedImages);
				}
			}
		}
	}
	
	/**
	 * @param file.
	 * @param apiContext.
	 * @param img.
	 * @param productLocalizedImages.
	 */
	public void uploadImages(
			final File file, final ApiContext apiContext, final Images img,
			final List<ProductLocalizedImage> productLocalizedImages) {
	        final String cmsId = saveFileToFileManager(
					file, new Integer(img.getImageId())
					.toString(), apiContext);

		if (!containsImage(productLocalizedImages, cmsId)) {
			logger.info("Image wth cms id " 
					+ cmsId + " not existing in product so adding image !!!!");
			final ProductLocalizedImage productLocalizedImage 
				= new ProductLocalizedImage();
			productLocalizedImage.setLocaleCode(Constant.LOCALE);
			productLocalizedImage.setCmsId(cmsId);
			productLocalizedImages.add(productLocalizedImage);
		} else {
			logger.info("Image wth cms id " 
					+ cmsId + " already existing in product"
							+ " so not adding image to product !!!!");
		}
	}

	/**
	 * @param productLocalizedImages.
	 * @param cmsId.
	 * @.return
	 */
	public boolean containsImage(
			final List<ProductLocalizedImage> productLocalizedImages,
			final String cmsId) {
		boolean containsImage = false;
		if (productLocalizedImages != null && productLocalizedImages.size() != 0) {
			for (ProductLocalizedImage productLocalizedImage : productLocalizedImages) {
				if (productLocalizedImage.getCmsId().equalsIgnoreCase(cmsId)) {
					containsImage = true;
					break;
				}
			}
		}
		return containsImage;
	}
	/**
	 * @param file.
	 * @param imageName.
	 * @param context.
	 * @return.
	 */
	public static String saveFileToFileManager(
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
	public static Document createOrUpdateDocument(
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
