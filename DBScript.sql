create table ignitiv.`store`(
   id int auto_increment,
   JOB_ID int,
   STORE_NUMBER varchar(64) NOT NULL,
   STORE_NAME varchar(64) NOT NULL,
   STORE_ADD1 text NOT NULL,
   STORE_ADD2  text,
   CITY varchar(64) NOT NULL,
   PROVINCE varchar(64) NOT NULL,
   POSTAL_CODE varchar(64) NOT NULL,
   PHONE_NUMBER varchar(64),
   WHSE int,
   STORE_FORMAT varchar(16),
   ECOMMERCE_IND varchar(2) NOT NULL default 'N',
   STORE_SERVICES text,
   REGULAR_HOURS text,
   STORE_CONTENT text,
   LATITUDE double,
   LONGITUDE double,
   STORE_WEBSITE_URL tinytext,
   PRIMARY KEY ( id ),
   index STORE_INDEX(STORE_NUMBER,STORE_NAME)

);

create table ignitiv.`ecofees`(
  id int auto_increment,
  ITEM INT,
  PROV varchar(64),
  FEE_AMT double,
  PRIMARY KEY ( id ),
  index ECOFEES_INDEX(ITEM,PROV)
);

create table ignitiv.`item`(
  id int auto_increment,
  ITEM varchar(64),
  ITEM_DESC text,
  HH_SELLING_UOM varchar(100),
  SELLING_UOM_FR varchar(100),
  PRODUCT_CLASS int,
  CATEGORY int,
  FINELINE_CLASS int,
  ITEM_DESC_FR text,
  HH_LUMBER_IND varchar(1) DEFAULT 'N',
  HH_HARDWARE_IND varchar(1) DEFAULT 'N',
  HH_FURNITURE_IND varchar(1) DEFAULT 'N',
  HH_ITEM_SOURCE_CODE varchar(1),
  HH_WARRANTY_EXCHANGE_IND varchar(1) DEFAULT 'N',
  COMMENTS text,
  HH_CTRL_BRAND_IND varchar(1) DEFAULT 'N',
  HH_CONSUMER_ITEM_DESC text,
  HH_CONSUMER_ITEM_DESC_FR text,
  ECOMMERCE_IND varchar(1) DEFAULT 'N',
  BRAND_CODE varchar(64),
  COMMENTS_FR text,
  LIMITED_QTY_IND varchar(1) DEFAULT 'N',
  DISPLAY_ITEM varchar(100),
  PRIMARY KEY ( id ),
 index  BRAND_INDEX(BRAND_CODE),
index ITEM_INDEX(ITEM)
);

commit;